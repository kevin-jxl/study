import NotFound from "../views/Tmp.vue";
import Person from "../views/company/person/Person";
import Duty from "../views/company/duty/Duty";
import Attendance from "../views/company/attendance/Attendance";
import Doc from "../views/company/doc/Doc";
import Card from "../views/company/card/Card";
import Berth from "../views/company/register/Berth.vue";
import Lot from "../views/company/register/Lot";
import Placehold from "../components/Placehold.vue";


let companys = [
    {
        path: '/company',
        redirect: '/company/person'
    },{
        path: '/company/person',
        name: 'Person',
        component: Person,
        meta: { title: '人员管理' }
    },{
        path: '/company/duty',
        name: 'Firm',
        component: Duty,
        meta: { title: '值班信息管理' }
    },{
        path: '/company/register',
        name: 'Register',
        component: Placehold,
        meta: { title: '车场登记' },
        children:[
            {
                path: '/company/register/lot',
                name: 'Lot',
                component: Lot,
                meta: { title: '添加车场' }
            },{
                path: '/company/register/berth',
                name: 'Berth',
                component: Berth,
                meta: { title: '添加泊位' }
            },
        ]
    },{
        path: '/company/attendance',
        name: 'Attendance',
        component: Attendance,
        meta: { title: '考勤管理' }
    },{
        path: '/company/doc',
        name: 'Doc',
        component: Doc,
        meta: { title: '文档管理' }
    },{
        path: '/company/card',
        name: 'Card',
        component: Card,
        meta: { title: '门禁卡管理' }
    }
]

export default companys;