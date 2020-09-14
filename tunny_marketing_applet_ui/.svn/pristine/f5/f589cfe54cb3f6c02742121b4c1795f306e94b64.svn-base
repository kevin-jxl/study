<template>
  <div class="app-container pull-auto">
    <basic-container >
       <div class="filter-container ">
        <div class="clearfix">
          <el-form :inline="true" v-model="formInline" class="demo-form-inline">
            <el-form-item label="日志类型">
              <el-select v-model="formInline.type" placeholder="活动区域" clearable size="mini">
                <el-option label="正常" value="0"></el-option>
                <el-option label="异常" value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="日志范围">
             <div class="block">
                <el-date-picker
                  v-model="formInline.timeValue"
                  type="daterange"
                  size="mini"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit" size="mini" class="log_query_button">查询</el-button>
            </el-form-item>
          </el-form>

        </div>
      </div>
      <!-- 表格 -->
      <el-table :data="tableData"
                tooltip-effect="dark"
                :header-cell-style="tableHeaderColor"
                style="width: 100%;font-size: 12px;margin-bottom:5px;border-radius:3px;"
                :row-style="rowClass"
                :cell-style="cellClass"
                height="calc(100% - 80px)">
        <el-table-column align="center" type="index" width="50"
                          label="序号">
        </el-table-column>

        <el-table-column align="center"
                          label="类型"
                          prop="type"
                          show-overflow-tooltip>
          <template slot-scope="scope">
            {{scope.row.type | typeFilter}}
          </template>
        </el-table-column>

        <el-table-column align="center"
                          label="请求接口"
                          prop="requestUri"
                          show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row?scope.row.requestUri:'' }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                          label="IP地址"
                          prop="remoteAddr">
          <template slot-scope="scope">
            <span>{{ scope.row?scope.row.remoteAddr:'' }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                          label="请求方式"
                          prop="method"
                          show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row?scope.row.method:'' }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center"
                          label="客户端"
                          prop="serviceId"
                          show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row?scope.row.serviceId:'' }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                          label="请求时间"
                          prop="time">
          <template slot-scope="scope">
            <span>{{ scope.row?scope.row.time:'' }}</span>
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
                        class="button_delete"
                        @click="deletes(scope.row)">删除
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

    </basic-container>
  </div>
</template>

<script>
  import { fetchList, delObj, addObj, getObj, putObj } from '@/api/log'
  import { tableOption } from '@/const/crud/log'
  import { mapGetters } from 'vuex'
import { get } from 'http';
  export default {
    name: 'log',
    data() {
      return {
        tableData: [],
        page: {
          total: 0, // 总页数
          currentPage: 1, // 当前页数
          pageSize: 20 // 每页显示多少条
        },
        listQuery: {
          page: 1,
          limit: 20,
          type: undefined,
          roleName:undefined,
          startTime:undefined,
          endTime:undefined,
        },
        tableLoading: false,
        tableOption: tableOption,
        tableKey: 0,
        rowClass: {
          height: '25px'
        },
        cellClass: {
          padding: '7px 0px'
        },
        listLoading: true,
        total: null,
        options:[{
          value: '选项1',
          label: '正常'
        }, {
          value: '选项2',
          label: '异常'
        }, {
          value: '选项3',
          label: '创建时间'
        }],
        formInline: {
          timeValue:[],
          type : '',
        }
      }
    },
    //过滤器
    filters: {
      //状态过滤器
      typeFilter(status) {
        const statusType = {
          0: "正常",
          1: "异常",
        };
        return statusType[status];
      },
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
        this.listQuery.orderByField = 'create_time'
        this.listQuery.isAsc = false
        let params = this.listQuery
        params.type = this.formInline.type;
        if(this.formInline.timeValue&&this.formInline.timeValue.length>0){
          params.startTime = this.formInline.timeValue[0];
          params.endTime = this.formInline.timeValue[1];
        }else{
          params.startTime = '';
          params.endTime = '';
        }
        fetchList(params).then(response => {
          this.tableData = response.data.records
          this.total = response.data.total
          this.tableLoading = false
          this.listLoading = false
        })
      },
      currentChange(val) {
        this.listQuery.page = val
        this.getList()
      },
      sizeChange(val) {
        this.listQuery.limit = val
        this.getList()
      },
      /**
       * 搜索回调
       */
      searchChange(form) {
        this.listQuery.type = form.type
        this.getList()
      },
      /**
       * 刷新回调
       */
      refreshChange() {
        this.getList()
      },
      //搜索框回车事件
      handleFilter () {
        // this.listQuery.page = 1;
        this.getList();
      },
      onSubmit() {
        this.getList();
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
      //分页查询开始页
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      },
      //删除
      deletes(row) {
        console.log(row);
        this.$confirm(
          '是否确认删除ID为"' + row.id + '"的日志?',
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
    }
  }
</script>

<style lang="scss"  scoped>
  .app-container{
    padding: 10px 20px;
    .basic-container{
      height: 100%;
    }
    /deep/ .filter-container{
      padding-bottom: 0;
      .el-form-item{
        margin-bottom: 5px;
        .log_query_button{
          position: relative;
          top: -1px;
        }
      }
    }
  }


  .el-table {
    border: 1px solid #e5e5e5;
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
  .elselect{
    width: 250px!important;
    height: 32px!important;
  }

</style>

