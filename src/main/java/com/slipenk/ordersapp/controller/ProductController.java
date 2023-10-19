package com.slipenk.ordersapp.controller;

import com.slipenk.ordersapp.entity.Product;
import com.slipenk.ordersapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.slipenk.ordersapp.dictionary.Dictionary.ORDER_APP_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.PRODUCTS_PATH;

@RestController
@RequestMapping(ORDER_APP_PATH)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(PRODUCTS_PATH)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping(PRODUCTS_PATH)
    public List<Product> addProducts(@RequestBody List<Product> productList) {
        return productService.addProducts(productList);
    }
}
