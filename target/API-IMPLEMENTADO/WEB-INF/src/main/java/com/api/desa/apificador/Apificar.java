package com.api.desa.apificador;

import com.api.desa.repository.ApificadorRepository;
import com.api.desa.entidad.NoticiaConFotoJson;
import com.api.desa.entidad.NoticiaJson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Apificar implements ApificadorRepository {

    private final String URL_ABC = "https://www.abc.com.py";
    private final String URL_BUSCADOR_ABC = "https://www.abc.com.py/buscar/";

    @Override
    public List<ResponseEntity> obtenerNoticias(String textoBusqueda, String foto) throws IOException {
        List listaRespuestaNoticias = new ArrayList<>();
        URL buscadorAbc = new URL(URL_BUSCADOR_ABC + textoBusqueda);
        URLConnection coneccionAbc = buscadorAbc.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(coneccionAbc.getInputStream()));
        String str;
        String resultado = "";
        int c = 1;
        while ((str = br.readLine()) != null) {
            c++;
            if (c == 4) {
                int indiceInicial = str.indexOf("[{\"_id\"");
                int indiceFinal = str.indexOf(",\"metadata\"");
                if (indiceInicial != -1 && indiceFinal != -1) {
                    resultado = str.substring(indiceInicial, indiceFinal);
                }else {
                    return listaRespuestaNoticias;
                }
            }
        }
        JSONArray listaNoticias = new JSONArray(resultado); //contiene la lista de noticias
        for (int i = 0; i < listaNoticias.length(); i++) {
            JSONObject noticiaJson = listaNoticias.getJSONObject(i);
            String fecha = obtenerFechaFormatoISO8601(noticiaJson.getString("publish_date"));
            JSONArray listaEnlaces = (JSONArray) noticiaJson.get("_website_urls");
            String enlace = "";
            if (!listaEnlaces.isEmpty()) {
                enlace = URL_ABC + listaEnlaces.get(0).toString();
            }else {
                enlace = "undefined";
            }
            String enlaceFoto = "";
            if (noticiaJson.has("promo_items")) {
                JSONObject datosFoto = (JSONObject) noticiaJson.get("promo_items");
                JSONObject fotoNoticia = (JSONObject) datosFoto.get("basic");
                enlaceFoto = fotoNoticia.getString("url");
            }else {
                enlaceFoto = "No contiene foto";
            }
            JSONObject datosTitulo = (JSONObject) noticiaJson.get("headlines");
            String titulo = datosTitulo.getString("basic");
            JSONObject datosResumen = (JSONObject) noticiaJson.get("subheadlines");
            String resumen = datosResumen.getString("basic");
            if (foto.equalsIgnoreCase("true") && noticiaJson.has("promo_items")) {
                URL urlFoto = new URL(enlaceFoto);
                HttpURLConnection fotoCon = (HttpURLConnection) urlFoto.openConnection();
                InputStream is = fotoCon.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                    baos.write(buffer, 0, read);
                }
                baos.flush();
                String contenidoFoto = Base64.encode(baos.toByteArray());
                String contentTypeFoto = fotoCon.getContentType();
                NoticiaConFotoJson respuestaNoticia = new NoticiaConFotoJson(fecha, enlace, enlaceFoto, titulo, resumen, contenidoFoto, contentTypeFoto);
                listaRespuestaNoticias.add(respuestaNoticia);
            }else {
                NoticiaJson respuestaNoticia = new NoticiaJson(fecha, enlace, enlaceFoto, titulo, resumen);
                listaRespuestaNoticias.add(respuestaNoticia);
            }
        }
        return listaRespuestaNoticias;
    }

    private String obtenerFechaFormatoISO8601(String fecha) {
        fecha = fecha.replace("T", " ");
        fecha = fecha.replace("Z", "");
        if (fecha.contains(".")) {
            fecha = fecha.substring(0, fecha.indexOf("."));
        }
        return fecha;
    }

    @Override
    public String generarHMAC() {
        String clave = "@apiimplementado2022";
        String contraseña = "key";
        try {
            Mac sha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec claveSecreta = new SecretKeySpec(clave.getBytes(), "HmacSHA256");
            sha256.init(claveSecreta);
            return org.apache.commons.codec.binary.Base64.encodeBase64String(sha256.doFinal(contraseña.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return e.getMessage();
        } catch (InvalidKeyException e) {
            return e.getMessage();
        }
    }
}