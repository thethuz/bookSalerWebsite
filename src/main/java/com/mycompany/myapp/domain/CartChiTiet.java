package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CartChiTiet.
 */

@Document(collection = "cart_chi_tiet")
public class CartChiTiet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("book_id")
    private String bookId;

    @Field("number_of_book")
    private Integer numberOfBook;

    @Field("thanhtien")
    private String thanhtien;

    @Field("cart_id")
    private String cartId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public CartChiTiet bookId(String bookId) {
        this.bookId = bookId;
        return this;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Integer getNumberOfBook() {
        return numberOfBook;
    }

    public CartChiTiet numberOfBook(Integer numberOfBook) {
        this.numberOfBook = numberOfBook;
        return this;
    }

    public void setNumberOfBook(Integer numberOfBook) {
        this.numberOfBook = numberOfBook;
    }

    public String getThanhtien() {
        return thanhtien;
    }

    public CartChiTiet thanhtien(String thanhtien) {
        this.thanhtien = thanhtien;
        return this;
    }

    public void setThanhtien(String thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getCartId() {
        return cartId;
    }

    public CartChiTiet cartId(String cartId) {
        this.cartId = cartId;
        return this;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartChiTiet cartChiTiet = (CartChiTiet) o;
        if (cartChiTiet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cartChiTiet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CartChiTiet{" +
            "id=" + id +
            ", bookId='" + bookId + "'" +
            ", numberOfBook='" + numberOfBook + "'" +
            ", thanhtien='" + thanhtien + "'" +
            ", cartId='" + cartId + "'" +
            '}';
    }
}
