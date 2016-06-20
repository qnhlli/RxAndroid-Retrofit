package com.http.qnhlli.myhttptest2.util;

import com.http.qnhlli.myhttptest2.impl.HttpImpl;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qnhlli on 2016/6/20.
 */
public class HttpMethods {
    public static final String BASE_URL = "http://192.168.1.210/";
    private Retrofit retrofit;
    private HttpImpl httpImpl;

    //构造方法私有
    private HttpMethods() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpUtil.genericClient())
                .build();
        httpImpl = retrofit.create(HttpImpl.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }


    public void GET(Subscriber<ResponseMessage> subscriber, int idMemberInfo){
        httpImpl.getAccount(idMemberInfo)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
