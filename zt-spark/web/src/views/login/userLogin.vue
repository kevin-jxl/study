<template>
  <div class="login-wrap">
    <div class="form login">
      <div class="logo logoTitle">
        <span>用户登录</span>
      </div>
      <el-form :model="user" status-icon :rules="rules" ref="user" label-width="100px">
        <el-form-item prop="username" style="width:100%;">
          <el-input
            size="small"
            type="input"
            v-model="user.username"
            autocomplete="off"
            placeholder="用户名/手机"
          ><i slot="prefix" class="el-icon-user"></i></el-input>
        </el-form-item>
        <el-form-item prop="password" style="width:100%;">
          <el-input
            size="small"
            type="password"
            v-model="user.password"
            autocomplete="off"
            placeholder="密码"
          ><i slot="prefix" class="el-icon-lock"></i></el-input>
        </el-form-item>
		<el-form-item prop="code"  style="width:100%;">
		  <el-input
		    size="small"
		    :maxlength="code.len"
		    v-model="user.code"
		    auto-complete="off"
		    placeholder="请输入验证码"
		  >
		    <i slot="prefix"
		    class="el-icon-warning-outline"></i>
			<div class="login-code" slot="suffix">
			  <span
			    class="login-code-img"
			    @click="refreshCode"
			    v-if="code.type == 'text'"
			  >{{user.code}}</span>
			  <img :src="code.src" alt="验证码" class="login-code-img" @click="refreshCode" v-else />
			</div>
		  </el-input>
		</el-form-item>
		<el-form-item class="RememberPassword">
			<span>
			    <el-checkbox v-model="remember">记住密码</el-checkbox>
			</span>
		</el-form-item>

        <el-button type="primary" size="small" @click="onLogin" style="margin:0 auto;">
          立即登录
          <i v-if="loginLoading" class="el-icon-loading" />
        </el-button>
		<div class="registered">
			<span>立即注册</span><span> | </span><span>忘记密码</span>
		</div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { debuglog } from "util";
import { randomLenNum, encryption } from "@/util/util";
import { loginByUsername } from "../../api";
import { setToken, getToken, removeToken } from "@/util/auth";
import { mapGetters } from "vuex";
import { Message } from 'element-ui'

// 根据用户名登录
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.user.password !== "") {
          this.$refs.user.validateField("checkPass");
        }
        callback();
      }
    };
    var validateUser = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入用户名"));
      } else {
        callback();
      }
    };
    const validateCode = (rule, value, callback) => {
      if (this.code.value !== value) {
        this.user.code = "";
        this.refreshCode();
        callback(new Error("请输入正确的验证码"));
      } else {
        callback();
      }
    };
    return {
      user: {
        username: window.localStorage.getItem('user')?window.localStorage.getItem('user'):'',
        password: window.localStorage.getItem('password')?window.localStorage.getItem('password'):'',
        code: "",
        randomStr: ""
      },
      code: {
        src: "/code",
        value: "",
        len: 4,
        type: "image"
      },
      rules: {
        username: [{ validator: validateUser, trigger: "blur" ,required: true,}],
        password: [{ validator: validatePass, trigger: "blur" ,required: true,}]
      },
      remember: true,
      loginLoading: false
    };
  },
  methods: {
    refreshCode() {
      let me = this;
      //获取验证码
      this.user.code = "";
      this.user.randomStr = randomLenNum(this.code.len, true);
      me.code.src =
        config.services + `${"/code"}?randomStr=${this.user.randomStr}`;
    },
    onLogin() {
      let me = this;
      var loginParams = {
        username: me.user.username,
        password: me.user.password,
        code: me.user.code,
        randomStr: this.user.randomStr
      };
      const user = encryption({
        data: loginParams,
        key: "TunnyTunnyTunnyTunny",
        param: ["password"]
      });
	  // console.log(this.remember)
	  if(this.remember){
		window.localStorage.setItem('user',me.user.username);
		window.localStorage.setItem('password',me.user.password);
	  }
      me.$refs.user.validate(valid => {
        if (valid) {
          loginByUsername(
            user.username,
            user.password,
            user.code,
            user.randomStr
          ).then(response => {
              const data = response.data;
              setToken(data.access_token);
              if (data.access_token) {
                  this.refreshCode()
                  me.$api.getUserInfo()
                  .then(res => {
					    if(res.data.data == false){
                            removeToken(data.access_token)
                            Message({
                                message: '权限不足,禁止访问',
                                type: 'error',
                                duration: 1000,
                            })
                        }else{
                            sessionStorage.setItem("userData",JSON.stringify(res.data.data));
                            me.$router.push("/dashbord");
                        }
                  })
                  .catch(err => {
                    console.log(err);
                  });
              }
              // commit('UPDATE_USER_INFO', data.access_token)
              // commit('SET_REFRESH_TOKEN', data.refresh_token)
              // commit('CLEAR_LOCK')
            })
            .catch(error => {
                this.refreshCode()
              console.log("登录时候的报错内容", error);
            });
        }
      });
    },
    clear() {
      $(document).unbind("keydown");
    }
  },
  mounted() {
    let me = this;
    me.refreshCode();
    // var loginuser = localStorage.getItem("user");
    // loginuser = JSON.parse(loginuser);
    // if (loginuser !== null) {
    //     me.remember = true;
    //     me.user.username = loginuser.username;
    //     me.user.password = loginuser.password;
    // }
    $(document).keydown(e => {
      if (e.keyCode === 13) {
        me.onLogin();
      }
    });
  },
  destroyed() {
    this.clear();
  }
};
</script>

<style lang="scss" scoped>
.form {
  position: absolute;
  top: 45%;
  right: 10%;
  margin: -160px 0 0 -160px;
  width: 300px;
  height: 260px;
  padding: 40px;
  // box-shadow: 0 0 100px rgba(0, 0, 0, 0.08);

  button {
    width: 100%;
  }

  p {
    color: rgb(204, 204, 204);
    text-align: center;
    margin-top: 16px;
    font-size: 12px;
    display: flex;
    justify-content: space-between;
  }
}

.logoTitle {
  text-align: center;
  cursor: pointer;
  margin-bottom: 24px;
  display: flex;
  justify-content: center;
  align-items: center;

  //   img {
  //     width: 40px;
  //     margin-right: 8px;
  //     background-color: cornflowerblue;
  //     border-radius: 30px;
  //   }

  span {
    vertical-align: text-bottom;
    font-size: 21px;
    text-transform: uppercase;
    display: inline-block;
    font-weight: 600;
	color: #333;
    -webkit-background-clip: text;
    // -webkit-text-fill-color: transparent;
  }
}

.RememberPassword{
	margin-top: -20px;
}
.login-code {
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin: 0 0 0 10px;
}
.login-code-img {
  width: 85px;
  height: 25px;
  margin-top: 8px;
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
.registered{
	margin-top: 5px;
	color: #000;
	cursor: pointer;
}

</style>