<template>
  <div class="category_main">
    <el-dialog
      title="提示"
      v-if="dialogVisible"
      :visible.sync="dialogVisible"
      class="permission_dialog"
      :before-close="beforeClose"
    >
      <div slot="title" class="drawer_title">
        <span class="titleSpan">{{dialogName}}</span>
      </div>
      <div class="dialog_main">
        <el-row :gutter="20" class="detail_main">
          <el-col :span="8" class="detail_left">
            <z-tree :z-nodes="category"
                    :check-enable="false"
                    :simple-check="false"
                    :default-select-one="true"
                    :search-enable="true"
                    @tree-click="zTreeOnClicks"
                    search-placeholder="请输入组织名称查找"
                    style="height:100%"
            ></z-tree>
          </el-col>
          <el-col :span="16" class="detail_right" v-loading="transferLoading">
            <div class="transfer_detail">
              <el-row class="transfer_row">
                <el-col :span="10" class="transfer_left">
                  <div class="transfer_title">
                    <el-checkbox :indeterminate="leftIndeterminate"
                                 v-model="leftCheckAll"
                                 @change="leftCheckAllChange"
                                 :disabled="nowLeftItems.length===0"
                    >{{transferTitle[0]||'已有任务'}}</el-checkbox>
                    <span>{{leftSelectedNo}}/{{nowLeftItems.length}}</span>
                  </div>
                  <div class="transfer_search">
                    <el-input
                      placeholder="请输入关键字"
                      size="small"
                      style="width: 100%"
                      v-model="leftSearchInput"
                    >
                      <i slot="suffix" class="el-input__icon el-icon-search" @click.stop="treeSearch"></i>
                    </el-input>
                  </div>
                  <div class="transfer_checkbox" v-if="nowLeftItems.length>0">
                    <el-checkbox-group v-model="leftCheckedItems" @change="leftCheckChanged">
                      <el-checkbox v-for="item in nowLeftItems" :label="item.id" :key="item.id">{{item.label}}</el-checkbox>
                    </el-checkbox-group>
                  </div>
                  <div v-else class="transfer_none">
                    <span>暂无数据</span>
                  </div>
                </el-col>
                <el-col :span="4" class="transfer_center">
                  <el-button type="primary" icon="el-icon-arrow-left"
                             circle @click="rightToLeft"
                             :disabled="rightSelectedNo===0"
                  ></el-button>
                  <el-button type="primary" icon="el-icon-arrow-right"
                             circle @click="leftToRight"
                             :disabled="leftSelectedNo===0"
                  ></el-button>
                </el-col>
                <el-col :span="10" class="transfer_right">
                  <div class="transfer_title">
                    <el-checkbox :indeterminate="rightIndeterminate"
                                 v-model="rightCheckAll"
                                 @change="rightCheckAllChange"
                                 :disabled="nowRightItems.length===0"
                    >{{transferTitle[1]||'模板任务'}}</el-checkbox>
                    <span>{{rightSelectedNo}}/{{nowRightItems.length}}</span>
                  </div>
                  <div class="transfer_search">
                    <el-input
                      placeholder="请输入关键字"
                      size="small"
                      style="width: 100%"
                      v-model="rightSearchInput"
                    >
                      <i slot="suffix" class="el-input__icon el-icon-search" @click.stop="treeSearch"></i>
                    </el-input>
                  </div>
                  <div class="transfer_checkbox" v-if="nowRightItems.length>0">
                    <el-checkbox-group v-model="rightCheckedItems" @change="rightCheckChanged" v-if="order===1">
                      <el-checkbox v-for="item in allItems" :label="item.id" :key="item.id">{{item.label}}</el-checkbox>
                    </el-checkbox-group>
                    <el-checkbox-group v-model="rightCheckedItems" @change="rightCheckChanged" v-else-if="order===2">
                      <el-checkbox v-for="item in allItemsReverse" :label="item.id" :key="item.id">{{item.label}}</el-checkbox>
                    </el-checkbox-group>
                    <el-checkbox-group v-model="rightCheckedItems" @change="rightCheckChanged" v-else>
                      <el-checkbox v-for="item in nowRightItems" :label="item.id" :key="item.id">{{item.label}}</el-checkbox>
                    </el-checkbox-group>
                  </div>
                  <div v-else class="transfer_none">
                    <span>暂无数据</span>
                  </div>
                </el-col>
              </el-row>
            </div>
            <div class="transfer_footer">
              <el-button @click="dialogCancel" size="small">取 消</el-button>
              <el-button type="primary" @click="dialogSubmit" size="small">确 定</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import ZTree from './zTree'
  export default {
    name:'categorySelect',
    data(){
      return {
        leftIndeterminate:false,
        rightIndeterminate:false,
        leftCheckAll:false,
        rightCheckAll:false,
        leftSearchInput:'',
        rightSearchInput:'',
        leftCheckedItems:[],
        rightCheckedItems:[],
        rightItems:[],
        rightNowItems:[],
        rightIds:[],
        allItems:[]
      }
    },
    props:{
      category:{
        type:Array,
        default:()=>[]
      },
      dialogName:{
        type: String,
        default:'选择弹窗'
      },
      dialogVisible:{
        type:Boolean,
        default:true
      },
      transferTitle:{
        type:Array,
        default:()=>['已有任务','模板任务']
      },
      transferItems:{
        type:Array,
        default:()=>[]
      },
      order:{
        type:Number,/*0 按id;1 按添加顺序 2 按添加顺序取反*/
        default:0
      },
      transferLoading:{
        type:Boolean,
        default:false
      }
    },
    computed:{
      transferItemsIds:function(){
        return this.transferItems.map(item=>item.id)
      },
      nowLeftItems:function(){
        return this.transferItems.filter(item=>{
          return !this.rightIds.includes(item.id)&&item.label.indexOf(this.leftSearchInput)!==-1
        })
      },
      nowRightItems:function () {
        return this.rightItems.concat(this.rightNowItems).filter(item=>{
          return item.label.indexOf(this.rightSearchInput)!==-1
        }).sort((a, b) => a.id - b.id)
      },
      leftSelectedNo:function () {
        return this.leftCheckedItems.length
      },
      rightSelectedNo:function () {
        return this.rightCheckedItems.length
      },
      allItemsReverse:function () {
        let newAllItems = []
        this.allItems.forEach(item=>{
          newAllItems.unshift(item)
        })
        return newAllItems
      }
    },
    components:{ZTree},
    methods:{
      zTreeOnClicks(event, treeId, treeNode){
        this.rightItems = this.rightItems.concat(this.rightNowItems)
        this.rightNowItems = []
        this.$emit('transfer-tree-search',treeId)
      },
      dialogCancel(){
        this.$emit('transfer-cancel')
      },
      dialogSubmit(){
        this.$emit('transfer-submit',this.nowRightItems)
      },
      beforeClose(){
        this.$emit('transfer-cancel')
      },
      leftToRight(){
        this.transferItems.filter(item=>{
          return this.leftCheckedItems.includes(item.id)
        }).forEach(item=>{
          this.allItems.push(item)
        })

        this.rightIds = this.rightIds.concat(this.leftCheckedItems)
        this.leftCheckedItems = []
        this.leftIndeterminate = false
        this.leftCheckAll = false
        this.rightNowItems = this.transferItems.filter(item=>{
          return this.rightIds.includes(item.id)
        })
      },
      rightToLeft(){
        this.allItems = this.allItems.filter(item=>{
          return !this.rightCheckedItems.includes(item.id)
        })

        let oldIds = this.rightCheckedItems.filter(item=>{
          return !this.transferItemsIds.includes(item)
        })

        this.rightIds = this.rightIds.filter(item=>{
          return !this.rightCheckedItems.includes(item)
        })
        this.rightCheckedItems = []
        this.rightIndeterminate = false
        this.rightCheckAll = false
        this.rightNowItems = this.transferItems.filter(item=>{
          return this.rightIds.includes(item.id)
        })
        this.rightItems = this.rightItems.filter(item=>{
          return !oldIds.includes(item.id)
        })
      },
      leftCheckChanged(value){
        let checkedCount = value.length;
        this.leftCheckAll = checkedCount === this.nowLeftItems.length;
        this.leftIndeterminate = checkedCount > 0 && checkedCount < this.nowLeftItems.length;
      },
      rightCheckChanged(value){
        let checkedCount = value.length;
        this.rightCheckAll = checkedCount === this.nowRightItems.length;
        this.rightIndeterminate = checkedCount > 0 && checkedCount < this.nowRightItems.length;
      },
      leftCheckAllChange(val) {
        this.leftCheckedItems = val ? this.nowLeftItems.map(item=>item.id) : [];
        this.leftIndeterminate = false;
      },
      rightCheckAllChange(val) {
        this.rightCheckedItems = val ? this.nowRightItems.map(item=>item.id) : [];
        this.rightIndeterminate = false;
      },
    },
    watch:{
      transferItems:function () {
        this.rightNowItems = this.rightItems.filter(item=>{
          return this.transferItems.map(item=>item.id).includes(item.id)
        })
        this.rightItems = this.rightItems.filter(item=>{
          return !this.transferItems.map(item=>item.id).includes(item.id)
        })
        this.rightIds = this.rightNowItems.map(item=>item.id)
      },
      dialogVisible:function () {
        this.rightIds = []
        this.rightItems = []
        this.rightNowItems = []
        this.leftCheckedItems = []
        this.rightCheckedItems = []
        this.leftIndeterminate = false
        this.rightIndeterminate = false
        this.leftCheckAll = false
        this.rightCheckAll = false
        this.leftSearchInput = ''
        this.rightSearchInput = ''
      }
    }
  }
