package SigueTuCarrera.Pagos.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import SigueTuCarrera.Pagos.model.Pagoss;
import SigueTuCarrera.Pagos.repository.PagosRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagosService {

    @Autowired
    private PagosRepository pagosRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    

    public Pagoss crearPago(Pagoss pago) {
        pago.setEstadoTransaccion(Pagoss.EstadoTransaccion.EXITOSA);
        Pagoss pagoGuardado = pagosRepository.save(pago);

        if (pagoGuardado.getEstadoTransaccion() == Pagoss.EstadoTransaccion.EXITOSA) {
            webClientBuilder.baseUrl("http://localhost:8006").build()
                    .put()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v1/tuition/{rut}/descontar-saldo")
                            .queryParam("monto", pagoGuardado.getMonto())
                            .build(pagoGuardado.getEstudianteId()))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }

        return pagoGuardado;
    }

    

    public List<Pagoss> obtenerPagos() {
        return pagosRepository.findAll();
    }

    public Optional<Pagoss> obtenerPago(UUID transaccionId) {
        return pagosRepository.findById(transaccionId);
    }

    public Optional<Pagoss> actualizarPago(UUID transaccionId, Pagoss pagoActualizado) {
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