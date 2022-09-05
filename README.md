# API-IMPLEMENTADO
Implementación RESTFull para la apificación de la página web de búsqueda del diario digital ABC Color

# Tabla de contenido
* Informacion general
* Tecnologias
* Como compilar
* Deployar en un servidor de aplicaciones
* Invocar a los métodos del servicio
* Ejecutar en el equipo local
* Documentación Swagger

## Información general
Servicio web que recupera las noticias de un sitio web de noticias.

## Tecnologias
El servicio web se implementó con:
* Lenguaje de programación java, JDK (Java Development Kit) 8.
* Spring-Boot 2.5.2
* Swagger
* Maven 3.8.6

## Como compilar
Tener instalado Apache Maven en el equipo y el JDK 8.
Desde la terminal ubicarse en el directorio raíz del proyecto (donde está el archivo pom.xml) y ejecutar los siguientes comandos:
- mvn clean ->remueve los archivos que se geraron en una compilación anterior. 
- mvn packge ->compila el proyecto y genera el archivo war o jar, según la parametrización del proyecto.

## Deployar en un servidor de aplicaciones
Para poder acceder a los recursos disponibles en el servicio, es necesario deployar el archivo war o jar generado en un servidor de aplicaciones JBOSS 7.1.1 o bien una versión que soporte JDK 8.

## Invocar a los métodos del servicio
1 - IP_DEL_SERVIDOR:8080/API-IMPLEMENTADO/obtenerAPIKey -> Retorna una cadena firmada con HMAC-SHA256
2 - IP_DEL_SERVIDOR:8080/API-IMPLEMENTADO/consulta?q={textoABuscar} -> Retorna la lista de noticias que tienen relación con el 'textoABuscar', se debe invocar a este recurso pasando como encabezado 'x-api-key' como key o clave y la cadena obtenida en el método 1 como value o valor.

## Ejecutar en el equipo local
Es posible acceder a los recursos del servicio, a través de un IDE como Eclipse, STS (Spring Tool Suit) o IntelliJidea ejecutando la clase main del proyecto. La URL de los recursos en este caso tiene otro valor:
1 - localhost:8080/obtenerAPIKey
2 - localhost:8080/consulta?q={textoABuscar}

## Documentación Swagger
Se puede acceder a la documentación interactiva del proyecto a través de la URL:
* En el servidor de aplicaciones: IP_DEL_SERVIDOR:8080/API-IMPLEMENTADO/swagger-ui.html#
* En el equipo local: localhost:8080/swagger-ui.html#
