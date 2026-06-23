package SigueTuCarrera.Pagos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ContextConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;


import SigueTuCarrera.Pagos.model.Pagos;
import SigueTuCarrera.Pagos.service.PagosService;
import SigueTuCarrera.Pagos.PagosApplication;
@WebMvcTest(PagosController.class)
@ContextConfiguration(classes = PagosApplication.class)
public class PagosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean 
    private PagosService pagosService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pagos pagoPrueba;
    private UUID idPrueba;

    @BeforeEach
    void setUp() {
        idPrueba = UUID.randomUUID();
        pagoPrueba = new Pagos();
        pagoPrueba.setTransaccionId(idPrueba);
        pagoPrueba.setEstudianteId("12345678-9");
        pagoPrueba.setMonto(150000.0);
    }

    @Test
    void testObtenerPagos_RetornaOk() throws Exception {
        when(pagosService.obtenerPagos()).thenReturn(Arrays.asList(pagoPrueba));

        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estudianteId").value("12345678-9"));
    }

    @Test
    void testObtenerPagos_RetornaNoContent() throws Exception {
        when(pagosService.obtenerPagos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCrearPago_RetornaCreated() throws Exception {
        when(pagosService.crearPago(any(Pagos.class))).thenReturn(pagoPrueba);

        mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagoPrueba)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.monto").value(150000.0));
    }

    @Test
    void testObtenerPago_PorId_RetornaOk() throws Exception {
        when(pagosService.obtenerPago(idPrueba)).thenReturn(Optional.of(pagoPrueba));

        mockMvc.perform(get("/api/v1/pagos/" + idPrueba))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaccionId").value(idPrueba.toString()));
    }

    @Test
    void testObtenerPago_PorId_RetornaNotFound() throws Exception {
        UUID idFalso = UUID.randomUUID();
        when(pagosService.obtenerPago(idFalso)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/pagos/" + idFalso))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarPago_RetornaOk() throws Exception {
        when(pagosService.actualizarPago(eq(idPrueba), any(Pagos.class))).thenReturn(Optional.of(pagoPrueba));

        mockMvc.perform(put("/api/v1/pagos/" + idPrueba)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagoPrueba)))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarPago_RetornaNoContent() throws Exception {
        when(pagosService.eliminarPago(idPrueba)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/pagos/" + idPrueba))
                .andExpect(status().isNoContent());
    }
}