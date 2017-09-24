package com.example.android.supportclass;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;

/**
 * 主页信息详情页面
 * Created by kona on 2017/9/22.
 */

public class HomeDetailActivity extends Activity {
    private WebView mWebView;
    private final String TAG = "PrepareActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_homedetail);
        mWebView = findViewById(R.id.wv_infor_detail);
       int i= getIntent().getIntExtra("homelist",0);
        mWebView.loadUrl("https://www.op.ac.nz/about-us/");
        switch (i){
            case 0:{
                mWebView.loadUrl("https://www.op.ac.nz/about-us/news-and-events/id/2154");
                break;
            }case 1:{

                mWebView.loadUrl("https://www.op.ac.nz/about-us/news-and-events/id/2167");
                break;
            }case 2:{
                mWebView.loadUrl("https://www.op.ac.nz/about-us/news-and-events/id/2166");
                break;
            }case 3:{
                mWebView.loadUrl("https://www.op.ac.nz/about-us/news-and-events/id/2163");
                break;
            }case 4:{
                mWebView.loadUrl("https://www.op.ac.nz/about-us/news-and-events/id/2162");
                break;
            }case 5:{
                mWebView.loadUrl("https://www.op.ac.nz/about-us/news-and-events/id/2161");
                break;
            }

        }
    }
}
