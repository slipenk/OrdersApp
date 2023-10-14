package com.slipenk.ordersapp.controller;

import com.slipenk.ordersapp.entity.Order;
import com.slipenk.ordersapp.entity.Product;
import com.slipenk.ordersapp.repository.OrderRepository;
import com.slipenk.ordersapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.slipenk.ordersapp.dictionary.Dictionary.ADD_PRODUCTS_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.ORDER_APP_PATH;

@RestController
@RequestMapping(ORDER_APP_PATH)
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(ADD_PRODUCTS_PATH)
    public List<Order> addOrders(@RequestBody List<Order> order) {
        return orderService.addOrders(order);
    }

}
