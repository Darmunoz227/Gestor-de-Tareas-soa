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
        }
        success {
            echo '✅ Pipeline ejecutado con éxito'
        }
        failure {
            echo '❌ Pipeline falló'
        }
    }
}