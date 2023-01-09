package com.example.ProductManagement.repository;

import com.example.ProductManagement.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product>  findAll();
    Product save(Product product);
    Product findById(int id);
}
