import Area from '../views/area/Area.vue';
import Firm from '../views/area/Firm.vue';
import Agency from '../views/area/Agency.vue';

let area = [
    {
        path: '/area',
        redirect: '/area/areas'
    },
    {
        path: '/area/areas',
        name: 'Areas',
        component: Area,
        meta: { title: '行政区域管理' },
    },
    {
        path: '/area/firm',
        name: 'firm',
        component: Firm,
        meta: { title: '企业管理' },
    },
    {
        path: '/area/agency',
        name: 'agency',
        component: Agency,
        meta: { title: '行政机构管理' },
    },
]


export default area;