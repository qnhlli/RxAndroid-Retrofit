package com.example.qnhlli.myhttptest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.210/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpUtil.genericClient())
                .build();
        MemberService service = retrofit.create(MemberService.class);
        /**
         * GET请求
         */
        /*service.getAccount(6703)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseMessage>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("---onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("---onError" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(ResponseMessage responseMessage) {
//                        System.out.println("---onCompleted"+responseMessage.getResultCode());
                        System.out.println("---onCompleted" + responseMessage.getDataContent());
                    }
                });*/

        /**
         * POST请求
         */
//        AddressBean addressBean=new AddressBean();
//        addressBean.setIdMemberInfo(6703);
//        addressBean.setPageNo(1);
//        addressBean.setPageSize(10);


        //加密参数
        LoginBean loginBean=new LoginBean();
        loginBean.setUserName("13530877914");
        loginBean.setLoginPwd("123456");
        String string=new Gson().toJson(loginBean);

        // 对登录的用户名,密码进行加密
        String loginMiWen = RSAEncryptor.getInstance(MainActivity.this).encrypt(string);

        RequestMessage request = new RequestMessage();
        request.setDeviceType("android");
        request.setReqMessage(loginMiWen);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("RequestMessage", request);

        service.getLogin(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseMessage>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("---onCompletedPost");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("---onErrorPost" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(ResponseMessage responseMessage) {
                        System.out.println("---onCompletedPost" + responseMessage.getDataContent());
                    }
                });


        /**
         * 上传图片
         */
       /* File file = new File(Environment.getExternalStorageDirectory(), "abc.png");
        System.out.println("---111:"+file);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody idMemberInfoBody = RequestBody.create(null, "6703");
        service.uploadImage(idMemberInfoBody,fileBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseMessage>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("---onCompleted" );
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("---onError");
                    }

                    @Override
                    public void onNext(ResponseMessage responseMessage) {
                        System.out.println("---onNext" + responseMessage.getDataContent());
                    }
                });*/


        /**
         * retrofit
         */

        /*service.get(6703)
       .enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onFailure(Throwable t) {
                System.out.println("---onError");
            }

           @Override
           public void onResponse(Response<ResponseMessage> response, Retrofit retrofit) {
               System.out.println("---onResponse");
               System.out.println("---onResponse"+response.code());
           }
       });*/

    }

}
