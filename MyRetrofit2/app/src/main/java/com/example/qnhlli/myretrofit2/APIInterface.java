package com.example.qnhlli.myretrofit2;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by qnhlli on 2016/6/7.
 */
public interface APIInterface {
    @GET("/users/{user}")
    Call<TestModel> repo(@Path("user") String user);
}
