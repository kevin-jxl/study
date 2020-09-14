<template>
  <div class="menu-wrapper">
    <template v-for="(item,index) in menu">
      <el-menu-item v-if="item.parentId===-1&&item.type==='0'"
                    :index="filterPath(item.path,index)"
                    @click="open(item)"
                    :key="item.label" class="menu_icon">
        <i :class="item.icons" class="tunnyicon"></i>
        <span slot="title">{{item.label}}</span>
      </el-menu-item>
      <el-menu-item v-else-if="!item.children||item.children.length===0 "
                    :index="filterPath(item.path,index)"
                    @click="open(item)"
                    :key="item.label">
        <i :class="item.icons" class="tunnyicon"></i>
        <span slot="title">{{item.label}}</span>
      </el-menu-item>
      <el-submenu v-else
                  :index="filterPath(item.label,index)"
                  :key="item.name" :popper-class="menuStyle">
        <template slot="title">
          <i :class="item.icons" class="tunnyicon"></i>
          <span slot="title"
                :class="{'el-menu--display':isCollapse}">{{item.label}}</span>
        </template>
        <template v-if="item.children&&item.children.length>0">
          <template v-for="(child,cindex) in item.children">
            <el-menu-item :class="{'siderbar-active':nowTagValue==child.path}"
                          :index="filterPath(child.path,cindex)"
                          @click="open(child)"
                          v-if="!child.children||child.children.length==0"
                          :key="cindex">
              <!--<i :class="child.icon"></i>-->
              <span slot="title">{{child.label}}</span>
            </el-menu-item>
            <sidebar-item v-else
                          :menu="[child]"
                          :key="cindex"
                          :isCollapse="isCollapse"></sidebar-item>
          </template>
        </template>
      </el-submenu>
    </template>
  </div>
</template>
<script>
import { resolveUrlPath, setUrlPath } from '@/util/util'
import { mapGetters } from 'vuex'
export default {
  name: 'SidebarItem',
  data() {
    return {}
  },
  props: {
    menu: {
      type: Array
    },
    isCollapse: {
      type: Boolean
    }
  },
  created() { },
  mounted() { },
  computed: {
    ...mapGetters(['themeName','menuType']),
    nowTagValue: function() {
      return setUrlPath(this.$route)
    },
    menuStyle:function () {
      if(this.menuType==='aside'&&this.themeName==='theme-one'){
        return 'menu_style blueAsideMenu'
      }else if(this.menuType==='aside'&&this.themeName==='theme-two'){
        return 'menu_style whiteAsideMenu'
      }else if(this.menuType==='aside'&&this.themeName==='theme-three'){
        return 'menu_style blackAsideMenu'
      }else if(this.menuType==='aside'&&this.themeName==='theme-four'){
        return 'menu_style grayAsideMenu'
      }
    }
  },
  methods: {
    filterPath(path, index) {
      return path == null ? index + '' : path
    },
    open(item) {
      this.$router.push({
        path: resolveUrlPath(item.path, item.label),
        query: item.query
      })
    },
    openMenu(item){
      this.$router.push({
        path: resolveUrlPath(item.children[0].path, item.children[0].label),
        query: item.query
      })
    }
  }
}
</script>
<style lang="scss" scoped>
  .menu-wrapper{
    font-size: 13px;
  }
  .menu_style{
    .el-menu{
      .el-menu-item{
        height: 40px;
        line-height: 40px;
        &:hover{
          color: #3a8ee6;
        }
      }
      .is-active{
        color: #3a8ee6;
      }
    }
  }
</style>

