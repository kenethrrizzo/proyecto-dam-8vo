package com.example.coffeestore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "User.db";
    public static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "User";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_APELLIDO = "apellidos";
    public static final String COLUMN_CEDULA= "cedula";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_GENERO = "genero";
    public static final String COLUMN_PROVINCIA = "provincia";
    public static final String COLUMN_CIUDAD = "ciudad";
    public static final String COLUMN_DIRECCION= "direccion";

    private static final String COLUMN_PASSWORD = "password";

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL(COMMENTS_TABLE_CREATE);
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_APELLIDO + " TEXT, "
                + COLUMN_CEDULA + " TEXT, "
                + COLUMN_GENERO + " TEXT, "
                //  + COLUMN_FECHA + " TEXT, "
                + COLUMN_PHONE + " TEXT, "
                + COLUMN_DIRECCION + " TEXT, "
                + COLUMN_PROVINCIA + " TEXT, "
                + COLUMN_CIUDAD + " TEXT, "
                + COLUMN_PASSWORD+ " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }


    public boolean insertData(String name, String apellidos, int cedula, String genero, int phone, String direccion, String provincia, String ciudad, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_APELLIDO, apellidos);
        contentValues.put(COLUMN_CEDULA, cedula);
        contentValues.put(COLUMN_GENERO, genero);
        contentValues.put(COLUMN_PHONE, phone);
        contentValues.put(COLUMN_DIRECCION, direccion);
        contentValues.put(COLUMN_PROVINCIA, provincia);
        contentValues.put(COLUMN_CIUDAD, ciudad);
        contentValues.put(COLUMN_PASSWORD, password);


        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }
}
