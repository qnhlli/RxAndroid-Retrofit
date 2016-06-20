package com.example.qnhlli.myhttptest;

import android.os.Environment;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by qnhlli on 2016/6/15.
 */
public interface MemberService {
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
    Observable<ResponseMessage> uploadImage(@Part("idMemberInfo") RequestBody idMemberInfo, @Part("file\"; filename=\"abc.png") RequestBody file);
}
