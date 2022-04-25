package com.example.findcompanyAPI.Activities;

import androidx.annotation.NonNull;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.findcompanyAPI.Database.DBHelper;
import com.example.findcompanyAPI.Models.EventHistory;
import com.example.findcompanyAPI.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private Integer id_U;

    //View
    private ListView expensesListV;

    //Data
    CustomListAdapter customListAdapter;
    private String[] expensesStr;
    private ArrayList<EventHistory> expensesList;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Bundle arguments = getIntent().getExtras();
        id_U = ((Integer) arguments.get("id"));
        binding();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }

    private void binding() {
        expensesListV = (ListView) findViewById(R.id.listViewEventsHistoty);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        setHistoryEvents();
        customListAdapter = new CustomListAdapter(this, expensesList);
        expensesListV.setAdapter(customListAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

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

    private void setHistoryEvents()
    {
        expensesList = new ArrayList<>();

        Cursor cursor = dbHelper.getHistoryEvents(db, id_U.toString());

        if(cursor.getCount() == 0) {
            expensesStr = new String[] {" "};
            return;
        }
        expensesStr = new String[cursor.getCount()];
        int i = 0;
        Log.d("myTag", "setEvents2");
        while(cursor.moveToNext()) {
            EventHistory expenses = new EventHistory(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_event")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_user")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_creator")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name_event")),
                    cursor.getString(cursor.getColumnIndexOrThrow("place_event")),
                    cursor.getString(cursor.getColumnIndexOrThrow("data_and_time_event")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("max_participants_event"))
            );

            expensesList.add(i, expenses);
            expensesStr[i++] = expenses.getId_event() + " " + expenses.getId_user() + " " + expenses.getId_creator() + " " + expenses.getName_event()
                    + "-" + expenses.getPlace_event() + " " + expenses.getDataAndtime_event() + " " + expenses.getMaxParticipants_event();
        }

    }

    public class CustomListAdapter extends BaseAdapter {

        private ArrayList<EventHistory> ExpensesList;
        private Context context;

        public CustomListAdapter(Context context, ArrayList<EventHistory> students) {
            this.context = context;
            this.ExpensesList = expensesList;
        }

        @Override
        public int getCount() {
            return this.ExpensesList.size();
        }

        @Override
        public Object getItem(int i) {return null;}

        @Override
        public long getItemId(int i) {return 0;}

        public void updateEventsList(ArrayList<EventHistory> filteredTasks) {
            ExpensesList.clear();
            ExpensesList.addAll(filteredTasks);
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.event_history_item, null);

            TextView itemname = (TextView) view.findViewById(R.id.textViewNameEHis);
            TextView itemPlace = (TextView) view.findViewById(R.id.textViewPlaceEHis);
            TextView itemDate = (TextView) view.findViewById(R.id.textViewDateAndTimeHis);
            TextView itemPar = (TextView) view.findViewById(R.id.textViewParticipantsHis);

            itemname.setText("Название: " + ExpensesList.get(position).getName_event());
            itemPlace.setText("Место: " + ExpensesList.get(position).getPlace_event());
            itemDate.setText("Время и дата: " + ExpensesList.get(position).getDataAndtime_event());
            itemPar.setText("Кол-во участников: " + ExpensesList.get(position).getMaxParticipants_event());

            return view;
        }
    }

}