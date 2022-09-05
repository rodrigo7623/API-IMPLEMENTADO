package com.api.desa.entidad;

public class ApiKey {
    private String apiKeyHMACSha256;

    public ApiKey() {
    }

    public ApiKey(String apiKeyHMACSha256) {
        this.apiKeyHMACSha256 = apiKeyHMACSha256;
    }

    public String getApiKeyHMACSha256() {
        return apiKeyHMACSha256;
    }

    public void setApiKeyHMACSha256(String apiKeyHMACSha256) {
        this.apiKeyHMACSha256 = apiKeyHMACSha256;
    }
}
