package com.suchness.mvvmwisdomtrafic.data.source;

import com.suchness.mvvmwisdomtrafic.entity.AlarmEntity;
import com.suchness.mvvmwisdomtrafic.entity.LoginEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Author hejunfeng
 * @Date 15:52 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.data.source
 **/
public interface ApiService {
    @FormUrlEncoded
    @POST("api/login")
    Observable<BaseResponse<LoginEntity>> LoginPost(@Field("userName") String userName,@Field("password") String password);
}
