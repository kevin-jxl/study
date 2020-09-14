<template>
  <div class="table_main" v-loading="loading">
    <el-row :gutter="20" class="left_title">
      <el-col :span="20">
        <el-button type="text" icon="el-icon-plus" size="mini"
                   @click="addClick"
        >新建</el-button>
        <el-button type="text" icon="el-icon-edit" size="mini"
                   @click="editClcik"
        >编辑</el-button>
        <el-button type="text" icon="el-icon-delete" size="mini">删除</el-button>
        <el-button type="text" icon="el-icon-upload2" size="mini">上移</el-button>
        <el-button type="text" icon="el-icon-download" size="mini">下移</el-button>
        <el-select v-model="value" placeholder="请选择" size="mini" class="search_select">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="value" placeholder="请选择" size="mini" class="search_select">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="value" placeholder="请选择" size="mini" class="search_select">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="value" placeholder="请选择" size="mini" class="search_select">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-dropdown>
          <span class="el-dropdown-link">
            更多<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>黄金糕</el-dropdown-item>
            <el-dropdown-item>狮子头</el-dropdown-item>
            <el-dropdown-item>螺蛳粉</el-dropdown-item>
            <el-dropdown-item disabled>双皮奶</el-dropdown-item>
            <el-dropdown-item divided>蚵仔煎</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
      <el-col :span="4" class="right_title">
        <el-button type="text" size="mini" @click="releaseClick">发布</el-button>
        <span>计划状态：编制中</span>
      </el-col>
    </el-row>
    <el-table :data="treeData"
              ref="table_plan"
              :row-style="showRow"
              v-bind="$attrs"
              height="calc(100% - 60px)"
              :cell-style="cellStyle"
              :header-cell-style="headerCellStyle"
              tooltip-effect="dark"
              :border="false"
              highlight-current-row
              @current-change="handleCurrentChange"
              @row-dblclick="handleRowDoubleClick"
    >
      <el-table-column min-width="3" align="center">
        <template slot-scope="scope">
          <div style="width: 100%;height: 100%"  @contextmenu.prevent="openMenu('first'+scope.row.id,scope,$event)" :ref="'first'+scope.row.id">
            <i class="el-icon-setting"/>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" type="index"
                       label="序号" min-width="3">
      </el-table-column>
      <el-table-column min-width="6" align="center" label="进度">
        <template slot-scope="scope">
          <i class="el-icon-success" />
        </template>
      </el-table-column>
      <el-table-column min-width="6" align="center" label="文档">
        <template slot-scope="scope">
          <i class="el-icon-success" />
        </template>
      </el-table-column>
      <el-table-column min-width="4" align="center" label="！">
        <template slot-scope="scope">
          <i class="el-icon-setting" />
        </template>
      </el-table-column>
      <el-table-column v-if="columns.length===0" width="150">
        <template slot-scope="scope">
          <span v-for="space in scope.row._level" :key="space" class="ms-tree-space"/>
          <span v-if="iconShow(0,scope.row)" class="tree-ctrl" @click="toggleExpanded(scope.$index)">
          <i v-if="!scope.row._expanded" class="el-icon-plus"/>
          <i v-else class="el-icon-minus"/>
        </span>
          {{ scope.$index }}
        </template>
      </el-table-column>
      <el-table-column v-for="(column, index) in columns" v-else
                       :key="column.value" :label="column.text"
                       :min-width="column.width" :show-overflow-tooltip="true"
                       header-align="center" :align="index === 0?'left':'center'">
        <template slot-scope="scope">
          <span v-for="space in scope.row._level" v-if="index === 0" :key="space" class="ms-tree-space"/>
          <span v-if="iconShow(index,scope.row)" class="tree-ctrl" @click="toggleExpanded(scope.$index)">
            <i v-if="!scope.row._expanded" class="el-icon-plus"/>
            <i v-else class="el-icon-minus"/>
          </span>
          <template v-if="scope.row.isEdit">
            <span>12</span>
          </template>
          <template v-else>
            <input-label v-if="column.editable&&column.type==='input'" :inner-text="scope.row[column.value]"></input-label>
            <select-label v-else-if="column.editable&&column.type==='select'" :inner-text="scope.row[column.value]"></select-label>
            <span v-else>{{ scope.row[column.value] }}</span>
          </template>
        </template>
      </el-table-column>
      <slot></slot>
    </el-table>
    <el-row :gutter="20" type="flex" class="row-bg" justify="center">
      <el-col :span="4">
        <div class="grid-content bg-purple">
          <span class="footer_title">项目工期：</span>
          <span>71工作日</span>
        </div>
      </el-col>
      <el-col :span="4">
        <div class="grid-content bg-purple-light">
          <span class="footer_title">标准工时：</span>
          <span>3.75人天</span>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <span class="footer_title">周期：</span>
          <span>2018-09-03～2019-01-01</span>
        </div>
      </el-col>
      <el-col :span="2">
        <div class="grid-content bg-purple">
          <span class="footer_title">进度：</span>
          <span>99%</span>
        </div>
      </el-col>
    </el-row>
    <Drawer title="Basic Drawer" :closable="false"
            v-model="editVisible" width="800" class="drawer_edit">
      <div slot="header" class="drawer_title">
        <span class="titleSpan">{{drawerName}}</span>
      </div>
      <div class="drawer_main">
        <el-form ref="form" :model="form" label-width="100px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="任务名称">
                <el-input v-model="form.name" size="small"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="活动名称">
                <el-input v-model="form.name" size="small"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="计划开始时间">
                <el-date-picker type="date" placeholder="选择日期"
                                v-model="form.planStartTime" style="width: 100%;"
                                size="small"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="计划结束时间">
                <el-date-picker type="date" placeholder="选择时间"
                                v-model="form.planEndTime" style="width: 100%;"
                                size="small"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="实际开始时间">
                <el-date-picker type="date" placeholder="选择日期"
                                v-model="form.actualStartTime" style="width: 100%;"
                                size="small"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="实际结束时间">
                <el-date-picker type="date" placeholder="选择时间"
                                v-model="form.actualEndTime" style="width: 100%;"
                                size="small"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="前置任务">
                <el-select v-model="form.preTask" placeholder="请选择活动区域" size="small">
                  <el-option label="区域一" value="shanghai"></el-option>
                  <el-option label="区域二" value="beijing"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="责任人">
                <el-select v-model="form.personName" placeholder="请选择活动区域" size="small">
                  <el-option label="区域一" value="shanghai"></el-option>
                  <el-option label="区域二" value="beijing"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="进度">
                <el-input-number v-model="form.schedule" controls-position="right"
                                 :min="1" :max="100" size="small"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="drawer_button">
        <el-button size="small">取 消</el-button>
        <el-button type="primary"
                   size="small">确 定
        </el-button>
      </div>
    </Drawer>
  </div>
