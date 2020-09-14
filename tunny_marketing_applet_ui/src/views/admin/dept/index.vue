<template>
  <div class="app-container calendar-list-container" style="height:calc(100% - 30px)">
    <basic-container style="height:100%">

      <el-row style="height: 100%" :gutter="20">
        <!--部门树-->
        <el-col :span="5"
                style="height:100%; border:1px solid #e5e5e5; border-radius:3px; padding: 5px; "
                class="zTree_custom">
          <z-tree :zNodes="treeData"
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
                  placeholder="请输入部门名称"
                  size="small"
                  style="width: 258px; height:28px;"
                  class="fl"
                  v-model="listQuery.dataSearch"
                  @keyup.enter.native="dataSearch"
                  clearable
                >
                  <i slot="suffix" class="el-input__icon el-icon-search" @click.stop="dataSearch"></i>
                </el-input>
              </el-col>
              <el-col :span="6">
                <el-button
                  size="mini" @click="handlerAdd"
                  class="fr">新增
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
                             prop="name"
                             label="部门名称"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.name:'' }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="部门简称"
                             prop="deptSimplify"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.deptSimplify:'' }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="部门负责人"
                             prop="deptLeader"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.deptLeader:'' }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="部门类别"
                             prop="deptType"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                {{scope.row.deptType | deptTypeFilter}}
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="所属部门"
                             prop="parentName"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.parentName:'' }}</span>
              </template>
            </el-table-column>

            <el-table-column align="center"
                             label="状态"
                             prop="userFlag">
              <template slot-scope="scope">
                {{scope.row.delFlag | statusFilter}}
              </template>
            </el-table-column>

            <el-table-column
              align="center"
              prop="createBy"
              label="创建人"
            >
            </el-table-column>

            <el-table-column
              min-width="150px"
              align="center"
              label="创建时间">
              <template slot-scope="scope">
                <span>{{scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
              </template>
            </el-table-column>

            <el-table-column
              align="center"
              prop="updateBy"
              label="修改人">
            </el-table-column>

            <el-table-column
              min-width="150px"
              align="center"
              label="修改时间">
              <template slot-scope="scope">
                <span>{{scope.row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
              </template>
            </el-table-column>
            <!--操作列-->
            <el-table-column align="center"
                             fixed="right"
                             label="操作"
                             width="100%">
              <template slot-scope="scope">
                <el-button type="text"
                           size="small"
                           plain
                           class="button_edit"
                           @click="handleEdit(scope.row,scope.index)">编辑
                </el-button>
                <el-button size="small"
                           type="text"
                           plain
                           class="button_delete"
                           @click="handleDel(scope.row,scope.index)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <!--分页-->
          <el-footer style="height: 30px;padding:2px 0 0 0">
            <!--分页控件-->
            <div v-show="!tableLoading"
                 class="pagination-container pull-center">
              <el-pagination @size-change="sizeChange"
                             @current-change="currentChange"
                             :current-page.sync="listQuery.page"
                             :page-sizes="[10,20,30,50]"
                             :page-size="listQuery.limit"
                             background
                             layout="total, sizes, prev, pager, next, jumper"
                             :total="page.total">
              </el-pagination>
            </div>
          </el-footer>
        </el-col>

        <!--新增或编辑修改弹出框-->
        <el-dialog v-if="dialogFormVisible"
                   :title="textMap[dialogStatus]"
                   :visible.sync="dialogFormVisible"
                   width="40%">
          <div slot="title">
            <span class="preTitle"></span>
            <span class="nextTitle">
              {{textMap[dialogStatus]}}
            </span>
          </div>
          <el-form :model="form"
                   :rules="rules"
                   ref="form"
                   label-width="100px"
                   label-position="right">
            <el-form-item label="所属部门"
                          prop="deptList"
                          size="small">
              <el-cascader
                v-model="deptLists"
                placeholder="试试搜索：华东大区"
                :options="treeData"
                filterable
                change-on-select
                :show-all-levels="false"
              ></el-cascader>
            </el-form-item>
            <el-form-item label="部门名称"
                          prop="name"
                          size="small">
              <el-input v-model="form.name"
                        placeholder="请输入部门名称" clearable></el-input>
              <input type="hidden"
                     v-model="form.deptId"/>
            </el-form-item>
            <el-form-item label="部门简称"
                          prop="deptSimplify"
                          size="small">
              <el-input v-model="form.deptSimplify"
                        placeholder="请输入部门简称" clearable></el-input>
            </el-form-item>
            <el-form-item label="部门负责人"
                          prop="deptLeader"
                          size="small">
              <el-input v-model="form.deptLeader"
                        placeholder="请输入部门负责人" clearable></el-input>
            </el-form-item>
            <el-form-item label="部门类别"
                          prop="deptType"
                          size="small">
              <el-select v-model="form.deptType" clearable filterable placeholder="请选择部门类别">
                <el-option
                  v-for="item in deptTypes"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="状态"
                          v-if="dialogStatus == 'update'"
                          prop="delFlag"
                          size="small">
              <el-select class="filter-item"
                         v-model="form.delFlag"
                         placeholder="请选择">
                <el-option v-for="item in statusOptions"
                           :key="item"
                           :label="item | statusFilter"
                           :value="item"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer"
               class="dialog-footer">
            <el-button @click="cancel()" size="small">取 消</el-button>
            <el-button v-if="dialogStatus=='create'"
                       type="primary"
                       @click="create('form')" size="small">确 定
            </el-button>
            <el-button v-else
                       type="primary"
                       @click="update('form')" size="small" xc>修 改
            </el-button>
          </div>
        </el-dialog>
      </el-row>
    </basic-container>
  </div>
</template>

<script>
  import {fetchTree, getObj, addObj, delObj, putObj, fetchList} from '@/api/dept'
  import {mapGetters} from 'vuex'
  import zTree from '@/components/common/zTree'

  export default {
    name: 'sysdept',
    data() {
      return {
        deptLists: [],
        deptTypes: [{
          value: '0',
          label: '区域'
        }, {
          value: '1',
          label: '公司'
        }, {
          value: '2',
          label: '部门'
        }],
        list: null,
        total: null,
        formStatus: '',
        showElement: false,
        typeOptions: ['0', '1'],
        methodOptions: ['GET', 'POST', 'PUT', 'DELETE'],

        rules: {
          deptList: [
            {
              required: true,
              message: '请选择所属部门',
              trigger: 'blur'
            }
          ],
          name: [
            {
              required: true,
              message: '请输入部门名称',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 20,
              message: '长度在 1 到 20 个字符',
              trigger: 'blur'
            }
          ],
          deptType: [
            {
              required: true,
              message: '请输入部门类别',
              trigger: 'blur'
            }
          ]
        },
        //表格数据初始化
        tableData: [],
        page: {
          total: 0, // 总页数
          currentPage: 1, // 当前页数
          pageSize: 10 // 每页显示多少条
        },
        //查询条件初始化
        listQuery: {
          dataSearch: undefined,
          page: 1,
          limit: 10
        },
        tableLoading: true,

        //树形结构初始化
        treeData: [],
        selectId: undefined,//当前选中的节点ID
        selectName: undefined,//当前选中的节点名称
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        labelPosition: 'right',
        form: {
          deptId: undefined,
          name: undefined,
          orderNum: undefined,
          deptSimplify: undefined,
          deptLeader: undefined,
          deptType: undefined,
          parentId: undefined,
          parentName: undefined,
          delFlag: undefined,
          //部门层级
          deptList: []
        },
        currentId: 0,
        deptManager_btn_add: false,
        deptManager_btn_edit: false,
        deptManager_btn_del: false,

        /*弹窗初始化*/
        statusOptions: ["0", "1"],
        dialogFormVisible: false,
        dialogFieldsVisible: false,
        dialogStatus: "",
        textMap: {
          update: "编辑",
          create: "创建"
        },
        rowClass: {//行的 style 的回调方法
          height: '25px'
        },
        cellClass: {//列的 style 的回调方法
          padding: '7px 0px'
        },
      }
    },
    components: {zTree},
    filters: {
      typeFilter(type) {
        const typeMap = {
          0: '菜单',
          1: '按钮'
        }
        return typeMap[type]
      },
      statusFilter(status) {
        const statusMap = {
          0: "无效",
          1: "有效"
        };
        return statusMap[status];
      },
      deptTypeFilter(type) {
        const typeMap = {
          0: "有效",
          1: "无效"
        };
        return typeMap[type];
      },
    },
    created() {
      this.getList()
      this.deptManager_btn_add = this.permissions['sys_dept_add']
      this.deptManager_btn_edit = this.permissions['sys_dept_edit']
      this.deptManager_btn_del = this.permissions['sys_dept_del']
    },
    computed: {
      ...mapGetters([
        'elements',
        'permissions'
      ])
    },
    methods: {
      getList() {
        fetchTree(this.listQuery).then(response => {
          this.treeData = [{
            id: 0,
            parentId: undefined,
            name: "集团",
            children: response.data,
            label: "集团",
            value: 0
          }]
          this.selectId = 0;
          this.getNodeData(this.treeData[0])
        })
      },
      currentChange(val) {
        this.page.currentPage = val
        this.listQuery.page = val
        //刷新表格
        this.getTableData(this.selectId)
      },
      sizeChange(val) {
        this.page.pageSize = val
        this.listQuery.limit = val
        //刷新表格
        this.getTableData(this.selectId)
      },
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      zTreeOnClicks(event, treeId, treeNode, nodes) {
        this.getNodeData(treeNode)
        this.deptLists = [];
        for (let i = 0; i < nodes.length; i++) {
          const element = nodes[i].id;
          this.deptLists.push(element);
        }
      },
      zTreeOnSearch(node) {
        this.getNodeData(node)
      },
      dataSearch() {
        this.getTableData(this.selectId);
      },
      //点击树菜单调用方法
      getNodeData(data) {
        //设置当前选中节点ID
        this.selectId = data.id;
        //设置当前选中节点名字
        this.selectName = data.name;

        //刷新表格
        this.getTableData(this.selectId)
      },
      update(formName) {
        this.form.deptList = this.deptLists
        const set = this.$refs
        set[formName].validate(valid => {
          if (valid) {
            if (this.deptLists) {
              this.form.parentId = this.deptLists[this.deptLists.length - 1]
            }
            putObj(this.form).then(() => {
              this.getList()
              //刷新表格
              this.getTableData(this.selectId);
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success',
                duration: 2000
              })
            })
            this.dialogFormVisible = false
            this.dialogFieldsVisible = false
          } else {
            return false
          }
        })
      },
      create(formName) {
        this.form.deptList = this.deptLists
        const set = this.$refs
        set[formName].validate(valid => {
          if (valid) {
            if (this.deptLists) {
              this.form.parentId = this.deptLists[this.deptLists.length - 1]
            }
            addObj(this.form).then(() => {
              this.getList()
              this.getTableData(this.selectId)
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
              this.dialogFormVisible = false
              this.dialogFieldsVisible = false
            })
          } else {
            return false
          }
        })
      },
      cancel() {
        this.dialogFormVisible = false
        this.dialogFieldsVisible = false
      },
      resetForm() {
        this.form = {
          deptId: undefined,
          name: undefined,
          orderNum: undefined,
          deptSimplify: undefined,
          deptLeader: undefined,
          deptType: undefined,
          parentId: undefined,
          parentName: undefined,
          delFlag: undefined,
          //部门层级
          deptList: []
        }
      },
      //根据部门编码获取列表
      getTableData(id) {
        //给listQuery查询条件赋值id
        this.listQuery.deptId = id;
        console.log(this.listQuery);
        //调用查询方法根据部门编码获取列表
        this.tableLoading = true
        fetchList(this.listQuery).then(response => {
          this.tableData = response.data.records
          this.page.total = response.data.total
          this.tableLoading = false

        })
      },
      handlerAdd: function () {
        //重置表单
        this.resetForm();
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      handleEdit(row, index) {
        this.resetForm();
        getObj(row.deptId).then(response => {
          this.form = response.data;
          this.deptLists = this.form.deptList;
          this.dialogFormVisible = true;
          this.dialogStatus = "update";
        });
      },
      handleDel(row, index) {
        this.$confirm(
          "是否确认删除岗位名称为为" + row.name + ", 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          delObj(row.deptId)
            .then(() => {
              this.getList()
              this.getTableData(this.selectId)
              this.$notify({
                title: "成功",
                message: "删除成功",
                type: "success",
                duration: 2000
              });
            })
            .catch(() => {
              this.getList()
              this.getTableData(this.selectId)
              this.$notify({
                title: "失败",
                message: "删除失败",
                type: "error",
                duration: 2000
              });
            });
        });
      },
      handleDelete() {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          delObj(this.currentId).then(() => {
            this.getList()
            this.resetForm()
            this.cancel()
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 2000
            })
          })
        })
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

