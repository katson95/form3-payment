node {
   stage('Checkout') { 
      git 'https://github.com/katson95/form3-payment.git'
      
   }
   stage('Build') {
      sh 'chmod +x gradlew'
      sh './gradlew clean'
   }
   stage('Results') {
      sh 'chmod +x gradlew'
      sh './gradlew test'
   }
}
