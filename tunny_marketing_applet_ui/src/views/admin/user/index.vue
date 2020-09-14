<template>
  <div class="app-container calendar-list-container" style="height:calc(100% - 30px)">
    <basic-container style="height:100%">

      <el-row style="height: 100%" :gutter="20">

        <!--部门树-->
        <el-col :span="5"
                style="height:100%; border:1px solid #e5e5e5; border-radius:3px; padding: 5px; "
                class="zTree_custom">

          <z-tree :zNodes="treeDeptData"
                  :checkEnable="false"
                  :simple-check="false"
                  :default-select-one="true"
                  :search-enable="true"
                  search-placeholder="请输入部门名称查找"
                  @tree-click="zTreeOnClicks"
                  @tree-search="zTreeOnSearch"
                  style="height: 99%; border:none;">
          </z-tree>
        </el-col>

        <!--列表-->
        <el-col :span="19" style='margin-top:0;height: 100%'>

          <!--列表查询框-->
          <el-header style="height: auto;padding: 0;">
            <el-row class="in_custom">
              <el-col :span="18">
                <el-input
                  placeholder="请输入员工姓名"
                  size="small"
                  style="width: 258px; height:28px;"
                  class="fl"
                  v-model="listQuery.userSearch"
                  @keyup.enter.native="userSearch"
                  clearable
                >
                  <i slot="suffix" class="el-input__icon el-icon-search" @click.stop="userSearch"></i>
                </el-input>
              </el-col>
              <el-col :span="6">
                <el-button
                  size="mini" @click="handlerAdd" class="fr">新增
                </el-button>
              </el-col>
            </el-row>
          </el-header>

          <!--列表展示-->
          <el-table :data="tableData"
                    tooltip-effect="dark"
                    :header-cell-style="tableHeaderColor"
                    style="width: 100%;font-size: 12px;margin:10px 0 15px 0;border-radius:3px;"
                    :row-style="rowClass"
                    :cell-style="cellClass"
                    height="calc(100% - 80px)"
          >
            <el-table-column align="center" type="index" width="50"
                             label="序号">
            </el-table-column>

            <el-table-column align="center"
                             label="账号"
                             prop="username"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.username:'' }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="姓名"
                             prop="userRealname"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.userRealname:'' }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="性别"
                             prop="sex">
              <template slot-scope="scope">
                <span>{{ scope.row.sex | sexFilter }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="岗位"
                             prop="postNames"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.postNames:'' }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="部门"
                             prop="deptName"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.deptName:'' }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="员工号"
                             prop="empno">
            </el-table-column>

            <el-table-column align="center"
                             label="状态"
                             prop="userFlag">
              <template slot-scope="scope">
                {{scope.row.userFlag | statusFilter}}
              </template>
            </el-table-column>
            <!--操作列-->
            <el-table-column align="center"
                             label="操作"
                             width="200">
              <template slot-scope="scope">
                <el-button v-if="sys_user_edit"
                           size="small"
                           type="text"
                           plain
                           class="button_edit"
                           @click="handleUpdate(scope.row)">编辑
                </el-button>
                <el-button v-if="sys_user_del"
                           size="small"
                           type="text"
                           plain
                           class="button_delete"
                           @click="deletes(scope.row)">删除
                </el-button>
                <el-button size="small"
                           type="text"
                           style="color: #85CE61"
                           @click="xnpostAllocation(scope.row)"
                           v-if="sys_user_del">虚拟岗位
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <!--分页-->
          <el-footer style="height: 30px;padding:2px 0 0 0">
            <!--分页控件-->
            <div v-show="!listLoading"
                 class="pagination-container">
              <el-pagination @size-change="handleSizeChange"
                             @current-change="handleCurrentChange"
                             :current-page.sync="listQuery.page"
                             :page-sizes="[10,20,30,50]"
                             :page-size="listQuery.limit"
                             class="pull-center"
                             background
                             layout=" total, prev, pager, next, jumper, sizes"
                             prev-text=" <<上一页 "
                             next-text=" 下一页>> "
                             :total="total">
              </el-pagination>
            </div>
          </el-footer>
        </el-col>
      </el-row>

      <!--新增用户弹出框-->
      <el-dialog :title="textMap[dialogStatus]"
                 :visible.sync="dialogFormVisible"
                 class="user_add_dialog"
      >
        <!--弹窗框标题头-->
        <div slot="title" class="add_title">
          <span class="titleSpan">{{textMap[dialogStatus]}}</span>
        </div>

        <!--弹窗内容-->
        <el-form :model="form"
                 :rules="editRules"
                 ref="form"
                 label-width="100px"
                 label-position="right">
          <el-row>
            <el-col :span="11">
              <el-form-item label="登录名"
                            prop="username"
                            size="small"
              >
                <el-input v-model="form.username"
                          placeholder="请输登录名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="密码"
                            prop="password"
                            size="small">
                <el-input type="password"
                          v-model="form.password"
                          placeholder="请输入密码"
                          :disabled="dialogStatus=='update'? true:false"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="姓名"
                            prop="userRealname"
                            size="small">
                <el-input v-model="form.userRealname"
                          placeholder="请输姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="性别"
                            size="small">
                <el-radio-group v-model="form.sex">
                  <el-radio label="0">男</el-radio>
                  <el-radio label="1">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="员工号"
                            prop="empno"
                            size="small">
                <el-input v-model="form.empno"
                          placeholder="请输员工号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="所属部门"
                            prop="deptList"
                            size="small">
                <el-cascader
                  v-model="form.deptList"
                  placeholder="试试搜索：华东大区"
                  :options="treeDeptData"
                  filterable
                  change-on-select
                  :show-all-levels="false"
                ></el-cascader>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="邮箱" prop="email"
                            size="small">
                <el-input v-model="form.email"
                          placeholder="请输入邮箱"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="电话" prop="phone"
                            size="small">
                <el-input v-model="form.phone"
                          placeholder="请输入电话"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="角色"
                            prop="roles"
                            size="small">
                <el-select class="filter-item"
                           filterable
                           v-model="form.roles"
                           placeholder="请选择角色"
                           multiple
                           @change="roleChange"
                           size="small">
                  <el-option v-for="item in rolesOptions"
                             :key="item.roleId"
                             :label="item.roleDesc"
                             :value="item.roleId"
                             :disabled="isDisabled[item.delFlag]">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="22">
              <el-form-item label="所属岗位"
                            prop="posts"
                            size="small">
                <el-select class="filter-item"
                           v-model="form.posts"
                           placeholder="请选择岗位"
                           multiple
                           size="small"
                           @change="postChange"
                           filterable>
                  <el-option v-for="item in postsOptions"
                             :key="item.id"
                             :label="item.postName"
                             :value="item.id"
                             :disabled="isDisabled[item.delFlag]">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="用户状态" prop="userFlag"
                            size="small">
                <el-switch
                  v-model="form.userFlag"
                  active-value="1"
                  inactive-value="0">
                </el-switch>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="用户签名" prop="userFlag" class="add_userFlag"
                            size="small">
                <el-upload
                  class="avatar-uploader"
                  action="/admin/user/uploadSignName"
                  :show-file-list="false"
                  :on-success="handleFileSuccess"
                  :on-error="handleFileError"
                  :before-upload="beforeFileUpload"
                >
                  <img v-if="filePath" :src="filePath" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="cancel('form')" size="small">取 消</el-button>
          <el-button v-if="dialogStatus=='create'"
                     type="primary"
                     @click="create('form')" size="small">确 定
          </el-button>
          <el-button v-else
                     type="primary"
                     @click="update('form')" size="small">修 改
          </el-button>
        </div>
      </el-dialog>
      <!--配置虚拟岗位-->
      <el-dialog
        title="提示"
        v-if="xnpostDialogTags"
        :visible.sync="xnpostDialogTags"
        class="user_dept_tags"
      >
        <div slot="title" class="dept_title">
          <span class="titleSpan">虚拟岗位</span>
        </div>
        <div class="dialog_main">
          <el-row>
            <el-col :span="24">
              <el-form :inline="true" :model="deptTagForm" class="demo-form-inline">
                <el-form-item>
                  <el-input v-model="deptTagForm.xnpostName" placeholder="岗位名称" size="small"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="postDummyTagSearch" size="small">查询</el-button>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
          <el-table
            ref="deptTagForm"
            :data="xnpostTagsData"
            :cell-style="cellStyle"
            :header-cell-style="headerCellStyle"
            height="calc(100% - 56px)"
            style="width: 100%;font-size: 13px;border: 1px solid #e5e5e5;border-radius: 5px;margin-top: 10px"
            @row-click="deptTagRowClick"
            @selection-change="deptTagSelect"
            :select-on-indeterminate="false"
            class="dialog_tags_table"
          >
            <el-table-column fixed="left"
                             type="selection">
            </el-table-column>
            <el-table-column align="center" type="index"
                             label="序号" min-width="5">
            </el-table-column>
            <el-table-column
              prop="postCode"
              align="center"
              min-width="10"
              label="岗位编码"
              width="180">
              <template slot-scope="scope">
                {{scope.row.postCode}}
              </template>
            </el-table-column>
            <el-table-column
              prop="postName"
              align="center"
              min-width="10"
              label="岗位名称"
              width="180">
              <template slot-scope="scope">
                {{scope.row.postName}}
              </template>
            </el-table-column>
            <el-table-column
              prop="postFlag"
              align="center"
              min-width="10"
              label="岗位状态">
              <template slot-scope="scope">
                {{scope.row.powerFlag | powerFlagFilter}}
              </template>
            </el-table-column>
            <el-table-column
              prop="remark"
              align="center"
              min-width="10"
              label="岗位备注">
              <template slot-scope="scope">
                {{scope.row.remark}}
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div style="text-align: center">
          <el-button @click="xnpostDialogTags = false" size="small">取 消</el-button>
          <el-button type="primary" @click="deptTagSubmit" size="small">确 定</el-button>
        </div>
      </el-dialog>
    </basic-container>
  </div>
