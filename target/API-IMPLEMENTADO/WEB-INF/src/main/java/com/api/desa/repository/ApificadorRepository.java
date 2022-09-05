package com.api.desa.repository;

import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ApificadorRepository {

    List<ResponseEntity> obtenerNoticias(String textoBusqueda, String datosFoto) throws IOException;

    String generarHMAC();
}
