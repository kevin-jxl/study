<template>
    <div class="app-container calendar-list-container">
        <div class="person-left">
            <div class="header-seach">
                <el-input style="width: 80%" placeholder="请输入员工姓名/编号" v-model="keyword" class="input-with-select" size="mini"></el-input>
                <el-button style="margin-left: 10px;" class="el-icon-search" @click="handleBtnClick" size="mini"></el-button>
				<el-button style="margin-left: 10px;" class="el-icon-refresh-right" @click="refresh" size="mini"></el-button>
            </div>
            <div class="form_search clearfix input-select">
                <el-form class="search-form-inline" :inline="true" ref="form" label-width="80px">
					<el-row :gutter="20">
						<el-col :span="12">
							<el-form-item label="" size="medium">
							    <el-select v-model="companyId" placeholder="请选择公司" :clearable="true">
									<el-option v-for="(item,index) in syscompanyList" :key="index" :label="item.name" :value="item.com_id"></el-option>
							    </el-select>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label=""  size="medium">
							    <el-select v-model="parkId" placeholder="请选择停车场" :clearable="true">
							    <el-option v-for="(item,index) in parkList" :key="index" :label="item.name" :value="item.organ_id"></el-option>
							    </el-select>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label=""  size="medium">
							    <el-select v-model="serviceId" placeholder="请选择职位" :clearable="true">
							    <el-option v-for="(item,index) in organList" :key="index" :label="item.service_name" :value="item.service_id"></el-option>
							    </el-select>
							</el-form-item>
						</el-col>
					</el-row>
                </el-form>
            </div>
            <div style="text-align: left; margin-left:20px; height: 30px; line-height: 30px;">
                <span style="font-weight: bold;">人员列表</span>
                <span>{{logged}}</span>
            </div>
            <div class="scroll-view">
                <div v-if="personnelList.length" class="person-view">
                    <div class="person-item" v-for="(item) of personnelList " :key="item.id">
                        <div  class="person-item-view" @click="addPine(item)">
							<div :class="clickItem==item.id? 'cricel-border':''">
								<el-avatar v-if="item.isDuty == 1" :size="60" :src="getAvatar(item.avatar)"></el-avatar>
								<el-avatar v-else :size="60" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"></el-avatar>
							</div>
                            <div style="margin-top:6px">{{item.name}}</div>
                        </div>
						
						
                    </div>
                </div>
				<div v-else class="no-data">
					<div class="text">暂无数据</div>
				</div>
            </div>
        </div>
         <div class="person-right">
             <div id="amap-main"></div>
        </div>
    </div>
</template>

