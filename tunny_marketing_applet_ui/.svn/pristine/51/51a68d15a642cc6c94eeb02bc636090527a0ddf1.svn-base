import {
    validatenull
} from './validate'
import {
    baseUrl
} from '@/config/env'

/**
 * 设置主题
 */
export const setTheme = (name) => {
    document.body.className = name
}

export const initMenu = (router, menu) => {
    if (menu.length === 0) {
        return
    }
    router.addRoutes(formatRoutes(menu))
}

export const openWindow = (url, title, w, h) => {
    // Fixes dual-screen position                            Most browsers       Firefox
    const dualScreenLeft = window.screenLeft !== undefined ? window.screenLeft : screen.left
    const dualScreenTop = window.screenTop !== undefined ? window.screenTop : screen.top

    const width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width
    const height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height

    const left = ((width / 2) - (w / 2)) + dualScreenLeft
    const top = ((height / 2) - (h / 2)) + dualScreenTop
    const newWindow = window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, copyhistory=no, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left)

    // Puts focus on the newWindow
    if (window.focus) {
        newWindow.focus()
    }
}

export const formatRoutes = (aMenu) => {
    const aRouter = []
    aMenu.forEach(oMenu => {
        const {
            path,
            component,
            name,
            icons,
            children,
          meta
        } = oMenu
        if (!validatenull(component)) {
            let filePath
            const oRouter = {
                path: path?path:'',
                component(resolve) {
                    let componentPath = ''
                  if(oMenu.type === '9'){
                    if (component === 'Layout') {
                      require(['../page/index'], resolve)
                      return
                    } else {
                      require([`../page/${component}`], resolve)
                      return
                    }
                  }else{
                    componentPath = component
                  }
                  require([`../${componentPath}.vue`], resolve)
                   /* if (component === 'Layout') {
                        require(['../page/index'], resolve)
                        return
                    } else {
                        componentPath = component
                    }
                    require([`../${componentPath}.vue`], resolve)*/
                },
                name: name,
                icon: icons,
                children: validatenull(children) ? [] : formatRoutes(children),
              meta:meta
            }
            aRouter.push(oRouter)
        }
    })
    return aRouter
}

/**
 * 加密处理
 */
export const encryption = (params) => {
    let {
        data,
        type,
        param,
        key
    } = params
    const result = JSON.parse(JSON.stringify(data))
    if (type === 'Base64') {
        param.forEach(ele => {
            result[ele] = btoa(result[ele])
        })
    } else {
        param.forEach(ele => {
            var data = result[ele]
            //key = CryptoJS.enc.Latin1.parse(key)
            key = CryptoJS.enc.Latin1.parse('tunnytunny123456')
            var iv = key
                // 加密
            var encrypted = CryptoJS.AES.encrypt(
                data,
                key, {
                    iv: iv,
                    mode: CryptoJS.mode.CBC,
                    padding: CryptoJS.pad.ZeroPadding
                })
            result[ele] = encrypted.toString()
        })
    }
    return result
}

/**
 * 设置浏览器头部标题
 */
export const setTitle = function(title) {
        title = title ? `${title}——Tunny PaaS服务开发框架` : 'Tunny PaaS服务开发框架'
        window.document.title = title
    }
    /**
     * 浏览器判断是否全屏
     */
export const fullscreenToggel = () => {
        if (fullscreenEnable()) {
            exitFullScreen()
        } else {
            reqFullScreen()
        }
    }
    /**
     * esc监听全屏
     */
export const listenfullscreen = (callback) => {
    function listen() {
        callback()
    }
    document.addEventListener('fullscreenchange', function(e) {
        listen()
    })
    document.addEventListener('mozfullscreenchange', function(e) {
        listen()
    })
    document.addEventListener('webkitfullscreenchange', function(e) {
        listen()
    })
    document.addEventListener('msfullscreenchange', function(e) {
        listen()
    })
}

/**
 * 浏览器判断是否全屏
 */
export const fullscreenEnable = () => {
    /*var isFullscreen = !document.fullscreenEnabled ||
        window.fullscreen ||
        document.msFullscreenEnabled ||
        document.webkitIsFullScreen*/

  var isFullscreen =document.fullscreenElement ||
    document.mozFullScreenElement ||
    document.webkitFullscreenElement
    return isFullscreen
}

/**
 * 浏览器全屏
 */
export const reqFullScreen = () => {
    if (document.documentElement.requestFullScreen) {
        document.documentElement.requestFullScreen()
    } else if (document.documentElement.webkitRequestFullScreen) {
        document.documentElement.webkitRequestFullScreen()
    } else if (document.documentElement.mozRequestFullScreen) {
        document.documentElement.mozRequestFullScreen()
    }
}

