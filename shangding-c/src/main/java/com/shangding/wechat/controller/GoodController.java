package com.shangding.wechat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.shangding.wechat.model.Good;
import com.shangding.wechat.service.GoodService;
import com.shangding.wechat.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;


/**
 * @author yjz
 * @since 2019.2.13
 */
@Controller
@RequestMapping("/good")
public class GoodController {

	@Autowired
	private GoodService goodService;

	@RequestMapping(value = "/{goodId}", produces = "application/json; charset=utf-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object getGood(@PathVariable(value = "goodId") int goodId) throws IOException {
		Object oneGood = goodService.getGood(goodId);
		ObjectMapper mapper = new ObjectMapper();
		String ret = mapper.writeValueAsString(oneGood);
		return ret;
	}

	@RequestMapping(value = "/get_all", produces = "application/json; charset=utf-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object getAll() throws IOException {
		List<Good> results = goodService.getAll();
		ObjectMapper mapper = new ObjectMapper();
		String ret = mapper.writeValueAsString(results);
		return ret;
	}

	/**
	 * 得到所有id为奇数的纪录
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_all_odd", produces = "application/json; charset=utf-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getAllOdd() throws IOException {
		List<Good> results = goodService.getAllOdd();
		ObjectMapper mapper = new ObjectMapper();
		String ret = mapper.writeValueAsString(results);
		return ret;
	}

	/**
	 * 得到所有id为偶数的纪录
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_all_even", produces = "application/json; charset=utf-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getAllEven() throws IOException {
		List<Good> results = goodService.getAllEven();
		ObjectMapper mapper = new ObjectMapper();
		String ret = mapper.writeValueAsString(results);
		return ret;
	}

	@RequestMapping(value = "/get_title", produces = "application/json; charset=utf-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object getTitle() throws IOException {
		List<Good> results = goodService.getTitle();
		ObjectMapper mapper = new ObjectMapper();
		String ret = mapper.writeValueAsString(results);
		return ret;
	}

}