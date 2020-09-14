<template>
  <div class="tags-container">
    <div class="tag_left" v-if="isShow">
      <el-button  icon="el-icon-arrow-left" size="mini" title="前一个"
                  @click="moveForward"></el-button>
    </div>
    <div class="tag_center" :class="{isWhole:!isShow}">
      <!-- tag盒子 -->
      <div class="tags-box" ref="tagBox">
        <div class="tags-list" ref="tagsList" @mousewheel="hadelMousewheel" @mouseup="hadelMouseUp" @mousemove="hadelMouse" @mousedown="hadelMousestart" @touchup="hadelMouseUp" @touchmove="hadelMouse" @touchstart="hadelMousestart">
          <div ref="tagsPageOpened" class="tag-item"
               :class="{'is-active':nowTagValue==item.value,isOnly:!item.close}"
               :name="item.value"
               @contextmenu.prevent="openMenu(item,$event)"
               v-for="(item,index) in tagList"
               :key="index"
               @click="openUrl(item)">
            <!--<span class="icon-yuan tag-item-icon"></span>-->
            <div class="tag-text">{{item.label||(item.meta&&item.meta.title)}}</div>
            <i class="el-icon-close tag-close" @click.stop="closeTag(item)" v-if="item.close"></i>
            <i class="el-icon-close tag-hide" v-else></i>
          </div>
        </div>
        <!--<div class="tags-menu pull-right tags_button">
          <el-button  icon="el-icon-d-arrow-left" size="mini" title="第一个"
                      @click="goFirst" :disabled="isFirstTag"></el-button>
          <el-button  icon="el-icon-arrow-left" size="mini" title="前一个"
                      @click="goPrevious" :disabled="isFirstTag"></el-button>
          <el-button  icon="el-icon-arrow-right" size="mini" title="后一个"
                      @click="goNext" :disabled="isLastTag"></el-button>
          <el-button  icon="el-icon-d-arrow-right" size="mini" title="最后一个"
                      @click="goLast" :disabled="isLastTag"></el-button>
        </div>-->
        <ul v-show="visible" :style="{left:left+'px',top:top+'px'}" class="contextmenu">
          <li @click="closeTag(selectedTag)">关闭</li>
          <li @click="closeOthersTags">关闭其他</li>
          <li @click="closeAllTags">关闭全部</li>
          <li @click="refreshTag" v-if="nowTagValue===selectedTag.value">刷新</li>
        </ul>
      </div>
    </div>
    <div class="tag_right" v-if="isShow">
      <el-button  icon="el-icon-arrow-right" size="mini" title="后一个"
                  @click="backwardShift"></el-button>
    </div>
  </div>
