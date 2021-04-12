package com.suchness.mvvmwisdomtrafic.adapter;

/**
 * @Author hejunfeng
 * @Date 16:40 2021/3/12 0012
 * @Description com.analysis.wisdomtraffic.adapter
 **/
public class PageInfo {
    int page = 0;

    public void nextPage() {
        page++;
    }

    public void reset() {
        page = 0;
    }

    public boolean isFirstPage() {
        return page == 0;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
