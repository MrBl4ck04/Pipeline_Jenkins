pipeline {
    agent any
    
    // Configurar triggers para automatizar el proceso
    triggers {
        // Configurar polling al SCM cada 2 minutos
        pollSCM('*/1 * * * *')
    }
    
    tools {
        // Configuración de herramientas
        maven 'Maven'
        jdk 'JDK'
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Obtener código del repositorio Git
                checkout scm
                echo 'Descarga de código completada'
            }
        }
        
        stage('Compilar') {
            steps {
                // Compilar el proyecto con Maven
                bat 'mvn clean compile'
                echo 'Compilación completada'
            }
        }
        
        stage('Pruebas') {
            steps {
                // Ejecutar pruebas unitarias
                bat 'mvn test'
                echo 'Pruebas completadas'
            }
            post {
                // Publicar resultados de pruebas
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Empaquetar') {
            steps {
                // Empaquetar la aplicación
                bat 'mvn package -DskipTests'
                echo 'Empaquetado completado'
            }
        }
        
        stage('Desplegar en Tomcat') {
            steps {
                // Desplegar el WAR en Tomcat
                echo '[!] Desplegando aplicación en Tomcat...'
                // Método 1: Usar el plugin de Maven para Tomcat (más eficiente)
                bat '''
                    @echo off
                    echo Desplegando con Maven Tomcat Plugin...
                    mvn tomcat7:deploy -DskipTests -s settings.xml || mvn tomcat7:redeploy -DskipTests -s settings.xml
                '''
                
                // Método 2 (alternativo): Despliegue manual en caso de que falle el método 1
                bat '''
                    @echo off
                    if %errorlevel% neq 0 (
                        echo El despliegue con Maven falló, usando método alternativo...
                        if exist "%TOMCAT_HOME%\\webapps\\hola-mundo" (
                            echo Deteniendo y eliminando la aplicación existente...
                            if exist "%TOMCAT_HOME%\\webapps\\hola-mundo.war" del "%TOMCAT_HOME%\\webapps\\hola-mundo.war"
                            rmdir /S /Q "%TOMCAT_HOME%\\webapps\\hola-mundo"
                        )
                        copy target\hola-mundo.war %TOMCAT_HOME%\webapps
                '''
                echo '[+] Despliegue completado'
            }
        }
    }
    
    post {
        success {
            echo '[+] Pipeline ejecutado exitosamente!'
        }
        failure {
            echo '[!] El pipeline ha fallado. Por favor revise los logs para más detalles.'
        }
        always {
            // Limpiar el espacio de trabajo
            cleanWs()
        }
    }
}