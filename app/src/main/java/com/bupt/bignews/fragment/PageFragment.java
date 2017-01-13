package com.bupt.bignews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.bignews.R;
import com.bupt.bignews.common.DefineView;
import com.bupt.bignews.entity.CategoriesBean;
import com.bupt.bignews.entity.ListNewsBean;
import com.bupt.bignews.entity.MessageEvent;
import com.bupt.bignews.fragment.Base.BaseFragment;
import com.bupt.bignews.ui.ArticleActivity;
import com.bupt.bignews.utils.CatagoriesUtils;
import com.bupt.bignews.utils.JsonUtils;
import com.bupt.bignews.utils.OkhttpManager;
import com.bupt.bwidgetutil.PullToRefreshListView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import helper.BaseAdapterHelper;
import helper.MultiItemTypeSupport;
import helper.QuickAdapter;
import okhttp3.Request;
import okhttp3.Response;
import utils.UIUtils;


/**
 * Created by ZhaoJie1234 on 2016/10/6.
 */
public class PageFragment extends BaseFragment implements DefineView {
    private static final String KEY = "EXTRA";
    private static final String ID="POSITION";
    private int index=0;      //listNewsBeens中的已经显示的index的位置
    private View mView;
    private TextView textView;
    private String title;
    private TextView tv_page;
    private OkhttpManager okhttpManager;
    private ArrayList<ListNewsBean> tempNewsBeens;
    private ArrayList<ListNewsBean> listNewsBeens;
    private ArrayList<ListNewsBean> savedNewsBeens;
    private QuickAdapter<ListNewsBean> quickAdapter;
    private JsonUtils jsonUtils;
    private LinearLayout empty,loading,error;
    private String[] titles = new String[]{"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
    private PullToRefreshListView pullToRefreshListView;
    private FrameLayout prompt_framelayout;
    public PageFragment() {
       super();
    }
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

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
        pullToRefreshListView =(PullToRefreshListView)mView.findViewById(R.id.fragment_listview);
        prompt_framelayout = (FrameLayout) mView.findViewById(R.id.prompt_framelayout);
        empty =(LinearLayout)mView.findViewById(R.id.empty);
        error =(LinearLayout)mView.findViewById(R.id.error);
        loading =(LinearLayout)mView.findViewById(R.id.loading);
    }

    @Override
    public void initValidata() {
        imageLoader = ImageLoader.getInstance();
        //图片加载
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.backgroundimg)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        Bundle bundle = getArguments();
        List<CategoriesBean> categoriesBeens =CatagoriesUtils.getCatagoryBeans();
        Log.d("zhaojie:","success"+bundle.getString(KEY));
        CategoriesBean categoriesBean = categoriesBeens
                .get(bundle.getInt(ID));
        //loading ,error,empty情况
        pullToRefreshListView.setVisibility(View.VISIBLE);
        prompt_framelayout.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        okhttpManager.getAsync(categoriesBean.getHref(), new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                pullToRefreshListView.setVisibility(View.GONE);
                prompt_framelayout.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }

            @Override
            public void requestSuccess(String result) {
                listNewsBeens =new ArrayList<ListNewsBean>();
                listNewsBeens = JsonUtils.getListNewsBeans(result);
                bindData();
            }
        });
    }

    @Override
    public void initListener() {
        Bundle bundleTitle = getArguments();
        UIUtils.setPullToRefreshLastUpdated(pullToRefreshListView,bundleTitle.getString(KEY),getActivity());
         pullToRefreshListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
             Bundle bundle = getArguments();
             List<CategoriesBean> categoriesBeens = CatagoriesUtils.getCatagoryBeans();
             CategoriesBean categoriesBean = categoriesBeens
                     .get(bundle.getInt(ID));

             @Override
             public void onRefresh() {
                 savedNewsBeens = new ArrayList<ListNewsBean>();
                 tempNewsBeens = new ArrayList<ListNewsBean>();
                 OkhttpManager.getAsync(categoriesBean.getHref(), new OkhttpManager.DataCallBack() {
                     @Override
                     public void requestFailure(Request request, Exception e) {

                     }

                     @Override
                     public void requestSuccess(String result) {
                         LinkedHashSet<ListNewsBean> newsBeenSet = new LinkedHashSet<ListNewsBean>();
                         index = listNewsBeens.size();
                         tempNewsBeens = JsonUtils.getListNewsBeans(result);
                         newsBeenSet.addAll(tempNewsBeens);
                         newsBeenSet.addAll(listNewsBeens);
                         listNewsBeens.removeAll(listNewsBeens);
                         listNewsBeens.addAll(newsBeenSet);
                         for(ListNewsBean item: listNewsBeens){
                             Log.i("success:", item.getTitle()+"\n");
                         }
                         quickAdapter.notifyDataSetChanged();
                         UIUtils.savePullToRefreshLastUpdateAt(pullToRefreshListView,bundle.getString(KEY),getActivity());
                     }
                 });
             }
         });
      pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Toast.makeText(getActivity(),listNewsBeens.get(position - 1).toString(),Toast.LENGTH_LONG).show();
              ListNewsBean postBean = new ListNewsBean();
              postBean = listNewsBeens.get(position -1);
              EventBus.getDefault().postSticky(new MessageEvent(postBean));
              Log.i("message", EventBus.getDefault().getStickyEvent(MessageEvent.class).toString());
              Intent intent = new Intent(getActivity(), ArticleActivity.class);
              startActivity(intent);

          }
      });
    }


    @Override
    public void bindData() {
        MultiItemTypeSupport<ListNewsBean> multiItemTypeSupport = new MultiItemTypeSupport<ListNewsBean>() {
            @Override
            public int getLayoutId(int position, ListNewsBean listNewsBean) {
                if(listNewsBean.getThumbnail_pic_s()==""){
                    return R.layout.item_news_no_img;
                }
                return R.layout.one_image_item_layout;
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int postion, ListNewsBean listNewsBean) {
                if(listNewsBean.getThumbnail_pic_s()==""){
                    return listNewsBean.NO_IMAGE;
                }
                return listNewsBean.ONE_IMAGE;
            }
        };
        if(listNewsBeens!=null) {
            pullToRefreshListView.setVisibility(View.VISIBLE);
            prompt_framelayout.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            quickAdapter = new QuickAdapter<ListNewsBean>(getActivity(), listNewsBeens, multiItemTypeSupport) {
                @Override
                protected void convert(BaseAdapterHelper helper, ListNewsBean item) {
                    switch (helper.layoutId) {
                        case R.layout.one_image_item_layout:
                            helper.setText(R.id.item_news_tv_title, item.getTitle());
                            helper.setText(R.id.item_news_tv_source, item.getAuthor_name());
                            helper.setText(R.id.item_news_tv_time, item.getDate());
                            // 还需要添加图片异步加载
                            imageLoader.displayImage(item.getThumbnail_pic_s(),(ImageView)helper.getView(R.id.item_news_tv_image),options);
                            break;
                        case R.layout.item_news_no_img:
                            helper.setText(R.id.item_news_tv_title, item.getTitle());
                            helper.setText(R.id.item_news_tv_source, item.getAuthor_name());
                            helper.setText(R.id.item_news_tv_time, item.getDate());
                            break;

                    }
                }
            };
            pullToRefreshListView.setAdapter(quickAdapter);
        }else{
            pullToRefreshListView.setVisibility(View.VISIBLE);
            prompt_framelayout.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
        }

    }
}
