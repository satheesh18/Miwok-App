package com.example.miwok;

public class word {
    private String english;
    private String miwok;
    private  int imageid=-1;
    private int musicid;
    public word(String temp1,String temp2,int temp3){
        english=temp1;
        miwok=temp2;
        musicid=temp3;
    }
    public word(String temp1,String temp2,int temp3,int temp4){
        english=temp1;
        miwok=temp2;
        imageid=temp3;
        musicid=temp4;
    }
    public String getenglish() {
        return english;
    }
    public String getmiwok() {
        return miwok;
    }
    public int getimageid(){
        return imageid;
    }
    public int getmusicid(){
        return musicid;
    }

}
