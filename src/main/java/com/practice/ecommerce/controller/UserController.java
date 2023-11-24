package com.practice.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.ecommerce.model.Order;
import com.practice.ecommerce.model.User;
import com.practice.ecommerce.service.IOrderService;
import com.practice.ecommerce.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IOrderService orderService;
	
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
	
	@GetMapping ("/login")
	public String login () {
		return "usuario/login";
	}
	
	@PostMapping("/acceder")
	public String acceder(User user, HttpSession session) {
		logger.info("Accesos: {}",user);
		
		Optional<User> user1 = userService.findByEmail(user.getEmail());
		//logger.info("Usuario de db: {}", user1.get());
		
		if(user1.isPresent()) {
			session.setAttribute("idusuario", user1.get().getId());
			if(user1.get().getType().equals("ADMIN")) {
				return "redirect:/administrador";
			}else {
				return "redirect:/";
			}
		}else {
			logger.info("Usuario no existe");
		}
		
		return"redirect:/";
	}
	
	@GetMapping("/compras")
	public String obtenerCompras(Model model, HttpSession session) {
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		User user= userService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		List<Order> orders = orderService.findByUser(user);
		
		model.addAttribute("orders", orders);
		
		return "usuario/compras";
	}
	
	@GetMapping("detalle/{id}")
	public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) {
		logger.info("Id de la orden: {}",id);
		Optional<Order> order= orderService.findById(id);
		
		model.addAttribute("details", order.get().getDetail());
		
		//session 
		model.addAttribute("session", session.getAttribute("idusuario"));
		return "usuario/detalleCompra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
}

