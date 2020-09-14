import Device from "../views/device/Device.vue";
import Annex from "../views/device/Annex.vue";
import Monitor from "../views/device/Monitor.vue";
import Spare from "../views/device/Spare.vue";
import Placehold from "../components/Placehold.vue"


let devices = [
    {
        path: '/device',
        redirect: '/device/list'
    },
    {
        path: '/device/list',
        name: 'Device',
        component: Device,
        meta: { title: '设备列表' },
    },
    {
        path: '/device/list/annex',
        name: 'Annex',
        component: Annex,
        meta: { title: '附件信息' }
    },{
        path: '/device/monitor',
        name: 'Monitor',
        component: Monitor,
        meta: { title: '设备监控' }
    },{
        path: '/device/spare',
        name: 'Spare',
        component: Spare,
        meta: { title: '备件列表' }
    }
]

export default devices;
