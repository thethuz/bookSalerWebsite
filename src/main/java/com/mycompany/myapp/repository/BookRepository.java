package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Book;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Spring Data MongoDB repository for the Book entity.
 */
@SuppressWarnings("unused")
public interface BookRepository extends MongoRepository<Book,String> {
  Book findOneByTacGia(String tacGia);

  Page<Book> findAllByTacGia(String tacGia);

  Page<Book> findAllByTag(String tag);
}
