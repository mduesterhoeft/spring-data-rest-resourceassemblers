package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductInitializer {

    @Autowired
    public ProductInitializer(ProductRepository productRepository) {
        productRepository.save(new Product("some"));
        productRepository.save(new Product("some other"));
    }
}
