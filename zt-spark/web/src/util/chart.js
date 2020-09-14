
/**
 * 折线图
 * @param {*} _echart 
 * @param {*} data 
 */
export let singleLineCharts = (_echart, data) => {
    let option = {
        // color: ['#11ccc0', '#ff7800'],
        title: {
            top: '10px',
            text: data.title,
            left: '10px',
            textStyle: {
                // color: "white",
                fontSize: 16,
                fontWeight: 'normal'
            }
        },
        grid: {
            top: '80px',
            left: '3%',
            right: '7%',
            bottom: '5  %',
            containLabel: true
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            top:'15px',
            right: '10px',
            data:  ['本次累计变化量', '上次累计变化量', '变化速率'],
            textStyle: {
                // color: "white",
                
            },
            selected:{
                '上次累计变化量': false,
                '变化速率': false
            }
        },
        xAxis: {
            name:'深度（m）',
            type: 'category',
            boundaryGap: true,
            axisLine: {
                lineStyle: {
                    type: "solid",
                    // color: "white" //坐标线的颜色
                }
            },
            axisLabel: {
                textStyle: {
                    // color: "white" //坐标值得具体的颜色
                }
            },
            data: data.xAxis
        },
        yAxis: {
            name:'变化量（mm）',
            type: 'value',
            axisLine: {
                lineStyle: {
                    type: "solid",
                    // color: "white" //坐标线的颜色
                }
            },
            axisLabel: {
                textStyle: {
                    // color: "white" //坐标值得具体的颜色
                }
            },
            splitLine:{
                show: true,
                lineStyle:{
                    type:'dashed'
                }
            },
        },
        series: [
            {
                name: '本次累计变化量',
                type: 'line',
                data: data.current,
            },
            {
                name: '上次累计变化量',
                type: 'line',
                data: data.last,
            },
            {
                name: '变化速率',
                type: 'line',
                data: data.rate,
            }
        ]
    };
    _echart.setOption(option, true);
}