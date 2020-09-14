import request from '../api'
console.log(request)

//区域列表
export function areaList(query) {
  return request({
    url: '/admin/sysadministrativearea/page',
    method: 'get',
    params: query
  })
}
//新增区域
export function addArea(obj) {
  return request({
    url: '/admin/sysadministrativearea',
    method: 'post',
    data: obj
  })
}
//区域编辑
export function detailArea(id) {
  return request({
    url: '/admin/sysadministrativearea/'+id,
    method: 'get',
  })
}
//区域修改
export function updateArea(obj) {
  return request({
    url: '/admin/sysadministrativearea',
    method: 'put',
    data: obj
  })
}
//区域删除
export function deleteArea(id) {
  return request({
    url: '/admin/sysadministrativearea/'+id,
    method: 'delete',
  })
}

export function downloadExcel(obj,name) {
	return request({
		url: '/admin/sysadministrativearea/downloadExcel',
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



