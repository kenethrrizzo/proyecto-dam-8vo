package com.example.coffeestore.dto;

public class ProductoEnCarrito {
    private Producto producto;
    private  Usuario usuario;
    private Integer cantidad;

    public Producto getProducto() {
        return this.producto;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }
    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
