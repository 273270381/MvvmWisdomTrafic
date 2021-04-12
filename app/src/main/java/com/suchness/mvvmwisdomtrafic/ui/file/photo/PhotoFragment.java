package com.suchness.mvvmwisdomtrafic.ui.file.photo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.suchness.mvvmwisdomtrafic.BR;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.databinding.FragmentImgBinding;
import java.io.File;
import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @Author hejunfeng
 * @Date 20:54 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.ui.file
 **/
public class PhotoFragment extends BaseFragment<FragmentImgBinding,PhotoViewModel> {
    private String url;
    private String UrlEnd;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_img;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    /**
     * 获取这个fragment需要展示图片的url
     * @param url
     * @return
     */
    public static PhotoFragment newInstance(String url) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        url = getArguments().getString("url");
        UrlEnd = url.substring(url.lastIndexOf(".") + 1, url.length()).toLowerCase();
        //设置控制条
        MediaController mediaController = new MediaController(getContext());
        binding.videoview.setMediaController(mediaController);
        mediaController.setMediaPlayer(binding.videoview);
        mediaController.show();
        init();
    }

    @Override
    public void initViewObservable() {
        viewModel.playEvent.observe(this, o -> {
            binding.videoview.setClickable(true);
            binding.videoview.start();
            viewModel.b.set(true);
            binding.videoview.setVisibility(View.VISIBLE);
            binding.videoplay.setVisibility(View.GONE);
            binding.photoview.setVisibility(View.GONE);
        });
    }

    private void init() {
        binding.videoview.setVideoURI(Uri.parse(url));
        binding.photoview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //ToastUtils.showToast(getContext(),"长按事件");
                return true;
            }
        });

        binding.photoview.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                //ToastUtils.showToast(getContext(),"点击事件，真实项目中可关闭activity");
            }
        });


        File file = new File(url);
        if (file.exists()){
            Glide.with(getContext())
                    .load(url)
                    .into(binding.photoview);
        }else{
            String[] urls = url.split("\\.");
            String imgPath = urls[0].substring(0,urls[0].length()-5)+"."+urls[1];
            Glide.with(getContext())
                    .load(imgPath)
                    .into(binding.photoview);
        }

        if(UrlEnd.equals("mp4")){
            binding.videoplay.setVisibility(View.VISIBLE);
        }
        binding.videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                binding.videoplay.setVisibility(View.VISIBLE);
            }
        });
    }
}
