package com.example.findcompanyAPI.Activities;

import static com.example.findcompanyAPI.Config.appPreferencesName;
import static com.example.findcompanyAPI.Config.baseRetrofitUrl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.findcompanyAPI.Api.api.ApiServices;
import com.example.findcompanyAPI.Database.DBHelper;
import com.example.findcompanyAPI.Models.EventHistory;
import com.example.findcompanyAPI.Models.EventStatistics;
import com.example.findcompanyAPI.R;
import com.example.findcompanyAPI.Utils.Utils;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatisticActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        binding();
    }


    private void binding() {
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();
        if(!Utils.hasConnection(StatisticActivity.this)) {
            Cursor cursor = dbHelper.getStatistic(db);

            AnimatedPieView animatedPieView = findViewById(R.id.animatedPieView2);
            AnimatedPieViewConfig config = new AnimatedPieViewConfig();
            Float angle = -90F;
            config.startAngle(angle).duration(1000).drawText(true).strokeMode(false).textSize(30f);
//            config.addData(new SimplePieInfo(13, getRandomColor(),
//                    "cursor.getString(1) "));
            while (cursor.moveToNext()){
                Log.d("TAG1", String.valueOf(cursor.getColumnIndexOrThrow("amount")));
                Log.d("TAG2", cursor.getString(2));
                config.addData(new SimplePieInfo(cursor.getColumnIndexOrThrow("amount"),
                        getRandomColor(),
                        cursor.getString(1)));
            }
            cursor.close();

            animatedPieView.applyConfig(config);
            animatedPieView.start();
        }
        else {
            dbHelper.deleteAllStrings(db);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseRetrofitUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiServices apiService = retrofit.create(ApiServices.class);

            Call<List<EventHistory>> call = apiService.getHistoryEvents();

            call.enqueue(new Callback<List<EventHistory>>() {
                @Override
                public void onResponse(Call<List<EventHistory>> call, Response<List<EventHistory>> response) {
                    if (!response.isSuccessful()){
                        Log.d("Code", String.valueOf(response.code()));
                        return;
                    }

                    List<EventHistory> events = response.body();
                    Log.d("eve", String.valueOf(events));

                    for (EventHistory event : events){
                        dbHelper.confirmAppAndSend(event.getId_event(),event.getId_user(),event.getId_creator(),event.getName_event(),event.getPlace_event(),
                                event.getDataAndtime_event(), event.getMaxParticipants_event());
                    }
                }

                @Override
                public void onFailure(Call<List<EventHistory>> call, Throwable t) {
                    Log.d("gg","11");
                }
            });

            Call<List<EventStatistics>> callStat = apiService.getStatistics();

            AnimatedPieView animatedPieView = findViewById(R.id.animatedPieView2);
            AnimatedPieViewConfig config = new AnimatedPieViewConfig();
            Float angle = -90F;
            config.startAngle(angle).duration(1000).drawText(true).strokeMode(false).textSize(30f);

            callStat.enqueue(new Callback<List<EventStatistics>>() {
                @Override
                public void onResponse(Call<List<EventStatistics>> call, Response<List<EventStatistics>> response) {
                    if (!response.isSuccessful()){
                        Log.d("Code", String.valueOf(response.code()));
                        return;
                    }

                    List<EventStatistics> events = response.body();

                    for (EventStatistics event : events){
                        config.addData(new SimplePieInfo(event.getAmount(), getRandomColor(), event.getName_event()));
                    }
                    animatedPieView.applyConfig(config);
                    animatedPieView.start();
                }

                @Override
                public void onFailure(Call<List<EventStatistics>> call, Throwable t) {
                    Log.d("gg","11");
                }
            });
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}