package com.example.qnhlli.myrxandroid2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //第一种方法
        /*Observable.just("one", "two", "three", "four", "five")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("qhl", s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("qhl", "eeee");
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        Log.i("qhl", "wwwwwww");
                    }
                });*/


        //第二种方法
        /*Observable.just(1, 2, 3, 4, 4)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer num) {
                        return num + "";
                    }

                    ;
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("lxm", s);
            }
        });*/

        //第三种方法
       /* Observable observable= Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello RxAndroid !!");
                subscriber.onCompleted();
            }
        });

        Subscriber subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.v("qhl", "testFirst:onCompleted");
            }


            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.v("qhl", "onNext:" + s);
            }
        };

        observable.subscribe(subscriber);*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                testNetwork();
            }
        }).start();

    }

    public void testNetwork() {
        Map<String, String> options = new HashMap<>();
        options.put("q", "coderrobin");
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GithubService apiService = restAdapter.create(GithubService.class);
        //第一种方法
       /* apiService.getTopNewAndroidRepos(options)
                .subscribe(new Subscriber<GithubResults>() {
                    @Override
                    public void onCompleted() {
                        Log.v("qhl","成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("qhl", "错误");
                    }

                    @Override
                    public void onNext(GithubResults githubResults) {
                        Log.v("qhl", githubResults.total_count + "");
                    }
                });*/
        //第二种方法
        apiService.getTopNewAndroidRepos(options)
                .observeOn(Schedulers.newThread())
                .flatMap(new Func1<GithubResults, Observable<Repo>>() {
                             @Override
                             public Observable<Repo> call(GithubResults githubResults) {
                                 Log.v("qhl", githubResults.total_count + "");

                                 return Observable.from(githubResults.items);
                             }
                         }
                )
                .map(new Func1<Repo, Object>() {
                    @Override
                    public Object call(Repo repo) {

                        return repo.url;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.v("qhl", "url:" + o);
                    }
                });
    }
}
