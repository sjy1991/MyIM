package com.example.he.myim.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by he on 17-5-4.
 */

public class ContactHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "contacts.db";
    public static final int VERSION_CODE = 1;
    public static final String TABLE_NAME = "t_contact";
    public static final String USERNAME = "username";
    public static final String CONTACT = "contact";


    public ContactHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, DB_NAME, factory, VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+TABLE_NAME+"(_id integer primary key, username varchar(20), contact varchar(20))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
