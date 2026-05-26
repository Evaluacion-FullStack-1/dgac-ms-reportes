package cl.dgac.reportes.repository;

import cl.dgac.reportes.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    Optional<Reporte> findByCodigoReporte(String codigoReporte);

    List<Reporte> findByEstado(String estado);

    List<Reporte> findByTipoReporte(String tipoReporte);

    List<Reporte> findByIncidenciaId(Long incidenciaId);

    @Query("SELECT r FROM Reporte r WHERE LOWER(r.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Reporte> buscarPorTitulo(String titulo);
}