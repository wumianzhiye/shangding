package com.shangding.wechat.facade;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shangding.wechat.dto.GoodDto;
import com.shangding.wechat.metadata.CartActiveEnum;
import com.shangding.wechat.metadata.OrderStatusEnum;
import com.shangding.wechat.model.Cart;
import com.shangding.wechat.model.Good;
import com.shangding.wechat.model.Order;
import com.shangding.wechat.model.OrderItem;
import com.shangding.wechat.service.CartService;
import com.shangding.wechat.service.GoodService;
import com.shangding.wechat.service.OrderItemService;
import com.shangding.wechat.service.OrderService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yjz
 * @since 2019/2/18
 */
@Service
public class OrderFacade {


    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodService goodService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private CartService cartService;

    /**
     * 生成订单，代付款状态
     *
     * @param json       被选择的购物车id，逗号隔开
     * @param packageSelectGoodIdNum     被选择的商品goodId-num;goodId-num
     * @param customerId
     * @param addressName 收货人名
     * @param addressPhone 收货电话
     * @param addressDetail 地址详情
     * @return orderId,可能是0
     */
    @Transactional(rollbackFor = Exception.class)
    public int createOrder(String json, String packageSelectGoodIdNum, int customerId, String addressName,String addressPhone,String addressDetail) {
        List<Cart> byCustomerIdAndIds = this.cartService.getByCustomerIdAndIds(customerId, json);

        if (byCustomerIdAndIds.isEmpty()) {
            return 0;
        }
        // 根据json拿到所有good详情list
        List<GoodDto> goodDtoList = getGoodDtoList(packageSelectGoodIdNum);

        // 是否为空
        if (CollectionUtils.isEmpty(goodDtoList)) {
            return 0;
        }

        // 每个goodName乘以数量
        StringBuffer goodNameSB = new StringBuffer();
        double finalPrice = 0;
        String goodMainUrl = goodDtoList.get(0).getGoodMainUrl();
        Integer goodNum = 0;
        for (GoodDto goodDto : goodDtoList) {
            goodNameSB.append(goodDto.getGoodName());
            goodNameSB.append("*");
            goodNameSB.append(goodDto.getNum());
            goodNameSB.append(" ");
            finalPrice += goodDto.getFinalPrice();
            goodNum += goodDto.getNum();
        }

        Order order = new Order(goodNameSB.toString(), new BigDecimal(finalPrice), goodMainUrl, goodNum, customerId,
                OrderStatusEnum.WAIT_PAY.getCode(), addressName, addressPhone, addressDetail);
        // 插入order表
        this.orderService.insert(order);

        // 插入orderItem表
        for (GoodDto goodDto : goodDtoList) {
            OrderItem orderItem = new OrderItem(goodDto.getId(), goodDto.getNum(), order.getId(), goodDto.getGoodPrice(), new BigDecimal(goodDto.getFinalPrice()));
            this.orderItemService.insert(orderItem);
        }

        // 删除对应的购物车
        for(Cart cart : byCustomerIdAndIds){
            cart.setActive(CartActiveEnum.YES.getCode());
            this.cartService.updateById(cart);
        }

        return order.getId();
    }

    /**
     * @param packageSelectGoodIdNum  被选择的商品和对应的数量，格式是：goodId-num;goodId-num
     * @return
     */
    private List<GoodDto> getGoodDtoList(String packageSelectGoodIdNum) {

        // json 被选择的商品和对应的数量，格式是：goodId-num;goodId-num
        // 根据规则把每个被选择的商品分开
        String[] selectGoodIdAndNumSplit = packageSelectGoodIdNum.split(";");

        // 判断是否选择了商品
        if (selectGoodIdAndNumSplit.length == 0) {
            return Lists.newArrayList();
        }

        // key-goodId,value-num
        Map<Integer, Integer> goodIdNumMap = Maps.newHashMap();
        // 存取goodId
        StringBuffer goodIdSB = new StringBuffer();
        // 拿出所有的商品id去数据库查
        for(String select : selectGoodIdAndNumSplit){
            String[] goodIdAndNumSplit = select.split("-");
            goodIdSB.append(NumberUtils.toInt(goodIdAndNumSplit[0]));
            goodIdSB.append(",");
            goodIdNumMap.put(NumberUtils.toInt(goodIdAndNumSplit[0]), NumberUtils.toInt(goodIdAndNumSplit[1]));
        }

        // key-goodId，value-good，去数据库拿数据
        String goodIds = goodIdSB.toString();
        goodIds = goodIds.substring(0, goodIds.length() - 1);
        Map<Integer, Good> mapByGoodIds = getMapByGoodIds(goodIds);

        return doPackageGoodDtoList(goodIdNumMap, mapByGoodIds);
    }

    /**
     * 根据goodIds查询good的map
     *
     * @param goodIds
     * @return key-goodId，value-good
     */
    private Map<Integer, Good> getMapByGoodIds(String goodIds) {
        List<Good> byIds = this.goodService.getByIds(goodIds);
        Map<Integer, Good> goodIdMap = Maps.newHashMap();
        for (Good good : byIds) {
            goodIdMap.put(good.getId(), good);
        }
        return goodIdMap;
    }

    /**
     * @param goodIdNumMap key-goodId,value-num
     * @param mapByGoodIds key-goodId，value-good
     */
    private List<GoodDto> doPackageGoodDtoList(Map<Integer, Integer> goodIdNumMap, Map<Integer, Good> mapByGoodIds) {
        // 拿到所有goodId
        Set<Integer> goodIdSet = goodIdNumMap.keySet();
        List<GoodDto> goodDtoList = Lists.newArrayList();
        for (Integer goodId : goodIdSet) {
            GoodDto goodDto = new GoodDto(goodId, mapByGoodIds.get(goodId).getGoodName(), mapByGoodIds.get(goodId).getGoodPrice(), goodIdNumMap.get(goodId),
                    mapByGoodIds.get(goodId).getGoodMainUrl());
            goodDtoList.add(goodDto);
        }
        return goodDtoList;
    }
}
