<template>
  <div>
    <el-row class="row_main">
      <el-col :span="24" class="col_right">
        <el-container style="height: 100%;width: 100%">
          <el-main class="right_main">
            <div style="height: 100%">
              <div class="form_search">
                <el-form ref="form" label-width="60px" label-position="right">
                  <el-row :gutter="10" class="search_row">
                    <el-col :span="6">
                      <el-form-item label="房间">
                        <el-input size="small" clearable></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="6">
                      <el-form-item label="客户名称">
                        <el-input size="small" clearable></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="6">
                      <el-form-item label="租赁类型">
                        <el-select v-model="value" placeholder="请选择" size="small"
                                   clearable filterable
                        >
                          <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="6" class="search_button">
                      <template v-if="!formShow">
                        <el-button type="primary" size="small">查询</el-button>
                        <el-button size="small"
                                   type="text"
                                   @click="searchForm">展开<i class="el-icon-arrow-down"></i> </el-button>
                      </template>
                      <template v-else>
                        <el-form-item label="申请时间">
                          <el-date-picker
                            v-model="value1"
                            type="date"
                            size="small"
                            placeholder="请选择">
                          </el-date-picker>
                        </el-form-item>
                      </template>
                    </el-col>
                  </el-row>
                  <el-collapse-transition>
                    <div v-show="formShow">
                      <el-row :gutter="10" class="search_row">
                        <el-col :span="6">
                          <el-form-item label="开始时间">
                            <el-date-picker
                              v-model="startTime"
                              type="date"
                              size="small"
                              placeholder="请选择">
                            </el-date-picker>
                          </el-form-item>
                        </el-col>
                        <el-col :span="6">
                          <el-form-item label="结束时间">
                            <el-date-picker
                              v-model="endTime"
                              type="date"
                              size="small"
                              placeholder="请选择">
                            </el-date-picker>
                          </el-form-item>
                        </el-col>
                        <el-col :span="6">
                          <el-form-item label="活动类型">
                            <el-select
                              v-model="value11"
                              multiple
                              filterable
                              size="small"
                              collapse-tags
                              placeholder="请选择">
                              <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <el-row :gutter="10" class="search_row">
                        <el-col :span="12">
                          <el-button type="primary" size="small">查询</el-button>
                          <el-button size="small">重置</el-button>
                          <el-button size="small"
                                     type="text"
                                     @click="searchForm">收起<i class="el-icon-arrow-up"></i> </el-button>
                        </el-col>
                      </el-row>
                    </div>
                  </el-collapse-transition>
                </el-form>
              </div>
              <div class="tags_search">
                <el-row :gutter="20" class="tag_row">
                  <el-col :span="20">
                    <el-button plain size="small" class="basic_button">新增</el-button>
                    <el-button plain size="small" class="basic_button">修改</el-button>
                    <el-button plain size="small" class="basic_button">删除</el-button>
                    <el-button plain size="small" class="controller_button">导入</el-button>
                    <el-button plain size="small" class="controller_button">导出</el-button>
                    <el-button plain size="small" class="controller_button">打印</el-button>
                    <el-button plain size="small" class="other_button">审批</el-button>
                    <el-button plain size="small" class="other_button">确认</el-button>
                    <el-button plain size="small" class="danger_button">驳回</el-button>
                    <el-button plain size="small" class="danger_button">取消</el-button>
                    <el-button plain size="small" class="danger_button">作废</el-button>
                  </el-col>
                  <el-col :span="4">
                    <el-button size="small"
                               type="text"
                               style="float: right"
                               @click="searchTags">{{tagShow?'收起':'展开'}}<i :class="tagShow?'el-icon-arrow-up':'el-icon-arrow-down'"></i> </el-button>
                  </el-col>
                </el-row>
                <el-collapse-transition>
                  <div v-show="tagShow">
                    <el-row :gutter="20" class="tag_row">
                      <el-button plain size="small" class="basic_button">修改</el-button>
                      <el-button plain size="small" class="other_button">审批</el-button>
                      <el-button plain size="small" class="controller_button">打印</el-button>
                      <el-button plain size="small" class="bardian_button">品牌变更</el-button>
                      <el-button plain size="small" class="bardian_button">退铺</el-button>
                      <el-button plain size="small" class="bardian_button">主体及品牌变更</el-button>
                      <el-button plain size="small" class="bardian_button">租金减免</el-button>
                      <el-button plain size="small" class="controller_button">导出</el-button>
                      <el-button plain size="small" class="controller_button">锁定</el-button>
                      <el-button plain size="small" class="danger_button">作废</el-button>
                    </el-row>
                    <el-row :gutter="20" class="tag_row">
                      <el-button plain size="small" class="basic_button">修改</el-button>
                      <el-button plain size="small" class="other_button">审批</el-button>
                      <el-button plain size="small" class="controller_button">打印</el-button>
                      <el-button plain size="small" class="bardian_button">品牌变更</el-button>
                      <el-button plain size="small" class="bardian_button">退铺</el-button>
                      <el-button plain size="small" class="bardian_button">主体及品牌变更</el-button>
                      <el-button plain size="small" class="bardian_button">租金减免</el-button>
                      <el-button plain size="small" class="controller_button">导出</el-button>
                      <el-button plain size="small" class="controller_button">锁定</el-button>
                      <el-button plain size="small" class="danger_button">作废</el-button>
                    </el-row>
                  </div>
                </el-collapse-transition>
              </div>
              <div class="table_search" :class="{open_search:formShow,open_tags:tagShow,open_all:formShow&&tagShow}">
                <el-table
                  ref="searchMulTable"
                  :data="tableData"
                  :cell-style="cellStyle"
                  :header-cell-style="headerCellStyle"
                  height="calc(100% - 30px)"
                  @row-dblclick="tableRowDoubleClick"
                  @row-click="tableRowClick"
                  @selection-change="tableSelectionChange"
                >
                  <el-table-column fixed="left"
                    type="selection">
                  </el-table-column>
                  <el-table-column align="center" type="index"
                                   label="序号" min-width="4">
                  </el-table-column>
                  <el-table-column
                    prop="id"
                    label="id"
                    align="center"
                    min-width="10"
                    :sortable="true" sort-by="id">
                  </el-table-column>
                  <el-table-column
                    prop="contractType"
                    label="合同类型"
                    min-width="10"
                    align="center"
                    show-overflow-tooltip>
                    <template slot-scope="scope">
                      <span>{{ scope.row.contractType | typeFilter }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="project"
                    label="项目"
                    align="center"
                    min-width="10"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    prop="stages"
                    align="center"
                    min-width="7"
                    label="分期"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    prop="room"
                    label="房间"
                    align="center"
                    min-width="10"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    prop="name"
                    align="center"
                    min-width="10"
                    label="客户名称"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    prop="brand"
                    align="center"
                    min-width="10"
                    label="品牌"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    prop="contractNo"
                    label="合同编号"
                    align="center"
                    min-width="10"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    prop="chargePerson"
                    align="center"
                    min-width="10"
                    label="经办人"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    prop="applyTime"
                    align="center"
                    min-width="15"
                    label="申请时间"
                    :sortable="true"
                    :sort-method="sortTime"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    prop="status"
                    align="center"
                    min-width="10"
                    label="审批状态">
                    <template slot-scope="scope">
                      <span>{{ scope.row.status | statusFilter }}</span>
                    </template>
                  </el-table-column>
                </el-table>
                <div class="pagination_footer">
                  <el-pagination @size-change="handleSizeChange"
                                 @current-change="handleCurrentChange"
                                 :current-page.sync="currentPage"
                                 :page-sizes="[10,20,30,50]"
                                 :page-size="pageSize"
                                 background
                                 layout=" total, prev, pager, next, jumper, sizes"
                                 prev-text=" <<上一页 "
                                 next-text=" 下一页>> "
                                 :total="total">
                  </el-pagination>
                </div>
              </div>
            </div>
          </el-main>
        </el-container>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import ZTree from "../../components/common/zTree";
  import {mapGetters} from "vuex";
  export default {
    name:'demo',
    components: {ZTree},
    data(){
      return {
        formShow:false,
        tagShow:false,
        currentPage:1,
        total:18,
        pageSize:20,
        tableData: [
          {
            id:1,
          contractType:1,
          project:'时代广场1',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190701',
          status:1
        }, {
            id:2,
          contractType:1,
          project:'时代广场2',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190702',
          status:1
        }, {
            id:3,
          contractType:1,
          project:'时代广场3',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190703',
          status:1
        }, {
            id:4,
          contractType:1,
          project:'时代广场4',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190704',
          status:1
        },{
            id:5,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190705',
          status:1
        }, {
            id:6,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        }, {
            id:7,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        }, {
            id:8,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        },{
            id:9,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        }, {
            id:10,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        }, {
            id:11,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        }, {
            id:12,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        },{
            id:13,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        }, {
            id:14,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        }, {
            id:15,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        }, {
            id:16,
          contractType:1,
          project:'时代广场',
          stages:'四期',
          room:'25棟-甲-100（租）',
          name:'张三',
          brand:'海尔家居',
          contractNo:'23412341243',
          chargePerson:'李四',
          applyTime:'20190708',
          status:1
        }
        ],
        multipleSelection:[],
        headerCellStyle:{
          padding:'5px 0',
          color:'#898989',
          backgroundColor:'#fafbfa',
          fontWeight:500
        },
        cellStyle:{
          padding:'7px 0',
          color:'#5d5d5d',
          lineHeight:'35px',
          fontWeight:500
        },
        options:[
          {
            label:'正常',
            value:'1'
          },
          {
            label:'失效',
            value:'2'
          }
        ],
        value:'',
        value1:'',
        value6:'',
        value11:'',
        startTime:null,
        endTime:null,
        endTimeOptions:{
          disabledDate(time) {
            if(this.startTime){
              return time.getTime() < Date.now();
            }else {
              return true
            }
          },
        }
      }
    },
    computed:{
      ...mapGetters(['menus'])
    },
    filters:{
      typeFilter(status){
        return '商业类租赁'
      },
      statusFilter(status){
        const statusDef = {
          1:'正常',
          0:'无效'
        }
        return statusDef[status]
      }
    },
    methods:{
      searchForm(){
        this.formShow = !this.formShow
      },
      searchTags(){
        this.tagShow = !this.tagShow
      },
      tableRowDoubleClick(row, event){
        this.$router.push({path: `/demo/index/${row.contractNo}`})
      },
      tableRowClick(row, event, column){
        this.$refs.searchMulTable.toggleRowSelection(row);
      },
      tableSelectionChange(val){
        this.multipleSelection = val;
      },
      handleSizeChange(){

      },
      handleCurrentChange(){

      },
      sortTime(a,b){
        return a.applyTime - b.applyTime
      }
    }
  }
</script>

<style lang="scss" scoped>
  .row_main{
    height: 100%;
    padding: 15px;
    /*overflow: hidden;*/
  }
  .col_left{
    height: 100%;
    /*padding: 10px;*/
    /*border: 1px solid #e5e5e5;*/
  }
  .col_right{
    height: 100%;
  }
  .right_main{
    height: 100%;
    padding:0 30px;
    overflow-y: overlay;
  }
  .row_main.el-scrollbar.el-scrollbar__wrap{
    overflow-x:hidden !important;
  }
  .search_row{
    height: 40px;
    .el-col{
      height: 40px;
    }
    button{
      //margin-top: 6px;
    }
    .search_button{
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    /deep/ .el-date-editor{
      .el-range-input{
        font-size: 12px;
      }
    }
  }
  .tag_row{
    margin-left: 0 !important;
    padding-bottom: 2px;
    .el-col{
      padding: 0 !important;
    }
    .el-col:last-child{
      padding: 0 10px 0 0 !important;
    }
  }
  .form_search{
    /*background-color: #f6fbfa;*/
    padding:3px;
    margin-bottom: 5px;
    /deep/ .el-form-item{
      margin:0;
    }
  }
  .tags_search{
    padding:3px;
  }
  .table_search{
    height:calc(100% - 90px);
    .el-table{
      font-size: 12px;
      border: 1px solid #e5e5e5;
    }
    .pagination_footer{
      height:30px;
      text-align: center;
      padding:0;
      .el-pagination{
        padding:2px 0 0 0;
      }
    }
    /deep/ .cell{
      line-height: 20px;
    }
    transition: height .4s linear;
  }
  .open_search{
    height:calc(100% - 170px);
  }
  .open_tags{
    height:calc(100% - 154px);
  }
  .open_all{
    height:calc(100% - 234px);
  }

  .el-button--small, .el-button--small.is-round {
    padding: 8px 10px !important;
  }
</style>
