package com.example.w3d5ex01;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w3d5ex01.model.Result;
import com.example.w3d5ex01.util.RandomParser;
import com.squareup.picasso.Picasso;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private static final String SAVE_BTN ="Save";
    private static final String READ_BTN ="Get";
    private static final String LOOK_BTN ="Look";
    private String buttonClicked ="";


    private OkHttpClient client;
    private List<Result> results;
    private String first, last, title, email,all, picture, city;
    Button buttonMain, buttonSave, buttonLookUp;
    TextView textView,textToSave, detailsToSave;
    ImageView image;

    private DBHelper helper;
    private SQLiteDatabase database;

    byte [] encodeByte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();
        results = new ArrayList<>();
        helper = new DBHelper(this);
        database = helper.getWritableDatabase();

        image = (ImageView) findViewById(R.id.imgMainId);

        buttonMain = (Button) findViewById(R.id.btnMainId);
        buttonSave = (Button) findViewById(R.id.btnSaveMainId);
        buttonLookUp = (Button) findViewById(R.id.btnLookMainId);

        buttonMain.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonLookUp.setOnClickListener(this);
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

                            try{
                                title = result.getName().getTitle();
                                first = result.getName().getFirst();
                                last  = result.getName().getLast();
                                email = result.getEmail();
                                city = result.getLocation().getCity();
                                picture = result.getPicture().getThumbnail();

                                all = "Name: "+first+" \n"+"Last Name: "+last+" \n"+"Email: "+email+" \n"+"City: "+city;

                                textView = (TextView) findViewById(R.id.tvMainId);

                            }catch(Exception e){

                                e.printStackTrace();
                            }

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

    public void getData(View view) {

        Picasso.with(this).load(picture).resize(200, 200).into(image);

        textView.setText(all);

    }

    public void saveData(View view) {

        String path = "";



        if(picture != "" || picture != null){

             try{

                     ContentValues contents ;
                     contents = new ContentValues();
                     String valueUnderSubtitle = textView.getText().toString();

                     //picture = " Yes";

                     contents.put(FeedReaderContract.FeedEntries.COLUMN_NAME,first);
                     contents.put(FeedReaderContract.FeedEntries.COLUMN_LAST,last);
                     contents.put(FeedReaderContract.FeedEntries.COLUMN_EMAIL,email);
                     contents.put(FeedReaderContract.FeedEntries.COLUMN_CITY,city);
                     contents.put(FeedReaderContract.FeedEntries.COLUMN_IMAGE,picture);



                     if(contents != null){

                         long recordId = database.insert( FeedReaderContract.FeedEntries.TABLE_NAME, null,contents);

                         if(recordId > 0){
                             Toast.makeText(this, picture , Toast.LENGTH_SHORT).show();
                             //Log.d(TAG, "saveRecord: Record saved");
                         }
                         else{
                             Toast.makeText(this, "Not Saved" , Toast.LENGTH_SHORT).show();
                             //Log.d(TAG, "saveRecord: Record not save");
                         }


                     }

                 textView.setText("");
                 image.setImageBitmap(null);
                 picture = null;

             }catch(Exception e){

                 Toast.makeText(this, e.toString() , Toast.LENGTH_SHORT).show();
                 Log.e(TAG, "Exception :"+e.getMessage());
                 e.printStackTrace();

             }


        }
        else{

            Toast.makeText(this, "There is nothing to save yet" , Toast.LENGTH_SHORT).show();

        }



    }

    public void lookUp(View view){

        Toast.makeText(this, "Look up" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btnSaveMainId:
                buttonClicked = SAVE_BTN;
                saveData(view);
                break;
            case R.id.btnMainId:
                buttonClicked = READ_BTN;
                getData(view);
                break;
            case R.id.btnLookMainId:
                buttonClicked = LOOK_BTN;
                lookUp(view);
                break;

        }

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        database.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "onStart: ");
    }


    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Log.d(TAG, "onRestart: ");
    }
}
