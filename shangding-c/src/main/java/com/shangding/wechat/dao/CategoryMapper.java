package com.shangding.wechat.dao;

import com.shangding.wechat.model.Category;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
public interface CategoryMapper {

    List<Category> getAll();
}
