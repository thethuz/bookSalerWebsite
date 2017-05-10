package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.CartChiTiet;
import com.mycompany.myapp.service.CartChiTietService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.CartService;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CartChiTiet.
 */
@RestController
@RequestMapping("/api")
public class CartChiTietResource {

    private final Logger log = LoggerFactory.getLogger(CartChiTietResource.class);

    @Inject
    private CartChiTietService cartChiTietService;

    /**
     * POST  /cart-chi-tiets : Create a new cartChiTiet.
     */
    @PostMapping("/cart-chi-tiets")
    @Timed
    public ResponseEntity<CartChiTiet> createCartChiTiet(@RequestBody CartChiTiet cartChiTiet) throws URISyntaxException {
        log.debug("REST request to save CartChiTiet : {}", cartChiTiet);
        if (cartChiTiet.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cartChiTiet", "idexists", "A new cartChiTiet cannot already have an ID")).body(null);
        }
        CartChiTiet result = cartChiTietService.save(cartChiTiet);
        return ResponseEntity.created(new URI("/api/cart-chi-tiets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cartChiTiet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cart-chi-tiets : Updates an existing cartChiTiet.
     */
    @PutMapping("/cart-chi-tiets")
    @Timed
    public ResponseEntity<CartChiTiet> updateCartChiTiet(@RequestBody CartChiTiet cartChiTiet) throws URISyntaxException {
        log.debug("REST request to update CartChiTiet : {}", cartChiTiet);
        if (cartChiTiet.getId() == null) {
            return createCartChiTiet(cartChiTiet);
        }
        CartChiTiet result = cartChiTietService.save(cartChiTiet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cartChiTiet", cartChiTiet.getId().toString()))
            .body(result);
    }

    @GetMapping("cart-chi-tiets/getbyuser")
    @Timed
    public ResponseEntity<List<CartChiTiet>> getAllCartChiTietByUser(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get cart chi tiet by current user");
        List<CartChiTiet> result = cartChiTietService.findAllByUser();

        HttpHeaders headers = new HttpHeaders();
        URI location=new URI("api/cart-chi-tiets/getbyuser");
    		headers.setLocation(location);
        System.out.println("Done");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }
    /**
     * GET  /cart-chi-tiets : get all the cartChiTiets.
     */
    @GetMapping("/cart-chi-tiets")
    @Timed
    public ResponseEntity<List<CartChiTiet>> getAllCartChiTiets(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CartChiTiets");
        Page<CartChiTiet> page = cartChiTietService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cart-chi-tiets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cart-chi-tiets/:id : get the "id" cartChiTiet.
     */
    @GetMapping("/cart-chi-tiets/{id}")
    @Timed
    public ResponseEntity<CartChiTiet> getCartChiTiet(@PathVariable String id) {
        log.debug("REST request to get CartChiTiet : {}", id);
        CartChiTiet cartChiTiet = cartChiTietService.findOne(id);
        return Optional.ofNullable(cartChiTiet)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cart-chi-tiets/:id : delete the "id" cartChiTiet.
     */
    @DeleteMapping("/cart-chi-tiets/{id}")
    @Timed
    public ResponseEntity<Void> deleteCartChiTiet(@PathVariable String id) {
        log.debug("REST request to delete CartChiTiet : {}", id);
        cartChiTietService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cartChiTiet", id.toString())).build();
    }
    //

}
