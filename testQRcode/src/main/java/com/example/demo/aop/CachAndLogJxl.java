package com.example.demo.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称：testQRcode
 * 类 名 称：CachAndLogJxl
 * 类 描 述：TODO
 * 创建时间：2020/8/13 16:28
 * 创 建 人：纪星亮
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CachAndLogJxl {

    String key();

    String name();

    boolean isCach() default false;
}