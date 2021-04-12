package com.suchness.mvvmwisdomtrafic.data.source;

import com.suchness.mvvmwisdomtrafic.entity.AlarmEntity;
import com.suchness.mvvmwisdomtrafic.entity.LoginEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author hejunfeng
 * @Date 15:52 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.data.source
 **/
public interface ApiService2 {
    @GET("highwaymessage/api/getAllMessage")
    Observable<AlarmEntity> queryPost(@Query("pageSize") String pageSize, @Query("pageNum") String pageNum);
}
