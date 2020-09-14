package com.ztman.common.core.util;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：spark
 * 类 名 称：CheckFieldUtil
 * 类 描 述：TODO
 * 创建时间：2020/8/25 14:05
 * 创 建 人：纪星亮
 */
public class CheckFieldUtil {

    /***
     * 解析验证参数错误
     * @param errors  错误信息，它由Spring MVC通过验证POJO后自动填充
     * @return 错误信息Map
     */
    public static Map<String, Object> validate(Errors errors) {
        Map<String, Object> errMap = new HashMap<>();
        // 获取错误列表
        List<ObjectError> oes = errors.getAllErrors();
        for (ObjectError oe : oes) {
            String key = null;
            String msg = null;
            // 字段错误
            if (oe instanceof FieldError) {
                FieldError fe = (FieldError) oe;
                key = fe.getField();// 获取错误验证字段名
            } else {
                // 非字段错误
                key = oe.getObjectName();// 获取验证对象名称
            }

            // 错误信息
            msg = oe.getDefaultMessage();
            errMap.put(key, msg);
        }
        return errMap;
    }
}