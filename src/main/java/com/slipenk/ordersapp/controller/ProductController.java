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

import static com.slipenk.ordersapp.dictionary.Dictionary.ADD_ORDER_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.ADD_PRODUCTS_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.GET_PRODUCTS_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.ORDER_APP_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.PRODUCTS_WITH_SIZE;
import static com.slipenk.ordersapp.dictionary.Dictionary.WERE_ADDED_SUCCESSFULLY;

@RestController
@RequestMapping(ORDER_APP_PATH)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(GET_PRODUCTS_PATH)
    public List<Product> getProducts() {
       return productService.getProducts();
    }

    @PostMapping(ADD_ORDER_PATH)
    public String addProducts(@RequestBody List<Product> productList) {
        productService.addProducts(productList);
        return PRODUCTS_WITH_SIZE + productList.size() + WERE_ADDED_SUCCESSFULLY;
    }
}
