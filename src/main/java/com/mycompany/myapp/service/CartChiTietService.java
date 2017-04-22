package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CartChiTiet;
import com.mycompany.myapp.repository.CartChiTietRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing CartChiTiet.
 */
@Service
public class CartChiTietService {

    private final Logger log = LoggerFactory.getLogger(CartChiTietService.class);
    
    @Inject
    private CartChiTietRepository cartChiTietRepository;

    /**
     * Save a cartChiTiet.
     *
     * @param cartChiTiet the entity to save
     * @return the persisted entity
     */
    public CartChiTiet save(CartChiTiet cartChiTiet) {
        log.debug("Request to save CartChiTiet : {}", cartChiTiet);
        CartChiTiet result = cartChiTietRepository.save(cartChiTiet);
        return result;
    }

    /**
     *  Get all the cartChiTiets.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<CartChiTiet> findAll(Pageable pageable) {
        log.debug("Request to get all CartChiTiets");
        Page<CartChiTiet> result = cartChiTietRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one cartChiTiet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public CartChiTiet findOne(String id) {
        log.debug("Request to get CartChiTiet : {}", id);
        CartChiTiet cartChiTiet = cartChiTietRepository.findOne(id);
        return cartChiTiet;
    }

    /**
     *  Delete the  cartChiTiet by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete CartChiTiet : {}", id);
        cartChiTietRepository.delete(id);
    }
}
