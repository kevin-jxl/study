<template>
	<div>
		<div id="calendar">
			<!-- 年份 月份 -->
			<div class="month">
				<ul>
					<!--点击会触发pickpre函数，重新刷新当前日期 @click(vue v-on:click缩写) -->
					<li class="arrow" @click="pickPre1(currentYear,currentMonth)"><i class="el-icon-d-arrow-left"></i></li>
					<li class="arrow" @click="pickPre(currentYear,currentMonth)"><i class="el-icon-arrow-left"></i></li>
					<li class="year-month" @click="pickYear(currentYear,currentMonth)">
						<span class="choose-year">{{ currentYear }} 年 {{ currentMonth }}月</span>
						<!-- <span class="choose-month">{{ currentMonth }}月</span> -->
					</li>
					<li class="arrow" @click="pickNext(currentYear,currentMonth)"><i class="el-icon-arrow-right"></i></li>
					<li class="arrow" @click="pickNext1(currentYear,currentMonth)"><i class="el-icon-d-arrow-right
							"></i></li>
				</ul>
			</div>
			<!-- 星期 -->
			<ul class="weekdays">
				<li>星期一</li>
				<li>星期二</li>
				<li>星期三</li>
				<li>星期四</li>
				<li>星期五</li>
				<li>星期六</li>
				<li>星期日</li>
			</ul>
			<!-- 日期 -->
			<ul class="days">
				<!-- v-for循环 每一次循环用<li>标签创建一天 -->
				<li v-for="(dayobject,i) in days" style="height: 50px;" :key="i" @click="clickFun(dayobject)" :class="dayobject.active?'activeBg':''">
					<!--本月-->
					<!--如果不是本月  改变类名加灰色-->
		
					<span v-if="dayobject.day.getMonth()+1 != currentMonth" class="other-month">{{ dayobject.day.getDate() }}</span>
		
					<!--如果是本月  还需要判断是不是这一天-->
					<span v-else>
						<!--今天  同年同月同日-->
						<span v-if="dayobject.day.getFullYear() == new Date().getFullYear() && dayobject.day.getMonth() == new Date().getMonth() && dayobject.day.getDate() == new Date().getDate()"
						 class="active">{{ dayobject.day.getDate() }}</span>
						<span v-else>{{ dayobject.day.getDate() }}</span>
					</span>
					<!--显示剩余多少数量-->
					<!-- <p v-if="leftobj[dayobject.index]">剩余：<span style="color: red" >{{leftobj[dayobject.index].count}}</span></p> -->
					<!---->
					<!-- <button @click="order(dayobject)" v-if="leftobj[dayobject.index]">预定</button> -->
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
	export default{
		data(){
			return {
				currentDay: 1,
				currentMonth: 1,
				currentYear: 1970,
				currentWeek: 1,
				days: [],
				leftobj: [ //存放剩余数量
					{
						count: 1
					},
					{
						count: 2
					},
					{
						count: 3
					},
					{
						count: 4
					},
					{
						count: 5
					},
				],
			}
		},
		created() {
			this.initData(null);
		},
		methods:{
			order: function(day) { //预定函数
				if (this.leftobj[day.index].count >= 1)
					this.leftobj[day.index].count--;
				else
					alert('已经没有位置了')
			},
			initData: function(cur) {
				var leftcount = 0; //存放剩余数量
				var date;
				//var index=0;   //控制显示预定的天数 ，比如下面设置只能预定三天的
				//this.initleftcount(); 每次初始化更新数量
				//有两种方案  一种是每次翻页 ajax获取数据初始化   http请求过多会导致资源浪费
				// 一种是每次请求 ajax获取数据初始化    数据更新过慢会导致缺少实时性
				//还可以setTimeout 定时请求更新数据  实现数据刷新（可能会更好）
			
			
				if (cur) {
					date = new Date(cur);
				} else {
					var now = new Date();
					var d = new Date(this.formatDate(now.getFullYear(), now.getMonth(), 1));
					d.setDate(42);
					date = new Date(this.formatDate(d.getFullYear(), d.getMonth() + 1, 1));
				}
				this.currentDay = date.getDate();
				this.currentYear = date.getFullYear();
				this.currentMonth = date.getMonth() + 1;
			
				this.currentWeek = date.getDay(); // 1...6,0
				if (this.currentWeek == 0) {
					this.currentWeek = 7;
				}
				var str = this.formatDate(this.currentYear, this.currentMonth, this.currentDay);
				this.days.length = 0;
				// 今天是周日，放在第一行第7个位置，前面6个
				//初始化本周
				for (var i = this.currentWeek - 1; i >= 0; i--) {
					var d = new Date(str);
					d.setDate(d.getDate() - i);
			
					var dayobject = {};
					dayobject.day = d;
					var now = new Date();
					if (d.getDate() === (now.getDate()) && d.getMonth() === now.getMonth() && d.getFullYear() === now.getFullYear()) {
						//dayobject.index=index++;//从今天开始显示供预定的数量
						dayobject.active = true;
					}
					// else  if(index!=0&&index<3)
					//     dayobject.index=index++;//从今天开始3天内显示供预定的数量
			
					this.days.push(dayobject); //将日期放入data 中的days数组 供页面渲染使用
			
			
				}
				//其他周
				for (var i = 1; i <= 42 - this.currentWeek; i++) {
					var d = new Date(str);
					d.setDate(d.getDate() + i);
					var dayobject = {};
					dayobject.day = d;
					var now = new Date();
					if (d.getDate() === (now.getDate()) && d.getMonth() === now.getMonth() && d.getFullYear() === now.getFullYear()) {
						// dayobject.index=index++;
						dayobject.active = true;
					}
			
					// else if(index!=0&&index<3)
					//      dayobject.index=index++;
					this.days.push(dayobject);
				}
			
			},
			pickPre: function(year, month) {
				// setDate(0); 上月最后一天
				// setDate(-1); 上月倒数第二天
				// setDate(dx) 参数dx为 上月最后一天的前后dx天
				var d = new Date(this.formatDate(year, month, 1));
				d.setDate(0);
				this.initData(this.formatDate(d.getFullYear(), d.getMonth() + 1, 1));
			},
			pickNext: function(year, month) {
				var d = new Date(this.formatDate(year, month, 1));
				d.setDate(42);
				this.initData(this.formatDate(d.getFullYear(), d.getMonth() + 1, 1));
			},
			
			pickPre1: function(year, month) {
				// setDate(0); 上月最后一天
				// setDate(-1); 上月倒数第二天
				// setDate(dx) 参数dx为 上月最后一天的前后dx天
				var d = new Date(this.formatDate(year, month, 1));
				d.setDate(0);
				console.log(d.getMonth())
				var month = null
				if (d.getMonth() == 11) {
					month = 1
				} else {
					month = d.getMonth() + 2
				}
				console.log(this.formatDate(d.getFullYear() - 1, month, 1))
				this.initData(this.formatDate(d.getFullYear() - 1, month, 1));
			},
			pickNext1: function(year, month) {
				var d = new Date(this.formatDate(year, month, 1));
				d.setDate(42);
				this.initData(this.formatDate(d.getFullYear() + 1, d.getMonth(), 1));
			},
			
			// pickYear: function(year, month) {
			// 	alert(year + "," + month);
			// },
			
			// 返回 类似 2016-01-02 格式的字符串
			formatDate: function(year, month, day) {
				var y = year;
				var m = month;
				if (m < 10) m = "0" + m;
				var d = day;
				if (d < 10) d = "0" + d;
				return y + "-" + m + "-" + d
			},
			clickFun(dayobject) {
				this.days.forEach((item, i) => {
					item.active = false;
					if (dayobject.day == item.day) {
						item.active = true;
						this.$set(this.days, i, this.days[i])
					}
				})
				this.$emit('child-event', dayobject);
			}
		}
			
	}
