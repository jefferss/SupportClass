package com.example.android.supportclass;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordSetListActivity extends Activity {

    private static final String filePath = "/data/data/com.example.android.supportclass/files/";
    private RadioGroup rgGroup;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_set_list);

        rgGroup = (RadioGroup)findViewById(R.id.rg_group);
        radioGroupChanged(rgGroup);

        //get all xml files
        File directory = new File(filePath);
        File[] files = directory.listFiles();
        List<String> xmlFileLst = new ArrayList<>();;

        for (int i = 0; i < files.length; i++)
        {
            if (files[i].getPath().endsWith(".xml")){
                //remove extension
                String filename = files[i].getName().substring(0, files[i].getName().lastIndexOf("."));
                xmlFileLst.add(filename);
            }
        }
        Collections.sort(xmlFileLst);

        //launch list view
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, R.layout.activity_word_set_list_view, R.id.txtWord, xmlFileLst);
        ListView lv = (ListView)findViewById(R.id.lvWord);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // show a toast on TextView text
                LinearLayout ll = (LinearLayout) view;
                TextView tv = ll.findViewById(R.id.txtWord);

                CharSequence className = tv.getText().toString();
                Toast.makeText(getApplicationContext(),
                        className, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(WordSetListActivity.this, WordActivity.class);
                Bundle b = new Bundle();
                b.putString("key", className.toString());
                intent.putExtras(b); //Put class name to next Intent
                startActivity(intent);
                finish();
            }
        });

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
                        intent.setClass(WordSetListActivity.this, MainActivity.class );
                        startActivity(intent);
                        break;
                    case R.id.rb_word:

                        break;

                }
            }
        });
    }
}
