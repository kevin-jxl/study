package com.ztman.admin.api.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class UserTreeNode {
    protected int id;
    private int value;
    protected int parentId;
    protected boolean checked = false;
    protected List<UserTreeNode> children;

    public void add(UserTreeNode node) {
        if (this.children == null) {
            children = new ArrayList<UserTreeNode>();
        }
        children.add(node);
    }

    public void setId(int id) {
        this.id = id;
        this.value = id;
    }

}
