package com.example.rubiesfashionstore.repository;

import com.example.rubiesfashionstore.model.Order;
import com.example.rubiesfashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);
}
