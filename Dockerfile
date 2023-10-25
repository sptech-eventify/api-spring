# Use uma imagem base do Java
FROM openjdk:17

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR da sua aplicação para o contêiner
COPY target/api-spring-0.0.1-SNAPSHOT.jar .

# Comando para executar o aplicativo Java
CMD ["java", "-jar", "api-spring-0.0.1-SNAPSHOT.jar"]
