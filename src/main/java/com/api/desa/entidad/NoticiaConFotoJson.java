package com.api.desa.entidad;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NoticiaConFotoJson extends NoticiaJson {

    @JsonProperty("contenido_foto")
    private String contenidoFoto;
    @JsonProperty("content_type_foto")
    private String contentType;

    public NoticiaConFotoJson(String fecha, String enlace, String enlaceFoto, String titulo, String resumen, String contenidoFoto, String contentType) {
        super(fecha, enlace, enlaceFoto, titulo, resumen);
        this.contenidoFoto = contenidoFoto;
        this.contentType = contentType;
    }

    public String getContenidoFoto() {
        return contenidoFoto;
    }

    public void setContenidoFoto(String contenidoFoto) {
        this.contenidoFoto = contenidoFoto;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "{" +
                "fecha='" + super.getFecha() + '\'' +
                ", enlace='" + super.getEnlace() + '\'' +
                ", enlaceFoto='" + super.getEnlaceFoto() + '\'' +
                ", titulo='" + super.getTitulo() + '\'' +
                ", resumen='" + super.getResumen() + '\'' +
                ", contenidoFoto='" + contenidoFoto + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
