package com.example.santhuongmai.dto.sdo;

import com.example.santhuongmai.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductFilterSdo {
    private Long id;
    private String description;
    private String name;
    private Long price;
    private Integer quantity;
    private Long categoryId;
    private String mota;
    private Integer quantityBuy;
    private Long userId;
    private Long priceSale;
    private Date createdAt;
    private List<Image> images;
    private List<Productcolor> productcolors;
    private List<Productsize> productsizes;
    private List<Productroom> productrooms;
    private Category category;
    private User user;
}