<script>
import { lazyAMapApiLoaderInstance } from 'vue-amap';
let self;
    export default {
        data() {
            return {
                state:'',
                map:'',
                opened: true,
				keyword: '',
                mapName:'张三',
				syscompanyList:[],
                markerList: [],
				companyId: '',
				parkList: [],
				parkId: '',
				organList: [],
				personnelList: [],
				clickItem: null,
				serviceId: '',
				marker: '',
				infoWindow: ''
            }
        },
		inject:['reload'],
		computed:{
			logged() {
				if(this.personnelList.length==0) return '';
				
				let loggedList = this.personnelList.filter(e => {
					return e.isDuty == '1';
				})
				return `( ${loggedList.length}/${this.personnelList.length} )`;
			},
			getAvatar() {
			    return function(avatar){
					if(avatar) {
						return avatar;
					} else {
						// return 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
						return 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3363295869,2467511306&fm=26&gp=0.jpg';
					}
			    }           
			} 
		},
		watch: {
			companyId(nVal,oVal) {
				this.parkId = '';
				this.serviceId = '';
				this.getPark(nVal)
				this.getPersonnel();
			},
			parkId(nVal,oVal) {
				this.serviceId = '';
				this.getOrgan(nVal)
				this.getPersonnel();
			},
			serviceId(nVal,oVal) {
				this.getPersonnel();
			}
		},
        mounted() {
			let user = JSON.parse(sessionStorage.getItem('userData'))
			console.log("user:"+JSON.stringify(user));
            let self = this;
			self.getSyscompany(user.sysUser.companyId);	
			self.getPersonnel();
             
            lazyAMapApiLoaderInstance.load().then(() => {
                self.map = new AMap.Map('amap-main', 
                {
                    zoom:13,
                    center: new AMap.LngLat("116.368904","39.913423")
                });
                // 同时引入工具条插件，比例尺插件和鹰眼插件
                AMap.plugin([
                    'AMap.ToolBar',
                    'AMap.Scale',
                    'AMap.OverView',
                    'AMap.MapType',
                    'AMap.Geolocation',
                ], function(){});
               
            });
        },
        methods: {
			
			//人员数据
			getPersonnel() {
				let params = {
					name:this.keyword,
					comId:this.companyId,
					parkId:this.parkId,
					serviceId: this.serviceId
				}
				this.$api.getPersonnel(params).then(res => {
					console.log('人员数据：'+JSON.stringify(res));
					this.personnelList = res.data.records;
				})
			},
			// 公司数据
			getSyscompany (tenantId) {
				this.$api.getSyscompany(tenantId).then(res => {
					console.log('公司数据：'+JSON.stringify(res))
					this.syscompanyList = res.data;
				})
			},
			getPark(comId) {
				console.log("comId："+comId);
				this.$api.getPark(comId).then(res => {
					console.log('车场数据：'+JSON.stringify(res))
					this.parkList = res.data;
				})
			},
			getOrgan(organId) {
				console.log("organId："+organId);
				this.$api.getOrgan(organId).then( res => {
					console.log('机构数据：'+JSON.stringify(res))
					this.organList = res.data;
				})
			},
            handleBtnClick() {
				this.keyword = this.keyword.trim();
				this.keyword = this.keyword.replace(
					/\\|\/|\?|\？|\*|\"|\“|\”|\'|\‘|\’|\<|\>|\{|\}|\[|\]|\【|\】|\：|\:|\、|\^|\$|\!|\~|\`|\|/g, "");
				this.getPersonnel();
            },
			refresh(){ //刷新
			    this.reload()
			},
           handleIconClick(ev) {
                console.log(ev);
            },
            handleSelect(item) {
                console.log(item);
                this.state = item.value
            },
           

            addPine(item) {
				if(item.isDuty!=1) {
					return this.$message.error(`用户 ${item.name} 未登录！`)
				}
				if(this.clickItem == item.id) return;
				let self = this;
				this.clickItem = item.id;
				if(self.marker) {
					self.map.remove(self.marker);
				}
				if(self.infoWindow) {
					self.infoWindow.close();
				}
				let lng = item.lng;
				if(!lng) {
					lng = '116.39';
				}
				let lat = item.lat;
				if(!lat) {
					lat = '39.85';
				}
                // 创建一个 Marker 实例：
                self.marker = new AMap.Marker({
                    position: new AMap.LngLat(lng,lat),
                    offset: new AMap.Pixel(-10, -10),
                    title: '北京'
                });
				self.map.setCenter([lng,lat]);
                self.map.add([self.marker]);
                var content = `<div style='display: flex; flex-direction: row; padding:10px 20px'>
                            <div style='font-size：10px; line-height: 30px'>
                                <div>姓名: ${item.name}</div> 
                                <div>编号: ${item.code}</div>  
                                <div>所属车场: ${item.parkingLot}</div>  
								<div>联系电话: ${item.tel}</div>   
                                <div>登录时间: ${item.loginTime}</div>   
                                <div>位置更新: ${item.lastLoginDate}</div>   
                            </div>
                    </div>`
                

                // 创建 infoWindow 实例	
                self.infoWindow = new AMap.InfoWindow({
                        anchor: 'top-center',
                        content: content,
                });
				
                self.marker.on('click',  function(e) {
                    // alert('您在[ '+e.lnglat.getLng()+','+e.lnglat.getLat()+' ]的位置点击了地图！');
                    self.infoWindow.open(self.map,[e.lnglat.getLng(),e.lnglat.getLat()]);
                })
               
            },
        }
    }
</script>

<style>
	
</style>

<style lang="scss" scoped>
	// img{
	// 	-webkit-filter: grayscale(1) !important; /* Webkit */
	// 	filter: gray !important; /* IE6-9 */  
	// 	filter: grayscale(1) !important; /* W3C */
	// }
.el-form-item {
	margin-bottom: 10px !important;
}
.app-container {
    height: 100%;
	box-sizing: border-box;
	padding: 10px;
	background: #fff;
}
.calendar-list-container{
    display: flex;
    flex-direction: row;
    .person-left{
        // border-right: 1px solid #d9d9d9;
        width: 400px;
        height: calc(100% - 10px);
        padding-right: 10px;
        .header-seach {
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-item: center;
        }
        .input-select {
            display: flex;
            flex-direction: row;
            margin-top: 10px;
            padding: 10px 18px;
            border-top:  1px solid #d9d9d9;
            .auto-div {
                margin-left: 10px;
            }
        }
    }
    .scroll-view {
        // padding: 10px;
        overflow-y:auto; 
        overflow-x:hidden;
        width:100%; 
        height:584px;
		
    }
	.no-data {
		height: 100%;
		color: #999;
		position: relative;
		.text {
			position: absolute;
			top: 50%;
			left:50%;
			transform: translateX(-50%) translateY(-50%);
		}
	}
    .person-view {
            display: flex;
            flex-flow: row wrap;
            justify-content: flex-start;
            
            .person-item {
                flex: 0 1 25%;
                // height: 100px;
                // background-color: #000fff;
                margin-top: 5px;
                .person-item-view {
                    padding: 10px;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                }
            }
        }
    .person-right{
        width: calc(100% - 400px);
        // padding-left: 10px;
        #amap-main{
            height: 100%;
            width: 100%;
        }
    }
	.cricel-border {
		height: 60px;
		width: 60px;
		border-radius: 50%;
		border: 2px solid #5961E8;
	}
	.el-button--mini, .el-button--mini.is-round {
	    padding: 7px !important;
	}
}
</style>