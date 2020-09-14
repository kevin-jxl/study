import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/marketingpc/hzmpbookingroom/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/marketingpc/hzmpbookingroom/',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/marketingpc/hzmpbookingroom/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/marketingpc/hzmpbookingroom/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/marketingpc/hzmpbookingroom/',
    method: 'put',
    data: obj
  })
}

export function delObjs(params) {
    return request({
        url: '/marketingpc/hzmpbookingroom/delObjs',
        method:'get',
        params: params
    })
}

//excel上传接口
export function uploadExcel() {
    return request({
        url: '/marketingpc/hzmpbookingroom/uploadExcel',
        method: 'get'
    })
}
//excel下载接口
export function downloadExcel() {
    return request({
        url: '/marketingpc/hzmpbookingroom/downloadExcel',
        method: 'get',
    })
}
