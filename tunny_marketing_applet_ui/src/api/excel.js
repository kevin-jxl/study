import request from '@/router/axios'

export function postObj(obj) {
  return request({
    url: '/admin/excelup/doImport',
    method: 'post',
    data: obj
  })
}