</template>

<script>
  import treeToArray from '../../../util/tableEval'
  import InputLabel from './inputLabel'
  import SelectLabel from './selectLabel'
  import Vue from 'vue'
  import {deepCloneObj} from "../../../util/util";
  export default {
    name: 'planTable',
    props: {
      data: {
        type: [Array, Object],
        required: true
      },
      columns: {
        type: Array,
        default: () => []
      },
      evalFunc: Function,
      evalArgs: Array,
      expandAll: {
        type: Boolean,
        default: false
      }
    },
    components:{InputLabel,SelectLabel},
    data(){
      return{
        loading:false,
        treeData:[],
        cloneData:{},
        expandedRows:[],
        headerCellStyle:{
          padding:'5px 0',
          color:'#898989',
          backgroundColor:'#fafbfa',
          fontWeight:'bold'
        },
        cellStyle:{
          padding:'5px 0',
          color:'#5d5d5d',
          fontWeight:'bold'
        },
        editVisible:false,
        currentRow:undefined,
        options: [
          {
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }
        ],
        form:{
          id:'',
          name:'',
          planTime:'',
          actualTime:'',
          planStartTime:'',
          planEndTime:'',
          actualStartTime:'',
          actualEndTime:'',
          preTask:'',
          personName:'',
          schedule:'',
          document:'',
        },
        value: '',
        drawerName:'新建',
      }
    },
    computed: {

    },
    mounted(){
      // this.cloneData = deepCloneObj(this.data)
      this.treeData = this.formatData()
    },
    methods: {
      // 格式化数据源
      formatData: function() {
        let tmp
        if (!Array.isArray(this.data)) {
          tmp = [this.data]
        } else {
          tmp = this.data
        }
        const func = this.evalFunc || treeToArray
        const args = this.evalArgs ? Array.concat([tmp, this.expandAll], this.evalArgs) : [tmp, this.expandAll]
        return func.apply(null, args)
      },
      showRow: function(row) {
        const show = (row.row.parent ? (row.row.parent._expanded && row.row.parent._show) : true)
        row.row._show = show
        return show ? 'animation:treeTableShow 1s;-webkit-animation:treeTableShow 1s;' : 'display:none;'
      },
      // 切换下级是否展开
      toggleExpanded: function(trIndex) {
        const record = this.treeData[trIndex]
        record._expanded = !record._expanded
        Vue.set(this.treeData,trIndex,record)
        if(record._expanded){
          this.expandedRows.push(record.id)
        }else{
          let index = this.expandedRows.indexOf(record.id)
          console.info(index)
          if(index !== -1){
            this.expandedRows.splice(index,1)
          }
        }
      },
      // 图标显示
      iconShow(index, record) {
        return (index === 0 && record.children && record.children.length > 0)
      },
      openMenu(ref,item,e){
        console.info(this)
        console.info(ref)
        console.info(item)
        console.info(e)
      },
      handleCurrentChange(currentRow, oldCurrentRow){
        this.currentRow = currentRow
      },
      handleRowDoubleClick(row, event){
        this.editVisible = true
        this.drawerName = '编辑'
        this.form = this.currentRow
      },
      addClick(){
        this.editVisible = true
        this.drawerName = '新建'
        this.form = {
          id:'',
          name:'',
          planTime:'',
          actualTime:'',
          planStartTime:'',
          planEndTime:'',
          actualStartTime:'',
          actualEndTime:'',
          preTask:'',
          personName:'',
          schedule:'',
          document:'',
        }

        /*if(this.currentRow){
          let index = this.treeData.map(item=>{
            return item.parentId
          }).lastIndexOf(this.currentRow.id)
          if(index === -1){
            index = this.treeData.findIndex(item=>{
              return item.id === this.currentRow.id
            })
            this.treeData.splice(index, 1, {...this.currentRow,
              _expanded:true,
              children:[{}],
            })
            this.treeData.splice(index+1, 0, {
              _expanded:false,
              _level:this.currentRow._level+1,
              _show:true,
              id:new Date().getMilliseconds()+'',
              name:'',
              planTime:'',
              actualTime:'',
              planStartTime:'',
              planEndTime:'',
              actualStartTime:'',
              actualEndTime:'',
              preTask:'',
              personName:'',
              schedule:'',
              document:'',
              children:[],
              parentId:this.currentRow.id,
              isEdit:true
            })
          }else{
            this.treeData.splice(index+1, 0, {
              _expanded:false,
              _level:this.currentRow._level+1,
              _show:true,
              id:new Date().getMilliseconds()+'',
              name:'',
              planTime:'',
              actualTime:'',
              planStartTime:'',
              planEndTime:'',
              actualStartTime:'',
              actualEndTime:'',
              preTask:'',
              personName:'',
              schedule:'',
              document:'',
              children:[],
              parentId:this.currentRow.id,
              isEdit:true
            })
          }
          console.info(index)
        }else{
          this.treeData.splice(this.treeData.length+1, 0, {
            _expanded:false,
            _level:1,
            _show:true,
            id:new Date().getMilliseconds()+'',
            name:'',
            planTime:'',
            actualTime:'',
            planStartTime:'',
            planEndTime:'',
            actualStartTime:'',
            actualEndTime:'',
            preTask:'',
            personName:'',
            schedule:'',
            document:'',
            parentId:'-1',
            isEdit:true
          })
        }*/

        // console.info(this.getRowById(this.cloneData,this.currentRow.id))
      },
      editClcik(){
        this.editVisible = true
        this.drawerName = '编辑'
        this.form = this.currentRow
      },
      releaseClick(){
        this.loading = true
        setTimeout(()=>{
          this.loading = false
        },2000)
        this.$nextTick(function () {
          this.$refs.table_plan.setCurrentRow(this.$refs.table_plan.data[12]);
        })
      },
      getLastChildId(arrays,id){
        if(arrays.children&&arrays.children.length>0){

        }else {

        }
      },
      getRowById(arrays,id){
        let row = undefined
        arrays.forEach(item=>{
          if(!row){
            if(item.id==id){
              row = item
            }else{
              if(item.children&&item.children.length>0){
                row = this.getRowById(item.children,id)
              }
            }
          }
        })
        return row
      }
    }
  }
