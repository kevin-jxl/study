import request from '@/router/axios'
export const loginByUsername = (username, password, code, randomStr) => {
    var grant_type = 'password'
    var scope = 'server'
    return request({
        url: '/auth/oauth/token',
        headers: {
            //'Authorization': 'Basic cGlnOnBpZw=='
          // 'Authorization': 'Basic dHVubnk6dHVubnk='
          'Authorization': 'Basic c3Bhcms6c3Bhcms='
        },
        method: 'post',
        params: { username, password, randomStr, code, grant_type, scope }
    })
}

export const loginBySocial = (state, code) => {
    var grant_type = 'mobile'
    return request({
        url: '/auth/mobile/token',
        headers: {
           // 'Authorization': 'Basic cGlnOnBpZw=='
          // 'Authorization': 'Basic dHVubnk6dHVubnk='
          'Authorization': 'Basic c3Bhcms6c3Bhcms='
        },
        method: 'post',
        params: { mobile: state + '@' + code, grant_type }
    })
}

export const getUserInfo = () => {
    return request({
        url: '/admin/user/info',
        method: 'get'
    })
}

export const logout = () => {
    return request({
        url: '/auth/oauth/removeToken',
        method: 'get'
    })
}
