import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/daemon/statustracelog/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/daemon/statustracelog/',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/daemon/statustracelog/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/daemon/statustracelog/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/daemon/statustracelog/',
    method: 'put',
    data: obj
  })
}
