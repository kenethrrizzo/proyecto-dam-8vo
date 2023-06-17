package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.coffeestore.R;
import com.example.coffeestore.adapters.ProductAdapter;
import com.example.coffeestore.database.ProductDbHelper;
import com.example.coffeestore.dto.Producto;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ActivityProductos extends AppCompatActivity {

    private List<Producto> productos;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        recyclerView = findViewById(R.id.recycler_view_products);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try (ProductDbHelper helper = new ProductDbHelper(this)) {
            helper.insertarProductosALaBaseDeDatos();
            productos = helper.obtenerTodosLosProductos();
        }

        ProductAdapter productAdapter = new ProductAdapter(productos, this);
        recyclerView.setAdapter(productAdapter);

        TextInputEditText txtUsuario = findViewById(R.id.txt_productsearch);

        txtUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoIngresado = s.toString();

                List<Producto> productosFiltrados = new ArrayList<>();
                for (Producto producto : productos) {
                    if (producto.getTitulo().contains(textoIngresado)) {
                        productosFiltrados.add(producto);
                    }
                }

                ProductAdapter productAdapter = new ProductAdapter(productosFiltrados, ActivityProductos.this);
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}