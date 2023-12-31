package com.practice.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.practice.ecommerce.model.Order;
import com.practice.ecommerce.model.User;

public interface IOrderService {
	
	List<Order> findAll();
	
	Optional<Order> findById(Integer id);
	
	Order save(Order order);
	
	String generateOrderNumber();
	
	List<Order> findByUser (User user);
	

}
