package com.ztman.admin.api.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceVO implements Serializable {
    private Integer id;
    private String name;
    private String label;
    private String type;
}
