pipeline {
    agent any
    
    environment {
        COMPOSE_PROJECT_NAME = 'gestor-tareas'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '📦 Obteniendo código desde GitHub...'
                checkout scm
            }
        }

        stage('Test & Coverage') {
            steps {
                echo '🧪 Ejecutando pruebas y análisis de cobertura...'
                withCredentials([string(credentialsId: 'codecov-token', variable: 'CODECOV_TOKEN')]) {
                    script {
                        // 1. Ejecutar pruebas usando un contenedor de Maven
                        // Montamos el código actual en el contenedor para probarlo
                        sh '''
                            docker run --rm \
                                -v "${WORKSPACE}/task-service":/app \
                                -w /app \
                                maven:3.9-eclipse-temurin-21 \
                                mvn clean test
                        '''
                        
                        // 2. Subir el reporte a Codecov
                        sh '''
                            cd task-service
                            # Descargar el uploader de Codecov
                            curl -Os https://uploader.codecov.io/latest/linux/codecov
                            chmod +x codecov
                            
                            # Enviar el reporte usando el token
                            ./codecov -t $CODECOV_TOKEN -f target/site/jacoco/jacoco.xml
                        '''
                    }
                }
            }
        }
                
        stage('Build') {
            steps {
                echo '🏗️ Construyendo imágenes Docker...'
                script {
                    sh '''
                        echo "Limpiando contenedores anteriores..."
                        docker compose down || true
                        
                        echo "Construyendo imágenes..."
                        docker compose build --no-cache
                        
                        echo "✅ Imágenes construidas exitosamente"
                    '''
                }
            }
        }
                
        stage('Deploy') {
            steps {
                echo '🚀 Desplegando contenedores...'
                script {
                    sh '''
                        echo "Levantando contenedores..."
                        docker compose up -d
                        
                        echo "Esperando a que los servicios estén listos..."
                        sleep 10
                        
                        echo "Verificando estado de los contenedores..."
                        docker compose ps
                        
                        echo "✅ Aplicación desplegada exitosamente"
                        echo "📱 Frontend disponible en: http://localhost:3000"
                        echo "⚙️  Backend disponible en: http://localhost:9090"
                    '''
                }
            }
        }
    }
    
    post {
        always {
            echo '🏁 Pipeline finalizado'
            junit '**/target/surefire-reports/*.xml'
            publishHTML([
                reportDir: 'task-service/target/site',
                reportFiles: 'surefire-report.html',
                reportName: 'Test Report'
            ])
        }
        success {
            echo '✅ Pipeline ejecutado con éxito'
        }
        failure {
            echo '❌ Pipeline falló'
        }
    }
}