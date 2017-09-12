package com.example.android.supportclass;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class WordActivity extends AppCompatActivity {
    private TextView txtvScreenWord;
    private TextView txtvScreenMeaning;
    private TextView txtvSource;
    private RadioButton rbnLevel1;
    private RadioButton rbnLevel2;
    private RadioButton rbnLevel3;



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
            FileInputStream inputStream = openFileInput("vocabularies.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("word");

            //use a random number to select a word.
            Random rand = new Random();
            Element element = (Element)nList.item(rand.nextInt(nList.getLength()));
            String screenWord = element.getElementsByTagName("english").item(0).getTextContent();
            String screenWordMeaning = element.getElementsByTagName("meaning").item(0).getTextContent();
            String sourceWord = element.getElementsByTagName("source").item(0).getTextContent();
            String level = element.getElementsByTagName("level").item(0).getTextContent();

            txtvScreenWord.setText(screenWord);
            txtvScreenMeaning.setText(screenWordMeaning);
            txtvSource.setText(sourceWord);
            switch (level) {
                case "1":
                    rbnLevel1.setChecked(true);
                    rbnLevel2.setChecked(false);
                    rbnLevel3.setChecked(false);
                    break;
                case "2":
                    rbnLevel1.setChecked(false);
                    rbnLevel2.setChecked(true);
                    rbnLevel3.setChecked(false);
                    break;
                case "3":
                    rbnLevel1.setChecked(false);
                    rbnLevel2.setChecked(false);
                    rbnLevel3.setChecked(true);
                    break;
            }
        }catch(Exception ex){
            Log.i("ExceptionOccurred", "openWordCards: load xml file exception. Msg: " + ex.getMessage());
        }

    }
}
