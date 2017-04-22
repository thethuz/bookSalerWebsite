package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BookManagementApp;

import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.repository.CartRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CartResource REST controller.
 *
 * @see CartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookManagementApp.class)
public class CartResourceIntTest {

    private static final String DEFAULT_TOTAL_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_PRICE = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final ZonedDateTime DEFAULT_ORDER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ORDER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private CartRepository cartRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCartMockMvc;

    private Cart cart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CartResource cartResource = new CartResource();
        ReflectionTestUtils.setField(cartResource, "cartRepository", cartRepository);
        this.restCartMockMvc = MockMvcBuilders.standaloneSetup(cartResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cart createEntity() {
        Cart cart = new Cart()
                .totalPrice(DEFAULT_TOTAL_PRICE)
                .userId(DEFAULT_USER_ID)
                .orderDate(DEFAULT_ORDER_DATE);
        return cart;
    }

    @Before
    public void initTest() {
        cartRepository.deleteAll();
        cart = createEntity();
    }

    @Test
    public void createCart() throws Exception {
        int databaseSizeBeforeCreate = cartRepository.findAll().size();

        // Create the Cart

        restCartMockMvc.perform(post("/api/carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cart)))
            .andExpect(status().isCreated());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate + 1);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testCart.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCart.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
    }

    @Test
    public void createCartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartRepository.findAll().size();

        // Create the Cart with an existing ID
        Cart existingCart = new Cart();
        existingCart.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartMockMvc.perform(post("/api/carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCart)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCarts() throws Exception {
        // Initialize the database
        cartRepository.save(cart);

        // Get all the cartList
        restCartMockMvc.perform(get("/api/carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cart.getId())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(sameInstant(DEFAULT_ORDER_DATE))));
    }

    @Test
    public void getCart() throws Exception {
        // Initialize the database
        cartRepository.save(cart);

        // Get the cart
        restCartMockMvc.perform(get("/api/carts/{id}", cart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cart.getId()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.orderDate").value(sameInstant(DEFAULT_ORDER_DATE)));
    }

    @Test
    public void getNonExistingCart() throws Exception {
        // Get the cart
        restCartMockMvc.perform(get("/api/carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCart() throws Exception {
        // Initialize the database
        cartRepository.save(cart);
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart
        Cart updatedCart = cartRepository.findOne(cart.getId());
        updatedCart
                .totalPrice(UPDATED_TOTAL_PRICE)
                .userId(UPDATED_USER_ID)
                .orderDate(UPDATED_ORDER_DATE);

        restCartMockMvc.perform(put("/api/carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCart)))
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testCart.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCart.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
    }

    @Test
    public void updateNonExistingCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Create the Cart

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCartMockMvc.perform(put("/api/carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cart)))
            .andExpect(status().isCreated());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCart() throws Exception {
        // Initialize the database
        cartRepository.save(cart);
        int databaseSizeBeforeDelete = cartRepository.findAll().size();

        // Get the cart
        restCartMockMvc.perform(delete("/api/carts/{id}", cart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
