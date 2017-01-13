package com.bupt.bignews.application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;

/**
 * Created by ZhaoJie1234 on 2016/10/3.
 */
public class Application extends android.app.Application {
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
}
