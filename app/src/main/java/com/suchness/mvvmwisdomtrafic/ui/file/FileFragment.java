package com.suchness.mvvmwisdomtrafic.ui.file;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.suchness.mvvmwisdomtrafic.BR;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.adapter.FileAdapter;
import com.suchness.mvvmwisdomtrafic.databinding.FragmentFileBinding;
import com.videogo.openapi.bean.EZCameraInfo;
import java.util.Collection;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.RequestCallback;

/**
 * @Author hejunfeng
 * @Date 20:08 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.ui.filemanager
 **/
public class FileFragment extends BaseFragment<FragmentFileBinding,FileViewModel> {
    private FileAdapter mAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_file;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        mAdapter = new FileAdapter();
        mAdapter.setAnimationEnable(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rv.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));
        mAdapter.setAnimationEnable(true);
        binding.rv.setAdapter(mAdapter);
        queryData();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("cameraName",mAdapter.getData().get(position).getCameraName());
                nav().navigate(R.id.action_fileFragment_to_fileManagerFragment,bundle);
            }
        });
    }

    private void queryData() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermission(new RequestCallback() {
            @Override
            public void agreed() {
                viewModel.query();
            }
        },permissions);
    }

    @Override
    public void initViewObservable() {
        viewModel.listCameras.observe(this, o -> {
            mAdapter.setList((Collection<? extends EZCameraInfo>) o);
        });
    }

    @Override
    public void onDestroyView() {
        mAdapter = null;
        super.onDestroyView();
    }
}
