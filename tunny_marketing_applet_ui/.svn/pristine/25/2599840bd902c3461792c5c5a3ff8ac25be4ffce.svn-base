<template>
  <div class="app-container pull-auto">
    <el-row style="height: 100%">
      <el-col :span="6" style='margin-top:0;margin-right: 20px;height: 100%'>
        <el-card class="box-card" body-style="padding:0;height:100%" style="height:100%" shadow="never">
          <z-tree :zNodes="treeDeptData"
                  :checkEnable="false"
                  :simple-check="false"
                  :default-select-one="true"
                  :search-enable="true"
                  search-placeholder="请输入组织名称查找"
                  @tree-click="zTreeOnClicks"
                  @tree-search="zTreeOnSearch"
                  style="height: 100%">
          </z-tree>
        </el-card>
      </el-col>

      <el-col :span="17" style='margin-top:0;height: 100%'>
        <el-header style="height: 40px;padding:0">
          <el-row>
            <el-col :span="18">
              <el-input
                placeholder="请输入员工姓名"
                size="small"
                style="float: left;width: 30%"
                v-model="userSearchInput"
                @keyup.enter.native="userSearch"
                clearable
              >
                <i slot="suffix" class="el-input__icon el-icon-search" @click.stop="treeSearch"></i>
              </el-input>
            </el-col>
            <!--<el-col :span="12"><div></div></el-col>-->
            <el-col :span="6">
              <el-button style="float: right;margin-left: 5px;width: 80px" size="small" @click="deriveExcel" type="primary">导出</el-button>
              <el-button style="float: right;margin-left: 10px;width: 80px" size="small" @click="handlerAdd">新增</el-button>
            </el-col>
          </el-row>
        </el-header>
        <el-card class="box-card" body-style="height:100%;padding:0" style="height:calc(100% - 70px)" shadow="never">
          <el-table :data="tableData"
                    :cell-style="cellStyle"
                    :header-cell-style="headerCellStyle"
                    height="100%"
                    tooltip-effect="light"
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
                             prop="userRealname">
            </el-table-column>

            <el-table-column align="center"
                             label="性别"
                             prop="sex">
              <template slot-scope="scope">
                <span>{{ scope.row.sex==0?'男':'女' }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="岗位"
                             prop="postName"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.postName:'' }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="部门"
                             prop="deptName">
            </el-table-column>

            <el-table-column align="center"
                             label="员工号"
                             prop="empno">
            </el-table-column>

            <el-table-column align="center"
                             label="状态"
                             prop="userFlag">
              <template slot-scope="scope">
                {{scope.row.userFlag==0?'有效':'无效'}}
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="操作"
                             width="100%">
              <template slot-scope="scope">
                <el-button v-if="sys_user_edit"
                           type="text"
                           plain
                           @click="handleUpdate(scope.row)">编辑
                </el-button>
                <el-button v-if="sys_user_del"
                           type="text"
                           style="color: #F78989"
                           plain
                           @click="deletes(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
        <el-footer style="height: 30px;padding:2px 0 0 0">
          <div v-show="!listLoading"
               class="pagination-container">
            <el-pagination @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :current-page.sync="listQuery.page"
                           :page-sizes="[10,20,30,50]"
                           :page-size="listQuery.limit"
                           class="pull-left"
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

    <!--部门选择弹出框-->
    <el-dialog :title="textMap['deptxz']"
               :visible.sync="dialogDeptVisible">
      <el-tree class="filter-tree"
               :data="treeDeptData"
               :default-checked-keys="checkedKeys"
               check-strictly
               node-key="id"
               highlight-current
               ref="deptTree"
               :props="defaultProps"
               @node-click="chooseDept"
               default-expand-all>
      </el-tree>
    </el-dialog>

    <!--新增用户弹出框-->
    <el-dialog :title="textMap[dialogStatus]"
               :visible.sync="dialogFormVisible">
      <div slot="title">
        <span class="preTitle"></span>
        <span class="nextTitle">{{textMap[dialogStatus]}}</span>
      </div>
      <el-form :model="form"
               :rules="rules"
               ref="form"
               label-width="100px"
               label-position="right"
      >
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
            <el-form-item label="密码" v-if="dialogStatus=='create'"
                          prop="password"
                          size="small">
              <el-input type="password"
                        v-model="form.password"
                        placeholder="请输入密码"></el-input>
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
                          prop="deptName"
                          size="small">
              <el-input v-model="form.deptName"
                        placeholder="选择部门"
                        @focus="handleDept()"
                        readonly></el-input>
              <input type="hidden"
                     v-model="form.deptId"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="22">
            <el-form-item label="角色"
                          prop="role"
                          class="roleLine"
                          size="small"
            >
              <el-select class="filter-item" filterable
                         v-model="roles"
                         placeholder="请选择角色"
                         multiple
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
          <el-col :span="11">
            <el-form-item label="所属岗位"
                          prop="postId"
                          size="small">
              <el-select class="filter-item"
                         v-model="form.postId"
                         placeholder="请选择岗位"
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
          <el-col :span="11">
            <el-form-item label="用户状态" prop="userFlag"
                          size="small">
              <el-switch
                v-model="form.userFlag"
                active-value="0"
                inactive-value="1">
              </el-switch>
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
  </div>
