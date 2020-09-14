
import request from '../api'

export function roleList() {
  return request({
    url: '/admin/role/roleList',
    method: 'get'
  })
}

export function fetchList(query) {   // 角色分页查询
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

export function getObj(id) { //角色修改查询列表
  return request({
    url: '/admin/role/' + id,
    method: 'get'
  })
}

export function addObj(obj) {  //角色新增
  return request({
    url: '/admin/role',  
    method: 'post',
    data: obj
  })
}

export function putObj(obj) { //角色修改
  return request({
    url: '/admin/role',
    method: 'post',
    data: obj
  })
}

export function putObjs(obj) { //修改
    return request({
        url: '/admin/role',
        method: 'put',
        data: obj
    })
  }
  

export function delObj(id) { //角色删除
  return request({
    url: '/admin/role/' + id,
    method: 'delete'
  })
}

export function permissionUpd(roleId, menuIds,menuName) {  //角色权限修改
  return request({
    url: '/admin/role/roleMenuUpd',
    method: 'put',
    params: {
        roleId: roleId,
        menuIds: menuIds,
		menuName:menuName
    }
  })
}


export function getCurrentId(roleId) { //角色权限默认选中
    return request({
        url: '/admin/role/selectListRoleId/',
        method: 'get',
        params: {
            roleId: roleId
        }
    })
}

export function fetchRoleTree(roleCode) {  //角色菜单树
  return request({
    url: '/admin/menu/roleTree/' + roleCode,
    method: 'get'
  })
}

