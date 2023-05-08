### Challenge java spring boot

## Descripción general del ejemplo de inicio de sesión de Spring Boot Security
Construin de laaplicación Spring Boot + Spring Security con JWT en eso:

    * El usuario puede registrarse en una nueva cuenta (registro) o iniciar sesión con nombre de usuario y contraseña.
    * Por el rol del Usuario (administrador, moderador, usuario), autorizamos al Usuario a acceder a los recursos.

Estas son las API que debemos proporcionar:

    Methods	Urls	Actions
    
    POST	/api/auth/signup	signup nuevo usuario
    POST	/api/auth/signin	login de un usuario
    POST	/api/auth/signout	logout de un usuario
    GET	    /api/user	        ya logueado se puede probar




La base de datos que usaremos es H2 al configurar la dependencia del proyecto y la fuente de datos.

##Flujo de Spring Boot Security Ejemplo de inicio de sesión
El diagrama muestra el flujo de cómo implementamos el proceso de registro de usuario, inicio/cierre de sesión de usuario y autorización.

![diagrama_secuencia.png](imagen_uml%2Fdiagrama_secuencia.png)

# Diagrama de componentes

![diagrama_componentes.jpg](imagen%2Fdiagrama_componentes.jpg)

Se almacenará un JWT legal en HttpOnly Cookie si el Cliente accede a recursos protegidos y con el header como siempre
ejemplo:
---
    curl -H 'Accept: application/json' -H "Authorization: Bearer ${TOKEN}" https://{hostname}/api/myresource
---

#Explicacion breve  Seguridad de primavera

* WebSecurityConfiges el quid de nuestra implementación de seguridad. Configura cors, csrf, gestión de sesiones, reglas para recursos protegidos. También podemos ampliar y personalizar la configuración predeterminada que contiene los elementos a continuación.
( WebSecurityConfigurerAdapterestá obsoleto desde Spring 2.7.0, puede verificar el código fuente para la actualización. Más detalles en:
WebSecurityConfigurerAdapter Obsoleto en Spring Boot )

* UserDetailsServicela interfaz tiene un método para cargar Usuario por nombre de usuario y devuelve un UserDetailsobjeto que Spring Security puede usar para autenticación y validación.

* UserDetailscontiene la información necesaria (como: nombre de usuario, contraseña, autoridades) para construir un objeto de autenticación.

* UsernamePasswordAuthenticationTokenobtiene {nombre de usuario, contraseña} de la Solicitud de inicio de sesión, AuthenticationManagerlo usará para autenticar una cuenta de inicio de sesión.

* AuthenticationManagertiene un DaoAuthenticationProvider(con la ayuda de UserDetailsService& PasswordEncoder) para validar UsernamePasswordAuthenticationTokenel objeto. Si tiene éxito, AuthenticationManagerdevuelve un objeto de autenticación completo (incluidas las autorizaciones otorgadas).

* OncePerRequestFilterrealiza una única ejecución por cada petición a nuestra API. Proporciona un doFilterInternal()método que implementaremos analizando y validando JWT, cargando los detalles del usuario (usando UserDetailsService), verificando la autorización (usando UsernamePasswordAuthenticationToken).

* AuthenticationEntryPointdetectará un error de autenticación.

El repositorio contiene UserRepositoryy RoleRepositorypara trabajar con la base de datos, se importará al controlador .

El controlador recibe y maneja la solicitud después de que fue filtrada por OncePerRequestFilter.

* AuthControllermaneja las solicitudes de registro/inicio de sesión

* TestControllertiene acceso a métodos de recursos protegidos con validaciones basadas en roles.

Comprenda la arquitectura profundamente y capte la descripción general más fácilmente:
Spring Boot Architecture para JWT con Spring Security

Tecnología

   Java 8
   Spring Boot 2.6.3 (con Spring Security, Spring Web, Spring Data JPA)
   jjwt 0.9.1
   H2: base de datos integrada
   Experto 3.6.1

