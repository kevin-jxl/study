<template>
  <el-select v-model="input"
             filterable
             placeholder="请选择"
             v-if="inputVisible"
             ref="input_focus"
             size="mini"
             @change="inputBlur"
  >
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
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
        options: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
        value8: '',
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
