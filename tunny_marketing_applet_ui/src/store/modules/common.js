import {
    setStore,
    getStore,
    removeStore
} from '@/util/store'
import {
    validatenull
} from '@/util/validate'
import {
    getDic
} from '@/api/admin'
import {
    baseUrl
} from '@/config/env'
import website from '@/const/website'
const common = {

  state: {
      isCollapse: false,
      isFullScren: false,
      isLoading:false,
      menuType:getStore({name: 'menuType'}) || 'aside',
      isTags: getStore({
        name: 'isTags'
      }) || 0,
      isLock: getStore({
          name: 'isLock'
        }) || false,
      theme: getStore({
          name: 'theme'
        }) || '#409EFF',
      themeName: getStore({
          name: 'themeName'
        }) || 'theme-four',
      cacheControl: getStore({
          name: 'cacheControl'
        }) || true,
      lockPasswd: getStore({
          name: 'lockPasswd'
        }) || '',
      website: website
    },
  actions: {
        // 获取字典公用类
      GetDic({
            commit,
            state,
            dispatch
        }, dic) {
          return new Promise((resolve, reject) => {
              if (dic instanceof Array) {
                  Promise.all(dic.map(ele => getDic(ele))).then(data => {
                      const result = {}
                      dic.forEach((ele, index) => {
                          result[ele] = data[index].data
                        })
                      resolve(result)
                    })
                }
            })
        },
        SetLoading({commit,state,dispatch},params){
          commit('SET_LOADING', params)
        }
    },
  mutations: {
      SET_COLLAPSE: (state, action) => {
          state.isCollapse = !state.isCollapse
        },
      SET_FULLSCREN: (state, action) => {
          state.isFullScren = !state.isFullScren
        },
      SET_LOCK: (state, action) => {
          state.isLock = true
          setStore({
              name: 'isLock',
              content: state.isLock,
              type: 'session'
            })
        },
      SET_THEME: (state, color) => {
          state.theme = color
          setStore({
              name: 'theme',
              content: state.theme
            })
        },
      SET_THEME_NAME: (state, themeName) => {
          state.themeName = themeName
            setStore({
              name: 'themeName',
              content: state.themeName
            })
        },
      SET_CACHE_CONTROL: (state, payload) => {
          state.cacheControl = payload
            setStore({
              name: 'cacheControl',
              content: state.cacheControl,
              type: 'session'
            })
        },
      SET_LOCK_PASSWD: (state, lockPasswd) => {
          state.lockPasswd = lockPasswd
          setStore({
              name: 'lockPasswd',
              content: state.lockPasswd,
              type: 'session'
            })
        },
      CLEAR_LOCK: (state, action) => {
          state.isLock = false
          state.lockPasswd = ''
          removeStore({
              name: 'lockPasswd'
            })
          removeStore({
              name: 'isLock'
            })
        },
      SET_TAGS: (state, types)=>{
        state.isTags = types
        setStore({
          name: 'isTags',
          content: state.isTags
        })
      },
      SET_LOADING:(state, action)=>{
        state.isLoading = action
      },
      SET_ENLOADING:()=>{
        state.isLoading = false
      },
      SET_MENU_TYPE: (state, action) => {
        state.menuType = action
        setStore({
          name: 'menuType',
          content: state.menuType,
          type: 'session'
        })
      },
    }
}
export default common
