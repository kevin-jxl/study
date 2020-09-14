<template>
    <div class="app-container calendar-list-container">
        <div class="filter-container crud-menu clearfix">
            <!--检索框-->
            <div class="form_search clearfix" style="padding:0 10px;">
                <el-form :inline="true" :model="listQuery" class="search-form-inline">
                    <el-form-item label="输入搜索:">
                        <el-input @keyup.enter.native="handleFilter" v-model="listQuery.tableName" placeholder="请输入用户名/姓名"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" class="el-icon-search" @click="handleFilter" size="mini"></el-button>
                    </el-form-item>
                    <el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-end">
                        <span class="el-icon-refresh-right " style="padding:5px;" @click="refresh"></span >
                    </el-tooltip>
                </el-form>
            </div>
            <el-divider />
        </div>
        <div style="height:calc(100% - 120px)">
            <z-table class="full-screen"
                :data="list"
                :currentPage="currentPage"
                :pageSize="pageSize"
                :total='total'
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange">
                <div slot="table-column">
                    <el-table-column align="center" type="index" width='100' label="序号" fixed></el-table-column>
                    <el-table-column align="center" label="表名">
                        <template slot-scope="scope">
                            <span>{{scope.row.tableName}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="注释">
                        <template slot-scope="scope">
                            <span>{{scope.row.tableComment}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column label="操作" align="center" width="280" v-if="userDatas.permissions.includes('sys_user_edit') ||  userDatas.permissions.includes('sys_user_del')">
                        <template slot-scope="scope">
                        <el-button
                            size="mini"
                            type="primary"
                            @click="generate(scope.row)"
                        >生成</el-button>
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
    generates
} from "@/api/intetFaceInte/intetFaceInte";
import { fetchTree } from "@/api/basic/menu";
import { mapGetters } from "vuex";
import ZTable from '@/components/ZTable.vue'

export default {
    name: "table_role",
    components: { ZTable },
    computed: {},
    data() {
        return {
            list: null,
            total: 0,
            listLoading: true,
            listQuery: {
                page: 1,
                limit: 30,
                tableName:'',
            },
            roleId: undefined,
            roleCode: undefined,
            form: { 
                tableName: undefined,
                attachment:undefined
            },
            dialogFormVisible: false,
            dialogPermissionVisible: false,
            dialogStatus: "",
            value:'',
            currentPage:1,
            pageSize:30,
            formLabelWidth:'120px',
            userDatas:{}
        };
    },
    inject:['reload'],
    created() {
        let me = this;
        me.userDatas = JSON.parse(sessionStorage.getItem('userData'))
        me.getList();
        me.form.userId = this.userDatas.sysUser.userId;
    },
    methods: {
        refresh(){
            this.reload()
        },
        getList() {
            let me = this;
            me.listLoading = true;
            fetchList(this.listQuery).then(response => {
                me.list = response.data.records;
                me.total = response.data.total;
                me.listLoading = false;
                console.log('代码生成',me.list)
            });
        },
        handleFilter() {
            this.listQuery.page = 1;
            this.listQuery.tableName= this.listQuery.tableName.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
            this.getList();
        },
        //代码生成
        generate(row) {
            const rowObj = row
            console.log('rowObjrowObj',rowObj)
            this.$confirm(
                "确定要生成对应的代码么?",
                "提示",
                {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }
            ).then(() => {
                let params ={
                    tableName:rowObj.tableName,
                    comments:rowObj.tableComment,
                    pageName:'com.ztman'
                }
                generates(params)
                .then(response => {
                    this.dialogFormVisible = false;
                    // this.getList();
                    // this.$notify({
                    //     title: "成功",
                    //     message: "生成成功",
                    //     type: "success",
                    //     duration: 2000
                    // });
                    
                    let data = response.data
                    let url = window.URL.createObjectURL(new Blob([data]))
                    let link = document.createElement('a')
                    link.style.display = 'none'
                    link.href = url
                    link.setAttribute('download', 'test.rar')

                    document.body.appendChild(link)
                    link.click()
                    console.log('link',link)
                })
                .catch((err) => {
                    this.$notify({
                        title: "失败",
                        message: "生成失败",
                        type: "error",
                        duration: 2000
                    });
                });
            });
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
    }
};
</script>
<style lang="scss" scoped>

.app-container {
    height: 100%;
	box-sizing: border-box;
	padding: 10px;
	background: #fff;
}

::v-deep .el-form-item {
    margin-bottom: 20px !important;
}
</style>
