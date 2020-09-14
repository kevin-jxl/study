import NotFound from "../views/Tmp.vue";

let reports = [
    {
        path: '/report',
        redirect: '/report/finance'
    },{
        path: '/report/finance',
        name: 'Finance',
        component: NotFound,
        meta: { title: '财务报表' }
    },{
        path: '/report/park',
        name: 'Park',
        component: NotFound,
        meta: { title: '停车报表' }
    },{
        path: '/report/operation',
        name: 'Operation',
        component: NotFound,
        meta: { title: '运营报表' }
    },{
        path: '/report/maintain',
        name: 'Maintain',
        component: NotFound,
        meta: { title: '运维报表' }
    }
]

export default reports;