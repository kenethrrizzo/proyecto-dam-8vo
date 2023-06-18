package com.example.coffeestore.dto;

public class ProductoEnCarrito {
    private Producto producto;
    private Integer cantidad;

    public Producto getProducto() {
        return this.producto;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
