package com.shangding.wechat.dao;

import com.shangding.wechat.model.Good;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yjz
 * @since 2019/2/12
 */
public interface GoodMapper {

    Good getGood(@Param("id") int id);

    List<Good> getAll();

    List<Good> getAllOdd();

    List<Good> getAllEven();

    List<Good> getByIds(@Param("goodIds") String goodIds);

    Good getCartInfo(@Param("id") int id);

    List<Good> getByType(String goodType);

    List<Good> getTitle();
}
