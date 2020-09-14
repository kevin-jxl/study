<template>
    <div class="app-container calendar-list-container clearfix" style="background: transparent; padding: 0px;">
        <div style="margin:0 0 10px 15px;font-weight:bold;float:left;">菜单列表</div>
        <div style="height:calc(100% - 32px)" class="menu-main">
            <div class="menu-lefts">
                <!--检索框-->
                <div class="form_search clearfix">
                    <el-form :inline="true" :model="formInline" class="search-form-inline">
                        <el-form-item label="输入搜索:">
                            <el-input
                                size="small"
                                @keyup.enter.native="handleFilter"
                                placeholder="系统/功能名称"
                                v-model.trim="filterText"
                        ></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button class="el-icon-search" @click="onSubmit" size="mini"></el-button>
                        </el-form-item>
                        
                        <el-tooltip
                            class="item fj-refresh"
                            style="margin-left:10px;"
                            effect="dark"
                            content="重置"
                            node-key="id"
                            placement="top-end" 
                        >
                        <span class="el-icon-refresh" style="padding:5px;" @click="resetChecked"></span>
                        </el-tooltip>
                        <el-tooltip class="item fj-refresh" effect="dark" content="刷新" placement="top-end" >
                            <span class="el-icon-refresh-right" style="padding:5px;" @click="refreshChecked"></span>
                        </el-tooltip>
                        <el-tooltip class="item fj-refresh" effect="dark" content="新增" placement="top-end" >
                            <span class="el-icon-plus" style="padding:5px;margin-right:10px;" @click="addMenus"></span>
                        </el-tooltip>
                        <el-tooltip class="item fj-refresh" effect="dark" content="删除" placement="top-end" >
                            <span class="el-icon-delete" style="padding:5px;margin-right:10px;" @click="deleteMenus" v-if="userDatas.permissions.includes('sys_menu_del')"></span>
                        </el-tooltip>
                    </el-form>
                </div>
                <div class="menu-system-main menu-system-radio">
                    <div class="menu-system-left">
                    <p>
                        <i class="el-icon-s-home" style="margin-right:5px;"></i>系统列表
                    </p>
                    <div style="margin-top:10px;">
                        <el-radio-group v-model="radio" @change="systemChanges(item)" v-for="item in rootData" :key="item.id" style="display:block;">
                            <el-radio :label="item.label">{{item.name}}</el-radio>
                        </el-radio-group>
                    </div>
                </div>
                <div class="menu-system-right" style="margin-left: 10px;height:calc(100% - 30px);overflow:auto;">
                    <p>
                        <i class="el-icon-s-home" style="margin-right:5px;"></i>功能列表
                    </p>
                    <div style="margin-top:10px;">
                        <el-tree
                            :data="featureList"
                            show-checkbox
                            node-key="id"
                            :props="defaultProps"
                            :filter-node-method="filterNode"
                            @current-change='getKeys'
                            ref="tree"
                            :check-strictly='true'
                            @check-change="getKeys"
                        ></el-tree>
                    </div>
                </div>
                </div>
            </div>
            <div class="menu-rights">
                <!-- <p class="tab-p">{{titles}}</p> -->
                <p class="tab-p" v-if="dialogStatus=='update'">修改菜单</p>
                <p class="tab-p" v-else>新增菜单</p>
                <div style="margin-top:10px;">
                    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" label-position='right' class="demo-ruleForm" size="medium">
                        <el-row>
                            <el-col :span="24">
                                <!-- <el-form-item label="上级ID" prop="parentId" style="width:50%;">
                                    <el-input v-model="ruleForm.parentId" size="small" placeholder="请输入" ></el-input> 
                                </el-form-item> -->
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="名称" prop="name" style="width:50%;">
                                    <el-input v-model="ruleForm.name" size="small" placeholder="请输入"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="类型" prop="type">
                                    <el-radio-group v-model="ruleForm.type" size="small">
                                        <el-radio label="0" style="border-radius:50%;">菜单</el-radio>
                                        <el-radio label="1">按钮</el-radio>
                                        <el-radio label="9">目录</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="图标" prop="icons" style="width:50%;">
                                    <e-icon-picker size="small"
                                        v-model="ruleForm.icons" 
                                        :options="iconOptions.options" 
                                        :disabled="iconOptions.disabled" 
                                        :readonly="iconOptions.readonly"
                                        :placement="iconOptions.placement" 
                                        :width="iconOptions.width"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="URL" prop="path" style="width:50%;">
                                    <el-input v-model="ruleForm.path" size="small" placeholder="请输入"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24" v-if="ruleForm.type!=9">
                                <el-form-item label="权限标识" prop="permission" style="width:50%;">
                                    <el-input v-model="ruleForm.permission" size="small" placeholder="请输入"></el-input>
                                </el-form-item>
                            </el-col>
                            <!-- <el-col :span="24">
                                <el-form-item label="权限" style="width:40%;">
                                    <el-select v-model="types" placeholder="请选择权限" style="width:100%;" multiple >
                                        <el-option label="管理员" value="1"></el-option>
                                        <el-option label="超级管理员" value="2"></el-option>
                                        <el-option label="超级无敌管理员" value="3"></el-option>
                                        <el-option label="宇宙超级无敌管理员" value="4"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col> -->
                            <el-col :span="24">
                                <el-form-item label="排序" prop="sort" size="small" style="width:50%;">
                                    <el-input v-model="ruleForm.sort" placeholder="请输入"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item style="width:40%;" size="small">
                                    <el-button v-if="dialogStatus=='update' && userDatas.permissions.includes('sys_menu_edit')"
                                        type="primary"
                                        size="small"
                                        @click="updateMenus('ruleForm')">保存
                                    </el-button>
                                    <el-button v-else-if="userDatas.permissions.includes('sys_menu_add')"
                                        type="primary"
                                        size="small"
                                        @click="addSubmitForm('ruleForm')">确 定
                                    </el-button>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
            </div>
        </div>
  </div>
