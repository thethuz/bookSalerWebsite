package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Cart.
 */

@Document(collection = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("total_price")
    private String totalPrice;

    @Field("order_date")
    private ZonedDateTime orderDate;

    @Field("status")
    private Boolean status;

    @Size(max = 50)
    @Field("token")
    private String token;

    @Size(max = 50)
    @Field("user_id")
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public Cart totalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public Cart orderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean isStatus() {
        return status;
    }

    public Cart status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public Cart token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public Cart userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        if (cart.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cart{" +
            "id=" + id +
            ", totalPrice='" + totalPrice + "'" +
            ", orderDate='" + orderDate + "'" +
            ", status='" + status + "'" +
            ", token='" + token + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
