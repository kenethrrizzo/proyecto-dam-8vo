package com.example.coffeestore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coffeestore.dto.ProductoEnCarrito;
import com.example.coffeestore.dto.Tarjeta;

import java.util.List;

public class CompraDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CoffeeStore";
    public static final int DATABASE_VERSION = 4;
    private static final String TABLE_COMPRA = "compra";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ID_USUARIO = "usuario_id";
    private static final String COLUMN_ID_PRODUCTO = "producto_id";
    private static final String COLUMN_CANTIDAD = "cantidad";
    private static final String COLUMN_NUM_TARJETA = "numero_tarjeta";
    private static final String COLUMN_FECHA_TARJETA = "fecha_tarjeta";
    private static final String COLUMN_CVV_TARJETA = "cvv_tarjeta";

    public CompraDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_COMPRA + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID_USUARIO + " INTEGER, "
                + COLUMN_ID_PRODUCTO + " INTEGER, "
                + COLUMN_CANTIDAD + " INTEGER, "
                + COLUMN_NUM_TARJETA + " TEXT, "
                + COLUMN_FECHA_TARJETA + " TEXT, "
                + COLUMN_CVV_TARJETA + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_COMPRA;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public void insertarCompra(List<ProductoEnCarrito> productos, Tarjeta tarjeta) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (ProductoEnCarrito producto : productos) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_USUARIO, 1);
            values.put(COLUMN_ID_PRODUCTO, producto.getProducto().getId());
            values.put(COLUMN_CANTIDAD, producto.getCantidad());
            values.put(COLUMN_NUM_TARJETA, tarjeta.getNumero());
            values.put(COLUMN_FECHA_TARJETA, tarjeta.getFechaDeExpiracion());
            values.put(COLUMN_CVV_TARJETA, tarjeta.getCvv());

            db.insert(TABLE_COMPRA, null, values);
        }
    }
}
