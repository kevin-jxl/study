// 用户管理
import request from '../api'  // params   --问号拼接的     data--body json数据

export function fetchList(query) {  //表格列表
  return request({
    url: '/admin/consumer/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) { //新增
  return request({
    url: '/admin/user/',
    method: 'post',
    data: obj
  })
}

export function getObj(userId) { //角色修改查询列表
    return request({
      url: '/admin/consumer/' + userId,
      method: 'get'
    })
  }


export function delObj(userId) { //用户删除
  return request({
    url: '/admin/user/' + userId,
    method: 'DELETE'
  })
}

export function putObj(obj) { //用户修改
  return request({
    url: '/admin/user/',
    method: 'put',
    data: obj
  })
}


export function getSyscompany(obj) { //企业下拉列表
    return request({
      url: '/admin/syscompany',
      method: 'get',
      params: obj
    })
}

export function getMechanism(obj) { //所属机构下拉列表
    return request({
      url: '/admin/sysadministrativeorgan/inputList',
      method: 'get',
      params: obj
    })
}

export function getRoleList(obj) { //角色下拉列表
    return request({
      url: '/admin/role/roleUserList',
      method: 'get',
      params: obj
    })
}

//所属机构
export function getSysserviceorga(obj) { //所属机构下拉列表
    return request({
      url: '/admin/sysserviceorgan/inputList',
      method: 'get',
      params: obj
    })
}

//导出
export function getDownloadExcel(obj) { 
    return request({
        url: '/admin/consumer/downloadExcel',
        method: 'get',
        params: obj,
        responseType: 'blob'
    }).then((response) => { // 处理返回的文件流
        let blob = new Blob([response.data], { type:   'application/xlsx' } )
        let link = document.createElement('a')
        link.href = window.URL.createObjectURL(blob)
        link.download = obj.tableName + '.xlsx'
        link.click()
    })
}
