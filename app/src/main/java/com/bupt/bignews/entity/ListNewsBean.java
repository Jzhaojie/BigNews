package com.bupt.bignews.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by ZhaoJie1234 on 2016/12/7.
 */
public class ListNewsBean implements Serializable{
    public String title;
    public String date;
    public String author_name;
    public String thumbnail_pic_s;
    public String thumbnail_pic_s1;
    public String thumbnail_pic_s2;
    public String url;
    public String uniquekey;
    public String type;
    public String realtype;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getData() {
        return date;
    }

    public void setData(String data) {
        this.date = data;
    }

    public String getRealtype() {
        return realtype;
    }

    public void setRealtype(String realtype) {
        this.realtype = realtype;
    }

    public String getThumbnail_pic_s1() {
        return thumbnail_pic_s1;
    }

    public void setThumbnail_pic_s1(String thumbnail_pic_s1) {
        this.thumbnail_pic_s1 = thumbnail_pic_s1;
    }

    public String getThumbnail_pic_s2() {
        return thumbnail_pic_s2;
    }

    public void setThumbnail_pic_s2(String thumbnail_pic_s2) {
        this.thumbnail_pic_s2 = thumbnail_pic_s2;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    @Override
    public String toString() {
        return "ListNewsBean{" +
                "author_name='" + author_name + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                ", thumbnail_pic_s1='" + thumbnail_pic_s1 + '\'' +
                ", thumbnail_pic_s2='" + thumbnail_pic_s2 + '\'' +
                ", url='" + url + '\'' +
                ", uniquekey='" + uniquekey + '\'' +
                ", type='" + type + '\'' +
                ", realtype='" + realtype + '\'' +
                '}';
    }
}