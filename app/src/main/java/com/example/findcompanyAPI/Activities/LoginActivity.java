package com.example.findcompanyAPI.Activities;

import static com.example.findcompanyAPI.Config.baseRetrofitUrl;
import static com.example.findcompanyAPI.Config.appPreferencesName;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText login, password;
    Button auth;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.login1);
        password = (EditText) findViewById(R.id.password1);
        auth = (Button) findViewById(R.id.auth1);
        DB = new DBHelper(this);

        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Utils.hasConnection(LoginActivity.this)) {
                    Toast.makeText(LoginActivity.this, "No active networks... ", Toast.LENGTH_LONG).show();
                    return;
                }

                String loginn = login.getText().toString();
                String passwordd = password.getText().toString();

                if (loginn.equals("") || passwordd.equals("") ){
                    Toast.makeText(LoginActivity.this,"Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                else {
                    User candidate = new User(loginn,passwordd);

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

                    Call<User> call = apiService.auth(candidate);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(!response.isSuccessful()){
                                Log.d("Code", String.valueOf(response.code()));
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    String errorMessage = jObjError.getString("message");
                                    Toast.makeText(LoginActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
                                    return;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            SharedPreferences settings = getSharedPreferences(appPreferencesName, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString( "token", response.body().getToken());
                            editor.apply();


                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("gg","11");
                        }
                    });

                }
            }
        });
    }
}