node {
   stage('Checkout') { 
      git 'https://github.com/katson95/form3-payment.git'
      
   }
   stage('Build') {
      sh './gradlew clean'
   }
   stage('Results') {
      sh './gradlew test'
   }
}
