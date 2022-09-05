package com.api.desa.entidad;

public class ErrorJson {

    private String codigo;
    private String error;

    public ErrorJson() {
    }

    public ErrorJson(String codigo, String error) {

        this.codigo = codigo;
        this.error = error;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "{" +
                "codigo='" + codigo + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
