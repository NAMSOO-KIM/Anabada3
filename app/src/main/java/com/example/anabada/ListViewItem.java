package com.example.anabada;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private String image;
    private String titleStr ;
    private String descStr ;

    public void setIcon(String icon) {
        image = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public String getIcon() {
        return this.image ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
}
