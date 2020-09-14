<template>
  <el-input v-if="inputVisible"
            v-model="input"
            @blur="inputBlur"
            ref="input_focus"
            size="mini"
            placeholder="请输入内容"></el-input>
  <div v-else class="inner_span"
       @contextmenu.prevent="openMenu('first',$event)">
    <span>{{innerText}}</span>
  </div>
</template>
<script>
  export default {
    name:'inputLabel',
    props:{
      innerText:{
        type: String,
        default: ''
      }
    },
    data(){
      return {
        inputVisible:false,
        input:''
      }
    },
    methods:{
      openMenu(ref,e){
        console.info(ref)
        console.info(e)
        this.inputVisible = true
        this.$nextTick(function () {
          this.$refs['input_focus'].focus()
        })
      },
      inputBlur(){
        this.inputVisible = false
      }
    }
  }
</script>
<style lang="scss" scoped>
  .inner_span{
    width: 100%;
  }
</style>
