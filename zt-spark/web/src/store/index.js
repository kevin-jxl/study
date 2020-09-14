import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

// 应用初始状态
const state = {
  message: {
    code: '200',
    msg: 'success !'
  },
  selectedMenu:{},
  userInfo:{},
  breadcrumb:false,
  munuAuthority:[] //按钮权限
};

// 定义所需的 mutations
const mutations = {
  updateMessage(state, value){
    state.message = value;
  },
  updateSelectedMenu(state, value){
    state.selectedMenu = value;
  },
  UPDATE_USER_DATA(state, value){
    state.userData = value;
  },
  UPDATE_BREADCRUMB(state, value){
	state.breadcrumb = value; 
  },
  UPDATE_MENU_AUTHORITY(state, value){
    state.munuAuthority = value; 
  }
};

const actions = {};

const getters = {
    message(){
        return state.message;
    },
    selectedMenu(){
        return state.selectedMenu;
    },
    userInfo(){
        return state.userInfo;
    },
    munuAuthority(){
        return state.munuAuthority;
    }
}

// 创建 store 实例
export default new Vuex.Store({
  state,
  actions,
  getters,
  mutations,
  modules: {}
})
