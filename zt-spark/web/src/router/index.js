import Vue from "vue";
import VueRouter from "vue-router";
import vuex from "../store/index.js"
import Home from "../views/index.vue";
import Login from "../views/login";
import Dashbord from "../views/dashbord/Dashbord.vue";

import companys from "./company";
import dispatchs from "./dispatch";
import parkings from "./parking";
import devices from "./device";
import finances from "./finance";
import reports from "./report";
import orders from "./order";
import messages from "./message";
import implodeds from "./imploded";
import Placehold from "../components/Placehold.vue"

Vue.use(VueRouter);

const routes = [
    {
        path: "/login",
        name: "login",
        component: Login,
    },
    {
        path: "/",
        name: "Home",
        component: Home,
        children: [
            {
                path: '/',
                redirect: '/dashbord'
            },
            {
                path: "/dashbord",
                name: "Dashbord",
                component: Dashbord,
                meta: { title: '首页' },
            },
            {
                path: "/company",
                name: "Company",
                children: companys,
                component: Placehold,
                meta: { title: '公司管理' },
            },
            {
                path: "/dispatch",
                name: "Dispatch",
                children: dispatchs,
                component: Placehold,
                meta: { title: '调度管理' },
            },
            {
                path: "/parking",
                name: "Parking",
                children: parkings,
                component: Placehold,
                meta: { title: '车场管理' },
            },
			{
			    path: "/device",
			    name: "Device",
				children: devices,
                component: Placehold,
			    meta: { title: '设备管理' },
			},
            {
                path: "/finance",
                name: "Finance",
                children: finances,
                component: Placehold,
                meta: { title: '财务管理' },
            },
            {
                path: "/report",
                name: "Report",
                children: reports,
                component: Placehold,
                meta: { title: '报表管理' },
            },
            {
                path: "/order",
                name: "Order",
                children: orders,
                component: Placehold,
                meta: { title: '订单管理' },
            },
            {
                path: "/message",
                name: "Message",
                children: messages,
                component: Placehold,
                meta: { title: '信息管理' },
            },
            {
                path: "/imploded",
                name: "Imploded",
                children: implodeds,
                component: Placehold,
                meta: { title: '系统集成' },
            }
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