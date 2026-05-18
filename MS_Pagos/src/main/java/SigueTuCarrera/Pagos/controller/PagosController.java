package SigueTuCarrera.Pagos.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SigueTuCarrera.Pagos.model.Pagoss;
import SigueTuCarrera.Pagos.service.PagosService;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagosController {

    @Autowired
    private PagosService pagosService;

    
    @GetMapping
    public ResponseEntity<List<Pagoss>> obtenerPagos() {
        List<Pagoss> pagos = pagosService.obtenerPagos();
        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();    // m204
        }
        return ResponseEntity.ok(pagos);                 // 200
    }

    
    @PostMapping
    public ResponseEntity<Pagoss> crearPago(@RequestBody Pagoss pago) {
        try {
            Pagoss nuevoPago = pagosService.crearPago(pago);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago); // 201
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    
    @GetMapping("/{transaccionId}")
    public ResponseEntity<Pagoss> obtenerPago(@PathVariable UUID transaccionId) {
        return pagosService.obtenerPago(transaccionId)
                .map(pago -> ResponseEntity.ok(pago))        //          200
                .orElse(ResponseEntity.notFound().build());  //404
    }

    
    @PutMapping("/{transaccionId}")
    public ResponseEntity<Pagoss> actualizarPago(
            @PathVariable UUID transaccionId,
            @RequestBody Pagoss pago) {
        return pagosService.actualizarPago(transaccionId, pago)
                .map(actualizado -> ResponseEntity.ok(actualizado)) // 200
                .orElse(ResponseEntity.notFound().build());         // 404
    }

    
    @DeleteMapping("/{transaccionId}")
    public ResponseEntity<Void> eliminarPago(@PathVariable UUID transaccionId) {
        if (pagosService.eliminarPago(transaccionId)) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();      // 404
    }
}