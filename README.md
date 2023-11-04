# Twitter API

## Descripción
El laboratorio consiste en diseñar una arquitectura de una Api que soporte las funcionalidades
simples de una aplicacion como Twitter, esto usando servicios en la nube
como Aws Lambda, ApiGateway y Dynamo junto con Java como lenguaje de programacion.

## Arquitectura utilizada
La arquitectura de la aplicación se compone de dos microservicios para definir las funcionalidades de dos dominios claves de una aplicacion tipo Twitter siendo estos dominios el domionio de usuarios y posts, estos microservicios estan contruidos usando funciones lambdas donde el acceso a los recuros expuestos se controlan mediante un ApiGateway.
A continuación se presenta un diagrama con la arquitectura.
![memory.jpg](images%2Fmemory.jpg)

En la arquitectura anterior se observa la falta de un mecanismo que permita sincronizar la informacion
de un dominio en otro, por ejemplo cuando un usuario cree un post este sea creado tambien en el dominio de posts y asi poder acceder a la informacion desde este dominio, manteniendo un bajo acoplamiento al tener una arquitectura guiada por el dominio o DDD.
Para esto se propone la siguiente arquitectura donde mediante un bus de eventos se lograria sincronizar esta informacion.
![event.jpg](images%2Fevent.jpg)

## Pre-requisitos
* [Maven](https://maven.apache.org/) - Administrador de dependencias
* [Git](https://git-scm.com/) - Sistema de control de versiones
* [Java 8](https://www.java.com/) - Tecnología para el desarrollo de aplicaciones

## Instrucciones de construcción y ejecución

1. **Construir la aplicación**: Desde la raíz del proyecto compilar la aplicación

``mvn clean install``

2. **Crear las funciones lambdas**: Se crean las funciones lambdas en AWS subiendo el archivo.jar correspondiente

3.  **Exponer recursos mediante ApiGateway**: Se crea un ApiGateway para redireccionar las peticiones a la lambda correspondiente.


4. **Implementar el API**: Se implementa el api para obtener la url mediante la cual se realizan las peticiones a los recursos expuestos.


## Resultados del despliegue
A continuación se muestran evidencias de las lambdas y el apigateway creado.

### Lambdas creadas
![lambda1.png](images%2Flambda1.png)
![lambda2.png](images%2Flambda2.png)
### ApiGateway creado
![aoi.png](images%2Faoi.png)
