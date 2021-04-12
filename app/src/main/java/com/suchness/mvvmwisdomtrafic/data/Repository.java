package com.suchness.mvvmwisdomtrafic.data;

import com.suchness.mvvmwisdomtrafic.data.source.HttpDataSource;
import com.suchness.mvvmwisdomtrafic.data.source.LocalDataSource;
import com.suchness.mvvmwisdomtrafic.entity.AlarmEntity;
import com.suchness.mvvmwisdomtrafic.entity.LoginEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * @Author hejunfeng
 * @Date 15:46 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.data
 **/
public class Repository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static Repository INSTANCE = null;
    private final HttpDataSource httpDataSource;
    private final LocalDataSource mLocalDataSource;

    public Repository(HttpDataSource httpDataSource, LocalDataSource mLocalDataSource) {
        this.httpDataSource = httpDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public static Repository getInstance(HttpDataSource httpDataSource, LocalDataSource mLocalDataSource){
        if (INSTANCE == null){
            synchronized (Repository.class){
                if (INSTANCE == null){
                    INSTANCE = new Repository(httpDataSource,mLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }

    @Override
    public Observable<BaseResponse<LoginEntity>> login(String userName, String passWord) {
        return httpDataSource.login(userName,passWord);
    }

    @Override
    public Observable<AlarmEntity> query(String pageSize, String pageNum) {
        return httpDataSource.query(pageSize,pageNum);
    }
}
