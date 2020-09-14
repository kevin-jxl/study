<template>
    <div class="full-screen aside-menu" style="position: relative;width:200px;">
        <p class="aside-title">{{trueName}}</p>
        <el-menu 
            :default-active="active"
            :default-openeds="openeds"
            class="el-menu-vertical-demo" 
            :collapse="isCollapse" 
            :collapse-transition='true'
            text-color="#333">
            <!-- {{item}} -->
            <NavMenu :index="item.id" v-for="item in selectedMenu.children" :key="item.id" :menu="item"></NavMenu>
        </el-menu>
        <!-- <div class="collapse" @click="isCollapse = !isCollapse">
            <span v-if="isCollapse"><i class="el-icon-d-arrow-right"></i></span>
            <span v-else><i class="el-icon-d-arrow-left"></i> 收起侧栏</span>
        </div> -->
    </div>
</template>

<script>
import {
    getUserMenu
} from "@/api/basic/menu";
import NavMenu from '../components/NavMenu.vue'
import menuList from '../util/menu.js'
import {mapGetters} from "vuex";
export default {
    data() {
        return {
            isCollapse: false,
            openeds: ['1'],
            active:'1-1',
            menuData: [],
            trueName:'首页'
        };
    },
    components: {
        NavMenu,
    },
    computed:{
        ...mapGetters(["selectedMenu"])
    },
    mounted () {
    },
    watch: {
        selectedMenu(value) {
            if(value.children){
                let selected = value.children.filter(menu => { return menu.path == this.$route.path});
                this.trueName = value.name;
                this.active = selected[0].id;
            }
            else{
                this.trueName = '首页'
            }
        }
    },
}
</script>

<style lang="scss" scoped>
.collapse{
    height: 30px; 
    width: 100%; 
    background: $asideBgColor; 
    position: absolute; 
    bottom: 0; 
    left: 0; 
    text-align: left;
    overflow: hidden;
    cursor: pointer;
    white-space: nowrap;
    color: #333;

    span{
        display: flex;
        padding-top: 5px;
        align-self: center;
        font-size: 14px; 
        color: #333;
    }
    i{
       padding: 3px 10px 3px 24px;
    }
}
.collapse:hover{
    background:$asideBgColor; 
}
.project {
    vertical-align: text-bottom;
    font-size: 30px;
    padding: 20px 30px;
    text-transform: uppercase;
    display: inline-block;
    font-weight: 600;
    background-image: -webkit-gradient(linear, 37.219838% 34.532506%, 36.425669% 93.178216%, from(#29cdff), to(#0a60ff), color-stop(0.37, #148eff));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}
.aside-title{
    height: 50px;
    line-height: 50px;
    width: 80%;
    margin:0 auto 10px;
    text-align: center;
    border-bottom: 1px solid #d9d9d9;
}
</style>


<style lang="scss" scoped="scoped">
/* .el-aside{
    min-width: 200px !important;
} */
.el-menu-vertical-demo:not(.el-menu--collapse) {
    min-height: 400px;
}
.el-menu {
    // border-right: solid 1px #e6e6e600 !important;
	border-right: none;
    text-align: left;
    background: $asideBgColor !important;
}
.el-menu-item.is-active{
	background: #f0efef;
}
// .el-menu-item:hover{
//     background: $asideBgColor !important;
//     color: #333;
// }
.el-submenu__title {
    padding: 0 40px 0 20px !important;
}
</style>