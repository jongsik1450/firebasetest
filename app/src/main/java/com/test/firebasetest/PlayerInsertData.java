package com.test.firebasetest;

public class PlayerInsertData {
    private String tName, tArea, pName, age;

    public PlayerInsertData(){}

    public PlayerInsertData(String tName, String tArea, String pName,String age){
        this.tName = tName;
        this.tArea = tArea;
        this.pName = pName;
        this.age = age;
    }

    public String gettName() {return tName; }
    public void settName(String tName) {this.tName = tName;}

    public String gettArea() {return tArea; }
    public void settArea(String tName) {this.tArea = tArea;}

    public String getpName() {return pName; }
    public void setpName(String pName) {this.pName = pName;}

    public String getage() {return age; }
    public void setage(String age) {this.age = age;}
}
