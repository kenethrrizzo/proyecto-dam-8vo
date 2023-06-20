package com.example.coffeestore.dto;

import androidx.annotation.NonNull;

public class Compra {
    private String idUsuario, idProducto, cantidad;
    private Tarjeta tarjeta;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    @NonNull
    @Override
    public String toString() {
        return this.idUsuario + ";" + this.idProducto + ";" + this.cantidad +
                ";" + this.tarjeta.getNumero() + ";" + this.tarjeta.getFechaDeExpiracion() +
                ";" + this.tarjeta.getCvv();
    }
}
