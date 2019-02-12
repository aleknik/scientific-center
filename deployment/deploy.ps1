# Requirements:
#
# 1) Set environment variables:
#    env_vars.bat
#
# 2) Create and distribute certificates:
#    \setup\ssl\ssl.ps1
#
# 3) Deploy bank and PCC mock services:
#    \setup\deployment\deploy-mock.ps1
#
# 4) Set test-bank-url:
#    \src\payment\payment-services\src\main\resources\application.yml

# Build (make sure npm packages are installed):
npm run-script build --prefix "..\scientific-center-client"

# Start server:
Start-Process -FilePath "..\scientific-center-service\mvnw.cmd" `
              -ArgumentList "clean install spring-boot:run -Dspring.profiles.active=production" `
              -WorkingDirectory "..\scientific-center-service"
