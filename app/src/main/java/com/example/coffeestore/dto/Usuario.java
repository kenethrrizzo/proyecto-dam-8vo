package com.example.coffeestore.dto;

public class Usuario {

    public Usuario() {}
    private Integer id;

    private String nombres;
    private String apellidos;
    private String cedula;
    private String genero;
    private String numeroTelefonico;
    private String direccion;

    private String provincia;
    private String ciudad;
    private String password;


    // Getters y Setters
    public Integer getId() {return id;}
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {return apellidos;}
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String generos) {
        this.genero = generos;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }
    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword ( String password) {
        this.password = password;
    }
}
