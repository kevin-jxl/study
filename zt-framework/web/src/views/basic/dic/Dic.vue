<template>
    <div class="app-containers calendar-list-container">
        <!--检索框-->
        <div class="form_search clearfix" style="padding:0 10px;">
            <el-form :inline="true" :model="listQuery" class="search-form-inline">
                <el-form-item label="输入搜索:">
                    <el-input @keyup.enter.native="handleFilter" v-model="listQuery.name" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="字典Code:">
                    <el-input @keyup.enter.native="handleFilter" v-model="listQuery.code" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="el-icon-search" @click="handleFilter" size="mini"></el-button>
                </el-form-item>
                
                <el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-end">
                    <span class="el-icon-refresh-right " style="padding:5px;margin-left:10px" @click="refresh"></span >
                </el-tooltip>
                <el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-end" v-if="userDatas.permissions.includes('sys_dict_rm')">
                    <span class="el-icon-download" style="padding:5px;margin-left:10px;" @click="exportTable"></span >
                </el-tooltip>
                
            </el-form>
        </div>
        <el-divider style="margin-bottom: 0px;"/>
        <div class="dic-main" >
            <el-row :gutter="20" style="height:100%;">
                <el-col :span="16" style="height:100%;">
                    <div class="full-screen" style="height:calc(100% - 10px);">
                        <!-- <div class="float-fl table-title" style="margin:30px 0 10px 0;">字典列表</div> -->
                        <div style="text-align: left; margin:10px 5px 10px 0;font-weight:bold;">字典列表</div>
                        <div style="height:calc(100% - 42px);">
                            <z-table class="full-screen"
                                :data="list"
                                :currentPage="currentPage"
                                :pageSize="pageSize"
                                :total='total'
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange">
                                <div slot="table-column">
                                    <el-table-column align="center" type="index" width='100' label="序号" fixed></el-table-column>
                                    <el-table-column align="center" label="字典名称">
                                        <template slot-scope="scope">
                                            <span>{{scope.row.name}}</span>
                                        </template>
                                    </el-table-column>

                                    <el-table-column align="center" label="字典Code">
                                        <template slot-scope="scope">
                                            <span>{{scope.row.code }}</span>
                                        </template>
                                    </el-table-column>

                                    <el-table-column align="center" label="创建人">
                                        <template slot-scope="scope">
                                            <span>{{scope.row.createName}}</span>
                                        </template>
                                    </el-table-column>

                                    <el-table-column align="center" label="创建时间"  width="200">
                                        <template slot-scope="scope">
                                            <span>{{scope.row.createTime}}</span>
                                        </template>
                                    </el-table-column>

                                    <el-table-column label="操作" align="center" width="280">
                                        <template slot-scope="scope">
                                            <el-button
                                                size="mini"
                                                type="text"
                                                v-if="roleManager_btn_edit"
                                                @click="handleDetail(scope.row)"
                                            >详细信息</el-button>
                                            <el-button
                                                size="mini"
                                                type="text"
                                                v-if="userDatas.permissions.includes('sys_dict_del')"
                                                @click="handleDelete(scope.row)"
                                            >删除</el-button>
                                        </template>
                                    </el-table-column>
                                </div>
                            </z-table>
                        </div>
                    </div>
                </el-col>
                <el-col :span="8" style="height:100%;">
                    <div class="full-screen dic-right">
                        <div class='dic-right-head'><p>字典管理:{{titleName|| '暂无'}}</p></div>
                        <div class="dic-right-tables">
                            <div class="table-title" style="text-align:left;margin:0 0 10px 0;">字典列表</div>
                            <div style="text-align: left; margin:0 5px 10px 0;">
                                <el-button
                                    size="mini"
                                    plain
                                    style="color: #66B1FF"
                                    v-if="roleManager_btn_edit"
                                    @click="addTableList()">
                                    类型录入
                                </el-button>
                            </div>
                            <div style="height: calc(100% - 62px);">
                                <z-table
                                    :data="typeList"
                                    :currentPage="currentPage1"
                                    :pageSize="pageSize1"
                                    :total='total'
                                    @size-change="handleSizeChange1"
                                    @current-change="handleCurrentChange1">
                                    <div slot="table-column">
                                        <el-table-column align="center" type="index" width='100' label="序号" fixed></el-table-column>
                                        <el-table-column align="center" label="类型名称">
                                            <template slot-scope="scope">
                                                <span v-if="scope.row.id != editId">{{scope.row.name}}</span>
                                                <el-input v-else size="small" v-model="scope.row.name" placeholder="请输入内容"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column align="center" label="类型编码">
                                            <template slot-scope="scope">
                                                <span v-if="scope.row.id != editId">{{scope.row.code}}</span>
                                                <el-input v-else size="small" v-model="scope.row.code" placeholder="请输入内容"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="操作" align="center">
                                            <template slot-scope="scope" >
                                                <el-button
                                                    size="mini"
                                                    type="text"
                                                    style="color: #66B1FF"
                                                    v-if="roleManager_btn_edit && ( scope.row.id != -1 && scope.row.id != editId) && userDatas.permissions.includes('sys_dict_edit')"
                                                    @click="editType(scope.row.id)"
                                                >编辑</el-button>
                                                <el-button
                                                    size="mini"
                                                    type="text"
                                                    style="color: #66B1FF"
                                                    v-if="roleManager_btn_del &&  ( scope.row.id != -1 && scope.row.id != editId) && userDatas.permissions.includes('sys_dict_del')"
                                                    @click="handleDeletes(scope.row)"
                                                >删除</el-button>
                                                <el-button
                                                    size="mini"
                                                    type="text"
                                                    style="color: #66B1FF"
                                                    v-if="roleManager_btn_edit && ( scope.row.id == -1 || scope.row.id == editId)  && userDatas.permissions.includes('sys_dict_edit') && userDatas.permissions.includes('sys_dict_add')"
                                                    @click="addList(scope.row)"
                                                >完成</el-button>
                                            </template>
                                        </el-table-column>
                                    </div>
                                </z-table>
                            </div>
                        </div> 
                    </div>
                </el-col>
            </el-row>
        </div>

        <!-- 删除弹框 -->
        <el-drawer title="删除" :visible.sync="dialogPermissionVisible" class="role_menu">
            <div slot="title">
                <span class="titleSpan">删除</span>
            </div>
            <div class="dialog_tree">
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary"
                    @click="updatePermession(roleId, roleCode)">更 新
                </el-button>
            </div>
        </el-drawer>
    </div>
