package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 项目名称：testQRcode
 * 类 名 称：CachAspectJxl
 * 类 描 述：TODO
 * 创建时间：2020/8/13 16:00
 * 创 建 人：纪星亮
 */
@Aspect
@Component
public class CachAspectJxl {

    @Around("@annotation(caj)")
    public Object doAround(ProceedingJoinPoint pjp, CachAndLogJxl caj) throws Throwable {
        System.out.println("执行前。。。。");

        String key = getkey(caj.key(), pjp);
        String name = caj.name();
        boolean cach = caj.isCach();
        System.out.println("key=" + key);
        System.out.println("name=" + name);
        System.out.println("cach=" + cach);

        Object proceed = pjp.proceed();
        System.out.println("执行后。。。。");

        return null;
    }

    private String getkey(String key, ProceedingJoinPoint pjp) {
        Method method = ((MethodSignature)(pjp.getSignature())).getMethod();
        Parameter[] parameters = method.getParameters();
        String[] parameterNames = new String[parameters.length];
        for (int i = 0; i < parameters.length; i ++) {
            System.out.println("parameters["+i+"]=" + parameters[i].getName());
            parameterNames[i] = parameters[i].getName();
        }
        return ElSetClass.getkey(key, parameterNames, pjp.getArgs());
    }
}