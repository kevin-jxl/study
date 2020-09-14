import Vue from 'vue'
import Vuex from 'vuex'
import User from './modules/user'
import getters from './getters'


Vue.use(Vuex)
// const store = new Vuex.Store({
//     modules: {
//         user,
//     },
//     getters,
// })

// export default store
// 创建 store 实例
// export default new Vuex.Store({
//     modules: {
//         user,
//     },
//     getters,

// })

export default new Vuex.Store({
  User,
  modules: {},
  getters
})

