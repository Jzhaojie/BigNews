package com.bupt.bignews.utils;

import android.os.Bundle;
import android.util.Log;

import com.bupt.bignews.entity.CategoriesBean;
import com.bupt.bignews.entity.ListNewsBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;

/**
 * Created by ZhaoJie1234 on 2016/12/8.
 */
public class JsonUtils {

    /*
    *
    * 向服务器发送数据返回当前页面新闻json数据
    * */
    public static ArrayList<ListNewsBean> getListNewsBeans(String json){
        ArrayList<ListNewsBean> listNewsBeans = new ArrayList<ListNewsBean>();
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonObject result = jsonObject.get("result").getAsJsonObject();
        JsonArray data = result.get("data").getAsJsonArray();
        for(JsonElement jsonElement: data){
                ListNewsBean bean = new ListNewsBean();
                bean = gson.fromJson(jsonElement, ListNewsBean.class);
                listNewsBeans.add(bean);
        }
        return listNewsBeans;
    }
}
