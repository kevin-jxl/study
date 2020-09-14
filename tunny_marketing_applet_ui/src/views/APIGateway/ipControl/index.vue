<template>
  <div class="api_ip_control">
    <div class="ip_search">
      <div class="search_item">
        <span>策略名称:</span>
        <el-input
          placeholder="请输入关键字"
          v-model="strategyName"
          size="mini"
          clearable>
        </el-input>
      </div>
      <el-button type="primary" size="mini" @click="getStrategyList">查询</el-button>
      <el-button size="mini" @click="resetGetStrategyList">重置</el-button>
      <el-button size="mini" @click="handleAddOpen">新增</el-button>
    </div>
    <div class="ip_list">
      <div class="detail_lists">
        <el-table
          ref="dataTable"
          :data="tableData"
          height="100%"
          v-loading="tableLoading"
        >
          <el-table-column align="center" type="index"
                           label="序号" min-width="5">
          </el-table-column>
          <el-table-column
            label="策略名称"
            min-width="10"
            align="center"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.policyName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="project"
            label="策略类型"
            align="center"
            min-width="10"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.policyType | strategyTypeFilter}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="stages"
            align="center"
            min-width="25"
            label="IP地址"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.ipAddress}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="stages"
            align="center"
            min-width="10"
            label="最后修改时间"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.updateTime | moment('YYYY-MM-DD HH:mm:ss')}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="room"
            label="操作"
            align="center"
            min-width="20"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <el-button type="text" size="mini" @click="handleEditOpen(scope.row)">编辑</el-button>
              <el-button type="text" size="mini" @click="handleApiBindOpen(scope.row)">绑定API</el-button>
              <el-button type="text" size="mini" style="color: #aa7a22" @click="handleStrategyDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="detail_pagination">
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page.sync="currentPage"
                       :page-sizes="[10,20,30,50]"
                       :page-size="pageSize"
                       class="pull-center"
                       background
                       layout=" total, prev, pager, next, jumper, sizes"
                       prev-text=" <<上一页 "
                       next-text=" 下一页>> "
                       :total="total">
        </el-pagination>
      </div>
    </div>
    <el-dialog
      title="提示"
      v-if="strategyAddVisible"
      :visible.sync="strategyAddVisible"
      class="strategy_add_dialog"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">{{strategyText}}</span>
      </div>
      <div class="dialog_main">
        <el-form ref="strategyAddForm"
                 :model="strategyAddForm"
                 :rules="strategyAddRules"
                 label-width="80px"
                 hide-required-asterisk="true"
                 label-position="right"
        >
          <el-form-item label="策略名称" size="small" prop="policyName">
            <el-input v-model="strategyAddForm.policyName"></el-input>
          </el-form-item>
          <el-form-item label="策略类型" size="small" prop="policyType">
            <el-select v-model="strategyAddForm.policyType" placeholder="请选择应用类型" size="small">
              <el-option v-for="item in strategyTypeOptions"
                         :label="item.label"
                         :value="item.value"
                         :key="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="IP地址" prop="ipAddress">
            <el-input type="textarea" v-model="strategyAddForm.ipAddress" resize="none"></el-input>
            <div>多个用;隔开</div>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="strategyAddVisible = false" size="small">取 消</el-button>
        <el-button v-if="strategyText==='新增'" type="primary" size="small" @click="handleStrategyAdd">确 定</el-button>
        <el-button v-else type="primary" size="small" @click="handleStrategyEdit">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="提示"
      v-if="strategyApiBindVisible"
      :visible.sync="strategyApiBindVisible"
      class="api_bind_dialog"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">绑定API</span>
      </div>
      <div class="dialog_main">
        <div class="alert_message">
          <i class="el-icon-info"></i>
          <span class="alert_title">请注意：如果API上原来已经绑定了一个策略，则会被本策略覆盖，请慎重选择！</span>
        </div>
        <div class="edit_form">
          <div class="app_func_api">
            <div class="api_title">接口权限(选填)</div>
            <div class="api_list">
              <div class="list_transfer">
                <el-transfer
                  style="text-align: left; display: inline-block"
                  v-model="apiList"
                  filterable
                  :titles="['选择接口', '已选择接口']"
                  :format="{
                    noChecked: '${total}',
                    hasChecked: '${checked}/${total}'
                  }"
                  :data="appApiList">
                  <span slot-scope="{ option }">{{ option.path }} - {{ option.label }}</span>
                </el-transfer>
              </div>
            </div>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="strategyApiBindVisible = false" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="handleStrategyApiAdd">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import Qs from 'qs'
  import {fetchStrategyList,doStrategyAdd,doStrategyEdit,
    fetchStrategyInfo,fetchStrategyApiList,doStrategyApiAdd,
    doStrategyDel} from '@/api/gateway/strategyList'
  import {fetchApiAllList} from '@/api/gateway/apiList'
  export default {
    name:'ip-control',
    data(){
      return {
        strategyName:'',
        tableData:[],
        currentPage:1,
        pageSize:10,
        total:0,
        strategyAddVisible:false,
        strategyApiBindVisible:false,
        strategyAddForm:{
          policyName:'',
          policyType:0,
          ipAddress:''
        },
        strategyAddRules:{
          policyName: [
            {
              required: true,
              message: "请输入策略名称",
              trigger: "blur"
            }
          ],
          policyType: [
            {
              required: true,
              message: "请选择策略类型",
              trigger: "blur"
            }
          ],
          ipAddress: [
            {
              required: true,
              message: "请输入IP地址",
              trigger: "blur"
            }
          ]
        },
        strategyText:'',
        strategyTypeOptions:[
          {
            label:'拒绝-黑名单',
            value:0
          },
          {
            label:'允许-白名单',
            value:1
          }
        ],
        appApiList:[],
        apiList:[],
        tableLoading:false,
        currentIP:undefined
      }
    },
    mounted(){
      this.getStrategyList()
    },
    filters:{
      strategyTypeFilter:function (val) {
        const strategtType = {
          0:'拒绝-黑名单',
          1:'允许-白名单',
        }
        return strategtType[val]
      }
    },
    methods:{
      handleSizeChange(val){
        this.pageSize = val
      },
      handleCurrentChange(val){
        this.currentPage = val
      },
      //打开新增弹窗
      handleAddOpen(){
        this.strategyText = '新增'
        this.strategyAddForm = {
          policyName:'',
          policyType:0,
          ipAddress:''
        }
        this.strategyAddVisible = true
      },
      //打开编辑弹窗
      handleEditOpen(params){
        fetchStrategyInfo(params.policyId).then(res=>{
          this.strategyAddForm = res.data.data
          this.strategyText = '编辑'
          this.strategyAddVisible = true
        })
      },
      //打开绑定api弹窗
      handleApiBindOpen(params){
        this.axios.all([this.getApiList(),this.getBindApiList(params.policyId)]).then(res=>{
          this.appApiList = res[0].data.data.map(item=>{
            return {
              key:item.apiId,
              label:item.apiName,
              path:item.path
            }
          })
          this.apiList = res[1].data.data.map(item=>{
            return item.apiId
          })
          this.currentIP = params
          this.strategyApiBindVisible = true
        })
      },
      //获取策略列表
      getStrategyList(){
        this.tableLoading = true
        fetchStrategyList({
          page:this.currentPage,
          limit:this.pageSize,
          policyName:this.strategyName
        }).then(res=>{
          this.tableLoading = false
          this.tableData = res.data.data.records
          this.total = res.data.data.total
          this.currentPage = res.data.data.page
        }).catch(()=>{
          this.tableLoading = false
        })
      },
      //重置查询
      resetGetStrategyList(){
        this.strategyName = ''
        this.getStrategyList()
      },
      //策略新增
      handleStrategyAdd(){
        this.$refs['strategyAddForm'].validate(valid => {
          if (valid) {
            doStrategyAdd(Qs.stringify(this.strategyAddForm)).then(() => {
              this.strategyAddVisible = false
              this.getStrategyList()
              this.$message({
                type: 'success',
                message: '新增成功!'
              });
            }).catch(()=>{
              this.$message({
                type: 'error',
                message: '新增失败，请重试'
              });
            });
          } else {
            return false;
          }
        });
      },
      //策略编辑
      handleStrategyEdit(){
        this.$refs['strategyAddForm'].validate(valid => {
          if (valid) {
            doStrategyEdit(Qs.stringify(this.strategyAddForm)).then(() => {
              this.strategyAddVisible = false
              this.getStrategyList()
              this.$message({
                type: 'success',
                message: '修改成功!'
              });
            }).catch(()=>{
              this.$message({
                type: 'error',
                message: '修改失败，请重试'
              });
            });
          } else {
            return false;
          }
        });
      },
      //获取所以api接口
      getApiList(){
        return fetchApiAllList({
          serviceId:'',
        })
      },
      //获取策略已绑定的接口
      getBindApiList(params){
        return fetchStrategyApiList({
          policyId:params,
        })
      },
      //ip策略绑定接口
      handleStrategyApiAdd(){
        doStrategyApiAdd(Qs.stringify({
          policyId:this.currentIP.policyId,
          apiIds:this.apiList.join(',')
        })).then(() => {
          this.strategyApiBindVisible = false
          this.getStrategyList()
          this.$message({
            type: 'success',
            message: '绑定成功!'
          });
        }).catch(()=>{
          this.$message({
            type: 'error',
            message: '绑定失败，请重试'
          });
        });
      },
      //删除ip策略
      handleStrategyDelete(params){
        this.$confirm('此操作将永久删除该策略, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          doStrategyDel(Qs.stringify({policyId:params.policyId})).then(res=>{
            if(res.data.message==='success'){
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getStrategyList()
            }else{
              this.$message({
                type: 'error',
                message: '删除失败，请重试'
              });
            }
          })
        })
      }
    }
  }
