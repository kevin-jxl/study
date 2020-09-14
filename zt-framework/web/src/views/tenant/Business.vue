<template>
	<div class="app-container calendar-list-container">
		<div class="form_search clearfix" style="padding:0 10px;">
		    <el-form :inline="true" class="search-form-inline">
				<el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-start">
				    <span class="el-icon-refresh-right " style="padding:5px;" @click="handleReload()"></span >
				</el-tooltip>
				<el-tooltip class="item fj-refresh" effect="dark" content="重置" placement="top-start" style="margin-right: 20px;">
				    <span class="el-icon-refresh " style="padding:5px;" @click="handleReload()"></span >
				</el-tooltip>
		        <el-tooltip class="item fj-refresh" effect="dark" content="保存" placement="top-start" style="margin-right: 20px;" v-if="userData.permissions.includes('sys_role_perm')">
		            <span class="el-icon-mobile" style="padding:5px;" @click="handleSubmit"></span >
		        </el-tooltip>
		    </el-form>
		</div>
		<p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:20px 0 28px 0;"></p>
		<div class="title">业务配置</div>
		<div class="minWrap">
			<div class="left">
				<div class="nav">选择租户</div>
				<ul class="tag-group">
					<li :class="v.Flag?'active':''" v-for="(v,i) in list" :key="i" @click="handleTab(v.tenantId)">{{v.tenantName}}</li>
				</ul>
			</div>
			<div class="right" :class="{'r_50': activeName=='third','r_75': activeName=='second'}">
				<div class="nav">租户信息</div>
				<el-tabs v-model="activeName" type="card" @tab-click="handleClick">
				    <el-tab-pane label="基础信息" name="first">
						<div class="tabBox" style="height:calc(100vh - 428px) !important;overflow-y: auto;">
							<el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
								<el-row :gutter="24" style="margin: 0;">
									<el-col :span="24">
										<el-form-item label="租户名称:">
											<el-input v-model="ruleForm.tenantName" placeholder="请输入租户名称"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="24">
										<el-form-item label="上级租户:">
											<el-input v-model="ruleForm.tenantParent" placeholder="请输入上级租户"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="24">
										<el-form-item label="租户编码:">
											<el-input v-model="ruleForm.tenantCode" placeholder="请输入租户编码"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="24">
										<el-form-item label="联系人:">
											<el-input v-model="ruleForm.adminName" placeholder="请输入联系人"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="24">
										<el-form-item label="联系方式:">
											<el-input v-model="ruleForm.adminPhone" placeholder="请输入联系方式"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="24">
										<el-form-item label="位置信息:">
											<el-input v-model="ruleForm.areaName" placeholder="请输入位置信息"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="24">
										<el-form-item label="详细描述:">
											<el-input v-model="ruleForm.remark" type="textarea" placeholder="请输入" style="width: 107%;"></el-input>
										</el-form-item>
									</el-col>
								</el-row>
							</el-form>
						</div>
					</el-tab-pane>
				    <el-tab-pane label="权限" name="second">
						<div class="tabBox">
							<div class="countBox">
								<div class="menu-system-left">
								    <p>
								        <i class="el-icon-setting" style="margin-right:5px;"></i>系统列表
								    </p>
								    <div style="margin-top:10px;height:calc(100vh - 467px) !important">
										<el-checkbox-group 
										    v-model="checkedList">
										    <el-checkbox v-for="(v,i) in checkboxList" :label="v.id" :key="i" @change = handleCheckChange(v,$event)>{{v.name}}</el-checkbox>
										  </el-checkbox-group>
								    </div>
								</div>
							</div>
							<div class="countBox">
								<div class="menu-system-left">
								    <p>
								        <i class="el-icon-s-home" style="margin-right:5px;"></i>功能导航栏
								    </p>
								    <div style="margin-top:10px;overflow-y:auto;">
										<el-tree
										  :data="rootData"
										  show-checkbox
										  node-key="id"
										  ref="tree"
										  @check="handleTreeClick">
										</el-tree>
								    </div>
								</div>
							</div>
							<div class="countBox" style="border-right: none;">
								<div class="menu-system-left">
								    <p>
								        <i class="el-icon-s-fold" style="margin-right:5px;"></i>功能列表
								    </p>
								    <div style="margin-top:10px;overflow-y:auto;">
								        <el-tree :data="data" :props="defaultProps1"></el-tree>
								    </div>
								</div>
							</div>
						</div>
					</el-tab-pane>
				    <el-tab-pane label="成员" name="third">
						<div class="tabBox">
							<div class="countBox">
								<div class="menu-system-left">
								    <p>
								        <i class="el-icon-s-home" style="margin-right:5px;"></i>联帕停车
								    </p>
								    <div style="margin-top:10px;overflow-y:auto;height: calc(100vh - 467px) !important;">
								        <el-tree :data="tenantOrganList" :props="defaultProps2" @node-click="handleNodeClick"></el-tree>
								    </div>
								</div>
							</div>
							<div class="countBox">
								<div class="menu-system-table">
								    <p>
								        <i class="el-icon-s-home" style="margin-right:5px;"></i>岗位成员列表
								    </p>
								    <div style="margin-top:10px;overflow-y:auto;height: calc(100vh - 467px) !important;">
								        <z-table
								            :data="ueseListArr"
								            @size-change="handleSizeChange"
								            @current-change="handleCurrentChange"
											:total='total'
											:pageSize="100">
								            <div slot="table-column">
								                <el-table-column align="center" label="员工编码" fixed>
								                    <template slot-scope="scope">
								                        <span>{{scope.row.userId}}</span>
								                    </template>
								                </el-table-column>
								                <el-table-column align="center" label="姓名">
								                    <template slot-scope="scope">
								                        <span>{{scope.row.username }}</span>
								                    </template>
								                </el-table-column>
								                <el-table-column align="center" label="职位">
								                    <template slot-scope="scope">
								                        <span>{{scope.row.postName}}</span>
								                    </template>
								                </el-table-column>
								                <el-table-column align="center" label="联系方式">
								                    <template slot-scope="scope">
								                        <span>{{scope.row.phone}}</span>
								                    </template>
								                </el-table-column>
											</div>
								        </z-table>
								    </div>
								</div>
							</div>
						</div>
					</el-tab-pane>
				</el-tabs>
			</div>
		</div>
	</div>
