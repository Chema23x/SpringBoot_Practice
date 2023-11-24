package com.practice.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.practice.ecommerce.model.Product;
import com.practice.ecommerce.model.User;
import com.practice.ecommerce.service.IUserService;
import com.practice.ecommerce.service.ProductService;
import com.practice.ecommerce.service.UploadFileService;


@Controller
@RequestMapping("/products")
public class ProductController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UploadFileService upload;
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("products", productService.findAll());
		return "productos/show";
	}
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	@PostMapping("/save")
	public String save(Product product, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
		LOGGER.info("Este es el objeto product {}", product);
		User user= userService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		product.setUser(user);
		
		//images
		if (product.getId()==null) { //cuando se crea un producto
			String nameImage=upload.saveImage(file);
			product.setImage(nameImage);
		}else {
			
		}
		
		productService.save(product);
		return "redirect:/products";
	}
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Product product = new Product();
		Optional <Product> optionalProduct=productService.get(id);
		product= optionalProduct.get();
		
		LOGGER.info("Producto buscado: {}", product);
		model.addAttribute("product", product);
		
		return "productos/edit";
	}
	@PostMapping("/update")
	public String update(Product product, @RequestParam("img") MultipartFile file) throws IOException {
		Product p = new Product();
		p=productService.get(product.getId()).get();
		
		if(file.isEmpty()) { // editamos el producto pero no cambiamos la imagen
			
			product.setImage(p.getImage());
		}else { //cuando se edita tambien la imagen
			//eliminar cuando no sea la imagen por defecto
			if(!p.getImage().equals("default.jpg")) {
				upload.deleteImage(p.getImage());
			}
			String nameImage=upload.saveImage(file);
			product.setImage(nameImage);
		}
		product.setUser(p.getUser());
		productService.update(product);
		return "redirect:/products";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		
		Product p = new Product();
		p=productService.get(id).get();
		
		//eliminar cuando no sea la imagen por defecto
		if(!p.getImage().equals("default.jpg")) {
			upload.deleteImage(p.getImage());
		}
		
		productService.delete(id);
		return "redirect:/products";
	}
}
