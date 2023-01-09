package com.example.ProductManagement.web.controller;


import com.example.ProductManagement.model.Product;
import com.example.ProductManagement.repository.ProductDao;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductController {

    private ProductDao productDao;

    public ProductController( ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/products")
    public MappingJacksonValue listProducts(){

        List<Product> products = productDao.findAll();

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("productFilter", simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(products);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct( @RequestBody Product product){

        Product savedProduct = productDao.save(product);

        if(Objects.isNull(savedProduct)){
            return ResponseEntity.noContent().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/products/{id}")
    public MappingJacksonValue showProduct(@PathVariable  int id){


        Product product = productDao.findById(id);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(product);
        mappingJacksonValue.setFilters(
                new SimpleFilterProvider().addFilter("productFilter",
                        SimpleBeanPropertyFilter.serializeAllExcept("prixAchat")
                        )
        );

        return mappingJacksonValue;
    }
}
