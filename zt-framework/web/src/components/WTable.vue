<template>
  <el-table
    class="wTable"
    ref="singleTable"
    stripe
    :height="height"
    :data="tabledata"
    highlight-current-row
    @selection-change="handleSelectionChange"
  >
    <slot></slot>
  </el-table>
</template>
<script>
export default {
  props: {
    tabledata: {
      type: Array,
      default: ()=>{
        return []
      }
    },
    multiple: {
      type: Boolean,
      default: false
    },
    height:{
      type:String,
      default:()=>{return "auto"}
    }
  },
  data() {
    return {
      currentRow: null,
      multipleSelection: [],
      status: true,
      rowClick: false
    };
  },
  methods: {
    handleSelectionChange(val) {
      if (this.multiple) {
        console.log("this.multiple1111:",this.multiple);
        this.multipleSelection = val;
          this.$emit(
            "singleRowCheck",
            this.multipleSelection
          );
      } else {
        console.log("this.multiple2222:",this.multiple);
        if (val.length > 1) {
          this.$refs.singleTable.clearSelection();
          let endVal = val.pop();
          this.$refs.singleTable.toggleRowSelection(endVal);
        } else {
          this.multipleSelection = val;
          this.$emit(
            "singleRowCheck",
            this.multipleSelection[this.multipleSelection.length - 1]
          );
        }
      }
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    setRowSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.singleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.singleTable.clearSelection();
      }
    }
  }
};
</script>