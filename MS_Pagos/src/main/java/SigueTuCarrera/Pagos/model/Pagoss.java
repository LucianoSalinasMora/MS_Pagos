package SigueTuCarrera.Pagos.model;


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
public class Pagoss {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transaccionId;
    
    private String estudianteId;
    private Double monto;
    private LocalDateTime fechaPago;
    private String numeroBoleta;
    
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;
    
    @Enumerated(EnumType.STRING)
    private EstadoTransaccion estadoTransaccion;

    public enum MetodoPago {
        WEBPAY, TRANSFERENCIA, PRESENCIAL, TARJETA_CREDITO
    }

    public enum EstadoTransaccion {
        EXITOSA, PENDIENTE, RECHAZADA, REEMBOLSADA
    }
}
