package com.api.desa.entidad;

import javax.persistence.*;

@Entity
@Table(name = "api_acceso")
public class ApiAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "password")
    private String password;

    @Column(name = "secret_key")
    private String secretKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "ApiAcceso{" +
                "id=" + id +
                ", apiKey='" + apiKey + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
