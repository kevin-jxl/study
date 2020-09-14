<template>
  <div>
    <div class="result_left">
      <i class="el-icon-circle-check"></i>
    </div>
    <div class="result_right">
      <div class="result_text">重置成功，请牢记新的登陆密码</div>
      <el-button type="text" @click="toLogin">重新登陆</el-button>
    </div>
  </div>
</template>
<script>
  export default {
    name:'reset-result',
    data(){
      return {

      }
    },
    methods:{
      toLogin(){
        this.$router.push('/login')
      }
    }
  }
</script>
<style lang="scss" scoped>
  .result_left{
    float: left;
    font-size: 40px;
    color: #5db35f;
  }
  .result_right{
    float: left;
    margin-left: 4px;
    .result_text{
      font-size: 16px;
      font-weight: 600;
      padding-top: 4px;
    }
  }
</style>
