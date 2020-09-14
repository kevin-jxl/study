const _import = require('./_import')
import Layout from '@/page/index/'
import demoLayout from '@/page/secondaryLayout'
import passwordLayout from '@/views/layout/passwordLayout'
export default [{
    path: '/',
    name: '主页',
    redirect: '/wel'
}, {
    path: '/wel',
    component: Layout,
    redirect: '/wel/index',
    children: [{
        path: 'index',
        name: '首页',
      meta:{
        title:"首页"
      },
        component: _import('wel')
    }]
}, {
    path: '*',
    redirect: '/404',
    hidden: true
}, {
    path: '/login',
    component: _import('login/index')
}, {
  path:'/reset',
  component:_import('layout/passwordLayout','views'),
},{
    path: '/authredirect',
    name: '授权页',
    component: _import('login/authredirect')
}, {
    path: '/lock',
    name: '锁屏页',
    component: _import('lock/index')
}, {
    path: '/crud',
    component: Layout,
    redirect: '/crud/index',
    children: [{
        path: 'index',
        name: 'crud实例',
        meta:{
            title:"crud实例"
        },
        component: _import('crud/index', 'views')
    }]
}, {
    path: '/sexcel',
    component: Layout,
    redirect: '/sexcel/index',
    children: [{
      path: 'index',
      name: 'Excel案例',
      meta:{
        title:"Excel案例"
      },
      component: _import('sexcel/index', 'views')
    }]
},{
  path: '/demo',
  component: Layout,
  children: [{
    path: 'index',
    meta:{
      title:'demo列表',
    },
    component:demoLayout,
    children: [{
      path:'',
      meta:{
        title:'demo列表',
      },
      name: 'demo列表',
      component: _import('demo/index', 'views'),
    },{
      path: ':id',
      meta:{
        title:'demo详情',
      },
      name: 'demo详情',
      component: _import('demo/detail', 'views'),
    }]
  }]
},
  {
    path: '/info',
    component: Layout,
    redirect: '/info/index',
    children: [{
        path: 'index',
        meta:{
          title:'修改信息',
        },
        name: '修改信息',
        component: _import('admin/user/info', 'views')
    }]
}, {
    path: '/404',
    component: _import('error-page/404', 'components'),
    name: '404'
}, {
    path: '/403',
    component: _import('error-page/403', 'components'),
    name: '403'
}, {
    path: '/500',
    component: _import('error-page/500', 'components'),
    name: '500'
}, {
    path: '/myiframe',
    component: Layout,
    redirect: '/myiframe',
    meta: {
        keepAlive: true
    },
    children: [{
        path: ':routerPath',
        name: 'iframe',
        component: _import('iframe/main', 'components'),
        props: true
    }]
}]
