<template>
    <div class="app-container calendar-list-container">
        <el-row style="height:calc(100% - 20px);" class="clearfix">
            <el-col :span="4" class="person-left">
                <div class="p-title">组织架构</div>
                <div style="height:calc( 100% - 30px );">
                    <el-tabs style="height: 100%" class="person-taps" @tab-click="handleClick" v-model="activeName">
                        <!-- <el-divider style="height:2px;"/> -->
                        <el-tab-pane label="联帕停车">
                            <div style="height: calc(100% - 40px); overflow: auto;margin-top:10px;">
                                <el-tree
                                    :data="rootData"
                                    node-key="id"
                                    ref="tree"
                                    default-expand-all
                                    @node-click='nodeClick'
                                >
                                </el-tree>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="万达停车">
                            <div style="height: calc(100% - 30px); overflow: auto;"></div>
                        </el-tab-pane>
                        <el-tab-pane label="绿地停车">
                            <div style="height: calc(100% - 30px); overflow: auto;"></div>
                        </el-tab-pane>
                        <el-tab-pane label="绿地停车">
                            <div style="height: calc(100% - 30px); overflow: auto;"></div>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </el-col>
            <el-col class="person-right">
                <div class="filter-container clearfix">
                    <!--检索框-->
                    <div class="form_search clearfix" style="padding:0 20px;">
                        <el-form :inline="true" :model="listQuery" class="search-form-inline" :rules="searchRules">
                            <el-form-item label="输入搜索:">
                                <el-input @keyup.enter.native="handleFilter" v-model.trim="listQuery.name" placeholder="请输入姓名/联系方式"></el-input>
                            </el-form-item>
                            <el-form-item label="所属车场:">
                                <el-select v-model="listQuery.parkingLot" placeholder="所属车场" @change="searchCompChange">
                                    <el-option label="全部" value=""></el-option>
                                    <el-option :label="v.label" :value="v.value" v-for="(v,i) in parkingLotList" :key="i"></el-option>
                                </el-select>
                            </el-form-item> 
                            <el-form-item label="所属业务机构:">
                                <el-select v-model="listQuery.company" placeholder="请选择" >
                                    <el-option label="全部" value=""></el-option>
                                    <el-option :label="v.label" :value="v.value" v-for="(v,i) in companyList" :key="i"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" class="el-icon-search" @click="handleFilter" size="mini"></el-button>
                            </el-form-item>
                            <el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-end">
                                <span class="el-icon-refresh-right " style="padding:5px;" @click="refresh"></span >
                            </el-tooltip>
                            <el-tooltip class="item fj-refresh" effect="dark" content="导出" placement="top-end" v-if="userDatas.permissions.includes('spark_person_rm')">
                                <span class="el-icon-download" style="padding:5px;margin-right:10px;" @click="exportTable"></span >
                            </el-tooltip>
                        </el-form>
                    </div>
                    <el-divider />
                    <div class="float-fl table-title" style="margin-left:20px;">人员列表</div>
                    <div style="text-align: right;">
                        <el-button class=""
                            type="primary"
                            @click="handleCreate"
                            icon="edit"
                            size="mini"
                            v-if="userDatas.permissions.includes('spark_person_add')"
                            style="margin-bottom:10px;"
                           >添加人员
                        </el-button>
                    </div>
                </div>
                <div style="height:calc(100% - 130px);margin-left:20px;">
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
                                    <span>{{scope.row.userName}}</span>
                                </template>
                            </el-table-column>

                            <el-table-column align="center" label="姓名">
                                <template slot-scope="scope">
                                    <span>{{scope.row.name}}</span>
                                </template>
                            </el-table-column>

                            <el-table-column align="center" label="联系电话" width="120">
                                <template slot-scope="scope">
                                    <span>{{scope.row.tel}}</span>
                                </template>
                            </el-table-column>

                            <el-table-column align="center" label="所属车场">
                                <template slot-scope="scope">
                                    <span>{{scope.row.organName}}</span>
                                </template>
                            </el-table-column>

                            <el-table-column align="center" label="所属业务机构" width="120">
                                <template slot-scope="scope">
                                    <span>{{scope.row.comName}}</span>
                                </template>
                            </el-table-column>

                            <el-table-column align="center" label="职位">
                                <template slot-scope="scope">
                                    <span>{{scope.row.post}}</span>
                                </template>
                            </el-table-column>

                            <el-table-column align="center" label="创建人">
                                <template slot-scope="scope">
                                    <span>{{scope.row.createName}}</span>
                                </template>
                            </el-table-column>
                            
                            <el-table-column align="center" label="创建时间" width="200">
                                <template slot-scope="scope">
                                    <span>{{scope.row.createTime}}</span>
                                </template>
                            </el-table-column>

                            <el-table-column label="操作" align="center" width="160" v-if="userDatas.permissions.includes('spark_person_edit') ||  userDatas.permissions.includes('spark_person_del')">
                                <template slot-scope="scope">
                                <el-button
                                    size="mini"
                                    type="text"
                                    v-if="userDatas.permissions.includes('spark_person_edit')"
                                    @click="handleUpdate(scope.row)"
                                >编辑</el-button>
                                <el-button
                                    size="mini"
                                    type="text"
                                    v-if="userDatas.permissions.includes('spark_person_del')"
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
                        <div class=" clearfix">
                            <!--  :rules="rules" -->
                            <el-form :model="form" class="clearfix" ref='form' :rules="rules" :inline-message='false'>
                                <el-row :gutter="20">
                                    <!-- <el-col :span="12"><el-form-item label="用户名" :label-width="formLabelWidth"  prop="userName"> 
                                        <el-input v-model="form.userName" autocomplete="off"></el-input>
                                    </el-form-item></el-col> -->
                                    <el-col :span="12"><el-form-item label="姓名:" :label-width="formLabelWidth" prop="name">
                                        <el-input v-model="form.name" autocomplete="off"></el-input>
                                    </el-form-item></el-col>
                                    <el-col :span="12"><el-form-item label="联系电话:" :label-width="formLabelWidth"  prop="tel">
                                        <el-input v-model="form.tel" autocomplete="off" :maxlength="11"></el-input>
                                    </el-form-item></el-col>
                                    
                                    <!-- 企业--》车场--》业务机构 -->  
                                    <el-col :span="12">
                                        <el-form-item label="所属车场:" :label-width="formLabelWidth" prop="organId">
                                            <el-select v-model="form.parkingLot" placeholder="请选择车场" >  <!-- @change="mechanismChange" -->
                                                <el-option label="无" value=""></el-option>
                                                <el-option
                                                    v-for="item in parkingLotList"
                                                    :key="item.value"
                                                    :label="item.label"
                                                    :value="item.value">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                    <!-- 所属机构无 -->
                                    <el-col :span="12">
                                        <el-form-item label="所属业务机构:" :label-width="formLabelWidth" prop="serviceOrganId">  
                                            <el-select v-model="form.company" placeholder="请选择业务机构"> <!-- @change="mechanismChange" -->
                                                <el-option label="无" value=""></el-option>
                                                <el-option
                                                    v-for="item in companyList"
                                                    :key="item.value"
                                                    :label="item.label"
                                                    :value="item.value">
                                                </el-option>
                                            </el-select>
                                            <!-- <el-select v-model="form.serviceOrganId" placeholder="请选择所属机构">
                                                <el-option label="无" value=""></el-option>
                                                <el-option
                                                    v-for="item in sysserviceList"
                                                    :key="item.service_id"
                                                    :label="item.service_name"
                                                    :value="item.service_id">
                                                </el-option>
                                            </el-select> -->
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="职务:" :label-width="formLabelWidth" prop="roleId">
                                            <el-select v-model="form.post" placeholder="请选择职务：" ><!-- @change="mechanismChange" -->
                                                <el-option label="无" value=""></el-option>
                                                <el-option
                                                    v-for="item in departmentList"
                                                    :key="item.value"
                                                    :label="item.label"
                                                    :value="item.value">
                                                </el-option>
                                            </el-select>
                                            <!-- <el-select v-model="form.roleId" placeholder="请选择职务">
                                                <el-option label="无" value=""></el-option>
                                                <el-option
                                                    v-for="item in roleList"
                                                    :key="item.roleId"
                                                    :label="item.roleName"
                                                    :value="item.roleId">
                                                </el-option>
                                            </el-select> -->
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-form-item label="备注信息:" :label-width="formLabelWidth" style="width: 95%;">
                                    <el-input  type="textarea" placeholder="数字不超过200字" v-model="form.remark" autocomplete="off" :maxlength="200" :rows="4"></el-input>
                                </el-form-item>
                            </el-form>
                            
                        </div>
						<div slot="footer" class="dialog-footer">
						    <!-- <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
						    <el-button @click="dialogFormVisible = false">取 消</el-button> -->
						    
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
							<el-button @click="cancel('form')" size="small">取 消</el-button>
						</div>
                    </el-dialog>
                </div>
            </el-col>
        </el-row>   
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
    getDownloadExcel,
} from "@/api/index.js";
// import { fetchTree } from "@/api/basic/menu";
import { mapGetters } from "vuex";
import ZTable from '@/components/ZTable.vue'

