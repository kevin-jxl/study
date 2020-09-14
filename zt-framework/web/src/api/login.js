import request from './api'
export function loginCode(params) {
    return request({
      url: '/code',
      method: 'get',
      params: params
    })
  }
export const loginByUsername = (username, password, code, randomStr) => {
    var grant_type = 'password'
    var scope = 'server'
    return request({
        url: '/auth/oauth/token',
        headers: {
            //'Authorization': 'Basic cGlnOnBpZw=='
            //'Authorization': 'Basic dHVubnk6dHVubnk='
            'Authorization': 'Basic c3Bhcms6c3Bhcms='
        },
        method: 'post',
        params: { username, password, randomStr, code, grant_type, scope }
    })
}

export const getUserInfo = () => {
    return request({
        url: '/admin/user/login',
        method: 'get'
    })
}

export const logout = () => {
    return request({
        url: '/auth/oauth/removeToken',
        method: 'get'
    })
}
