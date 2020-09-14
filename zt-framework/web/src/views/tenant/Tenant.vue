<template>
	<div class="app-container calendar-list-container">
		<div class="filter-container crud-menu clearfix">
			<!--检索框-->
			<div class="form_search clearfix" style="padding:0 10px;">
				<el-form :inline="true" :model="listQuery" class="search-form-inline">
					<el-form-item label="请输入">
						<el-input v-model="listQuery.tenantName" placeholder="租户名称"></el-input>
					</el-form-item>
					<el-form-item label="管理员:">
						<el-input v-model="listQuery.adminName" placeholder="管理员"></el-input>
					</el-form-item>
					<el-form-item label="联系方式:">
						<el-input v-model="listQuery.adminPhone" placeholder="联系方式"></el-input>
					</el-form-item>

					<el-form-item>
						<el-button class="el-icon-search" @click="onSubmit" size="mini"></el-button>
					</el-form-item>
					<el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-start">
						<span class="el-icon-refresh-right " style="padding:5px;" @click="handleReload()"></span>
					</el-tooltip>
					<el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-start" v-if="userData.permissions.includes('sys_tenant_rm')">
						<span class="el-icon-download " style="padding:5px;margin-right: 10px;" @click="handleExport()"></span>
					</el-tooltip>
				</el-form>
				<p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:-5px 0 28px 0;"></p>
			</div>
			<div class="float-fl table-title">数据列表</div>
			<el-button class="float-fr" type="primary" @click="handleCreate" icon="edit" size="mini" style="margin-bottom:10px;"
			 v-if="userData.permissions.includes('sys_tenant_add')">添加租户
			</el-button>
		</div>
		<div style="height:calc(100% - 120px)">
			<z-table :data="tableData" @size-change="handleSizeChange" @current-change="handleCurrentChange" :total='total'>
				<div slot="table-column">
					<el-table-column fixed align="center" type="index" width="50" label="序号"></el-table-column>

					<el-table-column align="center" label="租户名称">
						<template slot-scope="scope">
							<span>{{scope.row.tenantName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="租户编码">
						<template slot-scope="scope">
							<span>{{scope.row.tenantCode}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="上级租户">
						<template slot-scope="scope">
							<span>{{scope.row.tenantParent }}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="所属区域">
						<template slot-scope="scope">
							<span>{{scope.row.areaName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="管理员">
						<template slot-scope="scope">
							<span>{{scope.row.adminName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="联系方式">
						<template slot-scope="scope">
							<span>{{scope.row.adminPhone}}</span>
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
					<el-table-column align="center" label="是否启用" v-if="userData.permissions.includes('sys_tenant_edit')">
						<template slot-scope="scope">
							<el-switch v-model="scope.row.tenantFlag" @change="switchChange(scope.row)">
							</el-switch>
						</template>
					</el-table-column>
					<el-table-column label="操作" align="center" width="280" v-if="userData.permissions.includes('sys_tenant_edit') || userData.permissions.includes('sys_tenant_del') || userData.permissions.includes('sys_busuness_sel')">
						<template slot-scope="scope">
							<el-button size="mini" type="text" v-if="userData.permissions.includes('sys_tenant_edit')" @click="handleUpdate(scope.row)">编辑</el-button>
							<el-button size="mini" type="text" v-if="userData.permissions.includes('sys_tenant_del')" @click="handleDelete(scope.row)">删除</el-button>
							<el-button size="mini" type="text" v-if="userData.permissions.includes('sys_busuness_sel')" @click="linkBusiness(scope.row)">业务配置</el-button>
						</template>
					</el-table-column>
				</div>
			</z-table>
		</div>
		<!-- 编辑弹框 -->
		<div class="dialog_mask_mine">
			<el-dialog :title="dialogStatus=='create'?'添加租户':'编辑租户'" :visible.sync="dialogFormVisible" width="45%" :close-on-click-modal="false">
				<div class="fix-width">
					<el-form :model="form" class="clearfix" ref="form" :rules="rules">
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="租户名称" :label-width="formLabelWidth" prop="tenantName">
									<el-input v-model="form.tenantName" :maxlength="20" autocomplete="off"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="上级租户" :label-width="formLabelWidth">
									<el-input v-model="form.tenantParent" :maxlength="20" autocomplete="off"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="租户编码" :label-width="formLabelWidth" prop="tenantCode">
									<el-input v-model="form.tenantCode" autocomplete="off"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="所属区域" :label-width="formLabelWidth">
									<el-select v-model="form.areaId" placeholder="请选择所属区域">
										<el-option :label="v.area_name" :value="v.area_id" v-for="(v,i) in selectArea" :key="i"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="管理员" :label-width="formLabelWidth">
									<el-select v-model="form.admin" placeholder="请选择管理员" @change="handleUserRealname">
										<el-option :label="v.userRealname" :value="v.userId" v-for="(v,i) in userListArr" :key="i"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="联系方式" :label-width="formLabelWidth" prop="adminPhone">
									<el-input v-model="form.adminPhone" autocomplete="off" :maxlength="11"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-form-item label="备注信息" :label-width="formLabelWidth" style="width: 100%;">
							<el-input type="textarea" v-model="form.remark" autocomplete="off" :maxlength="200" :rows="4"></el-input>
						</el-form-item>
					</el-form>
				</div>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click="handleSubmit('form')">{{dialogStatus=='create'?'确定':'保存'}}</el-button>
					<el-button @click="dialogFormVisible = false">取 消</el-button>
				</div>
			</el-dialog>
		</div>
		<router-view></router-view>
	</div>

</template>

<script>
	import {
		tenantList, //列表
		addTenant, //添加
		detailTenant, //详情
		updateTenant, //编辑
		deleteTenant, //删除
		selectAreaTenant,
		userList,
		downloadExcel
	} from "@/api/tenant/tenant";
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
			return {
				listQuery: {
					page: 1,
					limit: 30,
					tenantName: '',
					adminName: '',
					adminPhone: ''
				},
				form: {
					tenantName: '',
					tenantCode: '',
					tenantParent: '',
					areaId: '',
					admin: '',
					adminPhone: '',
					tenantFlag: '1',
					remark: ''
				},
				rules: {
					tenantName: [
					    { required: true, message: "租户名称不能为空",trigger: "blur"},
					    { min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur" }
					],
					adminPhone: [{
							required: true,
							message: "手机号不能为空",
							trigger: "blur"
						},
						{
							validator: checkMobile,
							trigger: 'blur'
						}
					],
					tenantCode: [{
							required: true,
							message: "租户编码不能为空",
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
				tableData: [],
				roleManager_btn_add: true,
				roleManager_btn_edit: true,
				roleManager_btn_del: true,
				dialogStatus: null,
				drawer: false,
				direction: 'rtl',
				total: null,
				selectArea: null,
				userListArr: null, //管理员下来菜单数据
				userData: null,
				updataId:null
			}
		},
		components: {
			'ZTable': ZTable
		},
		inject: ['reload'],
		created() {
			this.userData = JSON.parse(sessionStorage.getItem('userData'))
			this.getList();
			this.getselectArea();
			
		},
		methods: {
			getList() { //获取列表
				let _this = this;
				_this.listLoading = true;
				tenantList(_this.listQuery).then(response => {
					_this.tableData = response.data.records;
					_this.tableData.forEach(item => {
						if (item.tenantFlag == 1) {
							item.tenantFlag = true
						} else if (item.tenantFlag == 0) {
							item.tenantFlag = false
						}
					})
					_this.total = response.data.total;
					_this.listLoading = false;
				});
			},
			getselectArea() { //获取列表
				let _this = this;
				selectAreaTenant({
					userId: this.userData.sysUser.userId
				}).then(response => {
					_this.selectArea = response.data;
				});
			},
			getuserList() { //获取用户列表
				let _this = this;
				userList({}).then(response => {
					_this.userListArr = response.data;
				});
			},
			//搜索
			onSubmit() {
				this.listQuery.tenantName = this.listQuery.tenantName.trim();
				this.listQuery.adminName = this.listQuery.adminName.trim();
                this.listQuery.adminPhone = this.listQuery.adminPhone.trim();
                this.listQuery.tenantName= this.listQuery.tenantName.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
                this.listQuery.adminName= this.listQuery.adminName.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
                this.listQuery.adminPhone= this.listQuery.adminPhone.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
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
				this.getuserList();
				detailTenant(row.tenantId).then(res => {
					let response = res.data.data.sysTenant;
					this.form = {
						tenantId: response.tenantId,
						tenantName: response.tenantName,
						tenantCode: response.tenantCode,
						tenantParent: response.tenantParent,
						areaId: response.areaId,
						admin: response.adminName,
						adminPhone: response.adminPhone,
						tenantFlag: response.tenantFlag,
						remark: response.remark
					}
					this.updataId = response.admin
					
				});
			},
			//回车事件
			handleFilter() {
				this.listQuery.page = 1;
			},
			// 新增
			handleCreate() {
				this.dialogStatus = 'create'
				this.dialogFormVisible = true
				this.getuserList();
				this.form = {
					tenantName: '',
					tenantCode: '',
					tenantParent: '',
					areaId: '',
					admin: '',
					adminPhone: '',
					tenantFlag: '1',
					remark: ''
				}
			},
			handleSubmit(form) {
				let _this = this;
				//校验必填项
				this.$refs[form].validate((valid) => {
					if (valid) {
						if (this.dialogStatus == 'create') {
							addTenant(_this.form).then((response) => {
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
							if(_this.updataId){
								_this.form.admin = _this.updataId;
							}
							updateTenant(_this.form).then((response) => {
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
					deleteTenant(rowObj.tenantId)
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
			linkBusiness(row) {
				this.$router.push({
					path: '/tenant/business',
					query: {
						id: row.tenantId,
					}
				})
			},
			switchChange(row) {
				let status = null,
					_this = this;
				updateTenant({
					tenantFlag: row.tenantFlag ? 1 : 0,
					tenantId: row.tenantId
				}).then(response => {
					_this.$notify({
						title: "成功",
						message: "操作成功",
						type: "success",
						duration: 2000
					});
				});
			},
			//页面刷新
			handleReload() {
				this.reload()
			},
			handleExport(){
				if(this.tableData.length>0){
					downloadExcel(this.listQuery,'租户').then(response => {});
				}else{
					this.$message.error('当前表格暂无数据，请补充数据在导出！');
				}
			},
			handleUserRealname(e){
				this.updataId = e;
			}
		}
	}
</script>

<style lang="scss">
	// .messageBox-del{
	// 	width: 285px!important;
	// }
</style>
