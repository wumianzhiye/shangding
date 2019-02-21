package com.shangding.wechat.facade;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shangding.wechat.model.Cart;
import com.shangding.wechat.model.Good;
import com.shangding.wechat.model.OrderItem;
import com.shangding.wechat.service.GoodService;
import com.shangding.wechat.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yjz
 * @since 2019/2/20
 */
@Service
public class CartFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartFacade.class);

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private GoodService goodService;

    /**
     * 根据订单id拿到cartList
     * @param orderId
     * @return
     */
    public List<Cart> getListByOrderId(int orderId){
        // 根据订单找详细商品
        List<OrderItem> byOrderId = this.orderItemService.getByOrderId(orderId);
        if (byOrderId.isEmpty()) {
            LOGGER.info("get order item is empty,orderId:{}", orderId);
            return Lists.newArrayList();
        }
        List<Cart> cartList = packageCartsList(byOrderId);

        return cartList;
    }

    /**
     * 打包成cartList
     * @param byOrderId
     * @return
     */
    private List<Cart> packageCartsList(List<OrderItem> byOrderId) {
        // 1.找数据
        // key-goodId,value-数量
        Map<Integer, Integer> goodIdNumMap = Maps.newHashMap();
        // goodId逗号隔开
        StringBuffer goodIdSB = new StringBuffer();
        for(OrderItem orderItem : byOrderId){
            goodIdNumMap.put(orderItem.getGoodId(), orderItem.getQuantity());
            goodIdSB.append(orderItem.getGoodId());
            goodIdSB.append(",");
        }

        String goodIdStr = goodIdSB.toString();
        goodIdStr = goodIdStr.substring(0, goodIdStr.length() - 1);
        List<Good> byIds = this.goodService.getByIds(goodIdStr);
        // key-goodId,value-Good
        Map<Integer, Good> goodIdGoodMap = Maps.newHashMap();
        for(Good good : byIds){
            goodIdGoodMap.put(good.getId(), good);
        }

        // 2.拼装成list<Cart>
        List<Cart> cartList = doPackageCartsList(goodIdNumMap, goodIdGoodMap);
        return cartList;
    }

    /**
     * 打包
     * @param goodIdNumMap
     * @param goodIdGoodMap
     * @return
     */
    private List<Cart> doPackageCartsList(Map<Integer, Integer> goodIdNumMap, Map<Integer, Good> goodIdGoodMap) {
        List<Cart> cartList = Lists.newArrayList();
        Set<Integer> goodIdSet = goodIdGoodMap.keySet();
        for(Integer goodId : goodIdSet){
            Good good = goodIdGoodMap.get(goodId);
            Integer goodNum = goodIdNumMap.get(goodId);
            Cart cart = new Cart(good.getGoodName(), good.getGoodPrice(), good.getGoodMainUrl(), goodNum);
            cartList.add(cart);
        }
        return cartList;
    }
}
