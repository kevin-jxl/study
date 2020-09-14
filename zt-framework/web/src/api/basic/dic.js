import request from '../api'

//字典列表
export function dicList(query) {
  return request({
    url: '/admin/sysdictindex/page',
    method: 'get',
    params: query
  })
}
//字典区域
export function addDic(obj) {
  return request({
    url: '/admin/sysdictindex',
    method: 'post',
    data: obj
  })
}

//字典删除
export function deleteDic(id) {
  return request({
    url: '/admin/sysdictindex/'+id,
    method: 'delete',
  })
}

//字典对照列表
export function selectDictsList(query) {
    return request({
        url: 'admin/sysdict/selectDicts',
        method: 'get',
        params: query
    })
}

//字典对照列表删除
export function deleteSysdict(id) {
    return request({
        url: '/admin/sysdict/'+id,
        method: 'delete',
    })
  }


//字典对照列表添加
export function addSysdict(obj) { //新增
    return request({
        url: '/admin/sysdict',
        method: 'post',
        data: obj
    })
}

//对照表编辑
export function updatesDic(id) {
    return request({
        url: '/admin/sysdict/',
        method: 'put',
        data: id
    })
}
//导出
export function getDownloadExcel(obj) { 
    return request({
        url: '/admin/sysdictindex/downloadExcel',
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

