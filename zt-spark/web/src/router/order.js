import NotFound from "../views/Tmp.vue";

let orders = [
    {
        path: '/order',
        redirect: '/order/list'
    },{
        path: '/order/list',
        name: 'List',
        component: NotFound,
        meta: { title: '订单管理' }
    },{
        path: '/order/invoice',
        name: 'Invoice',
        component: NotFound,
        meta: { title: '发票信息' }
    },{
        path: '/order/abnormal',
        name: 'Abnormal',
        component: NotFound,
        meta: { title: '异常定单管理' }
    }
]

export default orders;