package com.suchness.mvvmwisdomtrafic.ui.file.pic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.suchness.mvvmwisdomtrafic.BR;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.adapter.LifecycleAdapter;
import com.suchness.mvvmwisdomtrafic.adapter.LifecycleCallBack;
import com.suchness.mvvmwisdomtrafic.adapter.PicNodeAdapter;
import com.suchness.mvvmwisdomtrafic.databinding.FragmentPicBinding;
import com.suchness.mvvmwisdomtrafic.ui.file.FileManagerFragment;
import com.suchness.mvvmwisdomtrafic.ui.file.provider.ClickCallBack;
import com.suchness.mvvmwisdomtrafic.ui.file.provider.LongClickCallBack;
import com.suchness.mvvmwisdomtrafic.ui.file.provider.RootNodeProvider;
import com.suchness.mvvmwisdomtrafic.ui.file.provider.SecondNodeProvider;
import com.suchness.mvvmwisdomtrafic.utils.AnimationUtils;
import com.suchness.mvvmwisdomtrafic.utils.DensityUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @Author hejunfeng
 * @Date 16:38 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.ui.file.video
 **/
public class PicFragment extends BaseFragment<FragmentPicBinding,PicViewModel> implements LifecycleEventObserver {
    private PicNodeAdapter mAdapter;
    private SecondNodeProvider secondNodeProvider;
    private RootNodeProvider rootNodeProvider;
    private int height;
    private List<String> checkList = new ArrayList<>();
    private boolean isShowCheck = false;
    private String path;
    private RecyclerView recyclerView;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_pic;
    }

    /**
     * @return com.suchness.mvvmwisdomtrafic.ui.file.pic.PicFragment
     * @Author hejunfeng
     * @Date 16:21 2021/4/12 0012
     * @Param [path, owner]
     * @Description static内存泄露
     **/
    public static PicFragment newInstance(String path,LifecycleOwner owner){
        PicFragment fragment = new PicFragment();
        owner.getLifecycle().addObserver(fragment);
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    
    @Override
    public void initData() {
        recyclerView = binding.recyclerView;
        height = DensityUtil.dip2px(getActivity(),85);
        binding.footLayout.scrollTo(0,-height);
        path = getArguments().getString("path");
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mAdapter = new PicNodeAdapter();
        secondNodeProvider = new SecondNodeProvider();
        rootNodeProvider = new RootNodeProvider();
        mAdapter.setNodeProvider(rootNodeProvider,secondNodeProvider);
        recyclerView.setAdapter(mAdapter);
        viewModel.queryPath(path);
        iniListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.queryPath(path);
    }

    private void iniListener() {

        secondNodeProvider.setClickCallBack(new ClickCallBack() {
            @Override
            public void click(String s) {
                viewModel.onDetail(s,path);
            }
        });

        secondNodeProvider.setLongClickCallBack(new LongClickCallBack() {
            @Override
            public void showCheckBox() {
                if (!isShowCheck){
                    secondNodeProvider.setShowCheckBox(true);
                }else{
                    secondNodeProvider.setShowCheckBox(false);
                }
                isShowCheck = !isShowCheck;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void addFooter() {
                if (isShowCheck){
                    binding.footLayout.ScrollTo(0,height);
                    AnimationUtils.startZoomAnim(recyclerView,recyclerView.getHeight()-height);
                }else{
                    binding.footLayout.ScrollTo(0,-height);
                    AnimationUtils.startZoomAnim(recyclerView,recyclerView.getHeight()+height);
                }
                checkList.clear();
                secondNodeProvider.clearMap();
            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.dataListEvent.observe(this, o -> {
            mAdapter.setList((Collection<? extends BaseNode>) o);
            recyclerView.scheduleLayoutAnimation();
        });

        viewModel.shareFileEvent.observe(this, o -> {
            checkList = secondNodeProvider.getMap();
            senfiles("分享",checkList);
        });

        viewModel.deleteFileEvent.observe(this, o -> {
            checkList = secondNodeProvider.getMap();
            deleteFile(checkList);
        });
    }


    /**
     * 文件发送
     * @param dlgTitle
     * @param urls
     */
    public void senfiles( String dlgTitle, List<String> urls) {
        ArrayList<Uri> files = new ArrayList<>();
        if (urls.size() > 0){
            for (String url : urls){
                Uri uri = Uri.parse(url);
                files.add(uri);
            }
            if (files.size() == 0) {
                return;
            }
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            intent.setType("*/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            // 设置弹出框标题
            // 自定义标题
            if (dlgTitle != null && !"".equals(dlgTitle)) {
                startActivity(Intent.createChooser(intent, dlgTitle));
            } else { // 系统默认标题
                startActivity(intent);
            }
        }else{
            ToastUtils.showShortSafe("请选择文件");
        }
    }


    /***
     * @return void
     * @Author hejunfeng
     * @Date 20:11 2021/4/9 0009
     * @Param [urls]
     * @Description 文件删除
     **/
    private void deleteFile(List<String> urls){
        if (urls.size() > 0){
            for (String url : urls){
                File file = new File(url);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            }
            isShowCheck = false;
            secondNodeProvider.setShowCheckBox(false);
            binding.footLayout.ScrollTo(0,-height);
            AnimationUtils.startZoomAnim(recyclerView,recyclerView.getHeight()+height);
            ToastUtils.showShortSafe("删除成功");
            viewModel.queryPath(path);
            mAdapter.notifyDataSetChanged();
        }else{
            ToastUtils.showShortSafe("请选择文件");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_STOP){
            if (viewModel != null) {
                viewModel.removeRxBus();
                viewModel = null;
            }
            if (binding != null) {
                binding.unbind();
                binding = null;
            }
            recyclerView = null;
            rootNodeProvider = null;
            secondNodeProvider = null;
            mAdapter = null;
        }
    }
}
