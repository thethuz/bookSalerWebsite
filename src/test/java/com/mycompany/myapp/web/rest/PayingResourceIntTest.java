package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BookManagementApp;

import com.mycompany.myapp.domain.Paying;
import com.mycompany.myapp.repository.PayingRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PayingResource REST controller.
 *
 * @see PayingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookManagementApp.class)
public class PayingResourceIntTest {

    private static final long DEFAULT_PRICE = 0;
    private static final long UPDATED_PRICE = 1;

    private static final long DEFAULT_PRICE_WITH_VAT = 0;
    private static final long UPDATED_PRICE_WITH_VAT = 1;

    private static final String DEFAULT_PHUONG_THUC_THANH_TOAN = "AAAAAAAAAA";
    private static final String UPDATED_PHUONG_THUC_THANH_TOAN = "BBBBBBBBBB";

    private static final String DEFAULT_HOTEN = "AAAAAAAAAA";
    private static final String UPDATED_HOTEN = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DIA_CHI = "AAAAAAAAAA";
    private static final String UPDATED_DIA_CHI = "BBBBBBBBBB";

    private static final String DEFAULT_CHI_TIET_GIAO_DICH = "AAAAAAAAAA";
    private static final String UPDATED_CHI_TIET_GIAO_DICH = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CARTID = "AAAAAAAAAA";
    private static final String UPDATED_CARTID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DA_GIAO_TIEN = false;
    private static final Boolean UPDATED_DA_GIAO_TIEN = true;

    private static final Boolean DEFAULT_DA_GIAO_HANG = false;
    private static final Boolean UPDATED_DA_GIAO_HANG = true;

    @Inject
    private PayingRepository payingRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPayingMockMvc;

