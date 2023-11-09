package com.practice.ecommerce.model;

public class User {
	private Long id;
	private String name;
	private String username;
	private String email;
	private String direction;
	private String phone;
	private String type;
	private String password;
	
	//Constructor vacio
	public User() {
		
	}
	
	//Constructor
	public User(Long id, String name, String username, String email, String direction, String phone, String type,
			String password) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.direction = direction;
		this.phone = phone;
		this.type = type;
		this.password = password;
	}
	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", direction="
				+ direction + ", phone=" + phone + ", type=" + type + ", password=" + password + "]";
	}
	
	
}
