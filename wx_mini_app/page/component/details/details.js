// page/component/details/details.js
var Config = require('../../utils/config.js');
Page({
  data: {
    goods: {//前端调用貌似不需要加，但网络请求调用的时候要加
      goodId: 0,//id没用到
      goodName: 'origin',
      goodPrice: '',
      goodMainUrl: '',
      goodDetailUrls: [],
      parameter: '产品参数相关',
      service: '不支持退货'
    },
    num: 1,
    totalNum: 0,
    hasCarts: false,
    curIndex: 0,
    show: false,
    scaleCart: false
  },


  //获取从首页或购物车传过来的数据,这样就可以取出json数组中里面的字符串再放到一个数组中，解决了首页就处理数组字符串的问题
  onLoad: function (options) {
    this.setData({
      goodId: options.good_id,
      goodName: options.good_name,
      goodPrice: options.good_price,
      goodMainUrl: options.good_main_url,
      goodDetailUrls: options.good_detail_urls.split(','),
    })
  },

  click: function (e) {//点击‘添加到购物车按钮’：网络请求向数据库中存入购物车信息  
    var goodId = this.data.goodId;
    wx.login({
      success(res) {
        console.log(res);
        if (res.code) {
          wx.request({
            url: Config.rootUrl + "/cart/add",
            method: 'POST',
            header: {
              'content-type': 'application/x-www-form-urlencoded' // 默认值
            },
            data: {
              code: res.code,
              goodId: goodId,
            },
            success: function (resNew) {
              wx.showToast({
                title: '已添加到购物车',
                icon: 'success',
                duration: 500,
                mask: true//防止触摸穿透
              })
            },
            fail: function (err) {
              wx.showToast({
                title: '添加购物车失败，请重试',
                icon: 'success',
                duration: 2000,
                mask: true//防止触摸穿透
              })
            }
          });
        }
      }
    })
  },

  addCount() {
    let num = this.data.num;
    num++;
    this.setData({
      num: num
    })
  },

  // addToCart() {
  //   const self = this;
  //   const num = this.data.num;
  //   let total = this.data.totalNum;

  //   self.setData({
  //     show: true
  //   })
  //   setTimeout(function () {
  //     self.setData({
  //       show: false,
  //       scaleCart: true
  //     })
  //     setTimeout(function () {
  //       self.setData({
  //         scaleCart: false,
  //         hasCarts: true,
  //         totalNum: num + total
  //       })
  //     }, 200)
  //   }, 300)

  // },

  bindTap(e) {
    const index = parseInt(e.currentTarget.dataset.index);
    this.setData({
      curIndex: index
    })
  }

})