<template>
  <div class="api_gateway_application">
    <div class="application_search">
      <div class="search_item">
        <span>appId:</span>
        <el-input
          placeholder="请输入关键字"
          v-model="appId"
          size="mini"
          clearable>
        </el-input>
      </div>
      <div class="search_item">
        <span>中文名称:</span>
        <el-input
          placeholder="请输入关键字"
          v-model="name"
          size="mini"
          clearable>
        </el-input>
      </div>
      <div class="search_item">
        <span>英文名称:</span>
        <el-input
          placeholder="请输入关键字"
          v-model="elName"
          size="mini"
          clearable>
        </el-input>
      </div>
      <el-button type="primary" size="mini" @click="getApplicationList">查询</el-button>
      <el-button size="mini" @click="getApplicationListReset">重置</el-button>
      <el-button size="mini" @click="handleAddOpen">新增</el-button>
    </div>
    <div class="application_list">
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
            label="appId"
            min-width="10"
            align="center"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.appId}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="project"
            label="应用名称"
            align="center"
            min-width="10"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.appName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="stages"
            align="center"
            min-width="15"
            label="英文名称"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.appNameEn}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="stages"
            align="center"
            min-width="15"
            label="开发者"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.developer}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="appType"
            align="center"
            min-width="15"
            label="应用类型"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.appType | appTypeFilter}}</span>
            </template>
          </el-table-column>
