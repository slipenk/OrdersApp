package com.slipenk.ordersapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

import static com.slipenk.ordersapp.dictionary.Dictionary.ENABLED;
import static com.slipenk.ordersapp.dictionary.Dictionary.PASSWORD;
import static com.slipenk.ordersapp.dictionary.Dictionary.OWN_USER;
import static com.slipenk.ordersapp.dictionary.Dictionary.USERNAME;
import static com.slipenk.ordersapp.dictionary.Dictionary.USERS;

@Entity
@Table(name = USERS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnUser {

    @Id
    @Column(name = USERNAME, nullable = false, unique = true)
    private String username;

    @Column(name = PASSWORD, nullable = false)
    private String password;

    @Column(name = ENABLED, nullable = false)
    private boolean enabled;

    @OneToMany(
            mappedBy = OWN_USER,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<Authority> authorities;

    @OneToMany(
            mappedBy = OWN_USER,
            cascade = CascadeType.ALL)
    private List<Order> orders;
}
