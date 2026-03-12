pipeline {
    agent any
    
    stages {
        stage('Validate Environment') {
            steps {
                echo '========== Kiểm tra môi trường =========='
                
                // Kiểm tra Docker
                sh 'docker --version || { echo "ERROR: Docker chưa được cài đặt!" && exit 1; }'
                
                // Kiểm tra Maven, nếu chưa có thì tự động cài đặt
                sh '''
                    if ! command -v mvn &> /dev/null; then
                        echo "Maven chưa được cài đặt. Tiến hành cài đặt Maven..."
                        
                        # Thử cài qua apt (Ubuntu/Debian)
                        if command -v apt-get &> /dev/null; then
                            sudo apt-get update && sudo apt-get install -y maven
                        # Thử cài qua yum (CentOS/RHEL)
                        elif command -v yum &> /dev/null; then
                            sudo yum install -y maven
                        # Thử cài qua apk (Alpine)
                        elif command -v apk &> /dev/null; then
                            sudo apk add --no-cache maven
                        else
                            echo "ERROR: Không nhận dạng được package manager. Vui lòng cài Maven thủ công."
                            exit 1
                        fi
                        
                        echo "Cài đặt Maven thành công!"
                    else
                        echo "Maven đã sẵn sàng."
                    fi
                    mvn --version
                '''
                
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
                sh 'mvn clean package'
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
