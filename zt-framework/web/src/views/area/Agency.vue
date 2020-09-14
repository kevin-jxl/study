<template>
	<div class="app-container calendar-list-container">
		<div class="filter-container crud-menu clearfix">
			<!--检索框-->
			<div class="form_search clearfix" style="padding:0 10px;">
				<el-form :inline="true" :model="listQuery" class="search-form-inline">
					<el-form-item label="输入搜索:">
						<el-input  v-model="listQuery.name" placeholder="机构名称/机构编号"></el-input>
					</el-form-item>
					<el-form-item label="企业名称" v-if="isAdmin">
						<el-select v-model="listQuery.comId" placeholder="企业名称">
							<el-option label="全部" value=""></el-option>
							<el-option :label="v.name" :value="v.com_id" v-for="(v,i) in syscompanyList" :key="i"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button class="el-icon-search" @click="onSubmit" size="mini"></el-button>
					</el-form-item>
					<el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-start">
						<span class="el-icon-refresh-right " style="padding:5px;" @click="handleReload()"></span>
					</el-tooltip>
					<el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-start" v-if="userData.permissions.includes('sys_agency_rm')">
						<span class="el-icon-download " style="padding:5px;margin-right: 10px;" @click="handleExport()"></span>
					</el-tooltip>
				</el-form>
				<p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:-5px 0 28px 0;"></p>
			</div>
			<div class="float-fl table-title">数据列表</div>
			<el-button class="float-fr" type="primary" @click="handleCreate" icon="edit" size="mini" style="margin-bottom:10px;"
			 v-if="userData.permissions.includes('sys_agency_add')">添加机构
			</el-button>
		</div>
		<div style="height:calc(100% - 120px)">
			<z-table :data="tableData" @size-change="handleSizeChange" @current-change="handleCurrentChange" :total='total'>
				<div slot="table-column">
					<el-table-column fixed align="center" type="index" width="50" label="序号"></el-table-column>

					<el-table-column align="center" label="机构名称">
						<template slot-scope="scope">
							<span>{{scope.row.name}}</span>
						</template>
					</el-table-column>

					<!-- <el-table-column align="center" label="上级机构">
                        <template slot-scope="scope">
                            <span>{{scope.row.sjxzqy }}</span>
                        </template>
                    </el-table-column> -->

					<el-table-column align="center" label="机构编码">
						<template slot-scope="scope">
							<span>{{scope.row.code}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="负责人">
						<template slot-scope="scope">
							<span>{{scope.row.adminname}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="联系电话">
						<template slot-scope="scope">
							<span>{{scope.row.phone}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="创建人">
						<template slot-scope="scope">
							<span>{{scope.row.createNmae}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="创建时间" width="200">
						<template slot-scope="scope">
							<span>{{scope.row.createTime}}</span>
						</template>
					</el-table-column>

					<el-table-column label="操作" align="center" width="280" v-if="userData.permissions.includes('sys_agency_edit') || userData.permissions.includes('sys_agency_del')">
						<template slot-scope="scope">
							<el-button size="mini" type="text" v-if="userData.permissions.includes('sys_agency_edit')" @click="handleUpdate(scope.row)">编辑</el-button>
							<el-button size="mini" type="text" v-if="userData.permissions.includes('sys_agency_del')" @click="handleDelete(scope.row)">删除</el-button>
						</template>
					</el-table-column>
				</div>
			</z-table>
		</div>

		<!-- 编辑弹框 -->
		<div class="dialog_mask_mine">
			<el-dialog :title="dialogStatus=='create'?'添加行政机构':'编辑行政机构'" :visible.sync="dialogFormVisible" width="45%" :close-on-click-modal="false">
				<div class="fix-width">
					<el-form :model="form" class="clearfix" ref="form" :rules="rules">
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="机构名称" :label-width="formLabelWidth" prop="name">
									<el-input v-model="form.name" autocomplete="off" :maxlength="20"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="机构代码" :label-width="formLabelWidth" prop="code">
									<el-input v-model="form.code" autocomplete="off"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="负责人" :label-width="formLabelWidth" prop="adminname">
									<el-input v-model="form.adminname" autocomplete="off" :maxlength="20"></el-input>
								</el-form-item>
							</el-col>

							<el-col :span="12">
								<el-form-item label="联系电话" :label-width="formLabelWidth" prop="phone">
									<el-input v-model="form.phone" autocomplete="off" :maxlength="11"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="所属企业" :label-width="formLabelWidth" v-if="isAdmin">
									<el-select v-model="form.comId" placeholder="请选择所属企业">
										<el-option label="请选择" value=""></el-option>
										<el-option :label="v.name" :value="v.com_id" v-for="(v,i) in syscompanyList" :key="i"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<!-- <el-col :span="12">
								<el-form-item label="所属区域" :label-width="formLabelWidth">
									<el-select v-model="form.areaId" placeholder="请选择所属区域">
										<el-option label="请选择" value=""></el-option>
										<el-option :label="v.area_name" :value="v.area_id" v-for="(v,i) in areaList" :key="i"></el-option>
									</el-select>
								</el-form-item>
							</el-col> -->

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
	</div>

</template>

<script>
	import {
		agencyList, //列表
		addAgency, //添加
		detailAgency, //详情
		updateAgency, //编辑
		deleteAgency, //删除
		syscompany, //企业下拉
		sysadministrativearea,
		downloadExcel
	} from "@/api/area/agency";
	import ZTable from '@/components/ZTable.vue';
	// import config from '../../../public/static/config.js';
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
					comId: null,
					userId: '',
					name: ''
				},
				form: {
					name: '',
					code: '',
					adminname: '',
					phone: '',
					createId: '',
					remark: '',
					comId: '',
					areaId: ''
				},
				rules: {
					name: [
					    { required: true, message: "机构名称不能为空",trigger: "blur"},
					    { min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur" }
					],
					adminname: [
					    { required: true, message: "负责人不能为空",trigger: "blur"},
					    { min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur" },
						{
							validator: checkName,
							trigger: 'blur'
						}
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
							message: "机构代码不能为空",
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
				drawer: false,
				direction: 'rtl',
				syscompanyList: null,
				areaList: null,
				total: null,
				isAdmin: true,
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
			// if (this.userData.roles.length > 0) {
			// 	this.userData.roles.forEach(item => {
			// 		if (item == 'ROLE_ADMIN') {
			// 			this.isAdmin = true;
			// 		} else {
			// 			this.listQuery.comId = this.userData.sysUser.companyId
			// 		}
			// 	})
			// } else {
			// 	this.listQuery.comId = this.userData.sysUser.companyId
			// }
			// console.log(this.listQuery)
			this.getList(); //表格列表接口请求
			this.getEnterprise(); //企业下拉请求
			// this.getAreaList(); //区域下拉
		},
		methods: {
			getList() { //获取列表
				let _this = this;
				_this.listLoading = true;
				agencyList(_this.listQuery).then(response => {
					_this.tableData = response.data.records;
					_this.total = response.data.total;
					_this.listLoading = false;
				});
			},
			getEnterprise() {
				let _this = this;
				syscompany({
					tenantId: this.userData.sysUser.tenantId
				}).then(response => {
					_this.syscompanyList = response.data;
				});
			},
			getAreaList() {
				let _this = this;
				sysadministrativearea().then(response => {
					_this.areaList = response.data;
				});
			},
			//搜索
			onSubmit() {
                this.listQuery.name = this.listQuery.name.trim();
                this.listQuery.name= this.listQuery.name.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
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
				detailAgency(row.organId).then(res => {
					let response = res.data.data;
					this.form = {
						name: response.name,
						code: response.code,
						adminname: response.adminname,
						phone: response.phone,
						remark: response.remark,
						areaId: response.areaId,
						comId: response.comId,
						organId: response.organId
					}
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
				this.form = {
					name: '',
					code: '',
					adminname: '',
					phone: '',
					createId: this.userData.sysUser.userId,
					remark: '',
					comId: ''
				}
				//不是管理员默认增加comid
				if (!this.isAdmin) {
					this.form.comId = this.userData.sysUser.companyId
				}
			},
			//新增or修改
			handleSubmit(form) {
				let _this = this;
				this.$refs[form].validate((valid) => {
					if (valid) {
						if (this.dialogStatus == 'create') {
							addAgency(_this.form).then((response) => {
								
								
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
							updateAgency(_this.form).then((response) => {
								
								
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
					deleteAgency(rowObj.organId)
						.then(response => {
							if(response.data.data==false){
								this.$message.error(response.data.msg);
							}else{
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
			handleExport(){
				if(this.tableData.length>0){
					downloadExcel(this.listQuery,'行政机构').then(response => {});
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
