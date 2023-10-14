package com.slipenk.ordersapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.slipenk.ordersapp.dictionary.Dictionary.ID;
import static com.slipenk.ordersapp.dictionary.Dictionary.NAME;
import static com.slipenk.ordersapp.dictionary.Dictionary.PRICE;
import static com.slipenk.ordersapp.dictionary.Dictionary.PRODUCTS;
import static com.slipenk.ordersapp.dictionary.Dictionary.TOTAL_QUANTITY;

@Entity
@Table(name = PRODUCTS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false, unique = true)
    private int id;

    @Column(name = NAME, nullable = false, unique = true)
    private String name;

    @Column(name = PRICE, nullable = false)
    private double price;

    @Column(name = TOTAL_QUANTITY, nullable = false)
    private int totalQuantity;

}