<!--          <el-table-column-->
<!--            prop="stages"-->
<!--            align="center"-->
<!--            min-width="15"-->
<!--            label="开发者类型"-->
<!--            show-overflow-tooltip>-->
<!--            <template slot-scope="scope">-->
<!--              <span>{{ scope.row.userType | userTypeFilter}}</span>-->
<!--            </template>-->
<!--          </el-table-column>-->
          <el-table-column
            prop="stages"
            align="center"
            min-width="15"
            label="状态"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.status | statusFilter}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="stages"
            align="center"
            min-width="15"
            label="描述"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.appDesc}}</span>
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
              <el-dropdown size="mini" @command="(command)=>handleCommand(command,scope.row)">
                <span class="el-dropdown-link">
                  更多<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="0">开发配置</el-dropdown-item>
                  <el-dropdown-item command="1">接口授权</el-dropdown-item>
                  <el-dropdown-item command="2">重置秘钥</el-dropdown-item>
                  <el-dropdown-item command="3">删除应用</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
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
      v-if="appAddVisible"
      :visible.sync="appAddVisible"
      class="application_add_dialog"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">新增</span>
      </div>
      <div class="dialog_main">
        <el-form ref="appAddForm"
                 :model="appAddForm"
                 :rules="appAddRules"
                 label-width="80px"
                 hide-required-asterisk="true"
                 label-position="right"
        >
          <el-form-item label="应用图标" prop="userFlag" class="add_userFlag"
                        size="small">
            <el-upload
              class="avatar-uploader"
              action="/api/app/uploadProfilePhoto"
              :show-file-list="false"
              :on-success="handleFileSuccess"
              :on-error="handleFileError"
              :before-upload="beforeFileUpload"
            >
              <img v-if="filePath" :src="filePath" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="开发者" size="small" prop="developer">
            <el-input v-model="appAddForm.developer"></el-input>
          </el-form-item>
          <el-form-item label="应用名称" size="small" prop="appName">
            <el-input v-model="appAddForm.appName" @input="changeAddName"></el-input>
          </el-form-item>
          <el-form-item label="英文名称" size="small" prop="appNameEn">
            <el-input v-model="appAddForm.appNameEn" @input="changeAddNameEn"></el-input>
          </el-form-item>
          <el-form-item label="应用类型" size="small" prop="appType">
            <el-select v-model="appAddForm.appType" placeholder="请选择应用类型" size="small">
              <el-option v-for="item in appTypeOptions"
                         :label="item.label"
                         :value="item.value"
                         :key="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="应用官网" size="small" prop="website">
            <el-input v-model="appAddForm.website"></el-input>
          </el-form-item>
          <el-form-item label="状态" size="small" prop="status">
            <el-radio-group v-model="appAddForm.status">
              <el-radio :label="1">上线</el-radio>
              <el-radio :label="0">下线</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="描述">
            <el-input type="textarea" v-model="appAddForm.appDesc" resize="none"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="appAddVisible = false" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="handleApplicationAdd">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="提示"
      v-if="appEditVisible"
      :visible.sync="appEditVisible"
      class="application_edit_dialog"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">编辑</span>
      </div>
      <div class="dialog_main">
        <div class="alert_message">
          <i class="el-icon-info"></i>
          <span class="alert_title">重要信息,请妥善保管：AppId：</span>
          <el-input
            v-model="appEditForm.appId"
            size="small"
            class="app_id_input"
            :disabled="true">
          </el-input>
          <span class="alert_title">AppSecret：</span>
          <el-input
            v-model="appEditForm.appSecret"
            size="small"
            class="app_secret_input"
            :disabled="true">
          </el-input>
          <el-button type="text" size="small" @click="handleApplicationSecretReset(appEditForm)">重置秘钥</el-button>
        </div>
        <div class="edit_form">
          <el-form ref="appEditForm"
                   :model="appEditForm"
                   :rules="appEditRules"
                   label-width="80px"
                   hide-required-asterisk="true"
                   label-position="right"
          >
            <el-form-item label="应用图标" prop="userFlag" class="add_userFlag"
                          size="small">
              <el-upload
                class="avatar-uploader"
                action="/api/app/uploadProfilePhoto"
                :show-file-list="false"
                :on-success="handleFileSuccess"
                :on-error="handleFileError"
                :before-upload="beforeFileUpload"
              >
                <img v-if="filePath" :src="filePath" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="开发者" size="small" prop="developer">
              <el-input v-model="appEditForm.developer"></el-input>
            </el-form-item>
            <el-form-item label="应用名称" size="small" prop="appName">
              <el-input v-model="appEditForm.appName" @input="changeEditName"></el-input>
            </el-form-item>
            <el-form-item label="英文名称" size="small" prop="appNameEn">
              <el-input v-model="appEditForm.appNameEn" @input="changeEditNameEn"></el-input>
            </el-form-item>
            <el-form-item label="应用类型" size="small" prop="appType">
              <el-select v-model="appEditForm.appType" placeholder="请选择应用类型" size="small">
                <el-option v-for="item in appTypeOptions"
                           :label="item.label"
                           :value="item.value"
                           :key="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="应用官网" size="small" prop="website">
              <el-tooltip></el-tooltip>
              <el-input v-model="appEditForm.website"></el-input>
            </el-form-item>
            <el-form-item label="状态" size="small" prop="status">
              <el-radio-group v-model="appEditForm.status">
                <el-radio :label="1">上线</el-radio>
                <el-radio :label="0">下线</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="描述">
              <el-input type="textarea" v-model="appEditForm.appDesc" resize="none"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="appEditVisible = false" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="handleApplicationEdit">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="提示"
      v-if="developConfigVisible"
      :visible.sync="developConfigVisible"
      class="develop_config_dialog"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">应用开发配置</span>
      </div>
      <div class="dialog_main">
        <div class="alert_message">
          <i class="el-icon-info"></i>
          <span class="alert_title">重要信息,请妥善保管：AppId：</span>
          <el-input
            v-model="developConfigForm.appId"
            size="small"
            class="app_id_input"
            :disabled="true">
          </el-input>
          <span class="alert_title">AppSecret：</span>
          <el-input
            v-model="developConfigForm.appSecret"
            size="small"
            class="app_secret_input"
            :disabled="true">
          </el-input>
          <el-button type="text" size="small">重置秘钥</el-button>
        </div>
        <div class="edit_form">
          <el-form ref="developConfigForm"
                   :model="developConfigForm"
                   :rules="developConfigRules"
                   label-width="140px"
                   hide-required-asterisk="true"
                   label-position="right"
          >
            <el-form-item label="授权类型" prop="authorized_grant_types" size="small">
              <el-checkbox-group v-model="developConfigForm.authorized_grant_types">
                <el-checkbox label="authorization_code">授权码模式</el-checkbox>
                <el-checkbox label="client_credentials">客户端模式</el-checkbox>
                <el-checkbox label="password">密码模式</el-checkbox>
                <el-checkbox label="implicit">简化模式</el-checkbox>
                <el-checkbox label="refresh_token">刷新令牌</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="用户授权范围" size="small" prop="scope">
              <el-checkbox-group v-model="developConfigForm.scope">
                <el-checkbox label="userProfile">允许访问基本信息</el-checkbox>
                <el-checkbox label="api1">允许访问自定义信息</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="自动授权范围" size="small" prop="autoapprove">
              <el-checkbox-group v-model="developConfigForm.autoapprove">
                <el-checkbox label="userProfile">允许访问基本信息</el-checkbox>
                <el-checkbox label="api1">允许访问自定义信息</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="令牌有效期" size="small" prop="tokenValidity">
              <el-radio-group v-model="developConfigForm.tokenValidity">
                <el-radio :label="0">设置有效期</el-radio>
                <el-radio :label="1">不限制</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="访问令牌有效期"
                          v-if="developConfigForm.tokenValidity===0"
                          size="small" prop="access_token_validity" class="develop_time">
              <el-input v-model="developConfigForm.access_token_validity" @input="changeAccessValueNumber">
                <span slot="suffix">秒</span>
              </el-input>
            </el-form-item>
            <el-form-item label="刷新令牌有效期"
                          v-if="developConfigForm.tokenValidity===0"
                          size="small" prop="refresh_token_validity" class="develop_time">
              <el-input v-model="developConfigForm.refresh_token_validity" @input="changeRefreshValueNumber">
                <span slot="suffix">秒</span>
              </el-input>
            </el-form-item>
            <el-form-item label="第三方登陆回调地址" prop="redirect_uri">
              <el-input type="textarea" v-model="developConfigForm.redirect_uri" resize="none"></el-input>
              <div class="area_message">多个地址使用逗号“,”分割</div>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="developConfigVisible = false" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="handleApplicationDevelopEdit">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="提示"
      v-if="appFuncVisible"
      :visible.sync="appFuncVisible"
      class="app_func_dialog"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">应用功能授权</span>
      </div>
      <div class="dialog_main">
        <div class="alert_message">
          <i class="el-icon-info"></i>
          <span class="alert_title">重要信息,请妥善保管：AppId：</span>
          <el-input
            v-model="appFuncForm.alertAppId"
            size="mini"
            class="app_id_input"
            :disabled="true">
          </el-input>
          <span class="alert_title">AppSecret：</span>
          <el-input
            v-model="appFuncForm.alertAppSecret"
            size="mini"
            class="app_secret_input"
            :disabled="true">
          </el-input>
          <el-button type="text" size="small">重置秘钥</el-button>
        </div>
        <div class="edit_form">
          <div class="app_func_validity">
            <div class="validity_title">过期时间(选填)</div>
            <el-date-picker
              v-model="appFuncForm.expireTime"
              type="date"
              size="mini"
              value-format="yyyy-MM-DD"
              placeholder="选择日期">
            </el-date-picker>
          </div>
          <div class="app_func_api">
            <div class="api_title">接口权限(选填)</div>
            <div class="api_list">
              <div class="list_message">
                <i class="el-icon-info"></i>
                <span>请注意：</span>
                <span class="blue_message">支持动态授权,无需重新登录或刷新</span>
              </div>
              <div class="list_transfer">
                <el-transfer
                  style="text-align: left; display: inline-block"
                  v-model="appFuncForm.apiList"
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
        <el-button @click="appFuncVisible = false" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="handleApplicationFuncEdit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import Qs from 'qs'
  import {fetchApplicationList,doApplicationAdd,fetchApplicationInfo,doApplicationReset,
    doApplicationEdit,doApplicationDel,fetchApplicationDevelopInfo,
    doApplicationDevelopEdit,fetchApplicationFuncList,doApplicationFuncEdit} from '@/api/gateway/applicationList'
  import {fetchApiAllList} from '@/api/gateway/apiList'
  export default {
    name:'application-info',
    data(){
      return {
        appId:'',
        name:'',
        elName:'',
        tableData:[],
        currentPage:1,
        pageSize:10,
        total:0,
        appTypeOptions:[
          {
            label:'服务器应用',
            value:'server'
          },
          {
            label:'手机应用',
            value:'app'
          },
          {
            label:'PC网页应用',
            value:'pc'
          },
          {
            label:'手机网页应用',
            value:'wap'
          },
        ],
        appAddVisible:false,
        appEditVisible:false,
        developConfigVisible:false,
        appFuncVisible:false,
        tableLoading:false,
        appAddForm:{
          appIcon:'',
          developer:'',
          appName:'',
          appNameEn:'',
          appType:'server',
          website:'',
          status:1,
          appDesc:'',
        },
        appEditForm:{
          appId:'',
          appSecret:'',
          appIcon:'',
          developer:'',
          appName:'',
          appNameEn:'',
          appType:0,
          website:'',
          status:1,
          appDesc:'',
        },
        developConfigForm:{
          appId:'',
          appSecret:'',
          authorized_grant_types:[],
          scope:[],
          autoapprove:[],
          tokenValidity:0,
          access_token_validity:'',
          refresh_token_validity:'',
          redirect_uri:'',
        },
        appFuncForm:{
          alertAppId:'1559655826660',
          alertAppSecret:'9cb4202068ae4b3e836e0b84a318719f',
          expireTime:'',
          apiList:[],
        },
        appAddRules:{
          developer: [
            {
              required: true,
              message: "请输入开发者",
              trigger: "blur"
            }
          ],
          appName: [
            {
              required: true,
              message: "请输入应用名称",
              trigger: "blur"
            }
          ],
          appNameEn: [
            {
              required: true,
              message: "请输入英文名称",
              trigger: "blur"
            }
          ],
          appType: [
            {
              required: true,
              message: "请选择应用类型",
              trigger: "blur"
            }
          ],
          website: [
            {
              required: true,
              message: "请输入官网地址",
              trigger: "blur"
            }
          ],
        },
        appEditRules:{
          developer: [
            {
              required: true,
              message: "请输入开发者",
              trigger: "blur"
            }
          ],
          appName: [
            {
              required: true,
              message: "请输入应用名称",
              trigger: "blur"
            }
          ],
          appNameEn: [
            {
              required: true,
              message: "请输入英文名称",
              trigger: "blur"
            }
          ],
          appType: [
            {
              required: true,
              message: "请选择应用类型",
              trigger: "blur"
            }
          ],
          website: [
            {
              required: true,
              message: "请输入官网地址",
              trigger: "blur"
            }
          ],
        },
        developConfigRules:{
          authorized_grant_types: [
            {
              required: true,
              message: "请输入开发者",
              trigger: "blur"
            }
          ],
          scope: [
            {
              required: true,
              message: "请输入开发者",
              trigger: "blur"
            }
          ],
          tokenValidity: [
            {
              required: true,
              message: "请输入开发者",
              trigger: "blur"
            }
          ],
          access_token_validity: [
            {
              required: true,
              message: "请输入访问令牌有效期",
              trigger: "blur"
            }
          ],
          refresh_token_validity: [
            {
              required: true,
              message: "请输入刷新令牌有效期",
              trigger: "blur"
            }
          ],
          redirect_uri: [
            {
              required: true,
              message: "请输入第三方登录回调地址",
              trigger: "blur"
            }
          ],
        },
        filePath:'/static/img/bg/bg2.jpg',
        appApiList:[],
        apiList:[],
        currentApp:undefined
      }
    },
    mounted(){
      this.getApplicationList()
    },
    filters:{
      appTypeFilter:function (val) {
        const status = {
          "server":"服务应用 ",
          "app":"手机应用",
          "pc":"PC网页应用 ",
          "wap":"手机网页应用",
        }
        return status[val]
      },
      userTypeFilter:function (val) {
        const status = {
          "platform":"平台",
          "isp":"服务提供商",
          "dev":"自研开发者",
        }
        return status[val]
      },
      statusFilter:function (val) {
        const status = {
          "0":"下线",
          "1":"上线",
        }
        return status[val]
      },
    },
    methods:{
      handleSizeChange(val){
        this.pageSize = val
        this.getApplicationList()
      },
      handleCurrentChange(val){
        this.currentPage = val
        this.getApplicationList()
      },
      //签名上传成功
      handleFileSuccess(response, file, fileList){
        if(response.code===200){
          this.filePath = response.attUrl
          this.$message({
            message: '上传成功！',
            type: 'success'
          });
        }else{
          this.$message.error('上传失败，请重试！');
        }
      },
      //签名上传失败
      handleFileError(){
        this.$message.error('上传失败，请重试！');
      },
      //签名上传前校验格式、大小
      beforeFileUpload(file) {
        let fileType = ['image/png','image/jpeg','image/jpg','image/bmp']
        const isImg = fileType.includes(file.type);
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isImg) {
          this.$message.error('只能上传图片格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        }
        return isImg && isLt2M;
      },
      handleAddOpen(){
        this.filePath = ''
        this.appAddForm = {
          appIcon:'',
          userName:'',
          appName:'',
          appNameEn:'',
          appType:'server',
          website:'',
          status:1,
          appDesc:'',
        }
        this.appAddVisible = true
      },
      //打开编辑弹窗
      handleEditOpen(payload){
        this.filePath = ''
        fetchApplicationInfo(payload.appId).then(res=>{
          this.appEditForm = res.data.data
          this.filePath = res.data.data.appIcon
          this.appEditVisible = true
        })
      },
      //更多按钮事件
      handleCommand(command,params){
        switch (command) {
          case '0':this.handleDevelopConfigOpen(params);break;
          case '1':this.handleAppFuncOpen(params);break;
          case '2':this.handleRechangeSecret(params);break;
          case '3':this.handleDeleteApp(params);break;
        }
      },
      //打开开发配置弹窗
      handleDevelopConfigOpen(payload){
        fetchApplicationDevelopInfo(payload.appId).then(res=>{
          this.developConfigForm = res.data.data
          this.developConfigForm.tokenValidity = res.data.data.access_token_validity?0:1
          this.developConfigForm.redirect_uri = res.data.data.redirect_uri.join(',')
          this.developConfigForm.appId = res.data.data.additional_information.appId
          this.developConfigForm.appSecret = res.data.data.additional_information.appSecret
          this.developConfigVisible = true
        })
      },
      //打开功能配置弹窗
      handleAppFuncOpen(payload){
        this.axios.all([this.getApiList(),this.getBindApiList(payload.appId)]).then(res=>{
          this.appApiList = res[0].data.data.map(item=>{
            return {
              key:item.apiId,
              label:item.apiName,
              path:item.path
            }
          })
          this.appFuncForm.apiList = res[1].data.data.map(item=>{
            return item.apiId
          })
          this.appFuncForm.expireTime = res[1].data.data.length>0?res[1].data.data[0].expireTime:''
          this.currentApp = payload
          this.appFuncVisible = true
        })
      },
      //获取所以api接口
      getApiList(){
        return fetchApiAllList({
          serviceId:'',
        })
      },
      //获取策略已绑定的接口
      getBindApiList(params){
        return fetchApplicationFuncList({
          appId:params,
        })
      },
      //重置秘钥
      handleRechangeSecret(params){
        this.$confirm('重置后将影响应用正常使用, 确定重置吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.handleApplicationSecretReset(params)
        })
      },
      //删除应用
      handleDeleteApp(params){
        this.$confirm('此操作将永久删除该应用, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.getApplicationList()
          doApplicationDel(Qs.stringify({appId:params.appId})).then(res=>{
            if(res.data.message==='success'){
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getApplicationList()
              this.hideWindowLoading()
            }else{
              this.hideWindowLoading()
              this.$message({
                type: 'error',
                message: '删除失败，请重试'
              });
            }
          }).catch(()=>{
            this.hideWindowLoading()
            this.$message({
              type: 'error',
              message: '删除失败，请重试'
            });
          })
        })
      },
      changeRefreshValueNumber(val){
        this.$nextTick(()=>{
          this.developConfigForm.refreshTokenValidity = val.replace(/\D/g,'')
        })
      },
      changeAccessValueNumber(val){
        this.$nextTick(()=>{
          this.developConfigForm.access_token_validity = val.replace(/\D/g,'')
        })
      },
      changeAddName(val){
        this.$nextTick(()=>{
          this.appAddForm.appName = val.replace(/[^\u4E00-\u9FA5]/g,'')
        })
      },
      changeAddNameEn(val){
        this.$nextTick(()=>{
          this.appAddForm.appNameEn = val.replace(/[^a-zA-Z]/g,'')
        })
      },
      changeEditName(val){
        this.$nextTick(()=>{
          this.appEditForm.appName = val.replace(/[^\u4E00-\u9FA5]/g,'')
        })
      },
      changeEditNameEn(val){
        this.$nextTick(()=>{
          this.appEditForm.appNameEn = val.replace(/[^a-zA-Z]/g,'')
        })
      },
      //获取应用信息列表
      getApplicationList(){
        this.tableLoading = true
        fetchApplicationList({
          page:this.currentPage,
          limit:this.pageSize,
          appId:this.appId,
          appName:this.name,
          appNameEn:this.elName
        }).then(res=>{
          this.tableLoading = false
          this.tableData = res.data.data.records
          this.total = res.data.data.total
          this.currentPage = res.data.data.page
        }).catch(()=>{
          this.tableLoading = false
        })
      },
      //列表查询重置
      getApplicationListReset(){
        this.appId = ''
        this.name = ''
        this.elName = ''
        this.getApplicationList()
      },
      //新增应用
      handleApplicationAdd(){
        this.$refs['appAddForm'].validate(valid => {
          if (valid) {
            this.showWindowLoading()
            doApplicationAdd(Qs.stringify({...this.appAddForm,appIcon:this.filePath})).then(() => {
              this.appAddVisible = false
              this.getApplicationList()
              this.hideWindowLoading()
              this.$message({
                type: 'success',
                message: '新增成功!'
              });
            }).catch(()=>{
              this.hideWindowLoading()
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
      //重置秘钥
      handleApplicationSecretReset(params){
        this.showWindowLoading()
        doApplicationReset(Qs.stringify({appId:params.appId})).then(res=>{
          this.appEditForm.appSecret = res.data.data
          this.hideWindowLoading()
          this.$message({
            type: 'success',
            message: '重置成功!'
          });
        }).catch(()=>{
          this.hideWindowLoading()
          this.$message({
            type: 'error',
            message: '重置失败，请重试'
          });
        });
      },
      //编辑应用信息
      handleApplicationEdit(){
        this.$refs['appEditForm'].validate(valid => {
          if (valid) {
            this.showWindowLoading()
            doApplicationEdit(Qs.stringify({...this.appEditForm,appIcon:this.filePath})).then(() => {
              this.appEditVisible = false
              this.getApplicationList()
              this.hideWindowLoading()
              this.$message({
                type: 'success',
                message: '修改成功!'
              });
            }).catch(()=>{
              this.hideWindowLoading()
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
      handleApplicationDevelopEdit(){
        this.$refs['developConfigForm'].validate(valid => {
          if (valid) {
            this.showWindowLoading()
            doApplicationDevelopEdit(Qs.stringify({
              ...this.developConfigForm,
              authorized_grant_types:this.developConfigForm.authorized_grant_types.join(','),
              scope:this.developConfigForm.scope.join(','),
              autoapprove:this.developConfigForm.autoapprove.join(',')
            })).then(() => {
              this.developConfigVisible = false
              this.getApplicationList()
              this.hideWindowLoading()
              this.$message({
                type: 'success',
                message: '修改成功!'
              });
            }).catch(()=>{
              this.hideWindowLoading()
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
      handleApplicationFuncEdit(){
        doApplicationFuncEdit(Qs.stringify({
          appId:this.currentApp.appId,
          expireTime:this.appFuncForm.expireTime,
          authorityIds:this.appFuncForm.apiList.join(','),
        })).then(() => {
          this.appFuncVisible = false
          this.getApplicationList()
          this.hideWindowLoading()
          this.$message({
            type: 'success',
            message: '修改成功!'
          });
        }).catch(()=>{
          this.hideWindowLoading()
          this.$message({
            type: 'error',
            message: '修改失败，请重试'
          });
        });
      }
    }
  }
</script>
<style lang="scss" scoped>
  .api_gateway_application{
    padding: 10px 20px;
    .application_search{
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
    .application_list{
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
    .application_add_dialog {
      display: flex;
      justify-content: center;
      align-items: center;
      /deep/ .el-dialog {
        width: 600px;
        height: 540px;
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
    .application_edit_dialog {
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
        }
        .el-dialog__footer {
          padding: 10px;
        }
      }
    }
    .develop_config_dialog {
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
              .el-form-item {
                margin-bottom: 15px;
                .el-input__suffix{
                  font-size: 12px;
                }
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
              .develop_time{
                width: 40%;
              }
              .area_message{
                line-height: 20px;
              }
            }
          }
        }
        .el-dialog__footer {
          padding: 10px;
        }
      }
    }
    .app_func_dialog {
      display: flex;
      justify-content: center;
      align-items: center;
      /deep/ .el-dialog {
        width: 800px;
        height: 620px;
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
              .app_func_validity{
                display: flex;
                justify-content: flex-start;
                align-items: center;
                .validity_title{
                  width: 100px;
                }
                .el-date-editor{
                  width: 200px !important;
                }
              }
              .app_func_api{
                height: calc(100% - 40px);
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
                    height: calc(100% - 40px);
                    margin-top: 10px;
                    .el-transfer{
                      height: 100%;
                      width: 100%;
                      .el-transfer-panel{
                        width: calc(calc(100% - 100px) / 2);
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

    @media screen and (max-width: 1600px){
      .application_search{
        .search_item{
          .el-input{
            width: 160px;
          }
        }
      }
    }
  }
</style>
