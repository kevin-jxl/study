<template>
  <div class="app-container pull-auto">

    <el-row>
      <el-col :span="7" style='margin-top:0px;margin-right: 10px;'>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>组织架构树</span>
          </div>
          <el-tree class="filter-tree"
                   :data="treeData"
                   node-key="id"
                   highlight-current
                   :props="defaultProps"
                   :filter-node-method="filterNode"
                   @node-click="getNodeData"
                   default-expand-all>
          </el-tree>
        </el-card>
      </el-col>
      <el-col :span="16" style='margin-top:0px;'>

        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>岗位管理</span>
            <el-button style="float: right" size="small" type="primary" icon="el-icon-edit" @click="handleAdd">新增
            </el-button>
          </div>
          <el-table
            :data="tableData"
            border
            style="width: 100%">
            <el-table-column
              prop="postCode"
              label="岗位编码"
              width="80">
            </el-table-column>
            <el-table-column
              prop="postName"
              label="岗位名称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="delFlag"
              label="状态">
              <template slot-scope="scope">
                <el-tag>{{scope.row.delFlag | statusFilter}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              prop="deptName"
              label="所属部门">
            </el-table-column>
            <!--操作列-->
            <el-table-column align="center"
                             label="操作"
                             width="200">
              <template slot-scope="scope">
                <el-button
                  icon="el-icon-check"
                  size="small"
                  type="primary"
                  plain
                  @click="handleEdit(scope.row,scope.index)">编辑
                </el-button>
                <el-button
                  icon="el-icon-delete"
                  size="small"
                  type="danger"
                  plain
                  @click="handleDel(scope.row,scope.index)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <!--分页控件-->
          <div v-show="!tableLoading"
               class="pagination-container pull-right">
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
        </el-card>
      </el-col>
    </el-row>

    <!--新增或编辑修改弹出框-->
    <el-dialog :title="textMap[dialogStatus]"
               :visible.sync="dialogFormVisible">
      <el-form
        :model="form"
        ref="form"
        :rules="rules"
        label-width="100px">
        <el-form-item label="所属部门"
                      prop="deptId">
          <el-input v-model="form.deptName"
                    placeholder="请选择部门" :disabled="true"></el-input>
          <input type="hidden"
                 v-model="form.deptId"/>
        </el-form-item>
        <el-form-item label="岗位编码"
                      prop="postCode">
          <el-input v-model="form.postCode"
                    placeholder="岗位编码" clearable></el-input>
        </el-form-item>
        <el-form-item label="岗位名称"
                      prop="postName">
          <el-input v-model="form.postName"
                    placeholder="岗位名称" clearable></el-input>
        </el-form-item>
        <!--<el-form-item label="排序"-->
        <!--prop="postOder">-->
        <!--<el-input v-model="form.postOder"-->
        <!--placeholder="排序" clearable></el-input>-->
        <!--</el-form-item>-->
        <el-form-item label="状态"
                      v-if="dialogStatus == 'update'"
                      prop="delFlag">
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
        <el-button @click="cancel('form')">取 消</el-button>
        <el-button v-if="dialogStatus=='create'"
                   type="primary"
                   @click="create('form')">确 定
        </el-button>
        <el-button v-else
                   type="primary"
                   @click="update('form')">修 改
        </el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
  import {fetchList, getObj, addObj, putObj, delObj} from '@/api/syspost'
  import {mapGetters} from 'vuex'
  import waves from "@/directive/waves/index.js"; // 水波纹指令
  import {fetchTree} from '@/api/dept'

  export default {
    name: 'syspost',
    data() {
      return {
        //表格数据初始化
        tableData: [],
        page: {
          total: 0, // 总页数
          currentPage: 1, // 当前页数
          pageSize: 20 // 每页显示多少条
        },
        listQuery: {
          page: 1,
          limit: 20
        },
        tableLoading: true,
        //树形菜单初始化
        treeData: [],
        selectId: undefined,//当前选中的节点ID
        selectName: undefined,//当前选中的节点名称
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        //新增编辑表单初始化
        form: {
          delFlag: undefined,
          deptName: undefined
        },
        rules: {
          /*校验初始化*/
          postCode: [
            {
              required: true,
              message: "请输入岗位编码",
              trigger: "blur"
            }
          ],
          postName: [
            {
              required: true,
              message: "请输入岗位名称",
              trigger: "blur"
            }
          ],
          deptId: [
            {
              required: true,
              message: "请选择部门",
              trigger: "blur"
            }
          ]
        },
        statusOptions: ["0", "1"],
        /*弹窗初始化*/
        dialogFormVisible: false,
        dialogFieldsVisible: false,
        dialogStatus: "",
        textMap: {
          update: "编辑",
          create: "创建"
        }
      }
    },
    //观察函数
    watch: {},
    computed: {},
    created() {
      this.getList();
      this.getTableList();
    },
    mounted: function () {
    },
    filters: {
      statusFilter(status) {
        const statusMap = {
          0: "有效",
          1: "无效"
        };
        return statusMap[status];
      }
    },
    methods: {
      getList() {
        fetchTree(this.listQuery).then(response => {
          this.treeData = response.data
        })
      },
      getTableList() {
        console.log(this.listQuery);
        fetchList(this.listQuery).then(response => {
          this.tableData = response.data.records
          this.page.total = response.data.total
          this.tableLoading = false
        })
      },
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      getNodeData(data) {
        this.form.deptId = data.id;
        this.form.deptName = data.name;
        this.selectId = data.id;
        this.selectName = data.name;
        //根据部门ID查询数据
        this.listQuery.deptId = data.id;
        this.getTableList();
      },
      currentChange(val) {
        this.page.currentPage = val
        this.listQuery.page = val
        this.getTableList()
      },
      sizeChange(val) {
        this.page.pageSize = val
        this.listQuery.limit = val
        this.getTableList()
      },
      //表单方法
      handleAdd: function () {
        //监测有无选择部门
        if (typeof this.selectId == "undefined") {
          this.$notify({
            title: '提醒',
            message: '请先选择部门',
            type: 'warning'
          });
          return;
        }
        //重置表单
        this.resetTemp();
        this.form.deptId = this.selectId;
        this.form.deptName = this.selectName;

        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      handleEdit(row, index) {
        getObj(row.id).then(response => {
          this.form = response.data.data;
          if (row.delFlag == 0) {
            this.form.delFlag = "有效";
          } else {
            this.form.delFlag = "无效";
          }

          this.dialogFormVisible = true;
          this.dialogStatus = "update";
        });
      },
      handleDel(row, index) {

        this.$confirm(
          "是否确认删除岗位名称为为" + row.postName + ", 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          delObj(row.id)
            .then(() => {
              this.getTableList();
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
      cancel(formName) {
        this.dialogFormVisible = false;
      },
      /**
       * @title 数据更新
       * @param row 为当前的数据
       * @param index 为当前更新数据的行数
       * @param done 为表单关闭函数
       *
       **/
      update(formName) {
        const set = this.$refs;
        if (this.form.delFlag = "有效") {
          this.form.delFlag = 0;
        } else {
          this.form.delFlag = 1;
        }
        set[formName].validate(valid => {
          if (valid) {
            this.dialogFormVisible = false;
            putObj(this.form).then(() => {
              this.dialogFormVisible = false;
              this.getTableList();
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
      /**
       * @title 数据添加
       * @param row 为当前的数据
       * @param done 为表单关闭函数
       *
       **/
      create(formName) {
        const set = this.$refs;
        set[formName].validate(valid => {
          if (valid) {
            addObj(this.form).then(() => {
              this.dialogFormVisible = false;
              this.getTableList();
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
      /**
       * 刷新回调
       */
      refreshChange() {
        this.getList()
      },
      selectFileds() {
        this.dialogFieldsVisible = true
      },
      resetTemp() {
        this.form = {
          id: undefined,
          postCode: "",
          postName: "",
          deptId: "",
          deptName: "",
          delFlag: ""
        };
      }

    }
  }
</script>

<style lang="scss" scoped>
</style>

