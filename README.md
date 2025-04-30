# Pipeline de Integración Continua con Jenkins

Este proyecto demuestra la implementación de un pipeline de integración continua utilizando Jenkins, Tomcat, Maven, Java y GitHub. La aplicación es un simple "Hola Mundo" en Java que sirve como base para demostrar el proceso de CI/CD.

## Página de Inicio: Cobra Kai

La página principal de la aplicación ahora está dedicada a Cobra Kai, mostrando información relevante y un diseño actualizado. Puedes encontrar el archivo en `src/main/webapp/index.html`.

## Automatización Completa del Pipeline

Este proyecto ha sido configurado para automatizar completamente el proceso de integración continua y despliegue, eliminando la necesidad de intervención manual después de realizar cambios en el código.

### Características implementadas

- **Detección automática de cambios**: Jenkins está configurado para detectar cambios en el repositorio cada 5 minutos.
- **Compilación y empaquetado automático**: Al detectar cambios, Jenkins compila y empaqueta automáticamente la aplicación.
- **Despliegue automático en Tomcat**: La aplicación se despliega automáticamente en Tomcat sin intervención manual.
- **Recarga automática de la aplicación**: Se ha configurado Tomcat para recargar automáticamente la aplicación cuando detecta cambios.
- **Página de inicio personalizada**: Nueva página de inicio sobre Cobra Kai.

### Detalles de la Configuración de Automatización

#### 1. Configuración de Polling SCM en Jenkins

Para la detección automática de cambios, se ha configurado Jenkins con la opción **Consultar repositorio SCM** que verifica periódicamente si hay cambios en el repositorio:

- En la configuración del pipeline, se ha establecido una programación de polling: `H/5 * * * *` (cada 5 minutos)
- Cuando se detectan cambios, Jenkins inicia automáticamente una nueva ejecución del pipeline

#### 2. Configuración del Plugin de Maven para Tomcat

En el archivo `pom.xml`, se ha configurado el plugin `tomcat7-maven-plugin` para automatizar el despliegue:

```xml
<plugin>
    <groupId>org.apache.tomcat.maven</groupId>
    <artifactId>tomcat7-maven-plugin</artifactId>
    <version>2.2</version>
    <configuration>
        <url>http://localhost:8080/manager/text</url>
        <server>TomcatServer</server>
        <path>/${project.artifactId}</path>
        <update>true</update>
    </configuration>
</plugin>
```

La opción `<update>true</update>` es crucial ya que permite actualizar la aplicación existente en lugar de fallar si ya existe.

#### 3. Configuración de Credenciales para Tomcat

Las credenciales para el despliegue en Tomcat se configuran en el archivo `settings.xml` de Maven:

```xml
<servers>
    <server>
        <id>TomcatServer</id>
        <username>admin</username>
        <password>admin</password>
    </server>
</servers>
```

Este archivo debe ubicarse en la carpeta `.m2` del usuario o configurarse en Jenkins como un archivo de configuración global.

#### 4. Configuración de Recarga Automática en Tomcat

Para que Tomcat recargue automáticamente la aplicación cuando se detecten cambios, se debe modificar el archivo `context.xml` de Tomcat con la siguiente configuración:

```xml
<Context reloadable="true">
    <!-- Otras configuraciones -->
</Context>
```

Esta configuración debe aplicarse al contexto específico de la aplicación o al contexto global de Tomcat.

## Estructura del Proyecto

