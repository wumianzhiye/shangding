package com.shangding.wechat.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.shangding.wechat.facade.CustomerFacade;
import com.shangding.wechat.facade.OrderFacade;
import com.shangding.wechat.model.Order;
import com.shangding.wechat.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * @author yjz
 * @since 2019.2.13
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
	private static Gson GSON = new Gson();
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerFacade customerFacade;

	@Autowired
	private OrderFacade orderFacade;

	/**
	 * 根据customerId拿到所有订单list
	 * @param code 用户登录信息
	 * @return
	 * @throws IOException
	 */
	@GetMapping("get_customer_id_and_order_id")
	public Map getByCustomerId(@RequestParam("code") String code,
							   @RequestParam(value = "maxOrderId") int maxOrderId) throws IOException {
		int customerId = this.customerFacade.getCustomerId(code);
		LOGGER.info("get order list by customerId:{}", customerId);
		List<Order> results = this.orderService.getByCustomerId(customerId, maxOrderId);
		Map<String, Object> resultMap = Maps.newHashMap();
		// 获取最大orderId
		int minOrderId;
		if(results.isEmpty()){
			minOrderId = maxOrderId;
		} else {
			minOrderId = results.stream().map(Order::getId).min(Comparator.naturalOrder()).get();
		}
		resultMap.put("orders", results);
		resultMap.put("orderId", minOrderId);

		return resultMap;
	}

	/**
	 * @param orderId 订单id
	 * @return
	 * @throws IOException
	 */
	@GetMapping("get_by_id")
	public List<Order> getByCustomerId(@RequestParam("orderId") int orderId) throws IOException {
		LOGGER.info("order get by id:{}", orderId);
		Order byId = this.orderService.getById(orderId);
		List<Order> orderList = Lists.newArrayList();
		orderList.add(byId);
		return orderList;
	}


	/**
	 * @param code
	 * @param packageSelectCartIds 格式 cartId,cartId
	 * @param packageSelectGoodIdNum goodId-num;goodId-num
	 * @param addressName 收货人名
	 * @param addressPhone 收货电话
	 * @param addressDetail 地址详情
	 * @throws IOException
	 */
	@PostMapping("add")
	public int add(@RequestParam("code") String code,
				   @RequestParam("packageSelectCartIds") String packageSelectCartIds,
				   @RequestParam("packageSelectGoodIdNum") String packageSelectGoodIdNum,
				   @RequestParam("addressName") String addressName,
				   @RequestParam("addressPhone") String addressPhone,
				   @RequestParam("addressDetail") String addressDetail) throws IOException {
		int customerId = this.customerFacade.getCustomerId(code);
		LOGGER.info("order add,customerId:{},code:{},packageSelectCartIds:{},packageSelectGoodIdNum:{},addressName:{},addressPhone:{},addressDetail:{}",
				customerId, code, packageSelectCartIds, packageSelectGoodIdNum, addressName, addressPhone, addressDetail);
		int orderId = this.orderFacade.createOrder(packageSelectCartIds, packageSelectGoodIdNum, customerId, addressName, addressPhone, addressDetail);
		return orderId;
	}

	/**
	 * @param code 去找customerId
	 * @param orderId 订单id
	 * @throws IOException
	 * @return 返回此用户的未取消单子
	 */
	@GetMapping("cancel")
	public List<Order> cancel(@RequestParam("code") String code,
							  @RequestParam("orderId") int orderId) throws IOException {
		int customerId = this.customerFacade.getCustomerId(code);
		// 先取消单子
		this.orderService.cancelById(orderId, customerId);
		LOGGER.info("order cancel customerId:{},orderId:{}", customerId, orderId);
		// 查询单子返回回去
		List<Order> results = this.orderService.getByCustomerId(customerId, null);
		return results;
	}
}
