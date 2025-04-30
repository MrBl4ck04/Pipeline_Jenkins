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
        
        stage('Desplegar Tomcat') {
            steps {
                script {
                    // Ruta de Tomcat webapps con formato Windows (barras invertidas)
                    def tomcatWeb = 'C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps'
                    
                    // Usar ruta relativa al workspace de Jenkins con formato Windows (barras invertidas)
                    def warFile = "${WORKSPACE}\\target\\hola-mundo.war"
                    
                    // Verificar si el directorio target existe
                    bat "dir ${WORKSPACE}\\target"
                    
                    // Verificar si el archivo WAR específico existe en la ruta del workspace
                    bat "if exist \"${warFile}\" (echo El archivo WAR existe) else (echo El archivo WAR NO existe)"
                    
                    echo "Intentando copiar: ${warFile}"
                    
                    // Eliminar versión anterior del WAR en Tomcat si existe
                    bat "if exist \"${tomcatWeb}\\hola-mundo.war\" del \"${tomcatWeb}\\hola-mundo.war\""
                    bat "if exist \"${tomcatWeb}\\hola-mundo\" rmdir /s /q \"${tomcatWeb}\\hola-mundo\""
                    // Copiar el archivo .war al directorio webapps usando la ruta del workspace
                    bat "copy \"${warFile}\" \"${tomcatWeb}\\\""
                    
                    echo "Nuevo WAR desplegado correctamente"
                    sleep(time: 15, unit: 'SECONDS')
                    
                    echo "Aplicación desplegada en: http://localhost:8080/hola-mundo/"
                }
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