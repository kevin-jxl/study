<template>
  <div class="pa_enter">
    <el-form ref="form" :model="form" status-icon label-width="120px" :rules="rules">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="新的登陆密码" prop="password">
            <el-input
              v-model="form.password"
              type="password"
              placeholder="输入新的密码"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="确认新的登陆密码" prop="doublePassword">
            <el-input
              type="password"
              v-model="form.doublePassword"
              placeholder="确认新的密码"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button v-if="resetLoading" type="primary" @click="onSubmit" class="next_button" round>下一步</el-button>
        <el-button v-else type="primary" @click="onSubmit" class="next_button" round :loading="true">修改中</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import {resetPassword} from "../../../api/user";
  export default {
    name:'reset-password',
    data(){
      let validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.form.doublePassword !== '') {
            this.$refs.form.validateField('doublePassword');
          }
          callback();
        }
      };
      let validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.form.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        form: {
          password: '',
          doublePassword: ''
        },
        rules:{
          password:[
            { validator: validatePass, trigger: 'blur' }
          ],
          doublePassword:[
            { validator: validatePass2, trigger: 'blur' }
          ]
        },
        show: true,
        count: '',
        timer: null,
        resetLoading:true
      }
    },
    props:{
      currentLogin: String
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
      onSubmit(){
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.resetLoading = false

            resetPassword({
              encrypted:this.currentLogin,
              newpassword1:this.form.doublePassword
            }).then(res=>{
              this.resetLoading = true
              if(res.data.data){
                this.$message({
                  message: '修改成功',
                  type: 'success'
                });
                this.$emit('next-step','reset-result')
              }else{
                this.$message.error(res.data.msg);
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>
<style lang="scss" scoped>
  .pa_enter{
    width: 350px;
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
