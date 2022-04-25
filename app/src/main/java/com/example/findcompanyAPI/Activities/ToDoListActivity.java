package com.example.findcompanyAPI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.findcompanyAPI.JSONHelper.JSONHelper;
import com.example.findcompanyAPI.JSONHelper.User;
import com.example.findcompanyAPI.R;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity {

    private ArrayAdapter<User> adapter;
    private List<User> users ;
    public EditText nameText,ageText;
    ListView listView ;
    private Integer id_U;
    private Button addU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_news);
        Bundle arguments = getIntent().getExtras();
        id_U = ((Integer) arguments.get("id"));

        nameText = findViewById(R.id.nameText);
        ageText = findViewById(R.id.ageText);
        listView = findViewById(R.id.list);
        users = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(adapter);


        addU = findViewById(R.id.addButton);
        addU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String mark = ageText.getText().toString();
                User user = new User(name, mark);
                users.add(user);
                adapter.notifyDataSetChanged();
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
        return super.onOptionsItemSelected(item);
    }


    public void save(View view){
        boolean result = JSONHelper.exportToJSON(this, users);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
    }

    public void open(View view){
        users = JSONHelper.importFromJSON(this);
        if(users!=null){
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
    }
}