<template>
  <el-table :data="treeData"
            :row-style="showRow"
            v-bind="$attrs" height="100%"
            :cell-style="cellStyle"
            :header-cell-style="headerCellStyle"
            tooltip-effect="dark"
            :border="false"
  >
    <el-table-column
      type="selection"
      width="55">
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
        {{ scope.row[column.value] }}
      </template>
    </el-table-column>
    <slot></slot>
  </el-table>
</template>

<script>
  import treeToArray from '../../util/tableEval'
  import Vue from 'vue'
  export default {
    name: 'treeTable',
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
    data(){
      return{
        treeData:[],
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
      }
    },
    computed: {

    },
    mounted(){
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
      },
      // 图标显示
      iconShow(index, record) {
        return (index === 0 && record.children && record.children.length > 0)
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

  .el-table {
    font-size: 12px !important;
    color:#333 !important;
  }
  .el-table--medium td, .el-table--medium th {
    padding: 5px 0 !important;
  }

</style>
