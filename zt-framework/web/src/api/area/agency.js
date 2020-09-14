import request from '../api'


//机构列表
export function agencyList(query) {
  return request({
    url: '/admin/sysadministrativeorgan/page',
    method: 'get',
    params: query
  })
}
//机构增加
export function addAgency(obj) {
  return request({
    url: '/admin/sysadministrativeorgan',
    method: 'post',
    data: obj
  })
}
//机构编辑
export function detailAgency(id) {
  return request({
    url: '/admin/sysadministrativeorgan/'+id,
    method: 'get',
  })
}
//机构修改
export function updateAgency(obj) {
  return request({
    url: '/admin/sysadministrativeorgan',
    method: 'put',
    data: obj
  })
}
//机构删除
export function deleteAgency(id) {
  return request({
    url: '/admin/sysadministrativeorgan/'+id,
    method: 'delete',
  })
}
//所属企业
export function syscompany(query) {
  return request({
    url: '/admin/syscompany/',
    method: 'get',
	params: query
  })
}
//所属区域
export function sysadministrativearea(query) {
  return request({
    url: '/admin/sysadministrativearea/',
    method: 'get',
	params: query
  })
}
//导出
export function downloadExcel(obj,name) {
	return request({
		url: '/admin/sysadministrativeorgan/downloadExcel',
		method: 'get',
		params: obj,
		responseType: 'blob'
	}).then((response) => { // 处理返回的文件流
	console.log(response)
          let blob = new Blob([response.data], { type:'application/xlsx' } )
          let link = document.createElement('a')
          link.href = window.URL.createObjectURL(blob)
          link.download = name + '.xlsx'
          link.click()
      })
}
