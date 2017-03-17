package com.bupt.bignews.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.bignews.R;
import com.bupt.bignews.adapter.LeftItemAdapter;
import com.bupt.bignews.application.Application;
import com.bupt.bignews.ui.base.BaseActivity;
import com.bupt.bignews.widget.DragLayout;
import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends BaseActivity {
    //    UMImage image = new UMImage(this,"http://www.umeng.com/images/pic/social/integrated_3.png");
    private LinearLayout ll1;
    private DragLayout drag_layout;
    private ImageView top_bar_icon;
    private ListView lv_left_main;
    private ImageView iv_bottom;
    private TextView iv_username,iv_email;
    private Application myApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initValidata();
        initListener();
        bindData();
        myApplication = (Application)getApplication();
    }
    public void initView(){
        drag_layout = (DragLayout)findViewById(R.id.drag_layout);
        top_bar_icon = (ImageView)findViewById(R.id.top_bar_icon);
        lv_left_main = (ListView)findViewById(R.id.lv_left_main);
        ll1 = (LinearLayout)findViewById(R.id.ll1);
        iv_username = (TextView) findViewById(R.id.iv_username);
        iv_email = (TextView) findViewById(R.id.iv_email);
    }
    public void initValidata(){
        lv_left_main.setAdapter(new LeftItemAdapter());


    }
    public void initListener(){
        drag_layout.setDragListener(new CustomDragListener());
        top_bar_icon.setOnClickListener(new CustomOnClickListener());
        lv_left_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_SHORT);
                }

            }
        });

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=myApplication.getLoginUser()){
                    //head to user login activity
                    openActivity(LoginActivity.class);
                }else{
                    //head to user info activity
                }
            }
        });


    }
    public void bindData(){

    }
    class CustomDragListener implements DragLayout.DragListener{
        @Override
        public void onClose() {

        }

        @Override
        public void onDrag(float percent) {
            ViewHelper.setAlpha(top_bar_icon,1-percent);
        }

        @Override
        public void onOpen() {

        }
    }
    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            drag_layout.open();
        }
    }
    public DragLayout getDrag_layout(){
        return drag_layout;
    }

}
