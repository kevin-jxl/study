import request from '@/router/axios'

export function fetchTree(query) {
  return request({
    url: '/admin/dept/tree',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/admin/dept/',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/admin/dept/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/admin/dept/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/admin/dept/',
    method: 'put',
    data: obj
  })
}

/**
　　* @Description: 根据部门编码获取部门下的子部门
　　* @param
　　* @return  
　　* @author lmh
　　* @date 2019/1/22 10:57 
　　*/
export function fetchList(query) {
  return request({
    url: '/admin/dept/page',
    method: 'get',
    params: query
  })
}
