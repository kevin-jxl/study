import NotFound from "../views/Tmp.vue";

let messages = [
    {
        path: '/message',
        redirect: '/message/notice'
    },{
        path: '/message/notice',
        name: 'Notice',
        component: NotFound,
        meta: { title: '信息发布通知' }
    }
]

export default messages;