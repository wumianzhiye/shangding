// page/component/new-pages/user/address/address.js
var Config = require('../../utils/config.js');

Page({
  data:{
    address:{
      name:'',
      phone:'',
      detail:''
    }
  },
  onLoad(){
    var self = this;
    
    wx.getStorage({
      key: 'address',
      success: function(res){
        self.setData({
          address : res.data
        })
      }
    })
  },
  formSubmit(e){
    const value = e.detail.value;
    var name = value.name;
    var phone = value.name;
    var detail = value.detail;
    if (value.name && value.phone && value.detail){
      wx.setStorage({
        key: 'address',
        data: value,
        success(){
          wx.navigateBack();
        }
      })
      wx.login({
        success(res) {
          console.log(res);
          if (res.code) {
            wx.request({
              url: Config.rootUrl + '/customer/address/add',
              method: 'POST',
              header: {
                'content-type': 'application/x-www-form-urlencoded' 
              },
              data: {
                code: res.code,
                name: name,
                phone: phone,
                destination: detail,
              }

            })

          } else {
            console.log('登录失败！' + res.errMsg)
          }
        }
      })
     
    }else{
      wx.showModal({
        title:'提示',
        content:'请填写完整资料',
        showCancel:false
      })
    }
  }
})