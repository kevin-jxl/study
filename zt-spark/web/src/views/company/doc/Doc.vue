<template>
	<div class="app-container calendar-list-container">
		<div class="filter-container crud-menu clearfix">
			<!--检索框-->
			<div class="form_search clearfix" style="padding:0 10px;">
				<el-form :inline="true" :model="listQuery" class="search-form-inline">
					<el-form-item label="输入搜索:">
						<el-input v-model.trim="listQuery.documentName" placeholder="请输入"></el-input>
					</el-form-item>
					<el-form-item label="合作模式">
						<el-select placeholder="请选择" v-model="listQuery.cooperationMode">
							<el-option label="全部" value=""></el-option>
							<el-option label="合作模式1" value="1"></el-option>
							<el-option label="合作模式2" value="2"></el-option>
							<!-- <el-option :label="v.name" :value="v.com_id" v-for="(v,i) in syscompanyList" :key="i"></el-option> -->
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button class="el-icon-search" @click="onSubmit" size="mini"></el-button>
					</el-form-item>

					<el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-start">
						<span class="el-icon-refresh-right " style="padding:5px;" @click="handleReload()"></span>
					</el-tooltip>
					<el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-start" v-if="userData.permissions.includes('spark_doc_rm')">
						<span class="el-icon-download " style="padding:5px;margin-right: 10px;" @click="handleExport()"></span>
					</el-tooltip>
				</el-form>
				<p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:-5px 0 28px 0;"></p>
			</div>
			<div class="float-fl table-title">文档列表</div>
			<el-button class="float-fr" type="primary" @click="handleCreate('form')" icon="edit" size="mini" style="margin-bottom:10px;" v-if="userData.permissions.includes('spark_doc_add')">添加文档
			</el-button>
		</div>
		<div style="height:calc(100% - 130px)">
			<z-table :data="tableData" @size-change="handleSizeChange" @current-change="handleCurrentChange" :total='total'>
				<div slot="table-column">
					<el-table-column fixed align="center" type="index" width="50" label="序号"></el-table-column>

					<el-table-column align="center" label="文档名称">
						<template slot-scope="scope">
							<span>{{scope.row.documentName}}</span>
						</template>
					</el-table-column>

					<!-- <el-table-column align="center" label="上级行政区域">
                        <template slot-scope="scope">
                            <span>{{scope.row.parentId }}</span>
                        </template>
                    </el-table-column> -->

					<el-table-column align="center" label="文档编号">
						<template slot-scope="scope">
							<span>{{scope.row.code}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="签订方">
						<template slot-scope="scope">
							<span>{{scope.row.signingParty}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="签订时间">
						<template slot-scope="scope">
							<span>{{scope.row.signingTime}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="终止时间">
						<template slot-scope="scope">
							<span>{{scope.row.terminationTime}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="合作模式">
						<template slot-scope="scope">

							<span v-if="scope.row.cooperationMode==1">合作模式1</span>
							<span v-if="scope.row.cooperationMode==2">合作模式2</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="负责人">
						<template slot-scope="scope">
							<span>{{scope.row.leading}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="负责人电话">
						<template slot-scope="scope">
							<span>{{scope.row.tel}}</span>
						</template>
					</el-table-column>
					<el-table-column label="操作" align="center" width="280" v-if="userData.permissions.includes('spark_doc_edit') || userData.permissions.includes('spark_dov_del')">
						<template slot-scope="scope">
							<el-button size="mini" type="text" color="info" @click="handleUpdate(scope.row)" v-if="userData.permissions.includes('spark_doc_edit')">编辑</el-button>
							<el-button size="mini" type="text" @click="handleDelete(scope.row)" v-if="userData.permissions.includes('spark_doc_del')">删除</el-button>
						</template>
					</el-table-column>
				</div>
			</z-table>
		</div>

		<div class="dialog_mask_mine">
			<el-dialog :title="dialogStatus=='create'?'添加文档':'编辑文档'" :visible.sync="dialogFormVisible" width="700px"
			 :close-on-click-modal="false" :before-close="handelClose">
				<div>
					<el-form :model="form" class="clearfix" ref="form" :rules="rules">
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="文档名称:" :label-width="formLabelWidth" prop="documentName">
									<el-input v-model="form.documentName" autocomplete="off" :maxlength="20"></el-input>
								</el-form-item>
							</el-col>
							<!-- <el-col :span="12">
								<el-form-item label="文档编号:" :label-width="formLabelWidth" prop="code">
									<el-input v-model="form.code" autocomplete="off" :maxlength="40"></el-input>
								</el-form-item>
							</el-col> -->
							<el-col :span="12">
								<el-form-item label="签订方:" :label-width="formLabelWidth">
									<el-input v-model="form.signingParty" autocomplete="off" :maxlength="20"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="合作模式:" :label-width="formLabelWidth">
									<el-select placeholder="请选择合作模式" v-model="form.cooperationMode">
										<el-option label="请选择" value=""></el-option>
										<el-option label="合作模式1" value="1"></el-option>
										<el-option label="合作模式2" value="2"></el-option>
										<!-- <el-option :label="v.name" :value="v.com_id" v-for="(v,i) in syscompanyList" :key="i"></el-option> -->
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="负责人:" :label-width="formLabelWidth" prop="leading">
									<el-input v-model="form.leading" autocomplete="off" :maxlength="20"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="联系方式:" :label-width="formLabelWidth" prop="tel">
									<el-input v-model="form.tel" autocomplete="off" :maxlength="11"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="签订时间:" :label-width="formLabelWidth">
									<el-date-picker v-model="form.signingTime" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm"
									 format="yyyy-MM-dd HH:mm" @change="getSTime" :picker-options="pickerOptions0">
									</el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="终止时间:" :label-width="formLabelWidth">
									<el-date-picker v-model="form.terminationTime" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm"
									 format="yyyy-MM-dd HH:mm" @change="getETime" :picker-options="pickerOptions1">
									</el-date-picker>
								</el-form-item>
							</el-col>
						</el-row>
						<el-form-item label="备注信息:" :label-width="formLabelWidth" style="width: 100%;">
							<el-input type="textarea" v-model="form.remark" autocomplete="off" :maxlength="200" :rows="4"></el-input>
						</el-form-item>
						<el-form-item label="文件上传:" :label-width="formLabelWidth" style="text-align: left;max-width: 700px;overflow: auto;">
							<el-upload :action="url+'/spark/upload/singlefile'" list-type="picture" :on-preview="handlePictureCardPreview"
							 :on-remove="handleRemove" :on-success="handleUploadImg" :before-upload="beforeAvatarUpload" :file-list="fileList"
							 ref='upload'>
								<el-button size="small" type="primary" plain>上传</el-button>
							</el-upload>
							<el-dialog :visible.sync="dialogVisible1" :append-to-body="true">
								<img width="100%" :src="dialogImageUrl" alt="">
							</el-dialog>
						</el-form-item>
					</el-form>
				</div>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click="handleSubmit('form')">{{dialogStatus=='create'?'确定':'保存'}}</el-button>
					<el-button @click="handelClose">取 消</el-button>
				</div>
			</el-dialog>

		</div>
	</div>

</template>

<script>
	import {
		getDownloadExcel,
	} from "@/api/index.js";
	import ZTable from '@/components/ZTable.vue';
	import axios from 'axios';
	export default {
		data() {
			const checkMobile = (rule, value, cb) => {
				const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
				if (regMobile.test(value)) {
					// 合法的手机号码
					return cb()
				}
				cb(new Error('手机号码格式不正确'))
			};
			const checkCode = (rule, value, cb) => {
				const regCode = /^[0-9a-zA-Z]+$/;
				if (regCode.test(value)) {
					return cb()
				}
				cb(new Error('仅支持字母和数字输入'))
			};
			const checkName = (rule, value, cb) => {
				const regName = /^[\u4E00-\u9FA5]{0,}$/;
				if (regName.test(value)) {
					return cb()
				}
				cb(new Error('仅支持中文汉字输入'))
			};
			return {
				listQuery: {
					page: 1,
					limit: 30,
					documentName: '',
					cooperationMode:'',
					tableName: '文档管理'
				}, //列表搜索请求参数
				pickerOptions0: {
					disabledDate: (time) => {
						if (this.form.terminationTime != "") {
							return time.getTime() >  new Date(this.form.terminationTime ).getTime();
						}

					}
				},
				pickerOptions1: {
					disabledDate: (time) => {
						return time.getTime() < new Date(this.form.signingTime).getTime()
					}
				},
				dialogImageUrl: '',
				dialogVisible1: false,
				total: 0, //总数
				form: {
					documentName: '',
					signingParty: '',
					signingTime: '',
					terminationTime: '',
					// cooperationMode: '',
					leading: '',
					tel: '',
					remark: '',
					imgs: []
				}, //新增 编辑参数
				rules: { //表单校验
					documentName: [{
							required: true,
							message: "文档名称不能为空",
							trigger: "blur"
						},
						{
							min: 1,
							max: 20,
							message: "长度在 1 到 20 个字符",
							trigger: "blur"
						}
					],
					leading: [{
							required: true,
							message: "负责人不能为空",
							trigger: "blur"
						},
						{
							min: 1,
							max: 20,
							message: "长度在 1 到 20 个字符",
							trigger: "blur"
						},
						{
							validator: checkName,
							trigger: 'blur'
						}
					],
					tel: [{
							required: true,
							message: "手机号不能为空",
							trigger: "blur"
						},
						{
							validator: checkMobile,
							trigger: 'blur'
						}
					]
				},
				fileList: [], //图片回显
				formLabelWidth: '100px', //弹框宽度
				dialogTableVisible: false,
				dialogFormVisible: false,
				tableData: null, //列表数据源
				dialogStatus: null, //判断新增还是编辑 ‘create’:新增，'update':编辑
				userData: {},
				url: ''
			}
		},
		components: {
			'ZTable': ZTable
		},
		inject: ['reload'],
		created() {
			//获取用户信息和按钮权限
			this.userData = JSON.parse(sessionStorage.getItem('userData'))
			this.url = config.services
			this.getList();

			// console.log(this.userData.permissions.includes('sys_area_del1'))
		},
		methods: {
			handleRemove(file, fileList) {
				console.log(file, fileList)
				Array.prototype.remove = function(val) {
					let index = this.indexOf(val);
					if (index > -1) {
						this.splice(index, 1);
					}
				};
				this.form.imgs.remove(file.response.data.attachmentId);
			},
			handlePictureCardPreview(file) {
				console.log(file)
				this.dialogImageUrl = file.url;
				this.dialogVisible1 = true;
			},
			handleUploadImg(file) {
				console.log(this.form)
				console.log(file)
				this.form.imgs.push(file.data.attachmentId)
			},
			getList() { //获取列表
				let _this = this;
				_this.listLoading = true;
				_this.$api.getCompanyfileList(_this.listQuery).then(response => {
					_this.tableData = response.data.records;
					_this.total = response.data.total;
					_this.listLoading = false;
				});
			},

			//搜索
			onSubmit() {
				this.listQuery.documentName = this.listQuery.documentName.replace(
					/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g, "");
				this.getList();
			},
			//修改分页
			handleSizeChange(size) {
				// console.log('page size: ', size);
				this.listQuery.limit = size;
				this.getList();
			},
			//修改分页
			handleCurrentChange(page) {
				this.listQuery.page = page;
				this.getList();
				// console.log('current page: ', page);
			},
			//编辑
			handleUpdate(row) {
				this.dialogStatus = 'update'
				this.dialogFormVisible = true;
				this.$api.getCompanyfileInfo(row.id).then(res => {
					let response = res.data.data;
					this.form = {
						id: response.id,
						documentName: response.documentName,
						signingParty: response.signingParty,
						signingTime: response.signingTime,
						terminationTime: response.terminationTime,
						cooperationMode: response.cooperationMode,
						leading: response.leading,
						tel: response.tel,
						code: response.code,
						remark: response.remark,
						imgs: []
					}
					response.imgs.forEach(item => {
						this.fileList.push({
							name: item.file_name,
							url: config.imgServices + '/' + item.path,
							response: {
								data: {
									attachmentId: item.id
								}
							}
						})
						this.form.imgs.push(item.id)
					})
				});


			},
			//回车事件
			handleFilter() {
				this.listQuery.page = 1;
			},
			// 新增
			handleCreate(formName) {
				this.dialogStatus = 'create'
				this.form = {
					documentName: '',
					signingParty: '',
					signingTime: '',
					terminationTime: '',
					// cooperationMode: '',
					leading: '',
					tel: '',
					remark: '',
					imgs: []
				}
				this.dialogFormVisible = true
			},
			//数据提交新增or编辑
			handleSubmit(form) {
				let _this = this;
				//校验必填项
				this.$refs[form].validate((valid) => {
					if (valid) {
						if (this.dialogStatus == 'create') {
							this.$api.getCompanyfileAdd(_this.form).then((response) => {

								if (response.data.data == false) {
									_this.$message.error(response.data.msg);
								} else {
									this.fileList = []
									this.$refs.upload.clearFiles();
									_this.dialogFormVisible = false;
									_this.getList();
									_this.$notify({
										title: "成功",
										message: "创建成功",
										type: "success",
										duration: 2000
									});
								}

							});
						} else if (this.dialogStatus == 'update') {
							this.$api.getCompanyfileUpdate(_this.form).then((response) => {
								if (response.data.data == false) {
									_this.$message.error(response.data.msg);
								} else {
									this.$refs.upload.clearFiles();
									this.fileList = []
									_this.dialogFormVisible = false;
									_this.getList();
									_this.$notify({
										title: "成功",
										message: "修改成功",
										type: "success",
										duration: 2000
									});
								}
							});
						}
					} else {
						console.log('error submit!!');
						return false;
					}
				});

			},
			//删除
			handleDelete(row) {
				// console.log(row)
				const rowObj = row
				this.$confirm(
					"确定要删除该条信息吗?",
					"提示框", {
						confirmButtonText: "确定",
						cancelButtonText: "取消",
						type: "warning"
					}
				).then(() => {
					this.$api.getCompanyfileDelete(rowObj.id)
						.then(response => {
							if (response.data.data == false) {
								this.$message.error(response.data.msg);
							} else {
								this.dialogFormVisible = false;
								this.getList();
								this.$notify({
									title: "成功",
									message: "删除成功",
									type: "success",
									duration: 2000
								});
							}
						})
						.catch(() => {
							this.$notify({
								title: "失败",
								message: "删除失败",
								type: "error",
								duration: 2000
							});
						});
				});
			},
			//页面刷新
			handleReload() {
				this.reload()
			},
			//导出表格模板字符串直接跳转
			handleExport() {
				if (this.tableData.length == 0) {
					this.$message.error('当前表格暂无数据，请补充数据再导出！');
				} else {
					getDownloadExcel(this.listQuery, '/spark/companyfile/downloadExcel').then(response => {});
				}
			},
			getSTime(val) {
				this.form.signingTime = val;
				console.log(val)
			},
			getETime(val) {
				console.log(val)
				this.form.terminationTime = val;
			},
			//关闭弹框清除附件
			handelClose() {
				this.dialogFormVisible = false;
				this.fileList = [];
				this.$refs.upload.clearFiles();
			},
			beforeAvatarUpload(file) {
				console.log(file)
				const isJPGPNG = file.type === 'image/jpeg' || file.type === 'image/png';

				if (!isJPGPNG) {
					this.$message.error('只能上传 JPG 或 PNG格式的图片文件!');
				}
				return isJPGPNG;
			}
		}
	}
</script>

<style lang="scss" scoped="scoped">
	// .messageBox-del{
	// 	width: 285px!important;
	// }
	.el-date-editor.el-input{
		width: 202px!important;
	}
</style>
