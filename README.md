MS_Pagos
Microservicio encargado de registrar y gestionar las transacciones de pago de los estudiantes.

Especificaciones Técnicas
Puerto: 8007

Base de Datos (Laragon): pagos_db

Tecnologías: Spring Boot 3.4.1, WebClient Config Bean, Spring Data JPA, Springdoc OpenAPI (Swagger), JUnit 5, Mockito

Interconexión de Red
Apenas se procesa un pago exitoso en formato JSON, viaja mediante WebClient al puerto 8006 (Arancel) para rebajar automáticamente el saldo pendiente del alumno. Las rutas internas y las pruebas fueron ajustadas para apuntar a las direcciones dinámicas inter-servicio correspondientes.

Estructura de Desarrollo y Pruebas
Estructura de Carpetas: Configuración global de Swagger bajo el paquete config. Pruebas unitarias organizadas en las carpetas controller y service dentro del directorio src/test/java/SigueTuCarrera/.

Carga Inicial: Archivo SQL configurado al lado de application.properties utilizando sentencias INSERT IGNORE INTO.

Endpoints Principales
POST /auth/pagos - Registrar una transacción financiera (Monto, Número de Boleta, Método).

GET /pagos/estudiante/{rut} - Listar el historial de boletas pagadas por el alumno.

GET /swagger-ui.html - Documentación interactiva de la API.

Compilación y Despliegue Docker
Bash
mvn clean package
docker build -t ms-pagos:1.0 .
docker run -d -p 8007:8007 --name ms-pagos -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3
