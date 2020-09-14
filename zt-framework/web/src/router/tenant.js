import Tenant from '../views/tenant/Tenant.vue';
import Business from '../views/tenant/Business.vue';



let tenant = [
    {
        path: '/tenant',
        redirect: '/tenant/tenant'
    },
    {
        path: '/tenant/tenant',
        name: 'tenant',
        component: Tenant,
        meta: { title: '租户管理' }
    },
	{
	    path: '/tenant/business',
	    name: 'Business',
	    component: Business,
	    meta: { title: '业务配置' }
	}
]


export default tenant;