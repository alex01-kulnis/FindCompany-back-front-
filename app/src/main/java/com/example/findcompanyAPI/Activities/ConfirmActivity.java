package com.example.findcompanyAPI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.findcompanyAPI.Database.DBHelper;
import com.example.findcompanyAPI.Models.ConfirmVisit;
import com.example.findcompanyAPI.R;

import java.util.ArrayList;

public class ConfirmActivity extends AppCompatActivity {

    private Integer id_U;

    //View
    private ListView expensesListV;

    //Data
    CustomListAdapter customListAdapter;
    private String[] expensesStr;
    private ArrayList<ConfirmVisit> expensesList;
    private DBHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
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
        expensesListV = (ListView) findViewById(R.id.listViewEventsConfirm);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        setConfrimEvents();
        customListAdapter = new CustomListAdapter(this, expensesList);
        expensesListV.setAdapter(customListAdapter);

    }

    private void setConfrimEvents()
    {

        expensesList = new ArrayList<>();

        Cursor cursor = dbHelper.getComfirmEvents(db, id_U.toString());

        if(cursor.getCount() == 0) {
            expensesStr = new String[] {" "};
            return;
        }
        expensesStr = new String[cursor.getCount()];
        int i = 0;

        while(cursor.moveToNext()) {
            ConfirmVisit expenses = new ConfirmVisit(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_event")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_user")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_creator")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name_event")),
                    cursor.getString(cursor.getColumnIndexOrThrow("place_event")),
                    cursor.getString(cursor.getColumnIndexOrThrow("data_and_time_event")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("max_participants_event")),
                    cursor.getString(cursor.getColumnIndexOrThrow("secondname"))
            );

            expensesList.add(i, expenses);
            expensesStr[i++] = expenses.getId_event() + " " + expenses.getId_user() + " " + expenses.getId_creator() + " " + expenses.getName_event()
                    + "-" + expenses.getPlace_event() + " " + expenses.getDataAndtime_event() + " " + expenses.getMaxParticipants_event();
        }

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




    public class CustomListAdapter extends BaseAdapter {

        private ArrayList<ConfirmVisit> ExpensesList;
        private Context context;

        public CustomListAdapter(Context context, ArrayList<ConfirmVisit> students) {
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

        public void updateEventsList(ArrayList<ConfirmVisit> filteredTasks) {
            ExpensesList.clear();
            ExpensesList.addAll(filteredTasks);
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.event_confirm_item, null);

            TextView itemname = (TextView) view.findViewById(R.id.textViewNameE);
            TextView itemPlace = (TextView) view.findViewById(R.id.textViewPlaceE);
            TextView itemDate = (TextView) view.findViewById(R.id.textViewDateAndTime);
            TextView itemPar = (TextView) view.findViewById(R.id.textViewParticipants);
            TextView itemSec = (TextView) view.findViewById(R.id.textViewSecond);

            itemname.setText("Название: " + ExpensesList.get(position).getName_event());
            itemPlace.setText("Место: " + ExpensesList.get(position).getPlace_event());
            itemDate.setText("Время и дата: " + ExpensesList.get(position).getDataAndtime_event());
            itemPar.setText("Кол-во участников: " + ExpensesList.get(position).getMaxParticipants_event());
            itemSec.setText("Фамилия кандидата: " + ExpensesList.get(position).getSurname());

            Button buttonConf= (Button)view.findViewById(R.id.buttonConfirm);
            Button buttonCanc= (Button)view.findViewById(R.id.buttonCancel);

            buttonConf.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String id = ExpensesList.get(position).getId();
                    int Id = Integer.parseInt (id);
                    String id_event = ExpensesList.get(position).getId_event();
                    int id_Event = Integer.parseInt (id_event);
                    String id_user =  ExpensesList.get(position).getId_user();
                    int id_User = Integer.parseInt (id_user);
                    String id_creator = ExpensesList.get(position).getId_creator();
                    int id_Creator = Integer.parseInt (id_creator);
                    String maxParticipacion = ExpensesList.get(position).getMaxParticipants_event();
                    int MaxParticipacion = Integer.parseInt (maxParticipacion);
                    String name_event = ExpensesList.get(position).getName_event();
                    String place_event = ExpensesList.get(position).getPlace_event();
                    String evnt_date  = ExpensesList.get(position).getDataAndtime_event();
                    dbHelper.confirmAppAndSend(id_Event,id_User,id_Creator,name_event,place_event,evnt_date,MaxParticipacion);
                    dbHelper.deleteConfirm(db,ExpensesList.get(position));
                    recreate();
                }});

            buttonCanc.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    dbHelper.deleteConfirm(db,ExpensesList.get(position));
                    recreate();
                }});
            return view;
        }
    }
}