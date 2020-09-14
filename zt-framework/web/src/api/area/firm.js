import request from '../api'


//企业列表
export function firmList(query) {
  return request({
    url: '/admin/syscompany/page',
    method: 'get',
    params: query
  })
}
//企业增加
export function addFirm(obj) {
  return request({
    url: '/admin/syscompany',
    method: 'post',
    data: obj
  })
}
//企业编辑
export function detailFirm(id) {
  return request({
    url: '/admin/syscompany/'+id,
    method: 'get',
  })
}
//企业修改
export function updateFirm(obj) {
  return request({
    url: '/admin/syscompany',
    method: 'put',
    data: obj
  })
}
//企业删除
export function deleteFirm(id) {
  return request({
    url: '/admin/syscompany/'+id,
    method: 'delete',
  })
}
//所属企业
export function syscompany() {
  return request({
    url: '/admin/syscompany/',
    method: 'get',
  })
}
//开启关闭状态
export function statusFirm(obj) {
  return request({
    url: '/admin/syscompany/status',
    method: 'put',
    data: obj
  })
}
//企业增加
export function tenantList(obj) {
  return request({
    url: '/admin/tenant/list',
    method: 'post',
    data: obj
  })
}

export function downloadExcel(obj,name) {
	return request({
		url: '/admin/syscompany/downloadExcel',
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