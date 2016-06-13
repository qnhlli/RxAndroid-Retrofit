package com.example.qnhlli.myrxandroid2;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by qnhlli on 2016/6/12.
 */
public interface GithubService {
    @GET("/search/repositories")
    Observable<GithubResults> getTopNewAndroidRepos(@QueryMap Map<String, String> queryMap);
}
