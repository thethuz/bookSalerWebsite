package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Book.
 */
@Service
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    @Inject
    private BookRepository bookRepository;

    /**
     * Save a book.
     *
     * @param book the entity to save
     * @return the persisted entity
     */
    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
        Book result = bookRepository.save(book);
        return result;
    }

    public Book findOneByTacGia(String tacGia){
      log.debug("Request to get Book by tacGiaID: {}",tacGia);
      Book result=bookRepository.findOneByTacGia(tacGia);
      return result;
    }

    public Page<Book> findAllByTacGia(String tacGia){
      log.debug("Request to get Book by tacGiaID: {}",tacGia);
      Page<Book> result=bookRepository.findAllByTacGia(tacGia);
      return result;
    }

    public Page<Book> findAllByTag(String tag){
      log.debug("Request to get Book by tacGiaID: {}",tag);
      Page<Book> result=bookRepository.findAllByTag(tag);
      return result;
    }
    /**
     *  Get all the books.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Book> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        Page<Book> result = bookRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one book by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Book findOne(String id) {
        log.debug("Request to get Book : {}", id);
        Book book = bookRepository.findOne(id);
        return book;
    }

    /**
     *  Delete the  book by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.delete(id);
    }
}
