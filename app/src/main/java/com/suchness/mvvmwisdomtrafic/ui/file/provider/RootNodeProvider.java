package com.suchness.mvvmwisdomtrafic.ui.file.provider;

import android.view.View;


import androidx.lifecycle.LifecycleObserver;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.databinding.DefSectionHeadBinding;
import com.suchness.mvvmwisdomtrafic.entity.RootData;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hejunfeng
 * @Date 17:03 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.ui.file.provide
 **/
public class RootNodeProvider extends BaseNodeProvider {
    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.def_section_head;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        RootData data = (RootData)baseNode;
        baseViewHolder.setText(R.id.header,data.getTitle());
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        getAdapter().expandOrCollapse(position);
    }

}
