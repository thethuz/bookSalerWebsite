package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Paying;

import com.mycompany.myapp.repository.PayingRepository;
import com.mycompany.myapp.service.PayingService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Paying.
 */
@RestController
@RequestMapping("/api")
public class PayingResource {

    private final Logger log = LoggerFactory.getLogger(PayingResource.class);

    @Inject
    private PayingRepository payingRepository;
    private PayingService payingService;
    /**
     * POST  /payings : Create a new paying.
     *
     * @param paying the paying to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paying, or with status 400 (Bad Request) if the paying has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payings")
    @Timed
    public ResponseEntity<Paying> createPaying(@Valid @RequestBody Paying paying) throws URISyntaxException {
        log.debug("REST request to save Paying : {}", paying);
        if (paying.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("paying", "idexists", "A new paying cannot already have an ID")).body(null);
        }
        Paying result = payingService.save(paying);
        return ResponseEntity.created(new URI("/api/payings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("paying", result.getId().toString()))
            .body(result);
    }
    @GetMapping("/prefixPaying")
    @Timed
    public ResponseEntity<Paying> getPrefixPaying() throws URISyntaxException{
      log.debug("REST request to get Prefix-Paying");
      Paying result = payingService.getPrefix();
      return ResponseEntity.created(new URI("/api/payings/" + result.getId()))
          .headers(HeaderUtil.createEntityCreationAlert("paying", result.getId().toString()))
          .body(result);
    }

    /**
     * PUT  /payings : Updates an existing paying.
     *
     * @param paying the paying to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paying,
     * or with status 400 (Bad Request) if the paying is not valid,
     * or with status 500 (Internal Server Error) if the paying couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
     /*
    @PutMapping("/payings")
    @Timed
    public ResponseEntity<Paying> updatePaying(@Valid @RequestBody Paying paying) throws URISyntaxException {
        log.debug("REST request to update Paying : {}", paying);
        if (paying.getId() == null) {
            return createPaying(paying);
        }
        Paying result = payingRepository.save(paying);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("paying", paying.getId().toString()))
            .body(result);
    }
    */

    /**
     * GET  /payings : get all the payings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payings in body
     */
    @GetMapping("/payings")
    @Timed
    public List<Paying> getAllPayings() {
        log.debug("REST request to get all Payings");
        List<Paying> payings = payingRepository.findAll();
        return payings;
    }

    // @GetMapping("/payings/done")
    // @Timed
    // public ResponseEntity<Boolean> getPaid(@)

    /**
     * GET  /payings/:id : get the "id" paying.
     *
     * @param id the id of the paying to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paying, or with status 404 (Not Found)
     */
    @GetMapping("/payings/{id}")
    @Timed
    public ResponseEntity<Paying> getPaying(@PathVariable String id) {
        log.debug("REST request to get Paying : {}", id);
        Paying paying = payingRepository.findOne(id);
        return Optional.ofNullable(paying)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payings/:id : delete the "id" paying.
     *
     * @param id the id of the paying to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payings/{id}")
    @Timed
    public ResponseEntity<Void> deletePaying(@PathVariable String id) {
        log.debug("REST request to delete Paying : {}", id);
        payingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("paying", id.toString())).build();
    }

}
