package model;

/**
 * Created by Jeff on 17/09/2017.
 */

public class WordModel {
    private int id;
    private String englishWord;
    private String meaning;
    private String level;
    private String picture;
    private String source;

    public int getIdNum(){
        return id;
    }

    public String getEnglishWord(){
        return englishWord;
    }

    public String getMeaning(){
        return meaning;
    }

    public String getLevel(){
        return level;
    }

    public String getPicture(){
        return picture;
    }

    public String getSource(){
        return source;
    }

    public void setIdNum(int i){
        this.id = i;
    }

    public void setEnglishword(String e){
        this.englishWord = e;
    }

    public void setMeaning(String m){
        this.meaning = m;
    }

    public void setLevel(String lv){
        this.level = lv;
    }

    public void setPicture(String p){
        this.picture = p;
    }

    public void setSource(String s){
        this.source = s;
    }
}
