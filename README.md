# MS_Pagos
Microservicio encargado de registrar y gestionar las transacciones de pago de los estudiantes.
## Especificaciones Técnicas
* **Puerto:** `8007`
* **Base de Datos (Laragon):** `pagos_db`
* **Tecnologías:** Spring Boot 4.0.6, WebClient Config Bean

## Interconexión de Red
* Apenas se procesa un pago exitoso en formato JSON, viaja mediante WebClient al puerto `8006` (Arancel) para rebajar automáticamente el saldoPendiente del alumno.

## Endpoints Principales
* `POST /api/v1/pagos` - Registrar una transacción financiera (Monto, Número de Boleta, Método).
* `GET /api/v1/pagos/estudiante/{rut}` - Listar el historial de boletas pagadas por el alumno.
