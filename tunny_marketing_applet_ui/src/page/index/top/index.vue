<template>
  <div class="top">
    <h1 class="top-title">
      <div class="top_icons">
        <span class="logo_title is-bold">{{website.title}} </span>
        <div class="top_icon" v-if="menuType!=='top'">
          <i class="tunnyicon icon-tunny-shouqian tag-collapse"
             :class="[{ 'tag-collapse_right': isCollapse }]"
             @click="showCollapse"></i>
        </div>
      </div>
      <div class="top_menu" v-if="menuType==='top'">
        <el-menu :default-active="nowTagValue" class="el-menu-demo"
                 mode="horizontal" menu-trigger="hover">
          <top-menu-list :top-menu="resetMenu"></top-menu-list>
        </el-menu>
      </div>
    </h1>
    <div class="top-button is-right">
      <el-tooltip class="item icon_first"
                  effect="dark"
                  :content="isFullScren?'退出全屏':'全屏'"
                  placement="bottom">
        <span class="top-item">
          <i class="tunnyicon icon-tunny-fangda"
             @click="handleScreen"></i>
        </span>
      </el-tooltip>
      <el-badge value="3" class="item">
        <i class="tunnyicon icon-tunny-xiaoxi"></i>
      </el-badge>
      <el-dropdown class="item_name" placement="bottom-start">
        <span class="el-dropdown-link">
          {{userInfo.userRealname}}
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <router-link to="/">首页</router-link>
          </el-dropdown-item>
          <el-dropdown-item>
            <router-link to="/info/index">修改信息</router-link>
          </el-dropdown-item>
          <el-dropdown-item @click.native="logout"
                            divided>退出系统</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-tooltip class="item"
                  effect="dark"
                  content="用户头像"
                  placement="bottom">
        <img class="top-userImg"
             :src="userInfo.avatar">
      </el-tooltip>
    </div>
  </div>
