package com.api.desa.entidad;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NoticiaJson {

    private String fecha;
    private String enlace;
    @JsonProperty("enlace_foto")
    private String enlaceFoto;
    private String titulo;
    private String resumen;

    public NoticiaJson() {
    }

    public NoticiaJson(String fecha, String enlace, String enlaceFoto, String titulo, String resumen) {
        this.fecha = fecha;
        this.enlace = enlace;
        this.enlaceFoto = enlaceFoto;
        this.titulo = titulo;
        this.resumen = resumen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getEnlaceFoto() {
        return enlaceFoto;
    }

    public void setEnlaceFoto(String enlaceFoto) {
        this.enlaceFoto = enlaceFoto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    @Override
    public String toString() {
        return "{" +
                "fecha='" + fecha + '\'' +
                ", enlace='" + enlace + '\'' +
                ", enlaceFoto='" + enlaceFoto + '\'' +
                ", titulo='" + titulo + '\'' +
                ", resumen='" + resumen + '\'' +
                '}';
    }
}
