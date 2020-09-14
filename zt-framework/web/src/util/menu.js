const menuList = [
    {
        "id": '8',
        "label": "首页",
        "path": "/manage/home",
        'icon': 'el-icon-s-promotion',
    },
    {
        "id": '1',
        "label": "基础管理",
        "path": "/manage/user",
        'icon': 'el-icon-s-promotion',
        "children":[
           {
                "id": '1-2',
                "path": "/manage/user",
                "label": "用户管理",
                'icon': 'el-icon-s-order'
            },{
                "id": '1-3',
                "path": "/manage/rule/detail",
                "label": "角色管理",
                'icon': 'el-icon-s-order',
                // "children":[
                    // {
                    //     "id": '1-3-1',
                    //     "path": "/manage/rule/detail",
                    //     "label": "角色管理",
                    //     'icon': 'el-icon-s-order',
                    // }
                    // ,
                    //  {
                    //     "id": '1-3-2',
                    //     "path": "/manage/rule/edit",
                    //     "label": "角色配置",
                    //     'icon': 'el-icon-s-order',
                    // },{
                    //     "id": '1-3-3',
                    //     "path": "/manage/rule/post",
                    //     "label": "岗位配置",
                    //     'icon': 'el-icon-s-order',
                    // },
                // ]
            },{
                "id": '1-4',
                "path": "/manage/menu/detail",
                "label": "菜单管理",
                'icon': 'el-icon-s-order',
                // "children":[
                //     {
                //         "id": '1-4-1',
                //         "path": "/manage/menu/detail",
                //         "label": "菜单管理",
                //         'icon': 'el-icon-s-order',
                //     }, {
                //         "id": '1-4-2',
                //         "path": "/manage/menu/edit",
                //         "label": "功能菜单",
                //         'icon': 'el-icon-s-order',
                //     },{
                //         "id": '1-4-3',
                //         "path": "/manage/menu/config",
                //         "label": "功能配置",
                //         'icon': 'el-icon-s-order',
                //     }
                // ]
            },{
                "id": '1-5',
                "path": "/manage/log/business",
                "label": "日志管理",
                'icon': 'el-icon-s-order',
                "children":[
                    {
                        "id": '1-5-1',
                        "path": "/manage/log/business",
                        "label": "操作日志",
                        'icon': 'el-icon-s-order',
                    }, {
                        "id": '1-5-2',
                        "path": "/manage/log/sys",
                        "label": "系统日志",
                        'icon': 'el-icon-s-order',
                    }
                    , {
                        "id": '1-5-3',
                        "path": "/manage/log/login",
                        "label": "登录日志",
                        'icon': 'el-icon-s-order',
                    }
                ]
            },{
                "id": '1-6',
                "path": "/manage/dic",
                "label": "字典管理",
                'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '2',
        "label": "组织管理",
        "path": "/manage/area",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
                "id": '2-1',
                "path": "/manage/area",
                "label": "行政区域管理",
                'icon': 'el-icon-s-order'
            },{
                "id": '2-2',
                "path": "/manage/firm",
                "label": "企业管理",
                'icon': 'el-icon-s-order'
            },{
                "id": '2-3',
                "path": "/manage/agency",
                "label": "行政机构管理",
                'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '3',
        "label": "机构管理",
        "path": "/manage/mechanism",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
                "id": '3-1',
                "path": "/manage/mechanism",
                "label": "业务机构管理",
                'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '9',
        "path": "/manage/tenant",
        "label": "租户管理",
        'icon': 'el-icon-s-order',
        "children":[
            {
                "id": '9-1',
                "path": "/manage/tenant",
                "label": "租户管理",
                'icon': 'el-icon-s-order',
				
            },
			{
			    "id": '9-2',
			    "path": "/manage/business",
			    "label": "业务配置",
			    'icon': 'el-icon-s-order',
			}
        ]
    }, {
        "id": '4',
        "label": "业务流管理",
        "path": "/manage/repair",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
                "id": '4-1',
                "path": "/manage/repair",
                "label": "业务流管理",
                'icon': 'el-icon-s-order'
            }
            // ,{
            //     "id": '4-2',
            //     "path": "/manage/event",
            //     "label": "事件处理流程管理",
            //     'icon': 'el-icon-s-order'
            // },{
            //     "id": '24-3',
            //     "path": "/manage/approve",
            //     "label": "审批流程管理",
            //     'icon': 'el-icon-s-order'
            // }
        ]
    }, {
        "id": '5',
        "label": "数据管理",
        "path": "/manage/data/list",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
                "id": '5-1',
                "path": "/manage/data/list",
                "label": "数据列表管理",
                'icon': 'el-icon-s-order'
            }, {
                "id": '5-2',
                "path": "/manage/data/config",
                "label": "数据配置管理",
                'icon': 'el-icon-s-order'
            }, {
                "id": '5-3',
                "path": "/manage/data/business",
                "label": "业务数据管理",
                'icon': 'el-icon-s-order'
            }, {
                "id": '5-4',
                "path": "/manage/layer/model",
                "label": "三维建模数据",
                'icon': 'el-icon-s-order'
            }, {
                "id": '5-5',
                "path": "/manage/layer/gis",
                "label": "GIS数据管理",
                'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '6',
        "label": "接口集成",
        "path": "/manage/interface/sys",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
                "id": '6-1',
                "path": "/manage/interface/sys",
                "label": "系统集成",
                'icon': 'el-icon-s-order'
            },{
                "id": '6-2',
                "path": "/manage/interface/iot",
                "label": "物联网集成",
                'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '7',
        "label": "规则引擎管理",
        "path": "/manage/rule/sys",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
                "id": '7-1',
                "path": "/manage/rule/billing",
                "label": "计费规则",
                'icon': 'el-icon-s-order'
            },{
                "id": '7-2',
                "path": "/manage/rule/save",
                "label": "数据存储规则",
                'icon': 'el-icon-s-order'
            }
        ]
    }
]


export default menuList;