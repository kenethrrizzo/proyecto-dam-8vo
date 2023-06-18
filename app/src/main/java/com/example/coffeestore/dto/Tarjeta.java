package com.example.coffeestore.dto;

public class Tarjeta {
    private String numero;
    private String fechaDeExpiracion;
    private String cvv;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaDeExpiracion() {
        return fechaDeExpiracion;
    }

    public void setFechaDeExpiracion(String fechaDeExpiracion) {
        this.fechaDeExpiracion = fechaDeExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
