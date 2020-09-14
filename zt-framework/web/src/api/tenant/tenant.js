import request from '../api'


//租户列表
export function tenantList(query) {
  return request({
    url: '/admin/tenant/page',
    method: 'get',
    params: query
  })
}
//新增租户
export function addTenant(obj) {
  return request({
    url: '/admin/tenant',
    method: 'post',
    data: obj
  })
}
//租户查看
export function detailTenant(id) {
  return request({
    url: '/admin/tenant/'+id,
    method: 'get',
  })
}
//租户修改
export function updateTenant(obj) {
  return request({
    url: '/admin/tenant',
    method: 'put',
    data: obj
  })
}
//租户删除
export function deleteTenant(id) {
  return request({
    url: '/admin/tenant/'+id,
    method: 'delete',
  })
}
//区域下拉
export function selectAreaTenant(query) {
  return request({
    url: '/admin/sysadministrativearea/',
    method: 'get',
	params: query
  })
}

//查询用户
export function userList(obj) {
  return request({
    url: '/admin/user/adminList',
    method: 'post',
    data: obj
  })
}

export function downloadExcel(obj,name) {
	return request({
		url: '/admin/tenant/downloadExcel',
		method: 'get',
		params: obj,
		responseType: 'blob'
	}).then((response) => { // 处理返回的文件流
	console.log(response)
          let blob = new Blob([response.data], { type:'application/xlsx' } )
          let link = document.createElement('a')
          link.href = window.URL.createObjectURL(blob)
          link.download = name+'.xlsx'
          link.click()
      })
}