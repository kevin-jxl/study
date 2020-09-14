<template>
    <div class="app-container calendar-list-container">
        <div class="filter-container crud-menu clearfix">
            <!--检索框-->
            <div class="form_search clearfix" style="padding:0 10px;">
                <el-form :inline="true" :model="listQuery" class="search-form-inline clearfix">
                    <el-form-item label="请输入搜索:">
                        <el-input @keyup.enter.native="handleFilter" v-model.trim="listQuery.workName" placeholder="请输入姓名"></el-input>
                    </el-form-item>
                    <el-form-item label="值班周期:" style="margin-left:10px;">
                        <el-date-picker
                            v-model="listQuery.startTime"
                            type="month"
                            placeholder="选择月"
                            value-format="yyyy-MM"
                        >
                        </el-date-picker>
                     </el-form-item>
                    <el-form-item>
                        <el-button type="primary" class="el-icon-search" @click="handleFilter" size="mini"></el-button>
                    </el-form-item>
                    <el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-end" >
                        <span class="el-icon-refresh-right " style="padding:5px;" @click="refresh"></span >
                    </el-tooltip>
                    <el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-end">
                        <span class="el-icon-download" style="padding:5px;margin-right:10px;" @click="exportTable" v-if="userDatas.permissions.includes('spark_attendance_rm')"></span >
                    </el-tooltip>
                </el-form>
                <p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:-5px 0 28px 0;"></p>
            </div>
        </div>
        
        <div style="height:calc(100% - 110px)">
            <div class="table-title" style="text-align:left;margin:0 0 10px 0;">考勤列表</div>
            <z-table class="full-screen"
                :data="list"
                :currentPage="currentPage"
                :pageSize="pageSize"
                :total='total'
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
            >
                <div slot="table-column">
                    <el-table-column align="center" type="index" width='100' fixed label="序号" ></el-table-column>
                    <el-table-column align="center" label="时间" width="300">
                        <template slot-scope="scope">
                            <span>{{scope.row.workStartTime}}~{{scope.row.workEndTime}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="姓名">
                        <template slot-scope="scope">
                            <span>{{scope.row.workName}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="部门">
                        <template slot-scope="scope">
                            <span>{{scope.row.workDepartment}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="职位">
                        <template slot-scope="scope">
                            <span>{{scope.row.workPost}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="出勤天数" >
                        <template slot-scope="scope">
                            <span>{{scope.row.workAttendance ||0}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="异常天数" >
                        <template slot-scope="scope">
                            <span :class="scope.row.workAbnormal?'red-normal':'normal'">{{scope.row.workAbnormal || 0}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="请假" >
                        <template slot-scope="scope">
                            <span>{{scope.row.workLeave || 0}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="加班" >
                        <template slot-scope="scope">
                            <span>{{scope.row.workOvertime || 0}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="调休" >
                        <template slot-scope="scope">
                            <span>{{scope.row.workOff || 0}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="外出" >
                        <template slot-scope="scope">
                            <span>{{scope.row.workOut || 0}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="280" v-if="userDatas.permissions.includes('spark_attendance_del')">
                        <template slot-scope="scope">
                            <el-button
                                size="mini"
                                type="text"
                                v-if="userDatas.permissions.includes('spark_attendance_del')"
                                @click="handleDelete(scope.row)"
                            >删除</el-button>
                        </template>
                    </el-table-column>
                </div>
            </z-table>
        </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import {
    getDownloadExcel,
} from "@/api/index.js";
import ZTable from '@/components/ZTable.vue'
export default {
    name: "table_role",
    components: { ZTable },
    directives: {
    },
    data() {
        return {
            treeData: [],
            list: null,
            total: 0,
            listLoading: true,
            listQuery: {
                page: 1,
                limit: 20,
                workName:'',
                startTime:'',
                tableName:'考勤管理'
            },
            startTimes:[],
            value1:'',
            currentPage:1,
            pageSize:30,
            formLabelWidth:'120px',
            roleManager_btn_del:true,
            deleteNumber:0,
            currentId:[],
            userDatas:[],
        };
    },
    inject:['reload'],
    created() {
        let me = this
        me.getList();
        me.userDatas = JSON.parse(sessionStorage.getItem('userData'))
    },
    computed: {
        ...mapGetters([])
    },
    methods: {
        refresh(){
            this.reload()
        },
        exportTable(){  //导出
            if( this.list.length == 0 ){
                this.$message.error('当前表格暂无数据，请补充数据再导出！');
            }else{
                getDownloadExcel(this.listQuery,'/spark/companywork/downloadExcel').then(response => {});
            }
        },
        getList() {
            let me = this;
            this.listLoading = true;
            me.$api.getCompanyworkList(me.listQuery)
            .then(response => {
                this.list = response.data.records;
                this.total = response.data.total;
                this.listLoading = false;
            })
            .catch(err => {
                console.log(err);
            });
        },
        handleFilter() {
            let me = this;
            me.listQuery.page = 1;
            me.listQuery.workName = me.listQuery.workName.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
            me.listQuery.startTime = me.listQuery.startTime;
            me.getList()
        },
        // 分页
        handleSizeChange(val) {
            this.listQuery.limit = val;
            this.getList();
        },
        handleCurrentChange(val) {
            this.listQuery.page = val;
            this.getList();
        },
        // 删除
        handleDelete(row) {
            let me = this;
            console.log('row',row)
            this.$confirm(
                "确定要删除该条信息吗?",
                "提示框",
                {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }
            ).then(() => {
                me.$api.getCompanyworkDelete(row.workId)
                .then(res => {
                    this.dialogFormVisible = false;
                    this.getList();
                    this.$notify({
                        title: "成功",
                        message: "删除成功",
                        type: "success",
                        duration: 2000
                    });
                })
                .catch(err => {
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
};
</script>
<style lang="scss" scoped>
.red-normal{
    color: red;
}
.normal{
    color: black;
}

</style>
