package com.suchness.mvvmwisdomtrafic.ui.file.photo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @Author hejunfeng
 * @Date 20:58 2021/4/9 0009
 * @Description com.suchness.mvvmwisdomtrafic.ui.file.photo
 **/
public class PhotoViewModel extends BaseViewModel {
    public SingleLiveEvent playEvent = new SingleLiveEvent();

    public ObservableField<Boolean> b = new ObservableField<>(false);

    public PhotoViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand play = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            playEvent.call();
        }
    });
}
