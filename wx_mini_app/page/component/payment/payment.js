// page/component/orders/orders.js
var Config = require('../../utils/config.js');

Page({
  data: {
    address: {},
    hasAddress: false,
    total: 0,
    carts: [],
    packageSelectCartIds : 0,
    packageSelectGoodIdNum : 0,
  },

  //从服务器获取订单数据,onShow比onReady先执行
  onLoad(options) {
    this.setData({
      carts: JSON.parse(options.carts),
      packageSelectCartIds: options.packageSelectCartIds,
      packageSelectGoodIdNum: options.packageSelectGoodIdNum,
    });
    console.log('carts=' + options.carts);
    console.log('packageSelectCartIds=' + options.packageSelectCartIds);
    console.log('packageSelectGoodIdNum=' + options.packageSelectGoodIdNum);

    this.getTotalPrice();
    // var orderId = options.orderId;
    // var self = this;
    // setTimeout(function () {//用延迟执行的方式避免因为事务冲突得到刚刚删除空的数据库而得不到数据
    //   wx.request({
    //     url: Config.rootUrl + '/order/get_by_id',
    //     data: {
    //       orderId: orderId
    //     },
    //     success(res) {
    //       console.log("orderList:" + res.data);
    //       self.setData({
    //         orders: res.data
    //       })
    //       self.getTotalPrice();
    //     }
    //   });
    // }, 2000)
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
    let carts = this.data.carts;
    let total = 0;
    for (let i = 0; i < carts.length; i++) {
      total += carts[i].num * carts[i].goodPrice;
    }
    console.log("金额" + total)
    this.setData({
      total: total
    })
  },

  toPay() {
    // 先判断是否有地址
    if(this.data.hasAddress){



    } else{
      wx.showModal({
        title: '提示',
        content: '请选择地址',
        text: 'center',
      })
      return;
    }

    // 先生成订单
    var packageSelectCartIds = this.data.packageSelectCartIds; // 选择的购物车id，逗号隔开
    var packageSelectGoodIdNum = this.data.packageSelectGoodIdNum; //选择的购物车goodid-num；goodid-num
    var addressName = this.data.address.name; // 收货人
    var addressPhone = this.data.address.phone; // 电话
    var addressDetail = this.data.address.detail; // 详细地址
    wx.login({
      success(res) {
        console.log(res);
        if (res.code) {
          wx.request({
            url: Config.rootUrl + '/order/add',
            header: {
              'content-type': 'application/x-www-form-urlencoded'
            },
            method: "POST",
            data: {
              code: res.code,
              packageSelectCartIds: packageSelectCartIds,
              packageSelectGoodIdNum: packageSelectGoodIdNum, 
              addressName : addressName,
              addressPhone: addressPhone,
              addressDetail: addressDetail,
            },//不要用双引号，后台会错，暂时解决不了
            success: function (resNew) {
              // wx.navigateTo({
              //   url: '../payment/payment?orderId=' + resNew.data,
              // })
            }
          });

        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
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