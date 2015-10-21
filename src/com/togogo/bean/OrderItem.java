package com.togogo.bean;

/**
 * 
 * 订单中项目
 * @author Zhanhao Wong
 *
 */
public class OrderItem {
	
	/** 订单项目id **/
	private String id;
	
	/** 订单id **/
	private String orderId;
	
	/** 书本id **/
	private String bookId;
	
	/** 数量 **/
	private Integer quantity;
	
	/** 单价 **/
	private Double price;
	
	/** 总价小结 （单价*数量） **/
	private Double sumPrice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}
	
	

}
