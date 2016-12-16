package com.bupt.bignews.utils;


import com.bupt.bignews.R;
import com.bupt.bignews.entity.LeftitemMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhaoJie1234 on 2016/10/3.
 */
public class MenuDataUtils {
    public static List<LeftitemMenu> getItemMenus(){
        List<LeftitemMenu> menus =new ArrayList<LeftitemMenu>();
        menus.add(new LeftitemMenu(R.drawable.icon_zhanghaoxinxi,"账号信息"));
        menus.add(new LeftitemMenu(R.drawable.icon_wodeguanzhu,"我的关注"));
        menus.add(new LeftitemMenu(R.drawable.icon_shoucang,"我的收藏"));
        menus.add(new LeftitemMenu(R.drawable.icon_yijianfankui,"意见反馈"));
        menus.add(new LeftitemMenu(R.drawable.icon_shezhi,"设置"));
        return menus;
    }
}
