import axios from 'axios'
import store from '../store/index.js';
import errorCode from './errorCode'
import { Message } from 'element-ui'
import router from '../router'
import {
    getToken,removeToken
} from '@/util/auth'

let base = config.services;
//axios基本配置
axios.defaults.baseURL = base;
let hasMassage = false
// 超时时间
axios.defaults.timeout = 30000
// 跨域请求，允许保存cookie
axios.defaults.withCredentials = true
let msg
// HTTPrequest拦截
axios.interceptors.request.use(config => {
    if (getToken()) {
        // console.log('toubu Token ',getToken())
        config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
    }
    return config
}, error => {
    return Promise.reject(error)
})
// HTTPresponse拦截
axios.interceptors.response.use(data => {
    return data
}, error => {
    let errMsg = error.toString()
    let code = errMsg.substr(errMsg.indexOf('code') + 5)
    if (code === '401' || code === '403') {
        if (error.response.config.url !== '/auth/oauth/token') {
            if (!hasMassage) {
                hasMassage = true
                Message({
                    message: '登录状态失效，即将跳转登录！',
                    type: 'error',
                    duration: 1000,
                    onClose: () => {
                        hasMassage = false
                        removeToken()
						sessionStorage.removeItem('userData')
                        router.push({ path: '/login' })
                    }
                    
                })
            }
        } else {
            Message({
                message: '用户名或密码错误！',
                type: 'error',
                duration: 3000,
            })
        }
    } else {
        Message({
            message: errorCode[code] || errorCode['default'],
            type: 'error'
        })
    }

    return Promise.reject(new Error(error))
})

export default axios


/**
 * 
 * 下面是原来的代码
 * date:20200819
 */

// axios.defaults.withCredentials = false;
// axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
// axios.defaults.headers.get['Content-Type'] = 'application/json;charset=UTF-8';


// //请求开始拦截
// axios.interceptors.request.use(conf => {
//     conf.headers['Authorization'] = sessionStorage.getItem('token')+'';
//     return conf
//   }, error => ({ status: 0, msg: error.message })
// )
// //请求返回拦截
// axios.interceptors.response.use(response => {
//   return Promise.resolve(response).then(checkCode)
// },error => {
//   return Promise.reject(error)
// })

// // 后台自定义 code错误处理
// const checkCode = (res) => {
//   if(res.data.code === 403){
//     console.log("error: ", res.data);
//     store.commit('updateMessage', res.data);
//     return false
//   }else{
//     if(res.data.code !== 200){
//       store.commit('updateMessage', res.data);
//     }
//     return res.data;
//   }
// }
// export default axios;



/**
 * 接口请求部分
 * @param param
 */

// export const postData = (param) => { return axios.get(`/posts`, {data: param}).then(res => res) }            //例：Mock数据的get 请求
//登录
export const login = (param) => { return axios.post(`/user/login`, param).then(res => res) }            //例：post 请求
export const logout = () => { return axios.get(`/user/logout`, {}).then(res => res) } // 退出登录请求

export const getDisplaceList = () => { return axios.get(`/monitor/displace`, {}).then(res => res) } // 退出登录请求

// 获取验证码
export const loginCode = () => { return axios.get(`/code?randomStr=`, {}).then(res => res) }

