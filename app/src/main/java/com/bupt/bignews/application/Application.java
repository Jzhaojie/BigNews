package com.bupt.bignews.application;

import android.os.Debug;

import com.bupt.bignews.entity.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;

/**
 * Created by ZhaoJie1234 on 2016/10/3.
 */
public class Application extends android.app.Application {
    private User loginUser = new User();
    private static Application instance = null;
    private HashMap<String, Object> tempMap = new HashMap<String, Object>();

    public HashMap<String, Object> getTempMap() {
        return tempMap;
    }

    {
        PlatformConfig.setWeixin("wx3d811221ea09e165", "7c406a88d65626ed787d0c0e3ea84ca7");
        PlatformConfig.setQQZone("1106053965", "6ZW2W87adFLVeGgO");
        PlatformConfig.setSinaWeibo("2344215443", "b761140bca93936706f727ff42e87b0e", "http://sns.whalecloud.com");
        Config.DEBUG = true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        this.instance = this;
        UMShareAPI.get(this);
    }

    public static Application getInstance() {
        return instance;
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User user) {
        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(user.getPassword());
        loginUser.setEmail(user.getEmail());
        loginUser.setTelephone(user.getTelephone());
        loginUser.setHead_img(user.getHead_img());//head_img url
    }
}
