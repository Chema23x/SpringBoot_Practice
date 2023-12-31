package com.practice.ecommerce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.practice.ecommerce.model.Order;
import com.practice.ecommerce.model.OrderDetail;
import com.practice.ecommerce.model.Product;
import com.practice.ecommerce.service.IOrderDetailService;
import com.practice.ecommerce.service.IOrderService;
import com.practice.ecommerce.service.IUserService;
import com.practice.ecommerce.service.ProductService;
import com.practice.ecommerce.model.User;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductService productService;	
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private IOrderDetailService orderDetailService;
	
	//para almacenar los detalles de la orden
	List<OrderDetail> detalles = new ArrayList<OrderDetail>();
	
	// datos de la orden
	Order order = new Order();

	@GetMapping("")
	public String home(Model model, HttpSession session) {
		
		log.info("Sesion del usuario: {}", session.getAttribute("idusuario"));
		
		model.addAttribute("products", productService.findAll());
		
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "usuario/home";
	}
	
	@GetMapping("producthome/{id}")
	public String productHome (@PathVariable Integer id, Model model) {
		log.info("Id producto enviado como parámetro {}",id);
		Product product = new Product();
		Optional<Product> productOptional= productService.get(id);
		product = productOptional.get();
		
		model.addAttribute("product", product);
		
		return "usuario/productohome";
	}
	
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer quantity, Model model) {
		OrderDetail orderDetail = new OrderDetail();
		Product product = new Product();
		double sumaTotal = 0;
		
		Optional<Product> optionalProduct = productService.get(id);
		log.info("Producto añadido: {}", optionalProduct.get());
		log.info("Cantidad: {}", quantity);
		product=optionalProduct.get();
		
		orderDetail.setQuantity(quantity);
		orderDetail.setPrice(product.getPrice());
		orderDetail.setName(product.getName());
		orderDetail.setTotal(product.getPrice()*quantity);
		orderDetail.setProduct(product);
		
		//validar que addProduct no se agregue 2 veces
		
		Integer idProduct = product.getId();
		boolean ingresado=detalles.stream().anyMatch(p -> p.getProduct().getId()==idProduct);
		
		if (!ingresado) {
			detalles.add(orderDetail);
		} 	
		
		sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		order.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("order", order);
		
		return "usuario/carrito";
	}
	
	//quitar un producto del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteProductCart(@PathVariable Integer id, Model model) {
		
		List<OrderDetail> newOrders = new ArrayList<OrderDetail>();
		
		for(OrderDetail orderDetail: detalles) {
			if(orderDetail.getProduct().getId()!=id) {
				newOrders.add(orderDetail);
			}
		}
		
		//poner la nueva lista con los productos restantes
		detalles=newOrders;
		
		double sumaTotal = 0;
		sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		order.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("order", order);
		
		return "usuario/carrito";
	}
	
	@GetMapping ("/getCart")
	public String getCart(Model model, HttpSession session) {
		model.addAttribute("cart", detalles);
		model.addAttribute("order", order);
		
		//sesion
		
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "/usuario/carrito";
	}
	
	@GetMapping ("/order")
	public String order(Model model, HttpSession session) {
		
		User user = userService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get()	;
		
		model.addAttribute("cart", detalles);
		model.addAttribute("order", order);
		model.addAttribute("user", user);
		return "usuario/resumenorden";
	}
	
	//guardar la orden
	@GetMapping ("/saveOrder")
	public String saveOrder(HttpSession session) {
		Date creationDate = new Date();
		order.setCreationDate(creationDate);
		order.setNumber(orderService.generateOrderNumber());
		
		//usuario
		User user = userService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		order.setUser(user);
		orderService.save(order);
		
		// guardar Detalles
		for(OrderDetail dt:detalles) {
			dt.setOrder(order);
			orderDetailService.save(dt);
		}
			
		// limpiar lista y orden
		
		order = new Order();
		detalles.clear();
		return "redirect:/";
	}
	
	@PostMapping ("/search")
	public String searchProduct(@RequestParam String name, Model model) {
		log.info("Nombre del producto: {}", name);
		List<Product> products = productService.findAll().stream().filter(p-> p.getName().toLowerCase().contains(name)).collect(Collectors.toList());
		
		model.addAttribute("products", products);
		
		return "usuario/home";
	}
}
