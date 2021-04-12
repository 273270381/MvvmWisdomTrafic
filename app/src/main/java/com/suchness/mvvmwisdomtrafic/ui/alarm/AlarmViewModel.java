package com.suchness.mvvmwisdomtrafic.ui.alarm;

import android.app.Application;
import androidx.annotation.NonNull;
import com.suchness.mvvmwisdomtrafic.adapter.PageInfo;
import com.suchness.mvvmwisdomtrafic.data.Repository;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * @Author hejunfeng
 * @Date 20:15 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.ui.alarm
 **/
public class AlarmViewModel extends BaseViewModel<Repository> {

    public PageInfo pageInfo = new PageInfo();
    public Integer pageSize = 10;

    public SingleLiveEvent startRefresh = new SingleLiveEvent();

    public SingleLiveEvent finishRefresh = new SingleLiveEvent();

    public SingleLiveEvent failRefresh = new SingleLiveEvent();

    public AlarmViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public BindingCommand onRefresh = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            refresh();
        }
    });


    public void refresh() {
        // 下拉刷新，需要重置页数
        startRefresh.call();
        pageInfo.reset();
        request(String.valueOf(pageSize),pageInfo);
    }

    public void loadMore(){
        request(String.valueOf(pageSize),pageInfo);
    }

    public void request(String pageSize, PageInfo pageInfo){
        addSubscribe(model.query(pageSize,String.valueOf(pageInfo.getPage())).compose(RxUtils.schedulersTransformer())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {

                        }
                    }).subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finishRefresh.setValue(o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        failRefresh.call();
                    }
                }));
    }
}
