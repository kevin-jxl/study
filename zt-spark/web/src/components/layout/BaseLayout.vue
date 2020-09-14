<template>
    <el-container class="full-screen">
        <el-aside>
            <slot name="aside"></slot>
        </el-aside>
        <el-container style="height:100%;">
            <el-header v-show="$store.state.breadcrumb">
                <slot name="header">
                    <breadcrumb  v-if="$route.path != '/dashbord'"/>
                </slot>
            </el-header>
            <el-main>
                <slot name="main">
                    <router-view class="full-screen"/>
                </slot>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import Breadcrumb from '../../components/breadcrumb/Breadcrumb'
    export default {
        components: {
            Breadcrumb,
        }
    }
</script>

<style lang="scss">

.el-header {
    background-color: #f0efef;
    color: #333;
    height: 40px !important;
}
.el-footer {
    background-color: rgb(117, 117, 117);
    color: #333;
    height: 30px !important;
}
.el-aside {
    width: auto !important;
    background-color: #001529;
    color: #fff;
}
.el-main {
    color: #333;
    padding: 5px !important;
    background: #f0efef;
}
.el-menu-item i {
    color: #333 !important;
}
.el-submenu__title i {
    color: #333 !important;
}
.el-container{
    height: calc( 100% - 61px);
}
</style>