</template>

<script>
  import {fetchList, getObj, addObj, putObj, delObj, userConDept} from "@/api/user";
  import {deptRoleList, deptRoleListByPost, fetchDeptTree, roleListAll} from "@/api/role";
  import {xnpostTagsSearch} from '@/api/individuation'
  import {deptPostList, getPostList} from '@/api/syspost'
  import waves from "@/directive/waves/index.js"; // 水波纹指令
  import {mapGetters} from "vuex";
  import ElRadioGroup from "element-ui/packages/radio/src/radio-group";
  import ElOption from "element-ui/packages/select/src/option";
  import zTree from '@/components/common/zTree';
  import {fetchTree} from '@/api/dept'

  export default {
    components: {
      ElOption,
      ElRadioGroup,
      zTree
    },
    name: "table_user",
    directives: {
      waves
    },
    data() {
      let validatePhone = (rule, value, callback) => {
        var reg=11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/;
        if(!reg.test(value)){
          callback(new Error('请输入正确的手机号'));
        }else{
          // checkPhone({phone:value}).then(res=>{
          //   if (res.data.phoneStatus==='true') {
          //     callback(new Error('手机号已注册！'));
          //   } else {
          //     this.codeEnable = false;
              callback();
          //   }
          // })
        }
      };
      return {
        //虚拟岗位
        xnpostDialogTags: false,//虚拟岗位弹窗开关
        checkedIds: [],//虚拟岗位选中的id列表
        xnpostTagsData: [],//虚拟岗位列表
        deptTagsSelection: [],
        deptSelectTags: [],
        deptSelectTagIds: [],
        checkedNewIds: [],
        currentDeptTag: '',
        deptTagForm: {
          xnpostName: ''
        },
        headerCellStyle: {
          padding: '5px 0',
          color: '#898989',
          backgroundColor: '#fafbfa',
          fontWeight: 500
        },
        cellStyle: {
          padding: '7px 0',
          color: '#5d5d5d',
          lineHeight: '35px',
          fontWeight: 500
        },

        options: [],
        userSearchInput: '',
        selectId: undefined,//当前选中的节点ID
        selectName: undefined,//当前选中的节点名称
        tableData: [],
        checkedKeys: [],
        treeDeptData: [],
        list: null,
        total: null,
        listLoading: true,
        //查询条件
        listQuery: {
          deptId: undefined,
          userSearch: undefined,
          page: 1,
          limit: 10
        },
        labelPosition: 'right',
        depts: [],
        //初始化form表单
        form: {
          //登录名
          username: undefined,
          //密码
          password: undefined,
          //姓名
          userRealname: undefined,
          email: undefined,
          phone: undefined,
          //性别
          sex: '0',
          //员工号
          empno: undefined,
          //部门
          deptId: undefined,
          //部门名称
          deptName: undefined,
          //角色
          roles: [],
          //岗位
          posts: [],
          //状态
          userFlag: '0',
          //签名
          signName: undefined,
          //部门层级
          deptList: []
        },
        filePath:'',
        //表单校验
        rules: {
          username: [
            {
              required: true,
              message: "请输入登录名",
              trigger: "blur"
            },
            {
              min: 3,
              max: 20,
              message: "长度在 6 到 20 个字符",
              trigger: "blur"
            }
          ],
          password: [
            {
              required: true,
              message: "请输入密码",
              trigger: "blur"
            },
            {
              min: 6,
              max: 20,
              message: "长度在 6 到 20 个字符",
              trigger: "blur"
            }
          ],
          empno: [
            {
              required: true,
              message: "请输入员工号",
              trigger: "blur"
            }
          ],
          phone:[
            { required: true, message: '请输入手机号', trigger: 'blur' },
            { validator: validatePhone, trigger: 'blur' },
          ],
          /*deptList: [
            {
              required: false,
              message: "请选择部门",
              trigger: "blur",
              type: "array",
              min: 1
            }
          ],
          roles: [
            {
              required: false,
              message: "请选择角色",
              trigger: "change",
              type: "array",
              min: 1
            }
          ],
          posts: [
            {
              required: false,
              message: "请选择岗位",
              trigger: "change",
              type: "array",
              min: 1
            }
          ]*/
        },
        statusOptions: ["0", "1"],
        //角色选择
        rolesOptions: [],
        //岗位选择
        postsOptions: [],
        dialogFormVisible: false,
        dialogDeptVisible: false,
        dialogStatus: "",
        textMap: {
          update: "编辑用户",
          create: "新增用户",
        },
        isDisabled: {
          0: true,
          1: false
        },
        tableKey: 0,
        fileList2: [],
        rowClass: {
          height: '25px'
        },
        cellClass: {
          padding: '7px 0px'
        },
        // deptTagsSelection: [],
        currentRow: {}
      };
    },
    computed: {
      ...mapGetters(["permissions"]),
      editRules:function () {
        let validatePhone = (rule, value, callback) => {
          var reg=11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/;
          if(!reg.test(value)){
            callback(new Error('请输入正确的手机号'));
          }else{
            // checkPhone({phone:value}).then(res=>{
            //   if (res.data.phoneStatus==='true') {
            //     callback(new Error('手机号已注册！'));
            //   } else {
            //     this.codeEnable = false;
            callback();
            //   }
            // })
          }
        };
        let validateEmail = (rule, value, callback) => {
          var reg=/[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
          if(value!==0&&!value){
            callback();
          }else if(!reg.test(value)){
            callback(new Error('请输入正确的邮箱'));
          }else{
            callback();
          }
        };
        if(this.dialogStatus === "update"){
          return {
            username: [
              {
                required: true,
                message: "请输入登录名",
                trigger: "blur"
              },
              {
                min: 3,
                max: 20,
                message: "长度在 6 到 20 个字符",
                trigger: "blur"
              }
            ],
            empno: [
              {
                required: true,
                message: "请输入员工号",
                trigger: "blur"
              }
            ],
            phone:[
              { required: true, message: '请输入手机号', trigger: 'blur' },
              { validator: validatePhone, trigger: 'blur' },
            ],
            email:[
              { validator: validateEmail, trigger: 'blur' },
            ],
          }
        }else{
          return {
            username: [
              {
                required: true,
                message: "请输入登录名",
                trigger: "blur"
              },
              {
                min: 3,
                max: 20,
                message: "长度在 6 到 20 个字符",
                trigger: "blur"
              }
            ],
            password: [
              {
                required: true,
                message: "请输入密码",
                trigger: "blur"
              },
              {
                min: 6,
                max: 20,
                message: "长度在 6 到 20 个字符",
                trigger: "blur"
              }
            ],
            empno: [
              {
                required: true,
                message: "请输入员工号",
                trigger: "blur"
              }
            ],
            phone:[
              { required: true, message: '请输入手机号', trigger: 'blur' },
              { validator: validatePhone, trigger: 'blur' },
            ],
            email:[
              { validator: validateEmail, trigger: 'blur' },
            ],
          }
        }
      }
    },
    //过滤器
    filters: {
      //状态过滤器
      statusFilter(status) {
        const statusMap = {
          0: "无效",
          1: "有效",
          9: "锁定"
        };
        return statusMap[status];
      },
      //性别过滤器
      sexFilter(status) {
        const statusMap = {
          0: "男",
          1: "女",
        };
        return statusMap[status];
      },
      //岗位拼接
      postListFilter(items) {
        return items.map(item => item.postName).join(',')
      },
      powerFlagFilter(item){
        let statusMap = {
          0: "有效",
          1: "无效"
        };
        return statusMap[item];
      }
    },
    created() {
      //查询部门树
      this.getList();
      //查询角色列表
      this.getRoleList();
      //查询岗位列表
      this.getPostList();
      //检查是否有权限
      this.sys_user_add = this.permissions["sys_user_add"];
      this.sys_user_edit = this.permissions["sys_user_edit"];
      this.sys_user_del = this.permissions["sys_user_del"];
      this.sys_user_xnpost = this.permissions["sys_user_xnpost"];
    },
    methods: {
      //重置from表单
      resetForm() {
        this.form = {
          //登录名
          username: undefined,
          //密码
          password: undefined,
          //姓名
          userRealname: undefined,
          phone: undefined,
          email: undefined,
          //性别
          sex: '0',
          //员工号
          empno: undefined,
          //部门
          deptId: undefined,
          //部门名称
          deptName: undefined,
          //角色
          roles: [],
          //项目
          projects:[],
          //岗位
          posts: [],
          //状态
          userFlag: '0',
          //签名
          signName: undefined,
          //部门层级
          deptList: []
        }
      },

      //强制刷新岗位
      postChange() {
        this.$forceUpdate();
      },
      //强制刷新角色
      roleChange() {
        this.$forceUpdate();
      },
      //加载部门和列表总接口
      getList() {
        //查询部门树
        fetchTree().then(response => {
          this.treeDeptData = response.data;
          this.options = response.data;
          this.selectId = 0;
          this.getNodeData(this.treeDeptData[0])
        })
      },
      //点击树菜单调用方法
      getNodeData(data) {
        //设置当前选中节点ID
        this.selectId = data.id;
        //设置当前选中节点名字
        this.selectName = data.name;
        this.getTableData()
      },
      //根据部门编码获取列表
      getTableData() {
        if (this.selectId) {
          this.listQuery.deptId = this.selectId;
          //打开遮罩
          this.tableLoading = true
          fetchList(this.listQuery).then(response => {
            this.tableData = response.data.records
            this.total = response.data.total
            this.listLoading = false
            //关闭遮罩
            this.tableLoading = false
          })
        }
      },
      //点击树菜单
      zTreeOnClicks(event, treeId, treeNode) {
        this.getNodeData(treeNode)
      },
      //条件查询树
      zTreeOnSearch(node) {
        this.getNodeData(node)
      },
      //条件查询用户
      userSearch() {
        this.getTableData()
      },

      //新增用户
      handlerAdd() {
        //重置表单
        this.resetForm();
        this.dialogStatus = "create";
        this.filePath = "";
        this.dialogFormVisible = true;
        this.$refs['form'].resetFields();
      },
      // 编辑用户
      handleUpdate(row) {
        getObj(row.userId).then(response => {
          console.log(response.data);
          this.form = response.data;
          //打开弹窗
          this.dialogFormVisible = true;
          //设置弹窗标题
          this.dialogStatus = "update";
          this.filePath = response.data.signName;
          //角色信息
          this.form.roles = response.data.roleList.map(item => item.roleId)
          //岗位信息
          this.form.posts = response.data.postList.map(item => item.id)
          //设置一个展示密码
          this.form.password = "password";
        });
        this.$refs['form'].clearValidate()
      },
      //获取角色列表
      getRoleList() {
        roleListAll().then(response => {
          this.rolesOptions = response.data;
        })
      },
      //获取岗位列表
      getPostList() {
        getPostList().then(response => {
          this.postsOptions = response.data;
        })
      },
      //分页总条数
      handleSizeChange(val) {
        this.listQuery.limit = val;
        this.getTableData();
      },
      //分页查询开始页
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getTableData();
      },
      //创建
      create(formName) {
        const set = this.$refs;
        if (this.form.deptList) {
          this.form.deptId = this.form.deptList[this.form.deptList.length - 1]
        }
        set[formName].validate(valid => {
          if (valid) {
            addObj({...this.form,signName:this.filePath}).then(() => {
              this.dialogFormVisible = false;
              this.getTableData()
              this.$notify({
                title: "成功",
                message: "创建成功",
                type: "success",
                duration: 2000
              });
            });
          } else {
            return false;
          }
        });
      },
      //取消
      cancel(formName) {
        this.dialogFormVisible = false;
        this.$refs[formName].resetFields();
      },
      //修改
      update(formName) {
        const set = this.$refs;
        if (this.form.deptList) {
          this.form.deptId = this.form.deptList[this.form.deptList.length - 1]
        }
        set[formName].validate(valid => {
          if (valid) {
            this.dialogFormVisible = false;
            this.form.password = undefined;
            putObj({...this.form,signName:this.filePath}).then(() => {
              this.dialogFormVisible = false;
              this.getTableData()
              this.$notify({
                title: "成功",
                message: "修改成功",
                type: "success",
                duration: 2000
              });
            });
          } else {
            return false;
          }
        });
      },
      //删除
      deletes(row) {
        this.$confirm(
          "此操作将永久删除该用户(用户名:" + row.username + "), 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          delObj(row.userId)
            .then(() => {
              this.getTableData();
              this.$notify({
                title: "成功",
                message: "删除成功",
                type: "success",
                duration: 2000
              });
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
      // 修改table header的背景色
      tableHeaderColor({row, column, rowIndex, columnIndex}) {
        if (rowIndex === 0) {
          return 'background-color: #fafafa;color:#999; font-size:12px;'
        }
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
      //配置虚拟岗位按钮
      xnpostAllocation(row) {
        //查询from
        this.deptTagForm = {
          xnpostName: '',
        }
        this.currentRow = row
        this.deptTagsSelection = []
        //发送条件查询查询虚拟岗位
        xnpostTagsSearch({...this.deptTagForm, userId: row.userId}).then(res => {
          this.xnpostDialogTags = true//设置虚拟岗位列表开启
          this.checkedIds = res.data.checkeds//获取选中的列表
          this.xnpostTagsData = res.data.xnposts//获取虚拟岗位列表
          this.checkedNewIds = []//新的选中的id集合
          this.deptTagsSelection = []
          this.deptSelectTags = []
          this.deptSelectTagIds = []
          this.currentDeptTag = row.userId

          //条件查询form
          this.deptTagForm = {
            xnpostName: ''
          }
          //循环勾选
          if (this.checkedIds.length > 0) {
            this.$nextTick(() => {
              this.xnpostTagsData.filter(item => {
                return this.checkedIds.includes(item.id)
              }).forEach(item => {
                this.$refs['deptTagForm'].toggleRowSelection(item, true)
              })
            })
          }
        })
      },
      deptTagRowClick(row, event, column) {
        this.$refs['deptTagForm'].toggleRowSelection(row)
      },
      postDummyTagSearch() {
        xnpostTagsSearch({...this.deptTagForm, userId: this.currentDeptTag}).then(res => {
          this.xnpostTagsData = res.data.xnposts

          let ids = this.xnpostTagsData.map(item => item.id)
          let checks = this.checkedIds
          let selectChecks = JSON.parse(JSON.stringify(this.checkedIds))
          ids.forEach(item => {
            let index = checks.indexOf(item)
            if (index !== -1) {
              checks.splice(index, 1)
            }
          })
          this.checkedNewIds = checks

          if (selectChecks.length > 0) {
            this.$nextTick(() => {
              this.xnpostTagsData.filter(item => {
                return selectChecks.includes(item.id)
              }).forEach(item => {
                this.$refs['deptTagForm'].toggleRowSelection(item, true)
              })
            })
          }
        })
      },
      deptTagSelect(selection) {
        this.deptTagsSelection = selection
        this.deptSelectTagIds = selection.map(item=>item.id)
        this.checkedIds = this.checkedNewIds.concat(this.deptSelectTagIds)
      },
      deptTagSubmit() {
        this.xnpostDialogTags = false
        userConDept({
          userId: this.currentRow.userId,
          posDummys: this.checkedIds.join(',')
        }).then(res => {
          if (res.data.data) {
            this.$notify({
              title: '成功',
              message: '修改成功',
              type: 'success',
              duration: 2000
            })
          } else {
            return false
          }
        })
      }
    }
  };
</script>
<style scoped>
  .el-table--medium td, .el-table--medium th {
    padding: 5px 0 !important;
  }

  .preTitle {
    height: 16px;
    width: 5px;
    background-color: #3a8ee6;
    display: block;
    float: left;
    text-align: center;
    margin: 4px 0;
  }

  .nextTitle {
    float: left;
    padding: 0 0 0 5px;
    line-height: 24px;;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
    color: #666;
  }

  .fr {
    float: right;
  }

  .fl {
    float: left;
  }

  /* 清浮动 */
  .clearfix:after {
    visibility: hidden;
    display: block;
    font-size: 0;
    content: " ";
    clear: both;
    height: 0;
  }

  .clearfix {
    zoom: 1; /* for IE6 IE7 */
  }

  .app-container {
    padding: 10px 20px;
  }

  #areaTree {
    border: none
  }

  .zTree_custom >>> .el-header {
    padding: 5px !important;
  }

  .zTree_custom >>> .el-input__inner {
    height: 28px;
  }

  .in_custom >>> .el-input__inner {
    width: 258px;
    height: 28px;
  }

  /* .el-table{border: 1px solid #e5e5e5;} */
  .el-table::before {
    height: 0;
  }

  .el-table {
    border: 1px solid #e5e5e5;
  }

  .button_edit {
    padding: 0;
    border: none;
    color: #66B1FF;
  }

  .button_delete {
    padding: 0;
    border: none;
    color: #F78989;
    background-color: #fff;
  }

  .button_delete:hover {
    color: #F78989;
  }
</style>

<style lang="scss" scoped>
  .user_dept_tags {
    /deep/ .el-dialog {
      width: 70vw;
      height: 80vh;
      margin-top: 10vh !important;
      .dept_title {
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
        padding: 0 60px 10px 60px;
        .dialog_main {
          height: 100%;
          .el-row {
            margin-bottom: 5px;
            .el-form-item {
              margin-bottom: 0;
            }
          }
          .dialog_tags_table {
            font-size: 12px;
            border: 1px solid #e5e5e5;
            border-radius: 5px;
            .cell {
              line-height: 25px;
              .el-button {
                padding: 0;
              }
            }
          }
        }
      }
      .el-dialog__footer {
        padding: 10px;
        text-align: center;
      }
    }
  }
  .user_add_dialog{
    display: flex;
    justify-content: center;
    align-items: center;
    /deep/ .el-dialog{
      margin: 0 !important;
      .add_title {
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
        padding:0 20px;
        overflow: auto;
        .el-row {
          margin-bottom: 0;
          .el-form-item {
            margin-bottom: 15px;
          }
          .add_userFlag{
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
                  width: 138px;
                  height: 138px;
                  line-height: 138px;
                  text-align: center;
                }
                .avatar{
                  /*width: 178px;*/
                  height: 138px;
                  display: block;
                }
              }
            }
          }
        }
      }
    }
  }
</style>
