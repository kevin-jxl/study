
import request from '@/router/axios'

export function permissionSearch(query) {
  return request({
    url: '/admin/syspowerlabel/page',
    method: 'get',
    params: query
  })
}

export function permissionPost(obj) {
  return request({
    url: '/admin/syspowerlabel',
    method: 'post',
    data: obj
  })
}

export function permissionMenuSearch() {
  return request({
    url: '/admin/menu/getMenuByType',
    method: 'get',
    params: {
      menuType:0
    }
  })
}

export function permissionUpdate(params) {
  return request({
    url: '/admin/syspowerlabel',
    method: 'put',
    data: params
  })
}

export function permissionById(id) {
  return request({
    url: `/admin/syspowerlabel/${id}`,
    method: 'get'
  })
}

export function permissionDel(id) {
  return request({
    url: `/admin/syspowerlabel/${id}`,
    method: 'delete'
  })
}

export function deptSearch(query) {
  return request({
    url: '/admin/sysposdummy/page',
    method: 'get',
    params: query
  })
}

export function deptPost(obj) {
  return request({
    url: '/admin/sysposdummy',
    method: 'post',
    data: obj
  })
}

export function deptUpdate(params) {
  return request({
    url: '/admin/sysposdummy',
    method: 'put',
    data: params
  })
}

export function deptById(id) {
  return request({
    url: `/admin/sysposdummy/${id}`,
    method: 'get'
  })
}

export function deptDel(id) {
  return request({
    url: `/admin/sysposdummy/${id}`,
    method: 'delete'
  })
}

export function deptTagsSearch(query) {
  return request({
    url: '/admin/syspowerlabel/sysPowerLabelList',
    method: 'get',
    params: query
  })
}

export function xnpostTagsSearch(query) {
  return request({
    url: '/admin/sysposdummy/sysPosdummyList',
    method: 'get',
    params: query
  })
}

export function deptTagsUpdate(params) {
  return request({
    url: '/admin/sysposdummy/posDummyPowers',
    method: 'get',
    params: params
  })
}
