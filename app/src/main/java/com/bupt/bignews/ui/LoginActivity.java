package com.bupt.bignews.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.bignews.R;
import com.bupt.bignews.application.Application;
import com.bupt.bignews.common.DefineView;
import com.bupt.bignews.entity.LoginUser;
import com.bupt.bignews.entity.User;
import com.bupt.bignews.utils.ApiUtils;
import com.bupt.bignews.utils.JellyInterpolator;
import com.bupt.bignews.utils.JsonUtils;
import com.bupt.bignews.utils.OkhttpManager;
import com.bupt.bignews.utils.TokenUtil;
import com.bupt.bignews.utils.UserUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class LoginActivity extends Activity implements DefineView {
    private TextView mBtnLogin;

    private View progress;

    private View mInputLayout;

    private float mWidth,mHeight;

    private LinearLayout mName, mPsw;

    private EditText username,password;


    private LoginUser mUser;   //post userinfo


    private String tempName,tempPassword;

    private User getUser;     //get userinfo

    private TokenUtil tokenUtil;

    private ApiUtils apiUtils;

    private UserUtils userUtils;

    private Intent reIntent;

    private final static String LOGIN_URL="http://123.207.146.172:8000/rest-auth/login/";

    private final static String USER_DETAIL="http://123.207.146.172:8000/rest-auth/user";

    private final static String TAG = "zhaojie";

    private User returnUser;

    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //        当系统版本为4.4或者4.4以上的时候可以使用沉浸式状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        }
        setContentView(R.layout.login);
        initView();
        initValidata();
        initListener();
        bindData();

    }
    @Override
    public void initView() {
        mBtnLogin = (TextView)findViewById(R.id.main_btn_login);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = (LinearLayout) findViewById(R.id.input_layout_name);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
        username = (EditText) findViewById(R.id.et_username);
        password = (EditText)findViewById(R.id.et_password);

    }

    @Override
    public void initValidata() {
        tokenUtil = new TokenUtil(getApplicationContext());
        reIntent = new Intent();
        returnUser = new User();
        bundle = new Bundle();
    }

    @Override
    public void initListener() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWidth = mBtnLogin.getMeasuredWidth();
                mHeight = mBtnLogin.getMeasuredHeight();

                mName.setVisibility(View.INVISIBLE);
                mPsw.setVisibility(View.INVISIBLE);
                tempName = username.getText().toString().trim();
                tempPassword = password.getText().toString().trim();
                Log.i("zhaojie", "name:"+tempName+"password:"+tempPassword);
                mUser = new LoginUser();
                mUser.setUsername(tempName);
                mUser.setPassword(tempPassword);
                Log.i("zhaojie", mUser.toString());
                userLogin(mUser);
//                inputAnimator(mInputLayout, mWidth, mHeight);

            }
        });
    }
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }

    @Override
    public void bindData() {

    }
    public void userLogin(final LoginUser user){
        if(user.getPassword().isEmpty()||user.getUsername().isEmpty()){
            Toast.makeText(LoginActivity.this,"用户名或者密码不能为空",Toast.LENGTH_SHORT).show();
            Log.i("zhaojie", "userLogin: 用户名或者密码能为空");
            mName.setVisibility(View.VISIBLE);
            mPsw.setVisibility(View.VISIBLE);
        }else{
            inputAnimator(mInputLayout, mWidth, mHeight);
            OkhttpManager.postLoginUser(LOGIN_URL,user,new OkhttpManager.DataCallBack(){

                @Override
                public void requestFailure(Request request, Exception e) {
                    Log.i("zhaojie:", "requestFailure:请求失败 ");
                }

                @Override
                public void requestSuccess(String result) {
                    Log.i("zhaojie:", "requestToken: "+result);
                    JsonParser parser = new JsonParser();
                    JsonObject json = (JsonObject)parser.parse(result);
                    String token = json.get("key").toString();
                    String key = token.substring(1,token.length()-1);
                    Log.i("zhaojie", "requestSucce:"+key);
                    tokenUtil.saveToken(user.getUsername(),key);
                    Log.i(TAG, "getToken: "+tokenUtil.getToken());
                    Log.i(TAG, "requestSuccess: "+"helloworld");
                    userUtils = new UserUtils();
                    userUtils.getUserInfo(getApplicationContext());
                    returnUser = Application.getInstance().getLoginUser();
                    bundle.putString("username",returnUser.getUsername());
                    bundle.putString("head_img",returnUser.getHead_img());
                    reIntent.putExtra("loginuser",bundle);
                    LoginActivity.this.setResult(0,reIntent);
                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_LONG).show();
                    LoginActivity.this.finish();
                }
            });
        }
    }
}
