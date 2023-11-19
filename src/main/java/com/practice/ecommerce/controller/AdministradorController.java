package com.practice.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.ecommerce.model.Product;
import com.practice.ecommerce.service.ProductService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("")
	public String home(Model model) {
		
		List <Product> products= productService.findAll();
		model.addAttribute("products", products);
		return "administrador/home";
	}
}
