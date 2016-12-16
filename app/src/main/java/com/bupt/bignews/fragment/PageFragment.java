package com.bupt.bignews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bupt.bignews.R;
import com.bupt.bignews.common.DefineView;
import com.bupt.bignews.entity.CategoriesBean;
import com.bupt.bignews.entity.ListNewsBean;
import com.bupt.bignews.fragment.Base.BaseFragment;
import com.bupt.bignews.utils.CatagoriesUtils;
import com.bupt.bignews.utils.JsonUtils;
import com.bupt.bignews.utils.OkhttpManager;
import com.google.gson.Gson;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by ZhaoJie1234 on 2016/10/6.
 */
public class PageFragment extends BaseFragment implements DefineView {
    private static final String KEY = "EXTRA";
    private static final String ID="POSITION";
    private View mView;
    private TextView textView;
    private String title;
    private TextView tv_page;
    private OkhttpManager okhttpManager;
    private ArrayList<ListNewsBean> listNewsBeens;
    private QuickAdapter<ListNewsBean> quickAdapter;
    private JsonUtils jsonUtils;
    private String[] titles = new String[]{"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};

    public PageFragment() {
       super();
    }

    public static  PageFragment newInstance(String extra,int position){
        Bundle bundle = new Bundle();
        bundle.putString(KEY,extra);
        bundle.putInt(ID,position);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle =getArguments();
        if(bundle!=null){
            title = bundle.getString(KEY);
        }
        if(mView==null){
            mView=inflater.inflate(R.layout.page_fragment_layout,container,false);
            initView();
            initValidata();
            initListener();
            bindData();
        }
        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        tv_page =(TextView)mView.findViewById(R.id.tv_page);

    }

    @Override
    public void initValidata() {
        Bundle bundle = getArguments();
        List<CategoriesBean> categoriesBeens =CatagoriesUtils.getCatagoryBeans();
        Log.d("zhaojie:","success"+bundle.getString(KEY));
        CategoriesBean categoriesBean = categoriesBeens
                .get(bundle.getInt(ID));
        okhttpManager.getAsync(categoriesBean.getHref(), new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {

            }

            @Override
            public void requestSuccess(String result) {
                listNewsBeens = new ArrayList<ListNewsBean>();
                listNewsBeens = JsonUtils.getListNewsBeans(result);
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {
        quickAdapter = new QuickAdapter<ListNewsBean>(getActivity(), R.layout.one_image_item_layout,listNewsBeens) {
            @Override
            protected void convert(BaseAdapterHelper helper, ListNewsBean item) {
                if()
            }
        }

    }
}
