package com.ztman.admin.api.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class TreeNode {
    protected int id;
    private int value;
    protected int parentId;
    protected String types;
    protected boolean checked = false;
    protected List<TreeNode> children;

    public void add(TreeNode node) {
        if (this.children == null) {
            children = new ArrayList<TreeNode>();
        }
        children.add(node);
    }

    public void setId(int id) {
        this.id = id;
        this.value = id;
    }

}
