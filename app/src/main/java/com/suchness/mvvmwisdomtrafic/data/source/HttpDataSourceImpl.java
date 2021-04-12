package com.suchness.mvvmwisdomtrafic.data.source;

import com.suchness.mvvmwisdomtrafic.entity.AlarmEntity;
import com.suchness.mvvmwisdomtrafic.entity.LoginEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * @Author hejunfeng
 * @Date 15:51 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.data.source
 **/
public class HttpDataSourceImpl implements HttpDataSource{
    private ApiService apiService;
    private ApiService2 apiService2;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(ApiService apiService,ApiService2 apiService2){
        if (INSTANCE == null){
            synchronized (HttpDataSourceImpl.class){
                if (INSTANCE == null){
                    INSTANCE = new HttpDataSourceImpl(apiService,apiService2);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

    public HttpDataSourceImpl(ApiService apiService , ApiService2 apiService2) {
        this.apiService = apiService;
        this.apiService2 = apiService2;
    }

    @Override
    public Observable<BaseResponse<LoginEntity>> login(String userName,String passWord) {
        return apiService.LoginPost(userName,passWord);
    }

    @Override
    public Observable<AlarmEntity> query(String pageSize, String pageNum) {
        return apiService2.queryPost(pageSize,pageNum);
    }
}
