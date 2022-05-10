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
import java.text.DecimalFormat;
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

public class DiagramActivity extends AppCompatActivity {
    private Integer id_U;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);
        Bundle arguments = getIntent().getExtras();
        id_U = ((Integer) arguments.get("id"));
        binding();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_item) {
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        }
        else if (id == R.id.action_item1) {
            Intent intent = new Intent(getApplicationContext(), СreateActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        }
        else if (id == R.id.action_item2) {
            Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        }
        else if (id == R.id.action_item3) {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        }
        else if (id == R.id.action_item4) {
            Intent intent = new Intent(getApplicationContext(), ToDoListActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        }
        else if (id == R.id.action_item5) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        }
        else if (id == R.id.action_item6) {
            Intent intent = new Intent(getApplicationContext(), DiagramActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void binding() {
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();
        if(!Utils.hasConnection(DiagramActivity.this)) {
            Cursor cursor = dbHelper.getStatistic(db);
            Cursor cursor2 = dbHelper.getStatistic(db);

            AnimatedPieView animatedPieView = findViewById(R.id.animatedPieView);
            AnimatedPieViewConfig config = new AnimatedPieViewConfig();
            Float angle = -90F;
            config.startAngle(angle).duration(1000).drawText(true).strokeMode(false).textSize(30f);

            int i = 0;
            while (cursor2.moveToNext()){
                i+=cursor2.getColumnIndexOrThrow("amount");
            }
            cursor2.close();

            while (cursor.moveToNext()){
                double amount = cursor.getColumnIndexOrThrow("amount");
                double percent = (amount * 100) / i;
                String formattedDouble = new DecimalFormat("#0.00").format(percent);
                String label = cursor.getString(1) + ", " + formattedDouble + "%";
                config.addData(new SimplePieInfo(cursor.getColumnIndexOrThrow("amount"),
                        getRandomColor(),
                        label));
            }
            cursor.close();

            animatedPieView.applyConfig(config);
            animatedPieView.start();
        }
        else {
            dbHelper.deleteAllStrings(db);
            SharedPreferences settings = getSharedPreferences(appPreferencesName, Context.MODE_PRIVATE);
            String finalresult = "Bearer " + settings.getString("token","");

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request originalRequest = chain.request();
                            Request newRequest = originalRequest.newBuilder()
                                    .header("Authorization", finalresult)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseRetrofitUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
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

                    for (EventHistory event : events){
                        dbHelper.confirmAppAndSend(event.getId(),event.getId_event(),event.getId_user(),event.getId_creator(),event.getName_event(),event.getPlace_event(),
                                event.getDataAndtime_event(), event.getMaxParticipants_event());
                    }
                }

                @Override
                public void onFailure(Call<List<EventHistory>> call, Throwable t) {
                    Log.d("gg","11");
                }
            });

            Call<List<EventStatistics>> callStat = apiService.getStatistics();

            AnimatedPieView animatedPieView = findViewById(R.id.animatedPieView);
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

                    int i = 0;
                    for (EventStatistics event : events){
                        i +=event.getAmount();
                    }

                    for (EventStatistics event : events){
                        double percent = (event.getAmount().doubleValue() * 100) / i;
                        String formattedDouble = new DecimalFormat("#0.00").format(percent);
                        String label = event.getName_event() + ", " + formattedDouble + "%";

                        config.addData(new SimplePieInfo(event.getAmount(), getRandomColor(), label));
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