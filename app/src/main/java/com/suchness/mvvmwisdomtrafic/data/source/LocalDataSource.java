package com.suchness.mvvmwisdomtrafic.data.source;

/**
 * @Author hejunfeng
 * @Date 16:27 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.data.source
 **/
public interface LocalDataSource {
    /**
     * 保存用户名
     */
    void saveUserName(String userName);

    /**
     * 保存用户密码
     */

    void savePassword(String password);

    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 获取用户密码
     */
    String getPassword();
}
