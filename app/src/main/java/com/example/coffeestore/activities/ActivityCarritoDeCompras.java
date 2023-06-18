package com.example.coffeestore.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeestore.ProductosSingleton;
import com.example.coffeestore.R;
import com.example.coffeestore.adapters.ShoppingCartAdapter;
import com.example.coffeestore.database.ProductDbHelper;
import com.example.coffeestore.dto.Producto;
import com.example.coffeestore.dto.ProductoEnCarrito;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityCarritoDeCompras extends AppCompatActivity implements ShoppingCartAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ShoppingCartAdapter shoppingCartAdapter;
    private TextView totalPrice;

    @SuppressLint({"SetTextI18n", "MutatingSharedPrefs"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_de_compras);

        recyclerView = findViewById(R.id.recycler_view_products_in_cart);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<ProductoEnCarrito> productos = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("CarritoDeCompras", Context.MODE_PRIVATE);
        Set<String> productosIds = sharedPreferences.getStringSet("productos", new HashSet<>());
        if (productosIds.contains("0")) {
            productosIds.clear();
        }
        for (String productoId : productosIds) {
            try (ProductDbHelper helper = new ProductDbHelper(this)) {
                Producto producto = helper.obtenerProductoPorId(Integer.parseInt(productoId));
                ProductoEnCarrito productoEnCarrito = new ProductoEnCarrito();
                productoEnCarrito.setProducto(producto);
                productoEnCarrito.setCantidad(1);
                productos.add(productoEnCarrito);
            }
        }

        shoppingCartAdapter = new ShoppingCartAdapter(productos, this);
        shoppingCartAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(shoppingCartAdapter);

        totalPrice = findViewById(R.id.text_product_quantity);
        if (shoppingCartAdapter.getProductList().size() > 0) {
            totalPrice.setText("Precio total: $" + getTotalPrice(shoppingCartAdapter.getProductList()));
        }
    }

    private double getTotalPrice(List<ProductoEnCarrito> productos) {
        double total = 0;
        for (ProductoEnCarrito producto : productos) {
            total += producto.getProducto().getPrecio() * producto.getCantidad();
        }
        return total;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public ProductoEnCarrito plusClick(ProductoEnCarrito productoEnCarrito) {
        productoEnCarrito.setCantidad(
                productoEnCarrito.getCantidad() + 1
        );
        totalPrice.setText("Precio total: $" + getTotalPrice(shoppingCartAdapter.getProductList()));
        return productoEnCarrito;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public ProductoEnCarrito minusClick(ProductoEnCarrito productoEnCarrito) {
        if (productoEnCarrito.getCantidad() > 1) {
            productoEnCarrito.setCantidad(
                    productoEnCarrito.getCantidad() - 1
            );
        }
        totalPrice.setText("Precio total: $" + getTotalPrice(shoppingCartAdapter.getProductList()));
        return productoEnCarrito;
    }

    public void regresar(View v) {
        Intent intent = new Intent(v.getContext(), ActivityProductos.class);
        startActivity(intent);
    }

    public void comprar(View v) {
        Intent intent = new Intent(v.getContext(), ActivityPago.class);
        ProductosSingleton datosCompartidos = ProductosSingleton.getInstancia();
        datosCompartidos.setListaProductos(shoppingCartAdapter.getProductList());
        startActivity(intent);
    }
}
