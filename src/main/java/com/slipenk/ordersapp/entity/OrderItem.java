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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static com.slipenk.ordersapp.dictionary.Dictionary.ID;
import static com.slipenk.ordersapp.dictionary.Dictionary.ORDER_ID;
import static com.slipenk.ordersapp.dictionary.Dictionary.ORDER_ITEMS;
import static com.slipenk.ordersapp.dictionary.Dictionary.PRODUCT_ID;
import static com.slipenk.ordersapp.dictionary.Dictionary.QUANTITY;

@Entity
@Table(name = ORDER_ITEMS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false, unique = true)
    private int id;

    @Column(name = QUANTITY, nullable = false)
    private int quantity;

    @OneToOne
    @JoinColumn(name = PRODUCT_ID)
    private Product orderedProduct;

    @ManyToOne
    @JoinColumn(name = ORDER_ID)
    private Order order;

}
