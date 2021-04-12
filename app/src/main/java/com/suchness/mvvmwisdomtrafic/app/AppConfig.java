package com.suchness.mvvmwisdomtrafic.app;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @Author hejunfeng
 * @Date 18:59 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.app
 **/
public class AppConfig {
    public static final String service_url = "http://183.208.120.226:18080/";//西城大厦
    public static final String service_url_wangchan = "http://172.16.14.11:6045/";
    public static final String service_url_com = "http://222.187.219.82:6045/";
    public static final String AppKey = "31aee838377e49afb1e7983532184e9a";
    public static final String Secret = "e6239d553823dd17cd5048ebaa08b44f";
    public static final String DEVICE_SERIAL_NUM = "F25374503";
    private static AppConfig appConfig;
    private Context mContext;

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }

    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "wisdomtraffic"
            + File.separator + "CapturePicture" + File.separator;

    // 默认存放视频的路径
    public final static String DEFAULT_SAVE_VIDEO_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "wisdomtraffic"
            + File.separator + "CaptureVideo" + File.separator;
}
