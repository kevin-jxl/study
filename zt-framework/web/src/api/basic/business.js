// 日志管理
import request from '../api'

export function fetchList(query) {  //表格列表
  return request({
    url: '/admin/log/logPage',
    method: 'get',
    params: query
  })
}
// http://localhost:8000/admin/log/logPage?page=1&limit=20&type=&startTime=&endTime=&orderByField=create_time&isAsc=false


export function deletes(ids) {  //用户批量删除
    return request({
        url: '/admin/log/delete',
        method: 'Delete',
        params: {
            ids: ids
        }
    })
  }

//导出
export function getDownloadExcel(obj) { 
    return request({
        url: '/admin/log/downloadExcel',
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
  