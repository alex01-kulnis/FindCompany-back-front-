package com.example.findcompanyAPI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findcompanyAPI.Database.DBHelper;
import com.example.findcompanyAPI.R;

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
                String user = login.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("") ){
                    Toast.makeText(LoginActivity.this,"Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuserpass = DB.CheckPassword(user,pass);
                    if (checkuserpass == true){
                        Toast.makeText(LoginActivity.this,"Вход успешен", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        int id = DB.CurrentUser(user);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this,"Логин или пароль введены неверно, измените!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}