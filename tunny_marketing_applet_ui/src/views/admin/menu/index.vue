<template>
  <div class="app-container calendar-list-container" style="height:calc(100% - 30px)">
    <basic-container style="height:100%">
      <!-- 增加 -->
      <div class="dialog_mask dialog_mask_mine">
        <el-dialog v-if="dialogFormVisible"
                   :visible.sync="dialogFormVisible"
                   width="30%">
          <el-dialog
            title="请选择图标"
            :visible.sync="innerVisible"
            class="innerDialog"
            append-to-body>
            <div slot="title" class="drawer_title">
              <span class="titleSpan">请选择图标</span>
            </div>
            <div class="dialog_main">
              <div v-for="item in website.iconList"
                   class="icon_item"
                   :class="{icon_selected:selectedIcon===item}"
                   @click="selectedIcon=item"
                   @dblclick="innerDoubleClick(item)"
              >
                <i class="tunnyicon" :class="item"></i>
                <span class="icon_title">{{item}}</span>
              </div>
            </div>
            <div slot="footer" class="dialog-footer">
              <el-button @click="innerVisible=false" size="small">取 消</el-button>
              <el-button type="primary"
                         @click="iconSelected" size="small">确 定
              </el-button>
            </div>
          </el-dialog>
          <div slot="title">
            <span class="preTitle"></span>
            <span class="nextTitle">{{textMap[dialogStatus]}}</span>
          </div>
          <el-form :model="form"
                   size="mini"
                   ref="form">
            <el-form-item label="上级菜单"
                          :label-width="formLabelWidth"
                          prop="menuList"
                          size="small">
              <el-cascader
                v-model="menuList"
                placeholder="试试搜索：菜单管理"
                :options="treeData"
                filterable
                change-on-select
                :show-all-levels="false"
              ></el-cascader>
            </el-form-item>
            <el-form-item label="菜单类型" :label-width="formLabelWidth">
              <el-radio-group v-model="radio" @change="sourceFilter">
                <el-radio :label="1">目录</el-radio>
                <el-radio :label="2">菜单</el-radio>
                <el-radio :label="3">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>

          <template v-if="radio == '1'">
            <el-form :model="catalog"
                     size="mini"
                     ref="catalog"
                     :rules="rulesCatalog">
              <el-form-item label="菜单名称" :label-width="formLabelWidth" prop="name" key="catalogName">
                <el-input v-model="catalog.name" auto-complete="off" placeholder="请输入菜单名称"></el-input>
              </el-form-item>
              <el-form-item label="图标标识" :label-width="formLabelWidth" prop="icons" key="catalogIcons">
                <el-input v-model="catalog.icons" readonly="true" placeholder="请点击右侧按钮选择图标">
                  <el-button slot="prepend" size="mini">
                    <i v-if="catalog.icons" class="tunnyicon" :class="catalog.icons"></i>
                  </el-button>
                  <el-button slot="append" size="mini" @click="openInnerDialog">
                    <i class="el-icon-plus"></i>
                  </el-button>
                </el-input>
              </el-form-item>
              <el-form-item label="显示排序" :label-width="formLabelWidth" prop="sort" key="catalogSort">
                <el-input-number size="mini" v-model="catalog.sort" :controls="false"></el-input-number>
              </el-form-item>
              <el-form-item label="前端组件" :label-width="formLabelWidth" prop="component" key="catalogComponent">
                <el-select
                  v-model="catalog.component"
                  filterable
                  allow-create
                  default-first-option
                  placeholder="请选择/新增前端组件">
                  <el-option
                    v-for="item in catalogComponents"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    <span style="float: left">{{ item.value }}</span>
                    <span style="float: right; color: #8492a6; font-size: 12px">{{ item.label }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="前端地址" :label-width="formLabelWidth" prop="path" key="catalogPath">
                <el-input v-model="catalog.path" auto-complete="off"
                          placeholder="请输入前端地址，根目录以/开始"
                ></el-input>
              </el-form-item>
            </el-form>
          </template>

          <template v-else-if="radio == '2'">
            <el-form :model="menu"
                     size="mini"
                     ref="menu"
                     :rules="rulesMenu">
              <el-form-item label="菜单名称" :label-width="formLabelWidth" prop="name" key="menuName">
                <el-input v-model="menu.name" auto-complete="off" placeholder="请输入菜单名称"></el-input>
              </el-form-item>
              <el-form-item label="权限标识" :label-width="formLabelWidth" prop="permission" key="menuPermission">
                <el-input v-model="menu.permission" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="图标标识" :label-width="formLabelWidth" prop="icons" key="menuIcons">
                <el-input v-model="menu.icons" readonly="true"  placeholder="请点击右侧按钮选择图标">
                  <el-button slot="prepend" size="mini">
                    <i v-if="menu.icons" class="tunnyicon" :class="menu.icons"></i>
                  </el-button>
                  <el-button slot="append" size="mini" @click="openInnerDialog">
                    <i class="el-icon-plus"></i>
                  </el-button>
                </el-input>
              </el-form-item>
              <el-form-item label="显示排序" :label-width="formLabelWidth" prop="sort" key="menuSort">
                <el-input-number size="mini" v-model="menu.sort" :controls="false"></el-input-number>
              </el-form-item>
              <el-form-item label="前端组件" :label-width="formLabelWidth" prop="component" key="menuComponent">
                <el-input v-model="menu.component" auto-complete="off"  placeholder="请输入前端组件路径"></el-input>
              </el-form-item>
              <el-form-item label="前端地址" :label-width="formLabelWidth" prop="path" key="menuPath">
                <el-input v-model="menu.path" auto-complete="off" placeholder="请输入前端地址"></el-input>
              </el-form-item>
            </el-form>
          </template>
          <template v-else>
            <el-form :model="btn"
                     size="mini"
                     ref="btn"
                     :rules="rulesBtn">
              <el-form-item label="菜单名称" :label-width="formLabelWidth" prop="name" key="btnName">
                <el-input v-model="btn.name" auto-complete="off" placeholder="请输入菜单名称"></el-input>
              </el-form-item>
              <el-form-item label="权限标识" :label-width="formLabelWidth" prop="permission" key="btnPermission">
                <el-input v-model="btn.permission" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="显示排序" :label-width="formLabelWidth" prop="sort" key="btnSort">
                <el-input-number size="mini" v-model="btn.sort" :controls="false"></el-input-number>
              </el-form-item>
            </el-form>
          </template>
          <div slot="footer" class="dialog-footer">
            <el-button @click="cancel('form')" size="small">取 消</el-button>
            <el-button v-if="dialogStatus=='create'"
                       type="primary"
                       @click="create('form')" size="small">确 定
            </el-button>
            <el-button v-else
                       type="primary"
                       @click="update('form')" size="small">修 改
            </el-button>
          </div>
        </el-dialog>
      </div>
      <!-- 主体部分 -->
      <el-row :gutter="20" style="height:100%">
        <!-- 左边树形图 -->
        <el-col :span="5"
                style="height:100%; border:1px solid #e5e5e5; border-radius:3px; padding:5px;"
                class="zTree_custom">
          <zTree
            :zNodes="treeData"
            style="height:98%;  border:none;"
            :search-enable="true"
            search-placeholder="请输入菜单名称查找"
            :expand-all="true"
            @tree-click="zTreeOnClicks"
            @tree-search="zTreeOnSearch">
          </zTree>
        </el-col>
        <!-- 右半边 -->
        <el-col :span="19"
                style='margin-top:10px;height: 100%'>
          <!-- 新增按钮 -->
          <div class="filter-container clearfix">
              <el-button size="mini"
                         @click="handlerAdd"
                         class="fr">新增
              </el-button>
          </div>
          <!-- 表格 -->
          <el-table
            :data="tableData"
            tooltip-effect="dark"
            style="width: 100%;font-size: 12px;margin-bottom:10px;border-radius:3px;"
            fit
            :header-cell-style="tableHeaderColor"
            :row-style="rowClass"
            :cell-style="cellClass"
            height="calc(100% - 80px)"
          >
            <el-table-column
              align="center"
              type="index"
              width="50"
              label="序号">
            </el-table-column>
            <el-table-column
              align="center"
              prop="name"
              label="菜单名称"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.name:'' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="permission"
              label="权限标识"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.permission:'' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="icons"
              label="图标"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.icons:'' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="type"
              label="类型"
              show-overflow-tooltip>
              <template slot-scope="scope">
                {{scope.row.type | statusFilter}}
              </template>
            </el-table-column>
            <el-table-column
              prop="component"
              label="前端组件"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.component:'' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="path"
              label="前端地址"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row?scope.row.path:'' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="parentName"
              label="上级菜单"
              show-overflow-tooltip>
            </el-table-column>
            <!-- <el-table-column
              prop="delFlag"
              label="当前状态"
              show-overflow-tooltip>
              <template slot-scope="scope">
                {{scope.row.delFlag | currentStatusFilter}}
              </template>
            </el-table-column> -->
            <el-table-column label="操作"
                             header-align="center">
              <template slot-scope="scope">
                <el-button
                  v-if="menuManager_btn_edit"
                  @click="handleEdit(scope.$index, scope.row)"
                  size="small"
                  type="text"
                  plain
                  class="button_edit">编辑
                </el-button>
                <el-button
                  v-if="menuManager_btn_del"
                  @click="handleDelete(scope.$index, scope.row)"
                  size="small"
                  type="text"
                  plain
                  class="button_delete">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <el-pagination
            class="pull-center"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="listQuery.page"
            :page-sizes="[10, 20, 30, 50]"
            :page-size="listQuery.limit"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            background
            prev-text=" <<上一页 "
            next-text=" 下一页>> ">
          </el-pagination>
        </el-col>
      </el-row>
    </basic-container>
  </div>
</template>
<script>
  import {addObj, delObj, fetchTree, getObj, putObj, fetchList, getParents} from '@/api/menu'
  import {mapGetters} from 'vuex'
  import zTree from '@/components/common/zTree'

  export default {
    name: 'menu',
    components: {zTree},
    data() {
      return {
        menuList: [], //新增菜单上级菜单默认显示,默认值通过数组的方式指定。当前节点id到祖节点id的集合
        menuIdTable: undefined, //点击编辑时获取返回的menuId,提交到到form
        list: null,
        total: null, //分页
        listQuery: {//分页限制属性
          parentId: undefined,
          menuSearch: undefined,
          page: 1,
          limit: 10
        },
        treeData: [],//树形图&新增框
        form: {//像后台提交的form表单
          permission: undefined,
          name: undefined,
          menuId: undefined,
          parentId: undefined,
          icons: undefined,
          sort: undefined,
          component: undefined,
          type: undefined,
          path: undefined,
          prev: undefined,

        },
        catalog: {//目录
          menuId: undefined,
          name: undefined,
          icon: undefined,
          sort: undefined,
          component: undefined,
          path: undefined,
        },
        rulesCatalog: {
          name: [{
            required: true,
            message: "请输入菜单名称",
            trigger: "blur"
          }],
          icons: [{
            required: true,
            message: "请输入图标标识",
            trigger: "blur"
          }],
          sort: [{
            required: false,
            message: "请输入显示排序",
            trigger: "blur"
          }],
          component: [{
            required: true,
            message: "请输入前端组件",
            trigger: "blur"
          }],
          path: [{
            required: true,
            message: "请输入前端地址",
            trigger: "blur"
          }],
        },
        menu: {//菜单
          menuId: undefined,
          name: undefined,
          permission: undefined,
          icons: undefined,
          sort: undefined,
          component: undefined,
          path: undefined,
        },
        rulesMenu: {
          name: [{
            required: true,
            message: "请输入菜单名称",
            trigger: "blur"
          }],
          permission: [{
            required: false,
            message: "请输入权限标识",
            trigger: "blur"
          }],
          icons: [{
            required: false,
            message: "请输入图标标识",
            trigger: "blur"
          }],
          sort: [{
            required: false,
            message: "请输入显示排序",
            trigger: "blur"
          }],
          component: [{
            required: true,
            message: "请输入前端组件",
            trigger: "blur"
          }],
          /*path: [{
            required: true,
            message: "请输入前端地址",
            trigger: "blur"
          }],*/
        },
        btn: {//按钮
          menuId: undefined,
          name: undefined,
          permission: undefined,
          sort: undefined,
        },
        rulesBtn: {
          name: [{
            required: true,
            message: "请输入菜单名称",
            trigger: "blur"
          }],
          permission: [{
            required: true,
            message: "请输入权限标识",
            trigger: "blur"
          }],
          sort: [{
            required: false,
            message: "请输入显示排序",
            trigger: "blur"
          }],
        },
        currentId: -1,//当前树形图父节点
        dialogFormVisible: false, // 新增/编辑悬浮窗显示隐藏
        formLabelWidth: '80px', //新增/编辑悬浮窗 label宽度
        radio: 1, // 目录1 菜单2 按钮3
        tableData: [], //表格数据
        dialogStatus: "",//弹窗标题状态
        textMap: {//弹窗标题状态
          update: "编辑菜单",
          create: "新增菜单",
        },
        selectId: undefined,//当前选中的节点ID
        selectName: undefined,//当前选中的节点名称
        rowClass: {//行的 style 的回调方法
          height: '25px'
        },
        cellClass: {//列的 style 的回调方法
          padding: '7px 0px'
        },
        selectedOptions: [],//新增菜单上级菜单默认显示,默认值通过数组的方式指定。
        menuManager_btn_add: false,
        menuManager_btn_edit: false,
        menuManager_btn_del: false,
        innerVisible:false,
        selectedIcon:'',
        catalogComponents:[
          {
            value:'Layout',
            label:'主布局'
          },
          {
            value:'secondaryLayout',
            label:'空白布局'
          }
        ]
      }
    },
    filters: {
      statusFilter(status) {
        const statusMap = {
          0: "菜单",
          1: "按钮",
          9: "目录"
        };
        return statusMap[status];
      },
      currentStatusFilter(status) {
        const statusMap = {
          0: "正常",
          1: "删除",
        };
        return statusMap[status];
      },
    },
    created() {
      this.getList();
      this.menuManager_btn_add = this.permissions['sys_menu_add']
      this.menuManager_btn_edit = this.permissions['sys_menu_edit']
      this.menuManager_btn_del = this.permissions['sys_menu_del']
    },
    computed: {
      ...mapGetters([
        'elements','permissions','website'
      ])
    },
    methods: {
      // 获取树形图列表
      getList() {
        fetchTree(this.listQuery).then(response => {
          this.treeData = [{
            id: -1,
            parentId: undefined,
            name: "菜单",
            children: response.data,
            label: "菜单",
            value: -1
          }]
          this.selectId = -1;
          this.getNodeData(this.treeData[0])
        })
      },
      //点击树菜单调用方法
      getNodeData(data) {
        console.log(data.id);
        //设置当前选中节点ID
        this.selectId = data.id;
        //获取当前点击节点的this.selectId 的表格内容
        this.getTableData();
      },
      //根据部门编码获取列表
      getTableData() {
        //上一步获取到父节点的id
        if (this.selectId) {
          this.listQuery.parentId = this.selectId;
          // 根据父节点id 获取当前id下表格数据
          fetchList(this.listQuery).then(response => {
            // 获取表格列表主数据
            this.tableData = response.data.records
            // 获取表格数据总数
            this.total = response.data.total
            console.log(this.tableData);
          })
        }
      },
      // 新增 编辑 删除之后 重新加载tree
      getListTree() {
        fetchTree(this.listQuery).then(response => {
          this.treeData = [{
            id: -1,
            parentId: undefined,
            name: "菜单",
            children: response.data,
            label: "菜单",
            value: -1
          }]
        })
      },
      //点击树菜单
      zTreeOnClicks(event, treeId, treeNode, nodes) {
        // 主要是获取当前点击节点treeNode的id,存储到this.selectId 中,然后再获取当前点击节点的this.selectId 的表格内容
        this.getNodeData(treeNode);
        //获取当前节点id到祖节点id的集合
        this.menuList = [];
        for (let i = 0; i < nodes.length; i++) {
          const element = nodes[i].id;
          this.menuList.push(element);
        }
      },
      // 查询tree
      zTreeOnSearch(node) {
        this.getNodeData(node)
      },
      // 点击新增
      handlerAdd() {
        //重置from表单
        this.resetForm();
        //form表单头展示名字
        this.dialogStatus = "create";
        //展开from表单
        this.dialogFormVisible = true
        this.resetFormCatalog();
        this.resetFormMenu();
        this.resetFormBtn();
      },
      // 新增确定
      create(formName) {

        if (this.radio == "1") {
          // this.$refs['menu'].resetFields();
          // this.$refs['btn'].resetFields();
          this.resetForm()
          this.form.name = this.catalog.name;
          this.form.icons = this.catalog.icons;
          this.form.sort = this.catalog.sort;
          this.form.component = this.catalog.component;
          this.form.path = this.catalog.path;
          this.form.type = 9;

          if (this.menuList) {
            this.form.parentId = this.menuList[this.menuList.length - 1];
          }
          const set = this.$refs;
          set['catalog'].validate(valid => {
            if (valid) {
              addObj(this.form).then(() => {
                this.dialogFormVisible = false;//关闭悬浮窗
                // this.getList()
                this.getListTree();//刷新树形图
                this.getTableData();//获取当前表格列表
                this.$notify({
                  title: '成功',
                  message: '创建成功',
                  type: 'success',
                  duration: 2000
                })
              })
            } else {
              return false;
            }
          });

        } else if (this.radio == "2") {
          this.resetForm();
          // this.$refs['catalog'].resetFields();
          // this.$refs['btn'].resetFields();
          this.form.name = this.menu.name;
          this.form.permission = this.menu.permission;
          this.form.icons = this.menu.icons;
          this.form.sort = this.menu.sort;
          this.form.component = this.menu.component;
          this.form.path = this.menu.path;
          this.form.type = 0;

          if (this.menuList) {
            this.form.parentId = this.menuList[this.menuList.length - 1];
          }

          const set = this.$refs;
          set['menu'].validate(valid => {
            if (valid) {
              addObj(this.form).then(() => {
                this.dialogFormVisible = false;//关闭悬浮窗
                // this.getList()
                this.getListTree();//刷新树形图
                this.getTableData();//获取当前表格列表
                this.$notify({
                  title: '成功',
                  message: '创建成功',
                  type: 'success',
                  duration: 2000
                })
              })
            } else {
              return false;
            }
          });

        } else {
          // this.$refs['catalog'].resetFields();
          // this.$refs['menu'].resetFields();
          this.resetForm()
          this.form.name = this.btn.name;
          this.form.permission = this.btn.permission;
          this.form.sort = this.btn.sort;
          this.form.type = 1;

          if (this.menuList) {
            this.form.parentId = this.menuList[this.menuList.length - 1];
          }

          const set = this.$refs;
          set['btn'].validate(valid => {
            if (valid) {
              addObj(this.form).then(() => {
                this.dialogFormVisible = false;//关闭悬浮窗
                // this.getList()
                this.getListTree();//刷新树形图
                this.getTableData();//获取当前表格列表
                this.$notify({
                  title: '成功',
                  message: '创建成功',
                  type: 'success',
                  duration: 2000
                })
              })
            } else {
              return false;
            }
          });
        }


      },
      //列表点击编辑
      handleEdit(index, row) {
        getObj(row.menuId).then(response => {
          //打开弹窗
          this.dialogFormVisible = true;
          //设置弹窗标题
          this.dialogStatus = "update";

          this.resetFormCatalog();
          this.resetFormMenu();
          this.resetFormBtn();

          // * 菜单类型 （0菜单 1按钮,9目录）
          this.menuList = response.data.menuList;
          //点击编辑时获取返回的menuId,提交到到form
          this.menuIdTable = response.data.menuId;
          if (response.data.type == 0) {//菜单
            this.radio = 2;
            this.menu.name = response.data.name;
            this.menu.icons = response.data.icons;
            this.menu.sort = response.data.sort;
            this.menu.permission = response.data.permission;
            this.menu.component = response.data.component;
            this.menu.path = response.data.path;
          } else if (response.data.type == 1) {//按钮
            this.radio = 3;
            this.btn.name = response.data.name;
            this.btn.permission = response.data.permission;
            this.btn.sort = response.data.sort;
          } else {//目录
            this.radio = 1;
            this.catalog.name = response.data.name;
            this.catalog.icons = response.data.icons;
            this.catalog.sort = response.data.sort;
            this.catalog.menuId = response.data.menuId;
            this.catalog.component = response.data.component;
            this.catalog.path = response.data.path;
          }
        });
      },
      //弹出框点击修改
      update() {
        if (this.radio == "1") {
          this.resetForm()
          this.form.name = this.catalog.name;
          this.form.icons = this.catalog.icons;
          this.form.sort = this.catalog.sort;
          this.form.component = this.catalog.component;
          this.form.path = this.catalog.path;
          this.form.type = 9;

          // 点击编辑时获取返回的menuId,提交到到form
          this.form.menuId = this.menuIdTable;
          if (this.menuList) {
            this.form.parentId = this.menuList[this.menuList.length - 1];
          }
          const set = this.$refs;
          set['catalog'].validate(valid => {
            if (valid) {
              putObj(this.form).then(() => {
                this.dialogFormVisible = false;
                this.getListTree();//刷新树形图
                this.getTableData();//获取当前表格列表
                this.$notify({
                  title: '成功',
                  message: '更新成功',
                  type: 'success',
                  duration: 2000
                })
              })
            } else {
              return false;
            }
          })

        } else if (this.radio == "2") {
          this.resetForm()
          this.form.name = this.menu.name;
          this.form.permission = this.menu.permission;
          this.form.icons = this.menu.icons;
          this.form.sort = this.menu.sort;
          this.form.component = this.menu.component;
          this.form.path = this.menu.path;
          this.form.type = 0;
          // 点击编辑时获取返回的menuId,提交到到form
          this.form.menuId = this.menuIdTable;
          if (this.menuList) {
            this.form.parentId = this.menuList[this.menuList.length - 1];
          }
          const set = this.$refs;
          set['menu'].validate(valid => {
            if (valid) {
              putObj(this.form).then(() => {
                this.dialogFormVisible = false;
                this.getListTree();//刷新树形图
                this.getTableData();//获取当前表格列表
                this.$notify({
                  title: '成功',
                  message: '更新成功',
                  type: 'success',
                  duration: 2000
                })
              })
            } else {
              return false;
            }
          })
        } else {
          this.resetForm()
          this.form.name = this.btn.name;
          this.form.permission = this.btn.permission;
          this.form.sort = this.btn.sort;
          this.form.type = 1;

          // 点击编辑时获取返回的menuId,提交到到form
          this.form.menuId = this.menuIdTable;
          if (this.menuList) {
            this.form.parentId = this.menuList[this.menuList.length - 1];
          }
          const set = this.$refs;
          set['btn'].validate(valid => {
            if (valid) {
              putObj(this.form).then(() => {
                this.dialogFormVisible = false;
                this.getListTree();//刷新树形图
                this.getTableData();//获取当前表格列表
                this.$notify({
                  title: '成功',
                  message: '更新成功',
                  type: 'success',
                  duration: 2000
                })
              })
            } else {
              return false;
            }
          })
        }


      },
      // 点击删除,删除列表
      handleDelete(index, row) {
        this.$confirm("此操作将永久删除该用户(菜单名称:" + row.name + "), 是否继续?", '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          delObj(row.menuId)
            .then(() => {
              // this.getList()
              // this.resetForm()
              this.getListTree();
              this.getTableData();
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 2000
              })
            })
            .catch(() => {
              this.$notify({
                title: "失败",
                message: "删除失败",
                type: "error",
                duration: 2000
              });
            });
        })
      },
      // 悬浮框取消
      cancel(formName) {
        this.dialogFormVisible = false;
        this.$refs[formName].resetFields();
      },
      // 重置form
      resetForm() {
        this.form = {
          permission: undefined,
          name: undefined,
          menuId: undefined,
          parentId: this.currentId,
          icons: undefined,
          sort: undefined,
          component: undefined,
          type: undefined,
          path: undefined
        }
      },
      // 点击 目录 菜单 按钮 三个 单选按钮 清空当前的选项
      sourceFilter() {
        if (this.radio == "1") {
          // this.resetFormCatalog();
          // this.$refs['catalog'].resetFields();
        } else if (this.radio == "2") {
          // this.resetFormMenu();
          // this.$refs['menu'].resetFields();
        } else {
          // this.resetFormBtn();
          // this.$refs['btn'].resetFields();
        }
      },
      // 重置目录
      resetFormCatalog() {
        this.catalog = {
          name: undefined,
          icons: undefined,
          sort: undefined,
        }
      },
      // 重置菜单
      resetFormMenu() {
        this.menu = {
          name: undefined,
          permission: undefined,
          icons: undefined,
          sort: undefined,
          component: undefined,
          path: undefined,
        }
      },
      // 重置按钮
      resetFormBtn() {
        this.btn = {
          name: undefined,
          permission: undefined,
          sort: undefined,
        }
      },
      // 修改table header的背景色
      tableHeaderColor({row, column, rowIndex, columnIndex}) {
        if (rowIndex === 0) {
          return 'background-color: #fafafa;color:#999; font-size:12px;'
        }
      },
      // 分页每页条数变化时
      handleSizeChange(val) {
        this.listQuery.limit = val;
        this.getTableData();
      },
      // 当前页数变化时
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getTableData();
      },
      openInnerDialog(){
        this.selectedIcon = ''
        this.innerVisible = true
      },
      iconSelected(){
        if (this.radio == "1") {
          this.catalog.icons = this.selectedIcon
        }else if(this.radio == "2"){
          this.menu.icons = this.selectedIcon
        }
        this.innerVisible = false
      },
      innerDoubleClick(item){
        if (this.radio == "1") {
          this.catalog.icons = item
        }else if(this.radio == "2"){
          this.menu.icons = item
        }
        this.innerVisible = false
      }
    }
  }
