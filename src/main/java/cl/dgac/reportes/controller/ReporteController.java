package cl.dgac.reportes.controller;

import cl.dgac.reportes.dto.ReporteRequestDTO;
import cl.dgac.reportes.dto.ReporteResponseDTO;
import cl.dgac.reportes.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> listarReportes() {
        return ResponseEntity.ok(reporteService.listarReportes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReporteResponseDTO> crearReporte(
            @Valid @RequestBody ReporteRequestDTO dto) {

        ReporteResponseDTO reporteCreado = reporteService.crearReporte(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> actualizarReporte(
            @PathVariable Long id,
            @Valid @RequestBody ReporteRequestDTO dto) {

        return ResponseEntity.ok(reporteService.actualizarReporte(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar-codigo")
    public ResponseEntity<ReporteResponseDTO> buscarPorCodigoReporte(
            @RequestParam String codigoReporte) {

        return ResponseEntity.ok(reporteService.buscarPorCodigoReporte(codigoReporte));
    }

    @GetMapping("/estado")
    public ResponseEntity<List<ReporteResponseDTO>> listarPorEstado(
            @RequestParam String estado) {

        return ResponseEntity.ok(reporteService.listarPorEstado(estado));
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<ReporteResponseDTO>> listarPorTipoReporte(
            @RequestParam String tipoReporte) {

        return ResponseEntity.ok(reporteService.listarPorTipoReporte(tipoReporte));
    }

    @GetMapping("/incidencia/{incidenciaId}")
    public ResponseEntity<List<ReporteResponseDTO>> listarPorIncidencia(
            @PathVariable Long incidenciaId) {

        return ResponseEntity.ok(reporteService.listarPorIncidencia(incidenciaId));
    }

    @GetMapping("/titulo")
    public ResponseEntity<List<ReporteResponseDTO>> buscarPorTitulo(
            @RequestParam String titulo) {

        return ResponseEntity.ok(reporteService.buscarPorTitulo(titulo));
    }

    @GetMapping("/incidencias")
    public ResponseEntity<String> consultarIncidencias() {
        return ResponseEntity.ok(reporteService.consultarMicroservicioIncidencias());
    }
}