</template>
<script>
import { mapState, mapGetters } from 'vuex';
import { fullscreenToggel, listenfullscreen,setUrlPath,initMenu } from '@/util/util';
import { validatenull } from "@/util/validate";
import topLock from './top-lock';
import topMenu from './top-menu';
import topTheme from './top-theme';
import topColor from './top-color';
import topMenuList from './top-menu-list'
export default {
  components: { topLock, topMenu, topTheme, topColor,topMenuList },
  name: 'top',
  data () {
    return {
      itemNo:8,
      screenWidth:document.body.clientWidth,
    }
  },
  props:{
    topMenu:{
      type:Array,
      default:()=>[]
    },
    menuType:{
      type:String,
      default:'aside'
    },
    themeName:{
      type:String,
      default:'theme-four'
    }
  },
  filters: {},
  created() {
    if (validatenull(this.menu)) {
      this.$store.dispatch("GetMenu").then(data => {
        initMenu(this.$router, data);
      });
    }
  },
  mounted () {
    listenfullscreen(this.setScreen)
    let that = this
    window.onresize = () => {
      return (() => {
        window.screenWidth = document.body.clientWidth
        that.screenWidth = window.screenWidth
      })()
    }

    if(this.screenWidth >= 1920){
      this.itemNo = 11
    }else if(this.screenWidth >= 1660){
      this.itemNo = 10
    }else if(this.screenWidth >= 1440){
      this.itemNo = 9
    }else{
      this.itemNo = 8
    }
  },
  computed: {
    ...mapState({
      userInfo: state => state.user.userInfo
    }),
    ...mapGetters(['isFullScren', 'isCollapse','website']),
    nowTagValue: function() {
      return setUrlPath(this.$route);
    },
    resetMenu:function () {
      if(this.topMenu.length>this.itemNo){
        let firstMenu = this.topMenu.slice(0,this.itemNo)
        let lastMenu = this.topMenu.slice(this.itemNo)
        firstMenu.push({
          label:'...',
          name:'...',
          // icons:'tunnyicon icon-tunny-shenglvehao',
          path: '/other',
          children:lastMenu,
          type:'9'
        })
        return firstMenu
      }else{
        return this.topMenu
      }
    }
  },
  methods: {
    showCollapse () {
      this.$store.commit('SET_COLLAPSE')
    },
    handleScreen () {
      fullscreenToggel()
    },
    setScreen () {
      this.$store.commit('SET_FULLSCREN')
    },
    logout () {
      this.$confirm('是否退出系统, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          this.$router.push({ path: '/login' })
          // window.location.reload()
        })
      })
    },
    changeTags(){
      this.$store.commit('SET_TAGS',0)
    },
    changeBreadcrumbs(){
      this.$store.commit('SET_TAGS',1)
    },
    tagOrBreadcrumbs(command){
      switch (command){
        case 'tag':this.$store.commit('SET_TAGS',0);break;
        case 'breadcrumbs':this.$store.commit('SET_TAGS',1);break;
      }
    }
  },
  watch:{
    screenWidth:function (val) {
      if(this.screenWidth >= 1920){
        this.itemNo = 11
      }else if(this.screenWidth >= 1660){
        this.itemNo = 10
      }else if(this.screenWidth >= 1440){
        this.itemNo = 9
      }else{
        this.itemNo = 8
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .top{
    height: 100%;
    .top-title{
      height: 100%;
      .top_icons{
        float: left;
        width: 160px;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        .logo_title {
          padding: 0 10px 0 0px;
          color: #409eff;
          font-size: 24px;
          height: 100%;
          line-height: 50px;
          &.is-bold {
            font-weight: 700;
          }
        }
        .top_icon{
          padding-right: 0px;
          i{
            font-size: 14px !important;
            color: #333335;
          }
        }
      }
      /deep/ .top_menu{
        float:left;
        width:calc(100% - 200px);
        height: 100%;
        .el-menu-demo{
          background-color: #ffffff;
          height: 100%;
          width: 100%;
          .menu_items{
            height: 100%;
            display: flex;
            justify-content: flex-start;
            li{
              width: calc(100% / 9);
              height: 100%;
              font-size: 13px;
              display: flex;
              justify-content: center;
              align-items: center;
              span{
                display: flex;
                justify-content: center;
                align-items: center;
              }
              /*text-align: center;*/
              .el-submenu__title{
                padding:0 10px;
                height: 100%;
                font-size: 13px;
                display: flex;
                justify-content: center;
                align-items: center;
                position: absolute;
              }
              .el-submenu__icon-arrow{
                position: initial;
                right:10px;
                top:0;
                line-height: 49px;
                margin-top: 0;
                display: none;
              }
              i{
                height: 100%;
                /*margin-top: -5px;*/
                font-size: 15px !important;
              }
            }
            .el-menu-item, .el-submenu__title{
              line-height: 49px;
            }
            &:focus{
              outline:none;
            }
          }
        }
      }
    }
    .is-right{
      .item{
        margin-right: 12px;
      }
      .icon_first{
        border-left: 1px solid #e5e5e5;
        padding-left: 15px;
      }
      .el-badge{
        margin-right: 20px;
      }
      .item_name{
        padding-left: 15px;
        border-left: 1px solid #e5e5e5;
      }
      .iconfont{
        font-size: 16px !important;
        color: #8a8a8a;
        padding:1px 0;
      }
      .el-dropdown /deep/ .el-icon-news {
        font-size: 18px;
        margin-right: 12px;
      }
    }
  }
  .daidan_drop{
    width: 100px;
  }

  @media screen and (min-width: 1440px){
    .top{
      .top-title{
        /deep/ .top_menu{
          .el-menu-demo{
            .menu_items{
              li{
                width:calc(100% / 10)
              }
            }
          }
        }
      }
    }
  }
  @media screen and (min-width: 1660px){
    .top{
      .top-title{
        /deep/ .top_menu{
          .el-menu-demo{
            .menu_items{
              li{
                width:calc(100% / 11)
              }
            }
          }
        }
      }
    }
  }
  @media screen and (min-width: 1920px){
    .top{
      .top-title{
        /deep/ .top_menu{
          .el-menu-demo{
            .menu_items{
              li{
                width:calc(100% / 12)
              }
            }
          }
        }
      }
    }
  }
</style>
<style lang="scss">
  .el-menu--horizontal{
    font-weight: 700;
    width: 101px;
    .el-menu{
      background-color: #545d64;
      width: 100%;
      min-width: auto;
      .menu_items{
        background-color: #545d64;
        color:#ffffff;
        width: 100%;
        padding:0;
        .el-menu-item{
          color:#ffffff;
          background-color: #545d64;
          font-size: 13px;
          width: 100%;
          min-width: 100px;
          font-weight: normal;
          text-align: center;
          border-bottom: none !important;
          padding: 0;
          /*border-bottom: 1px solid #68d8ff;*/
          i{
            display: none;
          }
        }
        .el-menu-item:last-child{
          border-bottom: none;
        }
        .el-submenu{
          background-color: #545d64;
          color:#ffffff;
          .el-submenu__title{
            color:#ffffff;
            font-size: 13px;
            background-color: #545d64;
            .el-submenu__icon-arrow{
              right: 10px;
              top:50%;
              margin-top: -9px;
              display: none;
            }
          }
          .el-menu--horizontal{
            /*left:141px !important;*/
          }
        }
        i{
          font-size: 18px !important;
          margin-top: -2px;
        }
      }
    }
    .tunnyicon{
      font-size: 20px !important;
      margin-right: 5px;
    }
    .el-icon-arrow-down{
      margin-right: 0;
    }
  }

  @media screen and (min-width: 1440px){
    .el-menu--horizontal{
      width: 114px;
    }
  }
  @media screen and (min-width: 1660px){
    .el-menu--horizontal{
      width: 123px;
    }
  }
  @media screen and (min-width: 1920px){
    .el-menu--horizontal{
      width: 127px;
    }
  }
</style>

