<template>
    <div style="text-align: left;">
        <div>
            <div class="detail-item">
            <p class="p-style">工单编号：<span class="span-style">{{details.id}}</span></p>
            </div>
            <div class="detail-item">
                <p class="p-style">设备名称：<span class="span-style">{{details.equipmentName}}</span></p>
            </div>
            <div class="detail-item">
                <p class="p-style">设备编号：<span class="span-style">{{details.equipmentId}}</span></p>
            </div>
            <div class="detail-item">
                <p class="p-style">问题类型：<span class="span-style">{{details.problemType}}</span></p>
            </div>
            <div class="detail-item">
                <p class="p-style">上报时间：<span class="span-style">{{details.reportTime}}</span></p>
            </div>
            <div class="detail-item">
                <p class="p-style">派遣时间：<span class="span-style">{{details.dispatchTime}}</span></p>
            </div>
            <div class="detail-item">
                <p class="p-style">处置时限：<span class="span-style">{{details.timeLimit}}</span></p>
            </div>
            <div class="detail-item">
                <p class="p-style">上报地点：<span class="span-style">{{details.reportAddr}}</span></p>
            </div>
            <div class="detail-item item-flex">
                <!-- <p class="p-style">问题描述：<span class="span-style">{{details.describe}}</span></p> -->
                <div class="p-style">问题描述：</div>
                <div class="span-style">
                    {{details.describe}}
                </div>
            </div>
        </div>
         
                                     
    </div>
</template>

<script>
    export default {
        props: {
            details: {
                type:Object,
                default:()=>{}
            }
        },
        data() {
            return {
                // details: {
                //     id: '1200123',
                //     name: '闸机',
                //     code: 'KT009',
                //     type: '设备损坏',
                //     reportTime: '2020-09-02 10:00',
                //     dispatchTime: '2020-09-02 11:00',
                //     timeLimit: '一小时',
                //     reportAddress: '广平大街',
                //     describe: "门口左侧道路边，长期有村们   散倒垃圾，现已影响车辆行走，需要及时处理。"
                // }
            }
        }
        
    }
</script>

<style lang="scss" scoped>
    .detail-item {
        min-height: 30px;
        line-height: 30px;
    }
    .item-flex {
        display: flex;
        flex-direction:row;
    }
    .p-style {
        font-size:14px;
		font-weight: bold;
        color: #666666;
        min-width:70px;
    }
    .span-style {
        font-size: 14px; color:#747474;
		font-weight:normal;
    }
</style>