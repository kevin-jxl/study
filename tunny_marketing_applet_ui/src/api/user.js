import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/admin/user/userPage',
    method: 'get',
    params: query
  })
}

export function sendAuthCode(query) {
  return request({
    url: '/admin/user/sendAuthCode',
    method: 'get',
    params: query
  })
}


export function contrastAuthCode(query) {
  return request({
    url: '/admin/user/contrastAuthCode',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/admin/user/',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/admin/user/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/admin/user/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/admin/user',
    method: 'put',
    data: obj
  })
}

export function sendLoginCode(params) {
  return request({
    url: '/admin/account/sendAuthCode',
    method: 'get',
    params: params
  })
}

export function checkLoginWithCode(params) {
  return request({
    url: '/admin/account/contrastAuthCode',
    method: 'get',
    params: params
  })
}

export function resetPassword(params) {
  return request({
    url: '/admin/account/editPassword',
    method: 'put',
    data: params
  })
}

export function userConDept(params) {
  return request({
    url: '/admin/user/userPosDummy',
    method: 'get',
    params: params
  })
}

export function getRandomImg(query) {
  return request({
    url: '/code',
    method: 'get',
    responseType: 'arraybuffer',
    params: query
  })
}

