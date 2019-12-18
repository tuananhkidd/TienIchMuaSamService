package com.kidd.shopping.product.entity;

import com.google.cloud.firestore.annotation.ServerTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "product_category")
@Entity
public class ProductCategory {
    public static final String CREATE_DATE = "createdDate";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String category;
    @Column(name = "createdDate")
    private Date createdDate;

    @PrePersist
    public void onSaveDate(){
        this.createdDate = new Date();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
