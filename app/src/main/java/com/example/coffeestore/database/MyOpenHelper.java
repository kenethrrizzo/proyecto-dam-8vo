package com.example.coffeestore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coffeestore.dto.Usuario;

public class MyOpenHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "CoffeeStore";
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_USUARIO = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRES = "nombres";
    public static final String COLUMN_APELLIDOS = "apellidos";
    public static final String COLUMN_CEDULA= "cedula";
    public static final String COLUMN_GENERO = "genero";
    public static final String COLUMN_NUMERO_TELEFONICO  = "numero_telefonico";
    public static final String COLUMN_DIRECCION= "direccion";
    public static final String COLUMN_PROVINCIA = "provincia";
    public static final String COLUMN_CIUDAD = "ciudad";
    private static final String COLUMN_PASSWORD = "password";

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL(COMMENTS_TABLE_CREATE);
        String createTableQuery = "CREATE TABLE " + TABLE_USUARIO + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOMBRES + " TEXT, "
                + COLUMN_APELLIDOS + " TEXT, "
                + COLUMN_CEDULA + " TEXT, "
                + COLUMN_GENERO + " TEXT, "
                + COLUMN_NUMERO_TELEFONICO  + " TEXT, "
                + COLUMN_DIRECCION + " TEXT, "
                + COLUMN_PROVINCIA + " TEXT, "
                + COLUMN_CIUDAD + " TEXT, "
                + COLUMN_PASSWORD+ " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_USUARIO;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public boolean insertData(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRES, usuario.getNombres());
        values.put(COLUMN_APELLIDOS, usuario.getApellidos());
        values.put(COLUMN_CEDULA, usuario.getCedula());
        values.put(COLUMN_GENERO, usuario.getGenero());
        values.put(COLUMN_NUMERO_TELEFONICO, usuario.getNumeroTelefonico());
        values.put(COLUMN_DIRECCION, usuario.getDireccion());
        values.put(COLUMN_PROVINCIA, usuario.getProvincia());
        values.put(COLUMN_CIUDAD, usuario.getCiudad());
        values.put(COLUMN_PASSWORD, usuario.getPassword());

        long resultado = db.insert(TABLE_USUARIO, null, values);

        return resultado != -1;

    }

    public boolean validarCredenciales(String nombreUsuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_NOMBRES + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {nombreUsuario, password};

        Cursor cursor = db.query(TABLE_USUARIO, null, selection, selectionArgs, null, null, null);

        boolean loginExitoso = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return loginExitoso;
    }
}
