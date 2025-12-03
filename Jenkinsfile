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
                dir('task-service') {
                    script {
                        try {
                            // 1. Construir imagen temporal con el código fuente
                            // Esto evita problemas de volúmenes en Jenkins-in-Docker
                            sh 'docker build --target builder -t task-service-test .'
                            
                            // 2. Ejecutar tests en un contenedor nombrado
                            sh 'docker run --name test-runner task-service-test mvn test jacoco:report'
                        } finally {
                            // 3. Extraer reportes (incluso si fallan los tests)
                            sh 'mkdir -p target/site/jacoco'
                            sh 'mkdir -p target/surefire-reports'
                            sh 'docker cp test-runner:/app/target/site/jacoco/jacoco.xml target/site/jacoco/jacoco.xml || true'
                            sh 'docker cp test-runner:/app/target/surefire-reports/. target/surefire-reports/ || true'
                            
                            // 4. Limpiar
                            sh 'docker rm -f test-runner || true'
                            sh 'docker rmi -f task-service-test || true'
                        }
                    }
                    
                    // 5. Subir a Codecov
                    withCredentials([string(credentialsId: 'codecov-token', variable: 'CODECOV_TOKEN')]) {
                        sh '''
                            if [ -f target/site/jacoco/jacoco.xml ]; then
                                curl -Os https://uploader.codecov.io/latest/linux/codecov
                                chmod +x codecov
                                ./codecov -t $CODECOV_TOKEN -f target/site/jacoco/jacoco.xml
                            else
                                echo "⚠️ No se encontró reporte de cobertura"
                            fi
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
                        # Forzar eliminación de contenedores por nombre para evitar conflictos si fueron creados fuera de este pipeline
                        docker rm -f taskdb-postgres task-backend task-frontend || true
                        
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
            // publishHTML removed as plugin is missing
        }
        success {
            echo '✅ Pipeline ejecutado con éxito'
        }
        failure {
            echo '❌ Pipeline falló'
        }
    }
}