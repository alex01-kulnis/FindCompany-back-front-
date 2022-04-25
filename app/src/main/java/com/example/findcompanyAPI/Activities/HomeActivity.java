package com.example.findcompanyAPI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import com.example.findcompanyAPI.Database.DBHelper;
import com.example.findcompanyAPI.Models.Event;
import com.example.findcompanyAPI.R;

import java.util.ArrayList;

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

        Bundle arguments = getIntent().getExtras();
        id_U = ((Integer) arguments.get("id"));
        Log.d("myId", String.valueOf(id_U));

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

                if (search.length() == 0) {
                    setEvents();
                    return;
                }
                expensesList.clear();
                Cursor cursor = dbHelper.getParticularEvents(search.getText().toString());

                if (cursor.getCount() == 0) {
                    expensesStr = new String[]{" "};
                    return;
                }
                expensesStr = new String[cursor.getCount()];
                int i = 0;

                while (cursor.moveToNext()) {
                    Event expenses = new Event(
                            cursor.getInt(cursor.getColumnIndexOrThrow("id_event")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("id_user")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("id_creator")),
                            cursor.getString(cursor.getColumnIndexOrThrow("name_event")),
                            cursor.getString(cursor.getColumnIndexOrThrow("place_event")),
                            cursor.getString(cursor.getColumnIndexOrThrow("data_and_time_event")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("max_participants_event"))
                    );

                    expensesList.add(i, expenses);
                    expensesStr[i++] = expenses.getId_event() + " " + expenses.getId_user() + " " + expenses.getName_event()
                            + "-" + expenses.getPlace_event() + " " + expenses.getDataAndtime_event() + " " + expenses.getMaxParticipants_event();
                }
                customListAdapter.notifyDataSetChanged();
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

        return super.onOptionsItemSelected(item);
    }

    private void setEvents() {
        expensesList.clear();
        Cursor cursor = dbHelper.getEvents(db);

        if (cursor.getCount() == 0) {
            expensesStr = new String[]{" "};
            return;
        }
        expensesStr = new String[cursor.getCount()];
        int i = 0;

        while (cursor.moveToNext()) {
            Event expenses = new Event(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_event")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_user")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_creator")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name_event")),
                    cursor.getString(cursor.getColumnIndexOrThrow("place_event")),
                    cursor.getString(cursor.getColumnIndexOrThrow("data_and_time_event")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("max_participants_event"))
            );

            expensesList.add(i, expenses);
            expensesStr[i++] = expenses.getId_event() + " " + expenses.getId_user() + " " + expenses.getName_event()
                    + "-" + expenses.getPlace_event() + " " + expenses.getDataAndtime_event() + " " + expenses.getMaxParticipants_event();
        }
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
                    String id_event = ExpensesList.get(position).getId_event();
                    int id_Event = Integer.parseInt(id_event);
                    String id_user = ExpensesList.get(position).getId_user();
                    //int id_User = Integer.parseInt (id_user);

                    String maxParticipacion = ExpensesList.get(position).getMaxParticipants_event();
                    int MaxParticipacion = Integer.parseInt(maxParticipacion);

                    String id_User = Integer.toString(id_U);
                    boolean isUse = dbHelper.repitEvent(id_User, id_event);
                    Log.d("isUse", String.valueOf(isUse));
                    int countOfParticipant = dbHelper.CountParticipants(id_Event);
                    if (countOfParticipant >= MaxParticipacion)
                    {
                        Toast.makeText(HomeActivity.this, "Мест больше нету", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isUse) {
                        Toast.makeText(HomeActivity.this, "Вы уже участвуте или ожидаете", Toast.LENGTH_SHORT).show();
                    } else {
                        String surname = dbHelper.currentSecondName(Integer.toString(id_U));
                        String id_creator = ExpensesList.get(position).getId_creator();
                        int id_Creator = Integer.parseInt(id_creator);
                        String name_event = ExpensesList.get(position).getName_event();
                        String place_event = ExpensesList.get(position).getPlace_event();
                        String evnt_date = ExpensesList.get(position).getDataAndtime_event();
                        dbHelper.AddConfirmStr(id_Event, id_Creator, id_U, name_event, place_event, evnt_date, MaxParticipacion, surname);
                        Toast.makeText(HomeActivity.this, "Ожидайте подтверждения или отклонения", Toast.LENGTH_SHORT).show();
                    }
                    recreate();
                }
            });
            return view;
        }
    }
}