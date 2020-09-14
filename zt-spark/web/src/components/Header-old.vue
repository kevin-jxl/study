<template>
  <el-row class="header">
    <el-col class="logo" :span="4"> 
      <div class="grid-content bg-purple header-logo">
        <div class="logo_img">
          <img src="@/assets/logo.png" alt class="logo_img" />
        </div>
        <p class="logo_text" style="color:#fff;">{{$project.title}}</p>
      </div>
    </el-col>
    <el-col :span="17" class="header_center">
      <el-menu
        :default-active="activeIndex"
        mode="horizontal"
        @select="handleSelect"
        text-color="#fff"
        active-text-color="#ffd04b"
        style="float:right;"
      >
        <el-menu-item :index="item.id" v-for="(item,index) in menuData" :key="index">
          {{item.label}}
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="3" class="header_right">
      <div></div>
      <div>
        <span class="test">{{userNmaes}}</span>
      </div>
      <div @click="onLogout">
        <span>退出</span>
      </div>
    </el-col>
  </el-row>
</template>

<script>
// import { getUserMenu } from "@/api/basic/menu";
import { logout } from "@/api/login";
import { removeToken } from "@/util/auth";
import menuList from "../util/menu.js";

export default {
  data() {
    return {
      menuData: [],
      activeIndex: "8",
      userNmaes: ""
    };
  },
  mounted() {
    let me = this;
    // me.userInfo = JSON.parse(localStorage.getItem("user"));
    let username = "";
    username = JSON.parse(sessionStorage.getItem("userData"));
    this.userNmaes = username.sysUser.username;
    // getUserMenu().then(res => {
    //   this.toStringFun(res.data);
    //   console.log(res.data);
    //   this.menuData = res.data;
      this.menuData = menuList;
      let menus = me.menuData.filter(item => {
        return me.$route.path.indexOf(item.path) > -1;
      });
      if (menus.length > 0) {
        me.$store.commit("updateSelectedMenu", menus[0]);
        me.activeIndex = menus[0].id;
      } else {
        me.$store.commit("updateSelectedMenu", me.menuData[0]);
      }
    // });
  },
  methods: {
    onLogout() {
      logout().then(res => {
        sessionStorage.removeItem("userData");
        removeToken();
        this.$router.push("/login");
      });
    },
    handleSelect(key) {
      let me = this;
      let list = me.menuData.filter(item => {
        return item.id == key;
      });
      if (list.length == 0) {
        return;
      }
      let item = list[0];
      if (me.$route.path != item.path) {
        me.$store.commit("updateSelectedMenu", item);
        me.$router.push(item.path);
      }
    },
    getUserMenus() {
      getUserMenu().then(res => {
        console.log("菜单内容", res.data);
        this.menuData = res.data[0].children;
      });
    },
    toStringFun(arrData) {
      arrData.forEach(item => {
        item.id = item.id.toString();
        if (item.children) {
          this.toStringFun(item.children);
        }
      });
    }
  },
  computed: {}
};
</script>

<style lang="scss" scoped>
// @import "@/assets/style/global.scss";

.el-menu-item {
  background: $siderbarBgColor !important;
}
.el-menu {
  border-right: none !important;
}
.header {
  display: flex;
  width: 100%;
  background: $siderbarBgColor;
    .logo {
        height: 61px;
        line-height: 61px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        vertical-align: middle;
    .logo_text {
      vertical-align: middle;
      display: inline;
      margin-left: 10px;
    }
    .logo_img {
      vertical-align: middle;
      display: inline;
      width: 34px;
      height: 34px;
    }
  }
  .header_center {
    background: $siderbarBgColor;
    color: #fff !important;
    // display: flex;
    .el-menu-item {
      //   flex: 1;
      text-align: center;
      line-height: 61px;
    }
  }

  .el-menu.el-menu--horizontal {
    background: $siderbarBgColor;
  }
  .el-menu--horizontal > .el-menu-item {
    color: #fff;
  }
  .header_right {
    display: flex;
    cursor: pointer;
    div {
      margin-left: 10px;
      flex: 1;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      line-height: 61px;
      span {
        margin-left: 10px;
        color: #fff;
      }
    }
  }
}

::v-deep .el-menu.el-menu--horizontal {
    border-bottom: solid 0px #e6e6e6 !important;
}
</style>

