import request from '../api'

export function getMenuTree() {  //左侧树形
  return request({
    url: '/admin/menu/userMenuTree',
    method: 'get'
  })
}


export function menuInfo(menuId) { //菜单详情
    return request({
        url: '/admin/menu/' + menuId,
        method: 'get'
    })
}

export function addMenu(obj) {  //角色新增
    return request({
        url: '/admin/menu',  
        method: 'post',
        data: obj
    })
}

export function updateMenu(obj) {  //角色修改
    return request({
        url: '/admin/menu',  
        method: 'put',
        data: obj
    })
}

export function delObj(menuId) { //删除
    return request({
      url: '/admin/menu/' + menuId,
      method: 'DELETE'
    })
  }

export function getUserMenu() {  //头部、左侧树形
    return request({
        url: '/admin/menu/userMenu',
        method: 'get'
    })
}