package com.suchness.mvvmwisdomtrafic.ui.file.viewpic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import com.suchness.mvvmwisdomtrafic.BR;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.adapter.PhotoPagerAdapter;
import com.suchness.mvvmwisdomtrafic.databinding.FragmentViewPicBinding;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;
/**
 * @Author hejunfeng
 * @Date 20:15 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.ui.file
 **/
public class ViewPicFragment extends BaseFragment<FragmentViewPicBinding,ViewPicViewModel> {
    private List<String> urlList = new ArrayList<>();
    private String url;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_view_pic;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        url = getArguments().getString("url");
        String path = getArguments().getString("path");
        viewModel.queryUrls(path,url);
        binding.viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                url = urlList.get(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initViewObservable() {

        viewModel.urlsEvent.observe(this,o -> {
            UrlItems urlItems = (UrlItems) o;
            urlList = urlItems.getUrls();
            PhotoPagerAdapter viewPagerAdapter = new PhotoPagerAdapter(getActivity().getSupportFragmentManager(), (ArrayList<String>) urlList);
            binding.viewpager.setAdapter(viewPagerAdapter);
            binding.viewpager.setCurrentItem(urlItems.getPosition());
        });

        viewModel.shareEvent.observe(this,o -> {
            shareImg("分享","分享","分享",Uri.parse(url));
        });

        viewModel.deleteEvent.observe(this, o -> {
            delete(url);
        });
    }

    /**
     * 分享图片和文字内容
     *
     * @param dlgTitle
     *            分享对话框标题
     * @param subject
     *            主题
     * @param content
     *            分享内容（文字）
     * @param uri
     *            图片资源URI
     */
    private void shareImg(String dlgTitle, String subject, String content, Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (content != null && !"".equals(content)) {
            intent.putExtra(Intent.EXTRA_TEXT, content);
        }
        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            startActivity(intent);
        }
    }

    private void delete(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            file.delete();
            ToastUtils.showShortSafe("文件已删除");
            getActivity().finish();
        }
    }
}
