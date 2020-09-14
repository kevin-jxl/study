<template>
  <el-container class="avue-contail" :class="navigationStyle">
    <el-header class="avue-tabs">
      <!-- 顶部导航栏 -->
      <top :menu-type="menuType" :top-menu="concatMenu" :theme-name="themeName"/>
    </el-header>
    <el-container class="container_main">
      <el-aside v-if="menuType==='aside'"
                :style="{width: isCollapse ? asideWidthCollapse : asideWidth}"
                :class="{aside_collapse:isCollapse}"
                class="aside_menu">
        <sidebar class="avue-sidebar" :sider-menu="concatMenu"></sidebar>
      </el-aside>
      <el-main class="avue-main">
        <!-- 标签栏 -->
        <template v-if="showWelTagsAndBreadcrumbs||!isWel">
          <tags v-if="isTags===0"/>
          <breadcrumbs v-else/>
        </template>
        <!-- 主体视图层 -->
        <transition name="fade-transverse">
          <keep-alive>
            <router-view class="avue-view" v-loading="isLoading"
                         v-if="$route.meta.keepAlive&&isRouteAlive"
            />
          </keep-alive>
        </transition>
        <transition name="fade-transverse">
          <router-view class="avue-view" v-loading="isLoading"
                       v-if="!$route.meta.keepAlive&&isRouteAlive"
          />
        </transition>
      </el-main>
    </el-container>
    <el-button class="set_up_button"
               :class="{isOpen:drawerVisible}"
               @click="drawerVisible=!drawerVisible"
    >
      <i :class="drawerVisible?'tunnyicon icon-tunny-fork':'tunnyicon icon-tunny-shezhi'"></i>
    </el-button>
    <Drawer title=""
            v-model="drawerVisible"
            width="300"
    >
      <div slot="close">
        <i class="el-icon-close"></i>
      </div>
      <div class="drawer_main">
        <div class="drawer_item">
          <div class="item_title">整体风格</div>
          <div class="item_img">
            <div class="item_theme" @click="handleTheme('theme-one')">
              <img src="../../assets/img/theme1.png" class="theme_img"/>
              <div class="selected_item" :class="{isShow:currentTheme==='theme-one'}">
                <i class="el-icon-check"></i>
              </div>
              <span class="theme_title">蓝黑</span>
            </div>
            <div class="item_theme" @click="handleTheme('theme-two')">
              <img src="../../assets/img/theme2.png" class="theme_img"/>
              <div class="selected_item" :class="{isShow:currentTheme==='theme-two'}">
                <i class="el-icon-check"></i>
              </div>
              <span class="theme_title">白灰</span>
            </div>
            <div class="item_theme" @click="handleTheme('theme-three')">
              <img src="../../assets/img/theme3.png" class="theme_img"/>
              <div class="selected_item" :class="{isShow:currentTheme==='theme-three'}">
                <i class="el-icon-check"></i>
              </div>
              <span class="theme_title">深灰</span>
            </div>
            <div class="item_theme" @click="handleTheme('theme-four')">
              <img src="../../assets/img/theme4.png" class="theme_img"/>
              <div class="selected_item" :class="{isShow:currentTheme==='theme-four'}">
                <i class="el-icon-check"></i>
              </div>
              <span class="theme_title">浅灰</span>
            </div>
          </div>
        </div>
        <div class="drawer_item">
          <div class="item_title">导航风格</div>
          <div class="tag_button">
            <el-button round size="small" :class="{isSelected:isTags===1}"
                       @click="handleType('1')"
            >面包屑</el-button>
            <el-button round size="small" :class="{isSelected:isTags===0}"
                       @click="handleType('0')"
            >Tab标签</el-button>
          </div>
        </div>
        <div class="drawer_item">
          <div class="item_title">菜单版式</div>
          <div class="tag_button">
            <el-button round size="small" :class="{isSelected:menuType==='top'}"
                       @click="handleMenuType('top')"
            >顶部菜单</el-button>
            <el-button round size="small" :class="{isSelected:menuType==='aside'}"
                       @click="handleMenuType('aside')"
            >侧边菜单</el-button>
          </div>
        </div>
        <div class="drawer_item_title">
          <div class="title_tag">
            <div class="title">打开页面缓存</div>
            <el-switch
              v-model="cacheControl">
            </el-switch>
          </div>
        </div>
      </div>
    </Drawer>
  </el-container>
