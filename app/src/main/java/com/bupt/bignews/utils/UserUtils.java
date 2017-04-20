package com.bupt.bignews.utils;

import android.content.Context;
import android.util.Log;

import com.bupt.bignews.application.Application;
import com.bupt.bignews.entity.User;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by ZhaoJie1234 on 2017/3/19.
 */
public class UserUtils {

    private User getUser;     //get userinfo

    private TokenUtil tokenUtil;

    private ApiUtils apiUtils;

    private static String TAG = "zhaojie";

    private Application myApplication;

    public void getUserInfo(Context context){
        tokenUtil = new TokenUtil(context);
        if(tokenUtil.getToken()!=""){
            getUser = new User();
            apiUtils = new ApiUtils();
            String get_url = apiUtils.getApiUrl().get("user");
            Log.i(TAG, "url:" + get_url);
            OkhttpManager.getUserInfo(get_url,tokenUtil.getToken(), new OkhttpManager.DataCallBack() {
                @Override
                public void requestFailure(Request request, Exception e) {
                    requestFailure(request, e);
                }

                @Override
                public void requestSuccess(String result) {
                    Log.i(TAG, "Authorization:" + tokenUtil.getToken());
                    Application.getInstance().setLoginUser(JsonUtils.getJsonUser(result));
                    Log.i("zhaojie", "tokenlogin success: " + Application.getInstance().getLoginUser().toString());
                }
            });
        }else{
            Log.i(TAG, "error:没有获取到token，需要登陆");
        }

    }
}
