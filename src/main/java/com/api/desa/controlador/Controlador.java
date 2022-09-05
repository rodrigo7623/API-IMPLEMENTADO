package com.api.desa.controlador;

import com.api.desa.entidad.ApiAcceso;
import com.api.desa.entidad.ApiKey;
import com.api.desa.entidad.NoticiaConFotoJson;
import com.api.desa.repository.ApiAccesoRepository;
import com.api.desa.apificador.Apificar;
import com.api.desa.entidad.ErrorJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Context;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controlador {

    @Autowired
    private Apificar api;

    @Autowired
    private ApiAccesoRepository apiAccesoRepository;

    @GetMapping(value = "/consulta", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @ApiOperation(value = "Obtiene el listado de noticias relacionadas a un texto dado.", notes = "Esta consulta solo la puede realizar un usuario autorizado.")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Parámetros inválidos"),
    @ApiResponse(code = 403, message = "No autorizado"),
    @ApiResponse(code = 404, message = "No se encuentran noticias para el texto"),
    @ApiResponse(code = 500, message = "Error interno del servidor")})
    public ResponseEntity test(@Context HttpServletRequest httpRequest,
                               @RequestParam(value = "q") String q,
                               @RequestParam(value = "f", defaultValue = "false") String f,
                               @RequestHeader("x-api-key") String apiKey) {
        String accept = httpRequest.getHeader("Accept");
        apiKey = httpRequest.getHeader("x-api-key");
        if (!apiKeyValido(apiKey)) {
            return respuestaAccept(accept, "g103", "No autorizado", HttpStatus.FORBIDDEN);
        }
        if (q == null || q.isEmpty()) {
            return respuestaAccept(accept, "g268", "Parámetros inválidos", HttpStatus.BAD_REQUEST);
        }
        List<ResponseEntity> respuesta;
        try {
            respuesta = api.obtenerNoticias(q, f);
        } catch (IOException e) {
            System.out.printf(e.getMessage());
            return respuestaAccept(accept, "g100", "Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (respuesta.isEmpty())
            return respuestaAccept(accept, "g267", "No se encuentran noticias para el texto: {" + q + "}", HttpStatus.NOT_FOUND);
        if (accept == null || accept.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return new ResponseEntity(respuesta, HttpStatus.OK);
        }else {
            List<String> nuevaRespuesta = new ArrayList<>();
            switch (accept) {
                case MediaType.TEXT_HTML_VALUE:
                    StringBuilder sb = new StringBuilder();
                    for (Object o : respuesta) {
                        sb.append(o.toString());
                    }
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    try {
                        String obj = ow.writeValueAsString(sb.toString());
                        return new ResponseEntity(obj, HttpStatus.OK);
                    } catch (JsonProcessingException e) {
                        System.out.println(e.getMessage());
                    }
                case MediaType.APPLICATION_XML_VALUE:
                    ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    for (Object o : respuesta) {
                        try {
                            String obj = ow.writeValueAsString(o.toString());
                            nuevaRespuesta.add(obj);
                        } catch (JsonProcessingException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    return new ResponseEntity(nuevaRespuesta, HttpStatus.OK);
                case MediaType.TEXT_PLAIN_VALUE:
                    for (Object o : respuesta) {
                        nuevaRespuesta.add(o.toString());
                    }
                    return new ResponseEntity(nuevaRespuesta.toString(), HttpStatus.OK);
            }
        }
        return null;
    }

    private ResponseEntity respuestaAccept(String accept, String codigo, String error, HttpStatus status) {
        if (accept == null || accept.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) return new ResponseEntity(new ErrorJson(codigo,error), status);
        else {
            switch (accept) {
                case MediaType.TEXT_HTML_VALUE:
                case MediaType.APPLICATION_XML_VALUE:
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    try {
                        String obj = ow.writeValueAsString(new ErrorJson(codigo, error));
                        return new ResponseEntity(obj, status);
                    } catch (JsonProcessingException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case MediaType.TEXT_PLAIN_VALUE:
                    return new ResponseEntity(new ErrorJson(codigo, error).toString(), status);
            }
        }
        return null;
    }

    private boolean apiKeyValido(String apiKey) {
        String apiKeyDB = apiAccesoRepository.obtenerApiKey(apiKey);
        return apiKeyDB != null;
    }

    @GetMapping(value = "/obtenerAPIKey", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Genera el api_key firmado con HMAC-SHA256.", notes = "Utiliza una contraseña y una clave para generar el api_key")
    public ApiKey obtenerAPIKey(@Context HttpServletResponse response) {
        ApiKey ak = new ApiKey(api.generarHMAC());
        response.setStatus(200);
        return ak;
    }
}
