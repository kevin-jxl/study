<template>
    <div class="full-screen">
        <div class="full-screen">
            <z-table
                :data="tableData"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange">
                <div slot="table-column">
                    <el-table-column v-for="(column, index) in columns" 
                        :key="index"
                        :prop="column.prop" 
                        :label="column.label" 
                        :width="column.width">
                    </el-table-column>
                </div>
            </z-table>
        </div>
    </div>
</template>

<script>
    import ZTable from '../../../components/ZTable.vue'
    export default {
        
        components: {
            ZTable,
        },
        data() {
            return {
                columns: [
                    {prop: "date", label: "日期", width: "200"},
                    {prop: "name", label: "姓名", width: "180"},
                    {prop: "address", label: "地址", width: "auto"}
                ],
                tableData: [
                    {
                        date: "2016-05-02",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1518 弄"
                    },
                    {
                        date: "2016-05-04",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1517 弄"
                    },
                    {
                        date: "2016-05-01",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1519 弄"
                    },
                    {
                        date: "2016-05-03",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1516 弄"
                    }
                ]
            }
        },
        methods: {
            handleSizeChange(size) {
                console.log('page size: ', size);
            },
            handleCurrentChange(page) {
                console.log('current page: ', page);
            }
        },
    }
</script>

<style lang="scss" scoped>

</style>