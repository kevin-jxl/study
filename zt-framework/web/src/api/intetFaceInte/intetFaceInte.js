// 接口集成
import request from '../api'  // params   --问号拼接的     data--body json数据

export function fetchList(query) {  //表格列表
  return request({
    url: '/gen/generator/page',
    method: 'get',
    params: query
  })
}

export function generates(obj) { //新增
  return request({
    url: '/gen/generator/code',
    method: 'post',
    data: obj,
    responseType: 'blob'
  })
}

export function areaList(query) {  //系统集成管理表格列表
    return request({
        url: '/admin/integration/page',
        method: 'post',
        data: query
    })
}

export function addObj(obj) { //系统集成管理新增
    return request({
        url: '/admin/integration/',
        method: 'post',
        data: obj
    })
}
  
export function delObj(systemId) { //系统集成管理删除
    return request({
        url: '/admin/integration/' + systemId,
        method: 'DELETE'
    })
}

export function getObj(systemId) { //修改详情
    return request({
        url: '/admin/integration/' + systemId,
        method: 'get'
    })
}

export function putObj(obj) { //系统集成管理修改
    return request({
        url: '/admin/integration/',
        method: 'put',
        data: obj
    })
}

export function interfaceList(query) {  //接口管理表格列表
    return request({
        url: '/admin/interface/page',
        method: 'post',
        data: query
    })
}

export function interfaceAdd(obj) { //接口管理新增
    return request({
        url: '/admin/interface/',
        method: 'post',
        data: obj
    })
}

export function interfaceIdInfo(interfaceId) { //接口管理修改详情
    return request({
        url: '/admin/interface/' + interfaceId,
        method: 'get'
    })
}

export function integrationUpdate(obj) { //接口管理修改
    return request({
        url: '/admin/interface/',
        method: 'put',
        data: obj
    })
}

export function integrationDel(interfaceId) { //接口管理删除
    return request({
        url: '/admin/interface/' + interfaceId,
        method: 'DELETE'
    })
}

export function getDownloadExcel(obj,url) { 
    return request({
        url: url,
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
