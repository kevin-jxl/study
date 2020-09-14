
const code = 200 // 返回的状态码
export function randomNumber(min, max) {
    return Math.floor(Math.random() * (max - min) + min)
}
export function randomAvatar() {
    const avatarList = [
      'https://randomuser.me/api/portraits/men/32.jpg',
      'https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?h=350&auto=compress&cs=tinysrgb',
      'https://d3iw72m71ie81c.cloudfront.net/female-17.jpg',
      'https://randomuser.me/api/portraits/men/35.jpg',
      'https://pbs.twimg.com/profile_images/835224725815246848/jdMBCxHS.jpg',
      'https://pbs.twimg.com/profile_images/584098247641300992/N25WgvW_.png',
      'https://d3iw72m71ie81c.cloudfront.net/male-5.jpg',
      'https://images.pexels.com/photos/413723/pexels-photo-413723.jpeg?h=350&auto=compress&cs=tinysrgb',
      'https://randomuser.me/api/portraits/women/44.jpg',
      'https://randomuser.me/api/portraits/women/68.jpg',
      'https://randomuser.me/api/portraits/women/65.jpg',
      'https://randomuser.me/api/portraits/men/43.jpg',
      'https://tinyfac.es/data/avatars/475605E3-69C5-4D2B-8727-61B7BB8C4699-500w.jpeg',
      'https://pbs.twimg.com/profile_images/943227488292962306/teiNNAiy.jpg',
      'https://randomuser.me/api/portraits/men/46.jpg'
    ]
    return avatarList[randomNumber(0, avatarList.length - 1)]
}
const EnumRoleType = {
    ADMIN: 'admin',
    DEFAULT: 'guest',
    DEVELOPER: 'developer',
}
  
const userPermission = {
    DEFAULT: {
        visit: ['1', '2', '21', '7', '5', '51', '52', '53'],
        role: EnumRoleType.DEFAULT,
    },
    ADMIN: {
        role: EnumRoleType.ADMIN,
    },
    DEVELOPER: {
        role: EnumRoleType.DEVELOPER,
    },
}
const adminUsers = [
    {
      id: 0,
      username: 'admin',
      password: 'admin',
      permissions: userPermission.ADMIN,
      avatar: randomAvatar(),
    },
    {
      id: 1,
      username: 'guest',
      password: 'guest',
      permissions: userPermission.DEFAULT,
      avatar: randomAvatar(),
    },
    {
      id: 2,
      username: '吴彦祖',
      password: '123456',
      permissions: userPermission.DEVELOPER,
      avatar: randomAvatar(),
    },
]

export let login = (options) => {
    let _param=JSON.parse(options.body)
    const user = adminUsers.filter(item => item.username === _param.username);
    let posts = {}
    if (user.length > 0 && user[0].password === _param.password) {
        posts = {code, msg:'登录成功！', result: user[0]}
    } else {
        posts = {code: 403, msg:'用户名或密码错误！', result:''}
    }
    return { ...posts }
}

export let logout = () => {
    localStorage.setItem('isLogin', false)
    let posts = {code, msg:'退出登录成功！', result:''}
    return { ...posts }
}