package SigueTuCarrera.Pagos.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import SigueTuCarrera.Pagos.model.Pagos;
import SigueTuCarrera.Pagos.repository.PagosRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagosService {

    @Autowired
    private PagosRepository pagosRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    
    @Value("${app.services.arancel.url:http://localhost:8006}")
    private String arancelServiceUrl;

    public Pagos crearPago(Pagos pago) {
        pago.setEstadoTransaccion(Pagos.EstadoTransaccion.EXITOSA);
        Pagos pagoGuardado = pagosRepository.save(pago);

        if (pagoGuardado.getEstadoTransaccion() == Pagos.EstadoTransaccion.EXITOSA) {
            // 2. Cambia el string fijo por tu nueva variable 'arancelServiceUrl'
            webClientBuilder.baseUrl(arancelServiceUrl).build()
                    .put()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v1/tuition/{rut}/descontar-saldo") // Verifica si calza con tu endpoint original
                            .queryParam("monto", pagoGuardado.getMonto())
                            .build(pagoGuardado.getEstudianteId()))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }

        return pagoGuardado;
    }


    
    public List<Pagos> obtenerPagos() {
        return pagosRepository.findAll();
    }

    public Optional<Pagos> obtenerPago(UUID transaccionId) {
        return pagosRepository.findById(transaccionId);
    }

    public Optional<Pagos> actualizarPago(UUID transaccionId, Pagos pagoActualizado) {
        return pagosRepository.findById(transaccionId).map(existente -> {
            pagoActualizado.setTransaccionId(transaccionId);
            return pagosRepository.save(pagoActualizado);
        });
    }

    public boolean eliminarPago(UUID transaccionId) {
        if (pagosRepository.existsById(transaccionId)) {
            pagosRepository.deleteById(transaccionId);
            return true;
        }
        return false;
    }
}