package com.suchness.mvvmwisdomtrafic.app;

import com.suchness.mvvmwisdomtrafic.data.Repository;
import com.suchness.mvvmwisdomtrafic.data.source.ApiService;
import com.suchness.mvvmwisdomtrafic.data.source.ApiService2;
import com.suchness.mvvmwisdomtrafic.data.source.HttpDataSource;
import com.suchness.mvvmwisdomtrafic.data.source.HttpDataSourceImpl;
import com.suchness.mvvmwisdomtrafic.data.source.LocalDataSource;
import com.suchness.mvvmwisdomtrafic.data.source.LocalDataSourceImpl;
import com.suchness.mvvmwisdomtrafic.utils.RetrofitClient;

/**
 * @Author hejunfeng
 * @Date 17:39 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.app
 **/
public class Injection {
    public static Repository provideRepository(){
        //网络API服务
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        ApiService2 apiService2 = RetrofitClient.getInstance2().create(ApiService2.class);
        //网络数据源
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService,apiService2);
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return Repository.getInstance(httpDataSource, localDataSource);
    }
}
