package com.suchness.mvvmwisdomtrafic.ui.file.viewpic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @Author hejunfeng
 * @Date 9:50 2021/4/10 0010
 * @Description com.suchness.mvvmwisdomtrafic.ui.file.viewpic
 **/
public class UrlItems implements Parcelable {
    private List<String> urls;
    private Integer position;

    public UrlItems(List<String> urls, Integer position) {
        this.urls = urls;
        this.position = position;
    }

    protected UrlItems(Parcel in) {
        urls = in.createStringArrayList();
        if (in.readByte() == 0) {
            position = null;
        } else {
            position = in.readInt();
        }
    }

    public static final Creator<UrlItems> CREATOR = new Creator<UrlItems>() {
        @Override
        public UrlItems createFromParcel(Parcel in) {
            return new UrlItems(in);
        }

        @Override
        public UrlItems[] newArray(int size) {
            return new UrlItems[size];
        }
    };

    public List<String> getUrls() {
        return urls;
    }

    public Integer getPosition() {
        return position;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(urls);
        if (position == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(position);
        }
    }
}
