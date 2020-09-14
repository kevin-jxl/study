import request from '../api'


//业务机构列表
export function mechanismList(query) {
  return request({
    url: '/admin/sysserviceorgan/page',
    method: 'get',
    params: query
  })
}
//业务机构区域
export function addMechanism(obj) {
  return request({
    url: '/admin/sysserviceorgan',
    method: 'post',
    data: obj
  })
}
//业务机构编辑
export function detailMechanism(id) {
  return request({
    url: '/admin/sysserviceorgan/'+id,
    method: 'get',
  })
}
//业务机构修改
export function updateMechanism(obj) {
  return request({
    url: '/admin/sysserviceorgan',
    method: 'put',
    data: obj
  })
}
//业务机构删除
export function deleteMechanism(id) {
  return request({
    url: '/admin/sysserviceorgan/'+id,
    method: 'delete',
  })
}
//车场下拉接口
export function selectMechanism(query) {
  return request({
    url: '/admin/sysadministrativeorgan/list',
    method: 'get',
	params: query
  })
}
export function downloadExcel(obj,name) {
	return request({
		url: '/admin/sysserviceorgan/downloadExcel',
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