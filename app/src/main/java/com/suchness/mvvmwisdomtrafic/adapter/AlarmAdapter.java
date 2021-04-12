package com.suchness.mvvmwisdomtrafic.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.data.source.HttpDataSourceImpl;
import com.suchness.mvvmwisdomtrafic.databinding.AlarmItemBinding;
import com.suchness.mvvmwisdomtrafic.entity.AlarmEntity;

import org.jetbrains.annotations.NotNull;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @Author hejunfeng
 * @Date 21:04 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.adapter
 **/
public class AlarmAdapter extends BaseQuickAdapter<AlarmEntity.AlarmMessage, BaseDataBindingHolder<AlarmItemBinding>> implements LoadMoreModule {
    public AlarmAdapter() {
        super(R.layout.alarm_item);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<AlarmItemBinding> holder, AlarmEntity.AlarmMessage msg) {
        AlarmItemBinding binding = holder.getDataBinding();
        if (binding != null){
            binding.setMessage(msg);
            binding.executePendingBindings();
        }
        if (msg.getFinishState() != null && msg.getFinishState() == 1){
            binding.imStatue.setBackgroundResource(R.mipmap.completed);
            binding.ivNew.setVisibility(View.GONE);
        }else{
            if (msg.getHandleState() != null && msg.getHandleState() == 1){
                binding.imStatue.setBackgroundResource(R.mipmap.yichuzhi);
            }else if (msg.getOrderState() != null && msg.getOrderState() == 1){
                binding.imStatue.setBackgroundResource(R.mipmap.yijiedan);
            }else{
                binding.imStatue.setBackgroundResource(R.mipmap.yibaojing);
            }
        }
    }
}
