const menuList = [
    {
        "id": '1',
        "label": "首页",
        "path": "/dashbord",
        'icon': 'el-icon-s-promotion',
    },
    {
        "id": '2',
        "label": "公司管理",
        "path": "/company",
        'icon': 'el-icon-s-promotion',
        "children":[
           {
                "id": '2-2',
                "path": "/company/person",
                "label": "人员管理",
                'icon': 'el-icon-s-order'
            }, {
                "id": '2-3',
                "path": "/company/duty",
                "label": "值班信息管理",
                'icon': 'el-icon-s-order'
            }, {
                "id": '2-4',
                "path": "/company/register",
                "label": "车场登记",
                'icon': 'el-icon-s-order',
                "children":[
                    {
                        "id": '2-4-1',
                        "path": "/company/register/lot",
                        "label": "添加车场",
                        'icon': 'el-icon-s-order'
                    },{
                        "id": '2-4-2',
                        "path": "/company/register/berth",
                        "label": "添加泊位",
                        'icon': 'el-icon-s-order'
                    },
                ]
            }, {
                "id": '2-5',
                "path": "/company/attendance",
                "label": "考勤管理",
                'icon': 'el-icon-s-order'
            }, {
                "id": '2-6',
                "path": "/company/doc",
                "label": "文档管理",
                'icon': 'el-icon-s-order'
            }, {
                "id": '2-7',
                "path": "/company/card",
                "label": "门禁卡管理",
                'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '3',
        "label": "调度管理",
        "path": "/dispatch/business?type=1",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
                "id": '3-1',
                "path": "/dispatch/business?type=1",
                "label": "业务调度",
                'icon': 'el-icon-s-order',
                "children": [
                    {
                        "id": '3-1-1',
                        "path": "/dispatch/business?type=2",
                        "label": "派遣栏",
                        'icon': 'el-icon-s-order'
                    }, {
                        "id": '3-1-2',
                        "path": "/dispatch/business?type=3",
                        "label": "处置栏",
                        'icon': 'el-icon-s-order'
                    }, {
                        "id": '3-1-3',
                        "path": "/dispatch/business?type=4",
                        "label": "完成栏",
                        'icon': 'el-icon-s-order'
                    },
                ]    
            }, {
			    "id": '3-2',
			    "path": "/dispatch/supervise",
			    "label": "监督管理",
			    'icon': 'el-icon-s-order'
			}, {
			    "id": '3-3',
			    "path": "/dispatch/order",
			    "label": "工单管理",
			    'icon': 'el-icon-s-order'
			}, {
			    "id": '3-4',
			    "path": "/dispatch/tower",
			    "label": "岗楼管理",
			    'icon': 'el-icon-s-order'
			}
        ]
    }, {
        "id": '4',
        "label": "车场管理",
        "path": "/parking",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
                "id": '4-1',
                "path": "/parking/road",
                "label": "路内车场",
                'icon': 'el-icon-s-order'
            }, {
			    "id": '4-2',
			    "path": "/parking/indoor",
			    "label": "封闭车场",
			    'icon': 'el-icon-s-order'
			}, {
			    "id": '4-3',
			    "path": "/parking/video",
			    "label": "视频监控",
			    'icon': 'el-icon-s-order'
			}
        ]
    }, {
        "id": '5',
        "label": "设备管理",
        "path": "/device",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
                "id": '5-1',
                "path": "/device/list",
                "label": "设备列表",
                'icon': 'el-icon-s-order',
            },
            // {
            //     "id": '5-1-1',
            //     "path": "/device/list/annex",
            //     "label": "附件信息",
            //     'icon': 'el-icon-s-order'
            // },  
            {
			    "id": '5-2',
			    "path": "/device/monitor",
			    "label": "设备监测",
			    'icon': 'el-icon-s-order'
            }, {
			    "id": '5-3',
			    "path": "/device/spare",
			    "label": "备件列表",
			    'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '6',
        "label": "财务管理",
        "path": "/finance",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
                "id": '6-1',
                "path": "/finance/set",
                "label": "收费设置",
                'icon': 'el-icon-s-order'
            }, {
			    "id": '6-2',
			    "path": "/finance/vip",
			    "label": "会员管理",
			    'icon': 'el-icon-s-order'
			}, {
			    "id": '6-3',
			    "path": "/finance/settle",
			    "label": "结算管理",
			    'icon': 'el-icon-s-order'
            }, {
			    "id": '6-4',
			    "path": "/finance/order",
			    "label": "财务订单管理",
			    'icon': 'el-icon-s-order'
            }, {
			    "id": '6-5',
			    "path": "/finance/invoice",
			    "label": "发票管理",
			    'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '7',
        "label": "报表管理",
        "path": "/report",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
			    "id": '7-1',
			    "path": "/report/finance",
			    "label": "财务报表",
			    'icon': 'el-icon-s-order'
            }, {
			    "id": '7-2',
			    "path": "/report/park",
			    "label": "停车报表",
			    'icon': 'el-icon-s-order'
            }, {
			    "id": '7-3',
			    "path": "/report/operation",
			    "label": "运营报表",
			    'icon': 'el-icon-s-order'
            }, {
			    "id": '7-4',
			    "path": "/report/maintain",
			    "label": "运维报表",
			    'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '8',
        "label": "订单管理",
        "path": "/order",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
			    "id": '8-1',
			    "path": "/order/list",
			    "label": "订单管理",
			    'icon': 'el-icon-s-order'
            }, {
			    "id": '8-2',
			    "path": "/order/invoice",
			    "label": "发票信息",
			    'icon': 'el-icon-s-order'
            }, {
			    "id": '8-3',
			    "path": "/order/abnormal",
			    "label": "异常定单管理",
			    'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '9',
        "label": "信息管理",
        "path": "/message",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
			    "id": '9-1',
			    "path": "/message/notice",
			    "label": "信息发布通知",
			    'icon': 'el-icon-s-order'
            }
        ]
    }, {
        "id": '10',
        "label": "系统集成",
        "path": "/imploded",
        'icon': 'el-icon-s-promotion',
        "children":[
            {
			    "id": '10-1',
			    "path": "/imploded/iot",
			    "label": "物联网集成",
			    'icon': 'el-icon-s-order'
            }, {
			    "id": '10-2',
			    "path": "/imploded/sys",
			    "label": "系统集成",
			    'icon': 'el-icon-s-order'
            }
        ]
    }
]


export default menuList;