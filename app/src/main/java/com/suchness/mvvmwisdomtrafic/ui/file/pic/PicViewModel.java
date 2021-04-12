package com.suchness.mvvmwisdomtrafic.ui.file.pic;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.suchness.mvvmwisdomtrafic.entity.ItemData;
import com.suchness.mvvmwisdomtrafic.entity.RootData;
import com.suchness.mvvmwisdomtrafic.ui.file.viewpic.ViewPicFragment;
import com.suchness.mvvmwisdomtrafic.utils.AppOperator;
import com.suchness.mvvmwisdomtrafic.utils.DataUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.suchness.mvvmwisdomtrafic.utils.DataUtils.removeDuplicateWithOrder;

/**
 * @Author hejunfeng
 * @Date 16:39 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.ui.file.video
 **/
public class PicViewModel extends BaseViewModel {

    public SingleLiveEvent dataListEvent = new SingleLiveEvent();

    public SingleLiveEvent shareFileEvent = new SingleLiveEvent();

    public SingleLiveEvent deleteFileEvent = new SingleLiveEvent();

    public PicViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand onShare = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            shareFileEvent.call();
        }
    });


    public BindingCommand onDelete = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            deleteFileEvent.call();
        }
    });

    public void onDetail(String url,String path){
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("path",path);
        startContainerActivity(ViewPicFragment.class.getCanonicalName(),bundle);
    }

    public void queryPath(String path){
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                List<String> pathList = DataUtils.getImagePathFromSD(path);
                List<String> dateList = removeDuplicateWithOrder(DataUtils.getFileTime(pathList));
                List<BaseNode> list = new ArrayList<>();
                for (String dataStr : dateList){
                    List<BaseNode> items = new ArrayList<>();
                    for (String pathStr : pathList){
                        long time = new File(pathStr).lastModified();
                        String date = format.format(time);
                        if (dataStr.equals(date)){
                            ItemData itemData = new ItemData(pathStr);
                            items.add(itemData);
                        }
                    }
                    RootData rootData = new RootData(items,dataStr);
                    list.add(rootData);
                }
                dataListEvent.postValue(list);
            }
        });
    }
}
