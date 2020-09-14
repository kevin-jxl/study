<template>
  <div id="areaTree" class="z-tree">
    <el-container style="height: 100%">
      <el-header style="height: auto;padding: 10px;">
        <div class="search-input" v-if="searchEnable">
          <el-input
            :placeholder="searchPlaceholder"
            size="mini"
            style="width: 100%"
            v-model="searchInput"
            @keyup.enter.native="treeSearch"
          >
            <i slot="suffix" class="el-input__icon el-icon-search" @click.stop="treeSearch"></i>
          </el-input>
        </div>
      </el-header>
      <el-main style="padding:0 5px;height:calc(100% - 42px);">
        <div class="tree-box">
          <div class="zTreeDemoBackground left">
            <ul :id="'treeDemo'+name" class="ztree"></ul>
          </div>
        </div>
      </el-main>
    </el-container>


  </div>
</template>
<script>
  import "../../../static/util/ztree/jquery-1.4.4.min"
  import "../../../static/util/ztree/jquery.ztree.all.min"
  export default {
    name: 'zTree',
    components:{

    },
    props:{
      zNodes: {
        type:Array,
        default:()=>[]
      },
      simpleCheck: {
        type:Boolean,
        default:false
      },
      checkEnable: {
        type:Boolean,
        default:false
      },
      defaultSelectOne: {
        type:Boolean,
        default:false
      },
      searchEnable: {
        type:Boolean,
        default:false
      },
      searchPlaceholder: {
        type:String,
        default:'请输入查找'
      },
      expandAll: {
        type:Boolean,
        default:true
      }
    },
    data:function(){
      return{
        searchInput:'',
        noCheck:{
          enable:false
        },
        name:'',
        treeNode:undefined,
        check: {
          enable: true,
          nocheckInherit: true ,
          chkboxType: { "Y" : "ps", "N" : "ps" },
        },
        checkSimple: {
          enable: true,
          nocheckInherit: false ,
          chkboxType: { "Y" : "ps", "N" : "ps" },
          chkStyle: "radio",radioType: "all"
        },
        data: {
          simpleData: {
            enable: true
          }
        },
        callback: {
          beforeClick: this.beforeClick,
          onClick: this.zTreeOnClick,
          onCheck: this.zTreeOnCheck,
        },
        callbackNoCheck: {
          onClick: this.zTreeOnClick
        },
      }
    },
    methods:{
      zTreeOnClick: function(event, treeId, treeNode) {
        this.$emit('tree-click',event, treeNode.id, treeNode, treeNode.getPath());
      },
      zTreeOnCheck: function(event, treeId, treeNode) {
        // console.log(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
        var zTree = this.treeNode
        // var zTree = $.fn.zTree.getZTreeObj("treeDemo"+this.name)

        var  checkCount = zTree.getCheckedNodes(true).length,//选中
          nocheckCount = zTree.getCheckedNodes(false).length,//未选中
          changeCount = zTree.getChangeCheckedNodes().length;//获取输入框勾选状态被改变的节点集合（与原始数据 checkedOld 对比）
        var checkedNames = [],checkedIds = [];
        for (var i = 0; i <= zTree.getCheckedNodes(true).length - 1; i++) {
          checkedIds.push(zTree.getCheckedNodes(true)[i].id);
          checkedNames.push(zTree.getCheckedNodes(true)[i].name);
        };
        this.$emit('tree-check',event, checkedIds, zTree.getCheckedNodes(true));
      },
      beforeClick: function(treeId, treeNode) {
        var zTree = this.treeNode;
        // var zTree = $.fn.zTree.getZTreeObj("treeDemo"+this.name);
        // zTree.checkNode(treeNode, !treeNode.checked, null, true);
        zTree.checkNode(treeNode, !treeNode.checked, true, true); //第二个参数!treeNode.checked和"",省略此参数效果等同，则根据对此节点的勾选状态进行 toggle 切换，第三个参数设置为true时候进行父子节点的勾选联动操作 ，第四个参数true 表示执行此方法时触发 beforeCheck & onCheck 事件回调函数；false 表示执行此方法时不触发事件回调函数
        return false;
      },
      initTree:function () {
        let setting = {
          check:this.check,
          data:this.data,
          callback:this.callback
        };
        let settingSimple = {
          check:this.checkSimple,
          data:this.data,
          callback:this.callback
        };
        let settingNoCheck = {
          check:this.noCheck,
          data:this.data,
          callback:this.callbackNoCheck
        };

        if(this.checkEnable){
          if(this.simpleCheck){
            $.fn.zTree.init($("#treeDemo"+this.name), settingSimple, this.zNodes).expandAll(this.expandAll);
          }else{
            $.fn.zTree.init($("#treeDemo"+this.name), setting, this.zNodes).expandAll(this.expandAll);
          }
        }else{
          $.fn.zTree.init($("#treeDemo"+this.name), settingNoCheck, this.zNodes).expandAll(this.expandAll);

          if(this.defaultSelectOne){
            let zTree = $.fn.zTree.getZTreeObj("treeDemo"+this.name)
            let nodes = zTree.getNodes();
            if (nodes.length>0) {
              zTree.selectNode(nodes[0]);
            }
          }
        }

        this.treeNode = $.fn.zTree.getZTreeObj("treeDemo"+this.name);
      },
      treeSearch:function () {
        let searchName = this.searchInput;
        var node = this.treeNode.getNodesByFilter(function (node) {
          return node.name.indexOf(searchName) !== -1
        }, true);
        if(this.checkEnable){
          this.treeNode.checkNode(node, true, true);
          this.$emit('tree-search',this.treeNode.getCheckedNodes(true));
        }else{
          this.treeNode.selectNode(node);
          this.$emit('tree-search',node);
        }
      },
      treeAddNodes:function (parent,newNodes) {
        let parentNode =  this.treeNode.getNodesByParam("name", parent.name, null);
        this.treeNode.addNodes(parentNode, newNodes);
      },
      treeRemoveNode:function (node) {
        let removeNode =  this.treeNode.getNodesByParam("name", node.name, null);
        this.treeNode.removeNode(removeNode);
      },
      selectNodeById:function (params) {
        let selectNode =  this.treeNode.getNodesByParam("id", params, null);
        this.treeNode.selectNode(selectNode[0])
      }
    },
    mounted(){
      this.initTree()
      this.name = new Date().getTime()
    },
    watch:{
      zNodes:function () {
        this.initTree()
      },
      checkEnable:function () {
        this.initTree()
      },
      simpleCheck:function () {
        this.initTree()
      },
      // '$route':function () {
      //   this.initTree()
      // }
    }
  }
</script>
<style lang="scss" scoped>
  #areaTree{
    border:1px solid #e5e5e5;    margin-bottom: 2px;border-radius: 4px;overflow: hidden;
  }
  .search-input{
    /*margin: 5px;*/
    font-size: 12px;
  }
</style>
