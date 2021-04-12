package com.suchness.mvvmwisdomtrafic.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @Author hejunfeng
 * @Date 11:40 2021/4/12 0012
 * @Description com.suchness.mvvmwisdomtrafic.adapter
 **/
@SuppressLint("RestrictedApi")
public interface LifecycleAdapter extends LifecycleEventObserver {
    @Override
    void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event);
}
