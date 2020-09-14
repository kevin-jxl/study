import NotFound from "../views/Tmp.vue";

let finances = [
    {
        path: '/finance',
        redirect: '/finance/set'
    },{
        path: '/finance/set',
        name: 'Set',
        component: NotFound,
        meta: { title: '收费设置' }
    },{
        path: '/finance/vip',
        name: 'Vip',
        component: NotFound,
        meta: { title: '会员管理' }
    },{
        path: '/finance/settle',
        name: 'Settle',
        component: NotFound,
        meta: { title: '结算管理' }
    },{
        path: '/finance/order',
        name: 'Order',
        component: NotFound,
        meta: { title: '财务订单管理' }
    },{
        path: '/finance/invoice',
        name: 'Invoice',
        component: NotFound,
        meta: { title: '发票管理' }
    }
]

export default finances;