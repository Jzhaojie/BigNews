package com.bupt.bignews.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.bupt.bignews.R;
import com.bupt.bignews.ui.base.BaseActivity;
import com.bupt.bignews.widget.CustomVideoView;


public class WelcomeActivity extends BaseActivity {
    private CustomVideoView welcome_videoview;
    private Button welcome_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcome_videoview = (CustomVideoView) findViewById(R.id.welcome_videoview);
        welcome_button = (Button) findViewById(R.id.welcome_button);
        welcome_videoview.setVideoURI(Uri.parse("android.resource://"
                + this.getPackageName() + "/" + R.raw.hello));
        welcome_videoview.start();
        welcome_videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                welcome_videoview.start();                     //循环播放video
            }
        });           //为video播放完设置监听事件
        welcome_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (welcome_videoview.isPlaying()) {
                    welcome_videoview.stopPlayback();
                    welcome_videoview = null;  // stopPlayback()中已经写了welcome_videoview = null
                }
                openActivity(MainActivity.class);
                WelcomeActivity.this.finish();
            }
        });
    }
        private String getAppVersionName(Context context){
        String versionName = "";
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(),0);
            versionName = packageInfo.versionName;
            if(TextUtils.isEmpty(versionName)){
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
        }
    }

