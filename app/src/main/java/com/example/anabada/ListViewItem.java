package com.example.anabada;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable image;
    private String titleStr ;
    private String descStr ;

    public void setImage(Drawable icon) {
       image = icon ;
    }

    public Drawable getImage() {
        return this.image ;
    }
}
