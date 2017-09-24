package com.example.android.supportclass;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.webkit.WebView;

import com.example.android.supportclass.R;

/**
 * 主页信息详情页面
 * Created by kona on 2017/9/22.
 */

public class HomeCousesDetailActivity extends Activity {
    private WebView mWebView;
    private final String TAG = "PrepareActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_homedetail);
        mWebView = findViewById(R.id.wv_infor_detail);
        int i= getIntent().getIntExtra("homelist",0);
        switch (i){
            case 0:{
                mWebView.loadUrl("https://www.op.ac.nz/study/english/new-zealand-certificate-in-english-language-level-2/");
                break;
            }case 1:{

                mWebView.loadUrl("https://www.op.ac.nz/study/english/new-zealand-certificate-in-english-language-level-3/");
                break;
            }case 2:{
                mWebView.loadUrl("https://www.op.ac.nz/study/english/new-zealand-certificate-in-english-language-level-4/");
                break;
            }case 3:{
                mWebView.loadUrl("https://www.op.ac.nz/study/english/new-zealand-certificate-in-english-language-level-5/");
                break;
            }case 4:{
                mWebView.loadUrl("https://www.op.ac.nz/study/english/new-zealand-certificate-in-english-language-level-5/");
                break;
            }

        }


    }
}
