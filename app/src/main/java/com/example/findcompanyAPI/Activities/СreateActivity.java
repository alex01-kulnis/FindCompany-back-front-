package com.example.findcompanyAPI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findcompanyAPI.Database.DBHelper;
import com.example.findcompanyAPI.Helper.DateTimeHelper;
import com.example.findcompanyAPI.Helper.SystemHelper;
import com.example.findcompanyAPI.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class СreateActivity extends AppCompatActivity {

    private int id_U;
    private EditText Name_Event;
    private EditText Place_Event;
    private EditText event_date;
    private EditText Time_Start;
    private EditText Max_Paticipation;
    private Calendar date;
    private Calendar time;
    private Button createEvent;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Bundle arguments = getIntent().getExtras();
        id_U = ((Integer) arguments.get("id"));
        Log.d("myId", String.valueOf(id_U));

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        binding();
        setDateAndTime();
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

    private void setDateAndTime(){
        //date
        event_date.setInputType(InputType.TYPE_NULL);

        DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
            event_date.setText(DateTimeHelper.getGeneralDateFormat(year, monthOfYear + 1, dayOfMonth));
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH));

        event_date.setOnClickListener(view -> {
            SystemHelper.hideKeyboard(this);
            datePickerDialog.show();
        });
        event_date.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                SystemHelper.hideKeyboard(this);
                datePickerDialog.show();
            }
        });

        //time
        Time_Start.setInputType(InputType.TYPE_NULL);

        TimePickerDialog.OnTimeSetListener startT = (view, hourOfDay, minutes) -> {
            Time_Start.setText(DateTimeHelper.getGeneralTimeFormat(hourOfDay, minutes));
        };

        TimePickerDialog startWorkDialog = new TimePickerDialog(this, startT,
                time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE),true);

        Time_Start.setOnClickListener(view -> {
            SystemHelper.hideKeyboard(this);
            startWorkDialog.show();
        });
        Time_Start.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                SystemHelper.hideKeyboard(this);
                startWorkDialog.show();
            }
        });

        //Max_Participation
        Max_Paticipation.setInputType(InputType.TYPE_CLASS_NUMBER);

//        //createEventBttn
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_event = Name_Event.getText().toString();
                String place_event = Place_Event.getText().toString();
                String evnt_date = event_date.getText().toString();
                String time_start = Time_Start.getText().toString();
                String finalVariant = evnt_date + " " + time_start;
                String maxParticipacion = Max_Paticipation.getText().toString();
                int maxPatt = Integer.parseInt(maxParticipacion);

                if (name_event.equals("") || place_event.equals("") || evnt_date.equals("")|| time_start.equals("")|| maxParticipacion.equals("") ){
                    Toast toast = Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_SHORT);toast.show();
                }
                else {
                    Date datte = new Date();
                    SimpleDateFormat format = new SimpleDateFormat();
                    format.applyPattern("dd.MM.yyyy");
                    try {
                        datte= format.parse(evnt_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (datte.getTime() <= System.currentTimeMillis()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Некорректная дата", Toast.LENGTH_SHORT);toast.show();
                    }
                    else {
                        if (maxPatt < 2) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Минимальное кол-во 2 человека!", Toast.LENGTH_SHORT);toast.show();
                            return;
                        }
                        dbHelper.CreateEvent(id_U,id_U, name_event, place_event, finalVariant, maxPatt);
                        String id_ev = dbHelper.CurrentIdEvent(name_event);
                        Log.d("id_evnt", id_ev);
                        dbHelper.CreateEventAdmin(Integer.parseInt(id_ev),id_U,id_U, name_event, place_event, finalVariant, maxPatt);
                        clearFields();
                        Toast toast = Toast.makeText(getApplicationContext(), "Event добавлен", Toast.LENGTH_SHORT);toast.show();
                    }
                }

            }
        });
    }

    private void binding() {
        Name_Event =  findViewById(R.id.editTextName);
        Place_Event = findViewById(R.id.editTextPlace);
        event_date = findViewById(R.id.editTextDate);
        date = Calendar.getInstance();
        time = Calendar.getInstance();
        Time_Start = findViewById(R.id.editTextTime);
        Max_Paticipation = findViewById(R.id.editTextMaxParticipants);
        createEvent = findViewById(R.id.bttnCreateEvent);
    }

    private void clearFields() {
        Name_Event.setText("");
        Place_Event.setText("");
        event_date.setText("");
        Time_Start.setText("");
        Max_Paticipation.setText("");
    }
}