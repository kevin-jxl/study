import NotFound from "../views/Tmp.vue";
import Business from "../views/dispatch/business/Business";
// import Disposal from "../views/dispatch/business/Disposal";
// import Complete from "../views/dispatch/business/Complete";

import Supervise from "../views/dispatch/supervise/Supervise";
import Order from "../views/dispatch/order/Order";

import Placehold from "../components/Placehold.vue";
let dispatchs = [
    {
        path: '/dispatch',
        redirect: '/dispatch/business'
    },{
        path: '/dispatch/business',
        name: 'Business',
        component: Placehold,
        meta: { title: '业务调度' },
        children: [
            {
                path: '/dispatch/business',
                name: 'Business',
                component: Business,
                meta: { title: '派遣栏' }
            }, {
                path: '/dispatch/business',
                name: 'Business',
                component: Business,
                meta: { title: '处置栏' }
            }, {
                path: '/dispatch/business',
                name: 'Business',
                component: Business,
                meta: { title: '完成栏' }
            }
        ]
    },{
        path: '/dispatch/supervise',
        name: 'Supervise',
        component: Supervise,
        meta: { title: '监督管理' }
    },{
        path: '/dispatch/order',
        name: 'Order',
        component: Order,
        meta: { title: '工单管理' }
    },{
        path: '/dispatch/tower',
        name: 'Tower',
        component: NotFound,
        meta: { title: '岗楼管理' }
    }
]

export default dispatchs;