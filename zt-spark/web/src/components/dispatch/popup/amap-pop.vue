<template>
    <div>
        <div id="amap-pop"></div> 
    </div>
</template>

<script>
import { lazyAMapApiLoaderInstance } from 'vue-amap';
    export default {
         mounted() {
            lazyAMapApiLoaderInstance.load().then(() => {
                this.map = new AMap.Map('amap-pop', {
					zoom:13,
					center: new AMap.LngLat("116.368904","39.913423")
				});
                this.addPine();
            });
		},
		data() {
			return {
				map: '',
				markerList: []
			}
		},
		methods: {
			addPine() {
				let self = this;
			    if(self.markerList.length>0) {
			        self.map.remove(self.markerList);
			        self.markerList = [];
			    }
			
			    // 创建一个 Marker 实例：
			    var marker1 = new AMap.Marker({
			        position: new AMap.LngLat("116.368904","39.913423"),
			        offset: new AMap.Pixel(-10, -10),
			        // icon: '//vdata.amap.com/icons/b18/1/2.png', // 添加 Icon 图标 URL
			        title: '北京'
			    });
			    self.markerList.push(marker1);
				
				var marker2 = new AMap.Marker({
				    position: new AMap.LngLat("116.367715","39.911115"),
				    offset: new AMap.Pixel(-10, -10),
				    icon: '//vdata.amap.com/icons/b18/1/2.png', // 添加 Icon 图标 URL
				    title: '北京'
				});
				self.markerList.push(marker2);
				
				// _this.map.setCenter(["116.368904","39.913423"]);
				var circle = new AMap.Circle({
					center: new AMap.LngLat("116.368904","39.913423"),
				    offset: new AMap.Pixel(-10, -10),
				    title: '北京',
					radius: 1000,  //半径
					strokeColor: "#F33",  //线颜色
					strokeOpacity: 1,  //线透明度
					strokeWeight: 1,  //线粗细度
					fillColor: "#ee2200",  //填充颜色
					fillOpacity: 0.35 //填充透明度
				});
				
				self.markerList.push(circle);
				
			    self.map.add(self.markerList);
				
			    var content = `<div style='display: flex; flex-direction: row; padding:10px 20px'>
			                <div style='font-size：10px; line-height: 25px'>
			                    <div>员工姓名: 张三</div> 
			                    <div>员工编号: za-001</div>  
			                    <div>联系方式: 18500000000</div>  
			                </div>
			        </div>`
			    // 创建 infoWindow 实例	
			    var infoWindow = new AMap.InfoWindow({
			            anchor: 'top-center',
			            content: content,
			    });
				for(let item = 0; item<self.markerList.length-1; item++) {
					self.markerList[item].on('click',  function(e) {
					    infoWindow.open(self.map,[e.lnglat.getLng(),e.lnglat.getLat()]);
					})
				}
			    
			   
			}
		}
    }
</script>

<style lang="scss" scoped>
     #amap-pop{
        height: 420px;
        width: 340px;
    }
</style>