    private Paying paying;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PayingResource payingResource = new PayingResource();
        ReflectionTestUtils.setField(payingResource, "payingRepository", payingRepository);
        this.restPayingMockMvc = MockMvcBuilders.standaloneSetup(payingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paying createEntity() {
        Paying paying = new Paying()
                .price(DEFAULT_PRICE)
                .priceWithVAT(DEFAULT_PRICE_WITH_VAT)
                .phuongThucThanhToan(DEFAULT_PHUONG_THUC_THANH_TOAN)
                .hoten(DEFAULT_HOTEN)
                .email(DEFAULT_EMAIL)
                .diaChi(DEFAULT_DIA_CHI)
                .chiTietGiaoDich(DEFAULT_CHI_TIET_GIAO_DICH)
                .user_id(DEFAULT_USER_ID)
                .cartid(DEFAULT_CARTID)
                .daGiaoTien(DEFAULT_DA_GIAO_TIEN)
                .daGiaoHang(DEFAULT_DA_GIAO_HANG);
        return paying;
    }

    @Before
    public void initTest() {
        payingRepository.deleteAll();
        paying = createEntity();
    }

    @Test
    public void createPaying() throws Exception {
        int databaseSizeBeforeCreate = payingRepository.findAll().size();

        // Create the Paying

        restPayingMockMvc.perform(post("/api/payings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paying)))
            .andExpect(status().isCreated());

        // Validate the Paying in the database
        List<Paying> payingList = payingRepository.findAll();
        assertThat(payingList).hasSize(databaseSizeBeforeCreate + 1);
        Paying testPaying = payingList.get(payingList.size() - 1);
        assertThat(testPaying.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPaying.getPriceWithVAT()).isEqualTo(DEFAULT_PRICE_WITH_VAT);
        assertThat(testPaying.getPhuongThucThanhToan()).isEqualTo(DEFAULT_PHUONG_THUC_THANH_TOAN);
        assertThat(testPaying.getHoten()).isEqualTo(DEFAULT_HOTEN);
        assertThat(testPaying.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPaying.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
        assertThat(testPaying.getChiTietGiaoDich()).isEqualTo(DEFAULT_CHI_TIET_GIAO_DICH);
        assertThat(testPaying.getUser_id()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPaying.getCartid()).isEqualTo(DEFAULT_CARTID);
        assertThat(testPaying.isDaGiaoTien()).isEqualTo(DEFAULT_DA_GIAO_TIEN);
        assertThat(testPaying.isDaGiaoHang()).isEqualTo(DEFAULT_DA_GIAO_HANG);

    }

    @Test
    public void createPayingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payingRepository.findAll().size();

        // Create the Paying with an existing ID
        Paying existingPaying = new Paying();
        existingPaying.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayingMockMvc.perform(post("/api/payings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPaying)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Paying> payingList = payingRepository.findAll();
        assertThat(payingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPayings() throws Exception {
        // Initialize the database
        payingRepository.save(paying);

        // Get all the payingList
        restPayingMockMvc.perform(get("/api/payings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paying.getId())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].priceWithVAT").value(hasItem(DEFAULT_PRICE_WITH_VAT)))
            .andExpect(jsonPath("$.[*].phuongThucThanhToan").value(hasItem(DEFAULT_PHUONG_THUC_THANH_TOAN.toString())))
            .andExpect(jsonPath("$.[*].hoten").value(hasItem(DEFAULT_HOTEN.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].diaChi").value(hasItem(DEFAULT_DIA_CHI.toString())))
            .andExpect(jsonPath("$.[*].chiTietGiaoDich").value(hasItem(DEFAULT_CHI_TIET_GIAO_DICH.toString())))
            .andExpect(jsonPath("$.[*].user_id").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].cartid").value(hasItem(DEFAULT_CARTID.toString())))
            .andExpect(jsonPath("$.[*].daGiaoTien").value(hasItem(DEFAULT_DA_GIAO_TIEN.booleanValue())))
            .andExpect(jsonPath("$.[*].daGiaoHang").value(hasItem(DEFAULT_DA_GIAO_HANG.booleanValue())));
    }

    @Test
    public void getPaying() throws Exception {
        // Initialize the database
        payingRepository.save(paying);

        // Get the paying
        restPayingMockMvc.perform(get("/api/payings/{id}", paying.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paying.getId()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.priceWithVAT").value(DEFAULT_PRICE_WITH_VAT))
            .andExpect(jsonPath("$.phuongThucThanhToan").value(DEFAULT_PHUONG_THUC_THANH_TOAN.toString()))
            .andExpect(jsonPath("$.hoten").value(DEFAULT_HOTEN.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.diaChi").value(DEFAULT_DIA_CHI.toString()))
            .andExpect(jsonPath("$.chiTietGiaoDich").value(DEFAULT_CHI_TIET_GIAO_DICH.toString()))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.cartid").value(DEFAULT_CARTID.toString()))
            .andExpect(jsonPath("$.daGiaoTien").value(DEFAULT_DA_GIAO_TIEN.booleanValue()))
            .andExpect(jsonPath("$.daGiaoHang").value(DEFAULT_DA_GIAO_HANG.booleanValue()));
    }

    @Test
    public void getNonExistingPaying() throws Exception {
        // Get the paying
        restPayingMockMvc.perform(get("/api/payings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePaying() throws Exception {
        // Initialize the database
        payingRepository.save(paying);
        int databaseSizeBeforeUpdate = payingRepository.findAll().size();

        // Update the paying
        Paying updatedPaying = payingRepository.findOne(paying.getId());
        updatedPaying
                .price(UPDATED_PRICE)
                .priceWithVAT(UPDATED_PRICE_WITH_VAT)
                .phuongThucThanhToan(UPDATED_PHUONG_THUC_THANH_TOAN)
                .hoten(UPDATED_HOTEN)
                .email(UPDATED_EMAIL)
                .diaChi(UPDATED_DIA_CHI)
                .chiTietGiaoDich(UPDATED_CHI_TIET_GIAO_DICH)
                .user_id(UPDATED_USER_ID)
                .cartid(UPDATED_CARTID)
                .daGiaoTien(UPDATED_DA_GIAO_TIEN)
                .daGiaoHang(UPDATED_DA_GIAO_HANG);

        restPayingMockMvc.perform(put("/api/payings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaying)))
            .andExpect(status().isOk());

        // Validate the Paying in the database
        List<Paying> payingList = payingRepository.findAll();
        assertThat(payingList).hasSize(databaseSizeBeforeUpdate);
        Paying testPaying = payingList.get(payingList.size() - 1);
        assertThat(testPaying.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPaying.getPriceWithVAT()).isEqualTo(UPDATED_PRICE_WITH_VAT);
        assertThat(testPaying.getPhuongThucThanhToan()).isEqualTo(UPDATED_PHUONG_THUC_THANH_TOAN);
        assertThat(testPaying.getHoten()).isEqualTo(UPDATED_HOTEN);
        assertThat(testPaying.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaying.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testPaying.getChiTietGiaoDich()).isEqualTo(UPDATED_CHI_TIET_GIAO_DICH);
        assertThat(testPaying.getUser_id()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPaying.getCartid()).isEqualTo(UPDATED_CARTID);
        assertThat(testPaying.isDaGiaoTien()).isEqualTo(UPDATED_DA_GIAO_TIEN);
        assertThat(testPaying.isDaGiaoHang()).isEqualTo(UPDATED_DA_GIAO_HANG);
    }

    @Test
    public void updateNonExistingPaying() throws Exception {
        int databaseSizeBeforeUpdate = payingRepository.findAll().size();

        // Create the Paying

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayingMockMvc.perform(put("/api/payings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paying)))
            .andExpect(status().isCreated());

        // Validate the Paying in the database
        List<Paying> payingList = payingRepository.findAll();
        assertThat(payingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePaying() throws Exception {
        // Initialize the database
        payingRepository.save(paying);
        int databaseSizeBeforeDelete = payingRepository.findAll().size();

        // Get the paying
        restPayingMockMvc.perform(delete("/api/payings/{id}", paying.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Paying> payingList = payingRepository.findAll();
        assertThat(payingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
