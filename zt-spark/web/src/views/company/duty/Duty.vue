<template>
	<div class="app-container calendar-list-container">
		<div class="filter-container crud-menu clearfix">
			<!--检索框-->
			<div class="form_search clearfix" style="padding:0 10px;">
				<el-form :inline="true" :model="listQuery" class="search-form-inline">
					<el-form-item label="输入搜索:">
						<el-input v-model.trim="listQuery.dutyName" placeholder="请输入姓名"></el-input>
					</el-form-item>
					<el-form-item label="值班周期">
						<el-date-picker v-model="listQueryTime" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"
						 value-format="yyyy-MM-dd">
						</el-date-picker>
					</el-form-item>
					<el-form-item>
						<el-button class="el-icon-search" @click="onSubmit" size="mini"></el-button>
					</el-form-item>

					<el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-start">
						<span class="el-icon-refresh-right " style="padding:5px;" @click="handleReload()"></span>
					</el-tooltip>
					<el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-start"  v-if="userData.permissions.includes('spark_duty_rm')">
						<span class="el-icon-download " style="padding:5px;margin-right: 10px;" @click="handleExport()"></span>
					</el-tooltip>
				</el-form>
				<p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:-5px 0 28px 0;"></p>
			</div>
			<div class="float-fl table-title">值班信息列表</div>
			<el-button class="float-fr" type="primary" @click="handleCreate('form')" icon="edit" size="mini" v-if="userData.permissions.includes('spark_duty_add')" style="margin-bottom:10px;">值班设置
			</el-button>
		</div>
		<div style="height:calc(100% - 130px)">
			<z-table :data="tableData" @size-change="handleSizeChange" @current-change="handleCurrentChange" :total='total'>
				<div slot="table-column">
					<el-table-column fixed align="center" type="index" width="50" label="序号"></el-table-column>

					<el-table-column align="center" label="值班人">
						<template slot-scope="scope">
							<span>{{scope.row.dutyName}}</span>
						</template>
					</el-table-column>

					<!-- <el-table-column align="center" label="上级行政区域">
                        <template slot-scope="scope">
                            <span>{{scope.row.parentId }}</span>
                        </template>
                    </el-table-column> -->

					<!-- <el-table-column align="center" label="联系电话">
						<template slot-scope="scope">
							<span>{{scope.row.code}}</span>
						</template>
					</el-table-column> -->

					<el-table-column align="center" label="负责车场">
						<template slot-scope="scope">
							<span>{{scope.row.organName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="开始时间" width="200">
						<template slot-scope="scope">
							<span>{{scope.row.startTime}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="结束时间" width="200">
						<template slot-scope="scope">
							<span>{{scope.row.endTime}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="值班状态">
						<template slot-scope="scope">
							<span>{{scope.row.dutyStatus}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="排班人">
						<template slot-scope="scope">
							<span>{{scope.row.createName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="排班人电话">
						<template slot-scope="scope">
							<span>{{scope.row.createPhone}}</span>
						</template>
					</el-table-column>

					<el-table-column label="操作" align="center" width="280">
						<template slot-scope="scope">
							<el-button size="mini" type="text" color="info" @click="handleDetail(scope.row)">详情</el-button>
							<el-button size="mini" type="text" color="info" @click="handleUpdate(scope.row)" v-if="userData.permissions.includes('spark_duty_edit')">编辑</el-button>
							<el-button size="mini" type="text" @click="handleDelete(scope.row)" v-if="userData.permissions.includes('spark_duty_del')">删除</el-button>
						</template>
					</el-table-column>
				</div>
			</z-table>
		</div>
		<!-- 值班设置 -->
		<div class="dialog_mask_mine dialog_mask_mineZb">
			<el-dialog :title="dialogStatus=='create'?'值班设置':'编辑值班设置'" :visible.sync="dialogFormVisibleZb" width="600px" :close-on-click-modal="false" style="min-width: 485px;">
				<div>
					<el-form :model="form" class="clearfix" ref="form" :rules="rules">
						<el-row :gutter="24">
							<div class="clearfix">
								<el-col :span="12">
									<!--  -->
									<el-form-item label="企业:" :label-width="formLabelWidth" v-if="userData.roles.includes('ROLE_ADMIN') || userData.sysUser.tenantFlag==1">
										<!-- -->
										<el-select placeholder="请选择" v-model="form.comId" @change="getSysadministrativeorganInputList(form.comId)" >
											<el-option label="无" value=""></el-option>
											<el-option :label="v.name" :value="v.com_id" v-for="(v,i) in syscompanyList" :key="i" ></el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :span="12">
									<el-form-item label="负责车场:" :label-width="formLabelWidth">
										<el-select placeholder="请选择" v-model="form.organId">
											<el-option label="无" value=""></el-option>
											<el-option :label="v.name" :value="v.organ_id" v-for="(v,i) in sysadministrativeorganList" :key="i"></el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :span="24">
									<el-form-item label="值班周期:" :label-width="formLabelWidth">
										<el-date-picker v-model="form.time" type="daterange" range-separator="至" start-placeholder="开始日期"
										 end-placeholder="结束日期" value-format="yyyy-MM-dd">
										</el-date-picker>
									</el-form-item>
								</el-col>
								<el-col :span="24">
									<el-form-item label="值班班次:" :label-width="formLabelWidth">
										<el-select placeholder="请选择" v-model="form.dutyType">
											<el-option label="一班一天" value="一班一天"></el-option>
											<el-option label="二班一天" value="二班一天"></el-option>
											<!-- <el-option :label="v.name" :value="v.com_id" v-for="(v,i) in syscompanyList" :key="i"></el-option> -->
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :span="24">
									<el-form-item label="任务循环:" :label-width="formLabelWidth">
										<el-switch v-model="form.taskBool">
										</el-switch>
										<span style="margin-left: 10px;" :class="form.taskBool">开启</span><span>/</span><span>关闭</span>
									</el-form-item>
								</el-col>
								<el-col :span="24">
									<el-form-item label="任务描述:" :label-width="formLabelWidth" style="width: 100%;">
										<el-input type="textarea" v-model="form.taskDescription" autocomplete="off" :maxlength="200" :rows="4"></el-input>
									</el-form-item>
								</el-col>

								<el-col :span="24">
									<el-form-item label="添加任务:" :label-width="formLabelWidth">
										<el-button plain type="primary" @click="handleAddPerson">添加</el-button>
									</el-form-item>
								</el-col>
							</div>
							<div v-for="(v,i) in form.people" :key="i" class="clearfix" style="border: 1px solid #ccc;margin-bottom: 10px;padding-top: 20px;">
								<el-col :span="10">
									<el-form-item label="添加人员:" :label-width="formLabelWidth">
										<el-select placeholder="请选择" v-model="v.peopleId">
											<el-option label="无" value=""></el-option>
											<el-option :label="v.name" :value="v.id" v-for="(v,i) in companypeopleList" :key="i"></el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :span="12">
									<el-form-item label="添加时段:" :label-width="formLabelWidth">
										<el-time-picker is-range v-model="v.time" range-separator="至" start-placeholder="开始" end-placeholder="结束"
										 format='HH:mm' value-format="HH:mm" placeholder="选择时间范围" style="width: 180px;" @change="timeChange($event,i)">
										</el-time-picker>
									</el-form-item>
								</el-col>
								<el-col :span="2">
									<el-form-item label="">
										<i class="el-icon-error" v-show="i!=0" @click="handleDel(form.people,i)"></i>
									</el-form-item>
								</el-col>
								<el-col :span="24">
									<el-form-item label="添加点检:" :label-width="formLabelWidth">
										<el-button plain type="primary" @click="handleAddTask(i)">添加</el-button>
									</el-form-item>
								</el-col>
								<div v-for="(s,idx) in v.task" :key="idx">
									<el-col :span="10">
										<el-form-item label="添加设备:" :label-width="formLabelWidth">
											<el-select placeholder="请选择" v-model="s.equipmentName">
												<el-option label="设备1" value="设备1"></el-option>
												<el-option label="设备2" value="设备2"></el-option>
												<!-- <el-option :label="v.name" :value="v.com_id" v-for="(v,i) in syscompanyList" :key="i"></el-option> -->
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :span="12">
										<el-form-item label="添加编号:" :label-width="formLabelWidth">
											<el-select v-model="s.number" multiple placeholder="请选择" @change="bhchange($event,idx,i)">
												<el-option label="ASDF" value="ASDF"></el-option>
												<el-option label="GHJK" value="GHJK"></el-option>
												<!-- <el-option
											      v-for="item in options"
											      :key="item.value"
											      :label="item.label"
											      :value="item.value">
											    </el-option> -->
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :span="2">
										<el-form-item label="">
											<i class="el-icon-error" v-show="idx!=0" @click="handleDel(v.task,idx)"></i>
										</el-form-item>
									</el-col>
								</div>
							</div>

						</el-row>

					</el-form>
				</div>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click="handleSubmit('form')">{{dialogStatus=='create'?'确定':'保存'}}</el-button>
					<el-button @click="dialogFormVisibleZb = false">取 消</el-button>
				</div>
			</el-dialog>

		</div>

		<div class="dialog_mask_mine">
			<el-dialog title="值班详情" :visible.sync="dialogFormVisible" width="900px" :close-on-click-modal="false">
				<div class="dialgoBox">
					<div class="zdata">
						<z-data @child-event='parentEvent'></z-data>
					</div>
					<div class="cardBox">
						<div>
							<span class="cardTitle">人员值班信息</span>
							<span> {{year}}</span>
						</div>
						<div class="card" v-for="(v,i) in detailList" :key="i">
							<div>{{v.taskDescription}}</div>
							<div class="count">
								<div>
									<span>值班人：</span>
									<span>{{v.peopleName}}</span>
								</div>
							</div>
							<div class="count">
								<div>
									<span>值班时间：</span>
									<span>{{v.dutyStartTime}}-{{v.dutyEndTime}}</span>
								</div>
								<div>
									<span>电话：</span>
									<span>{{v.peoplePhone}}</span>
								</div>
							</div>
							<div v-for="(s,idx) in v.task" :key="idx">
								<div class="count">
									<div>
										<span>设备名称：</span>
										<span>{{s.equipmentName}}</span>
									</div>
								</div>
								<div class="count">
									<div>
										<span>设备编号：</span>
										<span>{{s.equipmentNum}}</span>
									</div>
								</div>
							</div>
						</div>
					</div>
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
	import ZData from '@/components/ZData.vue';
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
					dutyName: '',
					startTime: '',
					endTime: '',
					tableName: '值班信息管理'
				}, //列表搜索请求参数
				listQueryTime:[],
				total: 0, //总数
				value3: true,
				//新增 编辑参数
				form: {
					"dutyType": "",
					"organId": "",
					"startTime": "",
					"endTime": "",
					"dutyStatus": "",
					"taskDescription": "",
					"taskBool":null,
					"time": [],
					"people": [{
						"peopleId": '',
						"dutyStartTime": "",
						"dutyEndTime": "",
						"time": ['',''],
						"task": [{
							"equipmentName": "",
							"equipmentNum": "",
							number: []
						}]
					}],
				},
				rules: { //表单校验
					areaName: [{
							required: true,
							message: "行政区域名称不能为空",
							trigger: "blur"
						},
						{
							min: 1,
							max: 20,
							message: "长度在 1 到 20 个字符",
							trigger: "blur"
						}
					],
					adminname: [{
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
							message: "行政区域代码不能为空",
							trigger: "blur"
						},
						{
							validator: checkCode,
							trigger: 'blur'
						}
					],
				},
				formLabelWidth: '100px', //弹框宽度
				dialogFormVisibleZb: false,
				dialogFormVisible: false,
				tableData: null, //列表数据源
				dialogStatus: null, //判断新增还是编辑 ‘create’:新增，'update':编辑
				userData: null,
				value1: [],
				value2: '',
				syscompanyList:[],
				sysadministrativeorganList:[],//车厂数组
				companypeopleList:[],
				detailList:[],
				year:'',
			}
		},
		components: {
			'ZTable': ZTable,
			'ZData': ZData
		},
		inject: ['reload'],
		watch: {
			'form.time': function(val, oldVal) {
				if(val.length>0){
					this.form.startTime = val[0];
					this.form.endTime = val[1];
				}
				
			}
		},
		created() {
			//获取用户信息和按钮权限
			this.userData = JSON.parse(sessionStorage.getItem('userData'))
			console.log(this.userData)
			// this.listQuery.userId = this.userData.sysUser.userId;
			// this.form.createId = this.userData.sysUser.userId;
			// this.form.createName = this.userData.sysUser.userRealname;
			this.getList();
			this.getsyscompany();
			this.pepleList()
			if(!this.userData.roles.includes('ROLE_ADMIN') && (this.userData.sysUser.tenantFlag == 0 || this.userData.sysUser.tenantFlag ==null)){
				this.getSysadministrativeorganInputList(this.userData.sysUser.companyId)
			}
			// console.log(this.userData.permissions.includes('sys_area_del1'))

		},
		methods: {
			getList() { //获取列表
				let _this = this;
				_this.listLoading = true;
				_this.$api.getCompanydutyList(_this.listQuery).then(response => {
					_this.tableData = response.data.records;
					_this.total = response.data.total;
					_this.listLoading = false;
				});
			},
			pepleList() { //人员列表
				let _this = this;
				_this.$api.getCompanypeopleInputList({
					organId: this.userData.sysUser.organId,
					comId:this.userData.sysUser.companyId,
				}).then(response => {
					
					_this.companypeopleList=response.data;
					// console.log(_this.syscompanyList)
				});
				
			},
			getsyscompany() { //获取列表
				let _this = this;
				_this.$api.getsyscompany({
					tenantId: this.userData.sysUser.tenantId
				}).then(response => {
					
					_this.syscompanyList=response.data;
					console.log(_this.syscompanyList)
				});
				
			},
			getSysadministrativeorganInputList(id) { //获取列表
			console.log(id)
				let _this = this;
				_this.$api.getSysadministrativeorganInputList({
					comId: id
				}).then(response => {
					_this.sysadministrativeorganList = response.data
				});
				
			},
			//搜索
			onSubmit() {
				this.listQuery.dutyName = this.listQuery.dutyName.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
				this.listQuery.startTime = this.listQueryTime[0];
				this.listQuery.endTime = this.listQueryTime[1];
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
			//时间选择处理
			timeChange(ev, i) {
				console.log(ev)
				this.form.people[i].dutyStartTime = ev[0];
				this.form.people[i].dutyEndTime = ev[1];
				this.$forceUpdate();
			},
			//编号处理函数
			bhchange($event,idx,i){
				this.form.people[i].task[idx].equipmentNum = $event.join(",");
			},
			//编辑
			handleUpdate(row) {
				this.dialogStatus = 'update'
				this.dialogFormVisibleZb = true;
				this.$api.getCompanydutyInfo(row.id).then(res => {
					this.form = res.data.data
					this.getSysadministrativeorganInputList(res.data.data.comId)
				});
			},
			handleDetail() {
				this.dialogFormVisible = true;
				let date = new Date().toLocaleDateString().replace(/\//g, '-');
				let d = new Date();
				let weekArray = new Array("日", "一", "二", "三", "四", "五", "六");
				let week = weekArray[new Date().getDay()];
				this.year = d.getFullYear() +'年'+(d.getMonth() + 1)+'月'+ d.getDate()+'日' +' 星期'+week
				this.getCompanydutyDutyPeople(date)
			},
			getCompanydutyDutyPeople(date){
				let _this = this;
				this.$api.getCompanydutyDutyPeople({
					date:date
				}).then(res => {
					_this.$nextTick(() => {
					  _this.detailList = res.data.records
					})
					
					console.log(_this.detailList)
					
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
					"dutyType": "",
					"organId": "",
					"startTime": "",
					"endTime": "",
					"dutyStatus": "",
					"taskDescription": "",
					taskBool:null,
					"time": [],
					"people": [{
						"peopleId": '',
						"dutyStartTime": "",
						"dutyEndTime": "",
						"time": ['',''],
						"task": [{
							"equipmentName": "",
							"equipmentNum": "",
							number: []
						}]
					}],
				}
				this.dialogFormVisibleZb = true
			},
			//数据提交新增or编辑
			handleSubmit(form) {
				let _this = this;
				console.log(this.form)
				//校验必填项
				this.$refs[form].validate((valid) => {
					if (valid) {
						_this.form.taskFlag = _this.form.taskBool
						if (this.dialogStatus == 'create') {
							
							_this.$api.getCompanydutyAdd(_this.form).then((response) => {
								if (response.data.data == false) {
									_this.$message.error(response.data.msg);
								} else {
									_this.dialogFormVisibleZb = false;
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
							_this.form.createName='';
							_this.form.createPhone='';
							_this.form.createTime='';
							
							_this.$api.getCompanydutyUpdate(_this.form).then((response) => {
								if (response.data.data == false) {
									_this.$message.error(response.data.msg);
								} else {
									_this.dialogFormVisibleZb = false;
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
					this.$api.getCompanydutyDelete(rowObj.id)
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
					getDownloadExcel(this.listQuery, '/spark/companyduty/downloadExcel').then(response => {});
				}
			},
			parentEvent(data) {
				let d = new Date(data.day);  
				let datetime=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
				let weekArray = new Array("日", "一", "二", "三", "四", "五", "六");
				let week = weekArray[new Date(data.day).getDay()];
				this.year = d.getFullYear() +'年'+(d.getMonth() + 1)+'月'+ d.getDate()+'日' +' 星期'+week
				this.getCompanydutyDutyPeople(datetime)
			},
			handleAddPerson() {
				this.form.people.push({
					"peopleId": '',
					"dutyStartTime": "",
					"dutyEndTime": "",
					"time": ['',''],
					"task": [{
						"equipmentName": "",
						"equipmentNum": "",
						"number": []
					}]
				})
				this.$forceUpdate();
			},
			handleAddTask(i) {
				this.form.people[i].task.push({
					"equipmentName": "",
					"equipmentNum": "",
					"number": []
				})
				this.$forceUpdate();
			},
			handleDel(array, i) {
				array.splice(i, 1);
			},
		}
	}
</script>

<style lang="scss">
	.dialog_mask_mineZb .el-dialog__body {
		max-height: 400px !important;
		overflow-y: auto !important;
	}

	.dialog_mask_mineZb .el-date-editor .el-range-separator {
		line-height: 22px !important;
	}
</style>

<style scoped="scoped" lang="scss">
	.dialgoBox {
		display: flex;

		.zdata {
			width: 450px;
			padding-right: 20px;
			border-right: 1px solid #ccc;
		}

		.cardTitle {
			font-size: 16px;
			font-weight: 600;
			margin-right: 20px;
		}

		.cardBox {
			width: 350px;
			padding: 20px;
			text-align: left;
			max-height: 344px;
			overflow-y: auto;

			.card {
				box-shadow: 0 2px 2px 0 rgba(91, 91, 91, 0.1), 0 3px 1px -2px rgba(91, 91, 91, 0.1), 0 1px 5px 0 rgba(91, 91, 91, 0.12);
				padding: 5px 10px;
				margin-top: 15px;
			}

			.count {
				display: flex;
				margin-top: 8px;

				div:nth-child(1) {
					margin-right: 25px;
				}

				div span:nth-child(1) {
					color: #9a9a9a;
				}

				div span:nth-child(2) {
					color: #333;
				}
			}
		}
	}
</style>
