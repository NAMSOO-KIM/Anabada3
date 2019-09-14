package com.example.anabada;

import com.google.firebase.auth.FirebaseUser;

public class Boardinfo {

    private String boardTitle;
    private String boardDescription;
    private FirebaseUser user ;
    private String photoUrl;

    public Boardinfo(FirebaseUser user ,String boardTitle, String boardDescription,String photoUrl){
        this.boardTitle =boardTitle;
        this.boardDescription = boardDescription;
        this.user = user;
        this.photoUrl=photoUrl;

    }
    public Boardinfo(FirebaseUser user ,String boardTitle, String boardDescription){
        this.boardTitle =boardTitle;
        this.boardDescription = boardDescription;
        this.user = user;


    }
    public String getBoardTitle(){
        return this.boardTitle;

    }
    public void setBoardTitle(String boardTitle){
        this.boardTitle=boardTitle;
    }


    public String getBoardDescription(){
        return this.boardDescription;

    }
    public void setBoardDescription(String boardDescription){
        this.boardDescription=boardDescription;
    }
    public String getPhotoUrl(){
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }
}
