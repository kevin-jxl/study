<template>
	<div class="app-container calendar-list-container">
		<div class="filter-container crud-menu clearfix">
			<!--检索框-->
			<div class="form_search clearfix" style="padding:0 10px;">
				<el-form :inline="true" :model="listQuery" class="search-form-inline">
					<el-form-item label="输入搜索:">
						<el-input v-model="listQuery.serviceName" placeholder="机构名称/机构编码"></el-input>
					</el-form-item>
					<el-form-item label="所属车场" v-if="isAdmin">
						<el-select v-model="listQuery.administrativeOrganId" placeholder="请选择车场">
							<el-option label="全部" value=""></el-option>
							<el-option :label="v.name" :value="v.organ_id" v-for="(v,i) in SelectList" :key="i"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button class="el-icon-search" @click="onSubmit" size="mini"></el-button>
					</el-form-item>
					<el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-start">
						<span class="el-icon-refresh-right " style="padding:5px;" @click="handleReload()"></span>
					</el-tooltip>
					<el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-start" v-if="userData.permissions.includes('sys_mechanism_rm')">
						<span class="el-icon-download " style="padding:5px;margin-right: 10px;" @click="handleExport()"></span>
					</el-tooltip>
				</el-form>
				<p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:-5px 0 28px 0;"></p>
			</div>
			<div class="float-fl table-title">数据列表</div>
			<el-button class="float-fr" type="primary" @click="handleCreate" icon="edit" size="mini" style="margin-bottom:10px;"
			 v-if="userData.permissions.includes('sys_mechanism_add')">添加机构
			</el-button>
		</div>
		<div style="height:calc(100% - 120px)">
			<z-table :data="tableData" @size-change="handleSizeChange" @current-change="handleCurrentChange" :total='total'>
				<div slot="table-column">
					<el-table-column fixed align="center" type="index" width="50" label="序号"></el-table-column>
					<el-table-column align="center" label="业务机构名称">
						<template slot-scope="scope">
							<span>{{scope.row.serviceName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="业务机构编码">
						<template slot-scope="scope">
							<span>{{scope.row.code }}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="所属车场">
						<template slot-scope="scope">
							<span>{{scope.row.organName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="负责人">
						<template slot-scope="scope">
							<span>{{scope.row.adminName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="联系方式">
						<template slot-scope="scope">
							<span>{{scope.row.phone}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="创建人">
						<template slot-scope="scope">
							<span>{{scope.row.createName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="创建时间" width="200">
						<template slot-scope="scope">
							<span>{{scope.row.createTime}}</span>
						</template>
					</el-table-column>
					<el-table-column label="操作" align="center" width="280">
						<template slot-scope="scope">
							<!-- <el-button size="mini" type="text" @click="handleUpdate(scope.row,true)">详细信息</el-button> -->
							<el-button size="mini" type="text" v-if="userData.permissions.includes('sys_mechanism_edit')" @click="handleUpdate(scope.row,false)">编辑</el-button>
							<el-button size="mini" type="text" v-if="userData.permissions.includes('sys_mechanism_del')" @click="handleDelete(scope.row)">删除</el-button>
						</template>
					</el-table-column>
				</div>
			</z-table>
		</div>

		<!-- 编辑弹框 -->
		<div class="dialog_mask_mine">
			<el-dialog :title="dialogStatus=='create'?'添加业务机构':'编辑业务机构'" :visible.sync="dialogFormVisible" width="45%" :close-on-click-modal="false">
				<div class="fix-width">
					<el-form :model="form" class="clearfix" ref="form" :rules="rules">
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="业务机构名称" :label-width="formLabelWidth" prop="serviceName">
									<el-input v-model="form.serviceName" :maxlength="20" autocomplete="off" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="业务机构编码" :label-width="formLabelWidth" prop="code">
									<el-input v-model="form.code" autocomplete="off" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="所属车场" :label-width="formLabelWidth" v-if="isAdmin">
									<el-select v-model="form.administrativeOrganId" placeholder="请选择" :disabled="disabled">
										<el-option label="请选择" value=""></el-option>
										<el-option :label="v.name" :value="v.organ_id" v-for="(v,i) in SelectList" :key="i"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="负责人" :label-width="formLabelWidth" prop="adminName">
									<el-input v-model="form.adminName" :maxlength="20" autocomplete="off" :disabled="disabled"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="联系方式" :label-width="formLabelWidth" prop="phone">
									<el-input v-model="form.phone" autocomplete="off" :disabled="disabled" :maxlength="11"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-form-item label="备注信息" :label-width="formLabelWidth" style="width: 100%;">
							<el-input type="textarea" v-model="form.remark" autocomplete="off" :maxlength="50" :rows="4" :disabled="disabled"></el-input>
						</el-form-item>
					</el-form>
				</div>
				<div slot="footer" class="dialog-footer" v-if="!disabled">
					<el-button type="primary" @click="handleSubmit('form')">{{dialogStatus=='create'?'确定':'保存'}}</el-button>
					<el-button @click="dialogFormVisible = false">取 消</el-button>
				</div>
			</el-dialog>
		</div>
	</div>

</template>

<script>
	import {
		mechanismList, //列表
		addMechanism, //添加
		detailMechanism, //详情
		updateMechanism, //编辑
		deleteMechanism, //删除
		selectMechanism, //机构下拉
		downloadExcel
	} from "@/api/mechanism/mechanism";
	import ZTable from '@/components/ZTable.vue';
	export default {
		data() {
			const checkMobile = (rule, value, cb) => {
				const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
				if (regMobile.test(value)) {
					// 合法的手机号码
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
					serviceName: '',
					administrativeOrganId: '',
					userId: ''
				},
				SelectList: null,
				form: {
					serviceName: '',
					code: '',
					administrativeOrganId: '',
					adminName: '',
					phone: '',
					createId: '',
					remark: '',
				},
				rules: {
					adminName: [
					    { required: true, message: "负责人不能为空",trigger: "blur"},
					    { min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur" },
						{
							validator: checkName,
							trigger: 'blur'
						}
					],
					serviceName: [
					    { required: true, message: "业务机构名称不能为空",trigger: "blur"},
					    { min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur" }
					],
					phone: [{
							required: true,
							message: "手机号不能为空",
							trigger: "blur"
						},
						{
							validator: checkMobile,
							trigger: 'blur'
						}
					],
					code: [{
							required: true,
							message: "业务机构编码不能为空",
							trigger: "blur"
						},
						{
							validator: checkCode,
							trigger: 'blur'
						}
					]
				},
				formLabelWidth: '120px', //弹框宽度
				dialogTableVisible: false,
				dialogFormVisible: false,
				tableData: null,
				dialogStatus: null,
				disabled: false,
				total: null,
				isAdmin: false,
				userData: null
			}
		},
		components: {
			'ZTable': ZTable
		},
		inject: ['reload'],
		created() {
			this.userData = JSON.parse(sessionStorage.getItem('userData'))
			this.listQuery.userId = this.userData.sysUser.userId;
			this.form.createId = this.userData.sysUser.userId;
			if (this.userData.roles.length > 0) {
				this.userData.roles.forEach(item => {
					if (item == 'ROLE_TENANT' || item == 'ROLE_ADMIN') {
						this.isAdmin = true;
					} else {
						this.listQuery.administrativeOrganId = this.userData.sysUser.organId
					}
				})
			} else {
				this.listQuery.administrativeOrganId = this.userData.sysUser.organId
			}
			this.getList();
			this.getSelectList();
		},
		methods: {
			getList() { //获取列表
				let _this = this;
				_this.listLoading = true;
				mechanismList(_this.listQuery).then(response => {
					_this.tableData = response.data.records;
					_this.total = response.data.total;
					_this.listLoading = false;
				});
			},
			getSelectList() { //获取列表
				let _this = this;
				selectMechanism({
					userId: this.userData.sysUser.userId
				}).then(response => {
					_this.SelectList = response.data;
				});
			},
			//搜索
			onSubmit() {
                this.listQuery.serviceName = this.listQuery.serviceName.trim();
                this.listQuery.serviceName= this.listQuery.serviceName.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
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
			handleUpdate(row, status) {
				this.disabled = status;
				this.dialogStatus = 'update'
				detailMechanism(row.serviceId).then(res => {
					let response = res.data.data;
					this.form = {
						serviceName: response.serviceName,
						code: response.code,
						administrativeOrganId: response.administrativeOrganId,
						adminName: response.adminName,
						phone: response.phone,
						serviceId: response.serviceId,
						remark: response.remark,
					}
				});
				this.dialogFormVisible = true;
			},
			//回车事件
			handleFilter() {
				this.listQuery.page = 1;
			},
			// 新增
			handleCreate() {
				this.dialogStatus = 'create'
				this.form = {
					serviceName: '',
					code: '',
					administrativeOrganId: '',
					adminName: '',
					phone: '',
					createId: this.userData.sysUser.userId?this.userData.sysUser.userId:'',
					remark: '',
				}
				//不是管理员默认增加administrativeOrganId
				if (!this.isAdmin) {
					this.form.administrativeOrganId = this.$store.state.userData.sysUser.organId
				}
				this.dialogFormVisible = true;
				this.disabled = false;
			},
			//数据提交新增or编辑
			handleSubmit(form) {
				let _this = this;

				//校验必填项
				this.$refs[form].validate((valid) => {
					if (valid) {
						if (this.dialogStatus == 'create') {
							addMechanism(_this.form).then((response) => {
								if(response.data.data==false){
									_this.$message.error(response.data.msg);
								}else{
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
							updateMechanism(_this.form).then((response) => {
								if(response.data.data==false){
									_this.$message.error(response.data.msg);
								}else{
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
				const rowObj = row
				this.$confirm(
					"确定要删除该条信息吗?",
					"提示框", {
						confirmButtonText: "确定",
						cancelButtonText: "取消",
						type: "warning"
					}
				).then(() => {
					deleteMechanism(rowObj.serviceId)
						.then(response => {
							this.dialogFormVisible = false;
							this.getList();
							this.$notify({
								title: "成功",
								message: "删除成功",
								type: "success",
								duration: 2000
							});
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
			handleExport(){
				if(this.tableData.length>0){
					downloadExcel(this.listQuery,'业务机构').then(response => {});
				}else{
					this.$message.error('当前表格暂无数据，请补充数据在导出！');
				}
			}
		}
	}
</script>

<style lang="scss">
	// .messageBox-del{
	// 	width: 285px!important;
	// }
</style>
