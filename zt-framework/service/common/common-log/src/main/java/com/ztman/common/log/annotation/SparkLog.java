package com.ztman.common.log.annotation;

import com.ztman.common.log.enums.BusinessType;
import com.ztman.common.log.enums.OperatorType;
import java.lang.annotation.*;

/**
 * spark平台自定义日志注解
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SparkLog {
    //功能模块、描述、日志类别（登陆、业务操作）、操作人类别（管理员、普通用户）、是否保留参数
    /**
     * 功能模块
     */
    String title() default "";

    /**
     * 功能描述
     * @return
     */
    String desc() default "";

    /**
     * 操作类别
     * @return
     */
    BusinessType businessType() default BusinessType.OTHER;
    /**
     * 操作人类别
     * @return
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;
}
