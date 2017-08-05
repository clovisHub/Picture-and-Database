package com.example.w3d5ex01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.w3d5ex01.model.Result;
import com.example.w3d5ex01.util.RandomParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    private OkHttpClient client;
    private List<Result> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();
        results = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Request request = new Request.Builder()
                .url("https://randomuser.me/api")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        results.clear();

                        JSONObject apiRes = new JSONObject(response.body().string());

                        for (int i = 0; i < apiRes.getJSONArray("results").length(); i++) {
                            results.add(RandomParser.parseResult(apiRes.getJSONArray("results").getJSONObject(i)));
                        }

                        for (Result result : results) {
                            Log.d(TAG, "onResponse: " + result);
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: ", e);
                    }

                } else {
                    Log.e(TAG, "onResponse: Application error");
                }
            }
        });
    }
}
