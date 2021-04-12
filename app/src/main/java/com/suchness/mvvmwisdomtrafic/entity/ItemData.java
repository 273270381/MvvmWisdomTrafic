package com.suchness.mvvmwisdomtrafic.entity;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 17:09 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.entity
 **/
public class ItemData extends BaseNode {
    private String path;

    public ItemData(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
