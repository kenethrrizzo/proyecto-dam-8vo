package com.example.coffeestore.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coffeestore.dto.CategoriaProducto;
import com.example.coffeestore.dto.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CoffeeStore";
    public static final int DATABASE_VERSION = 5;
    private static final String TABLE_PRODUCTOS = "productos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRIPCION = "descripcion";
    private static final String COLUMN_TITULO = "titulo";
    private static final String COLUMN_PRECIO = "precio";
    private static final String COLUMN_URL_IMAGEN = "url_imagen";
    private static final String COLUMN_CATEGORIA = "categoria";


    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_PRODUCTOS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITULO + " TEXT, "
                + COLUMN_DESCRIPCION + " TEXT, "
                + COLUMN_PRECIO + " REAL, "
                + COLUMN_URL_IMAGEN + " TEXT, "
                + COLUMN_CATEGORIA + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_PRODUCTOS;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public void insertarProductosALaBaseDeDatos() {
        if (this.obtenerTodosLosProductos().size() > 0) {
            return;
        }

        List<Producto> productos = new ArrayList<>();

        Producto producto1 = new Producto(
                "Cafecrema",
                "Café caliente con crema",
                25.5,
                "https://www.sweetandcoffee.com.ec/wp-content/uploads/2019/07/SC-PROCESOS-DE-CALIDAD-2-400x250.jpg",
                CategoriaProducto.BEBIDA_CALIENTE
        );
        productos.add(producto1);

        Producto producto2 = new Producto(
                "Frappé de fresa",
                "Bebida fría con sabor a fresa",
                35.0,
                "https://www.recetasnestle.com.mx/sites/default/files/srh_recipes/cc0fef066530dcd705ad035c4fef8f7b.jpg",
                CategoriaProducto.BEBIDA_FRIA
        );
        productos.add(producto2);

        Producto producto3 = new Producto(
                "Croissant de chocolate",
                "Delicioso croissant relleno de chocolate",
                15.75,
                "https://images.hola.com/imagenes/cocina/recetas/20220125203459/croissant-chocolate-platano/1-43-806/croissant-choco-ado-t.jpg",
                CategoriaProducto.PANES
        );
        productos.add(producto3);

        Producto producto4 = new Producto(
                "Tarta de manzana",
                "Deliciosa tarta de manzana casera",
                40.0,
                "https://www.hogarmania.com/archivos/201402/5317-2-tarta-de-manzana-702-xl-668x400x80xX.jpg",
                CategoriaProducto.PASTELES
        );
        productos.add(producto4);

        Producto producto5 = new Producto(
                "Capuchino",
                "Café caliente con espuma de leche",
                28.5,
                "https://babycocina.com/wp-content/uploads/2020/06/cafe-capuchino.jpg",
                CategoriaProducto.BEBIDA_CALIENTE
        );
        productos.add(producto5);

        Producto producto6 = new Producto(
                "Limonada",
                "Refrescante limonada con hielo",
                20.0,
                "https://www.hersheyland.com/content/dam/Hersheyland_Mexico/es_mx/recipes/recipe-images/limonada.jpg",
                CategoriaProducto.BEBIDA_FRIA
        );
        productos.add(producto6);

        Producto producto7 = new Producto(
                "Pan de ajo",
                "Panecillos horneados con ajo y especias",
                12.0,
                "https://i.blogs.es/8e3bfe/pan_ajo/840_560.jpg",
                CategoriaProducto.PANES
        );
        productos.add(producto7);

        Producto producto8 = new Producto(
                "Pastel de chocolate",
                "Esponjoso pastel de chocolate con cobertura",
                45.0,
                "https://content-cocina.lecturas.com/medio/2023/03/23/el-mejor-pastel-de-chocolate_24bd9cda_1200x1200.jpg",
                CategoriaProducto.PASTELES
        );
        productos.add(producto8);

        Producto producto9 = new Producto(
                "Mocaccino",
                "Café caliente con chocolate y crema",
                30.0,
                "https://s3.ppllstatics.com/diariovasco/www/multimedia/202206/24/media/cortadas/mocacchino2-RzxK6jUZ6sUNGCintrcYBkP-1248x770@Diario%20Vasco.jpg",
                CategoriaProducto.BEBIDA_CALIENTE
        );
        productos.add(producto9);

        Producto producto10 = new Producto(
                "Smoothie de frutas",
                "Bebida fría batida con variedad de frutas",
                32.5,
                "https://www.cardamomo.news/__export/1656619064982/sites/debate/img/2022/06/30/smoothies.jpeg_423682103.jpeg",
                CategoriaProducto.BEBIDA_FRIA
        );
        productos.add(producto10);

        for (Producto producto : productos) {
            insertarProducto(producto);
        }
    }

    public void insertarProducto(Producto producto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, producto.getTitulo());
        values.put(COLUMN_DESCRIPCION, producto.getDescripcion());
        values.put(COLUMN_PRECIO, producto.getPrecio());
        values.put(COLUMN_URL_IMAGEN, producto.getUrlImagen());
        values.put(COLUMN_CATEGORIA, producto.getCategoria().toString());

        db.insert(TABLE_PRODUCTOS, null, values);
    }

    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String titulo = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO));
                @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRECIO));
                @SuppressLint("Range") String urlImagen = cursor.getString(cursor.getColumnIndex(COLUMN_URL_IMAGEN));
                @SuppressLint("Range") String categoriaString = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORIA));
                CategoriaProducto categoria = CategoriaProducto.valueOf(categoriaString);

                Producto producto = new Producto(titulo, descripcion, precio, urlImagen, categoria);
                producto.setId(id);
                productos.add(producto);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return productos;
    }

    public Producto obtenerProductoPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTOS + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        Producto producto = null;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String titulo = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO));
            @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION));
            @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRECIO));
            @SuppressLint("Range") String urlImagen = cursor.getString(cursor.getColumnIndex(COLUMN_URL_IMAGEN));
            @SuppressLint("Range") String categoriaString = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORIA));
            CategoriaProducto categoria = CategoriaProducto.valueOf(categoriaString);

            producto = new Producto(titulo, descripcion, precio, urlImagen, categoria);
            producto.setId(id);
        }

        cursor.close();

        return producto;
    }


}
