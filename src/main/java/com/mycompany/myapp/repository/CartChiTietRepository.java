package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CartChiTiet;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the CartChiTiet entity.
 */
@SuppressWarnings("unused")
public interface CartChiTietRepository extends MongoRepository<CartChiTiet,String> {

}
