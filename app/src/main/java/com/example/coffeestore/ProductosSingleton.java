package com.example.coffeestore;

import com.example.coffeestore.dto.ProductoEnCarrito;

import java.util.ArrayList;
import java.util.List;

public class ProductosSingleton {
    private static ProductosSingleton instancia;
    private List<ProductoEnCarrito> listaProductos;

    private ProductosSingleton() {
        listaProductos = new ArrayList<>();
    }

    public static ProductosSingleton getInstancia() {
        if (instancia == null) {
            instancia = new ProductosSingleton();
        }
        return instancia;
    }

    public List<ProductoEnCarrito> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoEnCarrito> listaProductos) {
        this.listaProductos = listaProductos;
    }
}
