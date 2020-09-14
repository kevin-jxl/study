<template>
  <div>
    <el-breadcrumb separator=">">
        <el-breadcrumb-item v-for="(breadItem, idx) in breadList" :key="idx" @click="routePath(breadItem)">
            <!-- <router-link :to="breadItem.path">{{breadItem.meta.title}}</router-link> -->
            {{breadItem.meta.title}}
        </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
    data() {
        return {
            breadList: [], // 路由集合
        };
    },
    watch: {
        $route() {
            this.getBreadcrumb();
        },
    },
    methods: {
        isHome(route) {
            return route.name === "home";
        },
        getBreadcrumb() {
            let matched = this.$route.matched;

            this.breadList = matched.filter((item, index) => { return index > 0;});
        },
    },
    created() {
        this.getBreadcrumb();
    },
    mounted(){
    }
};
</script>

<style lang="scss" scoped>
.el-breadcrumb{
    line-height: 40px;
}
.el-breadcrumb__inner{
    color: $siderbarBgColor !important;
}
</style>