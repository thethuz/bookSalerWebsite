package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Book;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data MongoDB repository for the Book entity.
 */
@SuppressWarnings("unused")
public interface BookRepository extends MongoRepository<Book,String> {
  Book findOneByTacGia(String tacGia);

  List<Book> findAllByTacGia(String tacGia);

  List<Book> findAllByTag(String tag);
}
