package com.http.qnhlli.myhttptest2.util;


import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by qnhlli on 2016/6/15.
 */
public class HttpUtil {
    private static final String ACCESS_NAME = "nhh@admin";
    private static final String ACCESS_PASSWORD = "nhh$2015";
    private static final int DEFAULT_TIMEOUT = 5;
    /**
     * 拦截器,添加头部
     *
     * @return
     */
    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient();
        //设置超时时间
        httpClient.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Basic " + base64Encode(ACCESS_NAME + ":" + ACCESS_PASSWORD))
                        .build();
                return chain.proceed(newRequest);
            }
        };
        /**
         * Log拦截器
         */
        LogInterceptor logInterceptor = new LogInterceptor(new LogInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.printLogDebug(message);
            }
        });
        logInterceptor.setLevel(LogInterceptor.Level.BODY);

        httpClient.interceptors().add(interceptor);
        httpClient.interceptors().add(logInterceptor);
        return httpClient;
    }

    private static String base64Encode(String value) {
        return Base64.encode(value.getBytes());
    }


}
