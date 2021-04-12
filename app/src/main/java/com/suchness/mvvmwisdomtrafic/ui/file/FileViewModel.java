package com.suchness.mvvmwisdomtrafic.ui.file;

import android.app.Application;

import androidx.annotation.NonNull;

import com.suchness.mvvmwisdomtrafic.app.AppApplication;
import com.suchness.mvvmwisdomtrafic.utils.AppOperator;
import com.videogo.exception.BaseException;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @Author hejunfeng
 * @Date 20:17 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.ui.file
 **/
public class FileViewModel extends BaseViewModel {

    public SingleLiveEvent listCameras = new SingleLiveEvent();

    public FileViewModel(@NonNull Application application) {
        super(application);
    }

    public void query(){
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<EZDeviceInfo> list_ezdevices = AppApplication.getOpenSDK().getDeviceList(0,40);
                    if (list_ezdevices.size() != 0){
                        List<EZCameraInfo> cameraInfos = new ArrayList<>();
                        for (EZDeviceInfo ezDeviceInfo : list_ezdevices){
                            for (EZCameraInfo cameraInfo : ezDeviceInfo.getCameraInfoList()){
                                if (!cameraInfo.getCameraName().contains("未接入")){
                                    cameraInfos.add(cameraInfo);
                                }
                            }
                        }
                        listCameras.postValue(cameraInfos);
                    }
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
