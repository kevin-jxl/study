import router from './router/router'
import store from './store'
import Vue from 'vue'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import {
    getToken
} from '@/util/auth'
import {
    setTitle
} from '@/util/util'
import {
    validatenull
} from '@/util/validate'

// NProgress Configuration
NProgress.configure({
    showSpinner: false
})

function hasPermission(roles, permissionRoles) {
    if (!permissionRoles) return true
    return roles.some(role => permissionRoles.indexOf(role) >= 0)
}
const website = store.getters.website
const whiteList = website.whiteList
const lockPage = '/lock'

router.beforeEach((to, from, next) => {
    // start progress bar
    NProgress.start()
  //判断tags列表是否包含跳转页，不包含则更新跳转页为不缓存
  if(!store.getters.tagList.map(item=>item.value).includes(to.path)){
    to.meta.keepAlive = false
    // Vue.nextTick(()=>{
    //   to.meta.keepAlive = true
    // })
  }
  //未启用缓存时，更新跳转页为不缓存
  if(!store.getters.cacheControl){
    to.meta.keepAlive = false
  }
    const value = to.query.src ? to.query.src : to.path
    const label = to.query.name ? to.query.name : to.meta.title
    if (whiteList.indexOf(value) === -1) {
        store.commit('ADD_TAG', {
            label: label,
            value: value,
            query: to.query,
            meta: to.matched&&to.matched.length>0&&to.matched[0].meta
        })
    }
    if (store.getters.access_token) { // determine if there has token
        /* has token*/
        if (store.getters.isLock && to.path !== lockPage) {
            next({
                path: lockPage
            })
            NProgress.done()
        } else if (to.path === '/login') {
            next({
                path: '/'
            })
            NProgress.done()
        } else {
            if (!store.getters.userInfo||(Object.keys(store.getters.userInfo).length === 0 && store.getters.userInfo.constructor === Object)) {
                store.dispatch('GetUserInfo').then(res => {
                    const roles = res.roles
                    next({...to,
                        replace: true
                    })
                }).catch(() => {
                    store.dispatch('FedLogOut').then(() => {
                        next({
                            path: '/login'
                        })
                        NProgress.done()
                    })
                })
            } else {
                next()
            }
        }
    } else {
        /* has no token*/
        if (whiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            next('/login')
            NProgress.done()
        }
    }
})

// 寻找子菜单的父类
function findMenuParent(tag) {
    const tagCurrent = []
    tagCurrent.push(tag)
    return tagCurrent
}

router.beforeResolve((to, from, next) => {
  // if(store.getters.cacheControl){
  //   to.meta.keepAlive = true
  // }
  next()
})

router.afterEach((to, from) => {
  NProgress.done()
  setTimeout(() => {
      const tag = store.getters.tag
      setTitle(tag.label)
      store.commit('SET_TAG_CURRENT', findMenuParent(tag))
  }, 0)
  //页面跳转后更新上一页状态为 缓存
  from.meta.keepAlive = true
})
