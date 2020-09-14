<template>
    <div class="app-container calendar-list-container">
		<div class="business-flex filter-container crud-menu clearfix">
			<div class="person-left">
			    <div class="left-content">
			        <div class="header">
			           <div class="header-flex-left">
			               <div class="header-left">
			                   <el-tabs v-model="activeName" @tab-click="handleClick">
									<el-tab-pane v-for="(item,index) in activeList" :key="index" :label="item.label" :name="item.name"></el-tab-pane>
			                   </el-tabs>
			               </div>
			           </div>
			           <div class="header-flex-right">
			               <div class="header-right clearfix">
			                   <el-form :inline="true" :model="listQuery" class="search-form-inline" >
			                       <el-form-item label="" >
			                            <el-date-picker  v-model="selectDate" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
			                       </el-form-item>
			                        <el-form-item label="" >
			                           <el-input v-model="listQuery.keyword" placeholder="请输入关键字"></el-input>
			                       </el-form-item>
			                      <el-form-item>
			                           <el-button class="el-icon-search" @click="onSubmit" ></el-button>
			                       </el-form-item>
											
									<el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-start">
										<span class="el-icon-refresh-right " style="padding:7px; margin-right: 20px;" @click="handleReload()"></span>
									</el-tooltip>
									<el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-start">
										<span class="el-icon-download " style="padding:7px;margin-right: 10px; margin-left: 30px;" @click="handleExport()"></span>
									</el-tooltip>
			                   </el-form>
			               </div>
			           </div>
			        </div>
			        <div class="table">
			            <div class="table-content">
								<div class="table-header">
								   <div class="float-fl table-title">数据列表</div>
								   <div style=" margin-top:10px;">
									   <el-button type="primary" @click="openDispatchPop" icon="el-icon-s-unfold" size="mini" style="margin-bottom:10px;">派遣</el-button>
									   <el-button type="primary" @click="openOverseePop" icon="el-icon-time" size="mini" style="margin-bottom:10px;">督办 </el-button>
									   <el-button type="primary" @click="openVerificationPop" icon="el-icon-bank-card" size="mini" style="margin-bottom:10px;">核查</el-button>
									   <el-button type="primary" @click="handleDelete" icon="el-icon-delete" size="mini" style="margin-bottom:10px;">删除</el-button>
								   </div>
								</div>
								<div class="table-view">
								   <RadioZTable 
									   :data="tableData"
									   :currentPage="currentPage"
									   :pageSize="pageSize"
									   :total='total'
									   @size-change="handleSizeChange"
									   @current-change="handleCurrentChange"
												@select-all="onSelectAll"
												@selection-change="selectItem"
												@row-click="onSelectOp">  
									   <div slot="table-column">
												
										   <el-table-column align="center"  type="selection" width='55' label="" fixed></el-table-column>
													
										   <el-table-column align="center" label="工单编号" width="120">
											   <template slot-scope="scope">
												   <span>{{scope.row.id}}</span>
											   </template>
										   </el-table-column>
				
										   <el-table-column align="center" label="状态">
											   <template slot-scope="scope">
												   <span>{{scope.row.process}}</span>
											   </template>
										   </el-table-column>
				
										   <el-table-column align="center" label="设备名称" width="120">
											   <template slot-scope="scope">
												   <span>{{scope.row.equipmentName}}</span>
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
										   
										   <el-table-column align="center" label="上报地点" width="200" show-overflow-tooltip>
											   <template slot-scope="scope">
												   <span>{{scope.row.reportAddr}}</span>
											   </template>
										   </el-table-column>
													
										   <el-table-column align="center" label="上报时间" width="200" show-overflow-tooltip>
											   <template slot-scope="scope">
												   <span>{{scope.row.reportTime}}</span>
											   </template>
										   </el-table-column>
													
										   <el-table-column align="center" label="描述" width="200" show-overflow-tooltip>
											   <template slot-scope="scope">
												   <span>{{scope.row.problemDesc}}</span>
											   </template>
										   </el-table-column>
												
									   </div>
								   </RadioZTable>
								</div>
							</div>
						</div>
			        <div class="details">
			              <div class="process-left">
			                  <div class="image-swiper">
			                        <el-carousel indicator-position="outside" arrow="always" v-show="currentTable.id != undefined">
			                           <el-carousel-item v-for="(item,index) in attachmentArray.img" :key="index">
			                                <el-image :src="item.path"></el-image>
			                           </el-carousel-item>
			                       </el-carousel>
			                  </div>
			               </div>   
			               <div class="process-right">
			                   <el-tabs v-model="subActive"  @tab-click="handleSubClick">
											<el-tab-pane v-for="(item, index) in subActiveList" :key="index" :label="item.label" :name="item.name"></el-tab-pane>
			                   </el-tabs>
										
			                   <div v-if="subActive==1" class="process-detail">
			                       <div class="scroll-view">
			                           <details-card v-show="currentTable.id != undefined" :details="orderDetails"></details-card>
			                       </div>
			                   </div>
										
			                   <div  v-if="subActive==2" class="process-detail">
			                        <div class="scroll-view">
			                           <AttachmentCard v-show="currentTable.id != undefined" :attachmentArray="attachmentArray"></AttachmentCard>
			                       </div>
			                   </div>
										
			                   <div v-if="subActive==3"  class="process-detail">
			                       <div class="scroll-view">
			                           <TimeLineCard v-show="currentTable.id != undefined" :workOrderProcess="workOrderProcess"></TimeLineCard>
			                       </div>
			                   </div>
			
			                   <div v-if="subActive==4"  class="process-detail">
			                       <div class="scroll-view">
			                           <VerificationCard v-show="currentTable.id != undefined" :workOrderVerify="workOrderVerify" :attachmentArray="attachmentArray"></VerificationCard>
			                       </div>
			                   </div>
										
			               </div>
			        </div>
			    </div>
			</div>
			<div class="person-right">
			    <div id="amap-main"></div> 
			</div>
		</div>
         
        
        <div class="dialog_mask_mine">
			<el-dialog title="派遣信息" :visible.sync="dispathDialogVisible" width="71%" :close-on-click-modal="false" @close="dispathDialogClose">
				<div style="display: flex; flex-direction:row; justify-content: space-between; min-height:380px;">
					<div style="flex:1 1 20%;border-right: 1px solid #d9d9d9; padding-left: 40px;">
                         <div class="dialog-title">
                            详情
                        </div>
                         <div class="pop-scroll-view" style="width: 280px; padding-right: 20px;">
                              <details-card  :details="orderDetails"></details-card>
                         </div>
                    </div>
                    <div style="flex: 1 1 80%;">
                        <DispatchPop ref="dispatch" :workOrderId="currentTable.id" @dispatch-success="handleReload" @cancel-dispatch-pop="dispathDialogVisible=false"></DispatchPop>
                        
                    </div>
                    <!-- <div style="flex:0 0 30%; padding-left: 20px">
                         <AMapPop></AMapPop>
                    </div> -->
				</div>
			</el-dialog>
		</div>
		
         <div class="dialog_mask_mine">
			<el-dialog title="督办信息" :visible.sync="overseeDialogVisible" width="960px" :close-on-click-modal="false" @close="dispathDialogClose">
				<div style="display: flex; flex-direction:row; min-height:380px;">
					<div style="flex: 0 0 300px; border-right: 1px solid #d9d9d9; padding-left: 40px; padding-bottom: 20px;">
                        <div class="dialog-title">
                            详情
                        </div>
                         <div class="pop-scroll-view" style="width: 280px; padding-right: 20px;">
                              <details-card :details="orderDetails"></details-card>
                         </div>
                    </div>
                    <div style="flex: 0 0 570px; padding-left: 20px; padding-right: 40px;">
                        <div class="dialog-title">
                            督办
                        </div>
                         <div class="pop-scroll-view">
                             <OverseePop ref="dispatch" :workOrderId="currentTable.id" @oversee-success="handleReload" @cancel-oversee-pop="overseeDialogVisible=false"></OverseePop>
                         </div>
                    </div>
				</div>
			</el-dialog>
		</div>
		
        <div class="dialog_mask_mine">
			<el-dialog title="完成结果" :visible.sync="verificationDialogVisible" width="35%" :close-on-click-modal="false" @close="dispathDialogClose">
				<div style="display: flex; flex-direction:row; justify-content: space-between; min-height:380px;">
                    <div class="pop-scroll-view" style="height: 490px;">
                        <VerificationPop ref="dispatch" :workOrderId="currentTable.id" :attachmentArray="attachmentArray" @verification-success="handleReload" @cancel-verification-pop="verificationDialogVisible=false"></VerificationPop>
                    </div>
				</div>
			</el-dialog>
		</div>

    </div>
