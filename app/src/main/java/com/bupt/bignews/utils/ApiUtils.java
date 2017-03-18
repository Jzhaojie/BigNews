package com.bupt.bignews.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoJie1234 on 2017/3/17.
 */
public class ApiUtils {
    private final static String API_URL = "http://123.207.146.172:8000/rest-auth/";
    public Map<String,String> getApiUrl(){
        Map<String,String> apiUrl = new HashMap<String,String>();
        apiUrl.put("login",API_URL+"login/");
        apiUrl.put("registration",API_URL+"registration/");
        apiUrl.put("user",API_URL+"user/");
//        apiUrl.put("login",API_URL+"login/");
//        apiUrl.put("login",API_URL+"login/");
//        apiUrl.put("login",API_URL+"login/");
        return apiUrl;
    }
}
