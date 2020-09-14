import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import '../theme/index.css'
import Element from 'element-ui';
//import 'element-ui/lib/theme-chalk/index.css';
import { mapGetters } from 'vuex';
import api, { ajaxPromise } from './api/index';
import 'default-passive-events'//解决谷歌浏览器报警告

import 'e-icon-picker/dist/index.css' // 基础样式
import 'e-icon-picker/dist/main.css' // fontAwesome 图标库样式


Vue.use(Element)
Vue.config.productionTip = false;
Vue.prototype.$api = api;

router.beforeEach((to, from, next) => {
  var isLogin = localStorage.getItem('isLogin') === 'false' && to.path !== '/login';
  if (to.path === '' || to.path === '/' || isLogin) {
    next({path: '/login'})
  } else {
    next()
  }
})

new Vue({
  router,
  store,
  render: h => h(App),
  computed: {
    ...mapGetters([
      'message'    
    ])
  },
  watch: {
    message(res, old) {
      if(res.msg === old.msg ) { return; }
      if(res.code === 403 && this.$route.path !== '/login'){
        this.$router.push({ name: "login", params: {} }).catch(err => { err; });
      }
      this.$message({message: res.msg, type:'error', duration:config.duration})
    }
  }
}).$mount("#app");
