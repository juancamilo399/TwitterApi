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


### Métodos soportados
A continuación se listan los métodos soportados, junto con las rutas y métodos HTTP definidos para cada uno de los
recursos

**Recurso Usuarios :**

| Método HTTP | Path            | Descripción                                            |
|-------------|-----------------|--------------------------------------------------------|
| POST        | /users          | Registrar un nuevo usuario                             |
| GET         | /users          | Obtener la información de todos los usuarios           |
| GET         | /users/{userId} | Obtener la información del usuario dado un id `userId` |

**Recurso Posts:**

| Método HTTP | Path                  | Descripción                                           |
|-------------|-----------------------|-------------------------------------------------------|
| POST        | /tweets               | Registrar un nuevo post                               |
| GET         | /tweets               | Obtener la información de todos los posts registrados |

### Evidencias de uso

Los recursos expuestos pueden utilizarse mediante la siguiente [url](https://8t5lkf00zg.execute-api.us-east-1.amazonaws.com/default).

**Register user**
![user post.png](images%2Fuser%20post.png)

**Get users**
![users.png](images%2Fusers.png)

**Find user**
![user.png](images%2Fuser.png)

**Create post**
![posts post.png](images%2Fposts%20post.png)

**Get post**
![posts.png](images%2Fposts.png)