export default {
    name: "table_role",
    components: { ZTable },
    directives: {
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
                name:'',
                parkingLot:'',
                company:'',
                tableName:'人员管理'
            },
            roleId: undefined,
            roleCode: undefined,
            deptTypes:[],
            roleList:[],
            activeName:'',
            searchRules:{
                username:[
                    { required: true, message: "用户名不能为空",trigger: "blur"},
                ]
            },
            rules: {
                userName: [
                    { required: true, message: "用户名不能为空",trigger: "blur"},
                    { min: 1, max: 30, message: "长度在 1 到 20 个字符", trigger: "blur" },
                    { validator: checkUserName}
                ],
                name: [
                    { required: true, message: "姓名不能为空",trigger: "blur"},
                    { min: 1, max: 20, message: "长度在 1 到 20 个字符 ",trigger: "blur" },
                    { validator:checkUserRealname, message: "只能输入中文 ",trigger: "blur" }
                ],
                tel:[
                    { required: true, message: "手机号不能为空",trigger: "blur"},
                    { validator: checkMobile, trigger: 'blur'}
                ],
            },
            form: { 
                userName: null,
                name: "",
                tel: null,
                post:null,
                remark:null,
                createBy:'',
                tableName:'',
                parkingLot:'',
                company:'',
            },
            dialogFormVisible: false,
            dialogPermissionVisible: false,
            dialogStatus: "",
            textMap: {
                update: "编辑人员",
                create: "添加用户",
            },
            isTrue:true,
            value:'',
            currentPage:1,
            pageSize:30,
            formLabelWidth:'100px',
            parkingLotList:[ //搜索框车场
                {
                    value: 1,
                    label: '车场1' 
                }, {
                    value: 2,
                    label: '车场2' 
                }
            ],
            companyList:[ //搜索框业务机构
                {
                    value: 1,
                    label: '机构1' 
                }, {
                    value:  2,
                    label: '机构2',
                }
            ],
            departmentList:[ //职务
                {
                    value: 1,
                    label: '职务1' 
                }, {
                    value: 2,
                    label: '职务2',
                }
            ],
            sysserviceList:[],
            permissionsList:[],
            userDatas:{},
            comId:'',
            organId:'',
            meSearchList:[],
            // rootData: [],
            rootData:[{
                label: '车场1',
                value:1,
                    children: [{
                        label: '岗亭组',
                        value:3
                    }]
                }, {
                    label: '车场2',
                    value:2,
               }],
        };
    },
    inject:['reload'],
    created() {
        let me = this;
        me.permissionsList = me.$store.state.munuAuthority;
        me.userDatas = JSON.parse(sessionStorage.getItem('userData'))
        me.getList();
        me.form.createBy = this.userDatas.sysUser.userId;
        if(me.userDatas.sysUser.userId != ''){
            // me.getRoleLists();
        }
    },
    methods: {
        refresh(){ //刷新
            this.reload()
        },
        exportTable(){  //导出
            if( this.list.length == 0 ){
                this.$message.error('当前表格暂无数据，请补充数据再导出！');
            }else{
                getDownloadExcel(this.listQuery,'/spark/companypeople/downloadExcel').then(response => {
                    console.log('responser人员',response)
                });
            }
        },
        //获取树表格当前数组
		handleClick(tab, event){
			// console.log('tabtab',tab.name);
            // let me = this;
            // console.log('treeData',me.treeData)
			// me.treeData.forEach(item=>{
			// 	if(item.name == tab.name){
            //         this.tabId = item.id
			// 		this.checkArr = item.checkArr;
			// 	}
			// })
		},
        getThreeList(roleCode,roleId){
            let _this = this;
            fetchRoleTree(roleCode).then(response => {
                this.rootData = response.data;
            });
        },
        nodeClick(data,tree,items){
            this.listQuery.parkingLot = data.value;
            this.getList();
            // console.log('datadatadata',data)
            // console.log('treetreetree',tree)
            // console.log('itemsitemsitems',items)
        },
        getList() {
            let me = this;
            me.listLoading = true;
            me.$api.getCompanypeopleList(me.listQuery)
            .then(response => {
                this.list = response.data.records;
                this.total = response.data.total;
                this.listLoading = false;
                console.log('responseresponseresponse',this.list)
            })
            .catch(err => {
                console.log(err);
            });
        },
        handleFilter() {
            this.listQuery.page = 1;
            this.listQuery.name = this.listQuery.name.replace(/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g,"");
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
            this.$api.getCompanypeopleInfo(row.id).then(res => {
                let me = this;
                me.form = res.data.data;
                console.log('res.data.data',res.data.data)
                me.dialogFormVisible = true;
                me.dialogStatus = "update";
            });
        },

        // 删除
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
                this.$api.getCompanypeopleDelete(row.id)
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
            console.log(id)
            let params={
                comId:id
            }
            if(id == '全部'){
                this.meSearchList = []
            }
            // getMechanism(params).then(response => {
            //     this.meSearchList = response.data;
            // });
        },
        // getRoleLists(){  //角色下拉列表
        //     getRoleList().then(response => {
        //         this.roleList = response.data;
        //     });
        // },
        mechanismChange(organid){ //车场change事件
            let params={
                organId:organid
            }
            getSysserviceorga(params).then(response => {
                
                this.sysserviceList = response.data;
                this.form.serviceOrganId = 16;
            });
            console.log('organid',organid)
            
        },
        create(formName) { //新增
            const set = this.$refs;
            this.form.postId = this.form.post;
            set[formName].validate(valid => {
                if (valid) {
                this.$api.getCompanypeopleAdd(this.form).then((response) => {
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
                username: null,
                name: null,
                tel: null,
                post:null,
                remark:null,
                createBy:this.userDatas.sysUser.userId,
            }
        },
        //修改
        update(formName) {
            const set = this.$refs;
            console.log('this.form',this.form)
            set[formName].validate(valid => {
                if (valid) {
                this.dialogFormVisible = false;
                this.$api.getCompanypeopleUpdate(this.form).then(() => {
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


.calendar-list-container{
    .person-left{
        border-right: 1px solid #d9d9d9;
        width: 350px;
        height: calc(100% - 10px);
    }
    .person-right{
        width: calc(100% - 350px);
    }
    .person-left,.person-right{
        height: 100%;
    }
    .person-taps{
        border-bottom: 1px solid #d9d9d9;
        padding:10px 0;
        width: 95%;
        margin: 0 auto;

    }   
}
.calendar-list-container .person-taps{
    border-bottom: 0px solid transparent !important;
}

.el-tabs__item.is-active{
    background: #4768f3 !important;
}

::v-deep .el-tab-pane {
    height: calc(100% - 20px) !important;
}
::v-deep .el-tabs__content {
    height: calc(100% - 30px) !important;
}
::v-deep .el-form-item__content{
    line-height: 37px !important;
}
.p-title{
    margin-left:15px;
    text-align:left;
    width:90%;
    font-weight:bold;
    margin:10px;
}
</style>
