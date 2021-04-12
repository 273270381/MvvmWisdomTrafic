package com.suchness.mvvmwisdomtrafic.ui.file.pic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.suchness.mvvmwisdomtrafic.BR;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.databinding.FragmentPicBinding;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @Author hejunfeng
 * @Date 15:24 2021/4/12 0012
 * @Description com.suchness.mvvmwisdomtrafic.ui.file.pic
 **/
public class TestFragment extends BaseFragment<FragmentPicBinding,PicViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_pic;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
