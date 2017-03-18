package com.bupt.bignews.utils;

import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v7.util.AsyncListUtil;

import com.bupt.bignews.entity.LoginUser;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ZhaoJie1234 on 2016/10/26.
 */
public class OkhttpManager {
    private static final String FILE_PREFIX="BigNews_";
    private static OkhttpManager okhttpManager = null;
    private OkHttpClient client;
    private Handler handler;
    private ApiUtils apiUtils;



    private OkhttpManager(){
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }
    private static  OkhttpManager getInstance(){
        if(okhttpManager==null){
            okhttpManager = new OkhttpManager();
        }
        return okhttpManager;
    }

    /**
     * 同步请求Get方法
     * @param url
     * @return
     * @throws IOException
     */
    private Response p_getAsync(String url) throws IOException{
        final Request request= new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response;
    }

    /**
     * 进行发送同步get请求，并且返回String类型数据
     * @param url
     * @return
     * @throws IOException
     */
    private String p_getAsyncString(String url)throws  IOException{
        Response response = p_getAsync(url);
        return response.body().string();
    }
    private void p_getAsync(String url, final DataCallBack callBack){
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback(){
            /**
             * 返回异步数据
             * @param call
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    deliverSuccess(response.body().string(),callBack);
                }catch (IOException e){
                    deliverFailure(request,e,callBack);
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                deliverFailure(request,e,callBack);
            }
        });
    }
  /*
  * 异步上传表单
  * */
    private void p_postAsyncParams(String url,Map<String,String>params,final DataCallBack callBack){
        RequestBody requestBody=null;
        if(params==null){
            params = new HashMap<String,String>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        for(Map.Entry<String,String> entry:params.entrySet()){
            String key = entry.getKey().toString();
            String value =null;
            if(entry.getValue()==null){
                value ="";
            }else{
                value = entry.getValue();
            }
            builder.add(key,value);
        }
        requestBody=builder.build();
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverFailure(request,e,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    deliverSuccess(response.body().string(),callBack);
            }
        });
    }
    /*
    * post login user 信息给服务器
    *
    * */
    private void p_user(String url, LoginUser loginUser,final DataCallBack callBack){
        Gson gson = new Gson();
        String jsonUser = gson.toJson(loginUser);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,jsonUser);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("accept","application/json")
                .addHeader("content-type","application/json")
                .addHeader("connection","keep-alive")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverFailure(request,e,callBack);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                deliverSuccess(response.body().string(),callBack);
            }
        });

    }



    /**
     *
     * get userinfo
     *
     */
    private void g_Userinfo(String url, String token, final DataCallBack callBack) {

        client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("authorization", "Token " + token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                deliverSuccess(response.body().string(), callBack);
            }
        });

    }




    /**
     * 异步下载文件
     * @param url
     * @param destDir
     * @param callBack
     */
     private void p_downloadAsync(final String url, final String destDir, final DataCallBack callBack){
         final Request request = new Request.Builder().url(url).build();
         client.newCall(request).enqueue(new Callback(){
             @Override
             public void onFailure(Call call, IOException e) {

             }

             @Override
             public void onResponse(Call call, Response response) throws IOException {
                 InputStream inputStream=null;
                 FileOutputStream fileOutputStream =null;
                 inputStream = response.body().byteStream();
                 byte[] buffer = new byte[2048];
                 int len =0;
                 File file = new File(destDir,getFileName(url));
                 fileOutputStream = new FileOutputStream(file);
                 while((len=inputStream.read(buffer))!=-1){
                  }
                 fileOutputStream.flush();
                 deliverSuccess(file.getAbsolutePath(),callBack);
             }
         });
     }
    private String getFileName(String url){
        int separatorIndex = url.lastIndexOf("/");
        String path =(separatorIndex<0) ? url:url.substring(separatorIndex+1,url.length());
        return FILE_PREFIX + path;
    }


    /**
     * 请求成功的数据情况
     * @param result
     * @param callBack
     */
    private void deliverSuccess(final String result,final DataCallBack callBack){
     handler.post(new Runnable() {
         @Override
         public void run() {
             if(callBack!=null){
                 callBack.requestSuccess(result);
             }
         }
     });
    }

    /**
     * 进行分发失败的数据情况
     * @param request
     * @param e
     * @param callBack
     */
    private void deliverFailure(final Request request,final IOException e,final DataCallBack callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(callBack!=null){
                    callBack.requestFailure(request,e);
                }
            }
        });
    }


//*************************************************公有方法****************************************

    /**
     * 根据请求地址，发起get同步请求，并且返回response信息
     * @param url
     * @param callBack
     * @return
     * @throws IOException
     */
    public static Response getSync(String url, DataCallBack callBack) throws IOException{
        return getInstance().p_getAsync(url);
    }

    /**
     * 根据请求的地址，进行发起get同步请求，并且返回String信息
     * @param url
     * @return
     * @throws IOException
     */
    public static String getSyncAsString(String url) throws IOException{
        return getInstance().p_getAsyncString(url);
    }

    /**
     * get异步请求
     * @param url
     * @param callBack
     */
    public static void getAsync(String url,DataCallBack callBack){
        getInstance().p_getAsync(url,callBack);
    }
    /**
     * 进行POST异步请求数据
     * @param url
     * @param params  需要POST的数据
     * @param callBack
     */
    public static void postAsyncParams(String url,Map<String,String> params,DataCallBack callBack){
        getInstance().p_postAsyncParams(url, params, callBack);
    }

    /**
     * 将user post 到服务器登陆
     * @param url
     * @param user
     * @param callBack
     */
    public static void postLoginUser(String url,LoginUser user,DataCallBack callBack){
        getInstance().p_user(url,user,callBack);
    }

    /**
     * 从服务器get userinfo
     * @param url
     * @param token
     * @param callBack
     */
    public static void getUserInfo(String url,String token,DataCallBack callBack){
        getInstance().g_Userinfo(url,token,callBack);
    }



    /**
     * 进行异步下载文件
     * @param url  文件地址
     * @param destDir  存入本地的路径
     * @param callBack 下载成功回调
     */
    public static void downloadAsync(String url,String destDir,DataCallBack callBack){
        getInstance().p_downloadAsync(url,destDir,callBack);
    }


    public interface DataCallBack{
         /**
          * 请求失败
          * @param request
          * @param e
          */
         void requestFailure(Request request,Exception e);

         /**
          * 请求成功
          * @param result
          */
         void requestSuccess(String result);
     }
}
