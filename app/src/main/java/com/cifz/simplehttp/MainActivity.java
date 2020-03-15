package com.cifz.simplehttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cifz.simplehttp.simple.Call;
import com.cifz.simplehttp.simple.Callback;
import com.cifz.simplehttp.simple.Dispatcher;
import com.cifz.simplehttp.simple.OkHttpClient;
import com.cifz.simplehttp.simple.Request;
import com.cifz.simplehttp.simple.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityTag";
    private static final String PATH = "http://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadSimple(View view) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().dispatcher(new Dispatcher()).reCount(3).build();
        Request request = new Request.Builder().url(PATH).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("自定义OKHTTP请求失败....");
                logged("自定义OKHTTP请求失败...."+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("OKHTTP请求成功.... result:" + response.string());
                logged("OKHTTP请求成功.... result:" + response.string());
            }
        });
    }

    public void logged(String string){
        Log.e(TAG,string);
    }
}
