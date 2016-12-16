package com.bupt.bignews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bupt.bignews.R;
import com.bupt.bignews.application.Application;
import com.bupt.bignews.entity.LeftitemMenu;
import com.bupt.bignews.utils.MenuDataUtils;

import java.util.List;

/**
 * Created by ZhaoJie1234 on 2016/9/15.
 */
public class LeftItemAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    private List<LeftitemMenu> itemMenus;
    public LeftItemAdapter(){
        mInflater = LayoutInflater.from(Application.getInstance());
        itemMenus = MenuDataUtils.getItemMenus();
    }
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    @Override
    public int getCount() {
        return itemMenus != null?itemMenus.size():0;
    }

    @Override
    public Object getItem(int position) {
        return itemMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*
        * 覆写getView
        * */
        Holder holder =null;
        if(convertView==null){
            holder =new Holder();
            convertView = mInflater.inflate(R.layout.item_left_menu_layout,null);
            holder.item_left_view_img=(ImageView)convertView.findViewById(R.id.item_left_view_img);
            holder.item_left_view_title =(TextView)convertView.findViewById(R.id.item_left_view_title);
            convertView.setTag(holder);
         }else{
            holder=(Holder)convertView.getTag();
        }
        holder.item_left_view_img.setImageResource(itemMenus.get(position).getIcon());
        holder.item_left_view_title.setText(itemMenus.get(position).getText());
        return convertView;
    }
    private static class Holder{
        ImageView item_left_view_img;
        TextView item_left_view_title;
    }
}