# Configurar Spring Datasource, JPA, propiedades de la aplicación
En la carpeta src/main/resources , abra application.properties , agregue algunas líneas nuevas.

---
    spring.h2.console.enabled=true
    # default path: h2-console
    spring.h2.console.path=/h2-ui
    
    spring.datasource.url=jdbc:h2:file:./testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
    spring.jpa.hibernate.ddl-auto=create-drop
    # create-drop
    
    # App Properties
    app.jwtCookieName= appName
    app.jwtSecret= AppSecretKey
    app.jwtExpirationMs= 86400000
    
    
    springdoc.version= '@springdoc.version@'
---    
    
$ ./gradlew bootRun

$ ./gradlew build && java -jar build/libs/tbk-spring-boot-swagger-demo-<version>.jar


$ curl localhost:8080/health
{"status":"UP","diskSpace":{"status":"UP","total":397635555328,"free":328389529600,"threshold":10485760}}}

Hay tres endpoint de api/auth que maneja la seguridad

Para crear un nuevo usuario se tiliza el siguiente endpoint
* http://localhost:8080/api/auth/signup
BODY:
---
    {
        "username": "tull",
        "password": "12345678",
        "email": "tull@gmail.com",
        "phones": [
            {
                "number": 1111,
                "citycode": 111,
                "contrycode": "1111"
            }
        ]
    }
---

caso correcto devuelve:
---
    {
        "ids": "3df9baea-5796-4133-89b9-d3d709ed4e3b",
        "username": null,
        "email": "tul1@gmail.com",
        "createAt": "2023-01-01,
        "lastLogin": null,
        "token": null,
        "active": false
    }
---

Para realizar el login correspondiente que devuelve el tocken para realizar la autentificación correspondiente,
se puede hacer el tockern por el cual genera un Cookie para utilizar.

Ejemplo de seccion por postman con JwtCookie
el parametro JwtType es opcional para el caso de Cokie es obligatorío ya que si es true se utiliza JwtCookie.
Inicie sesión en una cuenta: POST/api/auth/signin
http://localhost:8080/api/auth/signin
---
    {
        "username": "tull",
        "password": "12345678"
        "JwtType": true
    }
---


resultado:
---
    {
        "id": "ae9afe26-2eaf-4a80-8cdf-f3ece31cfd0d",
        "createAt": 1683514800000,
        "lastLogin": 1683580342847,
        "token": "appName=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0dWwyIiwiaWF0IjoxNjgzNTgwMzM5LCJleHAiOjE2ODM2NjY3Mzl9.xgefigzJwh92ydkpbAb953vMcl7tt7IXuGeHuzNm41dyCCxDPF5LTc9FBiq2FhJQ_ZfpNPrY6lCtjGeHXvfyzg; Path=/api; Max-Age=86400; Expires=Tue, 09 May 2023 21:12:22 GMT; HttpOnly",
        "username": "tul2",
        "email": "tul2@gmail.com",
        "password": "$2a$10$rYtXJJLUEwu2Ix3AQimmqOw25hW6.W7rLbvSfxWAhZnjfZsMaUA5y",
        "phone": [
            {
            "id": 2,
            "number": 1111,
            "cityCode": null,
            "contrycode": "1111"
            }
        ],
        "active": true
    }
---

obtenido el resultado se puede agregarel cookies para poder probar los restantes enpoints para esto ir a menu 
cookies y guardar
![img.png](imagen/postman_cookies.png)

en el caso de jwt 

![img.png](imagen/postman_jwt.png)

Para realizar el logout User esta el endpoint:
 * http://localhost:8080/api/auth/signout

resultado: 
---
    {
        "message": "You've been signed out!"
    }
---


Una ves logueado podemos probar si estan autenticado con el empoint:
 *http://localhost:8080/api/user/

resultado
---
    Welcome to the technical challenge
---