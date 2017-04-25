package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Book.
 */

@Document(collection = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    @Field("ten_sach")
    private String tenSach;

    @NotNull
    @Field("trang_thai_con_hang")
    private Boolean trangThaiConHang;

    @NotNull
    @Field("tac_gia")
    private String tacGia;

    @Size(max = 2000)
    @Field("tom_tat")
    private String tomTat;

    @Field("gia_cu")
    private Integer giaCu;

    @Field("gia_moi")
    private Integer giaMoi;

    @Min(value = 0)
    @Max(value = 5)
    @Field("rating")
    private Integer rating;

    @Size(max = 400)
    @Field("anh_dai_dien")
    private String anhDaiDien;

    @Size(max = 50)
    @Field("tag")
    private String tag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public Book tenSach(String tenSach) {
        this.tenSach = tenSach;
        return this;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public Boolean isTrangThaiConHang() {
        return trangThaiConHang;
    }

    public Book trangThaiConHang(Boolean trangThaiConHang) {
        this.trangThaiConHang = trangThaiConHang;
        return this;
    }

    public void setTrangThaiConHang(Boolean trangThaiConHang) {
        this.trangThaiConHang = trangThaiConHang;
    }

    public String getTacGia() {
        return tacGia;
    }

    public Book tacGia(String tacGia) {
        this.tacGia = tacGia;
        return this;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getTomTat() {
        return tomTat;
    }

    public Book tomTat(String tomTat) {
        this.tomTat = tomTat;
        return this;
    }

    public void setTomTat(String tomTat) {
        this.tomTat = tomTat;
    }

    public Integer getGiaCu() {
        return giaCu;
    }

    public Book giaCu(Integer giaCu) {
        this.giaCu = giaCu;
        return this;
    }

    public void setGiaCu(Integer giaCu) {
        this.giaCu = giaCu;
    }

    public Integer getGiaMoi() {
        return giaMoi;
    }

    public Book giaMoi(Integer giaMoi) {
        this.giaMoi = giaMoi;
        return this;
    }

    public void setGiaMoi(Integer giaMoi) {
        this.giaMoi = giaMoi;
    }

    public Integer getRating() {
        return rating;
    }

    public Book rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public Book anhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
        return this;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getTag() {
        return tag;
    }

    public Book tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        if (book.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + id +
            ", tenSach='" + tenSach + "'" +
            ", trangThaiConHang='" + trangThaiConHang + "'" +
            ", tacGia='" + tacGia + "'" +
            ", tomTat='" + tomTat + "'" +
            ", giaCu='" + giaCu + "'" +
            ", giaMoi='" + giaMoi + "'" +
            ", rating='" + rating + "'" +
            ", anhDaiDien='" + anhDaiDien + "'" +
            ", tag='" + tag + "'" +
            '}';
    }
}
