package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.coffeestore.R;
import com.example.coffeestore.adapters.ProductAdapter;
import com.example.coffeestore.database.ProductDbHelper;
import com.example.coffeestore.dto.CategoriaProducto;
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

            // Establecer listener para manejar las selecciones del menú emergente
            popupMenu.setOnMenuItemClickListener(item -> {
                // Aquí puedes obtener el ID del elemento seleccionado y realizar las acciones necesarias
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

            // Mostrar el menú emergente
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
}