</template>
<script>
import { resolveUrlPath, setUrlPath } from "@/util/util";
import { mapState, mapGetters } from "vuex";
export default {
  name: "tags",
  data() {
    return {
      visible: false,
      tagBodyLeft: 0,
      lock: false,
      startX: 0,
      startY: 0,
      endX: 0,
      endY: 0,
      top: 0,
      left: 0,
      selectedTag: {},
      isShow:false,
    };
  },
  created() {},
  mounted() {
    this.init();
  },
  inject:['reLoad'],
  watch: {
    $route(to) {
      this.init();
    },
    visible(value) {
      if (value) {
        document.body.addEventListener("click", this.closeMenu);
      } else {
        document.body.removeEventListener("click", this.closeMenu);
      }
    },
    tagBodyLeft(value) {
      this.$refs.tagsList.style.left = value + "px";
    },
    isShow(){
      this.init()
    }
  },
  computed: {
    ...mapGetters(["tagWel", "tagList", "isCollapse", "tag"]),
    nowTagValue: function() {
      return setUrlPath(this.$route);
    },
    tagListNum: function() {
      return this.tagList.length != 0;
    },
    isFirstTag:function () {
      let nowIndex = this.tagList.findIndex(item=>item.value === this.nowTagValue)
      return nowIndex===0
    },
    isLastTag:function () {
      let nowIndex = this.tagList.findIndex(item=>item.value === this.nowTagValue)
      return nowIndex===this.tagList.length - 1
    },
  },
  methods: {
    init() {
      this.refsTag = this.$refs.tagsPageOpened;
      setTimeout(() => {
        this.refsTag.forEach((item, index) => {
          if (this.tag.value === item.attributes.name.value) {
            let tag = this.refsTag[index];
            this.moveToView(tag);
          }
        });
      }, 1);
    },
    hadelMouseUp(e) {
      this.lock = false;
    },
    hadelMousestart(e) {
      this.lock = true;
      if (e.clientX && e.clientY) {
        this.startX = e.clientX;
        this.startY = e.clientY;
      } else {
        this.startX = e.changedTouches[0].pageX;
        this.startY = e.changedTouches[0].pageY;
      }
    },
    hadelMouse(e) {
      const boundarystart = 0,
        boundaryend =
          this.$refs.tagsList.offsetWidth - this.$refs.tagBox.offsetWidth + 100;
      if (!this.lock) {
        return;
      }
      //鼠标滑动
      if (e.clientX && e.clientY) {
        this.endX = e.clientX;
        this.endY = e.clientY;
        //触摸屏滑动
      } else {
        //获取滑动屏幕时的X,Y
        this.endX = e.changedTouches[0].pageX;
        this.endY = e.changedTouches[0].pageY;
      }
      //获取滑动距离
      let distanceX = this.endX - this.startX;
      let distanceY = this.endY - this.startY;
      //判断滑动方向——向右滑动
      distanceX = parseInt(distanceX * 0.8);
      if (distanceX > 0 && this.tagBodyLeft < boundarystart) {
        this.tagBodyLeft = this.tagBodyLeft + distanceX;
        //判断滑动方向——向左滑动
      } else if (distanceX < 0 && this.tagBodyLeft >= -boundaryend) {
        this.tagBodyLeft = this.tagBodyLeft + distanceX;
      }
    },
    hadelMousewheel(e) {
      const step = 100; //一个tag长度
      const boundarystart = 0,
        boundaryend = this.$refs.tagsList.offsetWidth - this.$refs.tagBox.offsetWidth;
      // Y>0向左滑动
      if (e.deltaY > 0 && this.tagBodyLeft > -boundaryend) {
        if(this.tagBodyLeft+boundaryend<100){
          this.tagBodyLeft = -boundaryend
        }else{
          this.tagBodyLeft = this.tagBodyLeft-step;
        }
        // Y<0向右滑动
      } else if (e.deltaY < 0 && this.tagBodyLeft < boundarystart) {
        if(this.tagBodyLeft<0&&(this.tagBodyLeft + step)>0){
          this.tagBodyLeft = 0
        }else{
          this.tagBodyLeft = this.tagBodyLeft + step;
        }
      }
    },
    openMenu(tag, e) {
      e.preventDefault()
      if (this.tagList.length == 1) {
        return;
      }

      this.visible = true;
      this.selectedTag = tag;
      this.left = e.clientX;
      this.top = e.clientY;
    },
    closeOthersTags() {
      this.openUrl(this.selectedTag)
      this.$store.commit("DEL_TAG_OTHER");
    },
    closeMenu() {
      this.visible = false;
    },
    closeAllTags() {
      this.$store.commit("DEL_ALL_TAG");
      this.$router.push({
        path: resolveUrlPath(this.tagWel.value),
        query: this.tagWel.query
      });
    },
    moveToView(tag) {
      this.isShow = this.$refs.tagsList.offsetWidth - this.$refs.tagBox.offsetWidth>0
      if (tag.offsetLeft < -this.tagBodyLeft) {
        // 标签在可视区域左侧
        this.tagBodyLeft = -tag.offsetLeft;
      } else if (
        tag.offsetLeft + 10> -this.tagBodyLeft &&
        tag.offsetLeft + tag.offsetWidth <
          -this.tagBodyLeft + this.$refs.tagBox.offsetWidth
      ) {
        // 标签在可视区域
      } else {
        // 标签在可视区域右侧
        this.tagBodyLeft = -(
          tag.offsetLeft -
          (this.$refs.tagBox.offsetWidth - 0 - tag.offsetWidth)
        );
      }

      /*this.$nextTick(()=>{
        this.isShow = this.$refs.tagsList.offsetWidth - this.$refs.tagBox.offsetWidth>0
      })*/
    },
    openUrl(item) {
      this.$router.push({
        path: resolveUrlPath(item.value, item.label),
        query: item.query
      });
    },
    eachTag(tag) {
      for (var key in this.tagList) {
        if (this.tagList[key].value == tag.value) {
          return key;
        }
      }
      return -1;
    },
    closeTag(item) {
      const key = this.eachTag(item);
      let tag;
      this.$store.commit("DEL_TAG", item);
      if (item.value == this.tag.value) {
        tag = this.tagList[key == 0 ? key : key - 1];
        this.openUrl(tag);
      }
      if(this.tagBodyLeft<-100){
        this.tagBodyLeft = this.tagBodyLeft+100;
      }else{
        this.tagBodyLeft = 0
      }
      this.isShow = this.$refs.tagsList.offsetWidth - this.$refs.tagBox.offsetWidth>0
    },
    refreshTag(){
      this.reLoad()
    },
    goPrevious(){
      let nowIndex = this.tagList.findIndex(item=>item.value === this.nowTagValue)
      let previousIndex = nowIndex>=0?nowIndex-1:0
      let previous = this.tagList[previousIndex]
      this.$router.push({
        path: resolveUrlPath(previous.value, previous.label),
        query: previous.query
      });
    },
    goNext(){
      let nowIndex = this.tagList.findIndex(item=>item.value === this.nowTagValue)
      let nextIndex = nowIndex<this.tagList.length-1?nowIndex+1:(this.tagList.length-1)
      let next = this.tagList[nextIndex]
      this.$router.push({
        path: resolveUrlPath(next.value, next.label),
        query: next.query
      });
    },
    goFirst(){
      let first = this.tagList[0]
      this.$router.push({
        path: resolveUrlPath(first.value, first.label),
        query: first.query
      });
    },
    goLast(){
      let last = this.tagList[this.tagList.length-1]
      this.$router.push({
        path: resolveUrlPath(last.value, last.label),
        query: last.query
      });
    },
    moveForward(){
      let boundarystart = 0
      if (this.tagBodyLeft < boundarystart) {
        if(this.tagBodyLeft<0&&(this.tagBodyLeft + 100)>0){
          this.tagBodyLeft = 0
        }else{
          this.tagBodyLeft = this.tagBodyLeft + 100;
        }
      }
    },
    backwardShift(){
      let boundaryend = this.$refs.tagsList.offsetWidth - this.$refs.tagBox.offsetWidth;
      if (this.tagBodyLeft > -boundaryend) {
        if(this.tagBodyLeft+boundaryend<100){
          this.tagBodyLeft = -boundaryend
        }else{
          this.tagBodyLeft = this.tagBodyLeft-100;
        }
      }
    }
  }
};
</script>
<style lang="scss" scoped>
    .contextmenu {
      margin: 0;
      background: #fff;
      z-index: 999999;
      position: fixed;
      list-style-type: none;
      padding: 5px 0;
      border-radius: 4px;
      font-size: 12px;
      font-weight: 400;
      color: #333;
      box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, .3);
      li {
        margin: 0 16px;
        padding: 0;
        height: 25px;
        line-height: 25px;
        cursor: pointer;
        &:hover {
          background: #eee;
          color: #3a8ee6;
        }
      }
      li:not(:last-child){
        border-bottom: 1px solid #ebebed;
      }
    }
  .tags_button{
    background-color: #edf2f5;
    padding-top: 10px;
    .el-button{
      padding:5px 5px;
      margin-left: 2px;
    }
  }
  .tags-container{
    height: 40px;
    display: flex;
    justify-content: space-between;
    .tag_left{
      width: 16px;
      height: 100%;
      z-index: 1000;
      margin-top: 9px;
      .el-button{
        height: calc(100% - 9px);
        width: 100%;
        padding:7px 1px;
      }
    }
    .tag_center{
      height: 100%;
      width: calc(100% - 32px);
      z-index: 999;
    }
    .tag_right{
      width: 16px;
      height: 100%;
      z-index: 1000;
      margin-top: 9px;
      .el-button{
        height: calc(100% - 9px);
        width: 100%;
        padding:7px 1px;
      }
    }
    .isWhole{
      width: 100%;
    }
  }
</style>


