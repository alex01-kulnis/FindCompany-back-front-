package com.example.findcompanyAPI.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.findcompanyAPI.Models.ConfirmVisit;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "FindCompanyDB.db";
    private static final int SCHEMA = 1; // версия базы данных

    public DBHelper(Context context) {
        super(context, DBNAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
   /*     db.execSQL("create Table Users ("
                + "id_user integer primary key autoincrement not null,"
                + "firstname text not null,                           "
                + "secondname    text not null,                          "
                + "login text unique,                                 "
                + "password text not null                           );"
        );
        db.execSQL("create table Events ("
                + "id_event integer primary key autoincrement not null,"
                + "id_user integer not null,                           "
                + "name_event text not null,                           "
                + "place_event text not null,                          "
                + "data_and_time_event  text not null,                    "
                + "max_participants_event   integer not null,             "
                + " foreign key(id_user) references Users(id_user)     "
                + " on delete cascade on update cascade              );"
        );
        db.execSQL("create table ParticipationInEvents ("
                + "id_event integer primary key autoincrement not null,           "
                + "id_user integer not null,                                      "
                + "name_event text not null,                                      "
                + "place_event text not null,                                     "
                + "data_and_time_event  Date not null,                               "
                + "max_participants_event   integer not null,                        "
                + "IsConfirm boolean not null,                                    "
                + " foreign key(id_user) references Users(id_user)                "
                + " on delete cascade on update cascade              );           "
        );*/
        db.execSQL(" create Table ConfirmVisiting ("
                +"id integer primary key autoincrement not null,"
                +"id_event integer  not null,"
                +"id_creator INTEGER not null,"
                +"id_user INTEGER not null,"
                +"name_event text not null,"
                +"place_event text not null,"
                +"data_and_time_event  text not null,"
                +"max_participants_event   integer not null,"
                +"secondname    text not null,"
                +" foreign key(id_user) references Users(id_user));"
        );

       db.execSQL(" create Table HistoryVisiting ("
               +"id integer primary key autoincrement not null,"
               +"id_event integer  not null,"
               +"id_creator INTEGER not null,"
               +"id_user INTEGER not null,"
               +"name_event text not null,"
               +"place_event text not null,"
               +"data_and_time_event  text not null,"
               +"max_participants_event   integer not null,"
               +" foreign key(id_user) references Users(id_user));"
               );
        db.execSQL("create Table Users ("
                + "id_user integer primary key autoincrement not null,"
                + "firstname text not null,                           "
                + "secondname    text not null,                          "
                + "login text unique,                                 "
                + "password text not null                           );"
        );
        /*db.execSQL("create Table VisitorsGroup ("
                + "id_event integer  not null,                            "
                + "id_user integer not null,                           "
                + "PRIMARY KEY (`id_event`, `id_user`),                     "
                + "FOREIGN KEY (id_user) REFERENCES Users(id_user) ON DELETE CASCADE,"
                + "FOREIGN KEY (id_event) REFERENCES Events(id_event) ON DELETE CASCADE);"
        );*/
        db.execSQL("create table Events ("
                + "id_event integer primary key autoincrement not null,"
                + "id_creator integer not null,                        "
                + "id_user integer not null,                           "
                + "name_event text not null,                           "
                + "place_event text not null,                          "
                + "data_and_time_event  text not null,                    "
                + "max_participants_event  integer not null);            "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists Users");
        onCreate(MyDB);
    }

    //registration
    public Boolean insertData(String login, String password, String firstame, String secondname   ){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", login);
        contentValues.put("password", password);
        contentValues.put("firstname", firstame);
        contentValues.put("secondname   ", secondname   );
        long result = MyDB.insert("Users",null, contentValues);
        if (result == 1) return  false;
        else return true;
    }

    public Boolean CheckUser(String login){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Users where login = ?", new String[] {login});
        if(cursor.getCount()>0)
            return  true;
        else
            return false;
    }

    public Boolean CheckPassword(String login, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Users where login = ? and password = ?",new String[] {login,password});
        if(cursor.getCount()>0)
            return  true;
        else
            return false;
    }

    //currentUser
    public int CurrentUser(String login) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select id_user from Users where login = ?", new String[] {login});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        return -1;
    }

    //currentSecond
    public String  currentSecondName   (String id) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select secondname    from Users where id_user = ?", new String[] {id});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String secondname    = cursor.getString(0);
            cursor.close();
            return secondname   ;
        }
        return "";
    }
    //registration

    //createEvent
    public void CreateEvent(Integer id_user,Integer id_creator, String name_event, String place_event, String evnt_date, Integer maxParticipacion) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user", id_user);
        contentValues.put("id_creator", id_creator);
        contentValues.put("name_event", name_event);
        contentValues.put("place_event", place_event);
        contentValues.put("data_and_time_event ", evnt_date);
        contentValues.put("max_participants_event  ", maxParticipacion);
        MyDB.insert("Events",null, contentValues);
    }

    public void CreateEventAdmin(Integer id_event,Integer id_user,Integer id_creator, String name_event, String place_event, String evnt_date, Integer maxParticipacion) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_event", id_event);
        contentValues.put("id_user", id_user);
        contentValues.put("id_creator", id_creator);
        contentValues.put("name_event", name_event);
        contentValues.put("place_event", place_event);
        contentValues.put("data_and_time_event ", evnt_date);
        contentValues.put("max_participants_event  ", maxParticipacion);
        MyDB.insert("HistoryVisiting",null, contentValues);
    }

    public String CurrentIdEvent(String name_event) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select id_event from Events where name_event = ?", new String[] {name_event});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String id_evnt = cursor.getString(0);
            cursor.close();
            return id_evnt;
        }
        return "";
    }
    //createEvent

    //findCompany
    public Cursor getEvents(SQLiteDatabase db) {
        return db.rawQuery("select id_event,id_user,id_creator, name_event, place_event, data_and_time_event , max_participants_event   from " + "Events" + ";", null);
    }

    public boolean repitEvent(String id_u,String id_eve) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor1 = MyDB.rawQuery("Select id_user from ConfirmVisiting where id_user = ? and id_event = ?", new String[] {id_u,id_eve});
        Cursor cursor2 = MyDB.rawQuery("Select id_user from HistoryVisiting where id_user = ? and id_event = ?", new String[] {id_u,id_eve});
        if (cursor1.getCount() > 0 || cursor2.getCount() > 0  )  {
//            cursor.moveToFirst();
//            int id = cursor.getInt(0);
//            cursor.close();
            return true;
        }
        return false;
    }

    public Cursor getParticularEvents(String name ) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.rawQuery("select id_event, id_user,id_creator, name_event, place_event, data_and_time_event , max_participants_event   from " +
                "Events where name_event like ?" + ";", new String[] {name});
    }

    public void AddConfirmStr(Integer id_event,Integer id_creator, Integer id_user,  String name_event, String place_event, String evntDate, Integer maxParticipacion,String secname) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_event", id_event);
        contentValues.put("id_user", id_user);
        contentValues.put("id_creator", id_creator);
        contentValues.put("name_event", name_event); 
        contentValues.put("place_event", place_event);
        contentValues.put("data_and_time_event ", evntDate);
        contentValues.put("max_participants_event  ", maxParticipacion);
        contentValues.put("secondname   ", secname);
        MyDB.insert("ConfirmVisiting",null, contentValues);
    }

    public int CountParticipants(Integer id_eve) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select id_event from HistoryVisiting where id_event = ? ", new String[] {String.valueOf(id_eve)});
        return cursor.getCount();
    }
    //findCompany

    //ConfirmActivity
    public Cursor getComfirmEvents(SQLiteDatabase db,String id_c) {
        return db.rawQuery("select id, id_event, id_user, id_creator, name_event, place_event, data_and_time_event , max_participants_event  ,secondname    from "
                + "ConfirmVisiting where id_creator = ?" + ";", new String[] {id_c});
    }

    public void confirmAppAndSend(Integer id_event, Integer id_user, Integer id_creator, String name_event, String place_event, String evntDate, Integer maxParticipacion ) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_event", id_event);
        contentValues.put("id_user", id_user);
        contentValues.put("id_creator", id_creator);
        contentValues.put("name_event", name_event);
        contentValues.put("place_event", place_event);
        contentValues.put("data_and_time_event ", evntDate);
        contentValues.put("max_participants_event  ", maxParticipacion);
        MyDB.insert("HistoryVisiting",null, contentValues);
    }

    public void deleteConfirm(SQLiteDatabase db, ConfirmVisit expenses) {
        db.delete("ConfirmVisiting", "id = ?", new String[] {expenses.getId()});
    }

    //HistoryActivity
    public Cursor getHistoryEvents(SQLiteDatabase db,String id) {
        return db.rawQuery("select id,id_event, id_user, id_creator, name_event, place_event, data_and_time_event , max_participants_event   from "
                + "HistoryVisiting where id_user = ?" + ";", new String[] {id});
    }

    public Cursor getStatistic(SQLiteDatabase db) {
        return db.rawQuery("SELECT id_event, name_event, COUNT(*) AS amount from "
                + "HistoryVisiting GROUP BY name_event ORDER BY id_event ASC" +  ";", new String[] {});
    }


    //provider
    public Cursor getUsers(SQLiteDatabase db) {
        return db.rawQuery("select id_user, firstname, secondname    from " + "Users" + ";", null);
    }
}
