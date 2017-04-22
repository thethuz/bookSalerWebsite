package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BookManagementApp;

import com.mycompany.myapp.domain.CartChiTiet;
import com.mycompany.myapp.repository.CartChiTietRepository;
import com.mycompany.myapp.service.CartChiTietService;

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
 * Test class for the CartChiTietResource REST controller.
 *
 * @see CartChiTietResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookManagementApp.class)
public class CartChiTietResourceIntTest {

    private static final Long DEFAULT_BOOK_ID = 1L;
    private static final Long UPDATED_BOOK_ID = 2L;

    private static final Integer DEFAULT_NUMBER_OF_BOOK = 1;
    private static final Integer UPDATED_NUMBER_OF_BOOK = 2;

    private static final String DEFAULT_THANHTIEN = "AAAAAAAAAA";
    private static final String UPDATED_THANHTIEN = "BBBBBBBBBB";

    private static final Long DEFAULT_CART_ID = 1L;
    private static final Long UPDATED_CART_ID = 2L;

    @Inject
    private CartChiTietRepository cartChiTietRepository;

    @Inject
    private CartChiTietService cartChiTietService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCartChiTietMockMvc;

    private CartChiTiet cartChiTiet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CartChiTietResource cartChiTietResource = new CartChiTietResource();
        ReflectionTestUtils.setField(cartChiTietResource, "cartChiTietService", cartChiTietService);
        this.restCartChiTietMockMvc = MockMvcBuilders.standaloneSetup(cartChiTietResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartChiTiet createEntity() {
        CartChiTiet cartChiTiet = new CartChiTiet()
                .bookId(DEFAULT_BOOK_ID)
                .numberOfBook(DEFAULT_NUMBER_OF_BOOK)
                .thanhtien(DEFAULT_THANHTIEN)
                .cartId(DEFAULT_CART_ID);
        return cartChiTiet;
    }

    @Before
    public void initTest() {
        cartChiTietRepository.deleteAll();
        cartChiTiet = createEntity();
    }

    @Test
    public void createCartChiTiet() throws Exception {
        int databaseSizeBeforeCreate = cartChiTietRepository.findAll().size();

        // Create the CartChiTiet

        restCartChiTietMockMvc.perform(post("/api/cart-chi-tiets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartChiTiet)))
            .andExpect(status().isCreated());

        // Validate the CartChiTiet in the database
        List<CartChiTiet> cartChiTietList = cartChiTietRepository.findAll();
        assertThat(cartChiTietList).hasSize(databaseSizeBeforeCreate + 1);
        CartChiTiet testCartChiTiet = cartChiTietList.get(cartChiTietList.size() - 1);
        assertThat(testCartChiTiet.getBookId()).isEqualTo(DEFAULT_BOOK_ID);
        assertThat(testCartChiTiet.getNumberOfBook()).isEqualTo(DEFAULT_NUMBER_OF_BOOK);
        assertThat(testCartChiTiet.getThanhtien()).isEqualTo(DEFAULT_THANHTIEN);
        assertThat(testCartChiTiet.getCartId()).isEqualTo(DEFAULT_CART_ID);
    }

    @Test
    public void createCartChiTietWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartChiTietRepository.findAll().size();

        // Create the CartChiTiet with an existing ID
        CartChiTiet existingCartChiTiet = new CartChiTiet();
        existingCartChiTiet.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartChiTietMockMvc.perform(post("/api/cart-chi-tiets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCartChiTiet)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CartChiTiet> cartChiTietList = cartChiTietRepository.findAll();
        assertThat(cartChiTietList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCartChiTiets() throws Exception {
        // Initialize the database
        cartChiTietRepository.save(cartChiTiet);

        // Get all the cartChiTietList
        restCartChiTietMockMvc.perform(get("/api/cart-chi-tiets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartChiTiet.getId())))
            .andExpect(jsonPath("$.[*].bookId").value(hasItem(DEFAULT_BOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].numberOfBook").value(hasItem(DEFAULT_NUMBER_OF_BOOK)))
            .andExpect(jsonPath("$.[*].thanhtien").value(hasItem(DEFAULT_THANHTIEN.toString())))
            .andExpect(jsonPath("$.[*].cartId").value(hasItem(DEFAULT_CART_ID.intValue())));
    }

    @Test
    public void getCartChiTiet() throws Exception {
        // Initialize the database
        cartChiTietRepository.save(cartChiTiet);

        // Get the cartChiTiet
        restCartChiTietMockMvc.perform(get("/api/cart-chi-tiets/{id}", cartChiTiet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cartChiTiet.getId()))
            .andExpect(jsonPath("$.bookId").value(DEFAULT_BOOK_ID.intValue()))
            .andExpect(jsonPath("$.numberOfBook").value(DEFAULT_NUMBER_OF_BOOK))
            .andExpect(jsonPath("$.thanhtien").value(DEFAULT_THANHTIEN.toString()))
            .andExpect(jsonPath("$.cartId").value(DEFAULT_CART_ID.intValue()));
    }

    @Test
    public void getNonExistingCartChiTiet() throws Exception {
        // Get the cartChiTiet
        restCartChiTietMockMvc.perform(get("/api/cart-chi-tiets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCartChiTiet() throws Exception {
        // Initialize the database
        cartChiTietService.save(cartChiTiet);

        int databaseSizeBeforeUpdate = cartChiTietRepository.findAll().size();

        // Update the cartChiTiet
        CartChiTiet updatedCartChiTiet = cartChiTietRepository.findOne(cartChiTiet.getId());
        updatedCartChiTiet
                .bookId(UPDATED_BOOK_ID)
                .numberOfBook(UPDATED_NUMBER_OF_BOOK)
                .thanhtien(UPDATED_THANHTIEN)
                .cartId(UPDATED_CART_ID);

        restCartChiTietMockMvc.perform(put("/api/cart-chi-tiets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCartChiTiet)))
            .andExpect(status().isOk());

        // Validate the CartChiTiet in the database
        List<CartChiTiet> cartChiTietList = cartChiTietRepository.findAll();
        assertThat(cartChiTietList).hasSize(databaseSizeBeforeUpdate);
        CartChiTiet testCartChiTiet = cartChiTietList.get(cartChiTietList.size() - 1);
        assertThat(testCartChiTiet.getBookId()).isEqualTo(UPDATED_BOOK_ID);
        assertThat(testCartChiTiet.getNumberOfBook()).isEqualTo(UPDATED_NUMBER_OF_BOOK);
        assertThat(testCartChiTiet.getThanhtien()).isEqualTo(UPDATED_THANHTIEN);
        assertThat(testCartChiTiet.getCartId()).isEqualTo(UPDATED_CART_ID);
    }

    @Test
    public void updateNonExistingCartChiTiet() throws Exception {
        int databaseSizeBeforeUpdate = cartChiTietRepository.findAll().size();

        // Create the CartChiTiet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCartChiTietMockMvc.perform(put("/api/cart-chi-tiets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartChiTiet)))
            .andExpect(status().isCreated());

        // Validate the CartChiTiet in the database
        List<CartChiTiet> cartChiTietList = cartChiTietRepository.findAll();
        assertThat(cartChiTietList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCartChiTiet() throws Exception {
        // Initialize the database
        cartChiTietService.save(cartChiTiet);

        int databaseSizeBeforeDelete = cartChiTietRepository.findAll().size();

        // Get the cartChiTiet
        restCartChiTietMockMvc.perform(delete("/api/cart-chi-tiets/{id}", cartChiTiet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CartChiTiet> cartChiTietList = cartChiTietRepository.findAll();
        assertThat(cartChiTietList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
