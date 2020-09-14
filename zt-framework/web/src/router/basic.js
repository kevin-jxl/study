import Tenant from '../views/basic/tenant/Tenant';
import User from '../views/basic/user/User.vue';
import RuleDetail from '../views/basic/rule/Detail';
import RuleDetails from '../views/basic/rule/index';
import RuleEdit from '../views/basic/rule/Edit';
import RulePost from '../views/basic/rule/Post';
import MenuDetail from '../views/basic/menu/Detail';
import MenuDetails from '../views/basic/menu/index';
import MenuEdit from '../views/basic/menu/Edit';
import MenuConfig from '../views/basic/menu/Config';
import Log from '../views/basic/log/index';
import LogBusiness from '../views/basic/log/Business';
import Logsys from '../views/basic/log/Sys';
import Dic from '../views/basic/dic/Dic';
import Logins from '../views/basic/log/Logins';



let basics = [
    {
        path: '/basic',
        redirect: '/basic/user'
    },
    {
        path: '/basic/user',
        name: 'user',
        component: User,
        meta: { title: '用户管理' }
    },
    {
        path: '/basic/rule/:menu',
        name: 'detail',
        component: RuleDetail,
        meta: { title: '角色管理' },
        children:[
            // {
            //     path: '/manage/rule/detail',
            //     name: 'ruleDetail',
            //     component: RuleDetail,
            //     meta: { title: '角色管理' },
            // },
            {
                path: '/basic/rule/edit',
                name: 'ruleEdit',
                component: RuleEdit,
                meta: { title: '角色配置' },
            },
            {
                path: '/basic/rule/post',
                name: 'rulePost',
                component: RulePost,
                meta: { title: '岗位配置' },
            }
        ]
    },
    {
        path: '/basic/menu/:menu',
        name: 'menuDetails',
        component: MenuDetail,
        meta: { title: '菜单管理' },
        // children:[
        //     {
        //         path: '/manage/menu/detail',
        //         name: 'menuDetail',
        //         component: MenuDetail,
        //         meta: { title: '菜单管理' },
        //     },
        //     {
        //         path: '/manage/menu/edit',
        //         name: 'menuEdit',
        //         component: MenuEdit,
        //         meta: { title: '功能菜单' },
        //     },
        //     {
        //         path: '/manage/menu/config',
        //         name: 'menueConfig',
        //         component: MenuConfig,
        //         meta: { title: '功能配置' },
        //     }
        // ]
    },
    {
        path: '/basic/log/:menu',
        name: 'Log',
        component: Log,
        meta: { title: '日志管理' },
        children:[
            {
                path: '/basic/log/business',
                name: 'business',
                component: LogBusiness,
                meta: { title: '操作日志' },
            },
            {
                path: '/basic/log/sys',
                name: 'logsys',
                component: Logsys,
                meta: { title: '系统异常日志' },
            },
            {
                path: '/basic/log/login',
                name: 'logins',
                component: Logins,
                meta: { title: '登录日志' },
            }
        ]
    },{
        path: '/basic/dic',
        name: 'dic',
        component: Dic,
        meta: { title: '字典管理' },
    },
]


export default basics;