import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/marketingpc/hzmpphotomanage/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/marketingpc/hzmpphotomanage/',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/marketingpc/hzmpphotomanage/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/marketingpc/hzmpphotomanage/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/marketingpc/hzmpphotomanage/',
    method: 'put',
    data: obj
  })
}

export function delObjs(params) {
    return request({
        url: '/marketingpc/hzmpphotomanage/delObjs',
        method:'get',
        params: params
    })
}

//excel上传接口
export function uploadExcel() {
    return request({
        url: '/marketingpc/hzmpphotomanage/uploadExcel',
        method: 'get'
    })
}
//excel下载接口
export function downloadExcel() {
    return request({
        url: '/marketingpc/hzmpphotomanage/downloadExcel',
        method: 'get',
    })
}
