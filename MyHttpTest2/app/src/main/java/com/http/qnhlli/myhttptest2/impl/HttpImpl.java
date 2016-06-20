package com.http.qnhlli.myhttptest2.impl;

import com.http.qnhlli.myhttptest2.util.ResponseMessage;
import com.squareup.okhttp.RequestBody;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by qnhlli on 2016/6/20.
 */
public interface HttpImpl {
    @GET("webservice/member/account/summary/{idMemberInfo}")
    Call<ResponseMessage> get(@Path("idMemberInfo") int idMemberInfo);

    @GET("webservice/member/account/summary/{idMemberInfo}")
    Observable<ResponseMessage> getAccount(@Path("idMemberInfo") int idMemberInfo);

    @GET("webservice/index")
    Observable<ResponseMessage> getIndex();

    @POST("webservice/member/address/list")
    Observable<ResponseMessage> getAddress(@Body Map<String,Object> map);

    @POST("webservice/member/login")
    Observable<ResponseMessage> getLogin(@Body Map<String,Object> map);


    //上传图片
    @Multipart
    @POST("webservice/member/change/imgHead")
    Observable<ResponseMessage> uploadImage(@Part("idMemberInfo") RequestBody idMemberInfo, @Part("file\"; filename=\"edf.png") RequestBody file);
}
