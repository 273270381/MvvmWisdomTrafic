package com.suchness.mvvmwisdomtrafic.ui.splach;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.ui.login.LoginActivity;

/**
 * @Author hejunfeng
 * @Date 14:32 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.ui
 **/
public class SplashActivity extends FragmentActivity {
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        handler = new Handler();
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                finish();
            }
        },500);

    }
}
