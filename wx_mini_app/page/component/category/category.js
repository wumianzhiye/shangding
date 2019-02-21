var Config = require('../../utils/config.js');
// import Config from '../../utils/config.js'
Page({
  data: {
    category: [
      { name: '茅台一号', id: 'tixu' },
      { name: '茅台二号', id: 'qunzi' },
      { name: '商鼎', id: 'shangding' }
    ],
    detail: [],
    curIndex: 0,
    isScroll: false,
    toView: 'tixu'/*默认定位到哪个view*/
  },


  onReady() {
    var self = this;
    wx.request({
      url: Config.rootUrl+'/category/get_all', success(res) {
        console.log(res.data)
        self.setData({
          detail: res.data
        })
      }
    });
  },
  switchTab(e) {
    const self = this;
    this.setData({
      isScroll: true
    })
    setTimeout(function () {
      self.setData({
        toView: e.target.dataset.id,
        curIndex: e.target.dataset.index
      })
    }, 0)
    setTimeout(function () {
      self.setData({
        isScroll: false
      })
    }, 1)

  }

})