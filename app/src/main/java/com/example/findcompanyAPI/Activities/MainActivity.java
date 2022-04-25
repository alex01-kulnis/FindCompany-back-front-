package com.example.findcompanyAPI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findcompanyAPI.Database.DBHelper;
import com.example.findcompanyAPI.R;

public class MainActivity extends AppCompatActivity {

    EditText  firstname, secondname, login, password;
    Button auth, registration;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        firstname = (EditText) findViewById(R.id.firstname);
        secondname = (EditText) findViewById(R.id.secondname);

        auth = (Button) findViewById(R.id.auth);
        registration = (Button) findViewById(R.id.registration);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uniquelogin = login.getText().toString();
                String pass = password.getText().toString();
                String first = firstname.getText().toString();
                String second = secondname.getText().toString();

                if(uniquelogin.equals("") || pass.equals("") || first.equals("") || second.equals(""))
                    Toast.makeText(MainActivity.this,"Заполние все поля!",Toast.LENGTH_SHORT).show();
                else {
                        Boolean checkUser = dbHelper.CheckUser(uniquelogin);
                        if (checkUser == false) {
                            Boolean insert = dbHelper.insertData(uniquelogin,pass,first,second);
                            if(insert == true) {
                                Toast.makeText(MainActivity.this,"Регистрация прошла успешно",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                int id = dbHelper.CurrentUser(uniquelogin);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this,"Регистрация провалена",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Пользователь с таким логином уже есть, измените его!",Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}