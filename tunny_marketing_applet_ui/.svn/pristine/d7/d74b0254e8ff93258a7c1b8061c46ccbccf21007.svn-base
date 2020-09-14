import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/admin/syspost/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/admin/syspost/',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/admin/syspost/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/admin/syspost/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/admin/syspost/',
    method: 'put',
    data: obj
  })
}
  /**
   * 获取岗位列表
   */
  export function getPostList() {
    return request({
      url: '/admin/syspost/postList',
      method: 'get'
    })

}
