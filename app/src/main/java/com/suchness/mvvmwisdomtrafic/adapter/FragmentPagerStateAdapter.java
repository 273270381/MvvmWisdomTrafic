package com.suchness.mvvmwisdomtrafic.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.suchness.mvvmwisdomtrafic.ui.file.pic.PicFragment;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 14:12 2021/4/10 0010
 * @Description com.suchness.mvvmwisdomtrafic.adapter
 **/
public class FragmentPagerStateAdapter extends FragmentStateAdapter {
    private List<Fragment> list;
    private volatile static FragmentPagerStateAdapter adapter = null;

    public FragmentPagerStateAdapter(@NonNull Fragment fragment,List<Fragment> fragments) {
        super(fragment);
        this.list = fragments;
    }

    public static FragmentPagerStateAdapter getInstance(Fragment fragment,List<Fragment> fragments,List<String> titles){
        if (adapter == null){
            synchronized (FragmentPagerStateAdapter.class){
                if (adapter == null){
                    adapter = new FragmentPagerStateAdapter(fragment,fragments);
                }
            }
        }
        return adapter;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
