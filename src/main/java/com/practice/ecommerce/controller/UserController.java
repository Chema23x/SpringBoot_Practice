package com.practice.ecommerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.ecommerce.model.User;
import com.practice.ecommerce.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	
	// usuario/registro
	@GetMapping ("/register")
	public String create() {
		return "usuario/registro";
	}
	
	@PostMapping ("/save")
	public String save(User user) {
		logger.info("Usuario registro: {}", user);
		user.setType("User");
		userService.save(user);
		
		return "redirect:/";
	}
	
}
