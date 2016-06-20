package com.http.qnhlli.myhttptest2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.http.qnhlli.myhttptest2.R;
import com.http.qnhlli.myhttptest2.impl.HttpImpl;
import com.http.qnhlli.myhttptest2.util.HttpMethods;
import com.http.qnhlli.myhttptest2.util.HttpUtil;
import com.http.qnhlli.myhttptest2.util.ResponseMessage;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qnhlli on 2016/6/20.
 */
public class HttpGet extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMovie();
    }

    private void getMovie(){
       Subscriber subscriber = new Subscriber<ResponseMessage>() {
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
               System.out.println("---onCompleted" + responseMessage.getDataContent());
           }
       };
        HttpMethods.getInstance().GET(subscriber, 6703);
    }
}
