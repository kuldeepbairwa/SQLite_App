package com.kuldeep.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseConn extends SQLiteOpenHelper {


    private static final String dbname = "signup.db";

    public DatabaseConn(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, username TEXT, password TEXT)";

        sqLiteDatabase.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  users");

        onCreate((sqLiteDatabase));

    }


    public boolean insertData(String name, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("name", name);
        c.put("username", username);
        c.put("password", password);

        long r = db.insert("users", null, c);
        if (r == 1) return false;
        else return true;
    }


    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        return cursor;
    }


    public boolean updateData(String name, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("name", name);
        c.put("password", password);

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});

        if (cursor.getCount() > 0) {
            long r = db.update("users", c, "username=?", new String[]{username});
            if (r == -1) return false;

            else return true;
        }
        else
            return false;


    }


    public boolean deleteData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});

        if (cursor.getCount() > 0) {
            long r = db.delete("users","username=?", new String[]{username});
            if (r == -1) return false;

            else return true;
        }
        else
            return false;


    }
}
