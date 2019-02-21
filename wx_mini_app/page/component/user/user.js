// page/component/new-pages/user/user.js
var Config = require('../../utils/config.js');
Page({
  data: {
    thumb: '',
    nickname: '',
    orders: [],
    hasAddress: false,
    address: {},
    lastOrderId:0,
    hasMoreOrder: true,
    hiddenBottom : false,
  },

  //（待做：自动刷新）
  onLoad() {
    
  },
  onShow() {
    var self = this;
    console.log('onshow')

    wx.login({
      success(res) {
        console.log(res);
        if (res.code) {
          /**
          * 获取用户信息
          */
          wx.getUserInfo({
            success: function (resNew) {
              self.setData({
                thumb: resNew.userInfo.avatarUrl,
              })
              console.log("userInfo"+JSON.stringify(resNew.userInfo));
              // 发起网络请求
              wx.request({
                url: Config.rootUrl + '/user/get_user_info',
                data: {
                  code: res.code,
                  thumb: resNew.userInfo.avatarUrl,
                  nickName: resNew.userInfo.nickName
                }
              })
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })

    var maxOrderId = this.data.lastOrderId;
    wx.login({
      success(res) {
        console.log(res);
        if (res.code) {

          setTimeout(function () {//用延迟执行的方式避免因为事务冲突得到刚刚删除空的数据库而得不到数据
            wx.request({
              url: Config.rootUrl + '/order/get_customer_id_and_order_id',
              data: {
                code: res.code,
                maxOrderId: 0,
              },
              success(resNew) {
                console.log(resNew.data);
                self.setData({
                  orders: resNew.data.orders,
                  lastOrderId: resNew.data.orderId,
                })
              }
            });
          }, 1000)
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })

    /**
     * 获取本地缓存 地址信息
     */
    wx.getStorage({
      key: 'address',
      success: function (res) {
        self.setData({
          hasAddress: true,
          address: res.data
        })
      }
    })
  },
  /**
   * 发起支付请求
   */
  payOrders() {
    wx.requestPayment({
      timeStamp: 'String1',
      nonceStr: 'String2',
      package: 'String3',
      signType: 'MD5',
      paySign: 'String4',
      success: function (res) {
        console.log(res)
      },
      fail: function (res) {
        wx.showModal({
          title: '支付提示',
          content: '暂不支持支付',
          showCancel: false
        })
      }
    })
  },

  cancelOrders(ele) {
    console.log(ele.currentTarget.dataset.orderid);
    var orderId = ele.currentTarget.dataset.orderid;
    var self = this;
    wx.showModal({
      title: '提示',
      content: '确定取消订单吗？',
      success: function (res) {
        if (res.confirm) {
          wx.login({
            success(res) {
              console.log(res);
              if (res.code) {
                wx.request({
                  url: Config.rootUrl + '/order/cancel',
                  data: {
                    code: res.code,
                    orderId: orderId
                  },
                  success(resNew) {
                    console.log(resNew);
                    self.setData({
                      orders: resNew.data
                    });
                    wx.showModal({
                      title: '提示',
                      content: '取消订单成功',
                      showCancel: false
                    });
                  }
                });
              } else {
                console.log('取消失败，登录失败！' + res.errMsg)
              }
            }
          })
        } else {
          console.log('取消')
        }
      }
    });
  },

  getMoreOrders(){
    // 是否还有没查出来的order
    if (this.data.hasMoreOrder){
      var maxOrderId = this.data.lastOrderId;
      var self = this;
      var orders = this.data.orders;
      console.log("；来了" + maxOrderId);
      wx.login({
        success(res) {
          console.log(res);
          if (res.code) {
            wx.request({
              url: Config.rootUrl + '/order/get_customer_id_and_order_id',
              data: {
                code: res.code,
                maxOrderId: maxOrderId,
              },
              success(resNew) {
                // 查看订单是否为空
                if (resNew.data.orders.length > 0){
                  self.setData({
                    orders: self.data.orders.concat(resNew.data.orders),
                    lastOrderId: resNew.data.orderId,
                    hasMoreOrder : true,
                  })
                } else {
                  self.setData({
                    hasMoreOrder: false,
                    hiddenBottom : true,
                  })
                }
              }
            });
          } else {
            console.log('登录失败！' + res.errMsg)
          }
        }
      })

    }

  },
  
})