package com.bupt.bignews.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.bignews.R;
import com.bupt.bignews.common.DefineView;
import com.bupt.bignews.entity.ListNewsBean;
import com.bupt.bignews.entity.MessageEvent;
import com.bupt.bignews.ui.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.Map;

import static org.greenrobot.eventbus.ThreadMode.BACKGROUND;
import static org.greenrobot.eventbus.ThreadMode.MAIN;

public class ArticleActivity  extends BaseActivity implements DefineView {
    private TextView article_title;
    private ImageView source_image;
    private TextView article_time;
    private TextView article_source;
    private WebView article;
    private ListNewsBean listNewsBean;
    private ImageLoader imageLoader;
    private String mString;
    private TextView top_bar_title;

   @Override
   public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_article);
       initView();
       EventBus.getDefault().register(this);
       initValidata();
       initListener();
       bindData();
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void bindData() {

    }

    @Override
    public void initView() {
        top_bar_title =(TextView) findViewById(R.id.top_bar_title);
        article_title =(TextView) findViewById(R.id.article_title);
        article_time =(TextView) findViewById(R.id.article_time);
        article_source =(TextView) findViewById(R.id.article_source);
        source_image = (ImageView)findViewById(R.id.source_image);
        imageLoader = ImageLoader.getInstance();
        article = (WebView)findViewById(R.id.article);
        article.setWebChromeClient(new WebChromeClient());
        article.setWebViewClient(new WebViewClient());
        WebSettings webSettings = article.getSettings();
        webSettings.setJavaScriptEnabled(true);  //开启javascript
        webSettings.setDomStorageEnabled(true);  //开启DOM
        webSettings.setDefaultTextEncodingName("utf-8"); //设置编码
        // // web页面处理
        webSettings.setAllowFileAccess(true);// 支持文件流

        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        webSettings.setBlockNetworkImage(false);
        //开启缓存机制
        webSettings.setAppCacheEnabled(true);
    }

    @Override
    public void initValidata() {
        if(listNewsBean != null) {
            article_title.setText(listNewsBean.getTitle());
            article_time.setText(listNewsBean.getDate());
            article_source.setText(listNewsBean.getAuthor_name());
            mString = listNewsBean.getUrl();
            String[] MainUrl = mString.split("//");
            String[] url = MainUrl[1].split("/");
            imageLoader.displayImage("http://" + url[0] + "/favicon.ico", source_image);
            top_bar_title.setText(listNewsBean.getAuthor_name());
            Log.i("message",url[0] + "/favicon.ico");
            article.loadUrl(listNewsBean.getUrl());
        }else {
            Log.e("error:","listNewsBean is empty");
        }
    }

    @Override
    public void initListener() {

    }



    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
   public void onMessageEvent(MessageEvent event){
        MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
        if(stickyEvent!=null) {
            listNewsBean = new ListNewsBean();
            listNewsBean = event.getListNewsBean();
            Log.i("listNewsBean", listNewsBean.toString());
        }else{
            Log.i("error", "onMessageEvent:listNewsBean is null ");
        }
   }



}


