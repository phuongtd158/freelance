package com.example.santhuongmai.repository.custom;

import com.example.santhuongmai.dto.sdi.FilterProductSdi;
import com.example.santhuongmai.dto.sdo.ProductFilterSdo;
import com.example.santhuongmai.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<ProductFilterSdo> filter(FilterProductSdi request);

}
