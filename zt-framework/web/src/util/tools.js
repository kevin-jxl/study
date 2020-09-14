import { format } from "util";

/**
 * 常用工具
 * @author hejin
 * @date 2019-2-22
 */

/**
 * 是否是空数组
 * @author hejin
 * @date 2019-2-22
 */
export function isEmptyArray (arr) {
  return arr === undefined || !Array.isArray(arr) || arr.length === 0
};

/**
 * 数组去重
 * @author hejin
 * @date 2019-8-26
 * @example dedupe([1, 1, 2, 3]) // [1, 2, 3]
 */
function dedupe(array) {
  return Array.from(new Set(array));
}

/**
 * 是否空对象
 * @author hejin
 * @date 2019-2-22
 * @param obj
 * @returns {boolean}
 */
export function isEmptyObject (obj) {
  if (!obj || typeof obj !== 'object') {
    return true
  }
  let name
  for (name in obj) {
    return false
  }
  return true
}

/**
 * 分解对象成单属性对象数组
 * @author hejin
 * @date 2019-2-22
 * @param obj
 * @returns {Array}
 */
export function objectToArray (obj) {
  let arrObj = []
  Object.keys(obj).map(key => {
    if (obj[key] !== undefined && trim(obj[key])) {
      let o = {}
      o[key] = obj[key]
      arrObj.push(o)
    }
  })
  return arrObj
};

/**
 * 去左右空格
 * @param { string } s
 */
export function trim (s) {
  return typeof (s) !== 'string' ? s : s.replace(/(^\s*)|(\s*$)/g, '')
}

/**
 * 均衡获取 0 到 9 的随机整数
 * @param { number } 区间值
 */
export function getRandom( number = 10 ){
  return Math.floor(Math.random() * number);
}



/**
 * 数组分割
 */
export function chunk(arr, length) {
  length = parseInt(length)
  var groups = []
  if (length <= 1 || arr.length < length) {
    groups.push(arr)
    return groups
  }
  var loop = Math.ceil(arr.length / length)
  for (var i = 0; i < loop; i++) {
    groups.push(arr.slice(length * i, length * (i + 1)))
  }
  return groups
};


/**
 * 生成一个唯一的uuid方法
 */
export function uuid(len, radix) {
  var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('')
  var uuid = []
  var i
  radix = radix || chars.length
  if (len) {
    // Compact form
    for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix]
  } else {
    // rfc4122, version 4 form
    var r

    // rfc4122 requires these characters
    uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-'
    uuid[14] = '4'

    // Fill in random data.  At i==19 set the high bits of clock sequence as
    // per rfc4122, sec. 4.1.5
    for (i = 0; i < 36; i++) {
      if (!uuid[i]) {
        r = 0 | Math.random() * 16
        uuid[i] = chars[(i === 19) ? (r & 0x3) | 0x8 : r]
      }
    }
  }
  return uuid.join('')
};


/**
 * 计算距离方法
 */
export function GetDistance(lat1, lng1, lat2, lng2) {  
  var radLat1 = lat1 * Math.PI / 180.0
  var radLat2 = lat2 * Math.PI / 180.0
  var a = radLat1 - radLat2
  var b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0
  var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
    Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)))
  s = s * 6378.137
  s = Math.round(s * 10000) / 10000
  return s
}

/**
 * 判断对象值是否为空
 * @param obj
 * @returns {boolean}
 */
export function isEmptyObjectValue(obj) {
  let flag = true;
  if (!isEmptyObject(obj)) {
    let key;
    for (key in obj) {
      flag = flag && obj[key] == undefined;
      if (!flag) {
        return false;
      }
    }
  }
  return flag;
}

/**
 * 格式化点数据经纬度
 * @param info
 */
export function formatLngLat(info){
  return {lng:Number(info['X']).toFixed(6), lat:Number(info['Y']).toFixed(6)}
}

/**
* 数组元素交换位置
* @param {array} arr 数组
* @param {number} index1 添加项目的位置
* @param {number} index2 删除项目的位置
* index1和index2分别是两个数组的索引值，即是两个要交换元素位置的索引值，如1，5就是数组中下标为1和5的两个元素交换位置
*/
export function swapArray(arr, index1, index2) {
  arr[index1] = arr.splice(index2, 1, arr[index1])[0];
   return arr;
}

//上移 将当前数组index索引与后面一个元素互换位置，向数组后面移动一位
export function zIndexUp(arr,index,length){
  if(index+1 != length){
      swapArray(arr, index, index+1);
  }else{
    console.log('已经处于置顶，无法上移');
 }
}

//下移 将当前数组index索引与前面一个元素互换位置，向数组前面移动一位
export function zIndexDown(arr,index,length){
  if(index!= 0){
    swapArray(arr, index, index-1);
  }else{
    console.log('已经处于置底，无法下移');
  }
}

//置顶，即将当前元素移到数组的最后一位
export function zIndexTop(arr,index,length){
  if(index+1 != length){
    //首先判断当前元素需要上移几个位置,置底移动到数组的第一位
    var moveNum = length - 1 - index;
    //循环出需要一个一个上移的次数
    for (var i = 0; i<moveNum; i++) {
      swapArray(arr, index, index + 1);
      index++;
    }
  }else{
    console.log('已经处于置顶');
 }
}

//置底，即将当前元素移到数组的第一位
export function zIndexBottom(arr,index){
  if(index!=0){
    //首先判断当前元素需要上移几个位置,置底移动到数组的第一位
    var moveNum = index - 0;
    //循环出需要一个一个上移的次数
    for (var i = 0; i<moveNum; i++) {
      swapArray(arr, index, index - 1);
      index--;
    }
  }else{
    console.log('已经处于置底');
 }
 return arr;
}

function isFunction(it) {
  return ostring.call(it) === '[object Function]';
}

function isArray(it) {
  return ostring.call(it) === '[object Array]';
}