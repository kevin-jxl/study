<template>
	<div class="app-container calendar-list-container">
		<div class="filter-container crud-menu clearfix">
			<!--检索框-->
			<div class="form_search clearfix" style="padding:0 10px;">
				<el-form :inline="true" :model="listQuery" class="search-form-inline">
					<el-form-item label="输入搜索:">
						<el-input v-model.trim="listQuery.searchKey" placeholder="请输入工单编号/上报人"></el-input>
					</el-form-item>
					<el-form-item label="业务类型:">
                        <el-select v-model="listQuery.type" placeholder="请选择" :clearable="true">
                        <el-option v-for="(item,index) in businessType" :key="index" :label="item.name" :value="item.id"></el-option>
                        <!-- <el-option label="业务类型二" value="beijing"></el-option> -->
                        </el-select>
                    </el-form-item>
                    <el-form-item label="所属车场:">
						<el-input v-model.trim="listQuery.park" placeholder="请输入"></el-input>
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

				</el-form>
				<p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:-5px 0 28px 0;"></p>
			</div>
			<div class="float-fl table-title">用户列表</div>
		</div>
		<div style="height:calc(100% - 130px)">
			<z-table :data="tableData" @size-change="handleSizeChange" @current-change="handleCurrentChange" :total='total'>
				<div slot="table-column">
					<el-table-column fixed align="center" type="index" width="50" label="序号"></el-table-column>

					<el-table-column align="center" label="工单编号">
						<template slot-scope="scope">
							<span>{{scope.row.id}}</span>
						</template>
					</el-table-column>
					<el-table-column align="center" label="问题类型">
						<template slot-scope="scope">
							<span>{{scope.row.problemType}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="上报人">
						<template slot-scope="scope">
							<span>{{scope.row.reportPersonName}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="联系电话" width="200">
						<template slot-scope="scope">
							<span>{{scope.row.contact}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="上报位置" width="200" show-overflow-tooltip>
						<template slot-scope="scope">
							<span>{{scope.row.reportAddr}}</span>
						</template>
					</el-table-column>
					<el-table-column align="center" label="上报时间" width="200">
						<template slot-scope="scope">
							<span>{{scope.row.reportTime}}</span>
						</template>
					</el-table-column>
					<el-table-column align="center" label="所属车场">
						<template slot-scope="scope">
							<span>{{scope.row.ofPark}}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" label="进度状态">
						<template slot-scope="scope">
							<span>{{scope.row.process}}</span>
						</template>
					</el-table-column>
					<el-table-column label="操作" align="center" width="280">
						<template slot-scope="scope">
							<el-button size="mini" type="text" color="info" @click="handleDetail(scope.row)">详情</el-button>
							<el-button size="mini" type="text" @click="handleDelete(scope.row)">删除</el-button>
						</template>
					</el-table-column>
				</div>
			</z-table>
		</div>
		<div class="dialog_mask_mine">
			<div class="dialog_mask_mine">
			<el-dialog title="派遣信息" :visible.sync="dialogVisible" width="75%"
			 :close-on-click-modal="false">
				<div style="display: flex; flex-direction:row; justify-content: space-between; height:360px;">
					<div style="flex:1 1 25%; border-right: 1px solid #d9d9d9; padding-right:20px;">
                        <div class="dialog-title">
                            详情
                        </div>
                        <div class="scroll-view">
                            <details-card :details="orderDetails"></details-card>
                        </div>
                    </div>
                    <div style="flex: 1 1 25%; border-right: 1px solid #d9d9d9; padding: 0 20px;">
                        <div class="dialog-title">
                            附件
                        </div>
                        <div class="scroll-view">
                           <AttachmentCard :attachmentArray="attachmentArray"></AttachmentCard>
                        </div>
                    </div>
                    <div style="flex:1 1 25%; border-right: 1px solid #d9d9d9; padding: 0 20px;">
                        <div class="dialog-title">
                            办理经过
                        </div>
                        <div  class="scroll-view">
                            <TimeLineCard :workOrderProcess="workOrderProcess"></TimeLineCard>
                        </div>
                    </div>
                    <div style="flex:1 1 25%; padding-left: 20px;">
                        <div class="dialog-title">
                            审核结果
                        </div>
                         <div class="scroll-view">
                            <VerificationCard :workOrderVerify="workOrderVerify" :attachmentArray="attachmentArray"></VerificationCard>
                        </div>
                    </div>
				</div>
			</el-dialog>
		</div>

		</div>
	</div>

</template>

<script>
	import {
	    getDownloadExcel,
	} from "@/api/index.js";
	import ZTable from '@/components/ZTable.vue';
    import axios from 'axios';
    import DetailsCard from '@/components/dispatch/details-card.vue';
    import AttachmentCard from '@/components/dispatch/attachment-card.vue';
	import VerificationCard from '@/components/dispatch/verification-card.vue';
    import TimeLineCard from '@/components/dispatch/timeLine-card.vue';
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
                dialogVisible: false,
				listQuery: {
					page: 1,
					limit: 30,
					searchKey: '',
					type: '',
					park: '',
				}, //列表搜索请求参数
				total: 0, //总数
				value3: true,
				
				formLabelWidth: '100px', //弹框宽度
				tableData: null, //列表数据源
				dialogStatus: null, //判断新增还是编辑 ‘create’:新增，'update':编辑
				userData: null,
				value1: '',
				value2: '',
				orderDetails: {},
				attachmentArray: [],
				workOrderProcess: [],
				workOrderVerify: {},
				workOrderId: '1001',
				businessType: [
					{name: '故障', id:'故障'},
				]
			}
		},
		components: {
            ZTable,
            DetailsCard,
            AttachmentCard,
            TimeLineCard,
			VerificationCard
		},
		inject: ['reload'],
		created() {
			//获取用户信息和按钮权限
			// this.userData = JSON.parse(sessionStorage.getItem('userData'))
			// this.listQuery.userId = this.userData.sysUser.userId;
			// this.form.createId = this.userData.sysUser.userId;
			// this.form.createName = this.userData.sysUser.userRealname;
			
			// console.log(this.userData.permissions.includes('sys_area_del1'))
			
		},
		mounted() {
			this.getList();
    	},
		methods: {
			getList() { //获取列表
				let _this = this;
				_this.listLoading = true;
				_this.$api.getWorkOrderList(_this.listQuery).then(response => {
					console.log("工单列表："+JSON.stringify(response));
					_this.tableData = response.data.data.records;
					_this.total = response.data.data.total;
					_this.listLoading = false;
				});
			},
			//搜索
			onSubmit() {
				// this.listQuery.searchKey = this.listQuery.searchKey.trim();
				this.listQuery.searchKey = this.listQuery.searchKey.replace(
					/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g, "");
				this.listQuery.park = this.listQuery.park.replace(
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
			
			handleDetail(row){
				this.getWorkOrderDetails(row.id);
				setTimeout(()=> {
					this.dialogVisible = true;
				},500)
			},

			 //工单详情
        getWorkOrderDetails(id) {
            let _this = this;
            _this.listLoading = true;
            _this.$api.getWorkOrderDetails(id).then((result) => {
                _this.listLoading = false;
               
                _this.orderDetails = result.data.data;
                _this.getWorkOrderAttachment( _this.orderDetails.attachmentGroupId);
                _this.getWorkOrderProcess(id);
                _this.getWorkOrderVerify(id);
            });
        },
        // 工单附件
        getWorkOrderAttachment(attachmentGroupId) {
             let _this = this;
            _this.listLoading = true;
            // attachmentGroupId = '04NZLGZWWQNXYT8RC4LNTY2ESYRFMZWN'
            _this.$api.getWorkOrderAttachment(attachmentGroupId).then((result) => {
                 _this.listLoading = false;
                 console.log('附件：'+JSON.stringify(result));
                //  _this.attachmentArray = result.data.data;
                let datas = result.data.data;

                let voices  = datas.filter(e => {
                    return e.type == 1
                })
               this.attachmentArray.voice = voices;

               let imgs = datas.filter(e => {
                    return e.type == 2
                })
                this.attachmentArray.img = imgs;

                let videos = datas.filter(e => {
                    return e.type == 3
                })
                this.attachmentArray.video = videos;
            });
        },
        //办理过程
        getWorkOrderProcess(orderId) {
            let _this = this;
            _this.$api.getWorkOrderProcess(orderId).then((result) => {
                // console.log('办理过程：'+JSON.stringify(result))
                _this.workOrderProcess = result.data.data;
            });
        },
        //工单核查信息
        getWorkOrderVerify(orderId) {
             let _this = this;
            _this.$api.getWorkOrderVerify(orderId).then((result) => {
                console.log('工单核查：'+JSON.stringify(result))
                _this.workOrderVerify = result.data.data;
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
					this.$api.getWorkOrderDelete(row.id).then(res => {
						this.getList();
						this.$notify({
							title: "成功",
							message: "删除成功",
							type: "success",
							duration: 2000
						});
					})
				});
			},
			//页面刷新
			handleReload() {
				this.reload()
			},
			//导出表格模板字符串直接跳转
			handleExport() {
				if(!this.tableData || this.tableData.length == 0 ){
				    this.$message.error('当前表格暂无数据，请补充数据再导出！');
				}else{
					let params = {
						searchKey: this.listQuery.searchKey,
						type: this.listQuery.type,
						park: this.listQuery.park,
						tableName:'工单管理'
					}
				    getDownloadExcel(params,'/spark/schedule/workOrder/import').then(response => {});
				}
			},
			parentEvent(data){
				console.log(data)
			},
			handleAddPerson(){
				this.form.person.push({
					name:'',
					time:''
				})
			},
			handleAddTask(){
				this.form.task.push({
					name:'',
					equipment:''
				})
			},
			handleDel(array,i){
				array.splice(i,1);
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
	.dialgoBox{
		display: flex;
		.zdata{
			width: 450px;
			padding-right: 20px;
			border-right: 1px solid #ccc;
		}
		.cardTitle{
			font-size: 16px;
			font-weight: 600;
			margin-right: 20px;
		}
		.cardBox{
			width: 330px;
			padding: 20px;
			text-align: left;
			max-height: 344px;
			overflow-y: auto;
			.card{
				box-shadow: 0 2px 2px 0 rgba(91, 91, 91, 0.1), 0 3px 1px -2px rgba(91, 91, 91,0.1), 0 1px 5px 0 rgba(91, 91, 91,0.12);
				padding: 5px 10px;
				margin-top: 15px;
			}
			.count{
				display: flex;
				margin-top: 8px;
				div:nth-child(1){
					margin-right: 25px;
				}
				div span:nth-child(1){
					color: #9a9a9a;
				}
				div span:nth-child(2){
					color: #333;
				}
			}
		}
	} 
    .dialog-title {
        text-align:left;
        height:40px;
        line-height:40px;
        font-size:16px;
        font-weight:bold;
        color:#333333;
    }
    .scroll-view {
        overflow-y:auto; 
        overflow-x:hidden;
        height:300px;
    }
</style>
