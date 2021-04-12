package com.suchness.mvvmwisdomtrafic.ui.monitor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.suchness.mvvmwisdomtrafic.BR;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.databinding.FragmentMonitorBinding;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @Author hejunfeng
 * @Date 20:08 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.ui.monitor
 **/
public class MonitorFragment extends BaseFragment<FragmentMonitorBinding,MonitorViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_monitor;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
