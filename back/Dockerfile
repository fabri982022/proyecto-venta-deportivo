FROM eclipse-temurin:21-jdk
WORKDIR /app

# Instalar herramientas necesarias
RUN apt-get update && \
    apt-get install -y netcat-traditional curl && \
    rm -rf /var/lib/apt/lists/*

# Copiar JAR pre-compilado
COPY target/deporte-*.jar app.jar
RUN test -f app.jar || (echo "JAR file not found!" && exit 1)

# Copiar y hacer ejecutable el script wait-for-it
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

EXPOSE 8080

# Healthcheck mejorado - espera más tiempo para que Spring Boot inicie
HEALTHCHECK --interval=15s --timeout=10s --retries=8 --start-period=180s \
    CMD curl -sf http://localhost:8080/api/v1/health > /dev/null || exit 1

ENTRYPOINT ["/app/wait-for-it.sh", "db:3306", "-t", "60", "--", "java", "-jar", "app.jar"]