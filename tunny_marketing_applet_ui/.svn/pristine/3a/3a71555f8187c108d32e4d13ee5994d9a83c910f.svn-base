import request from '@/router/axios'

export function fetchApiList(query) {
  return request({
    url: '/api/apiInfo/apiPage',
    method: 'get',
    params: query
  })
}
export function fetchApiAllList(query) {
  return request({
    url: '/api/apiInfo/all',
    method: 'get',
    params: query
  })
}

export function fetchApiInfo(apiId) {
  return request({
    url: `api/apiInfo/${apiId}/info`,
    method: 'get',
  })
}

export function doApiAdd(query) {
  return request({
    url: '/api/apiInfo/add',
    method: 'post',
    data: query
  })
}

export function doApiEdit(query) {
  return request({
    url: '/api/apiInfo/update',
    method: 'post',
    data: query
  })
}

export function doApiDel(query) {
  return request({
    url: '/api/apiInfo/remove',
    method: 'post',
    data: query
  })
}
