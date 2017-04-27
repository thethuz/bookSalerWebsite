package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BookManagementApp;

import com.mycompany.myapp.domain.Author;
import com.mycompany.myapp.repository.AuthorRepository;
import com.mycompany.myapp.service.AuthorService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuthorResource REST controller.
 *
 * @see AuthorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookManagementApp.class)
public class AuthorResourceIntTest {

    private static final String DEFAULT_TEN_TACGIA = "AAAAAAAAAA";
    private static final String UPDATED_TEN_TACGIA = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCE = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NGAY_SINH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NGAY_SINH = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private AuthorRepository authorRepository;

    @Inject
    private AuthorService authorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAuthorMockMvc;

    private Author author;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuthorResource authorResource = new AuthorResource();
        ReflectionTestUtils.setField(authorResource, "authorService", authorService);
        this.restAuthorMockMvc = MockMvcBuilders.standaloneSetup(authorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Author createEntity() {
        Author author = new Author()
                .tenTacgia(DEFAULT_TEN_TACGIA)
                .introduce(DEFAULT_INTRODUCE)
                .ngaySinh(DEFAULT_NGAY_SINH);
        return author;
    }

    @Before
    public void initTest() {
        authorRepository.deleteAll();
        author = createEntity();
    }

    @Test
    public void createAuthor() throws Exception {
        int databaseSizeBeforeCreate = authorRepository.findAll().size();

        // Create the Author

        restAuthorMockMvc.perform(post("/api/authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isCreated());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeCreate + 1);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getTenTacgia()).isEqualTo(DEFAULT_TEN_TACGIA);
        assertThat(testAuthor.getIntroduce()).isEqualTo(DEFAULT_INTRODUCE);
        assertThat(testAuthor.getNgaySinh()).isEqualTo(DEFAULT_NGAY_SINH);
    }

    @Test
    public void createAuthorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = authorRepository.findAll().size();

        // Create the Author with an existing ID
        Author existingAuthor = new Author();
        existingAuthor.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorMockMvc.perform(post("/api/authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAuthor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTenTacgiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = authorRepository.findAll().size();
        // set the field null
        author.setTenTacgia(null);

        // Create the Author, which fails.

        restAuthorMockMvc.perform(post("/api/authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isBadRequest());

        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAuthors() throws Exception {
        // Initialize the database
        authorRepository.save(author);

        // Get all the authorList
        restAuthorMockMvc.perform(get("/api/authors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(author.getId())))
            .andExpect(jsonPath("$.[*].tenTacgia").value(hasItem(DEFAULT_TEN_TACGIA.toString())))
            .andExpect(jsonPath("$.[*].introduce").value(hasItem(DEFAULT_INTRODUCE.toString())))
            .andExpect(jsonPath("$.[*].ngaySinh").value(hasItem(DEFAULT_NGAY_SINH.toString())));
    }

    @Test
    public void getAuthor() throws Exception {
        // Initialize the database
        authorRepository.save(author);

        // Get the author
        restAuthorMockMvc.perform(get("/api/authors/{id}", author.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(author.getId()))
            .andExpect(jsonPath("$.tenTacgia").value(DEFAULT_TEN_TACGIA.toString()))
            .andExpect(jsonPath("$.introduce").value(DEFAULT_INTRODUCE.toString()))
            .andExpect(jsonPath("$.ngaySinh").value(DEFAULT_NGAY_SINH.toString()));
    }

    @Test
    public void getNonExistingAuthor() throws Exception {
        // Get the author
        restAuthorMockMvc.perform(get("/api/authors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuthor() throws Exception {
        // Initialize the database
        authorService.save(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author
        Author updatedAuthor = authorRepository.findOne(author.getId());
        updatedAuthor
                .tenTacgia(UPDATED_TEN_TACGIA)
                .introduce(UPDATED_INTRODUCE)
                .ngaySinh(UPDATED_NGAY_SINH);

        restAuthorMockMvc.perform(put("/api/authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuthor)))
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getTenTacgia()).isEqualTo(UPDATED_TEN_TACGIA);
        assertThat(testAuthor.getIntroduce()).isEqualTo(UPDATED_INTRODUCE);
        assertThat(testAuthor.getNgaySinh()).isEqualTo(UPDATED_NGAY_SINH);
    }

    @Test
    public void updateNonExistingAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Create the Author

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuthorMockMvc.perform(put("/api/authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isCreated());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAuthor() throws Exception {
        // Initialize the database
        authorService.save(author);

        int databaseSizeBeforeDelete = authorRepository.findAll().size();

        // Get the author
        restAuthorMockMvc.perform(delete("/api/authors/{id}", author.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
