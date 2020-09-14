import Repair from '../views/repair/Repair.vue';
import Events from '../views/repair/Event.vue';
import Approve from '../views/repair/Approve.vue';

let repairs = [
    {
        path: '/repair',
        redirect: '/repair/repair'
    },
    {
        path: '/repair/repair',
        name: 'Repairs',
        component: Repair,
        meta: { title: '业务流管理' },
    },
    // {
    //     path: '/repair/event',
    //     name: 'Events',
    //     component: Events,
    //     meta: { title: '事件处理流程管理' },
    // },
    // {
    //     path: '/repair/approve',
    //     name: 'Approves',
    //     component: Approve,
    //     meta: { title: '审批流程管理' },
    // }
]

export default repairs;