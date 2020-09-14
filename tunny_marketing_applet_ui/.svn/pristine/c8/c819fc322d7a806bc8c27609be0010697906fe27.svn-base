import request from '@/router/axios'

export function fetchStrategyList(query) {
  return request({
    url: '/api/gateway/limit/ip/ipPage',
    method: 'get',
    params: query
  })
}
export function fetchStrategyApiList(query) {
  return request({
    url: '/api/gateway/limit/ip/list',
    method: 'get',
    params: query
  })
}

export function doStrategyAdd(query) {
  return request({
    url: '/api/gateway/limit/ip/add',
    method: 'post',
    data: query
  })
}

export function doStrategyApiAdd(query) {
  return request({
    url: '/api/gateway/limit/ip/api/add',
    method: 'post',
    data: query
  })
}

export function fetchStrategyInfo(apiId) {
  return request({
    url: `api/gateway/limit/ip/${apiId}/info`,
    method: 'get',
  })
}

export function doStrategyEdit(query) {
  return request({
    url: '/api/gateway/limit/ip/update',
    method: 'post',
    data: query
  })
}

export function doStrategyDel(query) {
  return request({
    url: '/api/gateway/limit/ip/remove',
    method: 'post',
    data: query
  })
}