</template>

<script>
    import {
        dicList,//字段列表
        addDic,//字段新增
        detailDic,//字段详情
        updateDic,//字段修改
        deleteDic,//字段删除
        selectDictsList,//字段对照列表
        deleteSysdict,
        addSysdict, //对找表添加
        updatesDic,//对找表编辑
        getDownloadExcel,//导出
    } from "@/api/basic/dic";
    import ZTable from '@/components/ZTable.vue'
    export default {
        components: {
            ZTable,
        },
        data() {
            return {
                listQuery: {
                    page: 1,
                    limit: 30,
                    name:'',
                    code:'',
                    tableName:'字典管理'
                },
                listQuery1: {
                    page: 1,
                    limit: 30,
                    name:'',
                    code:'',
                },
                roleManager_btn_del:true,
                list: [],
				typeList:[],
                form: {
                    roleName: undefined,
                    roleCode: undefined,
                    roleDesc: undefined
                },
                dialogFormVisible: false,
                dialogPermissionVisible: false,
                dialogStatus: "",
                roleManager_btn_edit: true,
                formInline: {
                    user: '',
                    region: ''
                },
                value:'',
                currentPage:1,
                pageSize:30,
                currentPage1:1,
                pageSize1:30,
                formLabelWidth:'120px',
                total: 0,
                addBtn:false,
                rowId:'',
                editId: -1,
                titleName:'',
                userDatas:[],
                rule:{
                    // empno:[
                    //     { required: true, message: "员工编号不能为空",trigger: "blur"},
                    //     { validator: checkNum, trigger: 'blur'}
                    // ]
                }    
            }
        },
        created() {
            let me = this
            me.getList();
            me.userDatas = JSON.parse(sessionStorage.getItem('userData'))
            console.log('字典权限',)
        },
        inject:['reload'],
        methods: {
            refresh(){
                this.reload()
            },
            exportTable(){  //导出
                if( this.list.length == 0 ){
                    this.$message.error('当前表格暂无数据，请补充数据再导出！');
                }else{
                    getDownloadExcel(this.listQuery).then(response => {});
                }
            },
            getList() {
                this.listLoading = true;
                dicList(this.listQuery).then(response => {
                    this.list = response.data.records;
                    this.total = response.data.total;
                    this.listLoading = false;
                    console.log('response',response)
                });
            },
            //搜索
            onSubmit() {
            	console.log('submit!');
            	this.getList();
            },
            //修改分页
            handleSizeChange(size) {
            	this.listQuery.limit = size;
            	this.getList();
            },
            //修改分页
            handleCurrentChange(page) {
            	this.listQuery.page = page;
            	this.getList();
            },
            //修改分页
            handleSizeChange1(size) {
            	this.listQuery1.limit = size;
            	this.getList();
            },
            //修改分页
            handleCurrentChange1(page) {
            	this.listQuery1.page = page;
            	this.getList();
                console.log('current page: ', page);
            },
            handleFilter() {
                this.listQuery.page = 1;
                this.listQuery.name = this.listQuery.name.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
                this.listQuery.code = this.listQuery.code.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
                this.getList();
            },
            //获取类型列表
			handleDetail(row){
                console.log('row.id',row.id)
                this.rowId = row.id;
                this.titleName = row.name;
				selectDictsList({dictId:row.id}).then(response => {
				    this.typeList = response.data;
				    // this.total = response.data.total;
				    console.log('对照列表',response.data)
				});
			},
            handleDelete(row) {
				const rowObj = row
                this.$confirm(
                    "确定要删除该条信息吗?",
                    "提示框",
                    {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }
                ).then(() => {
                    deleteDic(rowObj.id)
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
            },
            // 对照表新增
            addTableList(){
                if(this.rowId == ''){
                    this.$message('请选择一条字典信息');
                }else{
                    var tableList = {id: -1}
                    this.typeList.push(tableList)
                }
            },
            // 编辑类型
            editType(id) {
                this.editId = id;
            },
            // 对照表新增完成
            addList(row){
                let me = this;
                if(row.id != -1){ //编辑
                    let params ={
                        code:row.code,
                        name:row.name,
                        id:row.id
                    }
                    let regUser = /^[a-zA-Z]+$/;
                    if ( !regUser.test(row.code) ) {
                        this.$message.error('编码只能输入英文');
                        return
                    }else{
                        updatesDic(params).then(response => {
                            this.addBtn = false
                            this.$notify({
                                message: "编辑成功",
                                type: "success",
                                duration: 2000
                            });
                            me.editId =-1;
                            console.log('me.rowIdme.rowId',me.rowId)
                            selectDictsList({dictId:me.rowId}).then(response => {
                                this.typeList = response.data;
                            });
                        });
                    }
                    
                }else{
                    let params ={
                        code:row.code,
                        name:row.name,
                        dictIndexId:this.rowId
                    }
                    let regUser = /^[a-zA-Z]+$/;
                    console.log('row.code',regUser.test(row.code))
                    if ( !regUser.test(row.code) ) {
                        this.$message.error('编码只能输入英文');
                    }
                    else{
                        addSysdict(params).then(response => {
                            this.addBtn = false
                            this.$notify({
                                title: "成功",
                                message: "添加成功",
                                type: "success",
                                duration: 2000
                            });
                            selectDictsList({dictId:me.rowId}).then(response => {
                                this.typeList = response.data;
                            });
                        });
                    }
                    
                }
                
            },
            // 删除对照表
            handleDeletes(row){
                let me = this;
                me.$confirm(
                    "确定要删除该项目么?",
                    "提示",
                    {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }
                ).then(() => {
                    deleteSysdict(row.id)
                    .then(response => {
                        me.dialogFormVisible = false;
                        me.handleDetail(me.rowId)
                        me.$notify({
                            title: "成功",
                            message: "删除成功",
                            type: "success",
                            duration: 2000
                        });
                    })
                });
            }
        },
    }
</script>

<style lang="scss" scoped>
.app-containers {
    height: 100%;
	box-sizing: border-box;
	padding: 10px;
	background: #fff;
    .dic-main{
        background: #fff;
        height: calc(100% - 60px);
        .dic-right{
            border-left:1px solid rgb(220, 223, 230);
            .dic-right-head{
                height: 30px;
                background: #dfdfdf7a;
                line-height: 30px;
                font-size: 14px;
                font-weight: bold;
            }
            .dic-right-tables{
                padding: 10px;
                height: calc(100% - 50px);
            }
        }
        
    }
    
}

::v-deep .el-form-item {
    margin-bottom: 20px !important;
}
::v-deep .el-divider--horizontal {
    margin: 0px !important;
}
</style>