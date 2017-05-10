package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CartChiTiet;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the CartChiTiet entity.
 */
@SuppressWarnings("unused")
public interface CartChiTietRepository extends MongoRepository<CartChiTiet,String> {
  public List<CartChiTiet> findAllByCartId(String cartId);
  public CartChiTiet findByCartIdAndBookId(String cartId, String BookId);
  // public List<CartChiTiet> findAllByCartId
}
