package com.togogo.bean;

/**
 * 
 * 书本
 * @author Zhanhao Wong
 *
 */
public class Book {
	
	
	/** 书本id **/
	private String id;
	
	/** 书本名称 **/
	private String name;
	
	/** 书本价格 **/
	private double price;
	
	/** 书本图片地址 **/
	private String pic;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Book(String id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	

}
