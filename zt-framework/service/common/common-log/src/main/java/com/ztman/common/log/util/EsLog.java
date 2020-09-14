package com.ztman.common.log.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志存储体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsLog {
    //用户ID
    private String userId;
    //用户名称
    private String userName;
    //用户员工号
    private String userEmpNo;
    //用户部门ID
    private String userDeptId;
    //IP
    private String ip;
    //请求地址
    private String url;
    //方法类别（GET、POST、DELETE）
    private String methods;
    //请求参数
    private String params;
    //请求地址+端口
    private String address;
    //来源服务
    private String serviceName;
    //日志日期
    private String logDate;
    //请求来源客户端
    private String userAgent;
    //日志标识
    private String tag;
    //原始sql
    private String originalSql;
    //sql类型
    private String sqlCommandType;

}
