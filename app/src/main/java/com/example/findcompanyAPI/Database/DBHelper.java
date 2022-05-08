package com.example.findcompanyAPI.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.findcompanyAPI.Models.ConfirmVisit;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "FindCompanyDB.db";
    private static final int SCHEMA = 1; // версия базы данных

    public DBHelper(Context context) {
        super(context, DBNAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table HistoryVisiting (\n" +
               "               id integer primary key autoincrement not null,\n" +
               "               id_event integer  not null,\n" +
               "               id_creator integer not null,\n" +
               "               id_user integer not null,\n" +
               "               name_event text not null,\n" +
               "               place_event text not null,\n" +
               "               data_and_time_event text not null,\n" +
               "               max_participants_event integer not null)");

//        db.execSQL("create table " + "ff" + "(" + "_id"
//                + "integer primary key," + "name" + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists HistoryVisiting");
        onCreate(MyDB);
    }

    public void confirmAppAndSend(Integer id_event, Integer id_user, Integer id_creator, String name_event, String place_event, String data_and_time_event, Integer max_participants_event ) {
        Log.d("new","message");
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_event", id_event);
        contentValues.put("id_user", id_user);
        contentValues.put("id_creator", id_creator);
        contentValues.put("name_event", name_event);
        contentValues.put("place_event", place_event);
        contentValues.put("data_and_time_event ", data_and_time_event);
        contentValues.put("max_participants_event  ", max_participants_event);
        MyDB.insert("HistoryVisiting",null, contentValues);
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

    public void deleteAllStrings(SQLiteDatabase db) {
        db.delete("HistoryVisiting",null,null);
    }

    //provider
    public Cursor getUsers(SQLiteDatabase db) {
        return db.rawQuery("select id_user, firstname, secondname    from " + "Users" + ";", null);
    }
}
