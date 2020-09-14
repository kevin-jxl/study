<template>
  <div class="app-container calendar-list-container">
    <basic-container>
      <div class="filter-container crud-menu">
        <div class="clearfix">
          <!--检索框-->
          <el-input @keyup.enter.native="handleFilter"
                    style="width: 200px;"
                    class="filter-item fl"
                    size="mini"
                    placeholder="请输入角色名称"
                    suffix-icon="el-icon-search"
                    v-model="listQuery.roleName">
          </el-input>
          <el-button class="filter-item fr"
                     @click="handleCreate"
                     icon="edit"
                     size="mini"
                     v-if="roleManager_btn_add">新增
          </el-button>
        </div>
      </div>

      <el-table :key='tableKey'
                :data="list"
                v-loading="listLoading"
                element-loading-text="给我一点时间"
                fit
                size="mini"
                height="calc(100% - 80px)"
                tooltip-effect="dark"
                style="width: 100%;font-size: 12px;margin-bottom:15px;border-radius:3px;"
                :header-cell-style="tableHeaderColor">

        <el-table-column align="center" type="index" width="50"
                         label="序号">
        </el-table-column>

        <el-table-column label="角色名称">
          <template slot-scope="scope">
            <span>{{scope.row.roleName}}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="角色标识">
          <template slot-scope="scope">
            <span>{{scope.row.roleCode}}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="角色描述">
          <template slot-scope="scope">
            <span>{{scope.row.roleDesc }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="创建时间">
          <template slot-scope="scope">
            <span>{{scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作"
                         align="center"
                         width="280">
          <template slot-scope="scope">
            <el-button size="mini"
                       type="text"
                       style="color: #66B1FF"
                       v-if="roleManager_btn_edit"
                       @click="handleUpdate(scope.row)">编辑
            </el-button>
            <el-button size="mini"
                       type="text"
                       style="color: #F78989"
                       v-if="roleManager_btn_del"
                       @click="handleDelete(scope.row)">删除
            </el-button>
            <el-button size="mini"
                       type="text"
                       style="color: #85CE61"
                       @click="handlePermission(scope.row)"
                       v-if="roleManager_btn_perm">菜单权限
            </el-button>
            <el-button size="mini"
                       type="text"
                       style="color: #E6A23C"
                       @click="handleDataPermission(scope.row)"
                       v-if="roleManager_btn_perm"
            >数据权限
            </el-button>
          </template>
        </el-table-column>

      </el-table>

      <div v-show="!listLoading"
           class="pagination-container">
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page.sync="listQuery.page"
                       :page-sizes="[10,20,30, 50]"
                       :page-size="listQuery.limit"
                       background
                       class="pull-left"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
      </div>
    </basic-container>
    <div class="dialog_mask_mine">
      <el-dialog :visible.sync="dialogFormVisible" width="35%">
        <div slot="title">
          <span class="preTitle"></span>
          <span class="nextTitle">{{textMap[dialogStatus]}}</span>
        </div>
        <el-form :model="form"
                 :rules="rules"
                 size="mini"
                 ref="form"
                 label-width="100px">
          <el-form-item label="角色名称"
                        prop="roleName">
            <el-input v-model="form.roleName"
                      placeholder="角色名称"></el-input>
          </el-form-item>
          <el-form-item label="角色标识"
                        prop="roleCode">
            <el-input v-model="form.roleCode"
                      placeholder="角色标识"></el-input>
          </el-form-item>

          <el-form-item label="描述"
                        prop="roleDesc">
            <el-input v-model="form.roleDesc"
                      type="textarea"
                      placeholder="描述"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="cancel('form')" size="small">取 消</el-button>
          <el-button v-if="dialogStatus=='create'"
                     type="primary"
                     size="small"
                     @click="create('form')">确 定
          </el-button>
          <el-button v-else
                     type="primary"
                     size="small"
                     @click="update('form')">修 改
          </el-button>
        </div>
      </el-dialog>
    </div>
    <!--<el-dialog :title="textMap[dialogStatus]"
               :visible.sync="dialogDataVisible" class="role_dept">
      <div slot="title">
        <span class="titleSpan">{{textMap[dialogStatus]}}</span>
      </div>
      <div>
        <div class="dialog_form_select">
          <el-row :gutter="10" type="flex" class="row-bg" justify="center">
            <el-col :span="4">
              <span>选择菜单：</span>
            </el-col>
            <el-col :span="10">
              <el-select v-model="dataPermission" placeholder="请选择" id="selectMenu" size="small">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
        </div>
        <div class="dialog_tree" v-if="dataPermission === '1'">
          <z-tree :z-nodes="treeDeptData"
                  :check-enable="true"
                  :simple-check="false"
                  :default-select-one="false"
                  :search-enable="false"
                  search-placeholder="请输入组织名称查找"
                  class="dialog_z_tree"
          ></z-tree>
        </div>
      </div>
      <div slot="footer"
           class="dialog-footer">
        <el-button @click="dataDialogCancel" size="small">取 消</el-button>
        <el-button v-if="dialogStatus=='create'"
                   type="primary"
                   size="small"
                   @click="create('form')">确 定
        </el-button>
        <el-button v-else
                   type="primary"
                   size="small"
                   @click="update('form')">修 改
        </el-button>
      </div>
    </el-dialog>


    <el-dialog :title="textMap[dialogStatus]"
               :visible.sync="dialogPermissionVisible" class="role_menu">
      <div slot="title">
        <span class="titleSpan">{{textMap[dialogStatus]}}</span>
      </div>
      <div class="dialog_tree">
        &lt;!&ndash;<el-tree class="filter-tree"
                 :data="treeData"
                 :default-checked-keys="checkedKeys"
                 :check-strictly="false"
                 node-key="id"
                 highlight-current
                 :props="defaultProps"
                 show-checkbox
                 ref="menuTree"
                 :filter-node-method="filterNode"
                 default-expand-all>
        </el-tree>&ndash;&gt;
        &lt;!&ndash;<z-tree :z-nodes="treeData"
                :check-enable="true"
                :simple-check="false"
                :default-select-one="false"
                :search-enable="false"
                search-placeholder="请输入组织名称查找"
                class="dialog_z_tree"
        ></z-tree>&ndash;&gt;
        <tree-table :data="treeData" :columns="columns" border size="medium">
          &lt;!&ndash;<el-table-column label="操作" width="150" header-align="center" align="center">
            <template slot-scope="scope">
              <el-button
                size="mini" type="text" style="color:#5681f1"
                @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button
                size="mini" type="text" style="color:#f67673"
                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>&ndash;&gt;
        </tree-table>
      </div>
      <div slot="footer"
           class="dialog-footer">
        <el-button type="primary"
                   @click="updatePermession(roleId, roleCode)">更 新
        </el-button>
      </div>
    </el-dialog>-->

    <Drawer title="Basic Drawer" :closable="false" v-model="drawerPermissonVisible" class="drawer_permission">
      <div slot="header" class="drawer_title">
        <span class="titleSpan">{{textMap[dialogStatus]}}</span>
      </div>
      <div style="height: calc(100% - 40px);" class="drawer_tree">
        <z-tree :z-nodes="treeData"
                :check-enable="true"
                :simple-check="false"
                :default-select-one="false"
                :search-enable="false"
                search-placeholder="请输入组织名称查找"
                class="dialog_z_tree"
                @tree-check="permissionTreeCheck"
                style="height: 99%"
        ></z-tree>
      </div>
      <div class="drawer_button">
        <el-button @click="permisssionDialogCancel" size="small">取 消</el-button>
        <el-button type="primary" :disabled="newPermissions.length===0"  size="small"
                   @click="updatePermession(roleId, roleCode)">确 定
        </el-button>
      </div>
    </Drawer>
    <Drawer title="Basic Drawer"
            :closable="true"
            v-model="drawerDataVisible"
            @on-close="dataDialogCancel"
            class="drawer_data">
      <div slot="header" class="drawer_title">
        <span class="titleSpan">{{textMap[dialogStatus]}}</span>
      </div>
      <div style="height: calc(100% - 40px);" class="drawer_main">
        <div class="dialog_form_select">
          <el-row :gutter="10" type="flex" class="row-bg" justify="center">
            <el-col :span="6">
              <span>选择菜单：</span>
            </el-col>
            <el-col :span="18">
              <el-select v-model="dataPermission" placeholder="请选择" id="selectMenu" size="small">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
        </div>
        <div class="dialog_tree" v-if="dataPermission === '1'">
          <z-tree :z-nodes="treeDeptData"
                  :check-enable="true"
                  :simple-check="false"
                  :default-select-one="false"
                  :search-enable="false"
                  @tree-check="dataTreeCheck"
                  search-placeholder="请输入组织名称查找"
                  class="dialog_z_tree"
          ></z-tree>
        </div>
      </div>
      <div class="drawer_button">
        <el-button @click="dataDialogCancel" size="small">取 消</el-button>
        <el-button type="primary"
                   size="small"
                   @click="updateDataPersion(roleId, roleCode)">修 改
        </el-button>
      </div>
    </Drawer>
  </div>
</template>

<script>
  import {
    fetchList,
    getObj,
    addObj,
    putObj,
    delObj,
    permissionUpd,
    fetchRoleTree,
    selectRoledataPower,
    updateRoledataPower
  } from '@/api/role'
  import * as deptAPI from '@/api/dept'
  import {fetchTree} from '@/api/menu'
  import {deptPostList} from '@/api/syspost'
  import {mapGetters} from 'vuex'
  import waves from '@/directive/waves/index.js' // 水波纹指令
  import ZTree from "../../../components/common/zTree";
  import TreeTable from "../../../components/common/treeTable";

  export default {
    name: 'table_role',
    directives: {
      waves
    },
    data() {
      return {
        treeData: [],
        treeDeptData: [],
        checkedKeys: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        menuIds: '',
        list: null,
        total: null,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 20
        },
        form: {
          roleName: undefined,
          roleCode: undefined,
          roleDesc: undefined
        },
        roleId: undefined,
        roleCode: undefined,
        rules: {
          roleName: [
            {
              required: true,
              message: '角色名称',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 20,
              message: '长度在 3 到 20 个字符',
              trigger: 'blur'
            }
          ],
          roleCode: [
            {
              required: true,
              message: '角色标识',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 20,
              message: '长度在 3 到 20 个字符',
              trigger: 'blur'
            }
          ]
        },
        statusOptions: ['0', '1'],
        rolesOptions: undefined,
        postsOptions: undefined,
        dialogFormVisible: false,
        dialogPermissionVisible: false,
        dialogDataVisible: false,
        dialogStatus: '',
        textMap: {
          update: '编辑',
          create: '创建',
          permission: '分配权限'
        },
        isDisabled: {
          0: false,
          1: true
        },
        tableKey: 0,
        roleManager_btn_add: false,
        roleManager_btn_edit: false,
        roleManager_btn_del: false,
        roleManager_btn_perm: false,
        options: [
          {
          value: '0',
          label: '全部'
        }, {
          value: '1',
          label: '自定义'
        }, {
          value: '2',
          label: '本级及子级'
        }, {
          value: '3',
          label: '本级'
        }
        ],
        dataPermission: '',
        columns:[
          {value:'name',text:'菜单名称',width:20},
          {value:'path',text:'路径',width:10},
          {value:'icon',text:'图标',width:10},
          {value:'icon',text:'图标',width:10},
          {value:'icon',text:'图标',width:10},
        ],
        drawerPermissonVisible: false,
        drawerDataVisible: false,
        newPermissions:[],
        newData:[]
      }
    },
    components: {ZTree,TreeTable},
    created() {
      this.getList()
      this.roleManager_btn_add = this.permissions['sys_role_add']
      this.roleManager_btn_edit = this.permissions['sys_role_edit']
      this.roleManager_btn_del = this.permissions['sys_role_del']
      this.roleManager_btn_perm = this.permissions['sys_role_perm']
    },
    computed: {
      ...mapGetters(['elements', 'permissions'])
    },
    methods: {
      getList() {
        this.listLoading = true
        fetchList(this.listQuery).then(response => {
          this.list = response.data.records
          this.total = response.data.total
          this.listLoading = false
        })
      },
      handleFilter () {
        this.listQuery.page = 1;
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.limit = val
        this.getList()
      },
      handleCurrentChange(val) {
        this.listQuery.page = val
        this.getList()
      },
      handleCreate() {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
      },
      handleUpdate(row) {
        getObj(row.roleId).then(response => {
          this.form = response.data;
          this.dialogFormVisible = true;
          this.dialogStatus = 'update'
        })
      },
      permissionTreeCheck(e,ids,nodes){
        this.newPermissions = ids
      },
      dataTreeCheck(e,ids,nodes){
        this.newData = ids
      },
      handlePermission(row) {
        fetchRoleTree(row.roleCode)
          .then(response => {
            this.treeData = response.data
            this.dialogStatus = 'permission'
            // this.dialogPermissionVisible = true
            this.roleId = row.roleId
            this.roleCode = row.roleCode
            this.newPermissions = []
            this.drawerPermissonVisible = true
          })
      },
      handleDataPermission(row) {
        console.info(row)
        selectRoledataPower(row.roleId).then(response => {
          this.treeDeptData = [{
            id: 0,
            parentId: undefined,
            name: "集团",
            children: response.data.deptTrees,
            label: "集团",
            value: 0
          }]
          this.dataPermission = response.data.dsType?response.data.dsType+'':'2'
          this.dialogStatus = 'permission'
          // this.dialogDataVisible = true
          this.roleId = row.roleId
          this.roleCode = row.roleCode
          this.drawerDataVisible = true
        })
      },
      /**
       * 解析出所有的太监节点id
       * @param json 待解析的json串
       * @param idArr 原始节点数组
       * @param temp 临时存放节点id的数组
       * @return 太监节点id数组
       */
      resolveAllEunuchNodeId(json, idArr, temp) {
        for (let i = 0; i < json.length; i++) {
          const item = json[i]
          // 存在子节点，递归遍历;不存在子节点，将json的id添加到临时数组中
          if (item.children && item.children.length !== 0) {
            this.resolveAllEunuchNodeId(item.children, idArr, temp)
          } else {
            temp.push(idArr.filter(id => id === item.id))
          }
        }
        return temp
      },
      handleDept() {
        fetchDeptTree().then(response => {
          this.treeDeptData = response.data
          this.dialogDeptVisible = true
        })
      },
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      handleDelete(row) {
        this.$confirm(
          "是否确认删除角色为" + row.roleName + ", 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          delObj(row.roleId).then(response => {
            this.dialogFormVisible = false
            this.getList()
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 2000
            })
          }).catch(() => {
              this.$notify({
                title: "失败",
                message: "删除失败",
                type: "error",
                duration: 2000
              });
            });
        });
      },
      create(formName) {
        const set = this.$refs
        this.form.postId = this.form.post;
        set[formName].validate(valid => {
          if (valid) {
            addObj(this.form).then(() => {
              this.dialogFormVisible = false
              this.getList()
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
            })
          } else {
            return false
          }
        })
      },
      cancel(formName) {
        this.dialogFormVisible = false
        this.$refs[formName].resetFields()
      },
      dataDialogCancel(){
        // this.dialogDataVisible = false
        this.drawerDataVisible = false
        this.dataPermission = ''
      },
      permisssionDialogCancel(){
        // this.dialogDataVisible = false
        this.drawerPermissonVisible = false
        this.dataPermission = ''
      },
      update(formName) {
        const set = this.$refs;
        set[formName].validate(valid => {
          if (valid) {
            this.dialogFormVisible = false
            putObj(this.form).then(() => {
              this.dialogFormVisible = false
              this.getList()
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 2000
              })
            })
          } else {
            return false
          }
        })
      },
      updatePermession(roleId, roleCode) {
        this.menuIds = ''
        this.menuIds = this.newPermissions.join(',')
        permissionUpd(roleId, this.menuIds).then(() => {
          this.drawerPermissonVisible = false
          fetchRoleTree(roleCode)
            .then(response => {
              this.treeData = response.data
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 2000
              })
            })
        })
      },
      updateDataPersion(roleId, roleCode){
        updateRoledataPower(this.roleId,this.dataPermission,this.newData.join(',')).then(response => {
          if(response.data.data){
            this.$notify({
              title: '成功',
              message: '修改成功',
              type: 'success',
              duration: 2000
            })
          }else{
            return false
          }
        })
      },
      resetTemp() {
        this.form = {
          id: undefined,
          roleName: undefined,
          roleCode: undefined,
          roleDesc: undefined
        }

      },
      // 修改table header的背景色
      tableHeaderColor({row, column, rowIndex, columnIndex}) {
        if (rowIndex === 0) {
          return 'background-color: #fafafa;color:#999; font-size:12px;'
        }
      },
    }
  }
