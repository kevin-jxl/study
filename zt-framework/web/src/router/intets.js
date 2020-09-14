import CodeGenera from '../views/intetFaceInte/codeGeneration.vue';
import SysGeneras from '../views/intetFaceInte/sys/Sys.vue';
import SysInter from '../views/intetFaceInte/sys/Inter.vue';
import Iot from '../views/intetFaceInte/iot/Iot.vue';
import Interface from '../views/intetFaceInte/iot/Interface.vue';


let intets = [
    {
        path: '/intets',
        redirect: '/intets/codeGeneras'
    },
    {
        path: '/intets/codeGeneras',
        name: 'codeGeneras',
        component: CodeGenera,
        meta: { title: '代码生成' },
    },
    {
        path: '/intets/sys',
        name: 'SysGeneras',
        component: SysGeneras,
        meta: { title: '系统集成' },
    },
    {
        path: '/intets/inter',
        name: 'SysInter',
        component: SysInter,
        meta: { title: '接口管理' },
    },
    {
        path: '/intets/InternetThings',
        name: 'InternetThings',
        component: Iot,
        meta: { title: '物联网集成' },
    },
    {
        path: '/intets/interface',
        name: 'Interface',
        component: Interface,
        meta: { title: '接口管理' },
    }
    
]

export default intets;