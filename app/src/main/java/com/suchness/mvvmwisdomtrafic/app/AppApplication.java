package com.suchness.mvvmwisdomtrafic.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.ui.home.HomeActivity;
import com.suchness.mvvmwisdomtrafic.ui.login.LoginActivity;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.util.LogUtil;

import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.utils.KLog;

import static com.suchness.mvvmwisdomtrafic.app.AppConfig.AppKey;

/**
 * @Author hejunfeng
 * @Date 14:16 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic
 **/
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //是否开启打印日志
        //KLog.init(BuildConfig.DEBUG);
        KLog.init(true);
        //初始化全局异常崩溃
        initCrash();
        //内存泄漏检测
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }
        initSDK();
    }

    private void initSDK() {
        /**
         * sdk日志开关，正式发布需要去掉
         */
        EZOpenSDK.showSDKLog(true);
        /**
         * 设置是否支持P2P取流,详见api
         */
        EZOpenSDK.enableP2P(true);
        /**
         * APP_KEY请替换成自己申请的
         */
        EZOpenSDK.initLib(this, AppKey);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.videogo.action.ADD_DEVICE_SUCCESS_ACTION");
        intentFilter.addAction("com.action.OAUTH_SUCCESS_ACTION_TRAFFIC2");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        EzvizBroadcastReceiver receiver = new EzvizBroadcastReceiver();
        registerReceiver(receiver,intentFilter);
    }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(LoginActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }
    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }
    private class EzvizBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtil.i("TAG","action = "+action);
            //if (action.equals(Constant.OAUTH_SUCCESS_ACTION)){
            if (action.equals("com.action.OAUTH_SUCCESS_ACTION_TRAFFIC2")){
                Log.i("TAG", "onReceive: OAUTH_SUCCESS_ACTION_TRAFFIC");
                Intent i = new Intent(context, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                /*******   获取登录成功之后的EZAccessToken对象   *****/
                context.startActivity(i);
            }
        }
    }
}
