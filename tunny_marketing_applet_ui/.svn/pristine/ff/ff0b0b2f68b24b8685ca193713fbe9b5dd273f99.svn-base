
import request from '@/router/axios'

export function roleList() {
  return request({
    url: '/admin/role/roleList',
    method: 'get'
  })
}

export function fetchList(query) {
  return request({
    url: '/admin/role/rolePage',
    method: 'get',
    params: query
  })
}
//根据部门获取角色
export function deptRoleList(deptId) {
  return request({
    url: '/admin/role/roleList/' + deptId,
    method: 'get'
  })
}
//根据岗位获取角色
export function deptRoleListByPost(postId) {
  return request({
    url: '/admin/role/roleListByPostId/' + postId,
    method: 'get'
  })
}

export function getObj(id) {
  return request({
    url: '/admin/role/' + id,
    method: 'get'
  })
}

export function addObj(obj) {
  return request({
    url: '/admin/role',
    method: 'post',
    data: obj
  })
}

export function putObj(obj) {
  return request({
    url: '/admin/role',
    method: 'put',
    data: obj
  })
}

export function delObj(id) {
  return request({
    url: '/admin/role/' + id,
    method: 'delete'
  })
}

export function permissionUpd(roleId, menuIds) {
  return request({
    url: '/admin/role/roleMenuUpd',
    method: 'put',
    params: {
      roleId: roleId,
      menuIds: menuIds
    }
  })
}

export function fetchRoleTree(roleName) {
  return request({
    url: '/admin/menu/roleTree/' + roleName,
    method: 'get'
  })
}

export function fetchDeptTree(query) {
  return request({
    url: '/admin/dept/tree',
    method: 'get',
    params: query
  })
}

export function roleListAll() {
  return request({
    url: '/admin/role/roleListAll',
    method: 'get'
  })
}
export function selectRoledataPower(roleId) {
  return request({
    url: '/admin/role/selectRoledataPower',
    method: 'get',
    params: {
      roleId: roleId
    }
  })
}

export function updateRoledataPower(roleId,dsType,dsScope) {
  return request({
    url: '/admin/role/updateRoledataPower',
    method: 'get',
    params: {
      roleId: roleId,
      dsType: dsType,
      dsScope: dsScope
    }
  })
}
