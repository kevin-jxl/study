import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/marketingpc/hzmpcomment/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/marketingpc/hzmpcomment/',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/marketingpc/hzmpcomment/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/marketingpc/hzmpcomment/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/marketingpc/hzmpcomment/',
    method: 'put',
    data: obj
  })
}

export function delObjs(params) {
    return request({
        url: '/marketingpc/hzmpcomment/delObjs',
        method:'get',
        params: params
    })
}

//excel上传接口
export function uploadExcel() {
    return request({
        url: '/marketingpc/hzmpcomment/uploadExcel',
        method: 'get'
    })
}
//excel下载接口
export function downloadExcel() {
    return request({
        url: '/marketingpc/hzmpcomment/downloadExcel',
        method: 'get',
    })
}
