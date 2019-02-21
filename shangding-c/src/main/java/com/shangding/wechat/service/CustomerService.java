package com.shangding.wechat.service;

/**
 * @author yjz
 * @since 2019/2/16
 */
public interface CustomerService {

    /**
     * 判断此用户是否是我们的新用户，是新用户加入到数据库中，不是新用户不加
     * @param openId 微信id
     * @param thumb 微信头像url
     * @param nickName 微信昵称
     * @return 返回数据库里customerId
     */
    int getOrSaveCustomer(String openId, String thumb, String nickName);

    /**
     * 判断此用户是否是我们的新用户，是新用户加入到数据库中，不是新用户不加
     * @param openId 微信id
     * @return 返回数据库里customerId
     */
    int insertCustomerOnlyOpenId(String openId);
}
