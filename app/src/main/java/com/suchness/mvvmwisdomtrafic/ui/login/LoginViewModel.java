package com.suchness.mvvmwisdomtrafic.ui.login;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.suchness.mvvmwisdomtrafic.data.Repository;
import com.suchness.mvvmwisdomtrafic.ui.home.HomeActivity;
import com.suchness.mvvmwisdomtrafic.utils.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.suchness.mvvmwisdomtrafic.app.AppConfig.AppKey;
import static com.suchness.mvvmwisdomtrafic.app.AppConfig.Secret;

/**
 * @Author hejunfeng
 * @Date 15:41 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.ui.login
 **/
public class LoginViewModel extends BaseViewModel<Repository> {

    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> passWord = new ObservableField<>("");

    public SingleLiveEvent enterSuccess = new SingleLiveEvent();

    public SingleLiveEvent setAccessToken = new SingleLiveEvent();

    public LoginViewModel(@NonNull Application application,Repository repository) {
        super(application,repository);
        userName.set(model.getUserName());
        passWord.set(model.getPassword());
    }

    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });

    public void getAccessToken(){
        String url = "https://open.ys7.com/api/lapp/token/get";
        Map<String, String> map = new HashMap<>();
        map.put("appKey",AppKey);
        map.put("appSecret",Secret);
        OkHttpUtil.post(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //ToastUtils.showShortSafe("网络错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseBody);
                    String data = object.get("data").toString();
                    JSONObject obj = new JSONObject(data);
                    String accessToken = obj.get("accessToken").toString();
                    setAccessToken.postValue(accessToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },map);
    }

    private void login() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(passWord.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        addSubscribe(model.login(userName.get(),passWord.get()).compose(RxUtils.schedulersTransformer())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showDialog();
                        }
                    }).subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        dismissDialog();
                        //保存账号密码
                        model.saveUserName(userName.get());
                        model.savePassword(passWord.get());
                        enterSuccess.call();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ToastUtils.showShortSafe("网路异常");
                    }
                }));
    }
}
