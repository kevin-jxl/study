<template>
	<div class="app-container calendar-list-container">
		<div class="filter-container crud-menu clearfix">
			<!--检索框-->
			<div class="form_search clearfix" style="padding:0 10px;">
				<el-form :inline="true" :model="listQuery" class="search-form-inline">
					<el-form-item label="输入搜索:">
						<el-input  v-model.trim="listQuery.systemName"  @keyup.enter.native="onSubmit" placeholder="系统名称/单位名称" ></el-input>
					</el-form-item>
                    <el-form-item label="系统地址:">
						<el-input v-model.trim="listQuery.systemHttp"  @keyup.enter.native="onSubmit" placeholder="请输入系统地址" ></el-input>
					</el-form-item>
					<el-form-item>
						<el-button class="el-icon-search" @click="onSubmit" size="mini"></el-button>
					</el-form-item>

					<el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-start">
						<span class="el-icon-refresh-right " style="padding:5px;" @click="handleReload()"></span>
					</el-tooltip>
					<el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-start" v-if="userData.permissions.includes('sys_area_rm')">
						<span class="el-icon-download " style="padding:5px;margin-right: 10px;" @click="handleExport()"></span>
					</el-tooltip>
					
				</el-form>
				<p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:-5px 0 28px 0;"></p>
			</div>
			<div class="float-fl table-title">数据列表</div>
			<el-button class="float-fr" type="primary" @click="handleCreate('form')" icon="edit" size="mini" style="margin-bottom:10px;" v-if="userData.permissions.includes('sys_area_add')">添加系统
			</el-button>
		</div>
		<div style="height:calc(100% - 120px)">
			<z-table :data="tableData" @size-change="handleSizeChange" @current-change="handleCurrentChange" :total='total'>
				<div slot="table-column">
					<el-table-column fixed align="center" type="index" width="50" label="序号"></el-table-column>

					<el-table-column align="center" label="系统名称">
						<template slot-scope="scope">
							<span>{{scope.row.systemName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="系统地址">
						<template slot-scope="scope">
							<span>{{scope.row.systemHttp}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="接口数量">
						<template slot-scope="scope">
							<span>{{scope.row.interfaceCount}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="管理单位">
						<template slot-scope="scope">
							<span>{{scope.row.systemUnit}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="对接人">
						<template slot-scope="scope">
							<span>{{scope.row.systemMeetPeople}}</span>
						</template>
					</el-table-column>

                    <el-table-column align="center" label="联系电话">
						<template slot-scope="scope">
							<span>{{scope.row.systemMeetPeoplePhone}}</span>
						</template>
					</el-table-column>

                    <el-table-column align="center" label="状态">
						<template slot-scope="scope">
							<span>{{scope.row.systemFlag}}</span>
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

					<el-table-column label="操作" align="center" width="280" v-if="userData.permissions.includes('sys_area_edit') || userData.permissions.includes('sys_area_del')">
						<template slot-scope="scope">
							<el-button size="mini" type="text" color="info" v-if="userData.permissions.includes('sys_area_edit')" @click="linkInterface(scope.row)">接口管理</el-button>
							<el-button size="mini" type="text" color="info" v-if="userData.permissions.includes('sys_area_edit')" @click="handleUpdate(scope.row)">编辑</el-button>
							<el-button size="mini" type="text" v-if="userData.permissions.includes('sys_area_del')" @click="handleDelete(scope.row)">删除</el-button>
						</template>
					</el-table-column>
				</div>
			</z-table>
		</div>

		<div class="dialog_mask_mine">
			<el-dialog :title="dialogStatus=='create'?'添加':'编辑'" :visible.sync="dialogFormVisible" width="45%" :close-on-click-modal="false">
				<div class="fix-width">
					<el-form :model="form" class="clearfix" ref="form" :rules="rules">
						<el-row :gutter="20">
							<el-col :span="12">
								<el-form-item label="系统名称" :label-width="formLabelWidth" prop="systemName">
									<el-input v-model="form.systemName" autocomplete="off" :maxlength="20"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="系统地址" :label-width="formLabelWidth" prop="systemHttp">
									<el-input v-model="form.systemHttp" autocomplete="off" :maxlength="40"></el-input>
								</el-form-item>
							</el-col>
                            <el-col :span="12">
								<el-form-item label="管理单位" :label-width="formLabelWidth" prop="systemUnit">
									<el-input v-model="form.systemUnit" autocomplete="off" :maxlength="40"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="对接人" :label-width="formLabelWidth" prop="systemMeetPeople">
									<el-input v-model="form.systemMeetPeople" autocomplete="off" :maxlength="20"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="联系方式" :label-width="formLabelWidth" prop="systemMeetPeoplePhone">
									<el-input v-model="form.systemMeetPeoplePhone" autocomplete="off"
									 :maxlength="11"></el-input>
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
	</div>

</template>

<script>
    import {
        areaList, //列表
        addObj, //添加
        delObj, //删除
        getObj, //修改详情
        putObj, //修改
        getDownloadExcel //导出
    } from "@/api/intetFaceInte/intetFaceInte";
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
                    systemName: '',
                    systemHttp:'',
                    systemStatus:'1',
                    tableName:'系统集成'
				}, //列表搜索请求参数
				total: 0, //总数
				form: {
                    systemName:null,
                    systemHttp:null,
                    systemUnit:null,
                    systemMeetPeople:null,
                    systemMeetPeoplePhone:null,
                    remark:null,
                    systemStatus:'1'
                }, //新增 编辑参数
				rules: {//表单校验
					systemName: [{
							required: true,
							message: "系统名称不能为空",
							trigger: "blur"
						},
						{
							min: 1,
							max: 20,
							message: "长度在 1 到 20 个字符",
							trigger: "blur"
						}
					],
					systemMeetPeople:[{
							required: true,
							message: "对接人不能为空",
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
                    systemUnit:[{
							required: true,
							message: "管理单位不能为空",
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
					systemMeetPeoplePhone: [{
							required: true,
							message: "联系电话不能为空",
							trigger: "blur"
						},
						{
							validator: checkMobile,
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
				formLabelWidth: '120px', //弹框宽度
				dialogTableVisible: false,
				dialogFormVisible: false,
				tableData: null, //列表数据源
				dialogStatus: null, //判断新增还是编辑 ‘create’:新增，'update':编辑
				userData: null
			}
		},
		components: {
			'ZTable': ZTable
		},
		inject: ['reload'],
		created() {
			//获取用户信息和按钮权限
			this.userData = JSON.parse(sessionStorage.getItem('userData'))
			this.form.createId = this.userData.sysUser.userId;
			this.getList();
		},
		methods: {
			getList() { //获取列表
				let _this = this;
				_this.listLoading = true;
				areaList(_this.listQuery).then(response => {
					_this.tableData = response.data.records;
                    _this.total = response.data.total;
                    _this.listLoading = false;
                    _this.tableData.filter(item => { 
                        _this.form.systemFlag = item.systemFlag;
                        item.systemFlag == 1?item.systemFlag ='正常 ':item.systemFlag='异常'
                    });
				});
            },
			//搜索
			onSubmit() {
                this.listQuery.page = 1;
                this.listQuery.systemName= this.listQuery.systemName.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
                this.listQuery.systemHttp= this.listQuery.systemHttp.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
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
				getObj(row.systemId).then(res => {
					let response = res.data.data;
					this.form = {
                        systemName:response.systemName,
                        systemHttp:response.systemHttp,
                        systemUnit:response.systemUnit,
                        systemMeetPeople:response.systemMeetPeople,
                        systemMeetPeoplePhone:response.systemMeetPeoplePhone,
                        remark:response.remark,
                        systemStatus:'1',
                        systemId :row.systemId
					}
				});
			},
			//回车事件
			handleFilter() {
				this.listQuery.page = 1;
			},
			// 新增
			handleCreate(formName) {
				this.dialogStatus = 'create'
				// this.$refs[formName].resetFields();
				this.form = {
					systemName:null,
                    systemHttp:null,
                    systemUnit:null,
                    systemMeetPeople:null,
                    systemMeetPeoplePhone:null,
                    remark:null,
                    systemStatus:'1'
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
							addObj(_this.form).then((response) => {
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
							putObj(_this.form).then((response) => {
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
					delObj(rowObj.systemId)
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
                    getDownloadExcel(this.listQuery,'/admin/integration/downloadExcel').then(response => {});
				}else{
					this.$message.error('当前表格暂无数据，请补充数据在导出！');
				}
            },
            linkInterface(row) {
				this.$router.push({
					path: '/intets/inter',
					query: {
						systemId: row.systemId,
					}
				})
			},
		}
	}
</script>

<style lang="scss">
</style>
