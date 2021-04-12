package com.suchness.mvvmwisdomtrafic.ui.file.viewpic;

import android.app.Application;
import android.os.Message;

import androidx.annotation.NonNull;

import com.suchness.mvvmwisdomtrafic.utils.AppOperator;
import com.suchness.mvvmwisdomtrafic.utils.DataUtils;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * @Author hejunfeng
 * @Date 20:17 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.ui.file.viewpic
 **/
public class ViewPicViewModel extends BaseViewModel {

    public SingleLiveEvent urlsEvent = new SingleLiveEvent();
    public SingleLiveEvent shareEvent = new SingleLiveEvent();
    public SingleLiveEvent deleteEvent = new SingleLiveEvent();

    public ViewPicViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand share = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            shareEvent.call();
        }
    });

    public BindingCommand delete = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            deleteEvent.call();
        }
    });

    public void queryUrls(String path,String url){
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                List<String> urls = DataUtils.getImagePathFromSD(path);
                int position = urls.lastIndexOf(url);
                KLog.d("hjf","position :"+position);
                UrlItems urlItems = new UrlItems(urls,position);
                urlsEvent.postValue(urlItems);
            }
        });
    }
}
