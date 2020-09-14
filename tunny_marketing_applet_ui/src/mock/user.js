const Mock = require("mockjs")
const Random = Mock.Random
const code = 200
export const userInfo = {
    userInfo: {
        username: 'admin',
        name: 'avue',
    },
    roles: ['admin'],
    permission: [
        'sys_crud_btn_add',
        'sys_crud_btn_export',
        'sys_menu_btn_add',
        'sys_menu_btn_edit',
        'sys_menu_btn_del',
        'sys_role_btn1',
        'sys_role_btn2',
        'sys_role_btn3',
        'sys_role_btn4',
        'sys_role_btn5',
        'sys_role_btn6',
    ],//权限级别
}
let List = []
for (let i = 0; i < 5; i++) {
    List.push(Mock.mock({
        id: '@increment',
        name: Mock.mock('@cname'),
        username: Mock.mock('@last'),
        'type|0-1': 0,
        'sex|0-1': 0,
        grade: [0, 1],
        address: Mock.mock('@cparagraph(1, 3)'),
        check: [1, 3, 4]
    }))
}
export const tableData = {
    total: 11,
    pageSize: 10,
    tableData: List
};

const postData = req => {

  console.log(req) // 请求体，用于获取参数

  let posts = [] // 用于存放文章数据的数组

  for (let i = 0; i < 10; i++) {
    let post = {
      title: Random.csentence(10, 25), // 随机生成长度为10-25的标题
      icon: Random.dataImage('250x250', '文章icon'), // 随机生成大小为250x250的图片链接
      author: Random.cname(), // 随机生成名字
      date: Random.date() + ' ' + Random.time() // 随机生成年月日 + 时间
    }

    posts.push(post)
  }

  // 返回状态码和文章数据posts
  return {
    code,
    posts
  }
}

// Mock.mock(/\/admin\/menu\/allTree/, 'get', postData);

