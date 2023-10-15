package com.slipenk.ordersapp.service;

import com.slipenk.ordersapp.entity.Product;
import com.slipenk.ordersapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> addProducts(List<Product> productList) {
        return new ArrayList<>(productRepository.saveAll(productList));
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