```
.
├── Jenkinsfile                 # Definición del pipeline de Jenkins
├── pom.xml                     # Configuración de Maven
├── settings.xml                # Credenciales para despliegue en Tomcat
└── src
    ├── main
    │   ├── java
    │   │   └── com/ucb/ejemplo
    │   │       └── HolaMundoServlet.java  # Servlet principal
    │   └── webapp
    │       ├── index.html      # Página de inicio (Cobra Kai)
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
8. **Configuración de Polling SCM**: En la sección de Disparadores de Ejecuciones, selecciona **Consultar Periódicamente al SCM** y establece el valor `H/5 * * * *` para verificar cada 5 minutos
9. Guarda la configuración

### 4. Configurar Variables de Entorno

En Jenkins, configura la variable de entorno `TOMCAT_HOME` que apunte a la ubicación de tu instalación de Tomcat:

1. Ve a **Administrar Jenkins > Configurar el Sistema**
2. En la sección **Propiedades Globales**, marca **Variables de entorno**
3. Agrega una variable llamada `TOMCAT_HOME` con el valor de la ruta a tu instalación de Tomcat

### 5. Configurar Archivo de Credenciales de Maven

Para que Jenkins pueda desplegar en Tomcat, necesitas configurar el archivo `settings.xml`:

1. Ve a **Administrar Jenkins > Managed files**
2. Haz clic en **Add a new Config**
3. Selecciona **Maven settings.xml**
4. Asigna un ID (ej. "maven-settings")
5. Copia el contenido del archivo `settings.xml` proporcionado en este proyecto
6. En la configuración del pipeline, en la sección **Pipeline**, agrega la referencia a este archivo

## Configuración de Tomcat

### 1. Habilitar el Manager de Tomcat

Para que el despliegue automático funcione, debes configurar el Manager de Tomcat:

1. Edita el archivo `tomcat-users.xml` en la carpeta `conf` de tu instalación de Tomcat
2. Agrega los siguientes roles y usuario:

```xml
<tomcat-users>
    <!-- Otras configuraciones existentes -->
    <role rolename="manager-gui"/>
    <role rolename="manager-script"/>
    <user username="admin" password="admin" roles="manager-gui,manager-script"/>
</tomcat-users>
```

### 2. Configurar Recarga Automática

Para habilitar la recarga automática de la aplicación:

1. Edita el archivo `context.xml` en la carpeta `conf` de Tomcat
2. Asegúrate de que el atributo `reloadable` esté configurado como `true`:

```xml
<Context reloadable="true">
    <!-- Otras configuraciones -->
</Context>
```

Alternativamente, puedes crear un archivo `context.xml` específico para tu aplicación en la carpeta `conf/Jared/localhost/` con el nombre `hola-mundo.xml`.

## Ejecución del Pipeline

1. Sube el código a tu repositorio Git
2. En Jenkins, selecciona tu pipeline y haz clic en **Construir ahora** (o espera a que el polling SCM detecte cambios)
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

## Solución de Problemas

### Problemas Comunes

- **Error de compilación**: Verifica que la versión de JDK configurada en Jenkins coincida con la especificada en el `pom.xml`
- **Error de despliegue**: Asegúrate de que Tomcat esté en ejecución y que la variable `TOMCAT_HOME` esté correctamente configurada
- **Fallo en las pruebas**: Revisa los informes de pruebas generados por Jenkins para identificar la causa del fallo

### Problemas Específicos de Automatización

- **Jenkins no detecta cambios**: Verifica la configuración de Polling SCM y asegúrate de que la URL del repositorio sea correcta
- **Error 403 al desplegar en Tomcat**: Revisa las credenciales en `settings.xml` y asegúrate de que coincidan con las configuradas en `tomcat-users.xml`
- **La aplicación no se recarga automáticamente**: Verifica que el atributo `reloadable="true"` esté configurado en el archivo `context.xml` de Tomcat
- **El plugin de Maven para Tomcat falla**: Asegúrate de que el Manager de Tomcat esté habilitado y accesible en la URL configurada en el `pom.xml`

## Contribuciones

Si deseas contribuir a este proyecto, por favor:

1. Haz un fork del repositorio
2. Crea una rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Haz commit de tus cambios (`git commit -am 'Agrega nueva funcionalidad'`)
4. Haz push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un nuevo Pull Request
