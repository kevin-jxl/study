<template>
  <div class="app-container calendar-list-container">
    <basic-container>
      <div class="filter-container">
        <!--检索框-->
        <el-input @keyup.enter.native="handleFilter"
                  style="width: 200px;"
                  class="filter-item"
                  size="small"
                  placeholder="任务名称"
                  v-model="listQuery.description">
        </el-input>
        <el-button class="filter-item"
                   type="primary"
                   v-waves
                   icon="el-icon-search"
                   size="small"
                   @click="handleFilter">搜索</el-button>
        <el-button
                   class="filter-item"
                   style="margin-left: 10px;"
                   @click="handleCreate"
                   type="primary"
                   size="small"
                   icon="el-icon-edit"
        >新增</el-button>

      </div>
      <!-- 表格控件-->
      <el-table :key='tableKey'
                :data="list"
                v-loading="listLoading"
                element-loading-text="给我一点时间"
                border
                stripe
                fit
                highlight-current-row
                style="width: 99%">

        <el-table-column align="center" type="index" width="50"
                         label="序号">
        </el-table-column>

        <el-table-column align="center"
                         label="任务名称">
          <template slot-scope="scope">
            <span>
               {{scope.row.description}}
            </span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="任务类"
                         show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{scope.row.jobClassName}} </span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="所属分组"
                         show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{scope.row.jobGroup}} </span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="时间规则">
          <template slot-scope="scope">
            <template v-if="scope.row.edit">
              <el-input v-model="scope.row.cronExpression" class="edit-input" size="small"/>
              <el-button class="cancel-btn" size="small" icon="el-icon-refresh" type="warning" @click="cancelEdit(scope.row)">取消</el-button>
            </template>
            <span v-else>{{scope.row.cronExpression}}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="失败告警">

          <template slot-scope="scope">

            <span>{{scope.row.isAlarm | alarmFilter}}</span>

          </template>
        </el-table-column>


        <el-table-column align="center"
                         label="告警电话"
                         show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{scope.row.alarmPhones}} </span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="告警邮箱"
                         show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{scope.row.alarmMails}} </span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="上次执行时间">
          <template slot-scope="scope">
            <span>{{scope.row.prevFireTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}')}}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="下次执行时间">
          <template slot-scope="scope">
            <span>{{scope.row.nextFireTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}')}}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         class-name="status-col"
                         label="任务状态">
          <template slot-scope="scope">
            <el-tag>{{scope.row.triggerState | statusFilter}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="操作"
                         width="200">
          <template slot-scope="scope">

            <el-button icon="el-icon-check" v-if="scope.row.triggerState=='PAUSED'" size="small" type="success"
                       @click="handleModifyStatus(scope.row,'ACQUIRED')"> 运行
            </el-button>

            <el-button icon="el-icon-check" v-if="scope.row.triggerState!='PAUSED'" size="small"
                       @click="handleModifyStatus(scope.row,'PAUSED')">
              暂停
            </el-button>

            <el-button
              icon="el-icon-delete"
              size="small"
              type="danger"
              plain
              @click="deletes(scope.row)">删除
            </el-button>
          </template>
        </el-table-column>


      </el-table>
      <!--分页控件-->
      <div v-show="!listLoading"
           class="pagination-container">
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page.sync="listQuery.page"
                       :page-sizes="[10,20,30,50]"
                       :page-size="listQuery.limit"
                       class="pull-right"
                       background
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
      </div>
    </basic-container>


    <!--新增任务弹出框-->
    <el-dialog :title="textMap[dialogStatus]"
               :visible.sync="dialogFormVisible">
      <el-form :model="form"
               :rules="rules"
               ref="form"
               label-width="100px">
        <el-form-item label="任务名称"
                      prop="description">
          <el-input v-model="form.description"
                    placeholder="请输入任务名称"></el-input>
        </el-form-item>

        <el-form-item label="任务类"
                      prop="jobClassName">
          <el-input v-model="form.jobClassName"
                    placeholder="请输入任务执行类"></el-input>
        </el-form-item>

        <el-form-item label="任务分组"
                      prop="jobGroup">
          <el-input v-model="form.jobGroup"
                    placeholder="请输入任务分组"></el-input>
        </el-form-item>

        <el-form-item label="时间规则"
                      prop="cronExpression">
          <el-input v-model="form.cronExpression"
                    placeholder="请输入quartz时间规则"></el-input>
        </el-form-item>

        <el-form-item label="是否告警"
                      prop="isAlarm">
            <el-radio v-model="isAlarm" label="0">是</el-radio>
            <el-radio v-model="isAlarm" label="1">否</el-radio>

        </el-form-item>

        <el-form-item label="告警电话"
                      prop="alarmPhones">
          <el-input v-model="form.alarmPhones"
                    placeholder="请输入告警电话，若存在多个以,隔开"></el-input>
        </el-form-item>

        <el-form-item label="告警邮箱"
                      prop="alarmMails">
          <el-input v-model="form.alarmMails"
                    placeholder="请输入告警邮箱，若存在多个以,隔开"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button @click="cancel('form')">取 消</el-button>
        <el-button v-if="dialogStatus=='create'"
                   type="primary"
                   @click="create('form')">确 定</el-button>
        <el-button v-else
                   type="primary"
                   @click="update('form')">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { fetchList, addObj,pausejob,resumejob,rescheduleJob,deletejob } from "@/api/job";
  import waves from "@/directive/waves/index.js"; // 水波纹指令
  import { validateEMail } from "@/api/validator/validator"; //表单认证
  import { mapGetters } from "vuex";
  import ElRadioGroup from "element-ui/packages/radio/src/radio-group";
  import ElOption from "element-ui/packages/select/src/option";

  export default {
    components: {
      ElOption,
      ElRadioGroup
    },
    name: "table_user",
    directives: {
      waves
    },
    data () {
      return {
        treeDeptData: [],
        checkedKeys: [],
        defaultProps: {
          children: "children",
          label: "name"
        },
        list: null,
        total: null,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 20
        },
        isAlarm: '1',
        form: {
          description: undefined,
          jobName: undefined,
          jobClassName: undefined,
          jobGroup: undefined,
          cronExpression: undefined,
          isAlarm:undefined
        },
        rules: {
          description: [
            {
              required: true,
              message: "请输入任务名称",
              trigger: "blur"
            }
          ],
          jobClassName: [
            {
              required: true,
              message: "请输入任务执行类名",
              trigger: "blur"
            }
          ],
          jobGroup: [
            {
              required: true,
              message: "请输入任务分组名称",
              trigger: "blur"
            }
          ],
          cronExpression: [
            {
              required: true,
              message: "请输入时间规则",
              trigger: "blur"
            }
          ],
          alarmMails: [
            {
              validator: validateEMail,
              trigger: "blur"
            }
          ]
        },
        statusOptions: ["0", "1"],
        dialogFormVisible: false,
        userAdd: false,
        userUpd: false,
        userDel: false,
        dialogStatus: "",
        textMap: {
          update: "编辑",
          create: "创建"
        },
        isDisabled: {
          0: false,
          1: true
        },
        tableKey: 0
      };
    },
    computed: {
      ...mapGetters(["permissions"])
    },
    filters: {
      statusFilter (status) {
        const statusMap = {
          "PAUSED": "暂停",
          "ACQUIRED": "运行",
          "WAITING":"运行"
        };
        return statusMap[status];
      },
      alarmFilter (alarm) {
        const alarmMap = {
          "1": "否",
          "0": "是"
        };
        return alarmMap[alarm];
      }
    },
    created () {
      this.getList();
    },
    methods: {
      getList () {
        this.listLoading = true;
        this.listQuery.isAsc = false;
        fetchList(this.listQuery).then(response => {
          this.list = response.data.records;
          this.total = response.data.total;
          this.listLoading = false;
        });
      },
      handleFilter () {
        this.listQuery.page = 1;
        this.getList();
      },
      handleSizeChange (val) {
        this.listQuery.limit = val;
        this.getList();
      },
      handleCurrentChange (val) {
        this.listQuery.page = val;
        this.getList();
      },
      handleCreate () {
        this.resetTemp();
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
      },
      handleModifyStatus(row, status){
        this.$confirm(
          "您确定要修改任务状态么, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {

          row.triggerState = status;
          if(status =='PAUSED'){
            pausejob(row)
              .then(() => {
                //this.getList();
                this.$notify({
                  title: "成功",
                  message: "操作成功",
                  type: "success",
                  duration: 2000
                });
              })
              .catch(() => {
                this.$notify({
                  title: "失败",
                  message: "操作失败",
                  type: "error",
                  duration: 2000
                });
              });

          }else {
            resumejob(row)
              .then(() => {
                this.$notify({
                  title: "成功",
                  message: "操作成功",
                  type: "success",
                  duration: 2000
                });
              })
              .catch(() => {
                this.$notify({
                  title: "失败",
                  message: "操作失败",
                  type: "error",
                  duration: 2000
                });
              });
          }
        })

      },
      handleModifyStatusSure(row, status) {
        row.triggerState = status;
        if(status =='PAUSED'){
          pausejob(row)
            .then(() => {
              this.$notify({
                title: "成功",
                message: "操作成功",
                type: "success",
                duration: 2000
              });
            })
            .catch(() => {
              this.$notify({
                title: "失败",
                message: "操作失败",
                type: "error",
                duration: 2000
              });
            });
        //}else if(status =='ACQUIRED'){
        }else {
          resumejob(row)
            .then(() => {
             // this.getList();
              this.$notify({
                title: "成功",
                message: "操作成功",
                type: "success",
                duration: 2000
              });
            })
            .catch(() => {
              this.$notify({
                title: "失败",
                message: "操作失败",
                type: "error",
                duration: 2000
              });
            });
        }

      },
      create (formName) {
        const set = this.$refs;
        set[formName].validate(valid => {
          if (valid) {
            this.form.jobName=this.form.jobClassName;
            this.form.isAlarm=this.isAlarm;
            addObj(this.form).then((data) => {
              console.log(data.data);
              if(data.data.data == true){
                this.dialogFormVisible = false;
                this.getList();
                this.$notify({
                  title: "成功",
                  message: "创建成功",
                  type: "success",
                  duration: 2000
                });
              }else{
                this.dialogFormVisible = false;
                this.$notify({
                  title: "失败",
                  message: data.data.msg,
                  type: "error",
                  duration: 2000
                });
              }

            });
          } else {
            return false;
          }
        });
      },
      cancel (formName) {
        this.dialogFormVisible = false;
        this.$refs[formName].resetFields();
      },
      deletes (row) {
        this.$confirm(
          "此操作将永久删除该任务(任务名称:" + row.description + "), 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          deletejob(row)
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
      cancelEdit(row) {
          alert(1);
        row.cronExpression = row.cronExpression
        row.edit = false
        this.$message({
          message: 'The title has been restored to the original value',
          type: 'warning'
        })
      },
      confirmEdit(row) {
          alert(2);
        row.edit = false
        row.cronExpression = row.cronExpression
        this.$message({
          message: 'The title has been edited',
          type: 'success'
        })
      },
      resetTemp () {
        this.form = {
          description: undefined,
          jobClassName: undefined,
          jobGroup: undefined,
          cronExpression: undefined
        };
      }
    }
  };
</script>
