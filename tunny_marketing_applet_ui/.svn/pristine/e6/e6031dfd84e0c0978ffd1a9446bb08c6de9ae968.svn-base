<template>
  <div class="individuation_main">
    <el-tabs v-model="activeName" @tab-click="tabClick">
      <el-tab-pane label="权限标签设置" name="first">
        <el-table
          :data="permissionTableData"
          v-loading="permissionLoading"
          :cell-style="cellStyle"
          :header-cell-style="headerCellStyle"
          height="calc(100% - 35px)"
          style="width: 100%"
          class="permission_table"
        >
          <el-table-column align="center" type="index"
                           label="序号" min-width="5">
          </el-table-column>
          <el-table-column
            prop="menuName"
            align="center"
            min-width="10"
            label="所属菜单"
            width="180"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="powerName"
            align="center"
            min-width="10"
            label="权限名称"
            width="180"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="powerTable"
            align="center"
            min-width="10"
            label="权限表"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="powerFiled"
            align="center"
            min-width="10"
            label="权限字段"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="powerValue"
            align="center"
            min-width="10"
            label="权限值"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="powerControl"
            align="center"
            min-width="10"
            label="权限控制"
            show-overflow-tooltip>
            <template slot-scope="scope">
              {{scope.row.powerControl | powerControlFilter}}
            </template>
          </el-table-column>
          <el-table-column
            prop="powerFlag"
            align="center"
            min-width="10"
            label="权限状态"
            show-overflow-tooltip>
            <template slot-scope="scope">
              {{scope.row.powerFlag | powerFlagFilter}}
            </template>
          </el-table-column>
          <el-table-column
            prop="powerDesc"
            align="center"
            min-width="10"
            label="权限描述"
            show-overflow-tooltip>
            <template slot-scope="scope">
              {{scope.row.powerDesc}}
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            align="center"
            min-width="10"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="permissionEdit(scope.row)">编辑</el-button>
              <el-button type="text" size="small" class="delete_button" @click="permissionDelete(scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="permission_pagination">
          <el-pagination @size-change="handlePermissionSizeChange"
                         @current-change="handlePermissionCurrentChange"
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
      </el-tab-pane>
      <el-tab-pane label="虚拟岗位配置" name="second">
        <el-table
          :data="deptTableData"
          v-loading="deptLoading"
          :cell-style="cellStyle"
          :header-cell-style="headerCellStyle"
          height="calc(100% - 35px)"
          style="width: 100%"
          class="permission_table"
        >
          <el-table-column align="center" type="index"
                           label="序号" min-width="5">
          </el-table-column>
          <el-table-column
            prop="postCode"
            align="center"
            min-width="10"
            label="岗位编码"
            width="180"
            show-overflow-tooltip>
            <template slot-scope="scope">
              {{scope.row.postCode}}
            </template>
          </el-table-column>
          <el-table-column
            prop="postName"
            align="center"
            min-width="10"
            label="岗位名称"
            width="180"
            show-overflow-tooltip>
            <template slot-scope="scope">
              {{scope.row.postName}}
            </template>
          </el-table-column>
          <el-table-column
            prop="postFlag"
            align="center"
            min-width="10"
            label="岗位状态"
            width="180"
            show-overflow-tooltip>
            <template slot-scope="scope">
              {{scope.row.postFlag | postFlagFilter}}
            </template>
          </el-table-column>
          <el-table-column
            prop="remark"
            align="center"
            min-width="10"
            label="岗位备注"
            show-overflow-tooltip>
            <template slot-scope="scope">
              {{scope.row.remark}}
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            align="center"
            min-width="10"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="deptEdit(scope.row)">编辑</el-button>
              <el-button type="text" size="small" class="delete_button" @click="deptDelete(scope.row)">删除</el-button>
              <el-button type="text" size="small" @click="deptTagsClick(scope.row)">数据标签</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination-container">
          <el-pagination @size-change="handleDeptSizeChange"
                         @current-change="handleDeptCurrentChange"
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
      </el-tab-pane>
    </el-tabs>
    <el-button
      class="return_button" size="small" @click="add">新增
    </el-button>
    <el-dialog
      title="提示"
      v-if="permissionDialog"
      :visible.sync="permissionDialog"
      class="permission_dialog"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">{{permissionDialogName}}</span>
        <el-popover
          placement="bottom"
          trigger="click">
          <i class="el-icon-info title_info" slot="reference" title="帮助"></i>
          <div>
            <div>所属菜单：所属菜单</div>
            <div>权限名称：权限名称</div>
          </div>
        </el-popover>
      </div>
      <div class="dialog_main">
        <el-form ref="permissionForm"
                 :model="permissionForm"
                 :rules="permissionRules"
                 label-width="80px"
                 hide-required-asterisk="true"
                 label-position="right"
        >
          <el-form-item label="所属菜单" prop="menuId">
            <el-select v-model="permissionForm.menuId" placeholder="请选择活动区域" size="small" clearable filterable>
              <el-option
                v-for="item in permissionMenuOptions"
                :key="item.menuId"
                :label="item.name"
                :value="item.menuId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="权限名称" size="small" prop="powerName">
            <el-input v-model="permissionForm.powerName"></el-input>
          </el-form-item>
          <el-form-item label="权限表" size="small" prop="powerTable">
            <el-input v-model="permissionForm.powerTable"></el-input>
          </el-form-item>
          <el-form-item label="权限字段" size="small" prop="powerFiled">
            <el-input v-model="permissionForm.powerFiled"></el-input>
          </el-form-item>
          <el-form-item label="权限值" size="small" prop="powerValue">
            <el-input v-model="permissionForm.powerValue"></el-input>
          </el-form-item>
          <el-form-item label="权限控制" size="small" prop="powerControl">
            <el-select v-model="permissionForm.powerControl" placeholder="请选择权限控制" size="small">
              <el-option label="等于" value="0"></el-option>
              <el-option label="不等于" value="1"></el-option>
              <el-option label="包含" value="2"></el-option>
              <el-option label="不包含" value="3"></el-option>
              <el-option label="大于" value="4"></el-option>
              <el-option label="大于等于" value="5"></el-option>
              <el-option label="小于" value="6"></el-option>
              <el-option label="小于等于" value="7"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="权限状态" size="small" prop="powerFlag">
            <el-switch v-model="permissionForm.powerFlag"
                       active-value="1"
                       inactive-value="0"></el-switch>
          </el-form-item>
          <el-form-item label="描述">
            <el-input type="textarea" v-model="permissionForm.powerDesc" resize="none"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="permissionDialog = false" size="small">取 消</el-button>
        <el-button type="primary" @click="permissionSubmit" size="small">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="提示"
      v-if="deptDialog"
      :visible.sync="deptDialog"
      class="dept_dialog"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">{{deptDialogName}}</span>
      </div>
      <div class="dialog_main">
        <el-form ref="deptForm"
                 :model="deptForm"
                 :rules="deptRules"
                 label-width="80px"
                 label-position="right"
        >
          <el-form-item label="岗位编码" size="small" prop="postCode">
            <el-input v-model="deptForm.postCode"></el-input>
          </el-form-item>
          <el-form-item label="岗位名称" size="small" prop="postName">
            <el-input v-model="deptForm.postName"></el-input>
          </el-form-item>
          <el-form-item label="岗位状态" size="small" prop="postFlag">
            <el-switch v-model="deptForm.postFlag"
                       active-value="1"
                       inactive-value="0"></el-switch>
          </el-form-item>
          <el-form-item label="岗位备注">
            <el-input type="textarea" v-model="deptForm.remark" resize="none"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deptDialog = false" size="small">取 消</el-button>
        <el-button type="primary" @click="deptSubmit" size="small">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="提示"
      v-if="deptDialogTags"
      :visible.sync="deptDialogTags"
      class="dept_dialog_tags"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">数据标签</span>
      </div>
      <div class="dialog_main">
        <el-row>
          <el-col :span="24">
            <el-form :inline="true" :model="deptTagForm" class="demo-form-inline">
              <el-form-item label="所属菜单">
                <el-select v-model="deptTagForm.menuId" placeholder="所属菜单" size="small" clearable filterable>
                  <el-option
                    v-for="item in permissionMenuOptions"
                    :key="item.menuId"
                    :label="item.name"
                    :value="item.menuId">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="权限名称">
                <el-input v-model="deptTagForm.powerName" placeholder="权限名称" size="small"></el-input>
              </el-form-item>
              <el-form-item label="权限表">
                <el-input v-model="deptTagForm.powerTable" placeholder="权限表" size="small"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onDeptTagSearch" size="small">查询</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-table
          ref="deptTagForm"
          :data="permissionTagsData"
          :cell-style="cellStyle"
          :header-cell-style="headerCellStyle"
          height="calc(100% - 46px)"
          style="width: 100%"
          class="dialog_tags_table"
          @selection-change="deptTagSelect"
          @row-click="deptTagRowClick"
          :select-on-indeterminate="false"
        >
          <el-table-column fixed="left"
                           type="selection"
                           :selectable="tagTableSelect">
          </el-table-column>
          <el-table-column align="center" type="index"
                           label="序号" min-width="5">
          </el-table-column>
          <el-table-column
            prop="menuName"
            align="center"
            min-width="10"
            label="所属菜单"
            width="180">
          </el-table-column>
          <el-table-column
            prop="powerName"
            align="center"
            min-width="10"
            label="权限名称"
            width="180">
          </el-table-column>
          <el-table-column
            prop="powerTable"
            align="center"
            min-width="10"
            label="权限表">
          </el-table-column>
          <el-table-column
            prop="powerFiled"
            align="center"
            min-width="10"
            label="权限字段">
          </el-table-column>
          <el-table-column
            prop="powerValue"
            align="center"
            min-width="10"
            label="权限值">
          </el-table-column>
          <el-table-column
            prop="powerControl"
            align="center"
            min-width="10"
            label="权限控制">
            <template slot-scope="scope">
              {{scope.row.powerControl | powerControlFilter}}
            </template>
          </el-table-column>
          <el-table-column
            prop="powerFlag"
            align="center"
            min-width="10"
            label="权限状态">
            <template slot-scope="scope">
              {{scope.row.powerFlag | powerFlagFilter}}
            </template>
          </el-table-column>
        </el-table>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deptDialogTags = false" size="small">取 消</el-button>
        <el-button type="primary" @click="deptTagSubmit" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import {
    permissionSearch, permissionPost, permissionMenuSearch,
    permissionUpdate, permissionById,
    permissionDel, deptSearch, deptPost, deptUpdate,
    deptById, deptDel, deptTagsSearch, deptTagsUpdate
  } from '@/api/individuation'
  import {throttle} from '../../../util/util'

  export default {
    name: 'individuation',
    data() {
      return {
        activeName: 'first',
        permissionLoading: false,
        deptLoading: false,
        permissionTableData: [],
        deptTableData: [],
        permissionTagsData: [],
        currentDeptTag: '',
        permissionDialog: false,
        deptDialog: false,
        deptDialogTags: false,
        permissionDialogName: '',
        deptDialogName: '',
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
        currentPage: 1,
        total: 18,
        pageSize: 20,
        permissionMenuOptions: [],
        permissionForm: {
          menuId: '',
          powerName: '',
          powerTable: '',
          powerFiled: '',
          powerValue: '',
          powerControl: '',
          powerFlag: '1',
          powerDesc: '',
        },
        deptForm: {
          postCode: '',
          postName: '',
          postFlag: "1",
          remark: ''
        },
        deptTagForm: {
          menuId: '',
          powerName: '',
          powerTable: '',
        },
        deptTagsSelection: [],
        deptSelectTags: [],
        deptSelectTagIds: [],
        checkedIds: [],
        checkedNewIds: [],
        permissionRules: {
          menuId: [
            {required: true, message: '请选择活动名称', trigger: 'blur'},
          ],
          powerName: [
            {required: true, message: '请输入权限名称', trigger: 'blur'},
          ],
          powerTable: [
            {required: true, message: '请输入权限表', trigger: 'blur'},
          ],
          powerFiled: [
            {required: true, message: '请输入权限字段', trigger: 'blur'},
          ],
          powerValue: [
            {required: true, message: '请输入权限值', trigger: 'blur'},
          ],
          powerControl: [
            {required: true, message: '请输入权限控制', trigger: 'blur'},
          ],
          powerFlag: [
            {required: true, message: '请输入权限状态', trigger: 'blur'},
          ],
        },
        deptRules: {
          postCode: [
            {required: true, message: '请选择岗位编码', trigger: 'blur'},
          ],
          postName: [
            {required: true, message: '请选择岗位名称', trigger: 'blur'},
          ],
          postFlag: [
            {required: true, message: '请选择岗位状态', trigger: 'blur'},
          ],
        }
      }
    },
    computed: {
      ...mapGetters(['permissions'])
    },
    filters: {
      postFlagFilter(item) {
        let statusMap = {
          0: "停用",
          1: "正常"
        };
        return statusMap[item];
      },
      powerControlFilter(item) {
        let statusMap = {
          0: "等于",
          1: "不等于",
          2: "包含",
          3: "不包含",
          4: "大于",
          5: "大于等于",
          6: "小于",
          7: "小于等于",
        };
        return statusMap[item];
      },
      powerFlagFilter(item) {
        let statusMap = {
          0: "无效",
          1: "有效"
        };
        return statusMap[item];
      },
    },
    mounted() {
      this.permissionSearch()
    },
    methods: {
      tabClick(tab) {
        switch (tab.name) {
          case 'first':
            this.permissionSearch();
            break;
          case 'second':
            this.deptSearch();
            break;
        }
      },
      permissionSearch(pageSize, currentPage) {
        // this.showMainLoading()
        this.permissionLoading = true
        permissionSearch({
          parentId: -1,
          page: currentPage || 1,
          limit: pageSize || 10
        }).then(res => {
          this.permissionTableData = res.data.records
          this.permissionLoading = false
          this.currentPage = res.data.current
          this.total = res.data.total
          this.pageSize = res.data.size
        })
        // this.hideMainLoading()
      },
      permissionMeunSearch() {
        permissionMenuSearch().then(res => {
          this.permissionMenuOptions = res.data
        })
      },
      deptSearch(pageSize, currentPage) {
        this.deptLoading = true
        deptSearch({
          parentId: -1,
          page: currentPage || 1,
          limit: pageSize || 10
        }).then(res => {
          this.deptTableData = res.data.records
          this.deptLoading = false
          this.currentPage = res.data.current
          this.total = res.data.total
          this.pageSize = res.data.size
        })
      },
      add: throttle(function () {
        if (this.activeName === 'first') {
          this.permissionDialogName = '新增'
          this.permissionDialog = true
          this.permissionForm = {
            menuId: '',
            powerName: '',
            powerTable: '',
            powerFiled: '',
            powerValue: '',
            powerControl: '',
            powerFlag: '1',
            powerDesc: '',
          }

          this.permissionMeunSearch()
          this.$refs['permissionForm'].resetFields()
        } else {
          this.deptDialogName = '新增'
          this.deptDialog = true
          this.deptForm = {
            postCode: '',
            postName: '',
            postFlag: "1",
            remark: ''
          }
          this.$refs['deptForm'].resetFields()
        }
      }, 1000),
      deptTagsClick(row) {
        this.deptTagForm = {
          menuId: '',
          powerName: '',
          powerTable: '',
        }
        deptTagsSearch({...this.deptTagForm, posDummyId: row.id}).then(res => {
          this.permissionMeunSearch()
          this.deptDialogTags = true
          this.checkedIds = res.data.checkeds
          this.checkedNewIds = []
          this.deptTagsSelection = []
          this.deptSelectTags = []
          this.deptSelectTagIds = []
          this.currentDeptTag = row.id
          this.permissionTagsData = res.data.labels
          this.deptTagForm = {
            menuId: '',
            powerName: '',
            powerTable: ''
          }
          if (this.checkedIds.length > 0) {
            this.$nextTick(() => {
              this.permissionTagsData.filter(item => {
                return this.checkedIds.includes(item.id)
              }).forEach(item => {
                this.$refs['deptTagForm'].toggleRowSelection(item, true)
              })
            })
          }
        })
      },
      permissionEdit(row) {
        permissionById(row.id).then(res => {
          this.permissionMeunSearch()
          this.permissionForm = res.data.data
          this.permissionDialogName = '编辑'
          this.permissionDialog = true
        })
        // this.permissionForm = {...row}
      },
      deptEdit(row) {
        deptById(row.id).then(res => {
          this.deptDialogName = '编辑'
          this.deptDialog = true
          this.deptForm = res.data.data
        })
        // this.deptForm = {...row}
      },
      tagTableSelect(row, index) {
        return !this.deptSelectTags.includes(row.powerFiled) || this.deptSelectTagIds.includes(row.id)
      },
      deptTagSelect(selection) {
        this.deptTagsSelection = selection
        this.deptSelectTags = selection.map(item => item.powerFiled)
        this.deptSelectTagIds = selection.map(item => item.id)
        this.checkedIds = this.checkedNewIds.concat(this.deptSelectTagIds)
      },
      deptTagRowClick(row, event, column) {
        if (this.tagTableSelect(row)) {
          this.$refs['deptTagForm'].toggleRowSelection(row)
        }
      },
      handlePermissionSizeChange(val) {
        this.permissionSearch(val, this.currentPage)
      },
      handlePermissionCurrentChange(val) {
        this.permissionSearch(this.pageSize, val)
      },
      handleDeptSizeChange(val) {
        this.deptSearch(val, this.currentPage)
      },
      handleDeptCurrentChange(val) {
        this.deptSearch(this.pageSize, val)
      },
      onDeptTagSearch: throttle(function () {
        deptTagsSearch({...this.deptTagForm, posDummyId: this.currentDeptTag}).then(res => {
          this.deptTagsSelection = []
          this.permissionTagsData = res.data.labels

          let ids = this.permissionTagsData.map(item => item.id)
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
              this.permissionTagsData.filter(item => {
                return selectChecks.includes(item.id)
              }).forEach(item => {
                this.$refs['deptTagForm'].toggleRowSelection(item, true)
              })
            })
          }
        })
      }, 1000),
      permissionSubmit() {
        this.$refs['permissionForm'].validate((valid) => {
          if (valid) {
            // this.permissionDialog = false
            // let loading = this.showWindowLoading()
            if (this.permissionDialogName === '新增') {
              permissionPost(this.permissionForm).then(res => {
                if (res.data.data) {
                  this.$notify({
                    title: '成功',
                    message: '新增成功',
                    type: 'success',
                    duration: 2000
                  })
                  this.permissionSearch()
                } else {
                  return false
                }
              })
            } else {
              permissionUpdate(this.permissionForm).then(res => {
                if (res.data.data) {
                  this.$notify({
                    title: '成功',
                    message: '修改成功',
                    type: 'success',
                    duration: 2000
                  })
                  this.permissionSearch()
                } else {
                  return false
                }
              })
            }
            // this.hideWindowLoading(loading)
            this.permissionDialog = false
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      deptSubmit() {
        this.$refs['deptForm'].validate((valid) => {
          if (valid) {
            this.deptDialog = false
            if (this.deptDialogName === '新增') {
              deptPost(this.deptForm).then(res => {
                if (res.data.data) {
                  this.$notify({
                    title: '成功',
                    message: '新增成功',
                    type: 'success',
                    duration: 2000
                  })
                  this.deptSearch()
                } else {
                  return false
                }
              })
            } else {
              deptUpdate(this.deptForm).then(res => {
                if (res.data.data) {
                  this.$notify({
                    title: '成功',
                    message: '修改成功',
                    type: 'success',
                    duration: 2000
                  })
                  this.deptSearch()
                } else {
                  return false
                }
              })
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      deptTagSubmit() {
        this.deptDialogTags = false
        let checks = this.checkedIds.join(',')
        deptTagsUpdate({
          posdummyId: this.currentDeptTag,
          powerIds: checks
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
      },
      permissionDelete(row) {
        this.$confirm(
          "此操作将永久删除该权限(权限名:" + row.powerName + "), 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          permissionDel(row.id).then(res => {
            if (res.data.data) {
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 2000
              })
              this.permissionSearch()
            } else {
              return false
            }
          })
        });
      },
      deptDelete(row) {
        this.$confirm(
          "此操作将永久删除该岗位(岗位名:" + row.postName + "), 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          deptDel(row.id).then(res => {
            if (res.data.data) {
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 2000
              })
              this.deptSearch()
            } else {
              return false
            }
          })
        });
      }
    }
  }
