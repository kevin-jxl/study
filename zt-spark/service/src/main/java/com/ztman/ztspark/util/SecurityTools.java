package com.ztman.ztspark.util;

import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.security.util.SecurityUtils;

public class SecurityTools {
    public static boolean checkROLE_ADMIN(){
        return SecurityUtils.getRoles().contains(CommonConstant.ROLE_ADMIN);
    }
}
