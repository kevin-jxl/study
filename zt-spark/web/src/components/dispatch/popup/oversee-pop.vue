<template>
    <div>
         <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
           <el-row :gutter="20">
             <el-col :span="20">
                 <el-form-item label="督办事由：" prop="reason">
                <el-select v-model="ruleForm.reason" placeholder="请选择督办事由">
                <el-option label="超时未处理,请及时处理" value="超时未处理,请及时处理"></el-option>
                </el-select>
            </el-form-item>
             </el-col>
             <el-col :span="20">
                <el-form-item label="具体事项：" prop="desc">
                <el-input type="textarea" v-model="ruleForm.desc" style="width:439px" autocomplete="off" :maxlength="200" :rows="4" placeholder="请填写具体事项"></el-input>
            </el-form-item>
             </el-col>
             <el-col :span="20">
                <el-form-item label="督办时限：" prop="timeLin">
                <el-select v-model="ruleForm.timeLin" placeholder="请选择督办时限">
                <el-option label="1小时" value="1"></el-option>
                <el-option label="1小时" value="2"></el-option>
                </el-select>
                <el-checkbox v-model="ruleForm.checked" style="margin-left:5px;">短信提醒</el-checkbox>
            </el-form-item>
             </el-col>
           </el-row>
           
            <el-form-item size="small" style="width:539px; margin-top:50px;  text-align:right;">
                <el-button type="primary" @click="submitForm('ruleForm')" size="mini">派发</el-button>
                <el-button @click="cancel('ruleForm')" size="mini">取消</el-button>
            </el-form-item>
            </el-form>
    </div>
</template>

<script>
    export default {
      props: {
        workOrderId: {
          type: String,
          default:''
        }
      },
    data() {
      return {
        ruleForm: {
          reason: '',
          desc: '',
          timeLin: '',
          checked: false
        },
        rules: {
          reason: [
            { required: true, message: '请选择督办事由', trigger: 'change' }
          ],
          //  desc: [
          //   { required: true, message: '请填写具体事项', trigger: 'blur' }
          // ],
          // date: [
          //   { type: 'date', required: true, message: '请选择时限', trigger: 'change' }
          // ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        let _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // alert('submit!');
            let parmes = {
               workOrderId: _this.workOrderId,//工单id
               reasons: _this.ruleForm.reason, //督办事由
               description: _this.ruleForm.desc, // 具体事项 
               pmin: _this.ruleForm.timeLin,
               smsReminder: _this.ruleForm.checked ? '1' : '0'
            }
            _this.$api.putWorkOrderSupervise(parmes).then((result) => {
				console.log(JSON.stringify('督办：'+JSON.stringify(result)));
				_this.$notify({
					title: "成功",
					message: "督办成功",
					type: "success",
					duration: 2000
				});
				_this.$emit('oversee-success');
               _this.cancel(formName)
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      cancel(formName) {
        this.$refs[formName].resetFields();
        this.$emit('cancel-oversee-pop',false);
      }
    },
	beforeDestroy() {
		this.$refs.ruleForm.resetFields();
	}
  }
</script>

<style lang="scss" scoped>

.el-form-item__content {
    line-height: 40px !important;
    position: relative !important;
    font-size: 14px !important;
    display: inline-block !important;
}
</style>