</script>
<style lang="scss" scoped>
  .category_main{
    height: 100%;
    /deep/ .permission_dialog{
      .el-dialog{
        width: 900px;
        height: 500px;
        /*margin: 0 !important;*/
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
        .el-dialog__body{
          height: calc(100% - 61px);
          padding:1px 10px 20px 10px;
          .dialog_main{
            height: 100%;
            padding-right: 20px;
            .detail_main{
              height: 100%;
              .detail_left{
                height: 100%;
                padding:0 20px 20px 40px !important;
                border-right: 1px solid #e5e5e5;
                #areaTree{
                  border:none;
                }
              }
              .detail_right{
                height: 100%;
                padding:20px 20px 0 20px!important;
                .transfer_detail{
                  height: calc(100% - 50px);
                  .transfer_row{
                    height: 100%;
                    .transfer_left{
                      height: 100%;
                      border: 1px solid #e5e5e5;
                      .transfer_title{
                        padding: 0 10px;
                        line-height: 40px;
                        display: flex;
                        justify-content: space-between;
                        background-color: #eff3f6;
                      }
                      .transfer_search{
                        padding: 10px;
                      }
                      .transfer_checkbox{
                        height: calc(100% - 100px);
                        padding-bottom: 8px;
                        overflow: auto;
                        .el-checkbox-group{
                          .el-checkbox{
                            display: block;
                            margin-left: 10px;
                          }
                        }
                      }
                      .transfer_none{
                         font-size: 12px;
                         text-align: center;
                       }
                    }
                    .transfer_center{
                      height: 100%;
                      display: flex;
                      flex-direction: column;
                      justify-content: center;
                      align-items: center;
                      .el-button{
                        margin: 0;
                      }
                      .el-button:first-child{
                        margin-bottom: 20px;
                      }
                    }
                    .transfer_right{
                      height: 100%;
                      border: 1px solid #e5e5e5;
                      .transfer_title{
                        padding: 0 10px;
                        line-height: 40px;
                        display: flex;
                        justify-content: space-between;
                        background-color: #eff3f6;
                      }
                      .transfer_search{
                        padding: 10px;
                      }
                      .transfer_checkbox{
                        height: calc(100% - 100px);
                        padding-bottom: 8px;
                        overflow: auto;
                        .el-checkbox-group{
                          .el-checkbox{
                            display: block;
                            margin-left: 10px;
                          }
                        }
                      }
                      .transfer_none{
                        font-size: 12px;
                        text-align: center;
                      }
                    }
                  }
                }
                .transfer_footer{
                  height:50px;
                  padding:9px 0;
                  text-align: center;
                }
              }
            }
          }
        }
      }
    }
  }
</style>
