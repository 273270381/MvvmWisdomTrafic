package com.suchness.mvvmwisdomtrafic.ui.file;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.suchness.mvvmwisdomtrafic.BR;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.adapter.FragmentPagerStateAdapter;
import com.suchness.mvvmwisdomtrafic.app.AppConfig;
import com.suchness.mvvmwisdomtrafic.databinding.FragmentFileManagerBinding;
import com.suchness.mvvmwisdomtrafic.ui.file.pic.PicFragment;
import com.suchness.mvvmwisdomtrafic.ui.file.pic.TestFragment;

import java.util.ArrayList;
import java.util.List;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * @Author hejunfeng
 * @Date 15:36 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.ui.file
 **/
public class FileManagerFragment extends BaseFragment<FragmentFileManagerBinding,FileManagerViewModel> {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private FragmentStateAdapter adapter;
    private TabLayoutMediator mediator;
    private ViewPager2 viewPager2;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_file_manager;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        String cameraName = getArguments().getString("cameraName");
        PicFragment fragment = new PicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", AppConfig.DEFAULT_SAVE_IMAGE_PATH+cameraName);
        fragment.setArguments(bundle);
        getLifecycle().addObserver(fragment);
        fragmentList.add(fragment);

        PicFragment fragment2 = new PicFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("path", AppConfig.DEFAULT_SAVE_VIDEO_PATH+cameraName);
        fragment2.setArguments(bundle2);
        getLifecycle().addObserver(fragment2);
        fragmentList.add(fragment2);

        titles.add("图片");
        titles.add("视频");
    }

    @Override
    public void initData() {
        viewPager2 = binding.viewPager;
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        adapter = new FragmentStateAdapter(getChildFragmentManager(),getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        };
        viewPager2.setAdapter(adapter);


        mediator = new TabLayoutMediator(binding.tabs, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
            }
        });
        mediator.attach();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mediator.detach();
        viewPager2 = null;
        mediator = null;
        adapter = null;
    }
}
