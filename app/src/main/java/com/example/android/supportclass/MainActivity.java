package com.example.android.supportclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioGroup;

public class MainActivity extends Activity {

    private RadioGroup rgGroup;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rgGroup = (RadioGroup)findViewById(R.id.rg_group);
        radioGroupChanged(rgGroup);
    }

    //radio group 改变监听
    public void radioGroupChanged(RadioGroup rgGroup){
        intent  = new Intent();
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        //首页

                        break;
                    case R.id.rb_word:
                        intent.setClass(MainActivity.this, WordSetListActivity.class );
                        startActivity(intent);
                        break;

                }
            }
        });
    }
}
