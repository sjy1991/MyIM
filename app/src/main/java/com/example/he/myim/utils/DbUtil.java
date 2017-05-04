package com.example.he.myim.utils;

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

    private DbUtil(){};

    public static volatile DbUtil sDbUtil;

    public static DbUtil getDbUtil(){
        if (sDbUtil == null) {
            synchronized (DbUtil.class){
                if (sDbUtil == null) {
                    sDbUtil = new DbUtil();
                }
            }
        }
        return sDbUtil;
    }

    public static void init(@NonNull Context context){
        sContactHelper = new ContactHelper(context, ContactHelper.DB_NAME, null,
                ContactHelper.VERSION_CODE);
    }

    public List<String> queryContacts(String username){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = sContactHelper.getWritableDatabase();
        Cursor query = db.query(ContactHelper.TABLE_NAME, new
                        String[]{ContactHelper.CONTACT},
                ContactHelper.USERNAME + "=?",
                new String[]{username},
                null,
                null,
                ContactHelper.CONTACT);

        if (query.moveToFirst()) {
            while (query.moveToNext()){
                String string = query.getString(query.getColumnIndex(ContactHelper.CONTACT));
                list.add(string);
            }
        }
        query.close();
        db.close();
        return list;
    }


}
