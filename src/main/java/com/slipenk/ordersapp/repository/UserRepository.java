package com.slipenk.ordersapp.repository;

import com.slipenk.ordersapp.entity.OwnUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<OwnUser, String> {

    Optional<OwnUser> findByUsername(String username);
}
