package com.example.ProductManagement.repository;

import com.example.ProductManagement.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao{
    private static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(1, "Tekno", 65000, 28000));
        products.add(new Product(2, "Infinix", 75000, 32000));
        products.add(new Product(3, "Oppo", 115000, 70000));
        products.add(new Product(4, "Samsung", 170000, 105000));

    }


    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public Product findById(int id) {
        for(Product product : products){
            if(product.getId() == id){
                return product;
            }
        }

        return null;
    }
}
