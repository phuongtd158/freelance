package com.example.santhuongmai.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;


    @Column(name = "description",columnDefinition = "TEXT")
    private String description;
    @Column(name = "mota",columnDefinition = "TEXT")
    private String mota;

    private long price;
    private long pricesale;

    private int quantity;
    private int quantitybuy;
    private Timestamp createAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;
    
    @ManyToMany
    @JoinTable(name = "product_image",joinColumns = @JoinColumn(name="product_id"),inverseJoinColumns = @JoinColumn(name="image_id"))
    private Set<Image> images = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="product_productcolor",joinColumns = @JoinColumn(name="product_id"),inverseJoinColumns = @JoinColumn(name="productcolor_id"))
    private Set<Productcolor> productcolors = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="product_productsize",joinColumns = @JoinColumn(name="product_id"),inverseJoinColumns = @JoinColumn(name="productsize_id"))
    private Set<Productsize> productsizes = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="product_productroom",joinColumns = @JoinColumn(name="product_id"),inverseJoinColumns = @JoinColumn(name="productroom_id"))
    private Set<Productroom> productrooms = new HashSet<>();

}
