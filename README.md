# Pipeline de Integración Continua con Jenkins

Este proyecto demuestra la implementación de un pipeline de integración continua utilizando Jenkins, Tomcat, Maven, Java y GitHub. La aplicación es un simple "Hola Mundo" en Java que sirve como base para demostrar el proceso de CI/CD.

## Estructura del Proyecto

```
.
├── Jenkinsfile                 # Definición del pipeline de Jenkins
├── pom.xml                     # Configuración de Maven
└── src
    ├── main
    │   ├── java
    │   │   └── com/ucb/ejemplo
    │   │       └── HolaMundoServlet.java  # Servlet principal
    │   └── webapp
    │       ├── index.html      # Página de inicio
    │       └── WEB-INF
    │           └── web.xml     # Configuración de la aplicación web
    └── test
        └── java
            └── com/ucb/ejemplo
                └── HolaMundoServletTest.java  # Pruebas unitarias
```

## Requisitos Previos

Para ejecutar este proyecto, necesitarás tener instalado:

1. **Java JDK 8** o superior
2. **Maven** para la gestión de dependencias y construcción
3. **Jenkins** para la ejecución del pipeline
4. **Tomcat** como servidor de aplicaciones
5. **Git** para el control de versiones

## Configuración de Jenkins

### 1. Instalar Jenkins

- Descarga Jenkins desde [jenkins.io](https://jenkins.io/download/)
- Instala Jenkins siguiendo las instrucciones para tu sistema operativo
- Accede a Jenkins a través de `http://localhost:8080`

### 2. Configurar Herramientas en Jenkins

En Jenkins, ve a **Administrar Jenkins > Global Tool Configuration** y configura:

- **JDK**: Configura la ubicación de tu instalación de Java
- **Maven**: Configura la ubicación de tu instalación de Maven
- **Git**: Asegúrate de que Git esté configurado correctamente

### 3. Crear un Nuevo Pipeline

1. En Jenkins, haz clic en **Nueva Tarea**
2. Selecciona **Pipeline** y asigna un nombre (ej. "HolaMundoPipeline")
3. En la sección de configuración del pipeline, selecciona **Pipeline script from SCM**
4. Selecciona **Git** como SCM
5. Ingresa la URL de tu repositorio Git
6. Especifica la rama (ej. "\*/main")
7. Asegúrate de que **Script Path** esté configurado como "Jenkinsfile"
8. Guarda la configuración

### 4. Configurar Variables de Entorno

En Jenkins, configura la variable de entorno `TOMCAT_HOME` que apunte a la ubicación de tu instalación de Tomcat:

1. Ve a **Administrar Jenkins > Configurar el Sistema**
2. En la sección **Propiedades Globales**, marca **Variables de entorno**
3. Agrega una variable llamada `TOMCAT_HOME` con el valor de la ruta a tu instalación de Tomcat

## Ejecución del Pipeline

1. Sube el código a tu repositorio Git
2. En Jenkins, selecciona tu pipeline y haz clic en **Construir ahora**
3. El pipeline ejecutará automáticamente todas las etapas definidas en el Jenkinsfile:
   - Checkout: Obtiene el código del repositorio
   - Compilar: Compila el código fuente
   - Pruebas: Ejecuta las pruebas unitarias
   - Empaquetar: Genera el archivo WAR
   - Desplegar en Tomcat: Despliega la aplicación en el servidor Tomcat

## Acceso a la Aplicación

Una vez desplegada, la aplicación estará disponible en:

- **Página principal**: `http://localhost:8080/hola-mundo/`
- **Servlet Hola Mundo**: `http://localhost:8080/hola-mundo/holaMundo`

## Personalización del Pipeline

Puedes personalizar el pipeline modificando el archivo `Jenkinsfile`. Algunas posibles mejoras incluyen:

- Agregar etapas de análisis de código estático (SonarQube)
- Implementar notificaciones por correo electrónico o Slack
- Agregar etapas de pruebas de integración o rendimiento
- Configurar despliegues en diferentes entornos (desarrollo, pruebas, producción)

## Solución de Problemas

- **Error de compilación**: Verifica que la versión de JDK configurada en Jenkins coincida con la especificada en el `pom.xml`
- **Error de despliegue**: Asegúrate de que Tomcat esté en ejecución y que la variable `TOMCAT_HOME` esté correctamente configurada
- **Fallo en las pruebas**: Revisa los informes de pruebas generados por Jenkins para identificar la causa del fallo

## Contribuciones

Si deseas contribuir a este proyecto, por favor:

1. Haz un fork del repositorio
2. Crea una rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Haz commit de tus cambios (`git commit -am 'Agrega nueva funcionalidad'`)
4. Haz push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un nuevo Pull Request
