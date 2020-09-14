<template>
  <div class="app-container calendar-list-container" style="height:calc(100% - 30px)">
    <basic-container style="height:100%;width:100%">
      <el-row style="height: 100%;width: 100%">
        <!--字典分类树-->
        <el-col :span="6"
                class="zTree_custom">
          <div class="item_title">
            <span class="titleSpan">字典分类</span>
            <el-dropdown @command="handleTypeCommand">
              <span class="title_dropdown">
                <i class="icon-shouqianniuicon"></i>操作
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="add">新增</el-dropdown-item>
                <el-dropdown-item command="del" :disabled="!dictTypeSelectedNode">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <div style="height: calc(100% - 28px)">
            <z-tree ref="dictTree"
                    :zNodes="dictTypeData"
                    :checkEnable="false"
                    :simple-check="false"
                    :default-select-one="true"
                    :search-enable="true"
                    search-placeholder="请输入分类名称查找"
                    @tree-click="zTreeOnClicks"
                    @tree-search="zTreeOnSearch"
                    style="height: 98%; border:none;margin-top: 8px">
            </z-tree>
          </div>
        </el-col>
        <!--列表-->
        <el-col :span="8"
                class="detail_center" v-loading="dictListLoading">
          <div class="item_title">
            <span class="titleSpan">字典列表</span>
            <el-dropdown @command="handleListCommand">
              <span class="title_dropdown">
                <i class="icon-shouqianniuicon"></i>操作
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="add">新增</el-dropdown-item>
                <el-dropdown-item command="edit" :disabled="!dictListSelectedRow">编辑</el-dropdown-item>
                <el-dropdown-item command="del" :disabled="!dictListSelectedRow">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <!--列表查询框-->
          <el-header style="height: auto;padding:5px 0">
            <el-input
              placeholder="请输入字典名称"
              size="mini"
              style="width: 100%"
              v-model="dictListInput"
              clearable
            >
              <i slot="suffix" class="el-input__icon el-icon-search"></i>
            </el-input>
          </el-header>
          <!--列表展示-->
          <el-table :data="dictFilterList"
                    ref="dictListTable"
                    tooltip-effect="dark"
                    fit
                    style="font-size: 12px;"
                    :row-style="rowClass"
                    :cell-style="cellClass"
                    :highlight-current-row="true"
                    @row-click="dictListRowClick"
                    height="calc(100% - 66px)"
          >
            <el-table-column align="center" type="index" width="50"
                             label="序号">
            </el-table-column>
            <el-table-column align="center"
                             label="字典标识"
                             prop="code"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.code:'' }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center"
                             label="字典名称"
                             prop="name"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.name:'' }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
        <el-col :span="9"
                class="detail_right" v-loading="dictDetailLoading">
          <div class="item_title">
            <span class="titleSpan">字典对照</span>
            <el-dropdown @command="handleDetailCommand">
              <span class="title_dropdown">
                <i class="icon-shouqianniuicon"></i>操作
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="add">新增</el-dropdown-item>
                <el-dropdown-item command="edit" :disabled="!dictDetailSelectedRow">编辑</el-dropdown-item>
                <el-dropdown-item command="del" :disabled="!dictDetailSelectedRow">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <!--列表查询框-->
          <el-header style="height:auto;padding:5px 0;">
            <el-input
              placeholder="请输入字典对照值"
              size="mini"
              style="width: 100%"
              v-model="dictDetailInput"
              clearable
            >
              <i slot="suffix" class="el-input__icon el-icon-search"></i>
            </el-input>
          </el-header>

          <!--列表展示-->
          <el-table :data="dictFilterDetail"
                    tooltip-effect="dark"
                    fit
                    style="font-size: 12px;"
                    :row-style="rowClass"
                    :cell-style="cellClass"
                    :highlight-current-row="true"
                    @row-click="dictDetailRowClick"
                    height="calc(100% - 66px)"
          >
            <el-table-column align="center" type="index" width="50"
                             label="序号">
            </el-table-column>
            <el-table-column align="center"
                             label="字典对照码"
                             prop="code"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.code:'' }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center"
                             label="字典对照值"
                             prop="name"
                             show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.name:'' }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center"
                             label="当前状态"
                             prop="dictFlag">
              <template slot-scope="scope">
                {{scope.row.dictFlag | statusFilter}}
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </basic-container>
    <el-dialog
      v-if="dialogTypeVisible"
      :visible.sync="dialogTypeVisible"
      class="dict_type_dialog">
      <div slot="title" class="drawer_title">
        <span class="titleSpan">{{dialogTypeName}}</span>
      </div>
      <div class="dialog_main">
        <el-form ref="dictTypeForm"
                 :model="dictTypeForm"
                 :rules="dictTypeRules"
                 label-width="80px"
                 hide-required-asterisk="true"
                 label-position="right"
        >
          <el-form-item label="所属分类" size="small" prop="parentId">
            <tree-select
              :clearable="true"
              :searchable="true"
              :options="dictTypeOptions"
              :close-on-select="true"
              placeholder="请选择分类"
              v-model="dictTypeForm.parentId"
            ></tree-select>
          </el-form-item>
          <el-form-item label="字典标识" size="small" prop="code">
            <el-input v-model="dictTypeForm.code"></el-input>
          </el-form-item>
          <el-form-item label="字典名称" size="small" prop="name">
            <el-input v-model="dictTypeForm.name"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input type="textarea" v-model="dictTypeForm.remarks" resize="none"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTypeVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="dictTypeSubmit" size="small">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      v-if="dialogListVisible"
      :visible.sync="dialogListVisible"
      class="dict_list_dialog">
      <div slot="title" class="drawer_title">
        <span class="titleSpan">{{dialogListName}}</span>
      </div>
      <div class="dialog_main">
        <el-form ref="dictListForm"
                 :model="dictListForm"
                 :rules="dictListRules"
                 label-width="80px"
                 hide-required-asterisk="true"
                 label-position="right"
        >
          <el-form-item label="字典标识" size="small" prop="code">
            <el-input v-model="dictListForm.code"></el-input>
          </el-form-item>
          <el-form-item label="字典名称" size="small" prop="name">
            <el-input v-model="dictListForm.name"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input type="textarea" v-model="dictListForm.remarks" resize="none"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogListVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="dictListSubmit" size="small">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      v-if="dialogDetailVisible"
      :visible.sync="dialogDetailVisible"
      class="dict_detail_dialog">
      <div slot="title" class="drawer_title">
        <span class="titleSpan">{{dialogDetailName}}</span>
      </div>
      <div class="dialog_main">
        <el-form ref="dictDetailForm"
                 :model="dictDetailForm"
                 :rules="dictDetailRules"
                 label-width="90px"
                 hide-required-asterisk="true"
                 label-position="right"
        >
          <el-form-item label="字典对照码" size="small" prop="code">
            <el-input v-model="dictDetailForm.code"></el-input>
          </el-form-item>
          <el-form-item label="字典对照值" size="small" prop="name">
            <el-input v-model="dictDetailForm.name"></el-input>
          </el-form-item>
          <el-form-item label="当前状态" size="small" prop="dictFlag">
            <el-switch v-model="dictDetailForm.dictFlag"
                       active-value="1"
                       inactive-value="0"></el-switch>
          </el-form-item>
          <el-form-item label="备注">
            <el-input type="textarea" v-model="dictDetailForm.remarks" resize="none"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDetailVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="dictDetailSubmit" size="small">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    dictTypeTree, addDictType, getDictType, delDictType, putDictType,
    dictIndexList, addDictIndex, getDictIndex, delDictIndex, putDictIndex,
    dictList, addDict, getDict, delDict, putDict
  } from "@/api/sysdict";
  import waves from "@/directive/waves/index.js"; // 水波纹指令
  import TreeSelect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import {mapGetters} from "vuex";
  import ElRadioGroup from "element-ui/packages/radio/src/radio-group";
  import ElOption from "element-ui/packages/select/src/option";
  import zTree from '@/components/common/zTree';

  export default {
    components: {
      ElOption,
      ElRadioGroup,
      zTree,
      TreeSelect
    },
    name: "table_user",
    directives: {
      waves
    },
    data() {
      return {
        /*公共类*/
        statusOptions: ["0", "1"],
        dialogFormVisible: false,
        dialogDeptVisible: false,
        dialogStatus: "",
        textMap: {
          update: "编辑用户",
          create: "新增用户",
        },
        isDisabled: {
          0: false,
          1: true
        },

        /*字典分类*/

        //字典分类查询条件
        dictTypelistQuery: {
          dictName: undefined,
        },
        dictTypeSelectId: undefined,//字典分类当前选中的节点ID
        dictTypeSelectName: undefined,//字典分类当前选中的节点名称
        dictTypeData: [],//字典分类容器

        dictTypeLoading: false,//字典分类加载
        dictListLoading: false,//字典分类加载
        dictDetailLoading: false,//字典分类加载
        //字典分类表单校验


        /*字典列表*/
        dictIndexData: [],//字典容器


        /*字典明细*/
        dictDetailData: [],
        dictIndexSelectId: undefined,
        dictIndexSelectName: undefined,


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
        rowClass: {
          height: '5px'
        },
        cellClass: {
          padding: '5px 0px'
        },
        currentRow: {},

        dialogTypeVisible: false,
        dialogListVisible: false,
        dialogDetailVisible: false,
        dialogTypeName: '新增字典分类',
        dialogListName: '新增字典',
        dialogDetailName: '新增字典',
        dictTypeOptions: [],
        dictTypeForm: {
          parentId: null,
          code: '',
          name: '',
          remarks: ''
        },
        dictTypeRules: {
          parentId: [
            {required: true, message: '请选择所属分类', trigger: 'blur'},
          ],
          code: [
            {required: true, message: '请输入字典标识', trigger: 'blur'},
          ],
          name: [
            {required: true, message: '请输入字典名称', trigger: 'blur'},
          ],
        },
        dictListForm: {
          code: '',
          name: '',
          remarks: ''
        },
        dictListRules: {
          code: [
            {required: true, message: '请输入字典标识', trigger: 'blur'},
          ],
          name: [
            {required: true, message: '请输入字典名称', trigger: 'blur'},
          ],
        },
        dictDetailForm: {
          code: '',
          name: '',
          dictFlag: '1',
          remarks: ''
        },
        dictDetailRules: {
          code: [
            {required: true, message: '请输入字典对照码', trigger: 'blur'},
          ],
          name: [
            {required: true, message: '请输入字典对照值', trigger: 'blur'},
          ],
        },

        dictTypeSelectedNode: undefined,
        dictListSelectedRow: undefined,
        dictDetailSelectedRow: undefined,
        dictListInput: '',
        dictDetailInput: ''
      };
    },
    computed: {
      ...mapGetters(["permissions"]),
      dictFilterList() {
        return this.dictIndexData.filter(item => {
          return item.name.indexOf(this.dictListInput) !== -1
        })
      },
      dictFilterDetail() {
        return this.dictDetailData.filter(item => {
          return item.name.indexOf(this.dictDetailInput) !== -1
        })
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
      powerFlagFilter(item) {
        let statusMap = {
          1: "有效",
          0: "无效"
        };
        return statusMap[item];
      }
    },
    created() {
      //字典分类树查询
      this.dictTypeTree();

      //检查是否有权限
      this.sys_user_add = this.permissions["sys_user_add"];
      this.sys_user_edit = this.permissions["sys_user_edit"];
      this.sys_user_del = this.permissions["sys_user_del"];
      this.sys_user_xnpost = this.permissions["sys_user_xnpost"];
    },
    methods: {
      dictTypeTree() {
        //查询字典类型树
        dictTypeTree().then(response => {
          this.dictTypeData = [{
            id: 0,
            parentId: undefined,
            name: "全部分类",
            children: response.data,
            label: "全部分类",
            value: 0
          }]
          this.getNodeData(this.dictTypeData[0])
        })
      },
      //点击字典类型树调用方法
      getNodeData(data) {
        this.dictTypeSelectedNode = data
        //设置当前选中节点ID
        this.dictTypeSelectId = data.id;
        //设置当前选中节点名字
        this.dictTypeSelectName = data.name;
        this.getDictIndexData();
      },
      //根据字典分类获取列表
      getDictIndexData() {
        //打开遮罩
        this.dictListLoading = true
        this.dictDetailData = []
        dictIndexList(this.dictTypeSelectId).then(response => {
          this.dictIndexData = response.data;
          this.dictDetailSelectedRow = undefined
          if (this.dictIndexData && this.dictIndexData.length > 0) {
            this.dictIndexSelectId = this.dictIndexData[0].id;
            this.dictIndexSelectName = this.dictIndexData[0].name;
            this.dictListSelectedRow = this.dictIndexData[0]
            this.$nextTick(() => {
              this.$refs['dictListTable'].setCurrentRow(this.dictIndexData[0])
            })
            this.getDictData();
          } else {
            this.dictIndexSelectId = undefined;
            this.dictIndexSelectName = undefined;
            this.dictListSelectedRow = undefined;
          }
          //关闭遮罩
          this.dictListLoading = false
        });
      },
      //根据字典获取字典详情
      getDictData() {
        //打开遮罩
        this.dictDetailLoading = true
        dictList(this.dictIndexSelectId).then(response => {
          this.dictDetailData = response.data;
          //关闭遮罩
          this.dictDetailLoading = false
        });
      },
      //重置DictTypefrom表单
      resetDictTypeForm() {
        this.dictTypeForm = {}
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

      dictListRowClick(row, column, event) {
        this.dictIndexSelectId = row.id;
        this.dictIndexSelectName = row.name
        this.dictListSelectedRow = row
        this.dictDetailSelectedRow = undefined
        this.getDictData();
      },
      dictDetailRowClick(row, column, event) {
        this.dictDetailSelectedRow = row
      },

      handleTypeCommand(command) {
        switch (command) {
          case 'add':
            this.dictTypeAdd();
            break;
          case 'del':
            this.dictTypeDel();
            break;
        }
      },
      dictTypeAdd() {
        let typeList = []
        dictTypeTree().then(response => {
          typeList = [{
            id: 0,
            parentId: undefined,
            name: "全部分类",
            children: response.data,
            label: "全部分类",
            value: 0
          }]
          this.handleList(typeList)
          this.dialogTypeVisible = true
          this.dictTypeOptions = typeList
          this.dictTypeForm = {
            parentId: null,
            code: '',
            name: '',
            remarks: ''
          }
        })
      },
      dictTypeDel() {
        this.$confirm(
          "此操作将永久删除该字典类别(字典类别名:" + this.dictTypeSelectName + "), 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          delDictType(this.dictTypeSelectId).then(res => {
            if (res.data.data) {
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 2000
              })
              this.dictTypeTree()
            } else {
              return false
            }
          })
        });
      },
      dictTypeSubmit() {
        this.$refs['dictTypeForm'].validate((valid) => {
          if (valid) {
            addDictType(this.dictTypeForm).then(res => {
              if (res.data.data) {
                this.$notify({
                  title: '成功',
                  message: '新增成功',
                  type: 'success',
                  duration: 2000
                })
                this.dictTypeTree()
              } else {
                return false
              }
              this.dialogTypeVisible = false
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      handleList(array) {
        array.forEach(item => {
          if (item.children) {
            this.handleList(item.children)
          } else {
            delete item.children
          }
        })
      },

      handleListCommand(command) {
        switch (command) {
          case 'add':
            this.dictListAdd();
            break;
          case 'edit':
            this.dictListEdit();
            break;
          case 'del':
            this.dictListDel();
            break;
        }
      },
      dictListAdd() {
        this.dialogListVisible = true
        this.dialogListName = '新增字典'
        this.dictListForm = {
          code: '',
          name: '',
          remarks: ''
        }
      },
      dictListEdit() {
        getDictIndex(this.dictIndexSelectId).then(res => {
          this.dialogListVisible = true
          this.dialogListName = '修改字典'
          this.dictListForm = res.data.data
        })
      },
      dictListDel() {
        this.$confirm(
          "此操作将永久删除该字典(字典名:" + this.dictIndexSelectName + "), 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          delDictIndex(this.dictIndexSelectId).then(res => {
            if (res.data.data) {
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 2000
              })
              this.getDictIndexData()
            } else {
              return false
            }
          })
        });
      },
      dictListSubmit() {
        this.$refs['dictListForm'].validate((valid) => {
          if (valid) {
            if (this.dialogListName === '新增字典') {
              addDictIndex({...this.dictListForm, dictType: this.dictTypeSelectId}).then(res => {
                if (res.data.data) {
                  this.$notify({
                    title: '成功',
                    message: '新增成功',
                    type: 'success',
                    duration: 2000
                  })
                  this.getDictIndexData()
                } else {
                  return false
                }
              })
            } else {
              putDictIndex({...this.dictListForm, dictType: this.dictTypeSelectId}).then(res => {
                if (res.data.data) {
                  this.$notify({
                    title: '成功',
                    message: '修改成功',
                    type: 'success',
                    duration: 2000
                  })
                  this.getDictIndexData()
                } else {
                  return false
                }
              })
            }
            this.dialogListVisible = false
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

      handleDetailCommand(command) {
        switch (command) {
          case 'add':
            this.dictDetailAdd();
            break;
          case 'edit':
            this.dictDetailEdit();
            break;
          case 'del':
            this.dictDetailDel();
            break;
        }
      },
      dictDetailAdd() {
        this.dialogDetailVisible = true
        this.dialogDetailName = '新增字典对照'
        this.dictDetailForm = {
          code: '',
          name: '',
          delFlag: '1',
          dictFlag: '1',
          remarks: ''
        }
      },
      dictDetailEdit() {
        getDict(this.dictDetailSelectedRow.id).then(res => {
          this.dialogDetailVisible = true
          this.dialogDetailName = '修改字典对照'
          this.dictDetailForm = res.data.data
        })
      },
      dictDetailDel() {
        this.$confirm(
          "此操作将永久删除该字典对照(字典对照值:" + this.dictDetailSelectedRow.name + "), 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          delDict(this.dictDetailSelectedRow.id).then(res => {
            if (res.data.data) {
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 2000
              })
              this.getDictData()
            } else {
              return false
            }
          })
        });
      },
      dictDetailSubmit() {
        this.$refs['dictDetailForm'].validate((valid) => {
          if (valid) {
            if (this.dialogDetailName === '新增字典对照') {
              addDict({...this.dictDetailForm, dictIndexId: this.dictIndexSelectId}).then(res => {
                if (res.data.data) {
                  this.$notify({
                    title: '成功',
                    message: '新增成功',
                    type: 'success',
                    duration: 2000
                  })
                  this.getDictData()
                } else {
                  return false
                }
              })
            } else {
              putDict({...this.dictDetailForm, dictIndexId: this.dictIndexSelectId}).then(res => {
                if (res.data.data) {
                  this.$notify({
                    title: '成功',
                    message: '修改成功',
                    type: 'success',
                    duration: 2000
                  })
                  this.getDictData()
                } else {
                  return false
                }
              })
            }
            this.dialogDetailVisible = false
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
    }
  };
</script>
<style lang="scss" scoped>
  .app-container {
    padding-top: 0;
    /deep/ .basic-container {
      .item_title {
        display: flex;
        justify-content: space-between;
        .title_dropdown {
          font-size: 12px;
          i {
            font-size: 12px !important;
            color: #3a8ee6;
            margin-right: 5px;
          }
        }
      }
      .zTree_custom {
        height: 100%;
        border: 1px solid #e5e5e5;
        border-radius: 3px;
        padding: 8px;
        margin-top: 10px;
        &:hover {
          border: 1px solid #3a8ee6;
        }
        #areaTree {
          margin-top: 0 !important;
        }
        .el-header {
          padding: 5px 0 !important;
        }
        .el-input--small .el-input__inner {
          height: 28px;
          line-height: 28px;
        }
      }
      .detail_center {
        height: 100%;
        border: 1px solid #e5e5e5;
        border-radius: 3px;
        padding: 8px;
        margin-top: 10px;
        margin-left: 20px;
        &:hover {
          border: 1px solid #3a8ee6;
        }
        /*.el-input--small .el-input__inner {*/
        /*  height: 28px;*/
        /*  line-height: 28px;*/
        /*}*/
        .el-table{
          margin-top: 5px;
        }
      }
      .detail_right {
        height: 100%;
        border: 1px solid #e5e5e5;
        border-radius: 3px;
        padding: 8px;
        margin-top: 10px;
        margin-left: 20px;
        &:hover {
          border: 1px solid #3a8ee6;
        }
        /*.el-input--small .el-input__inner {*/
        /*  height: 28px;*/
        /*  line-height: 28px;*/
        /*}*/
        .el-table{
          margin-top: 5px;
        }
      }
    }
    /deep/ .dict_type_dialog {
      .el-dialog {
        width: 400px;
        height: 60vh;
        margin-top: 20vh !important;
        .el-dialog__body {
          height: calc(100% - 113px);
          padding: 0 60px 10px 60px;
          .dialog_main {
            height: 100%;
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
            .vue-treeselect__control {
              height: 32px;
            }
          }
        }
        .el-dialog__footer {
          padding: 10px;
          text-align: center;
        }
      }
    }
    /deep/ .dict_list_dialog {
      .el-dialog {
        width: 400px;
        height: 60vh;
        margin-top: 20vh !important;
        .el-dialog__body {
          height: calc(100% - 113px);
          padding: 0 60px 10px 60px;
          .dialog_main {
            height: 100%;
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
          }
        }
        .el-dialog__footer {
          padding: 10px;
          text-align: center;
        }
      }
    }
    /deep/ .dict_detail_dialog {
      .el-dialog {
        width: 400px;
        height: 60vh;
        margin-top: 20vh !important;
        .el-dialog__body {
          height: calc(100% - 113px);
          padding: 0 60px 10px 60px;
          .dialog_main {
            height: 100%;
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
          }
        }
        .el-dialog__footer {
          padding: 10px;
          text-align: center;
        }
      }
    }
    /deep/ .drawer_title {
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

    .titleSpan {
      padding: 0;
      font-size: 14px;
      font-weight: bold;
      text-align: center;
      /*color: #3a8ee6;*/
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
  }

  .el-dropdown-menu {
    .el-dropdown-menu__item {
      color: #326cf1;
    }
    .el-dropdown-menu__item:first-child {
      color: #00B83F;
    }
    .el-dropdown-menu__item:nth-child(3) {
      color: #f78585;
    }
    .is-disabled {
      color: #bbb !important;
    }
  }
</style>
