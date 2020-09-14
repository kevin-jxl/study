<template>
    <div class="app-container calendar-list-container">
        <div class="filter-container crud-menu clearfix">
            <!--检索框-->
            <div class="form_search clearfix" style="padding:0 10px;">
                <el-form :inline="true" :model="listQuery" class="search-form-inline" :rules="searchRules">
                    <el-form-item label="输入搜索:">
                        <el-input @keyup.enter.native="handleFilter" v-model.trim="listQuery.username" placeholder="请输入用户名/姓名"></el-input>
                    </el-form-item>
                    <el-form-item label="所属企业:">
                        <el-select v-model="listQuery.companyId" placeholder="所属企业" @change="searchCompChange">
                            <el-option label="全部" value=""></el-option>
                            <el-option :label="v.name" :value="v.com_id" v-for="(v,i) in deptTypes" :key="i"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="所属车场:">
                        <el-select v-model="listQuery.organId" placeholder="所属车场" >
                            <el-option label="全部" value=""></el-option>
                            <el-option :label="v.name" :value="v.organ_id" v-for="(v,i) in meSearchList" :key="i"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" class="el-icon-search" @click="handleFilter" size="mini"></el-button>
                    </el-form-item>
                    <el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-end">
                        <span class="el-icon-refresh-right " style="padding:5px;" @click="refresh"></span >
                    </el-tooltip>
                    <el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-end" v-if="userDatas.permissions.includes('sys_user_rm')">
                        <span class="el-icon-download" style="padding:5px;margin-right:10px;" @click="exportTable"></span >
                    </el-tooltip>
                </el-form>
            </div>
            <el-divider />
            <div class="float-fl table-title">用户列表</div>
            <div  style="text-align: right;">
                <el-button class=""
                    type="primary"
                    @click="handleCreate"
                    icon="edit"
                    size="mini"
                    style="margin-bottom:10px;"
                    v-if="userDatas.permissions.includes('sys_user_add')">添加用户
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
                    <el-table-column align="center" type="index" width='100' label="序号" fixed></el-table-column>
                    <el-table-column align="center" label="用户名">
                        <template slot-scope="scope">
                            <span>{{scope.row.username}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="姓名">
                        <template slot-scope="scope">
                            <span>{{scope.row.userRealname}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="联系电话">
                        <template slot-scope="scope">
                            <span>{{scope.row.phone}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="所属企业">
                        <template slot-scope="scope">
                            <span>{{scope.row.companyName}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="所属车场">
                        <template slot-scope="scope">
                            <span>{{scope.row.organName}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="所属角色">
                        <template slot-scope="scope">
                            <span>{{scope.row.roleName}}</span>
                        </template>
                    </el-table-column>
                    
                    <el-table-column align="center" label="添加时间" width="200">
                        <template slot-scope="scope">
                            <span>{{scope.row.createTime}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="职务">
                        <template slot-scope="scope">
                            <span>{{scope.row.postName}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" label="最后登录"  width='200'>
                        <template slot-scope="scope">
                            <span>{{scope.row.lastLoginDate}}</span>
                        </template>
                    </el-table-column> 

                    <el-table-column align="center" label="是否启用">
                        <template slot-scope="scope">
                            <el-switch
                                v-model="scope.row.userFlag"
                                @change="switchChange(scope.row)"
                            >
                            </el-switch>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="280" v-if="userDatas.permissions.includes('sys_user_edit') ||  userDatas.permissions.includes('sys_user_del')">
                        <template slot-scope="scope">
                        <el-button
                            size="mini"
                            type="text"
                            v-if="userDatas.permissions.includes('sys_user_edit')"
                            @click="handleUpdate(scope.row)"
                        >编辑</el-button>
                        <el-button
                            size="mini"
                            type="text"
                            v-if="userDatas.permissions.includes('sys_user_del')"
                            @click="handleDelete(scope.row)"
                        >删除</el-button>
                        </template>
                    </el-table-column>
                </div>
            </z-table>
        </div>

        <!-- 新建 编辑 弹框 -->
        <div class="dialog_mask_mine">
            <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false"  width="35%">
                <div slot="title">
                    <span class="preTitle"></span>
                    <span class="nextTitle">{{textMap[dialogStatus]}}</span>
                </div>
				<div class="fix-width clearfix">
                    <!--  :rules="rules" -->
                    <el-form :model="form" class="clearfix" ref='form' :rules="rules" :inline-message='false'>
                        <el-row :gutter="20">
                            <!-- <el-col :span="12"><el-form-item label="用户名" :label-width="formLabelWidth"  prop="username"> 
                                <el-input v-model="form.username" autocomplete="off"></el-input>
                            </el-form-item></el-col> -->
                             <el-col :span="12"><el-form-item label="姓名" :label-width="formLabelWidth" prop="userRealname">
                                <el-input v-model="form.userRealname" autocomplete="off"></el-input>
                            </el-form-item></el-col>
                             <el-col :span="12"><el-form-item label="员工编号" :label-width="formLabelWidth" prop="empno">
                                <el-input v-model="form.empno" autocomplete="off"></el-input>
                            </el-form-item></el-col>
                             <el-col :span="12"><el-form-item label="联系电话" :label-width="formLabelWidth"  prop="phone">
                                <el-input v-model="form.phone" autocomplete="off" :maxlength="11"></el-input>
                            </el-form-item></el-col>
                            <el-col :span="12">
                                <el-form-item label="所属企业" :label-width="formLabelWidth"  prop="companyId">
                                    <el-select v-model="form.companyId" @change="companyChange" clearable placeholder="请选择所属企业">
                                        <el-option label="无" value=""></el-option>
                                        <el-option
                                            v-for="item in deptTypes"
                                            :key="item.com_id"
                                            :label="item.name"
                                            :value="item.com_id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                             <!-- 企业--》车场--》业务机构 -->  
                            <el-col :span="12">
                                <el-form-item label="所属车场" :label-width="formLabelWidth" prop="organId">
                                    <el-select v-model="form.organId" placeholder="请选择车场" @change="mechanismChange">
                                        <el-option label="无" value=""></el-option>
                                        <el-option
                                            v-for="item in mechanism"
                                            :key="item.organ_id"
                                            :label="item.name"
                                            :value="item.organ_id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <!-- 所属机构无 -->
                            <el-col :span="12">
                                <el-form-item label="所属机构" :label-width="formLabelWidth" prop="serviceOrganId">  
                                    <el-select v-model="form.serviceOrganId" placeholder="请选择所属机构" @change="$forceUpdate()">
                                        <el-option label="无" value=""></el-option>
                                        <el-option
                                            v-for="item in sysserviceList"
                                            :key="item.service_id"
                                            :label="item.service_name"
                                            :value="item.service_id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="所属角色" :label-width="formLabelWidth" prop="roleId">
                                    <el-select v-model="form.roleId" placeholder="请选择所属角色">
                                        <el-option label="无" value=""></el-option>
                                        <el-option
                                            v-for="item in roleList"
                                            :key="item.roleId"
                                            :label="item.roleName"
                                            :value="item.roleId">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="初始密码" :label-width="formLabelWidth" prop="password">
                                    <el-input v-model="form.password" type="password" autocomplete="off"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="职务" :label-width="formLabelWidth" prop="postName">
                                    <el-input v-model="form.postName" autocomplete="off"></el-input>
                                </el-form-item>
                            </el-col>
                            
                        </el-row>
                        <el-form-item label="备注信息" :label-width="formLabelWidth" style="width: 95%;">
                            <el-input  type="textarea" placeholder="数字不超过200字" v-model="form.remark" autocomplete="off" :maxlength="200" :rows="4"></el-input>
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
                                    @click="update('form')">保存
                        </el-button>
                    </div>
                </div>
			</el-dialog>
        </div>

        <!-- 弹框 -->
        <el-drawer :title="textMap[dialogStatus]" :visible.sync="dialogPermissionVisible" class="role_menu">
            <div slot="title">
                <span class="titleSpan">{{textMap[dialogStatus]}}</span>
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
    fetchList,
    getObj,
    addObj,
    putObj,
    delObj,
    permissionUpd,
    fetchRoleTree,
    getSyscompany, //企业下拉
    getRoleList,//角色
    getMechanism,//车厂下拉
    getSysserviceorga,//机构下拉
    getDownloadExcel, //导出
} from "@/api/basic/users";
import { fetchTree } from "@/api/basic/menu";
import { mapGetters } from "vuex";
import ZTable from '@/components/ZTable.vue'
import waves from "@/directive/waves/index.js"; // 水波纹指令

export default {
    name: "table_role",
    components: { ZTable },
    directives: {
        waves
    },
    computed: {
    },
    data() {
        var checkMobile = (rule, value, cb) => {
            const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
            if (regMobile.test(value)) {
                return cb() 
            }
            cb(new Error('手机号码格式不正确'))
        };
        const checkNum = (rule, value, cb) => {
            const regCode = /^[0-9a-zA-Z]+$/;
            if (regCode.test(value)) {
            return cb()
            }
            cb(new Error('仅支持字母和数字输入'))
        };
        const checkUserRealname = (rule, value, cb) => {
            const regName = /^[\u4E00-\u9FA5]{0,}$/;
            if (regName.test(value)) {
            return cb()
            }
            cb(new Error('仅支持中文汉字输入'))
        };
        var checkUserName = (rule, value, cb) => {
            const regUser = /^[a-zA-Z]+$/;
            if (regUser.test(value)) {
                return cb()
            }
            cb(new Error('只能输入英文'))
        };
        return {
            treeData: [],
            checkedKeys: [],
            defaultProps: {
                children: "children",
                label: "name"
            },
            menuIds: "",
            list: null,
            total: 0,
            listLoading: true,
            listQuery: {
                page: 1,
                limit: 30,
                username:'',
                companyId:'',
                organId:'',
                tableName:'用户管理'
            },
            roleId: undefined,
            roleCode: undefined,
            deptTypes:[],
            roleList:[],
            searchRules:{
                username:[
                    { required: true, message: "用户名不能为空",trigger: "blur"},
                    // { validator (rule, value, callback, source, options) {
                    //         const errors = [];
                    //         var pattern = new RegExp("[`~!@#$^&*=|{}':;',\\[\\]<>《》/?~！@#￥……&*|{}【】‘；：”“'。，、？' ']");
                    //         if(pattern.test(value)){
                    //             errors.push('不支持特殊字符')
                    //         }else if(value.indexOf(" ") != -1){
                    //             errors.push('不支持特殊字符')
                    //         }
                    //         callback(errors)
                    //     } 
                    // }
                ]
            },
            rules: {
                username: [
                    { required: true, message: "用户名不能为空",trigger: "blur"},
                    { min: 1, max: 30, message: "长度在 1 到 20 个字符", trigger: "blur" },
                    { validator: checkUserName}
                ],
                userRealname: [
                    { required: true, message: "姓名不能为空",trigger: "blur"},
                    { min: 1, max: 20, message: "长度在 1 到 20 个字符 ",trigger: "blur" },
                    { validator:checkUserRealname, message: "只能输入中文 ",trigger: "blur" }
                ],
                password: [
                    { required: true,message: "密码不能为空",trigger: "blur"},
                ],
                phone:[
                    { required: true, message: "手机号不能为空",trigger: "blur"},
                    { validator: checkMobile, trigger: 'blur'}
                ],
                empno:[
                    { required: true, message: "员工编号不能为空",trigger: "blur"},
                    { validator: checkNum, trigger: 'blur'}
                ],
                // roleCode: [
                //     {
                //         required: true,
                //         message: "角色标识",
                //         trigger: "blur"
                //     },
                //     {
                //         min: 3,
                //         max: 20,
                //         message: "长度在 3 到 20 个字符",
                //         trigger: "blur"
                //     }
                // ]
            },
            form: { 
                username: "",
                userRealname: "",
                empno: "",
                phone: "",
                companyId:"",
                organId:"",
                postName:"",
                roleId: "",
                region: "",
                password: "",
                remark:"",
                userId:"",
                serviceOrganId:"",
                tenantId:""
            },
            dialogFormVisible: false,
            dialogPermissionVisible: false,
            dialogStatus: "",
            textMap: {
                update: "编辑",
                create: "添加用户",
            },
            isTrue:true,
            value:'',
            value1:'',
            currentPage:1,
            pageSize:30,
            formLabelWidth:'120px',
            mechanism:[],
            sysserviceList:[],
            permissionsList:[],
            userDatas:{},
            comId:'',
            organId:'',
            meSearchList:[]
        };
    },
    inject:['reload'],
    created() {
        let me = this;
        me.permissionsList = me.$store.state.munuAuthority;
        me.userDatas = JSON.parse(sessionStorage.getItem('userData'))
        me.getList();
        me.form.userId = this.userDatas.sysUser.userId;
        
        if(me.userDatas.sysUser.userId != ''){
            me.getSyscompanys();
            me.getRoleLists();
        }
        if(this.userDatas.sysUser.tenantId == null){
            me.form.tenantId = ''
        }else{
            me.form.tenantId = this.userDatas.sysUser.tenantId;
        }
    },
    mounted(){
        let me = this;
        me.form.userId = this.userDatas.sysUser.userId;
        if(me.userDatas.sysUser.userId != ''){
            me.getSyscompanys();
            me.getRoleLists();
        }
		console.log(me.form)
		
    },
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
            let me = this;
            me.listLoading = true;
            fetchList(this.listQuery).then(response => {
                me.list = response.data.records;
                me.list.forEach(item=>{
                    if(item.userFlag==1){
                        item.userFlag=true
                    }else if(item.userFlag==0){
                        item.userFlag=false
                    }
                })
                me.total = response.data.total;
                me.listLoading = false;
            });
        },
        handleFilter() {
            this.listQuery.page = 1;
            this.getList();
            this.listQuery.username = this.listQuery.username.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
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
            getObj(row.userId).then(res => {
                let me = this;
                    me.form = res.data.data;
                    me.form.password='******'
                    me.dialogFormVisible = true;
                    me.dialogStatus = "update";
                let params1 ={ tenantId:me.form.tenantId }
                getSyscompany(params1).then(responses => {
                    me.deptTypes = responses.data;
                    let params = { comId:res.data.data.companyId };
                    getMechanism(params).then(response => {  //车场
                        me.mechanism = response.data;
                        let params2 = { organId:res.data.data.organId };
                        getSysserviceorga(params2).then(response => {
                            me.sysserviceList = response.data;
                        });
                    });
                });
                me.getRoleLists()
            });
        },
        //切换开启关闭服务
        switchChange(row){
            let me=this,params ={
                userId:row.userId,
                userFlag:row.userFlag?1:0
            }
            console.log('row',row)
            putObj(params).then(() => {
                // this.getList();
                this.$notify({
                    title: "成功",
                    message: "修改成功",
                    type: "success",
                    duration: 2000
                });
            });
        },

        // 删除
        handleDelete(row) {
            console.log('row.userId',row.userId)
            this.$confirm(
                "确定要删除该条信息吗?",
                "提示框",
                {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }
            ).then(() => {
                delObj(row.userId)
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
        searchCompChange(id){
            // console.log(id)
            let params={
                comId:id
            }
            this.listQuery.organId = null
            getMechanism(params).then(response => {
                this.meSearchList = response.data;
            });
        },
        companyChange(comid){  //企业change事件
            let params = {
                comId:comid
            }
            getMechanism(params).then(response => {
                this.mechanism = response.data;
            });
			this.form.organId = null
			this.form.serviceOrganId = null
			// this.reset('organId')
			// this.$refs['form'].resetFields()
			// this.reset('serviceOrganId')
        },
        getSyscompanys(){  //企业下拉列表
            let me = this,
            params ={
                tenantId:me.form.tenantId
            }
            getSyscompany(params).then(response => {
                this.deptTypes = response.data;
            });
        },
        getRoleLists(){  //角色下拉列表
            getRoleList().then(response => {
                this.roleList = response.data;
            });
        },
        mechanismChange(organid){ //车场change事件
            let params={
                organId:organid
            }
			// this.form.serviceOrganId = null
            getSysserviceorga(params).then(response => {
                // this.form.serviceOrganId = 16;
				this.sysserviceList = response.data
            });
			// this.reset('serviceOrganId')
			this.form.serviceOrganId = null
			this.$forceUpdate()
        },
		reset(name){
			this.$refs['form'].fields.forEach(function (e) {
				console.log(e,e.prop)
			      if(e.prop == name){
			          e.resetField()
			      }
			 })
		},
        create(formName) { //新增
		
            const set = this.$refs;
            console.log('set',set[formName])
            this.form.postId = this.form.post;
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
        // 新增
        handleCreate() {
            this.resetTemp()
            this.dialogStatus = 'create'
            this.dialogFormVisible = true
        },
        // 重置
        resetTemp() {
            this.form = {
                id: undefined,
                roleName: undefined,
                roleCode: undefined,
                roleDesc: undefined
            }
        },
        //修改
        update(formName) {
            const set = this.$refs;
            set[formName].validate(valid => {
                if (valid) {
                this.dialogFormVisible = false;
                this.form.password = null;
				this.form.createTime = null
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
        }
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
::v-deep .el-divider--horizontal {
    margin-top: 0px;
}
::v-deep .el-form-item {
    margin-bottom: 20px !important;
}
</style>
