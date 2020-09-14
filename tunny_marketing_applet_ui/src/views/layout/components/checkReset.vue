<template>
  <div class="pa_enter">
    <el-form ref="form" :model="form" :rules="rules" status-icon>
      <el-row>
        <el-col :span="24">
          <span class="tipTitle">请输入你需要找回登陆密码的账户名</span>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item prop="email">
            <el-input
              v-model="form.email"
              placeholder="输入邮箱/手机号"
              @focus="codeEnable = true"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="14">
          <el-form-item prop="checkCode">
            <el-input
              v-model="form.checkCode"
              placeholder="输入验证码"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-button v-if="show"
                     class="verification_code"
                     type="success"
                     @click="getCode"
                     :disabled="codeEnable"
                     plain>获取验证码</el-button>
          <el-button v-else class="verification_code" type="success" plain disabled>{{count}}</el-button>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button v-if="checkLoading" type="primary" @click="onSubmit" class="next_button" round>下一步</el-button>
        <el-button v-else type="primary" @click="onSubmit" class="next_button" round :loading="true">验证</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import {checkLoginWithCode, sendLoginCode} from "../../../api/user";

  export default {
    name:'check-reset',
    data(){
      let validatePass = (rule, value, callback) => {
        if (value === '123') {
          callback(new Error('请输入正确的账户'));
        } else {
          this.codeEnable = false;
          callback();
        }
      };
      return {
        form: {
          email: '',
          checkCode: '',
        },
        rules:{
          email:[
            { required: true, message: '请输入邮箱/手机号', trigger: 'blur' },
            { validator: validatePass, trigger: 'blur' },
          ],
          checkCode:[
            { required: true, message: '请输入验证码', trigger: 'blur' }
          ]
        },
        show: true,
        count: '',
        timer: null,
        checkLoading:true,
        codeEnable:true
      }
    },
    methods:{
      getCode(){
        sendLoginCode({encrypted:this.form.email}).then(res=>{
          console.info(res)
          if(res.data.data){
            this.$message({
              message: '验证码发送成功',
              type: 'success'
            });
          }else{
            this.$message.error(res.data.msg);
          }
        })

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
      onSubmit(){
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.checkLoading = false

            checkLoginWithCode({
              encrypted:this.form.email,
              authCode:this.form.checkCode
            }).then(res=>{
              console.info(res)
              this.checkLoading = true
              if(res.data.data){
                this.$emit('next-step','reset-password',this.form.email)
              }else{
                this.$message.error(res.data.msg);
              }
            })
          } else {
            return false;
          }
        });
      }
    }
  }
</script>
<style lang="scss" scoped>
  .pa_enter{
    width: 300px;
    .tipTitle{
      line-height: 50px;
      font-weight: 600;
    }
  }
  .verification_code{
    width: 100%;
    height: 41px;
  }
  .next_button{
    width: 100%;
    border-width: 0;
    background: linear-gradient(to right, #3ea4fd, #66e8fd) !important;
    &:hover{
      opacity: 0.6;
    }
  }
</style>
