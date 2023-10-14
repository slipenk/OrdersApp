package com.slipenk.ordersapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static com.slipenk.ordersapp.dictionary.Dictionary.AUTHORITIES;
import static com.slipenk.ordersapp.dictionary.Dictionary.AUTHORITY;
import static com.slipenk.ordersapp.dictionary.Dictionary.ID;
import static com.slipenk.ordersapp.dictionary.Dictionary.USERNAME;

@Entity
@Table(name = AUTHORITIES)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, nullable = false, unique = true)
    private Integer id;

    @Column(name = AUTHORITY, nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = USERNAME)
    private OwnUser ownUser;
}
