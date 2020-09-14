import Mechanism from '../views/mechanism/Mechanism.vue';

let mechanism = [
    {
        path: '/mechanism',
        redirect: '/mechanism/mechanism',
        meta: { title: '业务流管理' },
    },
    {
        path: '/mechanism/mechanism',
        name: 'mechanism',
        component: Mechanism,
        meta: { title: '' },
    }
]


export default mechanism;