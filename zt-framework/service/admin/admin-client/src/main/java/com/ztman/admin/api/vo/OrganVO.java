package com.ztman.admin.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrganVO implements Serializable {
    private Integer id;
    private String name;
    private String label;
    private String type;
    private List<ServiceVO> children = new ArrayList<>();
}
