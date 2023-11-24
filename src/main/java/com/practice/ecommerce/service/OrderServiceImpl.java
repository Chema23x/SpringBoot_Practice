package com.practice.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.ecommerce.model.Order;
import com.practice.ecommerce.model.User;
import com.practice.ecommerce.repository.IOrderRepository;

@Service
public class OrderServiceImpl implements IOrderService {
	
	@Autowired
	private IOrderRepository orderRepository;

	@Override
	public Order save(Order order) {
		
		return orderRepository.save(order);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	
	// orden 000010
	public String generateOrderNumber() {
		
		int numero= 0;
		String numeroConcatenado="";
		
		List<Order> orders = findAll();
		
		List<Integer> numeros = new ArrayList<Integer>();
		
		orders.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumber())));
		
		if (orders.isEmpty()) {
			numero=1;
		}else{
			numero= numeros.stream().max(Integer::compare).get();
			numero++;
		}
		
		if (numero<10) {
			numeroConcatenado="000000000"+String.valueOf(numero);
		} else if(numero<100) {
			numeroConcatenado="00000000"+String.valueOf(numero);
		} else if(numero<1000) {
			numeroConcatenado="0000000"+String.valueOf(numero);
		}
		else if(numero<10000) {
			numeroConcatenado="000000"+String.valueOf(numero);
		}
		
		return numeroConcatenado;
	}

	@Override
	public List<Order> findByUser(User user) {
		return orderRepository.findByUser(user);
	}

}
