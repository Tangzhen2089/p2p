package com.itheima.domain;

import java.util.Date;

//投资记录
public class ProductAccount {
	
	private int id;
	private String pa_num;
	private Date pa_date;
	private Customer customer;
	private Product product;
	private double money;
	private double interest;
	private int status;//是否到期，0未到期，1到期
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPa_num() {
		return pa_num;
	}
	public void setPa_num(String pa_num) {
		this.pa_num = pa_num;
	}
	public Date getPa_date() {
		return pa_date;
	}
	public void setPa_date(Date pa_date) {
		this.pa_date = pa_date;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
}
