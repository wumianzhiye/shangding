package com.shangding.wechat.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yjz
 * @since 2019.2.16
 */
public class Order {
	private Integer id;
	private String goodName;
	private BigDecimal finalPrice;
	private String goodMainUrl;
	private Integer goodNum;
	private Integer customerId;
	private Integer status;
	private String address;
	private String phone;
	private String buyerName;
	private Date createTime;
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public BigDecimal getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getGoodMainUrl() {
		return goodMainUrl;
	}
	public void setGoodMainUrl(String goodMainUrl) {
		this.goodMainUrl = goodMainUrl;
	}
	public Integer getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(Integer goodNum) {
		this.goodNum = goodNum;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Order() {
	}

	public Order(Integer id, String goodName, BigDecimal finalPrice, String goodMainUrl, Integer goodNum) {
		super();
		this.id = id;
		this.goodName = goodName;
		this.finalPrice = finalPrice;
		this.goodMainUrl = goodMainUrl;
		this.goodNum = goodNum;
	}
	public Order(String goodName, BigDecimal finalPrice, String goodMainUrl, Integer goodNum) {
		super();
		this.goodName = goodName;
		this.finalPrice = finalPrice;
		this.goodMainUrl = goodMainUrl;
		this.goodNum = goodNum;
	}

	public Order(String goodName, BigDecimal finalPrice, String goodMainUrl, Integer goodNum, Integer customerId, Integer status,
				 String buyerName, String phone, String address) {
		this.goodName = goodName;
		this.finalPrice = finalPrice;
		this.goodMainUrl = goodMainUrl;
		this.goodNum = goodNum;
		this.customerId = customerId;
		this.status = status;
		this.buyerName = buyerName;
		this.phone = phone;
		this.address = address;
	}
}