</script>
<style rel="stylesheet/css">
  @keyframes treeTableShow {
    from {opacity: 0;}
    to {opacity: 1;}
  }
  @-webkit-keyframes treeTableShow {
    from {opacity: 0;}
    to {opacity: 1;}
  }
</style>

<style lang="scss" rel="stylesheet/scss" scoped>
  $color-blue: #2196F3;
  $space-width: 18px;
  .ms-tree-space {
    position: relative;
    top: 1px;
    display: inline-block;
    font-style: normal;
    font-weight: 400;
    line-height: 1;
    width: $space-width;
    height: 14px;
    &::before {
      content: ""
    }
  }
  .processContainer{
    width: 100%;
    height: 100%;
  }
  table td {
    line-height: 26px;
  }

  .tree-ctrl{
    position: relative;
    cursor: pointer;
    color: $color-blue;
    margin-left: -$space-width;
  }

  .table_main{
    height: 100%;
    .left_title{
      .el-select{
        margin-left: 2px;
      }
      .el-dropdown{
        margin-left: 4px;
        font-size: 12px;
        cursor: pointer;
      }
    }
    .right_title{
      display: flex;
      justify-content: space-around;
      align-items: center;
    }
  }
  .el-table {
    font-size: 12px !important;
    color:#333 !important;
    margin-top: 4px;
    margin-bottom: 3px;

    /deep/ .cell{
      line-height: 26px;
      padding:0 2px;
      font-weight: normal;
      .el-input,.el-input__inner{
        height: 26px;
      }
    }
  }
  .el-table--medium td, .el-table--medium th {
    padding: 5px 0 !important;
  }
  .search_select{
    width: 100px !important;
  }
  .el-dropdown{
    color:#3a8ee6;
  }
  .footer_title{
    font-weight: bold;
  }

  .drawer_edit{
    .drawer_title{
      line-height: 30px;
      .titleSpan{
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
    .drawer_main{
      height: calc(100% - 40px);
    }
    .drawer_button{
      height: 40px;
      text-align: center;
    }
  }

</style>