</template>

<script>
import { mapGetters } from "vuex";
import RadioZTable from '@/components/RadioZTable.vue';
import DetailsCard from '@/components/dispatch/details-card.vue';
import AttachmentCard from '@/components/dispatch/attachment-card.vue';
import VerificationCard from '@/components/dispatch/verification-card.vue';
import TimeLineCard from '@/components/dispatch/timeLine-card.vue';
import DispatchPop from '@/components/dispatch/popup/dispatch-pop.vue';
import OverseePop from '@/components/dispatch/popup/oversee-pop.vue';
import VerificationPop from '@/components/dispatch/popup/verification-pop.vue'
// import AMapPop from '@/components/dispatch/popup/amap-pop.vue';
import { lazyAMapApiLoaderInstance } from 'vue-amap';
import {
	    getDownloadExcel,
	} from "@/api/index.js";
    export default {
    name: "table_role",
    components: { 
        RadioZTable,
        DetailsCard,
        AttachmentCard,
		VerificationCard,
        TimeLineCard,
        DispatchPop,
        OverseePop,
        VerificationPop,
        // AMapPop
    },
    watch:{
        $route(to, from) {
            console.log(window.location.href);
            console.log(this.$route.path);
            let href = window.location.href;
            let last = href.charAt(href.length-1)
            console.log(last);
            if(last ==1) {
                this.activeName = '0';
            } else if(last == 2) {
                this.activeName = '1';
            } else if(last == 3) {
                 this.activeName = '2';
            } else if (last == 4) {
                this.activeName = '3';
            }
        },
		activeName(nVal, oVal) {
			this.currentTable = {};
		}
    },
	data() {
	  return {
	    dispathDialogVisible: false,
	    overseeDialogVisible: false,
	    verificationDialogVisible: false,
	    activeName: '0',
		activeList: [
			{label:'全部信息',name:'0'},
			{label:'派遣栏',name:'1'},
			{label:'处置栏',name:'2'},
			{label:'完成栏',name:'3'}
		],
	    subActive: '1',
		subActiveList: [
			{label:'详细信息',name:'1'},
			{label:'附件信息',name:'2'},
			{label:'办理经过',name:'3'},
			{label:'结果核查',name:'4'}
		],
	    selectDate: '',
		page: 1,
		limit: 30,
	    listQuery: {
	        keyword: ''
	    },
	    tableData: null,
		currentTable:{},
	    orderDetails: {},
	    attachmentArray: [],
	    workOrderProcess: [],
	    workOrderVerify: {},
	    currentPage:1,
	    pageSize:30,
	    total: 0,
	    banner:[
	        {title:"banner1",href: require("../../../assets/images/global.jpg")},
			{title:"banner2",href:require("../../../assets/images/globals.jpg")}
		],
		map:'',
		marker: ''
	  };
	},
    mounted() {
		let _this = this;
		_this.getList();
		_this.getWorkOrderStatistics();
		lazyAMapApiLoaderInstance.load().then(() => {
		    _this.map = new AMap.Map('amap-main', 
		    {   
		        zoom:11,
		        center: new AMap.LngLat("116.368904","39.913423")
				// center: new AMap.LngLat(_this.orderDetails.reportLng,_this.orderDetails.reportLat)
		        });
		    _this.map.addControl(new AMap.Scale());
		});
    },
   
    inject:['reload'],
    methods: {
		dispathDialogClose() {
			console.log('关闭弹窗');
			this.$refs.dispatch.cancel('ruleForm')
		},
		getList() { //获取列表
			let _this = this;
			let params = {
				page: this.page,
				limit: this.limit,
				searchKey: this.listQuery.keyword,
				date: this.selectDate ? this.selectDate : '',
				type: this.activeName
			}
			_this.listLoading = true;
			_this.$api.getWorkOrderAssignList(params).then(res => {
				console.log('调度数据：'+JSON.stringify(res));
				_this.tableData = res.data.data.records;
				_this.tableData.concat(res.data.data.records);
				_this.total = res.data.data.total;
				_this.listLoading = false;
				_this.getWorkOrderStatistics();
			});
			
		},
		
		getWorkOrderStatistics() {
			
			this.$api.getWorkOrderStatistics().then(res=>{
				console.log('统计：'+JSON.stringify(res));
				let data  = res.data.data;
				this.activeList[1].label = `派遣栏 （${data[0].v}）`;
				this.activeList[2].label = `处置栏 （${data[1].v}）`;
				this.activeList[3].label = `完成栏 （${data[2].v}）`;
			})
		},
			
		
		/**
		 * table单选操作
		 */
		onSelectAll() {
		    // this.$refs.multipleTable.clearSelection();
			console.log('全部取消');
		},
		selectItem(row) {
		    console.log('单选框',JSON.stringify(row));
			if(row.length>0) {
				this.currentTable = row[0];
				this.getWorkOrderDetails(this.currentTable.id)
			} else {
				this.currentTable = {};
				if(this.marker) {
					this.map.remove(this.marker);
				}
			}
			
		},
		onSelectOp(row) {
		     console.log('单选行',JSON.stringify(row));
			 this.currentTable = row;
			 this.getWorkOrderDetails(this.currentTable.id)
		},
		
        // id: 1001,1002,1003,1004
        //工单详情
        getWorkOrderDetails(id) {
            let _this = this;
            _this.listLoading = true;
            _this.$api.getWorkOrderDetails(id).then((result) => {
                _this.listLoading = false;
               console.log('详情：'+JSON.stringify(result));
                _this.orderDetails = result.data.data;
                _this.addPine();
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
                //  console.log('附件：'+JSON.stringify(result));
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
                console.log('办理过程：'+JSON.stringify(result))
                _this.workOrderProcess = result.data.data;
            });
        },
        //工单核查信息
        getWorkOrderVerify(orderId) {
             let _this = this;
            _this.$api.getWorkOrderVerify(orderId).then((result) => {
                _this.workOrderVerify = result.data.data;
				console.log('工单核查：'+JSON.stringify(_this.workOrderVerify))
            });
        },

        handleClick(tab, event) {
            console.log(tab, event);
			this.getList();
        },
        handleSubClick(tab, event) {
            console.log(tab.name);
            this.subActive = tab.name;
        },
      //搜索
		onSubmit() {
			this.listQuery.keyword = this.listQuery.keyword.trim();
			this.listQuery.keyword = this.listQuery.keyword.replace(
				/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g, "");
			this.getList();
		},
        //页面刷新
		handleReload() {
			this.reload()
		},
		//导出表格模板字符串直接跳转
		handleExport() {
			if( !this.tableData || this.tableData.length == 0 ){
			    this.$message.error('当前表格暂无数据，请补充数据再导出！');
			}else{
				let params = {
					searchKey: this.listQuery.keyword,
					date: this.selectDate ? this.selectDate : '',
					type: this.activeName,
					tableName:'调度管理'
				}
			    getDownloadExcel(params,'/spark/schedule/import').then(response => {});
			}
        },
         // 分页
        handleSizeChange(val) {
            this.limit = val;
            this.getList();
        },
         handleCurrentChange(val) {
            this.page = val;
            this.getList();
        },
        addPine() {
               let _this = this;
                // 创建一个 Marker 实例：
				if(_this.marker) {
					_this.map.remove(_this.marker);
				}
				let LngLat = [_this.orderDetails.reportLng,_this.orderDetails.reportLat]
				_this.map.setCenter(LngLat);
                _this.marker  = new AMap.Marker({
					position: new AMap.LngLat(LngLat[0],LngLat[1]),
                    offset: new AMap.Pixel(-10, -10),
                    title: '北京'
                });
                _this.map.add([ _this.marker]);
        },
        openDispatchPop() {
			console.log(this.currentTable.id);
			// this.$message.info('请先选中一列数据')
			// this.dispathDialogVisible = true;
			if(this.currentTable.id == undefined) {
				return	this.$message.info('请先选中一列数据')
			}
			if(this.currentTable.process !='待派遣') {
				return	this.$message.info(`当前工单${this.currentTable.process}`)
			}
            this.dispathDialogVisible = true;
        },
        openOverseePop() {
			if(this.currentTable.id == undefined) {
				return	this.$message.info('请先选中一列数据')
			}
			if(this.currentTable.process !='已派遣' &&this.currentTable.process !='已受理') {
				return	this.$message.info(`当前工单${this.currentTable.process}`)
			}
            this.overseeDialogVisible = true;
        },
        openVerificationPop() {
			if(this.currentTable.id == undefined) {
				return	this.$message.info('请先选中一列数据')
			}
			if(this.currentTable.process !='已完成') {
				return	this.$message.info(`当前工单${this.currentTable.process}`)
			}
            this.verificationDialogVisible = true;
        },
        //删除
		handleDelete() {
			if(this.currentTable.id == undefined) {
				return	this.$message.info('请先选中一列数据')
			}
            const rowObj = this.currentTable;
            let _this = this;
			_this.$confirm(
				"确定要删除该条信息吗?",
				"提示框", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning"
				}
			).then(() => {
				_this.$api.getWorkOrderDelete(rowObj.id).then(response => {
                    //刪除工单
                });
			});
		},
    }
  };
