package com.suchness.mvvmwisdomtrafic.ui.login;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.suchness.mvvmwisdomtrafic.BR;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.app.AppViewModelFactory;
import com.suchness.mvvmwisdomtrafic.databinding.ActivityLoginBinding;
import com.suchness.mvvmwisdomtrafic.ui.ShareViewModel;
import com.videogo.openapi.EZOpenSDK;
import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * @Author hejunfeng
 * @Date 14:35 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.ui
 **/
public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginViewModel> {
    private ShareViewModel shareViewModel;
    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限
    private static String[] allpermissions = {
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.VIBRATE,
            Manifest.permission.CAMERA,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        shareViewModel = getApplicationViewModel(ShareViewModel.class);

        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initViewObservable() {
        viewModel.enterSuccess.observe(this,o -> {
            viewModel.getAccessToken();
        });

        viewModel.setAccessToken.observe(this,o -> {
            Intent intent = new Intent();
            intent.setAction("com.action.OAUTH_SUCCESS_ACTION_TRAFFIC2");
            EZOpenSDK.getInstance().setAccessToken((String) o);
            sendBroadcast(intent);
            finish();
        });
    }
}
