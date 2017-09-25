package com.example.android.supportclass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * 主页信息详情页面
 * Created by kona on 2017/9/22.
 */

public class HomeDetailActivity extends Activity {
    private WebView mWebView;
    private final String TAG = "PrepareActivity";
    private RadioGroup rgGroup;
    private Intent intent;
    private RadioButton rbHomeBtn;
    private RadioButton rbLessonBtn;
    //radio group 改变监听
    public void radioGroupChanged(RadioGroup rgGroup){
        intent  = new Intent();

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        //首页
                        rbHomeBtn.setChecked(false);
                        rbLessonBtn.setChecked(false);
                        intent.setClass(HomeDetailActivity.this, MainActivity.class );
                        startActivity(intent);
                        break;
                    case R.id.rb_word:
                        rbHomeBtn.setChecked(false);
                        rbLessonBtn.setChecked(false);
                        intent.setClass(HomeDetailActivity.this, WordSetListActivity.class );
                        startActivity(intent);
                        break;

                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        rbHomeBtn.setChecked(false);
        rbLessonBtn.setChecked(false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_homedetail);
        rgGroup = (RadioGroup)findViewById(R.id.rg_group);
        rbHomeBtn=findViewById(R.id.rb_home);
        rbLessonBtn=findViewById(R.id.rb_word);
        radioGroupChanged(rgGroup);
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
