<template>
  <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumbs">
    <el-breadcrumb-item v-for="item in breadcrumbItems"
                        :to="{ path: nowMenuList.includes(item.path)?item.path+'/':'' }"
                        :key="item.path">
      {{item.name || item.meta.title}}
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>
<script>
  import { mapGetters } from 'vuex'
  export default {
    name: "breadcrumbs",
    components: { },
    data() {
      return {
        breadcrumbItems:null,
        menuList:['/Wel','/wel/index']
      };
    },
    computed: {
      ...mapGetters(['menus']),
      nowMenuList:function () {
        return this.menuList.concat(this.getMenuItem(this.menus))
      }
    },
    mounted() {
      this.getBreadcrumb()
    },
    methods: {
      getBreadcrumb() {
        let matched = this.$route.matched.filter(item => {
          return item.path.split('/').pop()&&(item.name || item.meta.title)
        })

        const first = matched[0]
        if (first && first.path.trim().toLocaleLowerCase().indexOf('Wel'.toLocaleLowerCase()) === -1) {
          matched = [{ path: '/Wel',name:'首页', meta: { title: '首页',isLeaf:true }}].concat(matched)
        }

        this.breadcrumbItems = matched
      },
      getMenuItem(items){
        let itemList = []
        items.forEach(item=>{
          if(item.children&&item.children.length>0){
            if(item.component!=='Layout'){
              itemList.push(item.path)
            }
            itemList = itemList.concat(this.getMenuItem(item.children))
          }else{
            if(item.component!=='Layout'){
              let paths = item.path.replace(/\/$/g, '')
              itemList.push(paths)
            }
          }
        })
        return itemList
      }
    },
    watch: {
      $route() {
        this.getBreadcrumb()
      }
    },
  };
</script>
<style lang="scss" scoped>
  .el-icon-arrow-down{
    display: none;
  }
  .breadcrumbs{
    margin-left: 10px;
    align-items: center;
    line-height: 40px;
  }
  .el-breadcrumb {
    font-size: 12px;
  }
</style>
