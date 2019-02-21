package com.shangding.wechat.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车
 * @author yjz
 * @since 2019.2.18
 */
public class Cart implements Serializable {

	public static final long serialVersionUID = 1L;

	private Integer id;
	private Integer goodId;
	private String goodName;
	private BigDecimal goodPrice;
	private String goodMainUrl;
	private String goodDetailUrls;
	private Integer active;
	private Integer num;
	private Integer customerId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodId() {
		return goodId;
	}

	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public BigDecimal getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(BigDecimal goodPrice) {
		this.goodPrice = goodPrice;
	}

	public String getGoodMainUrl() {
		return goodMainUrl;
	}

	public void setGoodMainUrl(String goodMainUrl) {
		this.goodMainUrl = goodMainUrl;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getGoodDetailUrls() {
		return goodDetailUrls;
	}

	public void setGoodDetailUrls(String goodDetailUrls) {
		this.goodDetailUrls = goodDetailUrls;
	}


	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Cart() {
	}

	public Cart(Integer id, Integer goodId, String goodName, BigDecimal goodPrice, String goodMainUrl, String goodDetailUrls,
				Integer active, Integer num) {
		super();
		this.id = id;
		this.goodId = goodId;
		this.goodName = goodName;
		this.goodPrice = goodPrice;
		this.goodMainUrl = goodMainUrl;
		this.goodDetailUrls=goodDetailUrls;
		this.active = active;
		this.num = num;
	}

	public Cart(Integer goodId, String goodName, BigDecimal goodPrice, String goodMainUrl,String goodDetailUrls, Integer customerId, Integer num) {
		super();
		this.goodId = goodId;
		this.goodName = goodName;
		this.goodPrice = goodPrice;
		this.goodMainUrl = goodMainUrl;
		this.goodDetailUrls=goodDetailUrls;
		this.customerId = customerId;
		this.num = num;
	}

	public Cart(Integer id, Integer goodId, String goodName, BigDecimal goodPrice, String goodMainUrl,
				String goodDetailUrls, Integer active, Integer num, Integer customerId) {
		this.id = id;
		this.goodId = goodId;
		this.goodName = goodName;
		this.goodPrice = goodPrice;
		this.goodMainUrl = goodMainUrl;
		this.goodDetailUrls = goodDetailUrls;
		this.active = active;
		this.num = num;
		this.customerId = customerId;
	}

	public Cart(String goodName, BigDecimal goodPrice, String goodMainUrl, Integer num) {
		this.goodName = goodName;
		this.goodPrice = goodPrice;
		this.goodMainUrl = goodMainUrl;
		this.num = num;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", goodId=" + goodId + ", goodName=" + goodName + ", goodPrice=" + goodPrice
				+ ", goodMainUrl=" + goodMainUrl + ", goodDetailUrls=" + goodDetailUrls + ", active=" + active
				+ ", num=" + num + "]";
	}
	
}
