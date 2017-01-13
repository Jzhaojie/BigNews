package com.bupt.bignews.entity;

import android.view.MotionEvent;

/**
 * Created by ZhaoJie1234 on 2017/1/10.
 */
public class MessageEvent {
    private ListNewsBean listNewsBean;
    public MessageEvent(ListNewsBean listNewsBean){
        this.listNewsBean = listNewsBean;
    }

    public MessageEvent() {
        super();
    }

    public ListNewsBean getListNewsBean() {
        return listNewsBean;
    }

    public void setListNewsBean(ListNewsBean listNewsBean) {
        this.listNewsBean = listNewsBean;
    }
}
