# 1、修改hosts文件 （配置其中一个）
 ```  
    - 服务器注册中心(对接测试)
        
    192.168.10.121 server0
    192.168.10.121 server1
    192.168.10.121 server2
    192.168.10.121 zt-gateway
    192.168.10.121 zt-mysql
    192.168.10.121 zt-redis
    - 本地注册中心（本地开发）
    
    127.0.0.1 server0
    127.0.0.1 server1
    127.0.0.1 server2
    127.0.0.1 zt-gateway
    192.168.10.121 zt-mysql
    127.0.0.1 zt-redis

```
# 2 依赖环境

 maven3.5以上、Redis、Node、Mysql5.7以上
 
# 3 启动说明 （如果是服务器配置中心，不需要启动前四个，不开发基础平台admin也不需要启动，只启动自己的业务）
```
    1.eureka
    2.config
    3.auth
    4.gateway
    5.admin
    #若需要代码生成器则需启动
    6.codegen
    

```
   
    
# 4、工程说明
```
 ztman
     ├── eureka -- 服务注册与发现[1001]
     ├── config -- 配置中心[2001]
     ├── auth -- 授权服务提供[3001]
     ├── gateway -- Spring Cloud Gateway网关[4001]
     ├── common -- 系统公共模块 
     ├    ├── common-core -- 公共工具类核心包
     ├    ├── common-data -- 数据权限管理工具
     ├    ├── common-mq -- RocketMQ消息队列
     ├    ├── common-job -- 定时任务
     ├    ├── common-log -- 日志服务
     ├    └── common-security --权限 安全工具类
     ├    └── common-swagger -- Swagger Api文档生成
     ├    └── common-tools -- 常用工具包
     ├── admin -- 通用用户权限管理模块
     ├    └── admin-client -- 通用用户权限管理系统公共api模块
     ├    └── admin-server -- 通用用户权限管理系统业务处理模块[8001]
     ├── business-inspection -- 巡检通
     ├    └── business-inspection-client -- 巡检通公共api模块
     ├    └── business-inspection-server -- 巡检通业务处理模块[9001]
     └── visual  -- 图形化模块 
     ├    └── code-gen -- 图形化代码生成[5003]







```
 