</script>

<style lang="scss" scoped>
  .fr {
    float: right;
  }

  .fl {
    float: left;
  }

  /* 清浮动 */
  .clearfix:after {
    visibility: hidden;
    display: block;
    font-size: 0;
    content: " ";
    clear: both;
    height: 0;
  }

  .clearfix {
    zoom: 1; /* for IE6 IE7 */
  }

  .el-button--primary.button_add {
    background-color: #ffffff;
    color: #999;
    border-color: #e7e9ee;
    width: 74px;
    height: 28px;
    line-height: 10px;;
  }

  .el-container {
    background-color: #edf2f5;
  }

  .app-container {
    padding: 10px 20px;
    .basic-container{
      .el-row{
        /deep/ .zTree_custom{
          #areaTree{
            .el-container{
              .el-header{
                padding: 5px !important;
              }
            }
          }
        }
      }
    }
  }

  .el-table::before {
    height: 0;
  }

  .el-table {
    border: 1px solid #e5e5e5;
  }

  .button_edit {
    padding: 0;
    border: none;
    color: #66B1FF;
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

  .el-dialog {
    width: 80%;
  }

  .dialog_mask_mine >>> .el-dialog__title {
    font-size: 14px;
  }

  .dialog_mask_mine >>> .el-dialog__title:before {
    width: 5px;
    height: 16px;
    background-color: #409eff;
  }

  .preTitle {
    height: 16px;
    width: 5px;
    background-color: #3a8ee6;
    display: block;
    float: left;
    text-align: center;
    margin: 4px 0;
  }

  .nextTitle {
    float: left;
    padding: 0 0 0 5px;
    line-height: 24px;;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
    color: #666;
  }

  .zTree_custom >>> .el-header {
    padding: 5px 0 !important;
  }

  /deep/ .innerDialog{
    .el-dialog{
      height: 80vh;
      width: 80vw;
      margin-top: 10vh !important;
      .drawer_title {
        line-height: 30px;
        .titleSpan {
          padding: 0;
          font-size: 14px;
          font-weight: bold;
          text-align: center;
          color: #3a8ee6;
        }
        .titleSpan:before {
          content: '';
          height: 16px;
          width: 5px;
          background-color: #3a8ee6;
          display: block;
          float: left;
          text-align: center;
          margin: 7px 5px 7px 0;
        }
      }
      .el-dialog__body {
        height: calc(100% - 113px);
        padding: 10px 60px;
        .dialog_main {
          height: 100%;
          overflow: auto;
          display: flex;
          flex-flow: row wrap;
          justify-content: flex-start;
          .icon_item{
            width: calc(100% / 5);
            height: 60px;
            display: flex;
            flex-flow: row nowrap;
            margin-bottom: 10px;
            padding: 0 5px;
            i{
              font-size: 30px !important;
            }
            .icon_title{
              white-space: nowrap;
              overflow: hidden;
              text-overflow: ellipsis;
              margin-left: 10px;
              line-height: 60px;
            }
            &:hover{
              background-color: #eeeeee;
              cursor: pointer;
            }
          }
          .icon_selected{
            background-color: #eeeeee;
          }
        }
      }
      .el-dialog__footer {
        padding: 10px;
        text-align: center;
      }
    }
  }

  /deep/ .el-input{
    .el-input-group__prepend{
      background-color: rgba(0,0,0,0);
    }
    .el-button{
      i{
        font-size: 12px !important;
      }
    }
  }
  /deep/ .el-input-number .el-input__inner{
    text-align: left;
  }
</style>
