<template>
  <div class="home full-screen">
    <Header />
    <base-layout>
        <div slot="main" class="full-screen">
          <router-view class="full-screen"  v-if="isRouterAlive"/>
        </div>
        <div slot="aside" class="full-screen" v-if="$route.path != '/dashbord'">
          <Aside />
        </div>
    </base-layout>
  </div>
</template>

<script>
// @ is an alias to /src
import BaseLayout from '../components/layout/BaseLayout.vue'
import Aside from '../components/Aside.vue'
import Header from '../components/Header.vue'

export default {
  name: "Home",
  components: {
    BaseLayout,
    Header,
    Aside
  },
  provide(){
    return{
      reload:this.reload
    }
  },
  data(){
    return{
      isRouterAlive: true,
      asideVisible: true
    }
  },
  methods:{
    reload(){
      this.isRouterAlive = false;
      this.$nextTick(function(){
        this.isRouterAlive = true;
      })
      
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
