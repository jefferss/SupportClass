package com.example.android.supportclass;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
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

public class WordActivity extends AppCompatActivity {
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

    private List<WordModel> wmlst = new ArrayList<>();
    private int currWordIdx;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String filePath = "/data/data/com.example.android.supportclass/files/";
    private static final String fileName = "vocabularies.xml";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent = new Intent();


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent.setClass(WordActivity.this , MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_word:
                    return true;
                case R.id.navigation_information:
                    intent.setClass(WordActivity.this , InformationActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

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

        btonPrevious.setEnabled(false);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_word);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        openWordCards();
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
            FileInputStream inputStream = openFileInput("vocabularies.xml");
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

    private void openWordCards() {

        try{
            //read content from xml file
            FileInputStream inputStream = openFileInput("vocabularies.xml");
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

            int currWordIdx = 0;
            int nextWordIdx = currWordIdx;
            UpdateWordCard(nextWordIdx);
        }catch(Exception ex){
            Log.i("ExceptionOccurred", "openWordCards: load xml file exception. Msg: " + ex.getMessage());
        }
    }

    private void UpdateWordCard(int nWdIdx) {
        txtvScreenWord.setText(wmlst.get(nWdIdx).getEnglishWord());
        txtvScreenMeaning.setText(wmlst.get(nWdIdx).getMeaning());
        txtvSource.setText(wmlst.get(nWdIdx).getSource());
        switch (wmlst.get(nWdIdx).getLevel()) {
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
            displayPicture(nWdIdx);
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
        int wordIdx = currWordIdx;
        UpdateWordCard(wordIdx);
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
        int wordIdx = currWordIdx;
        UpdateWordCard(wordIdx);
        if (currWordIdx <= 0) {
            btonPrevious.setEnabled(false);
        }
        else{
            btonPrevious.setEnabled(true);
        }
    }


}
