<template>
  <div class="app-container calendar-list-container" style="height:calc(100% - 30px)">
    <basic-container style="height:100%">
      <div class="filter-container crud-menu">
        <div class="crud-menu_left">
          <!--检索框-->
          <el-input
            style="width: 258px; height:28px;"
            class="fl"
            size="small"
            placeholder="请输入岗位名称"
            suffix-icon="el-icon-search"
            v-model="listQuery.post_name"
            @keyup.enter.native="handleFilter">
          </el-input>
        </div>

        <div class="crud-menu_right clearfix">
          <el-button
            size="mini" @click="handleAdd"
            class="fr">新增
          </el-button>
        </div>
      </div>
      <!--表格控件-->
      <el-table :data="tableData"
                tooltip-effect="dark"
                :header-cell-style="tableHeaderColor"
                style="width: 100%;font-size: 12px;margin-bottom:10px;border-radius:3px;"
                :row-style="rowClass"
                :cell-style="cellClass"
                :key="tableKey"
                height="calc(100% - 80px)"
                v-loading="tableLoading"
                element-loading-text="给我一点时间"
                fit
                highlight-current-row>
        <!--索引列-->
        <el-table-column align="center" type="index" label="序号">
        </el-table-column>

        <el-table-column align="center"
                         label="岗位编码"
                         show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{scope.row.postCode}} </span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="岗位名称"
                         show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{scope.row.postName}} </span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="岗位状态"
                         show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{scope.row.postFlag | statusFilter}} </span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="岗位备注"
                         show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{scope.row.remark}} </span>
          </template>
        </el-table-column>

        <!--操作列-->
        <el-table-column align="center"
                         label="操作"
                         width="100%">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              class="button_edit"
              plain
              @click="handleEdit(scope.row,scope.index)">编辑
            </el-button>
            <el-button
              size="small"
              type="text"
              class="button_delete"
              plain
              @click="handleDel(scope.row,scope.index)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
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

    </basic-container>
    <!--新增或编辑修改弹出框-->
    <el-dialog v-if="dialogFormVisible"
               :title="textMap[dialogStatus]"
               :visible.sync="dialogFormVisible"
               width="40%">
      <div slot="title">
        <span class="preTitle"></span>
        <span class="nextTitle">{{textMap[dialogStatus]}}</span>
      </div>
      <el-form :model="form"
               :rules="rules"
               ref="form"
               size="small"
               label-width="100px">
        <el-form-item label="岗位编码"
                      prop="postCode">
          <el-input v-model="form.postCode"
                    placeholder="岗位编码"></el-input>
        </el-form-item>
        <el-form-item label="岗位名称"
                      prop="postName">
          <el-input v-model="form.postName"
                    placeholder="岗位名称"></el-input>
        </el-form-item>

        <el-form-item label="岗位状态" prop="postFlag">

          <el-switch v-if='textMap[dialogStatus] == "创建"' v-model="switchStatus" active-value="1"
                     inactive-value="0"></el-switch>
          <el-switch v-else-if='textMap[dialogStatus] == "编辑"' v-model="form.postFlag" active-value="1"
                     inactive-value="0"></el-switch>
        </el-form-item>

        <el-form-item label="备注"
                      prop="remark">
          <el-input v-model="form.remark" type="textarea"
                    placeholder="备注"></el-input>
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
    <!--表格字段多选弹出框-->
    <el-dialog title="字段选择" width="480px"
               :visible.sync="dialogFieldsVisible">
      <el-checkbox-group v-model="checkboxVal">

        <el-checkbox label="id"></el-checkbox>
        <el-checkbox label="postCode">岗位编码</el-checkbox>
        <el-checkbox label="postName">岗位名称</el-checkbox>
        <el-checkbox label="postFlag">岗位状态</el-checkbox>
        <el-checkbox label="remark">备注</el-checkbox>
      </el-checkbox-group>
    </el-dialog>
  </div>
</template>

