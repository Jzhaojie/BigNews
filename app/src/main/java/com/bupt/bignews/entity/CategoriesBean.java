package com.bupt.bignews.entity;

import java.io.Serializable;

/**
 * Created by ZhaoJie1234 on 2016/9/8.
 */
public class CategoriesBean implements Serializable {
    private String title; //分类tab名称
    private String href;  //分类点击地址
    private String data_type;  //分类类型

    public CategoriesBean(String data_type, String href, String title) {
        super();
        this.data_type = data_type;
        this.href = href;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    @Override
    public String toString() {
        return "CategoriesBean[title=" + title + ",href="
                +  href + ",data_type=" + data_type +
                "]";
    }
}
