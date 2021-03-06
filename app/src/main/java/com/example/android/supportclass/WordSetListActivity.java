package com.example.android.supportclass;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class WordSetListActivity extends Activity {

    //for API 23, filePath is /storage/emulated/0/SupportClass/
    private static final String filePath = Environment.getExternalStorageDirectory() + "/SupportClass/";
    private static final String sampleFileNameXml = "sample.xml";
    private RadioGroup rgGroup;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_set_list);

        if(isStoragePermissionGranted()){
            initializeWordList();
        }
    }

    private void initializeWordList() {
        rgGroup = (RadioGroup)findViewById(R.id.rg_group);
        radioGroupChanged(rgGroup);

        File root = new File(filePath);
        if (!root.exists()) {
            root.mkdirs();
        }

        //get all xml files
        File directory = new File(filePath);
        File[] files = directory.listFiles();
        List<String> xmlFileLst = new ArrayList<>();
        ;
        if(files != null){
            for (int i = 0; i < files.length; i++)
            {
                if (files[i].getPath().endsWith(".xml")){
                    //remove extension
                    String filename = files[i].getName().substring(0, files[i].getName().lastIndexOf("."));
                    xmlFileLst.add(filename);
                }
            }
        }
        if(xmlFileLst.size() == 0){
            writeSampleXmlFile();
            xmlFileLst.add(sampleFileNameXml.substring(0, sampleFileNameXml.lastIndexOf(".")));
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

                //pass tapped item name to WordActivity
                Bundle b = new Bundle();
                b.putString("key", className.toString());
                intent.putExtras(b); //Put class name to next Intent
                startActivity(intent);

            }
        });
    }

    private void writeSampleXmlFile() {
        //File path = new File(filePath);
        InputStream input = getResources().openRawResource(R.raw.sample);
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(input);

            // write to xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath + sampleFileNameXml));
            transformer.transform(source, result);
        }catch(Exception ex){
            Log.i("ExceptionOccurred", "writeSampleXmlFile: write xml file exception. Msg: " + ex.getMessage());
        }
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

    public  boolean isStoragePermissionGranted() {
        //for API >= 23, runtime permission is needed
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("CheckPermission", "Permission is granted");
                return true;
            } else {

                Log.v("CheckPermission", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            Log.v("CheckPermission", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("CheckPermission","Permission: "+permissions[0]+ "was "+grantResults[0]);
            initializeWordList();
        }
    }
}
