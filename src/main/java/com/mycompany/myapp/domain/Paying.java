package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Paying.
 */

@Document(collection = "paying")
public class Paying implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("price")
    private long price;

    @Field("price_with_vat")
    private long priceWithVAT;

    @Size(max = 100)
    @Field("phuong_thuc_thanh_toan")
    private String phuongThucThanhToan;

    @Size(max = 100)
    @Field("hoten")
    private String hoten;

    @Size(max = 100)
    @Field("email")
    private String email;

    @Size(max = 150)
    @Field("dia_chi")
    private String diaChi;

    @Size(max = 3000)
    @Field("chi_tiet_giao_dich")
    private String chiTietGiaoDich;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public Paying price(long price) {
        this.price = price;
        return this;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getPriceWithVAT() {
        return priceWithVAT;
    }

    public Paying priceWithVAT(long priceWithVAT) {
        this.priceWithVAT = priceWithVAT;
        return this;
    }

    public void setPriceWithVAT(long priceWithVAT) {
        this.priceWithVAT = priceWithVAT;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public Paying phuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
        return this;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getHoten() {
        return hoten;
    }

    public Paying hoten(String hoten) {
        this.hoten = hoten;
        return this;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public Paying email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public Paying diaChi(String diaChi) {
        this.diaChi = diaChi;
        return this;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getChiTietGiaoDich() {
        return chiTietGiaoDich;
    }

    public Paying chiTietGiaoDich(String chiTietGiaoDich) {
        this.chiTietGiaoDich = chiTietGiaoDich;
        return this;
    }

    public void setChiTietGiaoDich(String chiTietGiaoDich) {
        this.chiTietGiaoDich = chiTietGiaoDich;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Paying paying = (Paying) o;
        if (paying.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, paying.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Paying{" +
            "id=" + id +
            ", price='" + price + "'" +
            ", priceWithVAT='" + priceWithVAT + "'" +
            ", phuongThucThanhToan='" + phuongThucThanhToan + "'" +
            ", hoten='" + hoten + "'" +
            ", email='" + email + "'" +
            ", diaChi='" + diaChi + "'" +
            ", chiTietGiaoDich='" + chiTietGiaoDich + "'" +
            '}';
    }
}
