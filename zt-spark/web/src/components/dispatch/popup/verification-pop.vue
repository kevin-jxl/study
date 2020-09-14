<template>
    <div>
         <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="110px" class="demo-ruleForm">
           <el-row :gutter="20">
             <el-col :span="20">
               <!-- 置灰，不可切换 -->
                  <el-form-item label="完成情况：" prop="delivery" > 
                      <el-switch v-model="ruleForm.delivery" active-text="已完成" disabled></el-switch>
                  </el-form-item>
             </el-col>
             <el-col :span="20">
                  <el-form-item label="是否按期完成：" prop="resource" style="width:500px;">
                      <el-radio-group v-model="ruleForm.resource"> 
                      <el-radio label="是"></el-radio>
                      <el-radio label="否"></el-radio>
                      </el-radio-group>
                  </el-form-item>
             </el-col>
              <el-col :span="20">
                  <el-form-item label="完成情况说明：" prop="desc" style="width:620px;">
                      <el-input type="textarea" v-model="ruleForm.desc" placeholder="字数不超过200字" autocomplete="off" :maxlength="200" :rows="4"></el-input>
                  </el-form-item>
             </el-col>
              <el-col :span="20">
                 <el-form-item label="音频信息：" prop="">
					 <div class="voice-item" v-if="attachmentArray.voice">
						 <div v-for="(item,index) in attachmentArray.voice" :key="index">
						     <i class="el-icon-video-play icon" style=" color: #ffffff; margin-left:10px"></i>
						 </div>
					 </div>
                  </el-form-item>
             </el-col>
			 <el-col :span="20">
			     <el-form-item label="图片信息：" prop="" >
						<div v-if="attachmentArray.img">
						        <el-image 
						            v-for="(item,index) in attachmentArray.img" :key="index"
						            class="img-view"
						            :src="item.path"
						            fit="fill">
						        </el-image>
						</div>
			      </el-form-item>
			 </el-col>
			 <el-col :span="20">
			     <el-form-item label="视频信息：" prop="">
						<div v-if="attachmentArray.video">
						    <el-image
						         v-for="(item,index) in attachmentArray.video" :key="index"
						         class="img-view"
						        src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
						        fit="fill">
						    </el-image>
						</div>
			      </el-form-item>
			 </el-col>
           </el-row>
           
           
            

            <el-form-item size="small" style="width:98%; text-align:right;">
                <el-button type="primary" @click="submitForm('ruleForm')" size="mini">保存</el-button>
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
        },
		attachmentArray:{
		    type:Array,
		    default:()=>[]
		},
      },
    data() {
      return {
        dialogImageUrl: '',
        dialogVisible: false,
        ruleForm: {
          // name: '',
          delivery: true,
          resource: '是',
          describe: ''
        },
        rules: {
          // name: [
          //   { required: true, message: '请输入完成结果', trigger: 'blur' },
          //   { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
          // ]
        }
      };
    },
    methods: {
      submitForm(formName) {
         let _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(!_this.ruleForm.delivery) {
				return _this.$message.error('维修未完成')
			}
             let parmes = {
               workOrderId: _this.workOrderId,//工单id
               description: _this.ruleForm.desc, // 完成情况说明
               isAchieve: _this.ruleForm.resource =="是" ? '1' : '0'
            }
			_this.$api.putWorkOrderVerify(parmes).then(res => {
				console.log(JSON.stringify('核查：'+JSON.stringify(res)));
				_this.$notify({
					title: "成功",
					message: "核查成功",
					type: "success",
					duration: 2000
				});
				_this.$emit('verification-success');
				_this.cancel(formName);
			})
			console.log(JSON.stringify(parmes));
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      cancel(formName) {
        this.$refs[formName].resetFields();
        this.$emit('cancel-verification-pop',false);
      },
       handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePictureCardPreview(file) {
        console.log(file)
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      beforeAvatarUpload(file) {
        console.log(file)
        const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
        // let isJPG = true;
        // const isJPG =  file.type === 'image/png';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      }
    }
  }
</script>
<style>
  .el-radio-group {
    /* display: inline-block; */
    /* line-height: 1; */
    /* vertical-align: middle; */
    height: 28px !important;
    display: flex !important;
    flex-direction: row !important;
    justify-content: flex-start !important;
    align-items: center !important;
  }
</style>

<style lang="scss" scoped>
.demo-ruleForm {
    padding:0 20px;
}
.el-form-item__content {
    line-height: 40px !important;
    position: relative !important;
    font-size: 14px !important;
    display: inline-block !important;
}
.img-view {
            width: 65px; height: 65px; margin-right: 10px;
            border-radius: 4px;
        }
        .voice-item {
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            div {
                position: relative;
                width: 150px;
                height: 25px;
                margin-top: 10px;
                background-color: #33AEFE;
                border-radius: 6px;
                .icon {
                    position: absolute;
                    top:50%;
                    left:0;
                    -webkit-transform: translateX(0) translateY(-50%);
                }
            }
        }
</style>