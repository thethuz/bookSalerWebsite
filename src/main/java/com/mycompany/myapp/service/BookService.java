package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import com.mycompany.myapp.domain.CartChiTiet;
import com.mycompany.myapp.domain.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mycompany.myapp.repository.PersistentTokenRepository;
import com.mycompany.myapp.repository.CartRepository;
import com.mycompany.myapp.repository.CartChiTietRepository;
import com.mycompany.myapp.service.CartChiTietService;


import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Book.
 */
@Service
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);
    private PersistentTokenRepository persistentTokenRepository;
    @Inject
    private BookRepository bookRepository;
    private CartRepository cartRepository;
    private CartChiTietRepository cartChiTietRepository;
    private CartChiTietService cartChiTietService;
    /**
     * Save a book.
     *
     * @param book the entity to save
     * @return the persisted entity
     */
    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
        Book result = bookRepository.save(book);
        return result;
    }

    public Book findOneByTacGia(String tacGia){
      log.debug("Request to get Book by tacGiaID: {}",tacGia);
      Book result=bookRepository.findOneByTacGia(tacGia);
      return result;
    }

    public List<Book> findAllByTacGia(String tacGia){
      log.debug("Request to get Book by tacGiaID: {}",tacGia);
      List<Book> result=bookRepository.findAllByTacGia(tacGia);
      return result;
    }
    public int addBookToCart(Book book, String userId){
      log.debug("request to add Book to Cart");
      if(!book.isTrangThaiConHang()){
        return 1; //Het hang
      }
      // persistentTokenRepository.findByUser();
      Cart cart=cartRepository.findByUserIdAndStatusTrue(userId);
        if(cart!=null){
          CartChiTiet cartChiTiet=cartChiTietRepository.findByCartIdAndBookId(cart.getId(),book.getId());
          if(cartChiTiet!=null){
            cartChiTiet.setNumberOfBook(cartChiTiet.getNumberOfBook()+1);
            cartChiTiet.setThanhtien(book.getGiaMoi()*cartChiTiet.getNumberOfBook());
            cartChiTietService.save(cartChiTiet);
          }else{
            cartChiTiet.setBookId(book.getId());
            cartChiTiet.setCartId(cart.getId());
            cartChiTiet.setNumberOfBook(1);
            cartChiTiet.setThanhtien(book.getGiaMoi()*cartChiTiet.getNumberOfBook());
            cartChiTietService.save(cartChiTiet);
          }
        }else if(cart==null){
          Cart cart1=new Cart();
          cart1.setUserId(userId);
          cart1.setStatus(true);
          cartRepository.save(cart1);
          CartChiTiet cartct1 = new CartChiTiet();
          cartct1.setCartId(cart1.getId());
          cartct1.setBookId(book.getId());
          cartct1.setNumberOfBook(1);
        }
      return 0;
    }
    public int subBookFromCart(Book book,String userId){
      log.debug("request to remove Book from Cart");
      if(!book.isTrangThaiConHang()){
        return 1;
      }
      Cart cart=checkCartExist(userId);
      if(cart==null){
        //create Cart
        //create CartChiTiet
        //return False
        return 1;
      }
      else if(cart!=null){
        CartChiTiet cartct= checkCartChiTietExist(cart.getId(),book.getId());
        if(cartct==null) return 2;
        if(cartct.getNumberOfBook()<=1){
          cartRepository.delete(cartct.getId());
        }
        else cartct.setNumberOfBook(cartct.getNumberOfBook()-1);
        cartChiTietService.save(cartct);
        //
      }
      return 0;
    }

    public Cart checkCartExist(String userId){
      // private CartRepository cartRepository;
      Cart result =cartRepository.findByUserIdAndStatusTrue(userId);
      return result;
    }
    public CartChiTiet checkCartChiTietExist(String cartId,String bookId){
      // private CartChiTietRepository cartctRepository;
      CartChiTiet result =cartChiTietRepository.findByCartIdAndBookId(cartId,bookId);
      return result;
    }

    public List<Book> findAllByTag(String tag){
      log.debug("Request to get Book by tacGiaID: {}",tag);
      List<Book> result=bookRepository.findAllByTag(tag);
      return result;
    }
    /**
     *  Get all the books.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Book> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        Page<Book> result = bookRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one book by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Book findOne(String id) {
        log.debug("Request to get Book : {}", id);
        Book book = bookRepository.findOne(id);
        return book;
    }

    /**
     *  Delete the  book by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.delete(id);
    }
}
