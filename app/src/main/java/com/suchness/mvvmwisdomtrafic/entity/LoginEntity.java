package com.suchness.mvvmwisdomtrafic.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author hejunfeng
 * @Date 15:58 2021/4/6 0006
 * @Description com.suchness.mvvmwisdomtrafic.entity
 **/
public class LoginEntity {
    private Integer code;
    private LoginBodyEntity data;
    private String message;
    private Boolean success;


    public Integer getCode() {
        return code;
    }

    public LoginBodyEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(LoginBodyEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class LoginBodyEntity implements Parcelable {
        private String account;
        private String birthday;
        private String email;
        private String name;
        private String phone;
        private String sex;
        private String userId;

        protected LoginBodyEntity(Parcel in) {
            account = in.readString();
            birthday = in.readString();
            email = in.readString();
            name = in.readString();
            phone = in.readString();
            sex = in.readString();
            userId = in.readString();
        }

        public static final Creator<LoginBodyEntity> CREATOR = new Creator<LoginBodyEntity>() {
            @Override
            public LoginBodyEntity createFromParcel(Parcel in) {
                return new LoginBodyEntity(in);
            }

            @Override
            public LoginBodyEntity[] newArray(int size) {
                return new LoginBodyEntity[size];
            }
        };

        public void setAccount(String account) {
            this.account = account;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAccount() {
            return account;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getSex() {
            return sex;
        }

        public String getUserId() {
            return userId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(account);
            dest.writeString(birthday);
            dest.writeString(email);
            dest.writeString(name);
            dest.writeString(phone);
            dest.writeString(sex);
            dest.writeString(userId);
        }
    }
}
