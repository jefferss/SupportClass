package com.example.android.supportclass;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.WordModel;

public class WordActivity extends AppCompatActivity {
    private TextView txtvScreenWord;
    private TextView txtvScreenMeaning;
    private TextView txtvSource;
    private RadioButton rbnLevel1;
    private RadioButton rbnLevel2;
    private RadioButton rbnLevel3;
    private List<WordModel> wmlst = new ArrayList<>();
    private int currWordIdx;

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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_word);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        openWordCards();
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
                wm.setPicture(element.getElementsByTagName("source").item(0).getTextContent());
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
    }

    public void showNextWord(View v) {
        currWordIdx += 1;
        int wordIdx = currWordIdx;
        UpdateWordCard(wordIdx);
    }

    public void showPreviousWord(View v) {
        currWordIdx -= 1;
        int wordIdx = currWordIdx;
        UpdateWordCard(wordIdx);
    }
}
