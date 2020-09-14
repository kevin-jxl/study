<template>
  <div class="main_div">
    <div style="height: 100%">
      <el-tabs v-model="activeName" class="detail_tabs">
        <el-tab-pane label="租赁信息" name="first">
          <div class="tab_detail">
            <el-container style="height: 100%">
              <el-main style="height: 100%">
                <el-form ref="form" :model="form" label-width="100px">
                  <el-row>
                    <span class="titleSpan">基本信息</span>
                    <el-form-item label="" prop="type"  label-width="40px">
                      <el-radio-group v-model="form.type">
                        <el-radio label="0">商管类</el-radio>
                        <el-radio label="1">产业</el-radio>
                        <el-radio label="2">平台服务商</el-radio>
                      </el-radio-group>
                    </el-form-item>
                  </el-row>
                  <el-row>
                    <el-col :span="7">
                      <el-form-item label="商户/客户名称">
                        <el-input v-model="form.name" size="small"></el-input>
                      </el-form-item></el-col>
                    <el-col :span="7">
                      <el-form-item label="序号">
                        <el-input v-model="form.room" size="small"></el-input>
                      </el-form-item></el-col>
                    <el-col :span="7">
                      <el-form-item label="法定代表人">
                        <el-input v-model="form.legalPerson" size="small"></el-input>
                      </el-form-item></el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="7">
                      <el-form-item label="合同主人">
                        <el-input v-model="form.contractOwn" size="small"></el-input>
                      </el-form-item></el-col>
                    <el-col :span="7">
                      <el-form-item label="身份证号">
                        <el-input v-model="form.idCard" size="small"></el-input>
                      </el-form-item></el-col>
                    <el-col :span="7">
                      <el-form-item label="法人身份证号">
                        <el-input v-model="form.ownIdCard" size="small"></el-input>
                      </el-form-item></el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="7">
                      <el-form-item label="甲方">
                        <el-select v-model="form.firstParty" placeholder="请选择活动区域"
                                   size="small" style="width: 190px !important;"
                                   filterable clearable
                        >
                          <el-option label="区域一" value="shanghai"></el-option>
                          <el-option label="区域二" value="beijing"></el-option>
                        </el-select>
                      </el-form-item></el-col>
                    <el-col :span="7">
                      <el-form-item label="品牌">
                        <el-input v-model="form.brand" size="small"></el-input>
                      </el-form-item></el-col>
                    <el-col :span="7">
                      <el-form-item label="经办人邮箱">
                        <el-input v-model="form.chargeEmail" size="small"></el-input>
                      </el-form-item></el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="21">
                      <el-form-item label="性质">
                        <el-checkbox-group v-model="form.brandStyle" size="small">
                          <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
                          <el-checkbox label="地推活动" name="type"></el-checkbox>
                          <el-checkbox label="线下主题活动" name="type"></el-checkbox>
                          <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
                        </el-checkbox-group>
                      </el-form-item></el-col>
                  </el-row>
                  <el-row style="margin-top: 10px;">
                    <span class="titleSpan">租赁信息</span>
                  </el-row>
                  <el-row>
                    <el-col :span="7">
                      <el-form-item label="面积">
                        <el-input v-model="form.acreage" size="small" style="width: 190px !important;">
                          <template slot="suffix">㎡</template>
                        </el-input>
                      </el-form-item></el-col>
                    <el-col :span="7">
                      <el-form-item label="面积">
                        <el-input v-model="form.acreage2" size="small" style="width: 190px !important;">
                          <template slot="suffix">㎡</template>
                        </el-input>
                      </el-form-item></el-col>
                    <el-col :span="7">
                      <el-form-item label="对赌">
                        <el-input v-model="form.betOn" size="small"></el-input>
                      </el-form-item></el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="7">
                      <el-form-item label="租期">
                        <el-input v-model="form.leaseTearm" size="small" style="width: 190px !important;">
                          <template slot="suffix">年</template>
                        </el-input>
                      </el-form-item></el-col>
                    <el-col :span="7">
                      <el-form-item label="开业条件">
                        <el-radio-group v-model="form.openStatus">
                          <el-radio label="0">是</el-radio>
                          <el-radio label="1">否</el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-col>
                    <el-col :span="7">
                      <el-form-item label="开业时间">
                        <el-date-picker
                          v-model="form.openTime"
                          style="width: 190px !important;"
                          type="date"
                          size="small"
                          format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd"
                          placeholder="选择日期">
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-form-item label="上传附件">
                      <el-upload
                        class="upload-demo"
                        ref="upload"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :file-list="fileList"
                        :auto-upload="false">
                        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                      </el-upload>
                    </el-form-item>
                  </el-row>
                  <el-row style="margin-top: 30px;">
                    <el-col :span="6" :offset="9">
                      <el-button style="margin-left: 10px;" size="small" @click="submitUpload">暂存</el-button>
                      <el-button style="margin-left: 10px;" size="small" type="primary" @click="submitUpload">提交</el-button>
                    </el-col>
                  </el-row>
                </el-form>
              </el-main>
            </el-container>
          </div>
        </el-tab-pane>
        <el-tab-pane label="租金标准" name="second">
          <div style="height: 100%">
            <plan-table :data="planList" :columns="columns" :expandAll="true" border size="medium"></plan-table>
          </div>
        </el-tab-pane>
        <el-tab-pane label="发票设置" name="third">
          <div style="height: 100%">
            <el-button @click="transferVisible = true">弹窗</el-button>
            <category-select
              :category="menu"
              :dialog-visible="transferVisible"
              :transfer-items="transferItems"
              :transfer-loading="transferLoading"
              dialogName="类型选择"
              :transferTitle="['A','B']"
              @transfer-cancel="transferCancel"
              @transfer-tree-search="transferTreeSearch"
              @transfer-submit="transferSubmit"
            ></category-select>
          </div>
        </el-tab-pane>
        <el-tab-pane label="开户行设置" name="fourth">
          <vue-u-editor-wrap v-model="msg" :config="myConfig"></vue-u-editor-wrap>
        </el-tab-pane>
        <el-tab-pane label="图片设置" name="fifth">
          <div>
            <img src="http://img1.xcarimg.com/album/2144/4498/m_20120214171512830487.jpg" style="height: 150px" usemap="#planetmap"/>
            <img src="../../assets/img/bg/bg2.jpg" style="height: 150px"/>
            <img src="/static/img/bg/bg1.jpg" style="height: 150px"/>
            <img :src="imgUrl" style="height: 150px"/>
            <img :src="imgUrl1" style="height: 150px"/>
            <img :src="imgUrl2" style="height: 150px"/>
            <img :src="imgUrl3" style="height: 150px"/>
            <map name="planetmap" id="planetmap">
              <area shape="rect" coords="0,0,10,10" alt="Venus" />
              <area shape="poly" coords="30,30,50,50,10,50" alt="Sun" @click="areaClick"/>
            </map>
          </div>
          <div class="img_carousel">
            <el-carousel>
              <el-carousel-item v-for="item in 4" :key="item">
                <img :src="imgUrl"/>
              </el-carousel-item>
            </el-carousel>
          </div>
          <div class="img_carousel_max">
            <el-carousel>
              <el-carousel-item v-for="item in 4" :key="item">
                <img :src="imgUrl"/>
              </el-carousel-item>
            </el-carousel>
          </div>
        </el-tab-pane>
        <el-tab-pane label="付款详情" name="sixth">付款详情</el-tab-pane>
      </el-tabs>
      <el-button class="return_button" size="small" @click="detailBack">返回</el-button>
    </div>
    <el-backtop target=".page-component__scroll .el-scrollbar__wrap"></el-backtop>
  </div>
