<template>
    <div class="fix-width dispatch-pop">
		<div class="pop-left pop-scroll-view">
			<div class="dialog-title">
			    派遣
			</div>
			
				<el-form :inline="true" :model="ruleForm" class="clearfix" ref="ruleForm" :rules="rules">
					<el-row :gutter="24">
							<el-col :span="12">
								<el-form-item label="选择队伍：" prop="team" >
									<el-select v-model="serviceId"  placeholder="请选择队伍">
									 <el-option v-for="(item,index) in organList" :key="index" :label="item.service_name" :value="item.service_id"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label=" 选择队员：" prop="player">
									<el-select v-model="ruleForm.player" placeholder="请选择队员" @change="changePersonnel">
									  <el-option v-for="(item,index) in personnelList" :key="index" :label="item.name" :value="item.id"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="周边查找：" prop="find" >
									<div style="display:inline-block;">
										<el-input style="width:125px" v-model="ruleForm.find" oninput="value=value.replace(/[^\d]/g,'')" :maxLength="9" type="tel" placeholder="周边查找"  autocomplete="off" >
										  <div slot="append">米</div>
										</el-input>
										<div style="margin-left:10px; color: #3f5195;" class="el-icon-search" @click="searchAround">开始搜索</div>
									</div>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="员工姓名：" prop="name">
									<el-input style="max-width: 195px;" v-model="ruleForm.name" placeholder="请输入员工姓名"  autocomplete="off" :maxlength="20"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="员工编号：" prop="code">
									<el-input v-model="ruleForm.code"  placeholder="请输入员工编号" autocomplete="off" :maxlength="20"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item  label="联系电话：" prop="phone">
									<el-input style="max-width: 195px;" v-model="ruleForm.phone"  placeholder="请输入联系电话" autocomplete="off" :maxlength="11"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="24">
								
								<el-form-item label="处置时限：" prop="timeLin" >
									<div style="display: flex; flex-direction: row; align-items: center; width: 100%;">
										<el-select v-model="ruleForm.timeLin"  placeholder="请选择处置时限" style="width:150px">
											<el-option label="1小时" value="1"></el-option>
											<el-option label="2小时" value="2"></el-option>
										</el-select>
										<el-switch v-model="ruleForm.delivery" active-text="1小时" style="margin-left:10px"></el-switch>
									</div>
									
								</el-form-item>
							</el-col>
							<el-col :span="24">
								
								<el-form-item label="任务补充：" prop="describe" style="width:100%; text-align:left;">
									<el-input v-model="ruleForm.describe" style="width: 540px;" type="textarea"  autocomplete="off" :maxlength="200" :rows="4"   placeholder="字数不超过200字"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
					
					<el-form-item size="small" style="width:97.5%; text-align:right;">
						<el-button type="primary" @click="submitForm('ruleForm')" size="mini">派遣</el-button>
						<el-button @click="cancel('ruleForm')" size="mini">取消</el-button>
					</el-form-item>
				</el-form>
			
		</div>
		<div class="pop-right">
			<div id="amap-pop"></div> 
		</div>
	</div>
</template>

