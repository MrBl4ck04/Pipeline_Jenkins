pipeline {
    agent any
    
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
                sh 'mvn clean compile'
                echo 'Compilación completada'
            }
        }
        
        stage('Pruebas') {
            steps {
                // Ejecutar pruebas unitarias
                sh 'mvn test'
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
                sh 'mvn package -DskipTests'
                echo 'Empaquetado completado'
            }
        }
        
        stage('Desplegar en Tomcat') {
            steps {
                // Desplegar el WAR en Tomcat
                echo 'Desplegando aplicación en Tomcat...'
                // Copiar el archivo WAR al directorio webapps de Tomcat
                sh 'cp target/hola-mundo.war $TOMCAT_HOME/webapps/'
                echo 'Despliegue completado'
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline ejecutado exitosamente!'
        }
        failure {
            echo 'El pipeline ha fallado. Por favor revise los logs para más detalles.'
        }
        always {
            // Limpiar el espacio de trabajo
            cleanWs()
        }
    }
}