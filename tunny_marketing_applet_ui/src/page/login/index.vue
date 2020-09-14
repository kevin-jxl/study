<template>
  <div class="login-container pull-height"
       @keyup.enter.native="handleLogin">
    <div class="login-border">
      <div class="login-main">
        <h2 class="login-title"> SPARK
        </h2>
        <userLogin></userLogin>
      </div>
    </div>
  </div>
</template>
<script>
import userLogin from './userlogin'
import socialLogin from './sociallogin'
import topTheme from '../index/top/top-theme'
import theme from '@/mixins/theme'
import { mapGetters } from 'vuex'
export default {
  name: 'login',
  mixins: [theme()],
  components: {
    topTheme,
    userLogin,
    socialLogin
  },
  data() {
    return {
      activeName: 'user'
    }
  },
  created() { },
  mounted() { },
  computed: {
    ...mapGetters(['website'])
  },
  props: [],
  methods: {}
}
</script>

<style lang="scss">
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  /*background: rgba(0, 0, 0, 0.3);*/
  position: relative;
}
.login-container::before {
  z-index: -999;
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-image: url('../../assets/img/login.png');
  background-size: auto;
}
.login-info {
  padding-left: 60px;
}
.login-info-title {
  line-height: 90px;
}
.login-info-item {
  font-size: 14px;
}
.login-border {
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: right;
  align-items: end;
  padding: 10px;
  width: 800px;
  height: 400px;
  background-image: url('../../assets/img/login_enter.png');
  background-size: cover;
}
.login-main {
  position: absolute;
  top:20%;
  left: 59%;
  width: 250px;
}
.login-main > h3 {
  margin-bottom: 20px;
}
.login-main > p {
  color: #76838f;
}
.login-title {
  margin: 0;
  text-align: left;
  /*font-weight: 600;*/
  /*font-size: 20px;*/
  color: #409eff;
  letter-spacing: 1px;
}
.login-submit {
  /*margin-top: 20px;*/
  width: 240px;
  background: #3080fe;
  border-radius: 28px;
}
.login-form {
  /*margin: 10px 0;*/
  .el-form-item__content {
    width: 270px;
  }
  .el-form-item {
    margin-bottom: 12px;
  }
  .el-input {
    input {
      text-indent: 5px;
      border-color: #dcdcdc;
      border-radius: 3px;
    }
    .el-input__prefix {
      i {
        padding: 0 5px;
        font-size: 16px !important;
      }
    }
  }
}
.login-code {
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin: 0 0 0 10px;
}
.login-code-img {
  width: 100px;
  height: 32px;
  margin:4px 0;
  background-color: #fdfdfd;
  border: 1px solid #f0f0f0;
  color: #333;
  font-size: 14px;
  font-weight: bold;
  letter-spacing: 5px;
  line-height: 32px;
  text-indent: 5px;
  text-align: center;
}
</style>
