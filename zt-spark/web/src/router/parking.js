import NotFound from "../views/Tmp.vue";

let parkings = [
    {
        path: '/parking',
        redirect: '/parking/road'
    },{
        path: '/parking/road',
        name: 'Road',
        component: NotFound,
        meta: { title: '路内车场' }
    },{
        path: '/parking/indoor',
        name: 'Indoor',
        component: NotFound,
        meta: { title: '封闭车场' }
    },{
        path: '/parking/video',
        name: 'Video',
        component: NotFound,
        meta: { title: '视频监控' }
    }
]

export default parkings;