package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeestore.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.coffeestore.adapters.ProductAdapter;
import com.example.coffeestore.database.ProductDbHelper;
import com.example.coffeestore.dto.CategoriaProducto;
import com.example.coffeestore.dto.Producto;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityProductos extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

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
        productAdapter.setOnItemClickListener(this);
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
                    if (producto.getTitulo().toUpperCase().contains(textoIngresado.toUpperCase())) {
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

        ImageButton buttonFilterProducts = findViewById(R.id.button_filterproducts);
        buttonFilterProducts.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(ActivityProductos.this, v);
            popupMenu.inflate(R.menu.menu_filter);

            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.filter_bebida_caliente) {
                    ProductAdapter productAdapter1 = new ProductAdapter(
                            getProductosFiltradosPorCategoria(CategoriaProducto.BEBIDA_CALIENTE),
                            ActivityProductos.this);
                    recyclerView.setAdapter(productAdapter1);
                } else if (itemId == R.id.filter_bebida_fria) {
                    ProductAdapter productAdapter1 = new ProductAdapter(
                            getProductosFiltradosPorCategoria(CategoriaProducto.BEBIDA_FRIA),
                            ActivityProductos.this);
                    recyclerView.setAdapter(productAdapter1);
                } else if (itemId == R.id.filter_panes) {
                    ProductAdapter productAdapter1 = new ProductAdapter(
                            getProductosFiltradosPorCategoria(CategoriaProducto.PANES),
                            ActivityProductos.this);
                    recyclerView.setAdapter(productAdapter1);
                } else if (itemId == R.id.filter_pasteles) {
                    ProductAdapter productAdapter1 = new ProductAdapter(
                            getProductosFiltradosPorCategoria(CategoriaProducto.PASTELES),
                            ActivityProductos.this);
                    recyclerView.setAdapter(productAdapter1);
                } else {
                    ProductAdapter productAdapter1 = new ProductAdapter(
                            productos,
                            ActivityProductos.this);
                    recyclerView.setAdapter(productAdapter1);
                }
                return true;
            });

            popupMenu.show();
        });

        ImageButton buttonMenuPrincipal = findViewById(R.id.button_main_menu);
        buttonMenuPrincipal.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(ActivityProductos.this, v);
            popupMenu.inflate(R.menu.menu_datos);

            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_ver_perfil) {
                    Intent intent = new Intent(this, ActivityConsultarUsuario.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_carrito_compras) {
                    Intent intent = new Intent(this, ActivityCarritoDeCompras.class);
                    startActivity(intent);
                    return true;
                }
                return true;
            });

            popupMenu.show();
        });
    }

    private List<Producto> getProductosFiltradosPorCategoria(CategoriaProducto categoria) {
        List<Producto> productosFiltrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getCategoria().equals(categoria)) {
                productosFiltrados.add(producto);
            }
        }
        return productosFiltrados;
    }

    @SuppressLint("MutatingSharedPrefs")
    @Override
    public void onItemClick(int productoId) {
        SharedPreferences sharedPreferences = getSharedPreferences("CarritoDeCompras", Context.MODE_PRIVATE);

        Set<String> productosIds = sharedPreferences.getStringSet("productos", new HashSet<>());
        if (productosIds.contains("0")) {
            productosIds.clear();
        }

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        productosIds.add(String.valueOf(productoId));
        editor.putStringSet("productos", productosIds);

        Toast.makeText(this, "Se agregó producto al carrito", Toast.LENGTH_SHORT).show();

        editor.apply();
    }

}