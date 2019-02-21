package com.shangding.wechat.service;

import com.shangding.wechat.dao.CategoryMapper;
import com.shangding.wechat.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAll() {
        return this.categoryMapper.getAll();
    }
}
