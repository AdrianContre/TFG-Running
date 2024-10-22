# Usa una imagen base de Maven para compilar y ejecutar la aplicación
FROM maven:3.8.4-openjdk-17-slim AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias del proyecto
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del código fuente al contenedor
COPY src ./src

COPY start.sh .

# Ejecuta la aplicación
CMD ["./start.sh"]
#CMD ["mvn", "clean", "spring-boot:run"]