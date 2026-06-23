INSERT IGNORE INTO pagos (transaccion_id, estudiante_id, monto, fecha_pago, numero_boleta, metodo_pago, estado_transaccion) 
VALUES 
('a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d', '12345678-9', 150000.0, '2026-03-15 10:30:00', 'BOL-001923', 'WEBPAY', 'EXITOSA'),
('b2c3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e', '12345678-9', 150000.0, '2026-04-15 14:15:22', 'BOL-002145', 'TRANSFERENCIA', 'EXITOSA'),
('c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f', '98765432-1', 320000.0, '2026-03-10 09:00:12', 'BOL-001844', 'TARJETA_CREDITO', 'RECHAZADA');