<template>
  <div class="home full-screen">
    <Header />
    <base-layout>
        <div slot="main" class="full-screen">
          <router-view />
        </div>
        <div slot="aside" class="full-screen">
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
  data() {
    return {
      
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
