package com.ztman.admin.api.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class OrganTenantVO implements Serializable {
    private Integer organId;
    private Integer serviceId;
    private String organName;
    private String serviceName;
    private Integer administrativeOrganId;
}
