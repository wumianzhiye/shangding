package com.shangding.wechat.service;

import com.shangding.wechat.model.Good;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
public interface GoodService {

    Good getGood(int good_id);

    List<Good> getAll();

    List<Good> getAllOdd();

    List<Good> getAllEven();

    List<Good> getByIds(String goodIds);

    Good getCartInfo(int good_id);

    List<Good> getByType(String goodType);

    List<Good> getTitle();
}
