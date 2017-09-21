package com.example.android.supportclass;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.WordModel;
import utility.OnSwipeTouchListener;

public class WordActivity extends Activity {
    private TextView txtvScreenWord;
    private TextView txtvScreenMeaning;
    private TextView txtvSource;
    private RadioButton rbnLevel1;
    private RadioButton rbnLevel2;
    private RadioButton rbnLevel3;
    private ImageView imgvWordPhoto;
    private Button btonNext;
    private Button btonPrevious;
    private RadioGroup rdogWord;
    private LinearLayout linltWord;
    private TextView txtvWordIndex;

    private List<WordModel> wmlst = new ArrayList<>();
    private int currWordIdx;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String filePath = "/data/data/com.example.android.supportclass/files/";
    private String fileName = "";
    private String className = "";

    //导航切换
    private Intent intent;
    private RadioGroup rgGroup;
    public void radioGroupChanged(RadioGroup rgGroup){
        intent=new Intent();

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        //首页
                        intent.setClass(WordActivity.this,MainActivity.class );
                        startActivity(intent);
                        break;
                    case R.id.rb_word:

                        break;

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_word);

        Bundle b = getIntent().getExtras();
        if(b != null){
            className = b.getString("key");
            fileName = className + ".xml";
        }


        rgGroup = (RadioGroup)findViewById(R.id.rg_group);
        radioGroupChanged(rgGroup);

        txtvScreenWord = (TextView) findViewById(R.id.tvWord);
        txtvScreenMeaning = (TextView) findViewById(R.id.tvMeaning);
        txtvSource = (TextView) findViewById(R.id.tvSource);
        rbnLevel1 = (RadioButton)  findViewById(R.id.rbLevel1);
        rbnLevel2 = (RadioButton)  findViewById(R.id.rbLevel2);
        rbnLevel3 = (RadioButton)  findViewById(R.id.rbLevel3);
        imgvWordPhoto = (ImageView) findViewById(R.id.imgWordPhoto);
        btonNext = (Button)  findViewById(R.id.btnNext);
        btonPrevious = (Button)  findViewById(R.id.btnPrecious);
        rdogWord = (RadioGroup) findViewById(R.id.rdgWord);
        linltWord = (LinearLayout) findViewById(R.id.container);
        txtvWordIndex = (TextView)  findViewById(R.id.txtWordIndex);

        btonPrevious.setEnabled(false);

   /*     BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_word);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/

        openWordSet();

        linltWord.setOnTouchListener(new OnSwipeTouchListener() {

            public void onSwipeLeft() {
                btonPrevious.setEnabled(true);
                currWordIdx += 1;
                updateWordCard(currWordIdx);
                if (currWordIdx >= wmlst.size()-1) {
                    btonNext.setEnabled(false);
                }
                else{
                    btonNext.setEnabled(true);
                }
            }

            public void onSwipeRight() {
                btonNext.setEnabled(true);
                currWordIdx -= 1;
                updateWordCard(currWordIdx);
                if (currWordIdx <= 0) {
                    btonPrevious.setEnabled(false);
                }
                else{
                    btonPrevious.setEnabled(true);
                }
            }


            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        rdogWord.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);
                String levelNum = "";

                switch (rb.getText().toString()) {
                    case "Level 1":
                        levelNum = "1";
                        break;
                    case "Level 2":
                        levelNum = "2";
                        break;
                    case "Level 3":
                        levelNum = "3";
                        break;
                }
                updateLevel(levelNum);
            }
        });

    }

    private void updateLevel(String levelNum) {
        //update both array list and xml file
        wmlst.get(currWordIdx).setLevel(levelNum);
        try{
            //read content from xml file
            FileInputStream inputStream = openFileInput(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("word");

            for(int i = 0; i < nList.getLength(); ++i){
                //Element targetElement = null;
                Element element = (Element)nList.item(i);
                if(element.getElementsByTagName("english").item(0).getTextContent().equals(wmlst.get(currWordIdx).getEnglishWord())){
                    element.getElementsByTagName("level").item(0).setTextContent(levelNum);
                    break;
                }
            }

            // write to xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath + fileName));
            transformer.transform(source, result);

        }catch(Exception ex){
            Log.i("ExceptionOccurred", "updateLevel: open xml file exception. Msg: " + ex.getMessage());
        }
    }

    private void openWordSet() {

        try{
            //read content from xml file
            FileInputStream inputStream = openFileInput(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("word");

            for(int i = 0; i < nList.getLength(); ++i){
                WordModel wm = new WordModel();
                Element element = (Element)nList.item(i);
                wm.setIdNum(Integer.parseInt( element.getElementsByTagName("id").item(0).getTextContent() ));
                wm.setEnglishword(element.getElementsByTagName("english").item(0).getTextContent());
                wm.setMeaning(element.getElementsByTagName("meaning").item(0).getTextContent());
                wm.setLevel(element.getElementsByTagName("level").item(0).getTextContent());
                wm.setPicture(element.getElementsByTagName("picture").item(0).getTextContent());
                wm.setSource(element.getElementsByTagName("source").item(0).getTextContent());

                wmlst.add(wm);
            }
            //randomly select a word
            long seed = System.nanoTime();
            Collections.shuffle(wmlst, new Random(seed));
            Collections.sort(wmlst);

            currWordIdx = 0;
            updateWordCard(currWordIdx);
        }catch(Exception ex){
            Log.i("ExceptionOccurred", "openWordSet: load xml file exception. Msg: " + ex.getMessage());
        }
    }

    private void updateWordCard(int wordIdx) {
        txtvScreenWord.setText(wmlst.get(wordIdx).getEnglishWord());
        txtvScreenMeaning.setText(wmlst.get(wordIdx).getMeaning());
        txtvSource.setText(wmlst.get(wordIdx).getSource());
        txtvWordIndex.setText(className + " : " + Integer.toString(currWordIdx+1) + " / " +  Integer.toString(wmlst.size()));
        switch (wmlst.get(wordIdx).getLevel()) {
            case "1":
                rbnLevel1.setChecked(true);
                break;
            case "2":
                rbnLevel2.setChecked(true);
                break;
            case "3":
                rbnLevel3.setChecked(true);
                break;
        }

        if(isStoragePermissionGranted()){
            //display picture file
            displayPicture(wordIdx);
        }
    }

    private void displayPicture(int nWdIdx) {
        File path=new File(filePath, wmlst.get(nWdIdx).getPicture());
        try {
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(path));
            imgvWordPhoto.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            //clear word image
            imgvWordPhoto.setImageResource(0);
            e.printStackTrace();
        }
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
            //display picture file
            displayPicture(currWordIdx);
        }
    }

    public void showNextWord(View v) {
        btonPrevious.setEnabled(true);
        currWordIdx += 1;
        updateWordCard(currWordIdx);
        if (currWordIdx >= wmlst.size()-1) {
            btonNext.setEnabled(false);
        }
        else{
            btonNext.setEnabled(true);
        }
    }

    public void showPreviousWord(View v) {
        btonNext.setEnabled(true);
        currWordIdx -= 1;
        updateWordCard(currWordIdx);
        if (currWordIdx <= 0) {
            btonPrevious.setEnabled(false);
        }
        else{
            btonPrevious.setEnabled(true);
        }
    }
}