</template>

<script>
import {
    getMenuTree,
    menuInfo,
    addMenu,
    updateMenu,
    delObj
} from "@/api/basic/menu.js";
import { EIconPicker } from 'e-icon-picker'
export default {
    components: {
        EIconPicker,
    },
    data() {
        return {
            radio: 3,
            formInline: {},
            rootData: [],
            defaultProps: {
                children: 'children',
                label: 'label'
            },
            ruleForm: {
                parentId:undefined,
                name:undefined,
                type:undefined,
                icons:undefined,
                path:undefined,
                permission:undefined,
                sort:undefined
            },
            types:[],
            filterText: '',
            featureList:[], //功能列表
            menuInfo:[], //详情
            dialogStatus:"add",
            menuIds:'',//树形选择id
            // 选择图标
            iconOptions:{
                options: {FontAwesome: false, ElementUI: true, addIconList: [], removeIconList: []},
                disabled: false,
                readonly: false,
                placement: 'bottom',
                width: 800
            },
            userDatas:[],
            platformName:'',
            parentId:'',
            // 表单规则
            rules: {
                type: [
                        { required: true, message: '类型不能为空', trigger: 'blur' },
                ],
                // region: [
                //     { required: true, message: '请选择权限', trigger: 'change' }
                // ],
                // type: [
                //     { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
                // ],
                // resource: [
                //     { required: true, message: '请选择活动资源', trigger: 'change' }
                // ],
            },
        };
    },
    inject:['reload'],
    mounted () {
        this.getMenuTrees();
    },
    created () {
        let me = this
        me.userDatas= JSON.parse(sessionStorage.getItem('userData'))
    },
    methods: {
        onSubmit() {},
        systemChanges(item) {
            this.featureList = item.children;
        },
        addMenus(){
            let me = this;
            if(me.menuIds== ''){
                me.$message('请选择要新增的菜单');
            }
            me.ruleForm = {
                parentId:undefined,
                name:undefined,
                type:undefined,
                icons:undefined,
                path:undefined,
                permission:undefined,
                sort:undefined
            }
            me.dialogStatus = "add";
            me.getMenuTrees();
        },
        // 新增菜单提交
        addSubmitForm(formName) {
            let me = this;
            me.ruleForm.parentId = me.parentId;
            if(me.ruleForm.parentId == ''){
                me.ruleForm.parentId = '1'
            }else{
                me.ruleForm.parentId = me.parentId
            }
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    addMenu(me.ruleForm).then(response => {
                        me.$message({
                            message: '恭喜你，新增成功',
                            type: 'success'
                        });
                        me.refreshChecked()
                    });
                    
                } else {
                    return false;
                }
            });
        },
        refreshChecked(){
            this.reload()
        },
        deleteMenus(){  //删除
            if(this.menuIds== ''){
                this.$message('请选择要删除的菜单');
            }else{
                this.$confirm(
                    "确定要删除该条信息吗?",
                    "提示框",
                    {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }
                ).then(() => {
                    delObj(this.menuIds)
                    .then(response => {
                        this.$notify({
                            title: "成功",
                            message: "删除成功",
                            type: "success",
                            duration: 2000
                        });
                        this    .refreshChecked()
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
            }
            
        },
        // 修改
        updateMenus(formName){
            let me = this;
            me.$refs[formName].validate((valid) => {
            if (valid) {
                updateMenu(me.ruleForm).then(response => {
                    me.$message({
                        message: '恭喜你，保存成功',
                        type: 'success'
                    });
                    // me.ruleForm = {}
                });
            }   else {
                    return false;
                }
            });
        },

        // 加载全部树形列表
        getMenuTrees(){
            getMenuTree().then(response => {
                this.rootData = response.data
            });
        },
        // 搜索tree
        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        // 重置
        resetChecked() {
            this.$refs.tree.setCheckedKeys([]);
        },

        // 功能列表树形选择
        getCheckedKeys (data, keys, key) {
            var res = [];
            recursion(data, false);
            return res;
            // arr -> 树形总数据
            // keys -> getCheckedKeys获取到的选中key值
            // isChild -> 用来判断是否是子节点
            function recursion (arr, isChild) {
                var aCheck = [];
                for ( var i = 0; i < arr.length; i++ ) {
                    var obj = arr[i];
                    aCheck[i] = false;

                    if ( obj.children ) {
                        aCheck[i] = recursion(obj.children, true) ? true : aCheck[i];
                        if ( aCheck[i] ) {
                            res.push(obj[key]);
                        }
                    }
                    for ( var j = 0; j < keys.length; j++ ) {
                        if ( obj[key] == keys[j] ) {
                            aCheck[i] = true;
                            if ( res.indexOf(obj[key]) == -1 ) {
                                res.push(obj[key]);
                            }
                            break;
                        }
                    }
                }
                if ( isChild ) {
                    return aCheck.indexOf(true) != -1;
                }
            }
        },
        getKeys (data, checked, indeterminate) {
            let me = this;
            var aKey = me.getCheckedKeys(me.rootData, me.$refs.tree.getCheckedKeys(), 'id');
            if (checked) {
                let arr = [data.id];
                me.$refs.tree.setCheckedKeys(arr);
                let menuId = data.id;
                this.menuIds = data.id;
                this.dialogStatus = "update";
                this.platformName = data.name;
                menuInfo(menuId).then(response => {
                    me.ruleForm = response.data;
                    me.parentId = response.data.menuId
                    console.log('当前',response.data)
                });
            }else{
                me.ruleForm = {}
                me.dialogStatus = "";
                me.parentId = '1'
            }
        },
  
    },
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val);
        },
    },
};
</script>

<style lang="scss" scoped>

.menu-main {
    width: 100%;
    margin-top: 10px;
    display: flex;
    .tab-p{
        text-align: left;
        margin-left: 7px;
    }
    .menu-lefts,
    .menu-rights {
        flex: 1;
        background: rgb(255, 255, 255);
        padding: 10px;
        // border: 1px solid #e8e8e8;

        .menu-system-main {
            height: calc(100% - 62px);
            display: flex;
            .menu-system-left,
            .menu-system-right {
                flex: 1;
                height: calc(100% - 20px);
                // border: 1px solid #e8e8e8;
                text-align: left;
                padding: 10px;
                p {
                    color: #606266 !important;
                    font-size: 14px;
                }
                .el-radio {
                        display: block !important;
                        margin-top: 10px;
                    .el-radio__inner {
                        // border-radius: none !important;
                    }
                }
            }
            .menu-system-right{
                height: calc(100% - 20px);
            }
        }
    }
    .menu-lefts {
        margin-right: 5px;

    }
    .tab-p{
        text-align:left;
        margin-left:7px;
    }
    
}

</style>