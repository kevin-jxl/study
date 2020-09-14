<template>
  <div class="login-wrap">
	  <div class="warp">
			<div class="wrapTitle">智慧停车数字化运营管控云平台 V1.0 <span>（基础平台）</span></div>
			<div class="form login">
			  <div class="logo">
			    <userLogin></userLogin>
			  </div>
			</div>
			<div class="wrapBottom">Copyright 北京兴泰科技有限公司</div>
	  </div>
    
  </div>
</template>

<script>
import { debuglog } from "util";
import userLogin from "./userLogin";
export default {
  data() {
    return {};
  },
  methods: {},
  mounted() {},
  destroyed() {},
  components: {
    userLogin
  }
};
</script>

<style  lang="scss">
.login-wrap {
  width: 100%;
  height: 100%;
  background-image: url(../../assets/images/bg.png);
  background-attachment: fixed;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  .warp{
	// width: 70%;
	// height: 84%;
	min-width: 1460px;
	min-height: 760px;
	background-image: url(../../assets/images/warp.png);  
	background-repeat: no-repeat;
	background-size: 100% 100%;
	position: relative;
	.wrapTitle{
		text-align: left;
		font-size: 25px;
		font-weight: bold;
		margin: 50px 0 0 50px;
		span{
			font-size: 23px;
		}
	}
	.wrapBottom{
		position: absolute;
		color: #fff;
		bottom: -10px;
		left: 50%;
		margin-left: -105px;
		
	}
  }
  .login {
    // background: rgba(97, 71, 71, 0.4);
    .el-form-item__content {
      margin-left: 0px !important;
    }
    .el-input__inner {
      background: #fff !important;
      color: #666 !important;
      height: 35px !important;
      text-align: left !important;
      border: 1px solid #ccc !important;
    }
  }
}
</style>
