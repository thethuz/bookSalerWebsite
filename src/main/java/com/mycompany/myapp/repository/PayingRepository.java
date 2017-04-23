package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Paying;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Paying entity.
 */
@SuppressWarnings("unused")
public interface PayingRepository extends MongoRepository<Paying,String> {

}
