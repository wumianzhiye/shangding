package com.shangding.wechat.model;

import java.util.Date;

/**
 * @author yjz
 * @since 2019/2/16
 */
public class Customer {

    private int id;
    private String openId;
    /**
     * 用户头像url
     */
    private String thumb;
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 是否第一次拿到用户信息
     */
    private Integer firstGetInfo;

    private Date createTime;

    private Date updateTime;



    public Customer() {
    }

    public Customer( String openId, String thumb, String nickName) {
        this.openId = openId;
        this.thumb = thumb;
        this.nickName = nickName;
    }

    public Customer(String openId, Integer firstGetInfo, Date updateTime) {
        this.openId = openId;
        this.firstGetInfo = firstGetInfo;
        this.updateTime = updateTime;
    }

    public Customer(String openId, Integer firstGetInfo) {
        this.openId = openId;
        this.firstGetInfo = firstGetInfo;
    }

    public Customer(Integer id, String openId, String thumb, String nickName, Date updateTime) {
        this.id =  id;
        this.openId = openId;
        this.thumb = thumb;
        this.nickName = nickName;
        this.updateTime = updateTime;
    }

    public Customer(Integer id, String openId, String thumb, String nickName, Integer firstGetInfo, Date updateTime) {
        this.id = id;
        this.openId = openId;
        this.thumb = thumb;
        this.nickName = nickName;
        this.firstGetInfo = firstGetInfo;
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFirstGetInfo() {
        return firstGetInfo;
    }

    public void setFirstGetInfo(Integer firstGetInfo) {
        this.firstGetInfo = firstGetInfo;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", thumb='" + thumb + '\'' +
                ", nickName='" + nickName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
