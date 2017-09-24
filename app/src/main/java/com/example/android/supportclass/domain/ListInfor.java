package com.example.android.supportclass.domain;

/**
 * Created by kona on 2017/9/21.
 */

public class ListInfor {
    private String title;
    private String date;

    public ListInfor(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
