package com.togogo.bean;

import java.util.Date;

/**
 * 订单
 * @author Zhanhao Wong
 *
 */
public class Order {

	/** 订单id **/
	private String id;
	
	/** 订单下单时间 **/
	private Date orderTime;
	
	/** 订单总价格 **/
	private Double sumPrice;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Double getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}
	
	
	
}
