package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Paying;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Spring Data MongoDB repository for the Paying entity.
 */
@SuppressWarnings("unused")
public interface PayingRepository extends MongoRepository<Paying,String> {
  public List<Paying> findAllByUser(String user_id);
}