<script>
	import { lazyAMapApiLoaderInstance } from 'vue-amap';
    export default {
      props: {
        workOrderId: {
          type: String,
          default:''
        }
      },
    data() {
      const checkMobile = (rule, value, cb) => {
				const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
				if (regMobile.test(value)) {
					// 合法的手机号码
					return cb()
				}
				cb(new Error('手机号码格式不正确'))
			};
			const checkCode = (rule, value, cb) => {
				const regCode = /^[0-9a-zA-Z]+$/;
				if (regCode.test(value)) {
					return cb()
				}
				cb(new Error('仅支持字母和数字输入'))
			};
			const checkName = (rule, value, cb) => {
				const regName = /^[\u4E00-\u9FA5]{0,}$/;
				if (regName.test(value)) {
					return cb()
				}
				cb(new Error('仅支持中文汉字输入'))
			};
      return {
		locationData:{},
		sysUser: {},
		organList: [],  
		serviceId: '',
		personnelList: [],
		selectedPersonnel: {},
        ruleForm: {
		  id: '',
          find: '',
          team: '',
          player: '',
          name: '',
          code: '',
          phone: '',
          timeLin: '',
          delivery: true,
          describe: ''
        },
        rules: {
          name: [
            { required: true, message: "员工姓名不能为空", trigger: "blur"},
			{ min: 1, max: 20, message: "长度在 1 到 20 个字符",trigger: "blur"},
			{ validator: checkName, trigger: 'blur'}
          ],
          // team: [
          //   { required: true, message: '请选择队伍', trigger: 'change' }
          // ],
          // player: [
          //   {  required: true, message: '请选择队员', trigger: 'change' }
          // ],
   //        code: [{
   //              required: true,
   //              message: "员工编号不能为空",
   //              trigger: "blur"
   //            },
   //            {
   //              min: 1,
   //              max: 20,
   //              message: "长度在 1 到 20 个字符",
   //              trigger: "blur"
   //            },
   //            {
   //              validator: checkCode,
   //              trigger: 'blur'
   //            }
			// ],
          phone: [{
                required: true,
                message: "手机号不能为空",
                trigger: "blur"
              },
              {
                validator: checkMobile,
                trigger: 'blur'
              }
          ],
        },
		map: '',
		markerList: [],
		circle: '',
		infoWindow: '',
      };
    },
	watch: {
		serviceId(nVal,oVal) {
			this.getPersonnel();
		}
	},
	mounted() {
		let user = JSON.parse(sessionStorage.getItem('userData'))
		console.log("user:"+JSON.stringify(user));
		if(user) {
			this.sysUser = user.sysUser;
			this.getOrgan(this.sysUser.organId);
		}
		lazyAMapApiLoaderInstance.load().then(() => {
		    this.map = new AMap.Map('amap-pop', {
				zoom:16,
				center: new AMap.LngLat("116.368904","39.913423")
			});
		});
		this.getLocation();
	},
    methods: {
		
		getLocation() {
			let self = this;
				
				 console.log('开始定位');
				// 搜索周边
				AMap.plugin('AMap.Geolocation', function() {
				var geolocation = new AMap.Geolocation({
			    // 是否使用高精度定位，默认：true
			    enableHighAccuracy: true,
			    // 设置定位超时时间，默认：无穷大
			    timeout: 5000,
			    // 定位按钮的停靠位置的偏移量，默认：Pixel(10, 20)
			    buttonOffset: new AMap.Pixel(10, 20),
			    //  定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
			    zoomToAccuracy: true,     
			    //  定位按钮的排放位置,  RB表示右下
			    buttonPosition: 'RB'
			  })
			
			  geolocation.getCurrentPosition()
			  AMap.event.addListener(geolocation, 'complete', onComplete)
			  AMap.event.addListener(geolocation, 'error', onError)
			
			  function onComplete (data) {
			    // data是具体的定位信息
				console.log('定位：'+JSON.stringify(data));
				self.locationData = {
					lng: data.position.lng.toString(),
					lat: data.position.lat.toString()
				}
				
			  }
			  function onError (err) {
			    // 定位出错
				console.log('定位出错：'+JSON.stringify(err));
			  }
			})
		},
		
		searchAround() {
			if(!this.ruleForm.find) {
				return this.$message.info('请输入范围')
			}
			this.addCircle(this.locationData);
			this.addPine(this.locationData);
      },
	  //获取队伍
	  getOrgan(organId) {
	  	console.log("organId："+organId);
	  	this.$api.getOrgan(organId).then( res => {
	  		console.log('机构数据：'+JSON.stringify(res))
	  		this.organList = res.data;
	  	})
	  },
	  getPersonnel() {
		  console.log('机构id:'+this.serviceId);
	  	let params = {
			name:'',
			comId:'',
			parkId:'',
	  		serviceId: this.serviceId
	  	}
	  	this.$api.getPersonnel(params).then(res => {
	  		console.log('人员数据：'+JSON.stringify(res));
	  		this.personnelList = res.data.records;
			this.addPine(this.locationData);
	  	})
	  },
	  changePersonnel(row) {
			console.log('选中：'+JSON.stringify(row));
			let item = this.personnelList.filter(e => {
				  return e.id == row;
			  })
			  this.ruleForm.name = item[0].name;
			  this.ruleForm.code = item[0].code;
			  this.ruleForm.phone = item[0].tel;
			  this.ruleForm.id = item[0].id;
	  },
      submitForm(formName) {
        let _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // alert('submit!');
             console.log('submit!!');
             let parmes = {
               workOrderId: _this.workOrderId,//工单id
               assingUserId: this.ruleForm.id,//用户id this.sysUser.userId
               emUserId: _this.ruleForm.id,//员工用户id this.sysUser.empno
               emName: _this.ruleForm.name,
               emNo: _this.ruleForm.code,
               contact: _this.ruleForm.phone,
               pmin: _this.ruleForm.timeLin,
               replenish: _this.ruleForm.describe
             }
             console.log(JSON.stringify(parmes))
             _this.listLoading = true;
             _this.$api.putWorkOrderAssign(parmes).then(result => {
				 console.log('派遣结果：'+JSON.stringify(result));
				 _this.$notify({
				 	title: "成功",
				 	message: "派遣成功",
				 	type: "success",
				 	duration: 2000
				 });
               _this.listLoading = false;
			   _this.$emit('dispatch-success');
               //工单派遣
               _this.cancel(formName)
             })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      cancel(formName) {
        this.$refs[formName].resetFields();
		this.serviceId="";
		if(this.circle) {
			this.map.remove(this.circle);
		}
		if(this.infoWindow) {
			this.infoWindow.close();
		}	
		if(this.markerList.length>0) {
		    this.map.remove(this.markerList);
		    this.markerList = [];
		}
        this.$emit('cancel-dispatch-pop',false);
      },
	  addCircle(params) {
	  	let self = this;
		if(this.circle) {
			self.map.remove(self.circle);
		}
		console.log('params：'+JSON.stringify(params));
		self.map.setCenter([params.lng,params.lat]);
	  	self.circle = new AMap.Circle({
	  		center: new AMap.LngLat(params.lng,params.lat),
	  	    offset: new AMap.Pixel(-10, -10),
	  	    title: '北京',
	  		radius: self.ruleForm.find,  //半径
	  		strokeColor: "#F33",  //线颜色
	  		strokeOpacity: 1,  //线透明度
	  		strokeWeight: 1,  //线粗细度
	  		fillColor: "#ee2200",  //填充颜色
	  		fillOpacity: 0.35 //填充透明度
	  	});
	      self.map.add([self.circle]);
	  },
	  addPine(params) {
	  	let self = this;
		console.log(self.infoWindow);
		
		if(self.infoWindow) {
			self.infoWindow.close();
		}	
	    if(self.markerList.length>0) {
	        self.map.remove(self.markerList);
	        self.markerList = [];
	    }
		 self.map.setCenter([params.lng,params.lat]);
		self.personnelList.forEach(item => {
			console.log('坐标：'+item.lng+','+item.lat);
			if(item.lng!=null || item.lat!=null) {
				if(self.ruleForm.find) {
					let leng = AMap.GeometryUtil.distance([params.lng,params.lat], [item.lng,item.lat])
					console.log('距离：'+leng);
					console.log('範圍：'+self.ruleForm.find);
					if( leng <= self.ruleForm.find) {
							var marker = new AMap.Marker({
							    position: new AMap.LngLat(item.lng,item.lat),
							    offset: new AMap.Pixel(-10, -10),
							    // icon: '//vdata.amap.com/icons/b18/1/2.png', // 添加 Icon 图标 URL
							    title: '北京'
							});
							self.markerList.push(marker);
					}
				} else {
					var marker = new AMap.Marker({
					    position: new AMap.LngLat(item.lng,item.lat),
					    offset: new AMap.Pixel(-10, -10),
					    // icon: '//vdata.amap.com/icons/b18/1/2.png', // 添加 Icon 图标 URL
					    title: '北京'
					});
					self.markerList.push(marker);
				}
			}
		  })
	      self.map.add(self.markerList);
			
	     
	      // 创建 infoWindow 实例	
	     
	  	for(let item = 0; item<self.markerList.length; item++) {
				
	  		self.markerList[item].on('click',  function(e) {
				let user = self.personnelList[item];
				var content = `<div style='display: flex; flex-direction: row; padding:10px 20px'>
				            <div style='font-size：10px; line-height: 25px'>
				                <div>员工姓名: ${user.name}</div> 
				                <div>员工编号: ${user.code}</div>  
				                <div>联系方式: ${user.tel}</div>  
				            </div>
				    </div>`
				self.infoWindow = new AMap.InfoWindow({
					    anchor: 'top-center',
					    content: content,
				});
	  		    self.infoWindow.open(self.map,[e.lnglat.getLng(),e.lnglat.getLat()]);
				self.ruleForm.name = user.name;
				self.ruleForm.code = user.code;
				self.ruleForm.phone = user.tel;
				self.ruleForm.id = user.id;
	  		})
	  	}
	  }
    }
  }
</script>

<style>
    .el-input-group__append, .el-input-group__prepend {
        background-color: #F5F7FA;
        color: #273D76;
        vertical-align: middle;
        display: table-cell;
        position: relative;
        border: 1px solid #DCDFE6;
        border-radius: 4px;
        padding: 0 8px !important;
        width: 1px;
        white-space: nowrap;
  }
</style>

<style lang="scss" scoped>
.dispatch-pop {
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: row;
	// background-color: #00B8EC;
	.pop-left {
		flex: 1 1 65%;
		padding: 0 20px;
		border-right: 1px solid #d9d9d9;
		
		.dialog-title {
		        text-align:left;
		        height:40px;
		        line-height:40px;
		        font-size:16px;
		        font-weight:bold;
		        color:#333333;
		 }
	}
	.pop-right {
		flex: 1 1 35%;
		padding-left: 2px;
		#amap-pop{
		    height: 100%;
		    width: 100%;
		}
	}
}
.pop-scroll-view {
     overflow-y:auto; 
        overflow-x:hidden;
        width:100%; 
        height:480px;
}
.demo-ruleForm {
    padding:0 20px;
}
.el-form-item__content {
    line-height: 40px !important;
    position: relative !important;
    font-size: 14px !important;
    display: inline-block !important;
}

</style>