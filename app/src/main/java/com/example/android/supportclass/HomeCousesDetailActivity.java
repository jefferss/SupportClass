package com.example.android.supportclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.supportclass.R;

/**
 * 主页信息详情页面
 * Created by kona on 2017/9/22.
 */

public class HomeCousesDetailActivity extends Activity {
    private WebView mWebView;
    private final String TAG = "PrepareActivity";
    private RadioGroup rgGroup;
    private Intent intent;
    private RadioButton homeBtn;
    private RadioButton lessonBtn;
    public void radioGroupChanged(RadioGroup rgGroup){
        intent  = new Intent();

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        //首页
                        homeBtn.setChecked(false);
                        lessonBtn.setChecked(false);
                        intent.setClass(HomeCousesDetailActivity.this, MainActivity.class );
                        startActivity(intent);
                        break;
                    case R.id.rb_word:
                        homeBtn.setChecked(false);
                        lessonBtn.setChecked(false);
                        intent.setClass(HomeCousesDetailActivity.this, WordSetListActivity.class );
                        startActivity(intent);
                        break;

                }
            }
        });
    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        homeBtn.setChecked(false);
        lessonBtn.setChecked(false);
    }*/
  @Override
    protected void onRestart() {
        super.onRestart();
        homeBtn.setChecked(false);
        lessonBtn.setChecked(false);
    }
  /*  @Override
    protected void onStart() {
        super.onStart();
        homeBtn.setChecked(false);
        lessonBtn.setChecked(false);
    }
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_homedetail);
        rgGroup = (RadioGroup)findViewById(R.id.rg_group);
        radioGroupChanged(rgGroup);
        lessonBtn= findViewById(R.id.rb_word);
        homeBtn=findViewById(R.id.rb_home);
        homeBtn.setChecked(false);
        lessonBtn.setChecked(false);
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
