package com.bupt.bignews.utils;

import com.bupt.bignews.entity.CategoriesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhaoJie1234 on 2016/12/8.
 */
public class CatagoriesUtils {
    private static final String URL = "http://v.juhe.cn/toutiao/index";
    private static final String TYPE= "?type=" ;
    private static final String PARAMS="&key=c632e57a9acb4672a1b1bf09e2d5ab39";
    public static List<CategoriesBean> getCatagoryBeans() {
        List<CategoriesBean> beans = new ArrayList();
        beans.add(new CategoriesBean("头条", URL + TYPE + "top" + PARAMS, "top"));
        beans.add(new CategoriesBean("社会", URL + TYPE + "shehui" + PARAMS, "society"));
        beans.add(new CategoriesBean("国内", URL + TYPE + "guonei" + PARAMS, "home"));
        beans.add(new CategoriesBean("国际", URL + TYPE + "guoji" + PARAMS, "abroad"));
        beans.add(new CategoriesBean("娱乐", URL + TYPE + "yule" + PARAMS, "entertainment"));
        beans.add(new CategoriesBean("体育", URL + TYPE + "tiyu" + PARAMS, "sports"));
        beans.add(new CategoriesBean("军事", URL + TYPE + "junshi" + PARAMS, "military"));
        beans.add(new CategoriesBean("科技", URL + TYPE + "keji" + PARAMS, "technology"));
        beans.add(new CategoriesBean("财经", URL + TYPE + "caijing" + PARAMS, "economy"));
        beans.add(new CategoriesBean("时尚", URL + TYPE + "shishang" + PARAMS, "fashion"));
        return beans;
    }
}
