<template>
  <div class="sidebar-container">
    <el-menu unique-opened :default-active="nowTagValue" class="el-menu-vertical-demo" mode="vertical"
             background-color="#535d62"
             text-color="#ffffff"
             active-text-color="#409eff" :collapse="isCollapse">
      <sidebar-item :menu="siderMenu" :isCollapse="isCollapse"></sidebar-item>
    </el-menu>
  </div>
</template>

<script>
import { setUrlPath } from "@/util/util";
import { mapGetters } from "vuex";
import SidebarItem from "./sidebarItem";
import logo from "./logo";
import { validatenull } from "@/util/validate";
import { initMenu } from "@/util/util";
export default {
  name: "sidebar",
  components: { SidebarItem, logo },
  data() {
    return {};
  },
  created() {

  },
  props:{
    siderMenu:{
      type:Array,
      default:()=>[]
    }
  },
  computed: {
    ...mapGetters(["tag", "isCollapse"]),
    nowTagValue: function() {
      return setUrlPath(this.$route);
    }
  },
  mounted() {},
  methods: {}
};
</script>
<style lang="scss" scoped>
  .colros{
    color: #535d62;
  }

  .sidebar-container,.el-menu-vertical-demo,{
    width: 100%;
    height: 100%;
    transition:width 0.1s ease-in-out;
  }
</style>

