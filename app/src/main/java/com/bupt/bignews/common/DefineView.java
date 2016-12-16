package com.bupt.bignews.common;

/**
 * Created by ZhaoJie1234 on 2016/9/8.
 */
public interface DefineView {
    public void initView(); //初始化界面
    public void initValidata(); //初始化变量
    public void initListener(); //初始化监听器
    public void bindData();   //绑定数据；
}
