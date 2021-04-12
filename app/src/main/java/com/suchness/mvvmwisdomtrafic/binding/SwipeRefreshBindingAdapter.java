package com.suchness.mvvmwisdomtrafic.binding;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * @Author hejunfeng
 * @Date 21:30 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.binding
 **/
public class SwipeRefreshBindingAdapter {
    @BindingAdapter(value = "onRefresh",requireAll = false)
    public static void onRefreshAndLoadMoreCommand(SwipeRefreshLayout layout,final BindingCommand onRefreshCommand){
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshCommand != null){
                    onRefreshCommand.execute();
                }
            }
        });
    }
}
