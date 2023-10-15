package com.slipenk.ordersapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrdersAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersAppApplication.class, args);
    }

}
