import request from '@/router/axios'

//查询字典类型树
export function dictTypeTree() {
  return request({
    url: '/admin/sysdicttype/tree',
    method: 'get'
  })
}

//新增字典类型
export function addDictType(obj) {
  return request({
    url: '/admin/sysdicttype/',
    method: 'post',
    data: obj
  })
}

//获取字典类型
export function getDictType(id) {
  return request({
    url: '/admin/sysdicttype/' + id,
    method: 'get'
  })
}

//删除字典类型
export function delDictType(id) {
  return request({
    url: '/admin/sysdicttype/' + id,
    method: 'delete'
  })
}

//修改字典类型
export function putDictType(obj) {
  return request({
    url: '/admin/sysdicttype/',
    method: 'put',
    data: obj
  })
}

//字典列表
export function dictIndexList(typeId) {
  return request({
    url: '/admin/sysdictindex/selectDictIndex',
    method: 'get',
    params: {
      typeId: typeId
    }
  })
}

//字典新增
export function addDictIndex(obj) {
  return request({
    url: '/admin/sysdictindex/',
    method: 'post',
    data: obj
  })
}

//字典获取
export function getDictIndex(id) {
  return request({
    url: '/admin/sysdictindex/' + id,
    method: 'get'
  })
}

//字典删除
export function delDictIndex(id) {
  return request({
    url: '/admin/sysdictindex/' + id,
    method: 'delete'
  })
}

//字典修改
export function putDictIndex(obj) {
  return request({
    url: '/admin/sysdictindex/',
    method: 'put',
    data: obj
  })
}

//字典明细列表
export function dictList(dictId) {
  return request({
    url: '/admin/sysdict/selectDicts',
    method: 'get',
    params: {
      dictId: dictId
    }
  })
}

//字典明细新增
export function addDict(obj) {
  return request({
    url: '/admin/sysdict/',
    method: 'post',
    data: obj
  })
}

//字典明细获取
export function getDict(id) {
  return request({
    url: '/admin/sysdict/' + id,
    method: 'get'
  })
}

//字典明细删除
export function delDict(id) {
  return request({
    url: '/admin/sysdict/' + id,
    method: 'delete'
  })
}

//字典明细更新
export function putDict(obj) {
  return request({
    url: '/admin/sysdict/',
    method: 'put',
    data: obj
  })
}

//通用的字典查询接口
export function selectDictByType() {
  return request({
    url: '/admin/sysdict/selectDictByType',
    method: 'get',
    params: {
      "searchType": "1",
      "searchCode": "CODE"
    }
  })
}





