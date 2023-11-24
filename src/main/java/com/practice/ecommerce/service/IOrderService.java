package com.practice.ecommerce.service;

import java.util.List;

import com.practice.ecommerce.model.Order;

public interface IOrderService {
	
	List<Order> findAll();
	
	Order save(Order order);
}
