import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/admin/student/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/admin/student/',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/admin/student/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/admin/student/' + id,
    method: 'delete'
  })
}

export function hello() {
  return request({
    url: '/admin/student/hello' //,
    //method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/admin/student/',
    method: 'put',
    data: obj
  })
}

export function delObjs(params) {
  return request({
    url: '/admin/student/delObjs',
    method: 'get',
    params: params
  })
}

//excel上传接口
export function uploadExcel() {
  return request({
    url: '/admin/student/uploadExcel',
    method: 'get'
  })
}
//excel下载接口
export function downloadExcel() {
  return request({
    url: '/admin/student/downloadExcel',
    method: 'get',
  })
}

