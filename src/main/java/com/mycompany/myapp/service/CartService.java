package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.domain.CartChiTiet;
import com.mycompany.myapp.repository.CartChiTietRepository;
import com.mycompany.myapp.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Cart.
 */
@Service
public class CartService {

    private final Logger log = LoggerFactory.getLogger(CartService.class);

    @Inject
    private CartRepository cartRepository;
    @Inject
    private UserService userService;
    @Inject
    private CartChiTietRepository cartChiTietRepository;
    /**
     * Save a cart.
     */
    public Cart save(Cart cart) {
        log.debug("Request to save Cart : {}", cart);
        Cart result = cartRepository.save(cart);
        return result;
    }
    public Cart findByCurrentUser(){
      log.debug("Request to find Cart by User");
      String id=getIdCurrentUserLogin();
      Cart cart=cartRepository.findByUserIdAndStatusTrue(id);
      return cart;
    }

    public String getIdCurrentUserLogin(){
      String id=userService.getUserWithAuthorities().getId();
      System.out.println("=============="+id);
      return id;
    }
    /**
     *  Get all the carts.
     */
    public Page<Cart> findAll(Pageable pageable) {
        log.debug("Request to get all Carts");
        Page<Cart> result = cartRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one cart by id.
     */
    public Cart findOne(String id) {
        log.debug("Request to get Cart : {}", id);
        Cart cart = cartRepository.findOne(id);
        return cart;
    }

    /**
     *  Delete the  cart by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Cart : {}", id);
        cartRepository.delete(id);
    }
}
