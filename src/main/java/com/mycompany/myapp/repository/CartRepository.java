package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cart;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Cart entity.
 */
@SuppressWarnings("unused")
public interface CartRepository extends MongoRepository<Cart,String> {

}