</script>
<style lang="scss" scoped>
  .app-container{
    padding: 10px 20px;
    /deep/ .filter-container{
      .filter-item{
        margin-bottom: 0;
      }
    }

    /deep/ .role_menu{
      .el-dialog{
        height: 70vh;
        margin: 0 auto;
        .el-dialog__header{
          height: 58px;
        }
        .el-dialog__body{
          height: calc(100% - 124px);
          padding:10px 20px;
          .dialog_tree{
            height:99%;
            #areaTree{
              height: 100%;
              border-width: 0;
              .el-header{
                padding: 0 !important;
              }
              .el-main{
                padding:0 200px !important;
              }
            }
          }
        }
      }
    }
    /deep/ .role_dept{
      .el-dialog{
        height: 70vh;
        margin: 0 auto;
        .el-dialog__header{
          height: 58px;
        }
        .el-dialog__body{
          height: calc(100% - 124px);
          padding:10px 20px;
          .dialog_form_select{
            .el-col:first-child{
              text-align: right;
            }
          }
          .dialog_tree{
            height:99%;
            #areaTree{
              height: 100%;
              border-width: 0;
              .el-header{
                padding: 0 !important;
              }
              .el-main{
                padding:0 200px !important;
              }
            }
          }
        }
      }
    }
    .basic-container{
      height: 100%;
    }
  }

  /deep/ .drawer_permission{
    .ivu-drawer-header{
      padding:10px 16px;
      .drawer_title{
        line-height: 30px;
        .titleSpan{
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
    }
    .ivu-drawer-body{
      padding:16px 0 10px 16px;
      .drawer_tree{
        #areaTree{
          height: 100%;
          border-width: 0;
          padding:0 0 10px 0;
          .el-header{
            padding: 0 !important;
          }
          .el-main{
            padding:0 2px !important;
          }
        }
      }
      .drawer_button{
        text-align: center;
      }
    }
  }
  /deep/ .drawer_data{
    .ivu-drawer-header{
      padding:10px 16px;
      .drawer_title{
        line-height: 30px;
        .titleSpan{
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
    }
    .ivu-drawer-body{
      padding:16px 0 10px 16px;
      .drawer_main{
        .dialog_form_select{
          .el-col:first-child{
            text-align: right;
            padding: 8px 1px!important;
          }
          .el-select{
            width: 160px !important;
          }
        }
        .dialog_tree{
          height:calc(100% - 40px);
          #areaTree{
            height: 100%;
            border-width: 0;
            .el-header{
              padding: 0 !important;
            }
            .el-main{
              padding:0 2px !important;
            }
          }
        }
      }
      .drawer_button{
        text-align: center;
      }
    }
  }
  .titleSpan{
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
    margin: 6px 5px 6px 0;
  }

  .dialog_mask_mine >>> .el-dialog__title {
    font-size: 14px;
  }

  .dialog_mask_mine >>> .el-dialog__title:before {
    width: 5px;
    height: 16px;
    background-color: #409eff;
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
