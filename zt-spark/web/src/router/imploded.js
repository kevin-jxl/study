import NotFound from "../views/Tmp.vue";

let implodeds = [
    {
        path: '/imploded',
        redirect: '/imploded/iot'
    },{
        path: '/imploded/iot',
        name: 'Iot',
        component: NotFound,
        meta: { title: '人员管理' }
    },{
        path: '/imploded/sys',
        name: 'Sys',
        component: NotFound,
        meta: { title: '值班信息管理' }
    }
]

export default implodeds;