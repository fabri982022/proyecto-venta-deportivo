# Stage 1: Build
FROM node:20 AS builder
WORKDIR /app

# Copiar archivos de dependencias
COPY package*.json ./

# Instalar dependencias con caché
RUN npm install --legacy-peer-deps

# Copiar código fuente y archivos de configuración
COPY . .

# Construir la aplicación
RUN npm run build

# Stage 2: Runtime
FROM node:20-alpine
WORKDIR /app

# Instalar serve para servir la aplicación
RUN npm install -g serve

# Copiar la aplicación construida desde el builder
COPY --from=builder /app/build ./build
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/package.json ./package.json

# Variables de entorno para Docker (sobrescribe las de docker-compose.yml en runtime)
ENV REACT_APP_API_URL=http://backend:8080
ENV NODE_ENV=development

EXPOSE 3000

# Healthcheck
HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 \
    CMD node -e "require('http').get('http://localhost:3000', (r) => {if (r.statusCode !== 200) throw new Error(r.statusCode)})"

# Servir la aplicación construida
CMD ["serve", "-s", "build", "-l", "3000"]