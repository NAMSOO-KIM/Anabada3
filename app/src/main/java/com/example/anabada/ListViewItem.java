package com.example.anabada;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable image;
    private String titleStr ;
    private String descStr ;

    public void setIcon(Drawable icon) {
        image = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public Drawable getIcon() {
        return this.image ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
}
