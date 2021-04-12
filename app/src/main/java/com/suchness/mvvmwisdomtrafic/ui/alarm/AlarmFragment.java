package com.suchness.mvvmwisdomtrafic.ui.alarm;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.suchness.mvvmwisdomtrafic.BR;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.adapter.AlarmAdapter;
import com.suchness.mvvmwisdomtrafic.adapter.ImageAdapter;
import com.suchness.mvvmwisdomtrafic.app.AppViewModelFactory;
import com.suchness.mvvmwisdomtrafic.databinding.FragmentAlarmBinding;
import com.suchness.mvvmwisdomtrafic.entity.AlarmEntity;
import com.suchness.mvvmwisdomtrafic.utils.DensityUtil;
import com.suchness.mvvmwisdomtrafic.view.CustomAnimation;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @Author hejunfeng
 * @Date 20:07 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.ui.alarm
 **/
public class AlarmFragment extends BaseFragment<FragmentAlarmBinding,AlarmViewModel> {
    private AlarmAdapter mAdapter;
    private ImageAdapter adapter;
    private List<Map<String ,String>> data = new ArrayList<>();
    private Banner banner;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_alarm;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public AlarmViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(AlarmViewModel.class);
    }

    @Override
    public void initParam() {
        getUrls();
    }

    private void getUrls() {
        try {
            String[] urls = getActivity().getAssets().list("image");
            for (int i = 0 ; i < urls.length ; i++){
                Map<String,String> map = new LinkedHashMap<>();
                String path = "file:///android_asset/image/"+urls[i];
                map.put("url",path);
                if (path.contains("a1")){
                    map.put("title","三台山梨园");
                }else if (path.contains("a2")){
                    map.put("title","项王故里");
                } else if (path.contains("a3")){
                    map.put("title","古黄河公园双塔");
                } else if (path.contains("a4")){
                    map.put("title","洪泽湖湿地");
                } else if (path.contains("a5")){
                    map.put("title","衲田");
                } else if (path.contains("a6")){
                    map.put("title","三台山");
                }
                data.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        mAdapter = new AlarmAdapter();
        mAdapter.setAnimationEnable(true);
        adapter = new ImageAdapter(data,getContext());
        final RecyclerView recyclerView = binding.rvList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        View view = getHeaderView();
        mAdapter.addHeaderView(view);
        recyclerView.setAdapter(mAdapter);
        binding.swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        initLoadMore();
    }

    private void initLoadMore() {
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(()->{
            viewModel.loadMore();
        });
        mAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        mAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        mAdapter.setAdapterAnimation(new CustomAnimation());
    }

    private View getHeaderView(){
        View view = getLayoutInflater().inflate(R.layout.head_view, binding.rvList, false);
        int width = DensityUtil.getDisplayWidth() - DensityUtil.dip2px(getActivity(), 160);
        float height = width / 1.8f;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
        banner = view.findViewById(R.id.banner);
        banner.setLayoutParams(lp);
        banner.addBannerLifecycleObserver(this)
                .setAdapter(adapter)
                .setBannerGalleryEffect(60,10)
                .addPageTransformer(new AlphaPageTransformer())
                .setIndicator(new CircleIndicator(getActivity()))
                .setOnBannerListener((data1, position1) -> {
                }).start();
        banner.isAutoLoop(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.swipeLayout.setRefreshing(true);
        viewModel.refresh();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.swipeLayout.setRefreshing(false);
    }

    @Override
    public void initViewObservable() {
        //监听下拉刷新完成
        viewModel.startRefresh.observe(this, object -> {
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        });

        //刷新完成
        viewModel.finishRefresh.observe(this, o -> {
            binding.swipeLayout.setRefreshing(false);
            mAdapter.getLoadMoreModule().setEnableLoadMore(true);
            AlarmEntity alarmEntity = (AlarmEntity) o;
            List<AlarmEntity.AlarmMessage> messages = alarmEntity.getData().getList();
            if (viewModel.pageInfo.isFirstPage()){
                mAdapter.setList(messages);
            }else{
                mAdapter.addData(messages);
            }
            if (messages.size() < viewModel.pageSize){
                mAdapter.getLoadMoreModule().loadMoreEnd();
            }else{
                mAdapter.getLoadMoreModule().loadMoreComplete();
            }
            viewModel.pageInfo.nextPage();
        });

        //刷新失败
        viewModel.failRefresh.observe(this,o -> {
            binding.swipeLayout.setRefreshing(false);
            mAdapter.getLoadMoreModule().setEnableLoadMore(true);
            mAdapter.getLoadMoreModule().loadMoreFail();
            ToastUtils.showShortSafe("服务器异常");
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        mAdapter.removeAllHeaderView();
        banner.destroy();
        mAdapter = null;
        adapter = null;
        super.onDestroyView();
    }
}
