import request from '../api'

export function fetchList(query) {
  return request({
    url: '/spark/companywork/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/spark/companywork/',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/spark/companywork/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/spark/companywork/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/spark/companywork/',
    method: 'put',
    data: obj
  })
}

export function delObjs(params) {
    return request({
        url: '/spark/companywork/delObjs',
        method:'get',
        params: params
    })
}

//excel上传接口
export function uploadExcel() {
    return request({
        url: '/spark/companywork/uploadExcel',
        method: 'get'
    })
}
//excel下载接口
export function downloadExcel() {
    return request({
        url: '/spark/companywork/downloadExcel',
        method: 'get',
    })
}