</script>

<style scoped="scoped">
	#calendar {
		width: 450px;
	}
	
	.month {
		width: 100%;
		background: #fff;
	}
	
	.month ul {
		margin: 0;
		padding: 0;
		display: flex;
		width: 260px;
		margin: 0 auto;
		height: 50px;
		align-items: center;
		/* justify-content: space-between; */
	}
	
	.year-month {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-around;
	}
	
	.year-month:hover {
		background: rgba(150, 2, 12, 0.1);
	}
	
	.choose-year {
		/* padding-left: 20px;
		padding-right: 20px; */
		font-weight: 600;
	}
	
	.choose-month {
		text-align: center;
		font-size: 1.5rem;
	}
	
	.arrow {
		/* padding: 30px; */
	}
	
	.arrow:hover {
		background: rgba(100, 2, 12, 0.1);
	}
	
	.month ul li {
		font-size: 20px;
		text-transform: uppercase;
		letter-spacing: 3px;
	}
	
	.weekdays {
		margin: 0;
		padding: 5px 0;
		background-color: #00B8EC;
		display: flex;
		flex-wrap: wrap;
		color: #FFFFFF;
		justify-content: space-around;
	}
	
	.weekdays li {
		display: inline-block;
		width: 13.6%;
		text-align: center;
	}
	
	.days {
		padding: 0;
		background: #FFFFFF;
		margin: 0;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-around;
		border-left: 1px solid #ccc;
	}
	
	.days li {
		list-style-type: none;
		display: inline-block;
		width: 14%;
		text-align: center;
		font-size: 1rem;
		display: flex;
		align-items: center;
		justify-content: center;
		border-right: 1px solid #ccc;
		border-bottom: 1px solid #ccc;
	}
	
	.days li .active {
		padding: 6px 12px;
		border-radius: 50%;
		background: #00B8EC;
		color: #fff;
	}
	
	.days li .other-month {
		padding: 5px;
		color: gainsboro;
	}
	
	.days li:hover {
		background: #e1e1e1;
	}
	
	.activeBg {
		background: #eaf7ff;
		color: #36b0fb;
	}
</style>
