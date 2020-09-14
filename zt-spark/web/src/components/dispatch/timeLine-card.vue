<template>
    <div style="text-align: left; font-size:12px;">
        <div style="padding-top:6px;">
           <el-timeline  :reverse="true">
                <el-timeline-item
                v-for="(item, index) in workOrderProcess"
                :key="index"
                icon="el-icon-aim"
                type="primary"
                color="#0bbd87"
                size="large"
                :timestamp="item.operationTime">
                <div style="display: flex; flex-direction: row;">
                    <div>{{item.joinName}}</div>
                    <div style="margin-left:20px; color: #666666">状态：
                        <span style="color:#333333">{{item.tagName}}</span>
                        </div>
                </div>
                </el-timeline-item>
            </el-timeline>
        </div>
         
                                     
    </div>
</template>

<script>
    export default {
        props:{
            workOrderProcess:{
                type:Array,
                default:()=>[]
            }
       },
        data() {
            return {
                //  activities: [{
                //     content: '活动按期开始',
                //     timestamp: '2018-04-15'
                //     }, {
                //     content: '通过审核',
                //     timestamp: '2018-04-13'
                //     }, {
                //     content: '创建成功',
                //     timestamp: '2018-04-11'
                // }]
            }
        }
    }
</script>
<style>
    .el-timeline {
        margin: 0;
        font-size: 14px;
        list-style: none;
        padding-left: 10px !important;
    }
</style>

<style lang="scss" scoped>
    .detail-item {
        min-height: 20px;
        line-height: 20px;
    }
    .p-style {
        font-size:16px;
        color: #333333;
    }
    .span-style {
        font-size: 14px; color:#666666;
    }
</style>