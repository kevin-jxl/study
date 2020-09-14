<template>
    <div style="text-align: left;">
        <div  style="text-align: left;">
            <div class="detail-item attachment-item">
                <p class="p-style">语音：</p>
                <div class="voice-item" v-if="attachmentArray.voice">
                    <div v-for="(item,index) in attachmentArray.voice" :key="index">
                        <i class="el-icon-video-play icon" style=" color: #ffffff; margin-left:10px"></i>
                    </div>
                </div>
            </div>
            <div class="detail-item attachment-item" >
                <p class="p-style">图片：</p>
                <div v-if="attachmentArray.img">
                        <el-image 
                            v-for="(item,index) in attachmentArray.img" :key="index"
                            class="img-view"
                            :src="item.path"
                            fit="fill">
                        </el-image>
                </div>
            </div>
            <div class="detail-item attachment-item">
                <p class="p-style">视频：</p>
                <div v-if="attachmentArray.video">
                    <el-image
                         v-for="(item,index) in attachmentArray.video" :key="index"
                         class="img-view"
                        src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
                        fit="fill">
                    </el-image>
                </div>
            </div>
        </div>
         
                                     
    </div>
</template>

<script>
    export default {
       props:{
            attachmentArray:{
                type:Array,
                default:()=>[]
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
    .p-style {
        font-size:14px;
        color: #333333;
    }
    .span-style {
        font-size: 14px; color:#666666;
    }
    .attachment-item {
                display: flex;
                flex-direction: row;
                justify-content: flex-start;
        }
        .img-view {
            width: 65px; height: 65px; margin-right: 10px;
            border-radius: 4px;
        }
        .voice-item {
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            div {
                position: relative;
                width: 150px;
                height: 25px;
                margin-top: 10px;
                background-color: #33AEFE;
                border-radius: 6px;
                .icon {
                    position: absolute;
                    top:50%;
                    left:0;
                    -webkit-transform: translateX(0) translateY(-50%);
                }
            }
        }
</style>