<script>
  import {fetchList, getObj, addObj, putObj, delObj} from '@/api/syspost'
  import {mapGetters} from 'vuex'
  import waves from "@/directive/waves/index.js"; // 水波纹指令
  //表单迭代展示的列
  const defaultFormThead = [
    {key: 'postCode', label: '岗位编码'},
    {key: 'postName', label: '岗位名称'},
    {key: 'postFlag', label: '岗位状态'},
    {key: 'remark', label: '备注'}
  ];
  //默认当前选中的显示列
  const defaultFormTheadKey = [
    'postName',
    'postOder',
    'postType',
    'remark',
  ];

  export default {
    name: 'syspost',
    data() {
      return {
        /*1.表格参数初始化*/
        tableKey: 0,
        tableData: [],
        formTheadOptions: defaultFormThead,
        checkboxVal: defaultFormTheadKey, // 默认全选中
        formThead: defaultFormThead, // 默认表头 Default header
        switchStatus: "0",
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
        downloadLoading: false,
        /*表单参数初始化*/
        form: {
          /*postFlag: undefined,*/
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
          ]

        },
        /*弹窗初始化*/
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
    //观察函数
    watch: {
      checkboxVal(valArr) {
        this.formThead = this.formTheadOptions.filter(i => valArr.indexOf(i.key) >= 0)
        this.tableKey = this.tableKey + 1// 为了保证table 每次都会重渲 In order to ensure the table will be re-rendered each time
      },

      searchValue(curVal, oldVal) {
        console.log(curVal, oldVal);
      }
    },
    computed: {
      ...mapGetters(["permissions"])
    },
    filters: {
      statusFilter(status) {
        const statusMap = {
          0: '停用',
          1: '正常'
        };
        return statusMap[status];
      }
    },
    created() {
      this.getList()
    },
    mounted: function () {
    },
    computed: {
      ...mapGetters(['permissions'])
    },
    methods: {
      getList() {
        this.tableLoading = true
        fetchList(this.listQuery).then(response => {
          this.tableData = response.data.records
          this.page.total = response.data.total
          this.tableLoading = false
        })
      },
      handleFilter() {
        this.listQuery.page = 1;
        this.getList();
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
      /**
       * @title 打开新增窗口
       *
       **/
      handleAdd: function () {
        //重置表单
        if (this.$refs['form']) {
          this.$refs['form'].resetFields();
        }
        this.form = {
          postCode:'',
          postName:'',
          remark:'',
        }
        this.switchStatus = 1
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      handleEdit(row, index) {
        getObj(row.id).then(response => {
          this.form = response.data.data;
          this.dialogFormVisible = true;
          this.dialogStatus = "update";
        });
      },
      handleDel(row, index) {

        this.$confirm(
          "是否确认删除ID为" + row.id + ", 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          delObj(row.id)
            .then(() => {
              this.getList();
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
      handleDownload() {
        this.downloadLoading = true
        import('@/vendor/Export2Excel').then(excel => {
          //获取当前选中的动态列
          console.log(this.checkboxVal);
          const tHeader = this.checkboxVal
          const filterVal = this.checkboxVal
          const data = this.formatJson(filterVal, this.tableData)
          excel.export_json_to_excel({
            header: tHeader,
            data,
            filename: 'table-list'
          })
          this.downloadLoading = false
        })
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => {
          if (j === 'timestamp') {
            return parseTime(v[j])
          } else {
            return v[j]
          }
        }))
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
        set[formName].validate(valid => {
          if (valid) {
            this.dialogFormVisible = false;
            putObj(this.form).then(() => {
              this.dialogFormVisible = false;
              this.getList();
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
              this.getList();
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
      // 修改table header的背景色
      tableHeaderColor({row, column, rowIndex, columnIndex}) {
        if (rowIndex === 0) {
          return 'background-color: #fafafa;color:#999; font-size:12px;'
        }
      }
    }
  }
</script>

<style scoped>
  /*.el-header {
    padding: 0 !important;
  }*/
  .el-table {
    font-size: 12px !important;
    color: #333 !important;
  }

  .el-table--medium td, .el-table--medium th {
    padding: 5px 0 !important;
  }

  .el-button--text {
    padding: 0 !important;
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
    padding: 5px 0 !important;
  }

  .zTree_custom >>> .el-input__inner {
    height: 28px;
  }

  .in_custom >>> .el-input__inner {
    width: 258px;
    height: 28px;
  }

  .el-input >>> .el-input__inner {
    height: 28px;
    line-height: 28px;
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

