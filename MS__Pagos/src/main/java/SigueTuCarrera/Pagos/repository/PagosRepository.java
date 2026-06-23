package SigueTuCarrera.Pagos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import SigueTuCarrera.Pagos.model.Pagos;

public interface PagosRepository extends JpaRepository<Pagos, UUID> {

}