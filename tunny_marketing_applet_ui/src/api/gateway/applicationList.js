import request from '@/router/axios'

export function fetchApplicationList(query) {
  return request({
    url: '/api/app//appPage',
    method: 'get',
    params: query
  })
}

export function doApplicationAdd(query) {
  return request({
    url: '/api/app/add',
    method: 'post',
    data: query
  })
}

export function fetchApplicationInfo(appId) {
  return request({
    url: `api/app/${appId}/info`,
    method: 'get',
  })
}

export function doApplicationReset(query) {
  return request({
    url: '/api/app/reset',
    method: 'post',
    data: query
  })
}

export function doApplicationEdit(query) {
  return request({
    url: '/api/app//update',
    method: 'post',
    data: query
  })
}

export function doApplicationDel(query) {
  return request({
    url: '/api/app/remove',
    method: 'post',
    data: query
  })
}

export function fetchApplicationDevelopInfo(appId) {
  return request({
    url: `api/app/client/${appId}/info`,
    method: 'get',
  })
}

export function doApplicationDevelopEdit(query) {
  return request({
    url: '/api/app/client/update',
    method: 'post',
    data: query
  })
}

export function fetchApplicationFuncList(query) {
  return request({
    url: '/api/app/authority/app',
    method: 'get',
    params: query
  })
}

export function doApplicationFuncEdit(query) {
  return request({
    url: '/api/app/authority/app/grant',
    method: 'post',
    data: query
  })
}
