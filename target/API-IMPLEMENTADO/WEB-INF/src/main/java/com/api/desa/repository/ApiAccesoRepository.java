package com.api.desa.repository;

import com.api.desa.entidad.ApiAcceso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ApiAccesoRepository extends CrudRepository<ApiAcceso, Integer> {

    @Query(value = "SELECT api_key FROM api_acceso WHERE api_key = :apiKey ", nativeQuery = true)
    String obtenerApiKey(@Param("apiKey") String apiKey);
}
