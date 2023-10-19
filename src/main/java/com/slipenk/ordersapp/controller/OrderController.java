package com.slipenk.ordersapp.controller;

import com.slipenk.ordersapp.entity.Order;
import com.slipenk.ordersapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.slipenk.ordersapp.dictionary.Dictionary.ORDERS_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.ORDER_APP_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.PAY_ORDER_PATH;

@RestController
@RequestMapping(ORDER_APP_PATH)
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(ORDERS_PATH)
    public List<Order> addOrders(@RequestBody List<Order> orders) {
        return orderService.addOrders(orders);
    }

    @PostMapping(PAY_ORDER_PATH)
    public List<Order> payOrders(@RequestBody List<Order> orders) {
        return orderService.payOrders(orders);
    }

}
