package com.bupt.bignews.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.bignews.R;
import com.bupt.bignews.common.DefineView;
import com.bupt.bignews.entity.LoginUser;
import com.bupt.bignews.entity.User;
import com.bupt.bignews.utils.JellyInterpolator;
import com.bupt.bignews.utils.OkhttpManager;

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

    private final static String LOGIN_URL="http://123.207.146.172:8000/rest-auth/login/";

    private final static String USER_DETAIL="http://123.207.146.172:8000/rest-auth/user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void userLogin(LoginUser user){
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
                    Log.i("zhaojie:", "requestSuccess: "+result);
                    getUserInfo();

                    LoginActivity.this.finish();
                }
            });
        }
    }

    public void getUserInfo() {
        getUser = new User();
        OkhttpManager.getAsync(USER_DETAIL, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                Log.i("zhaojie:", "requestFailure:请求失败 ");
            }

            @Override
            public void requestSuccess(String result) {

                Log.i("zhaojie:", "requestSuccess: "+result);


            }
        });


    }

}
