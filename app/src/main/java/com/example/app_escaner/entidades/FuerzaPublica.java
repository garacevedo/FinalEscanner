package com.example.app_escaner.entidades;

public class FuerzaPublica {


    private int id_fuerza_publica;
    private int identificacion;
    private String clave;
    private int login;
    private String nombre;
    private String apellidos;
    private String fuerza_publica;
    private String rango;
    private int id_fuerza;
    private String correo_electronico;

    public int getId_fuerza_publica() {
        return id_fuerza_publica;
    }

    public void setId_fuerza_publica(int id_fuerza_publica) {
        this.id_fuerza_publica = id_fuerza_publica;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFuerza_publica() {
        return fuerza_publica;
    }

    public void setFuerza_publica(String fuerza_publica) {
        this.fuerza_publica = fuerza_publica;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public int getId_fuerza() {
        return id_fuerza;
    }

    public void setId_fuerza(int id_fuerza) {
        this.id_fuerza = id_fuerza;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    @Override
    public String toString() {
        return "FuerzaPublica{" +
                "id_fuerza_publica=" + id_fuerza_publica +
                ", identificacion=" + identificacion +
                ", clave='" + clave + '\'' +
                ", login=" + login +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fuerza_publica='" + fuerza_publica + '\'' +
                ", rango='" + rango + '\'' +
                ", id_fuerza=" + id_fuerza +
                ", correo_electronico='" + correo_electronico + '\'' +
                '}';
    }
}
