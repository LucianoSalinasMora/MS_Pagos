package SigueTuCarrera.Pagos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import SigueTuCarrera.Pagos.model.Pagos;
import SigueTuCarrera.Pagos.repository.PagosRepository;
import SigueTuCarrera.Pagos.service.PagosService;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class PagosServiceTest {

    @Mock
    private PagosRepository pagosRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private PagosService pagosService;

    private Pagos pagoPrueba;
    private UUID idPrueba;

    
    @Mock private WebClient webClient;
    @Mock private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock private WebClient.RequestBodySpec requestBodySpec;
    @Mock private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        idPrueba = UUID.randomUUID();
        pagoPrueba = new Pagos();
        pagoPrueba.setTransaccionId(idPrueba);
        pagoPrueba.setEstudianteId("12345678-9");
        pagoPrueba.setMonto(150000.0);

        
        ReflectionTestUtils.setField(pagosService, "arancelServiceUrl", "http://localhost:8006");
    }

    @SuppressWarnings("unchecked")
    @Test
    void testCrearPago_ExitosoConLlamadaWebClient() {
        
        when(pagosRepository.save(any(Pagos.class))).thenReturn(pagoPrueba);

        
        when(webClientBuilder.baseUrl(eq("http://localhost:8006"))).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        
        Pagos resultado = pagosService.crearPago(pagoPrueba);

        
        assertNotNull(resultado);
        assertEquals(Pagos.EstadoTransaccion.EXITOSA, resultado.getEstadoTransaccion());
        verify(pagosRepository, times(1)).save(pagoPrueba);
    }

    @Test
    void testObtenerPagos() {
        when(pagosRepository.findAll()).thenReturn(Arrays.asList(pagoPrueba));

        List<Pagos> lista = pagosService.obtenerPagos();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
    }

    @Test
    void testObtenerPago_Existente() {
        when(pagosRepository.findById(idPrueba)).thenReturn(Optional.of(pagoPrueba));

        Optional<Pagos> resultado = pagosService.obtenerPago(idPrueba);

        assertTrue(resultado.isPresent());
        assertEquals(idPrueba, resultado.get().getTransaccionId());
    }

    @Test
    void testActualizarPago_Existente() {
        when(pagosRepository.findById(idPrueba)).thenReturn(Optional.of(pagoPrueba));
        when(pagosRepository.save(any(Pagos.class))).thenReturn(pagoPrueba);

        Optional<Pagos> resultado = pagosService.actualizarPago(idPrueba, pagoPrueba);

        assertTrue(resultado.isPresent());
    }

    @Test
    void testEliminarPago_Existente() {
        when(pagosRepository.existsById(idPrueba)).thenReturn(true);
        doNothing().when(pagosRepository).deleteById(idPrueba);

        boolean eliminado = pagosService.eliminarPago(idPrueba);

        assertTrue(eliminado);
        verify(pagosRepository, times(1)).deleteById(idPrueba);
    }
}