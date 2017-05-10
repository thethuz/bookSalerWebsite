package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CartChiTiet;
import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.service.CartService;
import com.mycompany.myapp.service.BookService;
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
    @Inject
    private CartService cartService;
    @Inject
    private BookService bookService;
    /**
     * Save a cartChiTiet.
     *
     * @param cartChiTiet the entity to save
     * @return the persisted entity
     */
    public CartChiTiet save(CartChiTiet cartChiTiet) {
        log.debug("Request to save CartChiTiet : {}", cartChiTiet);
        Book book = bookService.findOne(cartChiTiet.getBookId());
        cartChiTiet.setThanhtien(book.getGiaMoi()*cartChiTiet.getNumberOfBook());
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

    public List<CartChiTiet> findAllByUser(){
      log.debug("Request to get all CartCT by User");
      Cart cart = cartService.findByCurrentUser();
      if(cart==null) {
        return null;
      }else{
        return cartChiTietRepository.findAllByCartId(cart.getId());
      }
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
