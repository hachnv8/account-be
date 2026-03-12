pipeline {
    agent any
    
    stages {
        stage('Validate Environment') {
            steps {
                echo '========== Kiểm tra môi trường =========='
                
                // Kiểm tra Docker
                sh 'docker --version || { echo "ERROR: Docker chưa được cài đặt!" && exit 1; }'
                
                // Kiểm tra Java
                sh 'java -version || { echo "ERROR: Java chưa được cài đặt!" && exit 1; }'
                
                echo '========== Môi trường OK =========='
            }
        }
        
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build & Test') {
            steps {
                echo 'Tiến hành build source code và chạy test...'
                // Cấp quyền thực thi cho Maven Wrapper
                sh 'chmod +x mvnw'
                // Sử dụng Maven Wrapper (./mvnw) - tự tải Maven nếu server chưa có
                sh './mvnw clean package -DskipTests --no-transfer-progress'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                echo 'Đóng gói ứng dụng vào Docker image...'
                sh 'docker build -t account-backend:${BUILD_NUMBER} .'
            }
        }
        
        stage('Deploy') {
            steps {
                // Xoá container cũ nếu có
                sh 'docker stop account-backend-container || true'
                sh 'docker rm account-backend-container || true'
                // Chạy container mới: port 8085
                sh 'docker run -d --name account-backend-container -p 8085:8085 account-backend:${BUILD_NUMBER}'
            }
        }
    }
}
