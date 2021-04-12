package com.suchness.mvvmwisdomtrafic.ui.file.provider;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.entity.ItemData;
import com.suchness.mvvmwisdomtrafic.utils.MyVideoThumbLoader;
import com.suchness.mvvmwisdomtrafic.view.MyImageView;
import com.suchness.mvvmwisdomtrafic.view.SmoothCheckBox;

import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author hejunfeng
 * @Date 15:11 2021/3/26 0026
 * @Description com.analysis.wisdomtraffic.ui.filemanage.provider
 **/
public class SecondNodeProvider extends BaseNodeProvider {
    private LongClickCallBack longClickCallBack;
    private ClickCallBack clickCallBack;
    private boolean showCheckBox;
    private MyVideoThumbLoader mVideoThumbLoader;
    private Map<String,Boolean> map = new HashMap<>();

    public SecondNodeProvider() {
        mVideoThumbLoader = new MyVideoThumbLoader(context);
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_section_content;
    }

    public void setLongClickCallBack(LongClickCallBack longClickCallBack){
        this.longClickCallBack = longClickCallBack;
    }

    public void setClickCallBack(ClickCallBack clickCallBack){
        this.clickCallBack = clickCallBack;
    }

    public void setShowCheckBox(boolean showCheckBox){
        this.showCheckBox = showCheckBox;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        if (baseNode == null){
            return;
        }
        ItemData data = (ItemData)baseNode;
        String fileEnd = data.getPath().substring(data.getPath().lastIndexOf(".")+1,data.getPath().length()).toLowerCase();
        if (fileEnd.equals("mp4")){
            mVideoThumbLoader.showThumbByAsynctack(data.getPath(),(MyImageView) baseViewHolder.getView(R.id.iv),600,400);
            baseViewHolder.setVisible(R.id.videoFlag,true);
        }else{
            RoundedCorners roundedCorners = new RoundedCorners(16);//数字为圆角度数
            RequestOptions coverRequestOptions = new RequestOptions()
                    .transforms(new CenterCrop(),roundedCorners)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                    .skipMemoryCache(true);//不做内存缓存

            Glide.with(context).asBitmap().load(new File(data.getPath())).apply(coverRequestOptions).into((MyImageView) baseViewHolder.getView(R.id.iv));
            baseViewHolder.setVisible(R.id.videoFlag,false);
        }

        ((SmoothCheckBox) baseViewHolder.getView(R.id.cb_item)).setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked){
                    map.put(data.getPath(),true);
                }else{
                    map.remove(data.getPath());
                }
            }
        });

        if (showCheckBox){
            baseViewHolder.setVisible(R.id.cb_item,true);
        }else{
            baseViewHolder.setVisible(R.id.cb_item,false);
        }

        if (map != null && map.containsKey(data.getPath())){
            ((SmoothCheckBox)baseViewHolder.getView(R.id.cb_item)).setChecked(true);
        }else{
            ((SmoothCheckBox)baseViewHolder.getView(R.id.cb_item)).setChecked(false);
        }
    }

    public List<String> getMap(){
        List<String> urls = new ArrayList<>();
        Iterator<Map.Entry<String,Boolean>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Boolean> entry = iterator.next();
            if (entry.getValue() != null){
                urls.add(entry.getKey());
            }
        }
        return urls;
    }

    public void clearMap(){
        map.clear();
    }

    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        super.onChildClick(helper, view, data, position);
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        if (!showCheckBox){
            clickCallBack.click(((ItemData)((ItemData) data)).getPath());
        }else{
            ((SmoothCheckBox)helper.getView(R.id.cb_item)).toggle();
        }
        super.onClick(helper, view, data, position);
    }

    @Override
    public boolean onLongClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        longClickCallBack.showCheckBox();
        longClickCallBack.addFooter();
        return true;
    }

}
