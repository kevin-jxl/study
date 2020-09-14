import request from '../api'

//角色菜单树
export function fetchRoleTree(roleCode) {
  return request({
      url: '/admin/menu/roleTree/' + roleCode,
      method: 'get'
    })
}
//租户列表
export function tenantList(query) {
  return request({
    url: '/admin/tenant/page',
    method: 'get',
    params: query
  })
}
//租户查看
export function detailTenant(id) {
  return request({
    url: '/admin/tenant/'+id,
    method: 'get',
  })
}
//角色菜单树保存
export function roleMenuUpd(roleId, menuIds) {  //角色权限修改
  return request({
    url: '/admin/role/roleMenuUpd',
    method: 'put',
    params: {
        roleId: roleId,
        menuIds: menuIds
    }
  })
}

export function tenantOrgan(tenantId) {
	return request({
		url: '/admin/tenant/organ/' + tenantId,
		method: 'get'
	})
}
//人员列表
export function userList(obj) {
  return request({
    url: '/admin/user/tenantUserList',
    method: 'post',
    data: obj
  })
}
//角色菜单选择数组
export function selectListRoleId(query) {
  return request({
    url: '/admin/role/selectListRoleId',
    method: 'get',
    params: query
  })
}