</template>

<script>
	import {
	    fetchRoleTree,//树列表
		tenantList,
		detailTenant,
		roleMenuUpd,//菜单树保存
		tenantOrgan,//查询租户下车场
		userList,
		selectListRoleId
	} from "@/api/tenant/business";
	import ZTable from '@/components/ZTable.vue';
	export default {
	    data() {
	      return {
	        activeName: 'first',
			list: null,
			ruleForm: {},
			checkedList:[],
			checkboxList:[],
			rootData: [],
			defaultProps: {
			    children: 'children',
			    label: 'label'
			},
			defaultProps1: {
			    children: 'children',
			    label: 'label'
			},
			defaultProps2: {
			    children: 'children',
			    label: 'label'
			},
			data: [],
			ueseListArr: null,
			id:null,
			checkAllArr:[],
			roleId:null,//角色id
			tenantOrganList:[],//车场的列表
			total:null,
			userData:null
	      };
		},
		components:{
			'ZTable': ZTable
		},
		inject:['reload'],
		created() {
			this.userData = JSON.parse(sessionStorage.getItem('userData'))
			this.id =this.$route.query.id
			this.getList()
			if(this.id){
				this.getDetail(this.id)
				this.getTenantOrgan(this.id)
			}
		},
	    methods: {
			//获取左侧列表
			getList() {//获取列表
				let _this = this;
			    tenantList({page:1,limit:999,tenantFlag:1}).then(response => {
			        _this.list = response.data.records;
					_this.list.forEach(item=>{
						item.Flag=false
						if(item.tenantId == this.id){
							item.Flag = true
						}
					})
			    });
			},
			getSelectListRoleId(roleCode) {//获取列表
				let _this = this;
				_this.checkAllArr = [];//清空选中的数组
			    selectListRoleId({roleId:roleCode}).then(response => {
			        // console.log(response)
					_this.checkAllArr = response.data.data;
					_this.$nextTick(() => {
					  _this.$refs.tree.setCheckedKeys(_this.checkAllArr)
					})
			    });
			},
			getThreeList(roleCode,roleId){
				let _this = this;
				this.rootData = [];//清空功能导航栏数组
				this.checkedList=[];//清空选中的数组
				fetchRoleTree(roleCode).then(response => {
					_this.checkboxList = response.data;
					response.data.forEach(item=>{
						this.checkedList.push(item.id);
						this.rootData.push(item)
					})
					let str = JSON.stringify(this.rootData);
					this.data = JSON.parse(str);
					this.doDeleteArr(this.data)
					this.getSelectListRoleId(roleId)
				});
			},
			getTenantOrgan(id){
				let _this=this;
				tenantOrgan(id).then(response => {
				    _this.tenantOrganList = response.data.data;
					// console.log(response)
				});
			},
			handleClick(tab, event) {
				// console.log(this.activeName)
			},
			systemChanges(value) {
				// console.log("value", value);
			},
			//获取用户列表
			getUserList(id,type){
				userList({tenantId:id,type:type}).then(response => {
					// console.log(response)
					this.ueseListArr = response.data;
					this.total = response.data.length
				});
				
			},
			//车场树点击
			handleNodeClick(data) {
				this.ueseListArr = [];//每次点击重置人员数组
				if(!data.children){
					this.getUserList(data.id,data.type)
				}
			},
			//修改分页
			handleSizeChange(size) {
			    // console.log('page size: ', size);
			},
			//修改分页
			handleCurrentChange(page) {
			    // console.log('current page: ', page);
			},
			//标签点击切换
			handleTab(id){
				this.ueseListArr = []
				this.list.forEach((item,i)=>{
					item.Flag = false
					if(item.tenantId == id){
						item.Flag = true
						this.$set(this.list,i,this.list[i])
					}
				})
				this.getDetail(id)
				this.getTenantOrgan(id)
			},
			//获取详情接口
			getDetail(id){
				detailTenant(id).then(res => {
				    let response = res.data.data.sysTenant;
				    this.ruleForm = {
						tenantId:response.tenantId,
						tenantName: response.tenantName,
						tenantCode: response.tenantCode,
						tenantParent: response.tenantParent,
						areaId: response.areaId,
						adminName: response.adminName,
						adminPhone: response.adminPhone,
						tenantFlag: response.tenantFlag,
						areaName:response.areaName,
						remark:response.remark
				    }
					if(res.data.data.roleVO){
						this.roleId = res.data.data.roleVO.roleId;
						this.getThreeList(res.data.data.roleVO.roleCode,res.data.data.roleVO.roleId)
					}
					
				});
			},
			//系统列表选择项点击
			handleCheckChange(v,e){
				this.rootData = [];
				this.data = [];
				this.checkAllArr = [];//清空选择的树菜单id；
				v.checked = e;
				this.checkboxList.forEach((item,i)=>{
					if(item.checked){
						this.rootData.push(item)
					}
				})
				this.doChecked(this.rootData,true)
			},
			//点击功能导航栏函数
			handleTreeClick(data,checked){
				// console.log(checked)
				let _this = this;
				_this.f(this.rootData)
				let arr = [];
				arr = checked.checkedNodes.concat(checked.halfCheckedNodes)
				arr.forEach(item=>{
					item.checked = true;
				})
				
				this.data = []
				let str = JSON.stringify(this.rootData);
				this.data = JSON.parse(str);
				this.doDeleteArr(this.data)
				this.checkAllArr = [];
				this.checkAllArr = [...checked.checkedKeys];
			},
			//递归重置数组
			f(a){
				let _this =this;
				a.forEach(item=>{
					item.checked = false;
					if(item.children!=null){
						_this.f(item.children)
					}
				})
			},
			//递归删除选择的数组的false项
			doDelete(treeData,checked){
			  for(var i = treeData.length ; i > 0 ; i--){
				  if(treeData[i-1].checked == false && treeData[i-1].children ==null){
					treeData.splice(i-1,1);
				  }else if(treeData[i-1].checked == false && treeData[i-1].children.length==0){
					treeData.splice(i-1,1);	  
				  }else{ 
					if(treeData[i-1].children){
					  this.doDelete(treeData[i-1].children,checked)
					}
				  }
			  }
			},
			//删除多维数组项
			doDeleteArr(data){
				this.doDelete(data,false)
				this.doDelete(data,false)
				this.doDelete(data,false)
			},
			//获取选择的id
			doChecked(treeData,checked){
			  for(var i = treeData.length ; i > 0 ; i--){
				  if(treeData[i-1].checked == true){
					this.checkAllArr.push(treeData[i-1].id)
				  }
				  if(treeData[i-1].children){
				    this.doChecked(treeData[i-1].children,checked)
				  }
			  }
			},
			//保存菜单树
			handleSubmit(){
				// let strid = JSON.stringify(this.checkAllArr).replace('[','').replace(']','');
				let strid =  '';
				strid = this.checkAllArr.join(",");
				console.log(strid)
				if(strid){
					roleMenuUpd(this.roleId,strid).then(res => {
					    this.$notify({
					    	title: "成功",
					    	message: "创建成功",
					    	type: "success",
					    	duration: 2000
					    });
					    
					});
				}else{
					this.$message({
					  message: '请选择需要保存的菜单！',
					  type: 'warning'
					});
				}
				
			},
			//页面刷新
			handleReload(){
				this.reload()
			}
	    }
	  };
