package com.bupt.bignews.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.bignews.R;
import com.bupt.bignews.common.DefineView;
import com.bupt.bignews.entity.ListNewsBean;
import com.bupt.bignews.entity.MessageEvent;
import com.bupt.bignews.ui.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.Map;

import static org.greenrobot.eventbus.ThreadMode.BACKGROUND;
import static org.greenrobot.eventbus.ThreadMode.MAIN;

public class ArticleActivity extends BaseActivity implements DefineView {
    private TextView article_title;
    private ImageView source_image;
    private TextView article_time;
    private TextView article_source;
    private WebView article;
    private ListNewsBean listNewsBean;
    private ImageLoader imageLoader;
    private String mString;
    private TextView top_bar_title;
    private Button share_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        当系统版本为4.4或者4.4以上的时候可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        }
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
        top_bar_title = (TextView) findViewById(R.id.top_bar_title);
        article_title = (TextView) findViewById(R.id.article_title);
        article_time = (TextView) findViewById(R.id.article_time);
        article_source = (TextView) findViewById(R.id.article_source);
        share_button = (Button) findViewById(R.id.btn_share);
//        source_image = (ImageView)findViewById(R.id.source_image);
        imageLoader = ImageLoader.getInstance();
        article = (WebView) findViewById(R.id.article);
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


        UMShareConfig config = new UMShareConfig();
        config.isOpenShareEditActivity(true);
        UMShareAPI.get(ArticleActivity.this).setShareConfig(config);

    }

    @Override
    public void initValidata() {
        if (listNewsBean != null) {
            article_title.setText(listNewsBean.getTitle());
            article_time.setText(listNewsBean.getDate());
            article_source.setText(listNewsBean.getAuthor_name());
            mString = listNewsBean.getUrl();
            String[] MainUrl = mString.split("//");
            String[] url = MainUrl[1].split("/");
//            imageLoader.displayImage("http://" + url[0] + "/favicon.ico", source_image);
            top_bar_title.setText(listNewsBean.getAuthor_name());
            Log.i("message", url[0] + "/favicon.ico");
            article.loadUrl(listNewsBean.getUrl());
        } else {
            Log.e("error:", "listNewsBean is empty");
        }
    }

    @Override
    public void initListener() {

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(ArticleActivity.this).withText("Hello").withMedia(new UMImage(ArticleActivity.this, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493377400109&di=5df29c9cca540b03043495310bc6d43c&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2F71c64c3eed3fc76491998ae2a1485339f289325a.jpg"))
                        .setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                Toast.makeText(ArticleActivity.this, "开始分享" + share_media.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                Toast.makeText(ArticleActivity.this, "分享成功" + share_media.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                Toast.makeText(ArticleActivity.this, "分享出错" + share_media.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                Toast.makeText(ArticleActivity.this, "分享取消" + share_media.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }).open();
            }
        });
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
        if (stickyEvent != null) {
            listNewsBean = new ListNewsBean();
            listNewsBean = event.getListNewsBean();
            Log.i("listNewsBean", listNewsBean.toString());
        } else {
            Log.i("error", "onMessageEvent:listNewsBean is null ");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}


