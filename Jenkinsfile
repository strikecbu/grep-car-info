pipeline {
  agent {
    label 'Linux_GO'
  }

  environment {
    DOCKERHUB_CREDENTIALS=credentials('DockerHub')
  }

  options {
    buildDiscarder(
      logRotator(
        artifactDaysToKeepStr: '', 
        artifactNumToKeepStr: '', 
        daysToKeepStr: '', 
        numToKeepStr: '5',
      )
    )
  } 

  stages {
    stage('mvn build') {
      parallel {

        stage('car-config') {
          steps {
            dir(path: 'car-config') {
              sh 'mvn clean package -DskipTests'
            }
          }
        }
        stage('car-eureka') {
          steps {
            dir(path: 'car-eureka') {
              sh 'mvn clean package -DskipTests'
            }
          }
        }
        stage('car-gateway') {
          steps {
            dir(path: 'car-gateway') {
              sh 'mvn clean package -DskipTests'
            }
          }
        }
        stage('car-info-service') {
          steps {
            dir(path: 'car-info-service') {
              sh 'mvn clean package -DskipTests'
            }
          }
        }

        stage('web-scraping') {
          steps {
            dir(path: 'web-scraping') {
              sh 'mvn clean package -DskipTests'
            }
          }
        }
      }
    }
    stage('docker build') {
      parallel {

        stage('car-config') {
          steps {
            dir(path: 'car-config') {
              sh 'docker build -t andychentw/car-config --no-cache .'
            }
          }
        }
        stage('car-eureka') {
          steps {
            dir(path: 'car-eureka') {
              sh 'docker build -t andychentw/car-eureka --no-cache .'
            }
          }
        }
        stage('car-gateway') {
          steps {
            dir(path: 'car-gateway') {
              sh 'docker build -t andychentw/car-gateway --no-cache .'
            }
          }
        }
        stage('car-info-service') {
          steps {
            dir(path: 'car-info-service') {
              sh 'docker build -t andychentw/car-info-service --no-cache .'
            }
          }
        }

        stage('web-scraping') {
          steps {
            dir(path: 'web-scraping') {
              sh 'docker build -t andychentw/web-scraping --no-cache .'
            }
          }
        }

      }
    }

    stage('docker login') {
      steps {
        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin' 
      }
    }

    stage('push image') {
      parallel {
        
        stage('car-config') {
          steps {
            dir(path: 'car-config') {
              sh 'docker push andychentw/car-config:latest'
            }
          }
        }
        stage('car-eureka') {
          steps {
            dir(path: 'car-eureka') {
              sh 'docker push andychentw/car-eureka:latest'
            }
          }
        }
        stage('car-gateway') {
          steps {
            dir(path: 'car-gateway') {
              sh 'docker push andychentw/car-gateway:latest'
            }
          }
        }
        stage('car-info-service') {
          steps {
            dir(path: 'car-info-service') {
              sh 'docker push andychentw/car-info-service:latest'
            }
          }
        }
        stage('web-scraping') {
          steps {
            dir(path: 'web-scraping') {
              sh 'docker push andychentw/web-scraping:latest'
            }
          }
        }
      }
    }

  }

  post {
    always {
      sh 'docker logout'
    }
  }
  tools {
    maven 'Maven_3.8.6'
    jdk 'jdk17'
  }
}