</script>
<style lang="scss" scoped>
  .api_ip_control{
    padding: 10px 20px;
    .ip_search{
      /*height: 40px;*/
      display: flex;
      justify-content: flex-start;
      align-items: center;
      .search_item{
        .el-input{
          width: 200px;
        }
      }
      .search_item~.search_item{
        margin-left: 10px;
      }
      .el-button--primary{
        margin-left: 100px;
      }
    }
    .ip_list{
      height: calc(100% - 40px);
      margin-top: 10px;
      .detail_lists{
        height: calc(100% - 40px);
        margin-bottom: 8px;
        .el-dropdown{
          .el-dropdown-link{
            cursor: pointer;
            color: #409EFF;
            font-size: 12px;
          }
        }
      }
      .detail_pagination{
        height: 32px;
      }
    }
    .strategy_add_dialog {
      display: flex;
      justify-content: center;
      align-items: center;
      /deep/ .el-dialog {
        width: 600px;
        height: 300px;
        margin: 0 !important;
        .drawer_title {
          line-height: 30px;
          .titleSpan {
            padding: 0;
            font-size: 14px;
            font-weight: bold;
            text-align: center;
            color: #3a8ee6;
          }
          .titleSpan:before {
            content: '';
            height: 16px;
            width: 5px;
            background-color: #3a8ee6;
            display: block;
            float: left;
            text-align: center;
            margin: 7px 5px 7px 0;
          }
        }
        .el-dialog__body {
          height: calc(100% - 113px);
          padding: 1px 60px;
          .el-form-item {
            margin-bottom: 15px;
          }
          .el-textarea__inner {
            font-size: 12px;
          }
          .el-form-item__error {
            padding-top: 2px;
          }
          .el-select .el-input .el-select__caret {
            height: 30px;
          }
          .add_userFlag{
            margin-bottom: 0 !important;
            .el-form-item__label{
              line-height: 60px;
            }
            .avatar-uploader{
              .el-upload {
                border: 1px dashed #d9d9d9;
                border-radius: 6px;
                cursor: pointer;
                position: relative;
                overflow: hidden;
                &:hover{
                  border-color: #409EFF;
                }
                .avatar-uploader-icon{
                  font-size: 28px;
                  color: #8c939d;
                  width: 60px;
                  height: 60px;
                  line-height: 60px;
                  text-align: center;
                }
                .avatar{
                  /*width: 178px;*/
                  height: 60px;
                  display: block;
                }
              }
            }
          }
        }
        .el-dialog__footer {
          padding: 10px;
        }
      }
    }
    .api_bind_dialog {
      display: flex;
      justify-content: center;
      align-items: center;
      /deep/ .el-dialog {
        width: 800px;
        height: 600px;
        margin: 0 !important;
        .drawer_title {
          line-height: 30px;
          .titleSpan {
            padding: 0;
            font-size: 14px;
            font-weight: bold;
            text-align: center;
            color: #3a8ee6;
          }
          .titleSpan:before {
            content: '';
            height: 16px;
            width: 5px;
            background-color: #3a8ee6;
            display: block;
            float: left;
            text-align: center;
            margin: 7px 5px 7px 0;
          }
        }
        .el-dialog__body {
          height: calc(100% - 113px);
          padding: 1px;
          .dialog_main{
            height: 100%;
            .alert_message{
              height: 40px;
              margin: 0 10px;
              border: 1px solid #abdcff;
              border-radius: 5px;
              padding: 0 10px;
              background-color: #f0faff;
              display: flex;
              justify-content: flex-start;
              align-items: center;
              font-size: 12px;
              .app_id_input{
                width: 150px;
              }
              .app_secret_input{
                width: 250px;
                margin-right: 10px;
              }
              .alert_title {
                margin-left: 10px;
              }
              i{
                color: #00a2d4;
              }
            }
            .edit_form{
              height: calc(100% - 50px);
              padding: 0 20px;
              margin-top: 10px;
              font-size: 12px;
              .app_func_api{
                height: 100%;
                margin-top: 10px;
                display: flex;
                justify-content: center;
                align-items: flex-start;
                .api_title{
                  width: 100px;
                }
                .api_list{
                  width: calc(100% - 100px);
                  height: 100%;
                  .list_message{
                    border: 1px solid #abdcff;
                    border-radius: 5px;
                    padding: 0 10px;
                    background-color: #f0faff;
                    .blue_message{
                      color: #00a2d4;
                    }
                    i{
                      color: #00a650;
                    }
                  }
                  .list_transfer{
                    height: 100%;
                    .el-transfer{
                      height: 100%;
                      width: 100%;
                      .el-transfer-panel{
                        width: calc(calc(100% - 140px) / 2);
                        height: 100%;
                        .el-transfer-panel__body{
                          height: calc(100% - 40px);
                          .el-checkbox-group{
                            height: calc(100% - 62px);
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        .el-dialog__footer {
          padding: 10px;
        }
      }
    }
  }
</style>