</script>
<style lang="scss" scoped>
  .individuation_main {
    padding: 10px 30px;
    .el-tabs {
      height: 100%;
      /deep/ .el-tabs__header {
        .el-tabs__item {
          font-size: 12px;
        }
      }
      /deep/ .el-tabs__content {
        height: calc(100% - 55px);
        .el-tab-pane {
          height: 100%;
          .permission_table {
            font-size: 12px;
            border: 1px solid #e5e5e5;
            border-radius: 5px;
            .cell {
              line-height: 22px;
              .el-button {
                padding: 0;
              }
            }
            .delete_button {
              color: #F78989;
            }
          }
          .permission_pagination {
            margin-top: 3px;
          }
        }
      }
    }

    .return_button {
      position: absolute;
      top: 10px;
      right: 35px;
    }

    .permission_dialog {
      /deep/ .el-dialog {
        width: 500px;
        height: 80vh;
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
        }
        .el-dialog__footer {
          padding: 10px;
        }
      }
    }
    .dept_dialog {
      /deep/ .el-dialog {
        width: 500px;
        height: 50vh;
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
          padding: 10px 60px;
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
        .el-dialog__footer {
          padding: 10px;
        }
      }
    }
    .dept_dialog_tags {
      /deep/ .el-dialog {
        width: 80vw;
        height: 80vh;
        margin-top: 10vh !important;
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
          padding: 10px 60px;
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
                line-height: 22px;
                .el-button {
                  padding: 0;
                }
              }
              .el-table__fixed {
                th.el-table-column--selection {
                  display: none;
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
    /deep/ .title_info {
      position: absolute;
      top: 30px;
      right: 50px;
      color: #00a0e9;
    }
  }
</style>
