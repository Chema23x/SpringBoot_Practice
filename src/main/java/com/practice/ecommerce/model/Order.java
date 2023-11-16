package com.practice.ecommerce.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String number;
	private Date creationDate;
	private Date receivedDate;
	
	private double total;
	
	@ManyToOne
	private User user;
	
	@OneToOne(mappedBy = "order")
	private OrderDetail detail;
	
	//Constructor vacio
		public Order() {
			
		}
	//Constructor

		public Order(Long id, String number, Date creationDate, Date receivedDate, double total, User user,
				OrderDetail detail) {
			super();
			this.id = id;
			this.number = number;
			this.creationDate = creationDate;
			this.receivedDate = receivedDate;
			this.total = total;
			this.user = user;
			this.detail = detail;
		}
		
	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public OrderDetail getDetail() {
		return detail;
	}

	public void setDetail(OrderDetail detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", number=" + number + ", creationDate=" + creationDate + ", receivedDate="
				+ receivedDate + ", total=" + total + ", user=" + user + ", detail=" + detail + "]";
	}

		
}
