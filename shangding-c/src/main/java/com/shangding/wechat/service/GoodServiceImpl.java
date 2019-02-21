package com.shangding.wechat.service;

import com.shangding.wechat.dao.GoodMapper;
import com.shangding.wechat.model.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public Good getGood(int good_id) {
        return this.goodMapper.getGood(good_id);
    }

    @Override
    public List<Good> getAll() {
        return this.goodMapper.getAll();
    }

    @Override
    public List<Good> getAllOdd() {
        return this.goodMapper.getAllOdd();
    }

    @Override
    public List<Good> getAllEven() {
        return this.goodMapper.getAllEven();
    }

    @Override
    public List<Good> getByIds(String goodIds) {
        return this.goodMapper.getByIds(goodIds);
    }

    @Override
    public Good getCartInfo(int good_id) {
        return this.goodMapper.getCartInfo(good_id);
    }

    @Override
    public List<Good> getByType(String goodType) {
        return this.goodMapper.getByType(goodType);
    }

    @Override
    public List<Good> getTitle() {
        return this.goodMapper.getTitle();
    }
}
