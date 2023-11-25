package com.practice.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
