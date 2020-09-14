<template>
	<div class="app-container calendar-list-container">
		<div class="filter-container crud-menu clearfix">
			<!--检索框-->
			<div class="form_search clearfix" style="padding:0 10px;">
				<el-form :inline="true" :model="listQuery" class="search-form-inline">
					<el-form-item label="文件名称:">
						<el-input v-model="listQuery.documentName" placeholder="请输入文件名称"></el-input>
					</el-form-item>
                    <el-form-item label="上传人:">
						<el-input v-model="listQuery.people" placeholder="请输入上传人"></el-input>
					</el-form-item>
					<el-form-item>
						<el-button class="el-icon-search" @click="onSubmit" size="mini"></el-button>
					</el-form-item>
					<el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-start">
						<span class="el-icon-refresh-right " style="padding:5px;" @click="handleReload()"></span>
					</el-tooltip>
					<el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-start">
						<span class="el-icon-download " style="padding:5px;margin-right: 10px;" @click="handleExport()"></span>
					</el-tooltip>
                    <el-tooltip class="item fj-refresh" effect="dark" content="返回" placement="top-start">
						<span class="el-icon-back" style="padding:5px; margin-right: 10px;" @click="returns()"></span>
					</el-tooltip>

				</el-form>
				<p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:-5px 0 28px 0;"></p>
			</div>
			<div class="float-fl table-title">附件列表</div>
			<el-button class="float-fr" type="primary" @click="handleCreate('form')" icon="edit" size="mini" style="margin-bottom:10px;" v-if="userData.permissions.includes('sys_area_add')">
                添加附件
			</el-button>
		</div>
		<div style="height:calc(100% - 130px)">
			<z-table :data="tableData" @size-change="handleSizeChange" @current-change="handleCurrentChange" :total='total'>
				<div slot="table-column">
					<el-table-column fixed align="center" type="index" width="50" label="序号"></el-table-column>

					<el-table-column align="center" label="文件类型">
						<template slot-scope="scope">
							<span>{{scope.row.documentName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="文件名称">
						<template slot-scope="scope">
							<span>{{scope.row.code}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="上传人">
						<template slot-scope="scope">
							<span>{{scope.row.people}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="上传日期">
						<template slot-scope="scope">
							<span>{{scope.row.terminationTime}}</span>
						</template>
					</el-table-column>

                    <el-table-column align="center" label="说明">
						<template slot-scope="scope">
							<span>{{scope.row.tel}}</span>
						</template>
					</el-table-column>

                    <el-table-column align="center" label="附件">
						<template slot-scope="scope">
							<span>{{scope.row.tel}}</span>
						</template>
					</el-table-column>
                    
					<el-table-column label="操作" align="center" width="300" v-if="userData.permissions.includes('sys_area_edit') || userData.permissions.includes('sys_area_del')">
						<template slot-scope="scope">
							<el-button size="mini" type="text" color="info" v-if="userData.permissions.includes('sys_area_edit')" @click="handleUpdate(scope.row)">附件更新</el-button>
							<el-button size="mini" type="text" v-if="userData.permissions.includes('sys_area_del')" @click="handleDelete(scope.row)">删除</el-button>
						</template>
					</el-table-column>
				</div>
			</z-table>
		</div>
		<div class="dialog_mask_mine">
			<el-dialog :title="dialogStatus=='create'?'添加附件':'更新附件'" :visible.sync="dialogFormVisible" width="45%"
			 :close-on-click-modal="false">
				<div class="fix-width">
					<el-form :model="form" class="clearfix" ref="form" :rules="rules">
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="文件类型" :label-width="formLabelWidth" prop="documentName">
									<el-input v-model="form.documentName" autocomplete="off" :maxlength="20"  placeholder="请输入"></el-input>
								</el-form-item>
							</el-col>
                            <el-col :span="12">
								<el-form-item label="文件名称" :label-width="formLabelWidth">
									<el-select placeholder="请选择设备类型" v-model="form.cooperationMode">
										<el-option label="请选择" value=""></el-option>
										<!-- <el-option :label="v.name" :value="v.com_id" v-for="(v,i) in syscompanyList" :key="i"></el-option> -->
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
                        <el-form-item label="备注信息" :label-width="formLabelWidth" style="width: 95%;">
                            <el-input  type="textarea" placeholder="数字不超过200字" v-model="form.remark" autocomplete="off" :maxlength="200" :rows="4"></el-input>
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
	    getDownloadExcel,
	} from "@/api/index.js";
	import ZTable from '@/components/ZTable.vue';
	import axios from 'axios';
	export default {
		data() {
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
                    people:'',
					tableName:'附件列表'
				}, //列表搜索请求参数
				total: 0, //总数
				form: {
					documentName: '',
					signingParty: '',
					terminationTime: '',
					cooperationMode: '',
					leading: '',
					tel: '',
					code: '',
                    remark: '',
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
					code: [{
							required: true,
							message: "行政区域代码不能为空",
							trigger: "blur"
						},
						{
							validator: checkCode,
							trigger: 'blur'
						}
					],
				},
				formLabelWidth: '200px', //弹框宽度
				dialogTableVisible: false,
                dialogFormVisible: false,
				tableData: null, //列表数据源
				dialogStatus: null, //判断新增还是编辑 ‘create’:新增，'update':编辑
				userData: {
					"sysUser": {
						"userId": 179,
						"username": "shuda",
						"password": "4297f44b13955235245b2497399d7a93",
						"userRealname": "王舒达",
						"sex": "0",
						"phone": "17865651278",
						"email": null,
						"empno": "B000001",
						"signName": "",
						"deptId": 1,
						"salt": null,
						"avatar": null,
						"userFlag": "1",
						"passwordEndtime": null,
						"createTime": "2020-08-19 10:07:11",
						"createBy": "1",
						"updateTime": "2020-09-02 14:45:23",
						"updateBy": null,
						"delFlag": "1",
						"wxOpenid": null,
						"qqOpenid": null,
						"remark": "修改职务",
						"adminRemark": null,
						"postName": "修改职务",
						"areaId": null,
						"organId": 15,
						"companyId": 12,
						"serviceOrganId": 1,
						"tenantFlag": null
					},
					"permissions": ["sys_area_edit", "sys_agency_edit", "sys_busuness_sel", "sys_mechanism_add", "sys_tenant_edit",
						"sys_user_edit", "sys_menu_del", "sys_dict_edit", "sys_mechanism_edit", "sys_role_edit", "sys_bus_del",
						"sys_firm_del", "sys_firm_edit", "sys_role_del", "sys_dict_del", "sys_area_add", "sys_dict_add", "sys_user_del",
						"sys_menu_add", "sys_role_add", "sys_tenant_del", "sys_menu_edit", "sys_role_perm", "sys_user_add",
						"sys_area_del", "sys_firm_add", "sys_agency_add", "sys_tenant_add", "sys_mechanism_del"
					],
					"roles": ["superStars"],
					"tenantIds": null,
					"cpmpanyId": null,
					"organId": null
				},
				value1: '',
                value2: '',
                startTimes:'',
                deveiceList:[
                    {
                        names:'',
                        code:''
                    }
                ]
			}
		},
		components: {
			'ZTable': ZTable
		},
		inject: ['reload'],
		created() {
			//获取用户信息和按钮权限
			// this.userData = JSON.parse(sessionStorage.getItem('userData'))
			// this.listQuery.userId = this.userData.sysUser.userId;
			// this.form.createId = this.userData.sysUser.userId;
			// this.form.createName = this.userData.sysUser.userRealname;
			this.getList();
			// console.log(this.userData.permissions.includes('sys_area_del1'))
		},
		methods: {
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
				this.listQuery.documentName = this.listQuery.documentName.trim();
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
						terminationTime: response.terminationTime,
						cooperationMode: response.cooperationMode,
						leading: response.leading,
						tel: response.tel
					}
				});
            },
			//回车事件
			handleFilter() {
				this.listQuery.page = 1;
            },
            addDevice(){ //添加泊位
				this.deveiceList.push({
					names:'',
					code:''
				})
            },
            returns(){
                this.$router.push({
                    path: '/device/list',
				})
            },
            handleDel(array,i){ //删除泊位
                array.splice(i,1);
                console.log('array',array)
			},
			// 新增
			handleCreate(formName) {
				this.dialogStatus = 'create'
				this.form = {
					documentName: '',
					signingParty: '',
					terminationTime: '',
					cooperationMode: '',
					leading: '',
					tel: '',
					code: '',
					remark: ''
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
				if( this.tableData.length == 0 ){
				    this.$message.error('当前表格暂无数据，请补充数据再导出！');
				}else{
				    getDownloadExcel(this.listQuery,'/spark/companyfile/downloadExcel').then(response => {});
				}
			},
		}
	}
</script>

<style lang="scss">
</style>
