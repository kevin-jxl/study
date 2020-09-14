import * as api from './api'
import request from './http.js'

// ajax ES6封装
const baseUrl = config.arcgicServices;
export const ajaxPromise = ({ url, type = "get", dataType = "json", data = {f:'pjson'}}) => {
    return new Promise((resovle, reject) => {
        $.ajax({ 
            async: true, //同步，异步
            url: baseUrl + url,
            type, 
            dataType, 
            data, 
            success:res => { resovle(res) },
            error:res => { reject(res) }
        }) 
    })
}


export const loginByUsername = (username, password, code, randomStr) => {
    var grant_type = 'password'
    var scope = 'server'
    return request({
        url: '/auth/oauth/token',
        headers: {
            'Authorization': 'Basic c3Bhcms6c3Bhcms='
        },
        method: 'post',
        params: { username, password, randomStr, code, grant_type, scope }
    })
}
//获取当前日期yyyy-MM-dd
function date (){
	
	let nowDate = new Date();
	 
	let year = nowDate.getFullYear();
	 
	let month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1): nowDate.getMonth() + 1;
	 
	let day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	 
	let dateStr = year + "-" + month + "-" + day;
	return dateStr
}
console.log()

//人员管理导出
export const getDownloadExcel = (obj,url)=> { 
	obj.tableName = obj.tableName+date()
	return request({
        url: url,
        method: 'get',
        params: obj,
        responseType: 'blob'
    }).then((response) => { // 处理返回的文件流
        let blob = new Blob([response.data], { type:   'application/xlsx' } )
        let link = document.createElement('a')
        link.href = window.URL.createObjectURL(blob)
        link.download = obj.tableName + '.xlsx'
        link.click()
    })
}

export default api
