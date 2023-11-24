package com.practice.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.ecommerce.model.OrderDetail;
import com.practice.ecommerce.repository.IOrderDetailRepository;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
	
	@Autowired
	private IOrderDetailRepository orderDetailRepository;

	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		return orderDetailRepository.save(orderDetail);
	}

}
