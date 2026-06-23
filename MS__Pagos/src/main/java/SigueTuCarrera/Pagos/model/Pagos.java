package SigueTuCarrera.Pagos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Schema(description = "Modelo que representa el registro detallado de transacciones de pago en el sistema")
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Identificador único de la transacción", example = "4e2b83a0-761d-4819-a1d2-094cf515b13d")
    private UUID transaccionId;
    
    @Schema(description = "Identificador exclusivo del estudiante que efectúa el pago", example = "EST-88432")
    private String estudianteId;

    @Schema(description = "Monto monetario de la transacción en pesos (CLP)", example = "249990.0")
    private Double monto;

    @Schema(description = "Fecha y hora exacta en la que se procesó el pago", example = "2026-06-22T14:30:00")
    private LocalDateTime fechaPago;

    @Schema(description = "Número correlativo de la boleta electrónica asociada al cobro", example = "BOL-2026-7734")
    private String numeroBoleta;
    
    @Enumerated(EnumType.STRING)
    @Schema(description = "Pasarela o medio utilizado para realizar la operación bancaria", example = "WEBPAY")
    private MetodoPago metodoPago;
    
    @Enumerated(EnumType.STRING)
    @Schema(description = "Estado de flujo o conciliación en el que se encuentra el pago", example = "EXITOSA")
    private EstadoTransaccion estadoTransaccion;

    public enum MetodoPago {
        WEBPAY, TRANSFERENCIA, PRESENCIAL, TARJETA_CREDITO
    }

    public enum EstadoTransaccion {
        EXITOSA, PENDIENTE, RECHAZADA, REEMBOLSADA
    }
}