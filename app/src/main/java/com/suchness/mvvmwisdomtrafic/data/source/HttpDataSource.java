package com.suchness.mvvmwisdomtrafic.data.source;


import com.suchness.mvvmwisdomtrafic.entity.AlarmEntity;
import com.suchness.mvvmwisdomtrafic.entity.LoginEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * @Author hejunfeng
 * @Date 15:47 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.data.source
 **/
public interface HttpDataSource {
    /***
     * @return io.reactivex.Observable<java.lang.Object>
     * @Author hejunfeng
     * @Date 15:50 2021/4/6 0006
     * @Param []
     * @Description 登陆
     **/
    Observable<BaseResponse<LoginEntity>> login(String userName,String passWord);

    Observable<AlarmEntity> query(String pageSize, String pageNum);
}
