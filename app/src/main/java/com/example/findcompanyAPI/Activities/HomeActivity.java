package com.example.findcompanyAPI.Activities;

import static com.example.findcompanyAPI.Config.appPreferencesName;
import static com.example.findcompanyAPI.Config.baseRetrofitUrl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findcompanyAPI.Api.api.ApiServices;
import com.example.findcompanyAPI.Database.DBHelper;
import com.example.findcompanyAPI.Models.Event;
import com.example.findcompanyAPI.Models.User;
import com.example.findcompanyAPI.R;
import com.example.findcompanyAPI.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private int id_U;
    private EditText search;

    //View
    private ListView expensesListV;

    //Data
    CustomListAdapter customListAdapter;
    private String[] expensesStr;
    private ArrayList<Event> expensesList = new ArrayList<>();
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private ArrayList<Event> filterEventList;
    private String[] filterexpensesStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //getSupportActionBar().hide();
        binding();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private void binding() {
        expensesListV = (ListView) findViewById(R.id.listViewEvents);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();
        customListAdapter = new CustomListAdapter(this, expensesList);
        expensesListV.setAdapter(customListAdapter);
        setEvents();

        Button buttonF = findViewById(R.id.buttonFind);
        search = findViewById(R.id.editTextFind);

        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Utils.hasConnection(HomeActivity.this)) {
                    Toast.makeText(HomeActivity.this, "No active networks... ", Toast.LENGTH_LONG).show();
                    return;
                }

                expensesList.clear();
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

                Event event = new Event(search.getText().toString());

                Call<List<Event>> call = apiService.getSearchEvents(event);

                call.enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        if (!response.isSuccessful()){
                            Log.d("Code", String.valueOf(response.code()));
                            return;
                        }

                        List<Event> events = response.body();

                        for (Event event :events){
                            Event result = new Event(
                                    event.getId_event(),
                                    event.getId_creator(),
                                    event.getName_event(),
                                    event.getPlace_event(),
                                    event.getDataAndtime_event(),
                                    event.getMaxParticipants_event()
                            );
                            int i = 0;
                            expensesList.add( i,result);
                        }
                        customListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        Log.d("gg","11");
                    }
                });

            }
        });
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
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        } else if (id == R.id.action_item1) {
            Intent intent = new Intent(getApplicationContext(), СreateActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        } else if (id == R.id.action_item2) {
            Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        } else if (id == R.id.action_item3) {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        } else if (id == R.id.action_item4) {
            Intent intent = new Intent(getApplicationContext(), ToDoListActivity.class);
            intent.putExtra("id", id_U);
            startActivity(intent);
        } else if (id == R.id.action_item5) {
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

    private void setEvents() {
        if(!Utils.hasConnection(HomeActivity.this)) {
            Toast.makeText(HomeActivity.this, "No active networks... ", Toast.LENGTH_LONG).show();
            return;
        }
        expensesList.clear();

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

        Call<List<Event>> call = apiService.getEvents();

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (!response.isSuccessful()){
                    Log.d("Code", String.valueOf(response.code()));
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getString("message");
                        Toast.makeText(HomeActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                List<Event> events = response.body();

                for (Event event :events){
                    Event result = new Event(
                            event.getId_event(),
                            event.getId_creator(),
                            event.getName_event(),
                            event.getPlace_event(),
                            event.getDataAndtime_event(),
                            event.getMaxParticipants_event()
                    );
                    int i = 0;
                    expensesList.add( i,result);
                    Log.d("id",event.getName_event());
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("gg","11");
            }
        });

        customListAdapter.notifyDataSetChanged();
    }

    public class CustomListAdapter extends BaseAdapter {

        private ArrayList<Event> ExpensesList;
        private Context context;

        public CustomListAdapter(Context context, ArrayList<Event> students) {
            this.context = context;
            this.ExpensesList = expensesList;
        }

        @Override
        public int getCount() {
            return this.ExpensesList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public void updateEventsList(ArrayList<Event> filteredTasks) {
            ExpensesList.clear();
            ExpensesList.addAll(filteredTasks);
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.events_item, null);

            TextView itemname = (TextView) view.findViewById(R.id.textViewNameE);
            TextView itemPlace = (TextView) view.findViewById(R.id.textViewPlaceE);
            TextView itemDate = (TextView) view.findViewById(R.id.textViewDateAndTime);
            TextView itemPar = (TextView) view.findViewById(R.id.textViewParticipants);

            itemname.setText("Название: " + ExpensesList.get(position).getName_event());
            itemPlace.setText("Место: " + ExpensesList.get(position).getPlace_event());
            itemDate.setText("Время и дата: " + ExpensesList.get(position).getDataAndtime_event());
            itemPar.setText("Кол-во участников: " + ExpensesList.get(position).getMaxParticipants_event());

            Button buttonS = (Button) view.findViewById(R.id.buttonSend);

            buttonS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!Utils.hasConnection(HomeActivity.this)) {
                        Toast.makeText(HomeActivity.this, "No active networks... ", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Integer id_event = ExpensesList.get(position).getId_event();
                    Integer id_creator = ExpensesList.get(position).getId_creator();
                    String name_event = ExpensesList.get(position).getName_event();
                    String place_event = ExpensesList.get(position).getPlace_event();
                    String evnt_date = ExpensesList.get(position).getDataAndtime_event();
                    Integer maxParticipacion = ExpensesList.get(position).getMaxParticipants_event();

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

                    Event event = new Event(id_event,id_creator,name_event,place_event,evnt_date,maxParticipacion);

                    Call<Event> call = apiService.apply(event);

                    call.enqueue(new Callback<Event>() {
                        @Override
                        public void onResponse(Call<Event> call, Response<Event> response) {
                            if (!response.isSuccessful()){
                                Log.d("Code", String.valueOf(response.code()));
                                Log.d("Error", String.valueOf(response.errorBody()));
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    String errorMessage = jObjError.getString("message");
                                    Toast.makeText(HomeActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
                                    return;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            Toast.makeText(HomeActivity.this,"Ожидайте подтверждения или отклонения",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Event> call, Throwable t) {
                            Log.d("gg","11");
                        }
                    });
                }
            });
            return view;
        }
    }
}