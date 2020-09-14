<template>
    <el-submenu v-if="menu.children && menu.children.length >= 1" :index="'' + menu.id">
        <template slot="title">
            <i :class="menu.icon"></i>
            <span>{{menu.label}}</span>
        </template>
        <NavMenu v-for="item in menu.children" :key="item.id" :menu="item"></NavMenu>
    </el-submenu>
    <el-menu-item v-else :index="menu.id.toString()" @click="clickMenuItem(menu)">
        <i :class="menu.icon"></i>
        <span slot="title">{{menu.label}}</span>
    </el-menu-item>  
</template>

<script>
    export default {
        name: 'NavMenu',
        props: {
            menu: {
                type: Object,
                default: () => {
                    return {};
                }
            },
        },
        methods: {
            clickMenuItem(_info) {
                // debugger
                this.$router.push(_info.path)
            }
        },
        
    }
</script>

<style lang="scss" scoped>

</style>