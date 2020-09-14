package com.ztman.admin.api.entity;


import lombok.Data;

import java.math.BigInteger;

/**
 * 任务与触发器
 */
@Data
public class JobAndTrigger {
	private String jobName; //任务名称
	private String jobGroup; //任务分组
	private String jobClassName; //任务执行类名
	private String description;//任务名称
	private String triggerName; // 触发器名称
	private String triggerGroup; //触发器分组
	private BigInteger  repeatInterval;
	private BigInteger timesTriggered;
	private String cronExpression; //任务执行表达式
	private String timeZoneId; //时区
	private long startTime ; //任务开始时间
	private long  endTime; //任务结束时间
	private long prevFireTime;//上次执行时间
	private long nextFireTime;//下次执行时间
	private String triggerState ;//当前任务状态
	private int id; //告警配置主键
	private int isAlarm; //是否失败告警
	private String alarmPhones;// 告警电话
	private String alarmMails; //告警邮箱
}
