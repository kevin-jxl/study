<template>
  <div class="menu_items">
    <template v-for="(item,index) in topMenu">
      <el-menu-item v-if="!item.children||item.children.length===0" :index="filterPath(item.path,index)" @click="open(item)" :key="index">
        <span><i :class="'tunnyicon '+item.icons" v-if="item.type==='9'"></i>{{item.label}}</span>
      </el-menu-item>
      <el-submenu v-else :index="filterPath(item.path,index)" :popper-class="menuStyle" :key="index">
        <template slot="title">
          <i :class="'tunnyicon '+item.icons"></i>
          {{item.label}}
        </template>
        <top-menu-list :top-menu="item.children"></top-menu-list>
      </el-submenu>
    </template>
  </div>
</template>
<script>
  import { resolveUrlPath, setUrlPath } from '@/util/util'
  import { mapGetters } from 'vuex'
  export default {
    name:'top-menu-list',
    data(){
      return {

      }
    },
    props:{
      topMenu:{
        type:Array,
        default:()=>[]
      },
    },
    computed: {
      ...mapGetters(['themeName','menuType']),
      menuStyle:function () {
        if(this.menuType==='top'&&this.themeName==='theme-one'){
          return 'blueMenu'
        }else if(this.menuType==='top'&&this.themeName==='theme-two'){
          return 'whiteMenu'
        }else if(this.menuType==='top'&&this.themeName==='theme-three'){
          return 'blackMenu'
        }else if(this.menuType==='top'&&this.themeName==='theme-four'){
          return 'grayMenu'
        }
      }
    },
    methods:{
      open(item) {
        this.$router.push({
          path: resolveUrlPath(item.path, item.label),
          query: item.query
        })
      },
      filterPath(path, index) {
        return path == null ? index + '' : path
      },
    }
  }
</script>
