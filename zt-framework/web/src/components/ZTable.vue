<template>
  <div class="full-screen" style="background: #ffffff;">
    <div style="width: 100%; height: calc(100% - 52px);">
      <el-table :data="data" height="100%" style="width: 100%"
        border
        @row-click="handleRowClick"
        @select="handleSelect"
        @select-all="handleSelectAll"
        @selection-change="handleSelectionChange"
		:header-cell-style="{background:'#f0efef',color:'#333'}">
        <slot name="table-column"></slot>
      </el-table>
    </div>
    <div style="text-align: right;padding:10px 0;" >
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :page-size="pageSize"
        :layout="layout"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    data: {
      type: Array,
      default: () => { return [] }
    },
    currentPage: {
      type: Number,
      default: 1
    },
    pageSizes: {
      type: Array,
      default: () => {
        return [30, 50, 100];
      }
    },
    pageSize: {
      type: Number,
      default: () => {
        return 30;
      }
    },
    layout: {
      type: String,
      default: "total, sizes, prev, pager, next, jumper"
    },
    total: {
      type: Number,
      default: 100
    }
  },
  methods: {
    // pageSize 改变时会触发
    handleSizeChange(value) {
      this.$emit('size-change', value);
    },

    // currentPage 改变时会触发
    handleCurrentChange(value) {
      this.$emit('current-change', value);
    },

    // 当某一行被点击时会触发该事件
    handleRowClick(value) {
      this.$emit('row-click', value);
    },

    // 当用户手动勾选数据行的 Checkbox 时触发的事件
    handleSelect(value) {
      this.$emit('select', value);
    },

    // 当用户手动勾选全选 Checkbox 时触发的事件
    handleSelectAll(value) {
      this.$emit('select-all', value);
    },

    // 当用户手动勾选全选 Checkbox 时触发的事件
    handleSelectionChange(value) {
      this.$emit('selection-change', value);
    },
  },
};
</script>

<style lang="scss" scoped>

</style>