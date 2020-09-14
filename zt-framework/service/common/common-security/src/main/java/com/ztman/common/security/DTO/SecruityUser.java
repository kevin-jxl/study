package com.ztman.common.security.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Lenovo on 2019/4/8.
 */
@Data
@AllArgsConstructor
public class SecruityUser {
    private int id;
    private String userName;
    private String deptId;
    private String companyId;
    private String realName;
    private String empNo;
    private String tenantFlag;
    private String tenantId;
    public SecruityUser(){

    }
}
