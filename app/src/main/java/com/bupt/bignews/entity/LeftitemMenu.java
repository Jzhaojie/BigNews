package com.bupt.bignews.entity;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ZhaoJie1234 on 2016/10/3.
 */
public class LeftitemMenu {
    private int icon;
    private String  text;

    public LeftitemMenu(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String toString(){
        return "LeftitemMenu{" +
                "leftIcon=" + icon +
                ", title='" + text + '\'' +
                '}';
    }
}