/**
 * 浏览器退出全屏
 */
export const exitFullScreen = () => {
    if (document.documentElement.requestFullScreen) {
        document.exitFullScreen()
    } else if (document.documentElement.webkitRequestFullScreen) {
        document.webkitCancelFullScreen()
    } else if (document.documentElement.mozRequestFullScreen) {
        document.mozCancelFullScreen()
    }
}

/**
 * 递归寻找子类的父类
 */
export const findParent = (menu, id) => {
    for (let i = 0; i < menu.length; i++) {
        if (menu[i].children.length !== 0) {
            for (let j = 0; j < menu[i].children.length; j++) {
                if (menu[i].children[j].id === id) {
                    return menu[i]
                } else {
                    if (menu[i].children[j].children.length !== 0) {
                        return findParent(menu[i].children[j].children, id)
                    }
                }
            }
        }
    }
}

/**
 * 总体路由处理器
 */
export const resolveUrlPath = (url, name) => {
    let reqUrl = url
    if (url.indexOf('#') !== -1 && url.indexOf('http') === -1) {
        const port = reqUrl.substr(reqUrl.indexOf(':'))
        reqUrl = `/myiframe/urlPath?src=${baseUrl}${port}${reqUrl.replace('#', '').replace(port, '')}}&name=${name}`
    } else if (url.indexOf('http') !== -1) {
        reqUrl = `/myiframe/urlPath?src=${reqUrl}&name=${name}`
    } else {
        reqUrl = `${reqUrl}`
    }
    return reqUrl
}

/**
 * 总体路由设置器
 */
export const setUrlPath = ($route) => {
    let value = ''
    if ($route.query.src) {
        value = $route.query.src
    } else {
        value = $route.path
    }
    return value
}

/**
 * 动态插入css
 */
export const loadStyle = url => {
    const link = document.createElement('link')
    link.type = 'text/css'
    link.rel = 'stylesheet'
    link.href = url
    const head = document.getElementsByTagName('head')[0]
    head.appendChild(link)
}

/**
 * 根据字典的value显示label
 */
export const findByvalue = (dic, value) => {
    let result = ''
    if (validatenull(dic)) return value
    if (typeof(value) === 'string' || typeof(value) === 'number') {
        let index = 0
        index = findArray(dic, value)
        if (index !== -1) {
            result = dic[index].label
        } else {
            result = value
        }
    } else if (value instanceof Array) {
        result = []
        let index = 0
        value.forEach(ele => {
            index = findArray(dic, ele)
            if (index !== -1) {
                result.push(dic[index].label)
            } else {
                result.push(value)
            }
        })
        result = result.toString()
    }
    return result
}

/**
 * 根据字典的value查找对应的index
 */
export const findArray = (dic, value) => {
    for (let i = 0; i < dic.length; i++) {
        if (dic[i].value === value) {
            return i
        }
    }
    return -1
}

/**
 * 生成随机len位数字
 */
export const randomLenNum = (len, date) => {
    let random = ''
    random = Math.ceil(Math.random() * 10000000000000).toString().substr(0, typeof len === 'number' ? len : 4)
    if (date) random = random + Date.now()
    return random
}

/**
 * 深拷贝对象和数组
 * @param obj 对象
 * @returns {any} 拷贝的对象
 */
export const deepCloneObj = (obj) => {
  return JSON.parse(JSON.stringify(obj))
}

/**
 * 防抖
 * @param fn 函数
 * @param wait 毫秒值
 * @returns {Function}
 */
export const debounce = (fn, wait=1000) => {
  let timeout = null;
  return function () {
    let context = this;
    let args = arguments;

    if (timeout) clearTimeout(timeout);

    timeout = setTimeout(() => {
      fn.apply(context, args)
    }, wait);
  }
}

/**
 * 节流
 * @param fn 函数
 * @param wait 毫秒值
 * @returns {Function}
 */
export const throttle = (fn, wait=1000) => {
  let previous = 0;
  return function() {
    let now = Date.now();
    let context = this;
    if (now - previous > wait) {
      fn.apply(context, arguments);
      previous = now;
    }
  }
}

export const accMul = (arg1, arg2) => {
  if(!arg1 || !arg2){
    return 0
  }
  var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
  try {
    m += s1.split(".")[1].length;
  }
  catch (e) {
  }
  try {
    m += s2.split(".")[1].length;
  }
  catch (e) {
  }
  return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}
