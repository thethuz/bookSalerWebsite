package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.service.CartService;

import com.mycompany.myapp.repository.CartRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cart.
 */
@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    @Inject
    private CartRepository cartRepository;
    @Inject
    private CartService cartService;
    /**
     * POST  /carts : Create a new cart.
     */
    @PostMapping("/carts")
    @Timed
    public ResponseEntity<Cart> createCart(@Valid @RequestBody Cart cart) throws URISyntaxException {
        log.debug("REST request to save Cart : {}", cart);
        if (cart.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cart", "idexists", "A new cart cannot already have an ID")).body(null);
        }
        Cart result = cartRepository.save(cart);
        return ResponseEntity.created(new URI("/api/carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cart", result.getId().toString()))
            .body(result);
    }

    @GetMapping("/carts/byUser")
    @Timed
    public ResponseEntity<Cart> getCart() {
        log.debug("REST request to get Cart by User: {}");
        // Cart cart = cartRepository.findOne(id);
        Cart cart=cartService.findByCurrentUser();
        return Optional.ofNullable(cart)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * PUT  /carts : Updates an existing cart.
     */
    @PutMapping("/carts")
    @Timed
    public ResponseEntity<Cart> updateCart(@Valid @RequestBody Cart cart) throws URISyntaxException {
        log.debug("REST request to update Cart : {}", cart);
        if (cart.getId() == null) {
            return createCart(cart);
        }
        Cart result = cartRepository.save(cart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cart", cart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carts : get all the carts.
     */
    @GetMapping("/carts")
    @Timed
    public ResponseEntity<List<Cart>> getAllCarts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Carts");
        Page<Cart> page = cartRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /carts/:id : get the "id" cart.
     */
    @GetMapping("/carts/{id}")
    @Timed
    public ResponseEntity<Cart> getCart(@PathVariable String id) {
        log.debug("REST request to get Cart : {}", id);
        Cart cart = cartRepository.findOne(id);
        return Optional.ofNullable(cart)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /carts/:id : delete the "id" cart.
     *
     * @param id the id of the cart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCart(@PathVariable String id) {
        log.debug("REST request to delete Cart : {}", id);
        cartRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cart", id.toString())).build();
    }

}
