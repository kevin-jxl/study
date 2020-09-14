import {
    setToken,
} from '@/util/auth'
import {
    setStore,
    getStore
} from '@/util/storage'
const user = {
    state: {
        userInfo: getStore({
            name: 'userInfo'
        }) || {},
        message: {
            code: '200',
            msg: 'success !'
        },
        selectedMenu: {}
    },
    actions: {},
    mutations: {
        updateMessage(state, value) {
            state.message = value;
        },
        updateSelectedMenu(state, value) {
            state.selectedMenu = value;
        },
    }
}
export default user

