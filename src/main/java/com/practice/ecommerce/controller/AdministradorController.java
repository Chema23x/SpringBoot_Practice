package com.practice.ecommerce.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.ecommerce.model.Order;
import com.practice.ecommerce.model.Product;
import com.practice.ecommerce.service.IOrderService;
import com.practice.ecommerce.service.IUserService;
import com.practice.ecommerce.service.ProductService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private ProductService productService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IOrderService orderService;
	
	private Logger logg= LoggerFactory.getLogger(AdministradorController.class);
	
	
	@GetMapping("")
	public String home(Model model) {
		
		List <Product> products= productService.findAll();
		model.addAttribute("products", products);
		return "administrador/home";
	}
	
	@GetMapping ("/users")
	public String usuarios(Model model) {
		model.addAttribute("users", userService.findAll());
		return "administrador/usuarios";
	}
	@GetMapping ("/orders")
	public String orders(Model model) {
		model.addAttribute("orders", orderService.findAll());
		return "administrador/orders";
	}
	
	@GetMapping ("detalle/{id}")
	public String detalle (Model model, @PathVariable Integer id) {
		logg.info("Id de la order: {}",id);
		
		Order order = orderService.findById(id).get();
		
		model.addAttribute("detalles", order.getDetail());
		
		return "administrador/detalleorden";
	}
	}

