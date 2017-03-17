package com.bupt.bignews.application;

import com.bupt.bignews.entity.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;

/**
 * Created by ZhaoJie1234 on 2016/10/3.
 */
public class Application extends android.app.Application {
    private User loginUser = new User();
    private static Application instance =null;
    private HashMap<String,Object> tempMap = new HashMap<String,Object>();
    public HashMap<String,Object> getTempMap(){
        return tempMap;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        this.instance =this;
    }
    public static Application getInstance(){
        return instance;
    }
    private void initImageLoader(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }
    public User getLoginUser() {
        return loginUser;
    }
    public void setLoginUser(User user){
        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(user.getPassword());
        loginUser.setEmail(user.getEmail());
        loginUser.setTelephone(user.getTelephone());
        loginUser.setHead_img(user.getHead_img());//head_img url
    }
}
