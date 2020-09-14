<template>
  <div class="app-container pull-auto">
    <basic-container>
      <div class="filter-container ">
        <div class="clearfix">
          <!--检索框-->
          <el-input @keyup.enter.native="handleFilter"
                    style="width: 250px;"
                    class="filter-item fl"
                    size="small"
                    placeholder="请输入表名称"
                    v-model="listQuery.tableName">
              <i slot="suffix" class="el-input__icon el-icon-search" @click.stop="handleFilter"></i>
          </el-input>
        </div>
      </div>

      <!-- 表格 -->
      <el-table :data="tableData"
                tooltip-effect="dark"
                :header-cell-style="tableHeaderColor"
                style="width: 100%;font-size: 12px;margin-bottom:15px;border-radius:3px;"
                :row-style="rowClass"
                :cell-style="cellClass"
                height="calc(100% - 80px)"
      >
        <el-table-column align="center" type="index" width="50"
                          label="序号">
        </el-table-column>

        <el-table-column align="center"
                          label="表名称"
                          prop="tableName"
                          show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row?scope.row.tableName:'' }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                          label="表注释"
                          prop="tableComment"
                          show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row?scope.row.tableComment:'' }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                          label="索引"
                          prop="engine">
          <template slot-scope="scope">
            <span>{{ scope.row?scope.row.engine:'' }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center"
                          label="创建时间"
                          prop="createTime">
          <template slot-scope="scope">
            <span>{{ scope.row?scope.row.createTime:'' }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center"
                          label="操作"
                          width="100%">
          <template slot-scope="scope">
            <el-button  size="small"
                        type="text"
                        plain
                        class="button_create"
                        @click="handleDown(scope.row,scope.index)">生成
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

      <!--新增用户弹出框-->
      <el-dialog :visible.sync="dialogFormVisible">
        <!--弹窗框标题头-->
        <div slot="title">
          <span class="preTitle"></span>
          <span class="nextTitle">代码生成</span>
        </div>
        <!--弹窗内容-->
        <el-form label-width="100px"
                 label-position="right"
                 :option="formOption"
                 ref="formData"
                 v-model="formData">
          <el-row>
            <el-col :span="11">
              <el-form-item label="表名称"
                            prop="tableName"
                            size="small"
              >
                <el-input v-model="formData.tableName"
                          placeholder="请输入表名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="包名"
                            prop="packageName"
                            size="small"
              >
                <el-input v-model="formData.packageName"
                          placeholder="可为空，加载系统默认配置"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="11">
              <el-form-item label="作者"
                            prop="author"
                            size="small"
              >
                <el-input v-model="formData.author"
                          placeholder="可为空，加载系统默认配置"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="模块"
                            prop="moduleName"
                            size="small"
              >
                <el-input v-model="formData.moduleName"
                          placeholder="可为空，加载系统默认配置"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="11">
              <el-form-item label="表前缀"
                            prop="tablePrefix"
                            size="small"
              >
                <el-input v-model="formData.tablePrefix"
                          placeholder="请输入表名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="注释"
                            prop="comments"
                            size="small"
              >
                <el-input v-model="formData.comments"
                          placeholder="可为空，加载表备注"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button type="primary"
                     @click="gen()" size="small">生成
          </el-button>
        </div>
      </el-dialog>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, handleDown } from '@/api/gen'
import { tableOption, formOption } from '@/const/crud/gen'
import { mapGetters } from 'vuex'
export default {
  name: 'client',
  data() {
    return {
      tableData: [],
      formData: {},
      box: false,
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      listQuery: {
        page: 1,
        limit: 20,
        tableName: undefined
      },
      tableLoading: false,
      tableOption: tableOption,
      formOption: formOption,
      tableKey: 0,
      rowClass: {
        height: '25px'
      },
      cellClass: {
        padding: '7px 0px'
      },
      listLoading: true,
      total: null,
      dialogFormVisible: false,
      textMap: {
        produce: "生成",
      },
      //初始化form表单
      form: {
        //表名称
        tableName: undefined,
        //表注释
        tableComment: undefined,
        //索引
        engine: undefined,
        //创建时间
        createTime: undefined,
        //包名
        packageName: undefined,
        //作者
        author: undefined,
        //模块
        moduleName: undefined,
        //表前缀
        tablePrefix: undefined,
        //注释,
        comments: undefined,
      },
      //表单校验

    }
  },
  created() {
    this.getList()
  },
  mounted: function() { },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    getList() {
      this.tableLoading = true
      fetchList(this.listQuery).then(response => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.tableLoading = false
        this.listLoading = false
      })
    },
    resetForm() {
      this.form = {
        //表名称
        tableName: undefined,
        //表注释
        tableComment: undefined,
        //索引
        engine: undefined,
        //创建时间
        createTime: undefined,
        //包名
        packageName: undefined,
        //作者
        author: undefined,
        //模块
        moduleName: undefined,
        //表前缀
        tablePrefix: undefined,
        //注释,
        comments: undefined,
      }
    },
    currentChange(val) {
      this.page.currentPage = val
      this.listQuery.page = val
      this.getList()
    },
    sizeChange(val) {
      this.page.pageSize = val
      this.listQuery.limit = val
      this.getList()
    },
    handleDown: function(row, index) {
      this.formData.tableName = row.tableName
      // this.box = true
       this.dialogFormVisible =  true;
    },
    /**
     * 刷新回调
     */
    refreshChange() {
      this.getList()
    },
    searchChange(form) {
      this.listQuery.tableName = form.tableName
      this.getList()
    },
    gen(form) {
      console.log(this.formData)
      handleDown(this.formData).then(response => {
        this.box = true
      })
    },
    // 修改table header的背景色
    tableHeaderColor({row, column, rowIndex, columnIndex}) {
      if (rowIndex === 0) {
        return 'background-color: #fafafa;color:#999; font-size:12px;'
      }
    },
    //分页总条数
    handleSizeChange(val) {
      this.listQuery.limit = val;
      this.getList();
    },
    //检索
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    //分页查询开始页
    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.getList();
    },
    //取消
    cancel(formName) {
      this.dialogFormVisible = false;
      this.$refs[formName].resetFields();
    },
    //创建
    create(formName) {
      const set = this.$refs;
      if (this.form.deptList) {
        this.form.deptId = this.form.deptList[this.form.deptList.length - 1]
      }
      console.log(this.form.postId);
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
  }
}
</script>

<style lang="scss" scoped>
  .app-container{
    padding: 10px 20px;
    .basic-container{
      height: 100%;
      /deep/ .filter-container{
        .filter-item{
          margin-bottom: 0;
        }
      }
    }
  }

  
  .el-table {
    border: 1px solid #e5e5e5;
  }
  .button_create {
    padding: 0;
    border: none;
    color: #66B1FF;
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
</style>

