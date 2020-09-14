<template>
    <div class="app-container calendar-list-container">
        <div class="filter-container crud-menu clearfix">
            <!--检索框-->
            <div class="form_search clearfix" style="padding:0 10px;">
                <el-form :inline="true" :model="formInline" class="search-form-inline clearfix">
                    <el-form-item label="输入搜索:">
                        <el-input @keyup.enter.native="handleFilter" v-model="listQuery.title" placeholder="请输入"></el-input>
                    </el-form-item>
                    <el-form-item label="操作时间:" style="margin-left:10px;">
                        <el-date-picker
                            v-model="startTimes"
                            type="daterange"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            style="margin-top:2px;"
                            value-format="yyyy-MM-dd"
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
                        <span class="el-icon-download" style="padding:5px;margin-right:10px;" @click="exportTable" v-if="userDatas.permissions.includes('sys_sys_rm')"></span >
                    </el-tooltip>
                    <el-tooltip class="item fj-refresh" effect="dark" content="删除" placement="top-end" style="margin-right:10px;" >
                        <span class="el-icon-delete" style="padding:5px;" @click="handleDelete()" v-if="userDatas.permissions.includes('sys_sys_del')"></span>
                    </el-tooltip>
                    
                </el-form>
                <p style="height: 1px;width: 100%;background-color: #DCDFE6;float:left;margin:-5px 0 28px 0;"></p>
            </div>
        </div>
        
        <div style="height:calc(100% - 110px)">
            <div class="table-title" style="text-align:left;margin:0 0 10px 0;">日志列表</div>
            <z-table class="full-screen"
                :data="list"
                :currentPage="currentPage"
                :pageSize="pageSize"
                :total='total'
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                @selection-change="currentSelect"
            >
                <div slot="table-column">
                    <el-table-column type="selection"  width="55" fixed></el-table-column>
                    <el-table-column align="center" type="index" width='100' label="序号" ></el-table-column>
                    <el-table-column align="center" label="编号">
                        <template slot-scope="scope">
                            <span>{{scope.row.id}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="用户名">
                        <template slot-scope="scope">
                            <span>{{scope.row.createBy }}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="操作时间"  width="200">
                        <template slot-scope="scope">
                            <span>{{scope.row.createTime}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="IP地址">
                        <template slot-scope="scope">
                            <span>{{scope.row.remoteAddr}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="操作记录" width="630">
                        <template slot-scope="scope">
                            <span>{{scope.row.title}}</span>
                        </template>
                    </el-table-column>
                </div>
            </z-table>
        </div>
    </div>
</template>

<script>
import {
    fetchList,
    deletes,
    getDownloadExcel
} from "@/api/basic/business";
import { fetchTree } from "@/api/basic/menu";
import { mapGetters } from "vuex";
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
                limit: 30,
                type:0,
                title:'',
                tableName:'系统异常日志',
                startTime:'',
                endTime:''
            },
            startTimes:[],
            // form: {
            //     roleName: undefined,
            //     roleCode: undefined,
            //     roleDesc: undefined
            // },
            roleCode: undefined,
            formInline: {
                user: '',
                region: ''
            },
            value:'',
            currentPage:1,
            pageSize:30,
            formLabelWidth:'120px',
            roleManager_btn_del:true,
            times:'',
            deleteNumber:0,
            currentId:[],
            userDatas:[]
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
        exportTable(){  //导出
            if( this.list.length == 0 ){
                this.$message.error('当前表格暂无数据，请补充数据再导出！');
            }else{
                getDownloadExcel(this.listQuery).then(response => {});
            }
        },
        refresh(){
            this.reload()
        },
        getList() {
            this.listLoading = true;
            fetchList(this.listQuery).then(response => {
                this.list = response.data.records;
                this.total = response.data.total;
                this.listLoading = false;
            });
        },
        handleFilter() {
            this.listQuery.page = 1;
            if(this.startTimes){
                this.listQuery.startTime = this.startTimes[0]
                this.listQuery.endTime = this.startTimes[1]
                this.listQuery.page = 1;
                this.getList();
            }else{
                this.listQuery.page = 1;
                this.listQuery.startTime = ''
                this.listQuery.endTime = ''
                this.getList();
            }
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
        currentSelect(datas, row){
            let me = this;
            let idArray = []
            this.deleteNumber = datas.length
            datas.filter( item => {
                idArray.push(item.id)
                console.log(idArray)
            })
            this.currentId = idArray.join(",");
        },
        handleDelete() {
            if(this.deleteNumber >0){
                // deletes
                this.$confirm(
                    "确定要删除该条信息吗?",
                    "提示框",
                    {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning"
                    }
                ).then(() => {
                    deletes(this.currentId)
                    .then(response => {
                        this.dialogFormVisible = false;
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
            }else{
                this.$message({
                    message: '请选择要删除的内容',
                    type: 'warning'
                });
            }
        },
    }
};
</script>
<style lang="scss" scoped>
</style>
