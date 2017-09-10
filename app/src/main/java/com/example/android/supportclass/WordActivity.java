package com.example.android.supportclass;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class WordActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private TextView tvScreenWord;
    private TextView tvScreenMeaning;

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

        mTextMessage = (TextView) findViewById(R.id.message);
        tvScreenWord = (TextView) findViewById(R.id.txtWord);
        tvScreenMeaning = (TextView) findViewById(R.id.txtMeaning);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_word);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        openWordCards();
    }

    private void openWordCards() {
        InputStream input = getResources().openRawResource(R.raw.vocabularies);

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(input);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("word");
            Element element = (Element)nList.item(0);
            String screenWord = element.getElementsByTagName("english").item(0).getTextContent();
            String screenWordMeaning = element.getElementsByTagName("meaning").item(0).getTextContent();
            tvScreenWord.setText(screenWord);
            tvScreenMeaning.setText(screenWordMeaning);
        }catch(Exception ex){
            Log.i("ExceptionOccurred", "openWordCards: load xml file exception. Msg: " + ex.getMessage());
        }

    }
}
