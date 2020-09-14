package com.ztman.admin.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 部门树
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class DictTree extends TreeNode {
    private String name;
    private String label;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.label = name;
    }
}
