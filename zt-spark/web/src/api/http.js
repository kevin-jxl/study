import axios from 'axios';
import errorCode from './errorCode';
import { Message } from 'element-ui';
import router from '../router';
import { getToken, removeToken } from '@/util/auth';

let hasMassage = false
let base = config.services;

axios.defaults.baseURL = base;          //axios基本配置
axios.defaults.timeout = 30000;         // 超时时间
axios.defaults.withCredentials = true;  // 跨域请求，允许保存cookie

// HTTPrequest拦截
axios.interceptors.request.use(config => {
    if (getToken()) {
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
    }else {
        Message({
            message: errorCode[code] || errorCode['default'],
            type: 'error'
        })
    }
    return Promise.reject(new Error(error))
})

export default axios