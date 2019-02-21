// page/component/orders/orders.js
var Config = require('../../utils/config.js');

Page({
  data: {
    address: {},
    hasAddress: false,
    total: 0,
    orders: [],
    orderId: 0,
    status : 0,
  },

  //从服务器获取订单数据,onShow比onReady先执行
  onLoad(options) {
    this.setData ({
      orderId : options.orderId,
      status: options.status,
    });
    var orderId = options.orderId;
    var self=this;
    setTimeout(function () {//用延迟执行的方式避免因为事务冲突得到刚刚删除空的数据库而得不到数据
      wx.request({
        url: Config.rootUrl +'/cart/get_list_order_id',
        data:{
          orderId: orderId
        },
        success(res) {   
          console.log("orderList:" + res.data);       
          self.setData({
            orders: res.data
          }) 
          self.getTotalPrice();
        }
      });
    }, 500)
  },

  onShow: function () {
    const self = this;
    wx.getStorage({
      key: 'address',
      success(res) {
        self.setData({
          address: res.data,
          hasAddress: true
        })
      }
    });
  },

  /**
   * 计算总价
   */
  getTotalPrice() {
    let orders = this.data.orders;
    let total = 0;
    for (let i = 0; i < orders.length; i++) {
      total += orders[i].num * orders[i].goodPrice;
    }
    console.log("金额" + total)
    this.setData({
      total: total
    })
  },

  toPay() {
    wx.showModal({
      title: '提示',
      content: '本系统只做演示，支付系统已屏蔽',
      text: 'center',
      complete() {
        wx.switchTab({
          url: '/page/component/user/user'
        })
      }
    })
  }
})