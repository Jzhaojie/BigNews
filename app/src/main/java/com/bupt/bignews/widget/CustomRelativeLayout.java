package com.bupt.bignews.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by ZhaoJie1234 on 2016/9/6.
 */
public class CustomRelativeLayout extends RelativeLayout{
    private DragLayout dl;
    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRelativeLayout(Context context) {
        super(context);
    }
    public void setDragLayout(DragLayout dl){
        this.dl = dl;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        if(dl.getStatus() != DragLayout.Status.Close){
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (dl.getStatus() != DragLayout.Status.Close) {
            if(event.getAction() == MotionEvent.ACTION_UP){
                dl.close();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
