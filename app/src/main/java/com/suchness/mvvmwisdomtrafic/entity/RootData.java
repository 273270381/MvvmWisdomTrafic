package com.suchness.mvvmwisdomtrafic.entity;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.entity.node.NodeFooterImp;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 17:05 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.entity
 **/
public class RootData extends BaseExpandNode implements NodeFooterImp {
    private List<BaseNode> childData;
    private String title;

    public RootData(List<BaseNode> childData, String title) {
        this.childData = childData;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setChildData(List<BaseNode> childData) {
        this.childData = childData;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    @Override



    public List<BaseNode> getChildNode() {
        return childData;
    }

    @Nullable
    @Override
    public BaseNode getFooterNode() {
        return null;
    }
}
