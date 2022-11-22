package com.example.app_escaner.entidades;

public class Ciudadanos {

    private int id_ciudadano;
    private int numero_identificacion;
    private String nombre;
    private String apellidos;
    private String fecha_nacimento;
    private String rh;
    private String grupo_sanguineo;
    private String sexo;
    private String requerido;
    public int iconImg;


    public Ciudadanos(){

    }
    public Ciudadanos(int numero_identificacion, String nombre, String requerido, int iconImg) {
        this.numero_identificacion = numero_identificacion;
        this.nombre = nombre;
        this.requerido = requerido;
        this.iconImg = iconImg;
    }

    public int getId_ciudadano() {
        return id_ciudadano;
    }

    public void setId_ciudadano(int id_ciudadano) {
        this.id_ciudadano = id_ciudadano;
    }

    public int getNumero_identificacion() {
        return numero_identificacion;
    }

    public void setNumero_identificacion(int numero_identificacion) {
        this.numero_identificacion = numero_identificacion;
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

    public String getFecha_nacimento() {
        return fecha_nacimento;
    }

    public void setFecha_nacimento(String fecha_nacimento) {
        this.fecha_nacimento = fecha_nacimento;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getGrupo_sanguineo() {
        return grupo_sanguineo;
    }

    public void setGrupo_sanguineo(String grupo_sanguineo) {
        this.grupo_sanguineo = grupo_sanguineo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRequerido() {
        return requerido;
    }

    public void setRequerido(String requerido) {
        this.requerido = requerido;
    }

    public int getIconImg() {
        return iconImg;
    }

    public void setIconImg(int iconImg) {
        this.iconImg = iconImg;
    }

    @Override
    public String toString() {
        return "Ciudadanos{" +
                "id_ciudadano=" + id_ciudadano +
                ", numero_identificacion=" + numero_identificacion +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fecha_nacimento='" + fecha_nacimento + '\'' +
                ", rh='" + rh + '\'' +
                ", grupo_sanguineo='" + grupo_sanguineo + '\'' +
                ", sexo='" + sexo + '\'' +
                ", requerido='" + requerido + '\'' +
                ", iconImg=" + iconImg +
                '}';
    }
}
