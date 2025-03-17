# TFG-Running
# Trabajo de Fin de Grado - Ingeniería Informática
## Universidad Politécnica de Cataluña

Este repositorio contiene el **Trabajo de Fin de Grado** para la carrera de **Ingeniería Informática** de la **Universidad Politécnica de Cataluña**. El proyecto está dividido en dos partes principales:

---

## 1. **Frontend**

El frontend está construido con **ReactJS**, **Bootstrap 5** y **Font Awesome** para el diseño y la interfaz de usuario.

### Requisitos

- **Node.js** (versión recomendada: LTS)
- **npm** (gestor de dependencias)

### Instrucciones de ejecución

1. Instalar las dependencias necesarias:
   ```bash
   npm install
2. Ejecutar la aplicación:
3. ```bash
   npm run dev
   
## 2. **Backend**

El backend está desarrollado con **Spring Boot** y **MySQL**. Es una **API REST** que expone varios endpoints para interactuar con los datos. Además, se ha implementado **autenticación con tokens JWT** utilizando **Spring Security**.

### Requisitos

- **Docker**
- **Docker Compose**

### Instrucciones de ejecución

1. Asegúrate de tener **Docker** y **Docker Compose** instalados en tu sistema.

2. Desde la raíz del proyecto, ejecuta el siguiente comando para levantar los contenedores:
   ```bash
   docker-compose up --build
