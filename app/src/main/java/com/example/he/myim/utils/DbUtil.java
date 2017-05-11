package com.example.he.myim.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.he.myim.db.ContactHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by he on 17-5-4.
 */

public class DbUtil {

    private static ContactHelper sContactHelper;

    private DbUtil() {
    }

    ;

    public static volatile DbUtil sDbUtil;

    public static DbUtil getDbUtil() {
        if (sDbUtil == null) {
            synchronized (DbUtil.class) {
                if (sDbUtil == null) {
                    sDbUtil = new DbUtil();
                }
            }
        }
        return sDbUtil;
    }

    public static void init(@NonNull Context context) {
        sContactHelper = new ContactHelper(context, ContactHelper.DB_NAME, null,
                ContactHelper.VERSION_CODE);
    }

    public static List<String> queryContacts(String username) {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = sContactHelper.getReadableDatabase();
        Cursor query = db.query(ContactHelper.TABLE_NAME, new
                        String[]{ContactHelper.CONTACT},
                ContactHelper.USERNAME + "=?",
                new String[]{username},
                null,
                null,
                ContactHelper.CONTACT);

            while (query.moveToNext()) {
                String string = query.getString(query.getColumnIndex(ContactHelper.CONTACT));
                list.add(string);
            }


        query.close();
        db.close();
        return list;
    }

    public static void updateContact(String username, List<String> contacts) {
        SQLiteDatabase db = sContactHelper.getWritableDatabase();
        db.delete(ContactHelper.TABLE_NAME, ContactHelper.USERNAME+ "=?", new String[]{username});
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ContactHelper.USERNAME, username);
            for (String contact : contacts) {
                contentValues.put(ContactHelper.CONTACT, contact);
                db.insert(ContactHelper.TABLE_NAME, null, contentValues);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }


    }


}
