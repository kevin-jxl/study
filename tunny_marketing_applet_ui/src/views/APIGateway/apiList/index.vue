<template>
  <div class="gateway_api_list">
    <div class="api_search">
      <div class="search_item">
        <span>请求路径:</span>
        <el-input
          placeholder="请输入关键字"
          v-model="apiPath"
          size="mini"
          clearable>
        </el-input>
      </div>
      <div class="search_item">
        <span>接口名称:</span>
        <el-input
          placeholder="请输入关键字"
          v-model="apiName"
          size="mini"
          clearable>
        </el-input>
      </div>
      <div class="search_item">
        <span>接口编码:</span>
        <el-input
          placeholder="请输入关键字"
          v-model="apiCode"
          size="mini"
          clearable>
        </el-input>
      </div>
      <div class="search_item">
        <span>服务名:</span>
        <el-input
          placeholder="请输入关键字"
          v-model="serviceName"
          size="mini"
          clearable>
        </el-input>
      </div>
      <el-button type="primary" size="mini" @click="getApiList">查询</el-button>
      <el-button size="mini" @click="handleApiReset">重置</el-button>
      <el-button size="mini" @click="handleAddOpen">新增</el-button>
    </div>
    <div class="api_message">
      <i class="el-icon-info"></i>
      <span>自动扫描</span>
      <span class="alert_title">@EnableResourceServer</span>
      <span>资源服务器接口,注:自动添加的接口,都是未公开的. 只有公开的接口,才可以通过网关访问。否则将提示:"拒绝访问!"</span>
    </div>
    <div class="api_list">
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
            label="名称"
            min-width="10"
            align="center"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.apiName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="path"
            label="地址"
            align="center"
            min-width="10"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.path}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="apiCategory"
            align="center"
            min-width="10"
            label="分类"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.apiCategory}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="serviceId"
            align="center"
            min-width="10"
            label="服务名称"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.serviceId}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="appType"
            align="center"
            min-width="20"
            label="接口安全"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <el-tag size="mini"
                      :class="{tag_open_able:scope.row.isOpen===1,tag_open_enable:scope.row.isOpen===0}"
              >{{ scope.row.isOpen | openFilter}}</el-tag>
              <el-tag size="mini"
                      :class="{tag_auth_able:scope.row.isAuth===1,
                      tag_auth_enable:scope.row.isAuth===0}"
              >{{ scope.row.isAuth | authFilter}}</el-tag>
              <el-tag size="mini"
                      :class="{tag_status_able:scope.row.status===1,
                      tag_status_enable:scope.row.status===0,
                      tag_status_defend:scope.row.status===2}"
              >{{scope.row.status | statusFilter}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="updateTime"
            align="center"
            min-width="15"
            label="最后更新时间"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.updateTime | moment('YYYY-MM-DD HH:mm:ss')}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="stages"
            align="center"
            min-width="10"
            label="接口描述"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.apiDesc}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="room"
            label="操作"
            align="center"
            min-width="10"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <el-button type="text" size="mini" @click="handleEditOpen(scope.row)">编辑</el-button>
              <el-button type="text" size="mini" @click="handleApiDelete(scope.row)">删除</el-button>
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
      v-if="apiAddVisible"
      :visible.sync="apiAddVisible"
      class="api_add_dialog"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">新增</span>
      </div>
      <div class="dialog_main">
        <el-form ref="apiAddForm"
                 :model="apiAddForm"
                 :rules="apiAddRules"
                 label-width="100px"
                 hide-required-asterisk="true"
                 label-position="right"
        >
          <el-form-item label="服务名称" size="small" prop="serviceId">
            <el-input v-model="apiAddForm.serviceId"></el-input>
          </el-form-item>
          <el-form-item label="接口分类" size="small" prop="apiCategory">
            <el-input v-model="apiAddForm.apiCategory"></el-input>
          </el-form-item>
          <el-form-item label="接口标识" size="small" prop="apiCode">
            <el-input v-model="apiAddForm.apiCode"></el-input>
          </el-form-item>
          <el-form-item label="接口名称" size="small" prop="apiName">
            <el-input v-model="apiAddForm.apiName"></el-input>
          </el-form-item>
          <el-form-item label="请求地址" size="small" prop="path">
            <el-input v-model="apiAddForm.path"></el-input>
          </el-form-item>
          <el-form-item label="优先级" size="small" prop="priority" class="priority_input">
            <el-input-number v-model="apiAddForm.priority"
                             controls-position="right"
                             :min="0" :max="10"></el-input-number>
          </el-form-item>
          <el-form-item label="身份认证" size="small" prop="isAuth">
            <el-radio-group v-model="apiAddForm.isAuth">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否公开访问" size="small" prop="isOpen">
            <el-radio-group v-model="apiAddForm.isOpen">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="状态" size="small" prop="status">
            <el-radio-group v-model="apiAddForm.status">
              <el-radio :label="0">禁用</el-radio>
              <el-radio :label="1">启用</el-radio>
              <el-radio :label="2">维护中</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="描述">
            <el-input type="textarea" v-model="apiAddForm.apiDesc" resize="none"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="apiAddVisible = false" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="handleApiAdd">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="提示"
      v-if="apiEditVisible"
      :visible.sync="apiEditVisible"
      class="api_edit_dialog"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">编辑</span>
      </div>
      <div class="dialog_main">
        <div class="alert_message">
          <i class="el-icon-info"></i>
          <span class="alert_title">接口信息部分内容,需要在接口定义时修改。</span>
          <el-popover
            placement="bottom-start"
            width="400"
            trigger="click">
            <div class="pop_code">
              <div class="code_title">示例代码</div>
              <div class="code_detail">
                <div>// 接口介绍</div>
                <div class="code_annotation">@ApiOperation(value = "接口名称", notes = "接口备注")</div>
                <div class="code_annotation">@PostMapping("/testApi")</div>
                <div>// 忽略接口,将不再添加或修改次接口</div>
                <div class="code_annotation">@ApiIgnore</div>
                <div>// 方法名为接口标识</div>
                <div>public ResultBody testApi() {</div>
                <div>&nbsp;&nbsp;&nbsp;&nbsp;return ResultBody.success();</div>
                <div>}</div>
              </div>
            </div>
            <el-button slot="reference" type="text" size="small">示例代码</el-button>
          </el-popover>
        </div>
        <div class="edit_form">
          <el-form ref="apiEditForm"
                   :model="apiEditForm"
                   :rules="apiEditRules"
                   label-width="100px"
                   hide-required-asterisk="true"
                   label-position="right"
          >
            <el-form-item label="服务名称" size="small" prop="serviceId">
              <el-input v-model="apiEditForm.serviceId" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="接口分类" size="small" prop="apiCategory">
              <el-input v-model="apiEditForm.apiCategory"></el-input>
            </el-form-item>
            <el-form-item label="接口标识" size="small" prop="apiCode">
              <el-input v-model="apiEditForm.apiCode" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="接口名称" size="small" prop="apiName">
              <el-input v-model="apiEditForm.apiName" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="请求地址" size="small" prop="path">
              <el-input v-model="apiEditForm.path" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="优先级" size="small" prop="priority" class="priority_input">
              <el-input-number v-model="apiEditForm.priority"
                               controls-position="right"
                               :min="0" :max="10"></el-input-number>
            </el-form-item>
            <el-form-item label="身份认证" size="small" prop="isAuth">
              <el-radio-group v-model="apiEditForm.isAuth">
                <el-radio disabled :label="1">是</el-radio>
                <el-radio disabled :label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="是否公开访问" size="small" prop="isOpen">
              <el-radio-group v-model="apiEditForm.isOpen">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="状态" size="small" prop="status">
              <el-radio-group v-model="apiEditForm.status">
                <el-radio :label="0">禁用</el-radio>
                <el-radio :label="1">启用</el-radio>
                <el-radio :label="2">维护中</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="描述">
              <el-input type="textarea" v-model="apiEditForm.apiDesc" resize="none"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="apiEditVisible = false" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="handleApiEdit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import Qs from 'qs'
  import {fetchApiList,doApiAdd,fetchApiInfo,doApiDel,doApiEdit} from '@/api/gateway/apiList'
  export default {
    name:'api-list',
    data(){
      return {
        apiPath:'',
        apiName:'',
        apiCode:'',
        serviceName:'',
        tableData:[],
        tableLoading:false,
        currentPage:1,
        pageSize:10,
        total:0,
        apiAddVisible:false,
        apiEditVisible:false,
        apiAddForm:{
          serviceId:'',
          apiCategory:'',
          apiCode:'',
          apiName:'',
          path:'',
          priority:1,
          isAuth:0,
          isOpen:0,
          status:0,
          apiDesc:'',
        },
        apiEditForm:{
          serviceId:'',
          apiCategory:'',
          apiCode:'',
          apiName:'',
          path:'',
          priority:1,
          isAuth:0,
          isOpen:0,
          status:0,
          apiDesc:'',
        },
        apiAddRules:{
          serviceId: [
            {
              required: true,
              message: "请输入服务名称",
              trigger: "blur"
            }
          ],
          apiCategory: [
            {
              required: true,
              message: "请输入接口分类",
              trigger: "blur"
            }
          ],
          methodName: [
            {
              required: true,
              message: "请输入接口标识",
              trigger: "blur"
            }
          ],
          apiName: [
            {
              required: true,
              message: "请输入接口名称",
              trigger: "blur"
            }
          ],
          path: [
            {
              required: true,
              message: "请输入请求地址",
              trigger: "blur"
            }
          ],
        },
        apiEditRules:{
          serviceId: [
            {
              required: true,
              message: "请输入服务名称",
              trigger: "blur"
            }
          ],
          apiCategory: [
            {
              required: true,
              message: "请输入接口分类",
              trigger: "blur"
            }
          ],
          methodName: [
            {
              required: true,
              message: "请输入接口标识",
              trigger: "blur"
            }
          ],
          apiName: [
            {
              required: true,
              message: "请输入接口名称",
              trigger: "blur"
            }
          ],
          path: [
            {
              required: true,
              message: "请输入请求地址",
              trigger: "blur"
            }
          ],
        },
      }
    },
    mounted(){
      this.getApiList()
    },
    filters:{
      openFilter:function (val) {
        const status = {
          "0":"内部的",
          "1":"公开的",
        }
        return status[val]
      },
      authFilter:function (val) {
        const status = {
          "0":"无需认证",
          "1":"身份认证",
        }
        return status[val]
      },
      statusFilter:function (val) {
        const status = {
          "0":"禁用",
          "1":"启用",
          "2":"维护中",
        }
        return status[val]
      },
    },
    methods:{
      handleSizeChange(val){
        this.pageSize = val
        this.getApiList()
      },
      handleCurrentChange(val){
        this.currentPage = val
        this.getApiList()
      },
      handleAddOpen(){
        this.apiAddForm = {
          serviceId:'',
          apiCategory:'default',
          apiCode:'',
          apiName:'',
          path:'',
          priority:1,
          isAuth:0,
          isOpen:0,
          status:0,
          apiDesc:'',
        }
        this.apiAddVisible = true
      },
      handleEditOpen(params){
        fetchApiInfo(params.apiId).then(res=>{
          this.apiEditForm = res.data.data
          this.apiEditVisible = true
        })
      },
      getApiList(){
        this.tableLoading = true
        fetchApiList({
          page:this.currentPage,
          limit:this.pageSize,
          path:this.apiPath,
          apiName:this.apiName,
          apiCode:this.apiCode,
          serviceId:this.serviceName
        }).then(res=>{
          this.tableLoading = false
          this.tableData = res.data.data.records
          this.total = res.data.data.total
          this.currentPage = res.data.data.page
        }).catch(()=>{
          this.tableLoading = false
        })
      },
      handleApiAdd(){
        this.$refs['apiAddForm'].validate(valid => {
          if (valid) {
            doApiAdd(Qs.stringify(this.apiAddForm)).then(() => {
              this.apiAddVisible = false
              this.getApiList()
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
      handleApiReset(){
        this.apiPath = ''
        this.apiName = ''
        this.apiCode = ''
        this.serviceName = ''
        this.getApiList()
      },
      handleApiEdit(){
        this.$refs['apiEditForm'].validate(valid => {
          if (valid) {
            doApiEdit(Qs.stringify(this.apiEditForm)).then(() => {
              this.apiEditVisible = false
              this.getApiList()
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
      handleApiDelete(params){
        this.$confirm('此操作将永久删除该api接口, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          doApiDel(Qs.stringify({apiId:params.apiId})).then(res=>{
            if(res.data.message==='success'){
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getApiList()
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
  .gateway_api_list{
    padding: 10px 20px;
    .api_search{
      /*height: 40px;*/
      display: flex;
      justify-content: flex-start;
      align-items: center;
      .search_item{
        white-space: nowrap;
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
    .api_message{
      height: 40px;
      margin-top: 10px;
      border: 1px solid #abdcff;
      border-radius: 5px;
      padding: 0 10px;
      background-color: #f0faff;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      font-size: 12px;
      .alert_title{
        border: 1px solid #abdcff;
        border-radius: 5px;
        padding: 0 10px;
        background-color: #fff1f0;
      }
      i{
        color: #00a2d4;
        margin-right: 10px;
      }
    }
    .api_list{
      height: calc(100% - 90px);
      margin-top: 10px;
      .detail_lists{
        height: calc(100% - 40px);
        margin-bottom: 8px;
        .tag_open_able{
          color:#00a2d4;
          border-color: #00a2d4;
        }
        .tag_open_enable{
          color:#00a650;
          border-color: #00a650;
        }
        .tag_auth_able{
          color:#00a2d4;
          border-color: #00a2d4;
        }
        .tag_auth_enable{
          color:#00a650;
          border-color: #00a650;
        }
        .tag_status_able{
          color:#00a2d4;
          border-color: #00a2d4;
        }
        .tag_status_enable{
          color:#00a650;
          border-color: #00a650;
        }
        .tag_status_defend{
          color:#0a6332;
          border-color: #0a6332;
        }
      }
      .detail_pagination{
        height: 32px;
      }
    }
    .api_add_dialog {
      display: flex;
      justify-content: center;
      align-items: center;
      /deep/ .el-dialog {
        width: 600px;
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
          .priority_input{
            width: 50%;
          }
        }
        .el-dialog__footer {
          padding: 10px;
        }
      }
    }
    .api_edit_dialog {
      display: flex;
      justify-content: center;
      align-items: center;
      /deep/ .el-dialog {
        width: 600px;
        height: 650px;
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
            padding: 0 60px;
            margin-top: 10px;
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
            .priority_input{
              width: 50%;
            }
          }
        }
        .el-dialog__footer {
          padding: 10px;
        }
      }
    }

    @media screen and (max-width: 1600px){
      .api_search{
        height: 80px;
        flex-wrap: wrap;
        .search_item{
          width: calc(calc(100% - 30px) / 4);
          .el-input{
            width: auto;
          }
        }
        .el-button--primary{
          margin-left: 0;
        }
      }
      .api_list{
        height: calc(100% - 140px);
      }
    }
  }
  .pop_code{
    padding: 2px 10px;
    .code_title{
      border-bottom: 1px solid #e8eaec;
    }
    .code_detail{
      margin-top: 10px;
      border: 1px solid #e8eaec;
      padding-left: 30px;
      .code_annotation{
        color: #666600;
        font-style: italic;
      }
    }
  }
</style>
