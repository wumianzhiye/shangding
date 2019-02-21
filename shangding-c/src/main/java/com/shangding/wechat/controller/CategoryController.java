package com.shangding.wechat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shangding.wechat.model.Category;
import com.shangding.wechat.model.FullCategory;
import com.shangding.wechat.model.Good;
import com.shangding.wechat.service.CategoryService;
import com.shangding.wechat.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @since 2019.2.13
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
    CategoryService categoryService;
	@Autowired
	GoodService goodService;

	/**
	 * 菜单商品
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_all", produces = "application/json; charset=utf-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object getAll() throws IOException {
		List<Category> results = categoryService.getAll();
		List<FullCategory> full_results=new ArrayList<>();
		for (Category category : results) {
			List<Good> goods = this.goodService.getByType(category.getCatType());
			FullCategory fullCategory=new FullCategory(category, goods);
			full_results.add(fullCategory);
		}
		ObjectMapper mapper = new ObjectMapper();
		String ret = mapper.writeValueAsString(full_results);
		return ret;
	}
}