package com.practice.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.ecommerce.model.Order;
import com.practice.ecommerce.model.User;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByUser (User user);

}
