package com.example.qnhlli.myretrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIInterface service = retrofit.create(APIInterface.class);
        Call<TestModel> model = service.repo("Guolei1130");
        model.enqueue(new Callback<TestModel>() {
            @Override
            public void onResponse(Response<TestModel> response, Retrofit retrofit) {
                 System.out.println("---success:"+response.body().getLogin());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("---fail:");
            }
        });
    }
}