</script>

<style scoped="scoped" lang="scss">
	.app-container {
	    height: calc(100vh - 178px) !important;
		height: 720px;
		box-sizing: border-box;
		padding: 10px;
		background: #fff;
	}
	.title{
		text-align: left;
		margin-bottom: 10px;
	}
	.bg-purple {
	    background: #d3dce6;
	}
	.grid-content {
		min-height: 36px;
	}
	.nav{
		display: flex;
		align-items: center;
		justify-content:center;
		height: 40px;
		background: #efefef;
		margin-bottom: 20px;
	}
	.minWrap{
		display: flex;
		height: calc(100vh - 310px) !important;
		overflow-y: auto;
		border-left: 1px solid #dadada;
		border-top: 1px solid #dadada;
		.left{
			flex: 0 0 25%;
			border-right: 1px solid #dadada;
			height: calc(100vh - 310px) !important;
		}
		.right{
			// flex-grow: 1;
			// flex-basis: 0;
			width: 25%;
			border-right: 1px solid #dadada;
			
		}
		.r_50{
			width: 50%;
		}
		.r_75{
			width: 75%;
		}
		.tag-group{
			display: flex;
			flex-wrap:wrap;
			justify-content: flex-start;
			padding: 0 10px 10px 10px;
			li{
				padding: 0 10px;
				height: 26px;
				text-align: center;
				line-height: 26px;
				border: 1px solid #ccc;
				box-sizing: border-box;
				color: #333;
				cursor: pointer;
				margin: 5px;
				border-radius: 3px;
			}
			.active{
				border: none;
				background: #4768f3;
				color: #fff;
			}
		}
		.tabBox{
			display: flex;
			box-sizing: border-box;
			.countBox{
				flex: 1;
				border-right: 1px solid #dadada;
				.menu-system-left {
				    flex: 1;
				    text-align: left;
				    padding: 10px;
					width: 50%;
					margin: 0 auto;
				    p {
				        color: #606266 !important;
				        font-size: 14px;
						margin-left: -10px;
				    }
				    .el-checkbox {
				            display: block !important;
				            margin-top: 10px;
				        .el-checkbox__inner {
				            border-radius: none !important;
				        }
				    }
				}
				.menu-system-table{
					flex: 1;
					text-align: left;
					padding: 10px;
					width: 100%;
					box-sizing: border-box;
					p {
					    color: #606266 !important;
					    font-size: 14px;
					}
				}
			}
		}
	}
</style>
