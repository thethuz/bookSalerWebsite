package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Author.
 */

@Document(collection = "author")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    @Field("ten_tacgia")
    private String tenTacgia;

    @Size(max = 2000)
    @Field("introduce")
    private String introduce;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenTacgia() {
        return tenTacgia;
    }

    public Author tenTacgia(String tenTacgia) {
        this.tenTacgia = tenTacgia;
        return this;
    }

    public void setTenTacgia(String tenTacgia) {
        this.tenTacgia = tenTacgia;
    }

    public String getIntroduce() {
        return introduce;
    }

    public Author introduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        if (author.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Author{" +
            "id=" + id +
            ", tenTacgia='" + tenTacgia + "'" +
            ", introduce='" + introduce + "'" +
            '}';
    }
}
