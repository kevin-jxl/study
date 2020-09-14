import { Loading } from 'element-ui';
export default{
  install(Vue,options) {
    Vue.prototype.showMainLoading = function () {
      this.$store.dispatch('SetLoading',true)
    }
    Vue.prototype.hideMainLoading = function () {
      this.$store.dispatch('SetLoading',false)
    }
    Vue.prototype.showWindowLoading = function () {
      return this.$loading({
        lock: true,
        text: '处理中'
      })
    }
    Vue.prototype.hideWindowLoading = function (loading) {
      /*if(loading instanceof Object){
        this.$nextTick(()=>{
          loading.close();
        })
      }*/
      let loadingInstance = Loading.service({ fullscreen: true });
      this.$nextTick(() => {
        loadingInstance.close();
      });
    }
  }
}
