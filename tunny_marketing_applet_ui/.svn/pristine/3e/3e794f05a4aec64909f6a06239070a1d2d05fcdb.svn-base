
/**
 *
 * http配置
 *
 */

import axios from 'axios'
import store from '../store'
import router from '../router/router'
import { getToken, setToken, removeToken } from '@/util/auth'
import { Message } from 'element-ui'
import errorCode from '@/const/errorCode'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style

let hasMassage = false
// 超时时间
axios.defaults.timeout = 30000
// 跨域请求，允许保存cookie
axios.defaults.withCredentials = true
NProgress.configure({ showSpinner: false })// NProgress Configuration
let msg
// HTTPrequest拦截
axios.interceptors.request.use(config => {
  NProgress.start() // start progress bar
  if (store.getters.access_token) {
    config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
  }
  return config
}, error => {
  return Promise.reject(error)
})
// HTTPresponse拦截
axios.interceptors.response.use(data => {
  NProgress.done()
  return data
}, error => {
  NProgress.done()
  let errMsg = error.toString()
  let code = errMsg.substr(errMsg.indexOf('code') + 5)
  if(code === '401' || code === '403'){
    if(error.response.config.url!=='/auth/oauth/token'){
      if(!hasMassage){
        hasMassage = true
        Message({
          message: '登录状态失效，即将跳转登录！',
          type: 'error',
          duration:3000,
          onClose:()=>{
            store.dispatch('FedLogOut')
            hasMassage = false
            router.push({path:'/login'})
          }
        })
      }
    }else{
      Message({
        message: '用户名或密码错误！',
        type: 'error',
        duration:3000,
      })
    }
  }else{
    Message({
      message: errorCode[code] || errorCode['default'],
      type: 'error'
    })
  }

  return Promise.reject(new Error(error))
})

export default axios
