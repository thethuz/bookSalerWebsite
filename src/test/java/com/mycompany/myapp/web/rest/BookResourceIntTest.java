package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BookManagementApp;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.repository.BookRepository;
import com.mycompany.myapp.service.BookService;

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
 * Test class for the BookResource REST controller.
 *
 * @see BookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookManagementApp.class)
public class BookResourceIntTest {

    private static final String DEFAULT_TEN_SACH = "AAAAAAAAAA";
    private static final String UPDATED_TEN_SACH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TRANG_THAI_CON_HANG = false;
    private static final Boolean UPDATED_TRANG_THAI_CON_HANG = true;

    private static final Long DEFAULT_TAC_GIA = 1L;
    private static final Long UPDATED_TAC_GIA = 2L;

    private static final String DEFAULT_TOM_TAT = "AAAAAAAAAA";
    private static final String UPDATED_TOM_TAT = "BBBBBBBBBB";

    private static final String DEFAULT_GIA_CU = "AAAAAAAAAA";
    private static final String UPDATED_GIA_CU = "BBBBBBBBBB";

    private static final String DEFAULT_GIA_MOI = "AAAAAAAAAA";
    private static final String UPDATED_GIA_MOI = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING = 0;
    private static final Integer UPDATED_RATING = 1;

    private static final String DEFAULT_ANH_DAI_DIEN = "AAAAAAAAAA";
    private static final String UPDATED_ANH_DAI_DIEN = "BBBBBBBBBB";

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    @Inject
    private BookRepository bookRepository;

    @Inject
    private BookService bookService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBookMockMvc;

    private Book book;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookResource bookResource = new BookResource();
        ReflectionTestUtils.setField(bookResource, "bookService", bookService);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createEntity() {
        Book book = new Book()
                .tenSach(DEFAULT_TEN_SACH)
                .trangThaiConHang(DEFAULT_TRANG_THAI_CON_HANG)
                .tacGia(DEFAULT_TAC_GIA)
                .tomTat(DEFAULT_TOM_TAT)
                .giaCu(DEFAULT_GIA_CU)
                .giaMoi(DEFAULT_GIA_MOI)
                .rating(DEFAULT_RATING)
                .anhDaiDien(DEFAULT_ANH_DAI_DIEN)
                .tag(DEFAULT_TAG);
        return book;
    }

    @Before
    public void initTest() {
        bookRepository.deleteAll();
        book = createEntity();
    }

    @Test
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTenSach()).isEqualTo(DEFAULT_TEN_SACH);
        assertThat(testBook.isTrangThaiConHang()).isEqualTo(DEFAULT_TRANG_THAI_CON_HANG);
        assertThat(testBook.getTacGia()).isEqualTo(DEFAULT_TAC_GIA);
        assertThat(testBook.getTomTat()).isEqualTo(DEFAULT_TOM_TAT);
        assertThat(testBook.getGiaCu()).isEqualTo(DEFAULT_GIA_CU);
        assertThat(testBook.getGiaMoi()).isEqualTo(DEFAULT_GIA_MOI);
        assertThat(testBook.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testBook.getAnhDaiDien()).isEqualTo(DEFAULT_ANH_DAI_DIEN);
        assertThat(testBook.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        Book existingBook = new Book();
        existingBook.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBook)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTenSachIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setTenSach(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTrangThaiConHangIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setTrangThaiConHang(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTacGiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setTacGia(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.save(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId())))
            .andExpect(jsonPath("$.[*].tenSach").value(hasItem(DEFAULT_TEN_SACH.toString())))
            .andExpect(jsonPath("$.[*].trangThaiConHang").value(hasItem(DEFAULT_TRANG_THAI_CON_HANG.booleanValue())))
            .andExpect(jsonPath("$.[*].tacGia").value(hasItem(DEFAULT_TAC_GIA.intValue())))
            .andExpect(jsonPath("$.[*].tomTat").value(hasItem(DEFAULT_TOM_TAT.toString())))
            .andExpect(jsonPath("$.[*].giaCu").value(hasItem(DEFAULT_GIA_CU.toString())))
            .andExpect(jsonPath("$.[*].giaMoi").value(hasItem(DEFAULT_GIA_MOI.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].anhDaiDien").value(hasItem(DEFAULT_ANH_DAI_DIEN.toString())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())));
    }

    @Test
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.save(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId()))
            .andExpect(jsonPath("$.tenSach").value(DEFAULT_TEN_SACH.toString()))
            .andExpect(jsonPath("$.trangThaiConHang").value(DEFAULT_TRANG_THAI_CON_HANG.booleanValue()))
            .andExpect(jsonPath("$.tacGia").value(DEFAULT_TAC_GIA.intValue()))
            .andExpect(jsonPath("$.tomTat").value(DEFAULT_TOM_TAT.toString()))
            .andExpect(jsonPath("$.giaCu").value(DEFAULT_GIA_CU.toString()))
            .andExpect(jsonPath("$.giaMoi").value(DEFAULT_GIA_MOI.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.anhDaiDien").value(DEFAULT_ANH_DAI_DIEN.toString()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()));
    }

    @Test
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findOne(book.getId());
        updatedBook
                .tenSach(UPDATED_TEN_SACH)
                .trangThaiConHang(UPDATED_TRANG_THAI_CON_HANG)
                .tacGia(UPDATED_TAC_GIA)
                .tomTat(UPDATED_TOM_TAT)
                .giaCu(UPDATED_GIA_CU)
                .giaMoi(UPDATED_GIA_MOI)
                .rating(UPDATED_RATING)
                .anhDaiDien(UPDATED_ANH_DAI_DIEN)
                .tag(UPDATED_TAG);

        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBook)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTenSach()).isEqualTo(UPDATED_TEN_SACH);
        assertThat(testBook.isTrangThaiConHang()).isEqualTo(UPDATED_TRANG_THAI_CON_HANG);
        assertThat(testBook.getTacGia()).isEqualTo(UPDATED_TAC_GIA);
        assertThat(testBook.getTomTat()).isEqualTo(UPDATED_TOM_TAT);
        assertThat(testBook.getGiaCu()).isEqualTo(UPDATED_GIA_CU);
        assertThat(testBook.getGiaMoi()).isEqualTo(UPDATED_GIA_MOI);
        assertThat(testBook.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testBook.getAnhDaiDien()).isEqualTo(UPDATED_ANH_DAI_DIEN);
        assertThat(testBook.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Get the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
