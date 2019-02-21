// page/component/list/list.js
var Config = require('../../utils/config.js');

Page({
  data: {
    all_goods: [],
    searchValue: '',
    search_goods:[],
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    this.setData({
      searchValue: options.searchValue,
    })
  },
  onReady: function () {
    var searchValueNew = this.data.searchValue;
    var search_goods = [];
    // 页面渲染完成
    var self = this;
    wx.request({
      url: Config.rootUrl +'/good/get_all',
      success(res) {
        for(var i = 0;i< res.data.length;i++){
          console.log(res.data[i].goodName);
          if (res.data[i].goodName.indexOf(searchValueNew) >= 0){
            search_goods.push(res.data[i]);
          }
        }
        self.setData({
          all_goods: res.data,
          search_goods: search_goods,
         
        });
      }
    });
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  },

  searchInput: function (e) {
    this.setData({
      searchValue: e.detail.value
    })
  },

  suo: function(e){
    var searchValueNew = this.data.searchValue;
    console.log("哈哈"+searchValueNew);
    if (searchValueNew === undefined || searchValueNew === ''){
      wx.showToast({
        title: '请输入关键字搜索商品',
        duration: 2000,
        mask: true//防止触摸穿透
      })
      return;
    }
    var search_goods = [];
    var allGoods = this.data.all_goods;
    for (var i = 0; i < allGoods.length; i++) {
      if (allGoods[i].goodName.indexOf(searchValueNew) >= 0) {
        search_goods.push(allGoods[i]);
      }
    }
    this.setData({
      search_goods: search_goods,    
    });
  }
})