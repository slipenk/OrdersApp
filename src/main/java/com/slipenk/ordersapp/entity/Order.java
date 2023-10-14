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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static com.slipenk.ordersapp.dictionary.Dictionary.ID;
import static com.slipenk.ordersapp.dictionary.Dictionary.ORDER;
import static com.slipenk.ordersapp.dictionary.Dictionary.ORDERS;
import static com.slipenk.ordersapp.dictionary.Dictionary.PAID;
import static com.slipenk.ordersapp.dictionary.Dictionary.USERNAME;

@Entity
@Table(name = ORDERS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false, unique = true)
    private int id;

    @Column(name = PAID, nullable = false)
    private boolean paid;

    @ManyToOne
    @JoinColumn(name = USERNAME)
    private OwnUser ownUser;

    @OneToMany(
            mappedBy = ORDER,
            cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
