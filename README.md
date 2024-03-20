# PROYECTO TIENDA DE ROPA

## GUIA DE DESPLIEGUE
1. Comandos para tener la base de datos:
-  docker pull noelmartinnez/bd_iw_tienda1
-  docker run -d -p 5432:5432 --name bd_iw_tienda1 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=noel -e POSTGRES_DB=bd_iw_tienda1 noelmartinnez/bd_iw_tienda1

2. Abrir proyecto con IntellIJ
* Forma 1 - Compilar proyecto:
     - En Windows: ./mvnw spring-boot:run -D spring-boot.run.profiles=postgres
     - En Ubuntu: mvn spring-boot:run -D spring-boot.run.profiles=postgres
* Forma 2 - Compilar proyecto:
     - Edit configurations:
![image](https://github.com/ffr9/tiendaropa3/assets/78731028/37507abf-0f25-4194-8ebd-89e31530afbd)
![image](https://github.com/ffr9/tiendaropa3/assets/78731028/a6882e44-639e-4b7d-b7e7-b9166a0bdc05)
![Captura de pantalla de 2024-01-25 16-34-08](https://github.com/ffr9/tiendaropa3/assets/78731028/7496fdc6-7a32-4b93-bfba-44a892689b7a)
     - Una vez hecho todo esto, darle al botón verde para arrancar la aplicación.
 
3. Ingresar en localhost:8080
