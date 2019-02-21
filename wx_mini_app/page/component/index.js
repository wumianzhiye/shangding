var Config = require('../utils/config.js');

Page({
  data: {
    imgUrls: [
      '/image/b1.jpg',
      '/image/b2.jpg',
      '/image/b3.jpg'
    ],
    indicatorDots: false,
    autoplay: false,
    odd_goods: ["nae", "john"],
    even_goods: [],
    title_goods:[],
    new_even: "jjfdsafsdafsdafasf",
    interval: 3000,
    duration: 800,
    searchInput : "",
  },

  onReady() {//获取奇数商品详情    
    this.setData({
      //new_even:this.data.new_even.substr(1,6)
      //new_even: "aabybccddeeffgghhii".substr(3,6)       
    })
    var self = this;
    wx.request({
      url: Config.rootUrl+'/good/get_all_odd',
      success(res) {
        self.setData({
          odd_goods: res.data,
          //new_even:res.data[2].goodName.substr(3,6)//good      
        });
      }
    });
    wx.request({
      url: Config.rootUrl +'/good/get_all_even',
      success(res) {
        self.setData({
          even_goods: res.data,
        });
      },
    });
    wx.request({
      url: Config.rootUrl + '/good/get_title',
      success(res) {
        self.setData({
          title_goods: res.data,
        });
      },
    });
  },
  // 页面搜索功能
  suo: function (e) {
    if (this.data.searchInput === undefined){
      wx.showToast({
        title: '请输入关键字搜索商品',
        duration: 2000,
        mask: true//防止触摸穿透
      })
      return;
    }
    wx.navigateTo({
      url: 'list/list?searchValue=' + this.data.searchInput,
    })
  },
  searchInput: function (e) {
    this.setData({
      searchInput: e.detail.value
    })
  }

})