</template>

<script>
  import {fetchList, getObj, addObj, putObj, delObj} from "@/api/user";
  import {deptRoleList, deptRoleListByPost, fetchDeptTree, roleListAll} from "@/api/role";
  import {deptPostList, getPostList} from '@/api/syspost'
  import waves from "@/directive/waves/index.js"; // 水波纹指令
  // import { parseTime } from '@/utils'
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
      return {
        headerCellStyle:{
          padding:'5px 0',
          color:'#898989',
          backgroundColor:'#fafbfa',
          fontWeight:500
        },
        cellStyle:{
          padding:'8px 0',
          color:'#5d5d5d',
          lineHeight:'15px',
          fontWeight:500
        },
        userSearchInput:'',
        selectId: undefined,//当前选中的节点ID
        selectName: undefined,//当前选中的节点名称
        tableData: [],
        checkedKeys: [],
        treeDeptData: [],
        defaultProps: {
          children: "children",
          label: "name"
        },
        list: null,
        total: null,
        listLoading: true,
        listQuery: {
          deptId: undefined,
          page: 1,
          limit: 20
        },
        labelPosition: 'right',
        depts: [],
        roles: [],
        posts: [],

        //初始化form表单
        form: {
          //登录名
          username: undefined,
          //密码
          password: undefined,
          //姓名
          userRealname: undefined,
          //性别
          sex: '0',
          //员工号
          empno: undefined,
          //部门
          deptId: undefined,
          //部门名称
          deptName: undefined,
          //角色
          role: [],
          //岗位
          postId: undefined,
          //岗位名字
          postName: undefined,
          //状态
          userFlag: undefined,
          //签名
          signName: undefined
        },
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
          deptId: [
            {
              required: true,
              message: "请选择部门",
              trigger: "blur"
            }
          ],
          roleList: [
            {
              required: true,
              message: "请选择角色",
              trigger: "blur"
            }
          ],
          post: [
            {
              required: true,
              message: "请选择岗位",
              trigger: "blur"
            }
          ]
        },
        statusOptions: ["0", "1"],
        rolesOptions: [],
        postsOptions: [],
        dialogFormVisible: false,
        dialogDeptVisible: false,
        userAdd: false,
        userUpd: false,
        userDel: false,
        dialogStatus: "",
        textMap: {
          update: "编辑用户",
          create: "新增用户",
          deptxz: "请选择部门"
        },
        isDisabled: {
          0: false,
          1: true
        },
        tableKey: 0
      };
    },
    computed: {
      ...mapGetters(["permissions"]),
    },
    filters: {
      statusFilter(status) {
        const statusMap = {
          0: "有效",
          1: "无效",
          9: "锁定"
        };
        return statusMap[status];
      },
      sexFilter(status) {
        const statusMap = {
          0: "男",
          1: "女",
        };
        return statusMap[status];
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
    },
    methods: {
      resetForm() {
        this.form = {
          //登录名
          username: undefined,
          //密码
          password: undefined,
          //姓名
          userRealname: undefined,
          //性别
          sex: '0',
          //员工号
          empno: undefined,
          //部门
          deptId: undefined,
          //部门名称
          deptName: undefined,
          //角色
          role: [],
          //岗位
          postId: undefined,
          //岗位名字
          postName: undefined,
          //状态
          userFlag: undefined,
          //签名
          signName: undefined
        }
      },
      getList() {
        //查询部门树
        fetchTree(this.listQuery).then(response => {
          this.treeDeptData = response.data;
          this.selectId = 0;

          this.getNodeData(this.treeDeptData[0])
        })
      },
      //点击树菜单
      zTreeOnClicks(event, treeId, treeNode) {
        this.getNodeData(treeNode)
      },
      zTreeOnSearch(node) {
        this.getNodeData(node)
      },
      userSearch(){},
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
          console.log(this.listQuery);
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
      //新增用户
      handlerAdd() {
        //重置表单
        this.resetForm();
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
        this.$refs['form'].resetFields()
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
      //选择部门
      chooseDept(data) {
        this.dialogDeptVisible = false;
        this.form.deptId = data.id;
        this.form.deptName = data.name;
      },
      //打开部门选择
      handleDept() {
        fetchDeptTree().then(response => {
          this.treeDeptData = response.data;
          this.dialogDeptVisible = true;
        });
      },
      handleSizeChange(val) {
        this.listQuery.limit = val;
        this.getTableData();
      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getTableData();
      },
      handleUpdate(row) {
        getObj(row.userId).then(response => {
          this.form = response.data;
          //打开弹窗
          this.dialogFormVisible = true;
          //设置弹窗标题
          this.dialogStatus = "update";
          //角色信息
          this.form.role = response.data.roleList.map(item=>item.roleId)
          this.roles = this.form.role
          // deptRoleListByPost(row.postId).then(response => {
          //   this.rolesOptions = response.data;
          // });
        });
        this.$refs['form'].clearValidate()
      },
      create(formName) {
        const set = this.$refs;
        set[formName].validate(valid => {
          if (valid) {
            addObj(this.form).then(() => {
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
      cancel(formName) {
        this.dialogFormVisible = false;
        this.$refs[formName].resetFields();
      },
      update(formName) {
        const set = this.$refs;
        this.form.role = this.roles;
        //this.form.postId = this.post;
        console.log(this.form.postId);
        set[formName].validate(valid => {
          if (valid) {
            this.dialogFormVisible = false;
            this.form.password = undefined;
            putObj(this.form).then(() => {
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
      deriveExcel(){

      },
      resetTemp() {
        this.form = {
          id: undefined,
          username: "",
          password: "",
          role: [],
          delFlag: "",
          deptId: ""
        };
      }
    }
  };
</script>
<style lang="scss">
  /*.el-header {
    padding: 0 !important;
  }*/
  .el-table {
    font-size: 12px !important;
    color:#333 !important;
  }
  .el-table--medium td, .el-table--medium th {
    padding: 5px 0 !important;
  }
  .el-button--text {
    padding:0 !important;
  }
  .preTitle{
    height: 20px;
    width: 8px;
    background-color: #3a8ee6;
    display: block;
    float: left;
    text-align: center;
    margin:4px 0;
  }
  .nextTitle{
    float: left;
    padding:0 0 0 5px;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
  }
</style>
