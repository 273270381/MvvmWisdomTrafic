package com.suchness.mvvmwisdomtrafic.binding;

import androidx.databinding.BindingAdapter;

import com.suchness.mvvmwisdomtrafic.view.ScrollItemView;

import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * @Author hejunfeng
 * @Date 19:32 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.binding
 **/
public class ScrollItemViewBindAdapter {
    @BindingAdapter(value = "onShare", requireAll = false)
    public static void onShare(ScrollItemView scrollItemView, final BindingCommand command){
        scrollItemView.setShareClickListener(new ScrollItemView.ScrollOnClickListener() {
            @Override
            public void click() {
                if (command != null){
                    command.execute();
                }
            }
        });
    }

    @BindingAdapter(value = "onDelete", requireAll = false)
    public static void onDelete(ScrollItemView scrollItemView, final BindingCommand command){
        scrollItemView.setDeleteClickListener(new ScrollItemView.ScrollOnClickListener() {
            @Override
            public void click() {
                if (command != null){
                    command.execute();
                }
            }
        });
    }
}
