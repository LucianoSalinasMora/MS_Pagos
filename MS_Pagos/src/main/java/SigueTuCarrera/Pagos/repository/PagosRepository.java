package SigueTuCarrera.Pagos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import SigueTuCarrera.Pagos.model.Pagoss;

public interface PagosRepository extends JpaRepository<Pagoss, UUID> {

}