import Vue from "vue";
import VueRouter from "vue-router";
import vuex from "../store/index.js"
import Home from "../views/index.vue";
import Login from "../views/login";
import NotFound from "../views/Tmp.vue";
import basics from './basic' //基础管理
import Basic from '../views/basic'
import Areas from '../views/area' //组织管理
import area from './area'
import Mechanism from '../views/mechanism' //机构管理
import mechanism from './mechanism' //机构管理
import Tenants from '../views/tenant' //租户管理
import tenant from './tenant'
import Homes from '../views/home/home.vue'
import intetFaces from '../views/intetFaceInte/index.vue'  //接口流程管理
import intets from './intets'  //接口流程管理

import repairs from '../views/repair'//业务流程管理
import repair from './repair'
Vue.use(VueRouter);

const routes = [
    {
        path: "/login",
        name: "login",
        component: Login,
    },
    {
        path: "/manage/home",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/manage/home",
                name: "home",
                component: Homes
            },
            {
                path: "/basics",
                name: "basics",
                component: Basic,
                children: basics,
                meta: { title: '基础管理' },
            },
            {
                path: "/area",
                name: "Areas",
                component: Areas,
                children: area,
                meta: { title: '组织管理' },
            },
            {
                path: "/business",
                name: "Mechanism",
                component: Mechanism,
				children: mechanism,
                meta: { title: '机构管理' },
            },
			{
			    path: "/tenant",
			    name: "Tenants",
			    component: Tenants,
				children: tenant,
			    meta: { title: '租户管理' },
			},
            {
                path: "/repair",
                name: "repair",
                component: repairs,
                children: repair,
                meta: { title: '业务流管理' },
            },
            {
                path: "/intets",
                name: "Intets",
                component: intetFaces,
                children: intets,
                meta: { title: '接口集成' },
            },
            {
                path: "/manage/tmp",
                name: "NotFound",
                component: NotFound,
            },
            { path: '*', redirect: '/manage/tmp', hidden: true }
        ]
    },
];
const router = new VueRouter({
    routes
});
// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)

}
router.beforeEach((to, from, next) => {
	if(to.name!='home'){
		vuex.commit('UPDATE_BREADCRUMB',true)
	}else{
		vuex.commit('UPDATE_BREADCRUMB',false)
	}
	next();
})
export default router;