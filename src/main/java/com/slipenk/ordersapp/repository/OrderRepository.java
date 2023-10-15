package com.slipenk.ordersapp.repository;

import com.slipenk.ordersapp.entity.Order;
import com.slipenk.ordersapp.entity.OwnUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByPaidAndCreatedDateTimeBefore(boolean paid, Timestamp timestamp);

    List<Order> findByPaid(boolean paid);

    List<Order> findByOwnUser(OwnUser user);
}
