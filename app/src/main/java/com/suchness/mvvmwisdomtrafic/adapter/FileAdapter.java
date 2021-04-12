package com.suchness.mvvmwisdomtrafic.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.databinding.ScanPicItemBinding;
import com.videogo.openapi.bean.EZCameraInfo;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hejunfeng
 * @Date 15:55 2021/4/7 0007
 * @Description com.suchness.mvvmwisdomtrafic.adapter
 **/
public class FileAdapter extends BaseQuickAdapter<EZCameraInfo, BaseDataBindingHolder<ScanPicItemBinding>> {
    public FileAdapter() {
        super(R.layout.scan_pic_item);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ScanPicItemBinding> holder, EZCameraInfo ezCameraInfo) {
        ScanPicItemBinding binding = holder.getDataBinding();
        if (binding != null){
            binding.setCameraInfo(ezCameraInfo);
        }
    }
}
