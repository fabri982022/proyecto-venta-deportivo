FROM eclipse-temurin:21-jdk
WORKDIR /app

# Instalar herramientas necesarias
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

# Copiar JAR pre-compilado
COPY target/deporte-*.jar app.jar
RUN test -f app.jar || (echo "JAR file not found!" && exit 1)

EXPOSE 8080

# Healthcheck mejorado - espera más tiempo para que Spring Boot inicie
HEALTHCHECK --interval=15s --timeout=10s --retries=8 --start-period=180s \
    CMD curl -sf http://localhost:8080/api/v1/health > /dev/null || exit 1

# Ejecutar la aplicación directamente
CMD ["java", "-jar", "app.jar"]