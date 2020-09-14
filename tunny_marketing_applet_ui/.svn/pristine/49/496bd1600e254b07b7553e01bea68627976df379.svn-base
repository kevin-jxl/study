/**
 * 定时任务job的API
 */
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/admin/job/jobPage',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/admin/job/',
    method: 'post',
    data: obj
  })
}
//暂停job
export function pausejob(obj) {
  return request({
    url: '/admin/job/pausejob',
    method: 'post',
    data: obj
  })
}
//恢复job
export function resumejob(obj) {
  return request({
    url: '/admin/job/resumejob',
    method: 'post',
    data: obj
  })
}
//更新job
export function rescheduleJob(obj) {
  return request({
    url: '/admin/job/reschedulejob',
    method: 'post',
    data: obj
  })
}

//删除job
export function deletejob(obj) {
  return request({
    url: '/admin/job/deletejob',
    method: 'post',
    data: obj
  })
}