</template>

<script>
import { mapGetters } from 'vuex'
import tags from './tags'
import top from './top/'
import sidebar from './sidebar/'
import breadcrumbs from './breadcrumbs'
import { validatenull } from '@/util/validate'
import { calcDate } from '@/util/date'
import { setStore, getStore } from '@/util/store'
import { setTheme } from '@/util/util'
import ElCollapseTransition from "element-ui/src/transitions/collapse-transition";
export default {
  components: {
    ElCollapseTransition,
    top,
    tags,
    sidebar,
    breadcrumbs
  },
  name: 'index',
  data() {
    return {
      // [侧边栏宽度] 正常状态
      asideWidth: '170px',
      // [侧边栏宽度] 折叠状态
      asideWidthCollapse: '60px',
      // 刷新token锁
      refreshLock: false,
      isRouteAlive:true,
      drawerVisible:false,
      currentTheme:'1',
      // menuType:'top',
      defaultMenu:[
        {
          id:100,
          label:'首页',
          name:'首页',
          path: '/wel/index',
          icons:'tunnyicon icon-tunny-shouye',
          component: 'Layout',
          parentId:-1,
          type:'9',
        },
        {
          id:102,
          label:'综合实例',
          name:'综合实例',
          path: '/example',
          icons:'tunnyicon icon-tunny-shezhi2',
          component: 'Layout',
          parentId:-1,
          type:'9',
          children:[
            {
              label: 'crud实例',
              path: '/crud/index',
              icon: 'el-icon-setting',
              parentId: 2
            },
            {
              label: 'EXCEL测试',
              path: '/sexcel/index',
              icon: 'el-icon-setting',
              parentId: 3
            },
            {
              label: 'DEMO',
              path: '/demo/index',
              icon: 'el-icon-tickets',
              parentId: 4
            }
          ]
        }
      ],
      showWelTagsAndBreadcrumbs:true
    }
  },
  created() {
  },
  computed: {
    ...mapGetters(['isLock', 'isCollapse', 'website','isTags',
      'isLoading','themeName',"menus",'menuType']),
    routeWel(){
      return this.$route.path.trim().toLocaleLowerCase().indexOf('Wel'.toLocaleLowerCase()) === -1
    },
    showMenu(){
      return this.checkMenu(this.menus)
    },
    concatMenu:function () {
      return this.defaultMenu.concat(this.showMenu)
    },
    navigationStyle:function () {
      return {
        topBlue:this.menuType==='top'&&this.themeName==='theme-one',
        topWhite:this.menuType==='top'&&this.themeName==='theme-two',
        topBlack:this.menuType==='top'&&this.themeName==='theme-three',
        topGray:this.menuType==='top'&&this.themeName==='theme-four',
        asideBlue:this.menuType==='aside'&&this.themeName==='theme-one',
        asideWhite:this.menuType==='aside'&&this.themeName==='theme-two',
        asideBlack:this.menuType==='aside'&&this.themeName==='theme-three',
        asideGray:this.menuType==='aside'&&this.themeName==='theme-four'
      }
    },
    isWel:function () {
      return this.$route.path.indexOf('/wel') !== -1
    },
    cacheControl: {
      get () {
        return this.$store.state.common.cacheControl
      },
      set (value) {
        this.handleCacheControl(value)
      }
    }
  },
  mounted() {
    this.currentTheme = this.themeName
  },
  props: [],
  provide(){
    return {
      reLoad : this.reLoad
    }
  },
  methods: {
    reLoad(){
      //设置当前页为不缓存
      this.$route.meta.keepAlive = false
      this.isRouteAlive = false
      setTimeout(()=>{
        this.$nextTick(()=>{
          this.isRouteAlive = true
        })
      },200)
    },
    handleTheme(val){
      this.currentTheme = val
      this.$store.commit('SET_THEME_NAME', val)
      // setTheme(val)
    },
    handleType(val){
      switch (val){
        case '0':this.$store.commit('SET_TAGS',0);break;
        case '1':this.$store.commit('SET_TAGS',1);break;
      }
    },
    handleMenuType(val){
      this.$store.commit('SET_MENU_TYPE',val);
    },
    handleCacheControl(val){
      this.$store.commit('SET_CACHE_CONTROL',val);
    },
    checkMenu(params){
      params.forEach(item=>{
        if(item.children&&item.children.length>0&&item.children[0].path===item.path+'/'){
          item.type = '0'
          item.children = []
          item.path = item.path+'/'
        }
        if(item.children&&item.children.length>0){
          item.children = this.checkMenu(item.children)
        }
      })
      return params
    }
  }
}
</script>

