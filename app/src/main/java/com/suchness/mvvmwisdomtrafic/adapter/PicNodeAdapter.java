package com.suchness.mvvmwisdomtrafic.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.suchness.mvvmwisdomtrafic.entity.ItemData;
import com.suchness.mvvmwisdomtrafic.entity.RootData;
import com.suchness.mvvmwisdomtrafic.ui.file.provider.RootNodeProvider;
import com.suchness.mvvmwisdomtrafic.ui.file.provider.SecondNodeProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 17:00 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.adapter
 **/
public class PicNodeAdapter extends BaseNodeAdapter{

    public void setNodeProvider(RootNodeProvider rootNodeProvider,SecondNodeProvider secondNodeProvider){
        addFullSpanNodeProvider(rootNodeProvider);
        addNodeProvider(secondNodeProvider);
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof RootData){
            return 0;
        }else if(node instanceof ItemData){
            return 1;
        }
        return -1;
    }

}
