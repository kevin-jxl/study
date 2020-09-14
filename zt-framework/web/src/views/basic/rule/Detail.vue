<template>
    <div class="app-container calendar-list-container">
        <div class="filter-container crud-menu">
            <!--检索框-->
            <div class="form_search clearfix" style="padding:0 10px;">
                <el-form :inline="true" :model="formInline" class="search-form-inline">
                    <el-form-item label="输入搜索:">
                        <el-input @keyup.enter.native="handleFilter"  v-model.trim="listQuery.roleName" placeholder="请输入" ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" class="el-icon-search" @click="handleFilter" size="mini"></el-button>
                    </el-form-item>
                    <el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-start">
                        <span class="el-icon-refresh-right " style="padding:5px;"  @click="refresh"></span >
                    </el-tooltip>
                </el-form>
            </div>
            <el-divider />
            <div class="float-fl table-title">角色列表</div>
            <div  style="text-align: right;">
                <el-button
                    type="primary"
                    @click="handleCreate"
                    icon="edit"
                    size="mini"
                    style="margin-bottom:10px;"
                    v-if="userDatas.permissions.includes('sys_role_add')">添加角色
                </el-button>
            </div>
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
                    <el-table-column align="center" type="index"  width='100'  label="序号" fixed></el-table-column>
                    
                    <el-table-column align="center" label="角色名称">
                        <template slot-scope="scope">
                            <span>{{scope.row.roleName}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="角色描述">
                        <template slot-scope="scope">
                            <span>{{scope.row.roleDesc }}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="成员数量(人)">
                        <template slot-scope="scope">
                            <span>{{scope.row.userCount}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="创建人">
                        <template slot-scope="scope">
                            <span>{{scope.row.createPeople}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="添加时间" width='200'>
                        <template slot-scope="scope">
                            <span>{{scope.row.createTime}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="是否启用"> 
                        <template slot-scope="scope">
                            <el-switch
                                v-model="scope.row.roleFlag"
                                @change='switchChange(scope.row)'
                            >
                            </el-switch>
                            
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="280" v-if="userDatas.permissions.includes('sys_role_edit') || userDatas.permissions.includes('sys_role_del') || userDatas.permissions.includes('sys_role_perm')">
                        <template slot-scope="scope">
                        <el-button
                            size="mini"
                            type="text"
                            v-if="userDatas.permissions.includes('sys_role_edit')"
                            @click="handleUpdate(scope.row)"
                        >编辑</el-button>
                        <el-button
                            size="mini"
                            type="text"
                            v-if="userDatas.permissions.includes('sys_role_del')"
                            @click="handleDelete(scope.row)"
                        >删除</el-button>
                        <el-button
                            size="mini"
                            type="text"
                            @click="handlePermission(scope.row)"
                            v-if="userDatas.permissions.includes('sys_role_perm')"
                        >权限配置</el-button>
                        </template>
                    </el-table-column>
                </div>
            </z-table>
            <!-- 菜单角色弹框 -->
            <el-drawer 
                :title="textMap[dialogStatus]" 
                :visible.sync="dialogPermissionVisible"
                class="role_menu" 
                :append-to-body='true'
                size="20%"
                :wrapperClosable='false'
            >
                <div slot="title">
                    <span class="titleSpan">{{textMap[dialogStatus]}}</span>
                </div>
                <el-tabs v-model="activeName" type="card"  @tab-click="handleClick" style="width:90%;margin:0 auto;min-height:500px;height:calc(100vh - 220px);overflow-y:scroll;">
                    <el-tab-pane :label="item.label" :name="item.name" v-for="item in treeData" :key="item.id">
                        {{item.label}}
                        <div class="dialog_tree">
                            <el-tree class="filter-tree"
								:default-checked-keys="item.checkIdList"
                                :data="item.children"
                                :check-strictly="false"
                                node-key="id"
                                highlight-current
                                :props="defaultProps"
                                show-checkbox
                                ref="trees"
                                :filter-node-method="filterNode"
                                :default-expand-all='true'
                                @check='permissionTreeCheck'
                                @setChecked='setCheckeds'
                            > 
                            </el-tree>
                        </div>
                    </el-tab-pane>
                </el-tabs>
                <div class="dialog-footer" style="display:flex;height:30px;justify-content:flex-end;margin:50px 40px 0 0 ;">
                    <el-button size="mini"  type="primary"  @click="updatePermession(roleId, roleCode)">保存</el-button>
                    <el-button size="mini"  type="primary"  @click="handlePermission()">重置</el-button>
                </div>
            </el-drawer>
        </div>
        <!-- 新建 编辑 弹框 -->
        <div class="dialog_mask_mine">
            <el-dialog :visible.sync="dialogFormVisible" width="35%" :close-on-click-modal="false">
                <div slot="title">
                    <span class="preTitle"></span>
                    <span class="nextTitle">{{textMap[dialogStatus]}}</span>
                </div>
				<div class="fix-width">
                    <el-form :model="form" class="clearfix" :rules="rules" ref="form" ><!-- :rules="rules" -->
                        <el-row :gutter="20">
                            <el-col :span="12"><el-form-item label="角色名称" :label-width="formLabelWidth"  prop="roleName">
                                <el-input v-model="form.roleName" autocomplete="off" placeholder="请输入"></el-input>
                            </el-form-item></el-col>
                            <!-- <el-col :span="12"><el-form-item label="角色标识" :label-width="formLabelWidth"  prop="roleCode">
                                <el-input v-model="form.roleCode" autocomplete="off" placeholder="请输入英文"></el-input>
                            </el-form-item></el-col> -->
                        </el-row>
                        <el-form-item label="角色描述" :label-width="formLabelWidth" style="width: 95%;"  prop="roleDesc">
                            <el-input  type="textarea" placeholder="字数不超过200字" v-model="form.roleDesc" autocomplete="off" :maxlength="200" :rows="4"></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <!-- <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
                        <el-button @click="dialogFormVisible = false">取 消</el-button> -->

                    <el-button @click="cancel('form')" size="small">取 消</el-button>
                        <el-button v-if="dialogStatus=='create'"
                                    type="primary"
                                    size="small"
                                    @click="create('form')">确 定
                        </el-button>
                        <el-button v-else
                                    type="primary"
                                    size="small"
                                    @click="update('form')">保 存
                        </el-button>
                    </div>
                </div>
			</el-dialog>
        </div>
    </div>
</template>

<script>
import {
    fetchList,
    getObj,
    addObj,
    putObj,
    delObj,
    permissionUpd,
    fetchRoleTree,
    getCurrentId,
    putObjs
} from "@/api/basic/role";
import { mapGetters } from "vuex";
import ZTable from '@/components/ZTable.vue'
import waves from "@/directive/waves/index.js"; // 水波纹指令

export default {
    name: "table_role",
    components: { ZTable },
    directives: {
        waves
    },
    data() {
        return {
            treeData: [],
            defaultProps: {
                children: "children",
                label: "name"
            },
            activeName: '基础平台',
            menuIds: "",
            list: null,
            listLoading: true,
            total: 0,
            listQuery: {
                page: 1,
                limit: 30,
                roleName:'',
                enterprises:'',
                parklot:''
            },
            form: {
                roleName: undefined,
                roleCode: undefined,
                roleDesc: undefined,
            },
            roleId: undefined,
            roleCode: undefined,
            rules: {
                roleName: [
                    { required: true, message: "角色名称不能为空",trigger: "blur"},
                    { min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur" }
                ],
                roleCode: [
                    // {
                    //     required: true,
                    //     message: "角色标识",
                    //     trigger: "blur"
                    // },
                    {
                        required:true,
                        pattern:/^[A-Za-z ]+$/,
                        message: "只能输入英文",
                        trigger: "blur"
                    }
                ]
            },
            dialogFormVisible: false,
            dialogPermissionVisible: false,
            dialogStatus: "",
            textMap: {
                update: "编辑角色",
                create: "添加角色",
                permission: "权限分配"
            },
            roleManager_btn_add: true,
            roleManager_btn_edit: true,
            roleManager_btn_del: true,
            drawerPermissonVisible: false,
            // drawerDataVisible: false,
            roleManager_btn_perm:true,
            newPermissions: [],
            isTrue:true,
            formInline: {
                user: '',
                region: ''
            },
            value:'',
            value1:'',
            currentPage:1,
            pageSize:30,
            formLabelWidth:'120px',
            newArray:[],
            userDatas:[],
            currentId:[],
            checkArr:[],
            tabId:''
        };
    },
    created() {
        this.getList();
        let me = this;
        me.userDatas = JSON.parse(sessionStorage.getItem('userData'))
    },
    computed: {
        ...mapGetters([])
    },
    inject:['reload'],
    watch:{
    },
    methods: {
        refresh(){
            this.reload()
        },
        getList() {
            let me = this
            this.listLoading = true;
            fetchList(this.listQuery).then(response => {
                me.list = response.data.records;
                me.list.forEach(item=>{
                    if(item.roleFlag == 1){
                        item.roleFlag = true
                    }else if(item.roleFlag == 0){
                        item.roleFlag = false
                    }
                })
                me.total = response.data.total;
                me.listLoading = false;
                console.log('this.list',this.list)
            });
        },
        onSubmit() {
            console.log('submit!');
        },
        handleFilter() {
            this.listQuery.roleName = this.listQuery.roleName.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
            this.listQuery.page = 1;
            this.getList();
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
        handleUpdate(row) {
            getObj(row.roleId).then(response => {
                this.form = response.data;
                this.dialogFormVisible = true;
                this.dialogStatus = "update";
            });
        },
        permissionTreeCheck(e,currentNodes,nodes){
            // this.newPermissions = currentNodes
            let currentIds = [];
            let ids = currentNodes.checkedNodes;
            ids.filter(item=>{
                currentIds.push(item.id)
            })
            this.currentId = currentIds;
            this.checkArr = currentNodes.checkedKeys;
            this.checkArr = [...currentNodes.checkedKeys,...currentNodes.halfCheckedKeys]
            console.log('当前选中',this.checkArr)
			console.log(e,currentNodes,nodes)
        },
        handlePermission(row) {
            let me = this;
			if(row){
				me.roleCode = row.roleCode;
				me.roleId = row.roleId;
			}
            fetchRoleTree(me.roleCode).then(response => {
                me.treeData = response.data;
                me.dialogStatus = "permission";
                me.dialogPermissionVisible = true;
				me.treeData.forEach(item=>{
					if(item.name == me.activeName){
                        me.checkArr = item.checkIdList;
                        me.tabId = item.id
					}
				})
				console.log(me.checkArr)
            });
        },
		
        updatePermession(roleId, roleCode) {
            let me = this;
            me.menuIds = "";
            me.menuIds = me.checkArr.join(",");
            me.menuIds = me.menuIds + ',' + me.tabId
            permissionUpd(roleId, me.menuIds,me.activeName).then(() => {
                me.drawerPermissonVisible = false;
                fetchRoleTree(roleCode).then(response => {
                    me.treeData = response.data;
                    me.$notify({
                        title: "成功",
                        message: "修改成功",
                        type: "success",
                        duration: 2000
                    });
                });
            });
        },
        //切换开启关闭服务
        switchChange(row){
            console.log(row)
            let me=this,params ={
                roleId:row.roleId,
                roleFlag:row.roleFlag?1:0
            }
            console.log(params)
            putObjs(params).then(() => {
                me.$notify({
                    title: "成功",
                    message: "修改成功",
                    type: "success",
                    duration: 2000
                });
                // me.getList();
            });
        },
        /**
         * 解析出所有的太监节点id
         * @param json 待解析的json串
         * @param idArr 原始节点数组
         * @param temp 临时存放节点id的数组
         * @return 太监节点id数组
         */
        resolveAllEunuchNodeId(json, idArr, temp) {
            for (let i = 0; i < json.length; i++) {
                const item = json[i];
                // 存在子节点，递归遍历;不存在子节点，将json的id添加到临时数组中
                if (item.children && item.children.length !== 0) {
                    this.resolveAllEunuchNodeId(item.children, idArr, temp);
                } else {
                    temp.push(idArr.filter(id => id === item.id));
                }
            }
            return temp;
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        handleDelete(row) {
            this.$confirm(
                "确定要删除该条信息吗?",
                "提示框",
                {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }
        ).then(() => {
            delObj(row.roleId)
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
        // 新增
        handleCreate() {
            this.resetTemp()
            this.dialogStatus = 'create'
            this.dialogFormVisible = true
        },
        resetTemp() {
            this.form = {
                roleName: undefined,
                roleCode: undefined,
                roleDesc: undefined
            }
        },
        setCheckeds(data,isTrue,isfleg){
            // console.log('data',data)
            // console.log('isTrue',isTrue)
            // console.log('isfleg',isfleg)
        },
        
        create(formName) {
            console.log('formName',formName)
            console.log('set',set)
            const set = this.$refs;
            set[formName].validate(valid => {
                if (valid) {
                addObj(this.form).then((response) => {
                    if(response.data.data == false){
                        this.$message.error(response.data.msg);
                    }else{
                        this.dialogFormVisible = false;
                        this.getList();
                        this.$notify({
                            title: "成功",
                            message: "创建成功",
                            type: "success",
                            duration: 2000
                        });
                    }
                });
                } else {
                return false;
                }
            });
        },
        cancel(formName) {
            this.dialogFormVisible = false;
            this.$refs[formName].resetFields();
        },
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
        
        // 修改table header的背景色
        tableHeaderColor({ row, column, rowIndex, columnIndex }) {
            if (rowIndex === 0) {
                return "background-color: #fafafa;color:#999; font-size:12px;";
            }
        },
		//获取树表格当前数组
		handleClick(tab, event){
			console.log('tabtab',tab.name);
            let me = this;
            console.log('treeData',me.treeData)
			me.treeData.forEach(item=>{
				if(item.name == tab.name){
					console.log(1)
                    this.tabId = item.id
					this.checkArr = item.checkIdList;
				}
			})
			console.log(this.checkArr)
		}
    }
};
</script>
<style lang="scss" scoped>

::v-deep .el-divider--horizontal {
    margin-top: 0px;
}
::v-deep .el-form-item {
    margin-bottom: 20px !important;
}
</style>