<style lang="scss" scoped>
  .avue-contail {
    height: 100%;
    .container_main{
      height: calc(100% - 50px);
    }
  }
  .avue-sidebar {
    height: 100%;
  }
  .avue-tabs {
    padding: 0;
    height: 50px !important;
  }
  .avue-main {
    position: relative;
    padding: 0 20px;
    background-color: #edf2f5;
  }
  .avue-view {
    width: calc(100% - 40px);
    height: calc(100% - 60px) !important;
    background-color: #ffffff;
    position: absolute;
    top: 40px;
    overflow: hidden;
    /*left: 0;*/
  }
  .avue-view-wel{
    height: 100% !important;
  }
  .el-aside::-webkit-scrollbar{
    height:0 !important;
  }
  .aside_menu{
    transition:width 0.1s ease-in;
  }
  .set_up_button{
    position: absolute;
    top:40%;
    right: 5px;
    width: 40px;
    height: 40px;
    padding:0;
    opacity: 0.5;
    border-radius: 10px;
    /deep/ i{
      font-size: 40px!important;
    }
    &:hover{
      opacity: 1;
    }
    transition: right 0.25s linear;
  }
  .isOpen{
    right: 303px;
    z-index: 1001;
    opacity: 1;
    width: 42px;
    height: 42px;
    background-color: #00a2d4;
    border: 1px solid #00a2d4;
    padding:10px;
    /deep/ i{
      font-size: 20px!important;
      color:#ffffff;
    }
  }
  /deep/ .v-transfer-dom{
    .ivu-drawer-close{
      right: 10px;
      top: 10px;
      i{
        font-size: 16px;
      }
    }
    .ivu-drawer-body{
      padding: 30px 20px;
      .drawer_item{
        margin-bottom: 20px;
        .item_title{
          font-weight: 700;
          font-size: 14px;
        }
        .item_img{
          padding:20px 0 0 0;
          display: flex;
          justify-content: space-around;
          align-items: center;
          .theme_img{
            width: 60px;
          }
          .item_theme{
            width: 60px;
            height: 80px;
            position: relative;
            cursor: pointer;
            text-align: center;
            .selected_item{
              display: none;
              position: absolute;
              top: calc(calc(100% - 20px) / 2);
              left: 50%;
              margin-left: -10px;
              margin-top: -11px;
              i{
                font-size: 20px;
                color: #00a2d4;
              }
            }
            .isShow{
              display: block;
            }
            .theme_title{
              text-align: center;
            }
          }
        }
        .tag_button{
          display: flex;
          justify-content: space-around;
          align-items: center;
          padding: 20px 20px 0 20px;
          .isSelected{
            background-color: #1890ff;
            color:#ffffff;
          }
        }
      }
      .drawer_item_title{
        padding-top: 20px;
        border-top: 1px solid #e8e8e8;
        .title_tag{
          display: flex;
          justify-content: space-between;
          align-items: center;
          .title{
            font-size: 14px;
            font-weight: 700;
            margin-right: 10px;
          }
        }
      }
    }
  }
</style>
