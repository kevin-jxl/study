<template>
  <el-container>
    <el-header>
      <div class="pa_header">
        <span class="icon_header">TUNNY</span>
        <span class="title_header">找回密码</span>
        <el-button type="text" class="button_title" @click="toLogin">返回登陆</el-button>
      </div>
    </el-header>
    <el-main>
      <div class="pa_main">
        <component
          v-bind:is="currentTabComponent"
          @next-step="nextStep"
          :current-login="currentLogin"
        ></component>
      </div>
    </el-main>
  </el-container>
</template>
<script>
  import CheckReset from './components/checkReset'
  import ResetPassword from './components/reset'
  import ResetResult from './components/result'
  export default {
    name:'passwordLayout',
    data(){
      return {
        currentTabComponent:'check-reset',
        currentLogin:''
      }
    },
    components:{
      'check-reset':CheckReset,
      'reset-password':ResetPassword,
      'reset-result':ResetResult,
    },
    methods:{
      getCode(){
        const TIME_COUNT = 60;
        if (!this.timer) {
          this.count = TIME_COUNT;
          this.show = false;
          this.timer = setInterval(() => {
            if (this.count > 0 && this.count <= TIME_COUNT) {
              this.count--;
            } else {
              this.show = true;
              clearInterval(this.timer);
              this.timer = null;
            }
          }, 1000)
        }
      },
      nextStep(step,currentLogin){
        this.currentTabComponent = step
        this.currentLogin = currentLogin?currentLogin:this.currentLogin
      },
      toLogin(){
        this.$router.push('/login')
      }
    }
  }
</script>
<style lang="scss" scoped>
  .el-container{
    height: 100%;
  }
  .el-header {
    padding: 0;
    background-color: #f0f1f0;
    height: 100px !important;
  }
  .pa_header{
    height: 100%;
    padding:0 0 0 100px;
    span{
      line-height: 100px;
    }
    .icon_header{
      margin-left: 30px;
      font-size: 20px;
      color: #3a8ee6;
    }
    .title_header{
      margin-left: 30px;
      font-size: 20px;
      font-weight: 500;
      /*color: #3a8ee6;*/
    }
    .button_title{
      margin-left: calc(100% - 400px);
      font-size: 20px;
    }
  }
  .el-main{
    height: calc(100% - 100px);
  }
  .pa_main{
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
  }
</style>
