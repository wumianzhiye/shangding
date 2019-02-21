package com.shangding.wechat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shangding.wechat.facade.CartFacade;
import com.shangding.wechat.facade.CustomerFacade;
import com.shangding.wechat.model.Cart;
import com.shangding.wechat.model.Good;
import com.shangding.wechat.service.CartService;
import com.shangding.wechat.service.GoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author yjz
 * @since 2019.2.13
 */
@RestController
@RequestMapping("/cart")
public class CartController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomerFacade customerFacade;

	@Autowired
	private GoodService goodService;

	@Autowired
	private CartFacade cartFacade;


	/**
	 * 拿到个人的购物车信息
	 * @param code
	 * @return
	 * @throws IOException
	 */
	@GetMapping("get_customer_id")
	public Object getByCustomerId(@RequestParam("code") String code) throws IOException {
		int customerId = this.customerFacade.getCustomerId(code);
		LOGGER.info("北京第三交通委提醒你,get cart,customerId:{}", customerId);
		List<Cart> results = cartService.getByCustomerId(customerId);
		ObjectMapper mapper = new ObjectMapper();
		String ret = mapper.writeValueAsString(results);
		return ret;
	}

	/**
	 * 加商品到购物车
	 * @param goodId 被选择的商品id
	 * @param code  调取微信接口拿到customerId
	 */
	@PostMapping("/add")
	public void add(@RequestParam(value = "goodId", required = false) Integer goodId,
					@RequestParam("code") String code) {
		// 拿到用户id
		int customerId = this.customerFacade.getCustomerId(code);
		LOGGER.info("cart add,customerId:{},goodId:{}", customerId, goodId);
		synchronized (customerId + "add_cart") {
			Good good = this.goodService.getGood(goodId);
			Cart cart = new Cart(goodId, good.getGoodName(), good.getGoodPrice(), good.getGoodMainUrl(), good.getGoodDetailUrls(), customerId, 1);
			this.cartService.save(cart);
		}
	}

	/**
	 * 根据orderId拿到所有订单list
	 * @param orderId 订单id
	 * @return
	 * @throws IOException
	 */
	@GetMapping("get_list_order_id")
	public List<Cart> getByCustomerId(@RequestParam("orderId") int orderId) throws IOException {
		LOGGER.info("get cart list by orderId:{}", orderId);
		List<Cart> listByOrderId = this.cartFacade.getListByOrderId(orderId);
		return listByOrderId;
	}
}