</script>
<style>
	.el-dialog__body {
	    padding: 0px 0px !important;
	    color: #666 !important;
	    font-size: 14px !important;
	    word-break: break-all !important;
	}
    .el-tabs__header {
        padding: 0;
        position: relative;
        margin: 0 !important;
    }
    .image-swiper {
        width: 400px; height:200px;
    }
    .el-carousel__container {
        position: relative;
        height: 200px !important;
    }
    .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 200px !important;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
  thead .el-table-column--selection .cell{
      display: none !important;
  }
</style>

<style lang="scss" scoped>
	.el-form-item {
	    margin-bottom: 10px !important;
	}
	
.business-flex{
    display: flex;
    flex-direction: row;
	height: 100%;
}
.person-left {
    flex: 0 0 75%;
    height: calc(100%);
    // background-color:aquamarine;
    
}
.person-right{
    flex: 0 1 25%;
    height: calc(100%);
    #amap-main{
        height: 100%;
        width: 100%;
    }
}

.left-content {
    height: 100%;
    display: flex;
    flex-direction:column;
    .header {
        flex: 0 0 4%;
        background-color: #fff;
        display: flex;
        flex-direction: row;
        border-bottom: 1px solid #d9d9d9;
        .header-flex-left {
            flex: 0 1 40%;
            position: relative;
            .header-left {
                position: absolute;
                left:0;
                bottom: 0;
            }
        }
        .header-flex-right {
            flex: 0 1 60%;
            .header-right {
                height: 100%;
                width: 100%;
                display: flex;
                flex-direction: row;
                justify-content: flex-end;
            }
        }
    }
    .table{
        flex: 0 0 55%;
        border-bottom: 1px solid #d9d9d9;
        
        .table-content {
            height: 100%;
            display: flex;
            flex-direction: column;
            padding: 0 20px;
            .table-header {
                flex: 0 1 50px;
                display: flex;
                flex-direction: row;
                justify-content: space-between;
                align-items: center;
            }
            .table-view {
                flex: auto;
                // height: 394px;
            }
        }
    }
    .details {
        flex: 0 1 41%;
        display: flex;
        flex-direction: row;
        padding-top: 10px;
		padding-right: 20px;
        .process-left {
            flex: 0 1 40%;
            border-right: #ececec solid 1px;
            display: flex;
             flex-direction: row;
             justify-content: center;
             align-items: center;
        }
        .process-right {
            flex: 0 1 60%;
			// margin-right: 1.5%;
            // border-right: #ececec solid 1px;
            .process-detail {
                padding: 10px 0 10px 40px;
                text-align: left;
            }
            .detail-item {
                min-height: 25px;
                line-height: 25px;
            }
            .attachment-item {
                    display: flex;
                    flex-direction: row;
                    justify-content: flex-start;
            }
            .img-view {
                width: 55px; height: 55px; margin-right: 10px;
            }
            .voice-item {
                display: flex;
                flex-direction: column;
                justify-content: flex-start;
                div {
                    width: 150px;
                    height: 22px;
                    margin-top: 5px;
                    background-color: #33AEFE;
                    border-radius: 4px;
                }
            }
        }
    }
    .scroll-view {
        overflow-y:auto; 
        overflow-x:hidden;
        width:100%; 
        height:240px;
    }
    
}
.pop-scroll-view {
     overflow-y:auto; 
        overflow-x:hidden;
        width:100%; 
        height:380px;
}
.dialog-title {
        text-align:left;
        height:40px;
        line-height:40px;
        font-size:16px;
        font-weight:bold;
        color:#333333;
 }
.el-button--mini, .el-button--mini.is-round {
    padding: 5px !important;
}
</style>