</template>
<script>
  import PlanTable from './components/planTable'
  import CategorySelect from '../../components/common/categorySelect'
  import VueUEditorWrap from 'vue-ueditor-wrap'
  import {mapGetters} from "vuex";
  export default {
    name:'detail',
    data(){
      return {
        activeName: 'first',
        form: {
          type:'1',
          name: '孟宏',
          room:'25棟-甲-100（租）',
          legalPerson:'张三',
          openTime:'20190102',
          contractOwn:'李斌',
          idCard:'12312312312312312312',
          ownIdCard:'123456122345674562',
          firstParty:'shanghai',
          brand:'海尔家居',
          chargeEmail:'123123123@163.com',
          acreage:'126',
          acreage2:'106',
          betOn:'',
          leaseTearm:'2',
          openStatus:'1',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          resource: '',
          desc: '',
          brandStyle:[]
        },
        fileList: [
          {
            name: 'food.jpeg',
            url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'},
          {
            name: 'food2.jpeg',
            url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
          }
        ],
        planList:[
          {
            id:'0',
            name:'规范与框整理123构建',
            planTime:'14.5',
            actualTime:'4',
            planStartTime:'2018-01-01',
            planEndTime:'2019-01-01',
            actualStartTime:'2018-03-03',
            actualEndTime:'2019-03-09',
            preTask:'建设社会主义',
            personName:'试试',
            schedule:'78',
            document:'0',
            children:[
              {
                id:'1',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',
                parentId:'0',
                isEdit:false
              },
              {
                id:'2',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',parentId:'0',
                isEdit:false
              },
              {
                id:'3',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',parentId:'0',
                isEdit:false
              },
              {
                id:'4',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',parentId:'0',
                isEdit:false
              },
              {
                id:'5',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',parentId:'0',
                isEdit:false
              },
              {
                id:'6',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',parentId:'0',
                isEdit:false
              },
            ],
            parentId:'-1',
            isEdit:false
          },
          {
            id:'10',
            name:'规范与框整理构建',
            planTime:'14.5',
            actualTime:'4',
            planStartTime:'2018-01-01',
            planEndTime:'2019-01-01',
            actualStartTime:'2018-03-03',
            actualEndTime:'2019-03-09',
            preTask:'建设社会主义',
            personName:'试试',
            schedule:'78',
            document:'0',
            children:[
              {
                id:'11',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',
                parentId:'10',
                isEdit:false
              },
              {
                id:'12',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',
                parentId:'10',
                isEdit:false
              },
              {
                id:'13',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',
                parentId:'10',
                isEdit:false
              },
              {
                id:'14',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',
                parentId:'10',
                isEdit:false
              },
              {
                id:'15',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',
                parentId:'10',
                isEdit:false
              },
              {
                id:'16',
                name:'规范与框整理构建',
                planTime:'14.5',
                actualTime:'4',
                planStartTime:'2018-01-01',
                planEndTime:'2019-01-01',
                actualStartTime:'2018-03-03',
                actualEndTime:'2019-03-09',
                preTask:'建设社会主义',
                personName:'试试',
                schedule:'78',
                document:'1',
                parentId:'10',
                isEdit:false
              },
            ],
            parentId:'-1',
            isEdit:false
          },
        ],
        columns:[
          {value:'name',text:'任务',width:20},
          {value:'planTime',text:'工期',width:10},
          {value:'actualTime',text:'实际工时',width:10},
          {value:'planStartTime',text:'计划开始',width:10},
          {value:'planEndTime',text:'计划结束',width:10},
          {value:'actualStartTime',text:'实际开始',width:10},
          {value:'actualEndTime',text:'实际结束',width:10},
          {value:'preTask',text:'前置任务',width:10},
          {value:'personName',text:'责任人',width:10,editable:true,type:'select'},
          {value:'schedule',text:'进度',width:10,editable:true,type:'input'},
        ],
        imgUrl:require('../../assets/img/bg/bg2.jpg'),
        imgUrl1:'../../assets/img/bg/bg3.jpg',
        imgUrl2:'/static/img/bg/bg2.jpg',
        imgUrl3:'http://img1.xcarimg.com/album/2144/4498/m_20120214171512830487.jpg',
        msg:"<h1 label=\"标题居中\" style=\"font-size: 32px; font-weight: bold; border-bottom: 2px solid rgb(204, 204, 204); padding: 0px 4px 0px 0px; text-align: center; margin: 0px 0px 20px;\">heshide</h1><ol class=\"custom_cn list-paddingleft-1\"><li class=\"list-cn-1-1 list-cn-paddingleft-1\"><p>一、开始</p></li><li class=\"list-cn-1-2 list-cn-paddingleft-1\"><p>你好啊<br/></p></li><li class=\"list-cn-1-3 list-cn-paddingleft-1\"><p>二、计划</p></li></ol>",
        myConfig:{
          // 编辑器不自动被内容撑高
          autoHeightEnabled: false,
          // 初始容器高度
          initialFrameHeight: 340,
          // 初始容器宽度
          initialFrameWidth: '100%',
          serverUrl: 'http://35.201.165.105:8000/controller.php',
          UEDITOR_HOME_URL: '/static/cdn/UEditor/'
        },
        transferVisible:false,
        transferLoading:false,
        transferItems:[
          {
            id:0,
            label:'北京'
          },
          {
            id:1,
            label:'上海'
          },
          {
            id:2,
            label:'广州'
          },
          {
            id:3,
            label:'深圳'
          },
          {
            id:4,
            label:'杭州'
          },
          {
            id:5,
            label:'重庆'
          },
          {
            id:6,
            label:'成都'
          },
          {
            id:7,
            label:'西安'
          },
          {
            id:8,
            label:'郑州'
          },
          {
            id:9,
            label:'沈阳'
          },
        ]
      }
    },
    components:{PlanTable,VueUEditorWrap,CategorySelect},
    computed:{
      ...mapGetters(['menu'])
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      },
      submitUpload() {
        this.$refs['form'].submit();
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      detailBack(){
        this.$router.go(-1)
      },
      transferCancel(){
        this.transferVisible = false
      },
      transferTreeSearch(id){
        this.transferLoading = true

        setTimeout(()=>{
          this.transferLoading = false
          if(id===1){
            this.transferItems = [
              {
                id:0,
                label:'北京'
              },
              {
                id:1,
                label:'上海'
              },
              {
                id:2,
                label:'广州'
              },
              {
                id:3,
                label:'深圳'
              },
              {
                id:4,
                label:'杭州'
              },
              {
                id:5,
                label:'重庆'
              },
              {
                id:6,
                label:'成都'
              },
              {
                id:7,
                label:'西安'
              },
              {
                id:8,
                label:'郑州'
              },
              {
                id:9,
                label:'沈阳'
              },
            ]
          }else{
            this.transferItems = [
              {
                id:10,
                label:'济南'
              },
              {
                id:11,
                label:' 青岛'
              },
              {
                id:12,
                label:'潍坊'
              },
            ]
          }
        },1000)
      },
      transferSubmit(items){
        this.transferVisible = false
        console.info(items)
      },
      areaClick(event){
        console.info(event)
      }
    },
    mounted(){
      this.showMainLoading()

      setTimeout(()=>{
        this.hideMainLoading()
      },2000)
    }
  }
</script>
<style lang="scss" scoped>
  .main_div{
    padding:10px 30px;
  }
  .detail_tabs{
    height: 100%;
    /deep/ .el-tabs__content{
      height: calc(100% - 60px) ;
    }
    /deep/ .el-tabs__item{
      font-size: 12px;
    }
  }
  .el-tab-pane{
    height: 100%;
  }
  .titleColorSpan{
    height: 16px;
    width: 5px;
    background-color: #3a8ee6;
    display: block;
    float: left;
    text-align: center;
    margin: 12px 0;
  }
  @include preTitle(#3a8ee6,#3a8ee6,12px,40px)
  .tab_detail{
    height: 100%;
    /*padding:20px;*/
    .el_row{
      padding: 0 !important;
      line-height: 40px;
    }
    .el_col{
      padding: 0 !important;
      line-height: 40px;
    }
    .el-form-item {
      margin-bottom: 0 !important;
      float: left;
    }
    .el-main{
      padding-top: 0!important;
      padding-bottom: 0!important;
    }
  }
  .el-input--suffix .el-input__inner{
    padding-right: 15px !important;
  }
  .return_button{
    position: absolute;
    top: 10px;
    right:35px;
  }

  .upload-demo /deep/ .el-upload-list__item{
    margin-top: 0 ;
    font-size: 12px ;
    line-height: 1.5 ;
    float: left;
    width: auto;
    a{
      margin-right: 20px;
    }
  }

  .img_carousel{
    height: 300px;
    width: 500px;
    margin-right: 10px;
    display: inline-block;
    /deep/ .el-carousel{
      height: 100%;
      .el-carousel__container{
        height: 100%;
        img{
          height: 100%;
          width: 100%;
          object-fit: cover;
          object-position: 50% 50%;
        }
      }
    }
  }
  .img_carousel_max{
    height: 400px;
    width: 600px;
    display: inline-block;
    /deep/ .el-carousel{
      height: 100%;
      .el-carousel__container{
        height: 100%;
        img{
          height: 100%;
          width: 100%;
          object-fit: cover;
          object-position: 50% 50%;
        }
      }
    }
  }
</style>
