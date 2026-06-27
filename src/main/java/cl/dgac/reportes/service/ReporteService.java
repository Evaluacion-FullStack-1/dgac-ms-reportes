package cl.dgac.reportes.service;

import cl.dgac.reportes.dto.ReporteRequestDTO;
import cl.dgac.reportes.dto.ReporteResponseDTO;
import cl.dgac.reportes.exception.ResourceNotFoundException;
import cl.dgac.reportes.mapper.ReporteMapper;
import cl.dgac.reportes.model.Reporte;
import cl.dgac.reportes.repository.ReporteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final ReporteMapper reporteMapper;
    
    // Inyectamos directamente el WebClient configurado con LoadBalanced
    private final WebClient webClientIncidencias;

    public ReporteService(ReporteRepository reporteRepository,
                          ReporteMapper reporteMapper,
                          WebClient webClientIncidencias) {
        this.reporteRepository = reporteRepository;
        this.reporteMapper = reporteMapper;
        this.webClientIncidencias = webClientIncidencias;
    }

    public List<ReporteResponseDTO> listarReportes() {
        return reporteRepository.findAll()
                .stream()
                .map(reporteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ReporteResponseDTO buscarPorId(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con ID: " + id));
        
        return reporteMapper.toDTO(reporte); // <- Aquí faltaba el mapeo
    }

    public ReporteResponseDTO crearReporte(ReporteRequestDTO dto) {
        Reporte reporte = reporteMapper.toEntity(dto);

        reporte.setCodigoReporte("REP-" + System.currentTimeMillis());
        reporte.setFechaGeneracion(java.time.LocalDate.now());
        reporte.setEstado("GENERADO");

        Reporte reporteGuardado = reporteRepository.save(reporte);

        return reporteMapper.toDTO(reporteGuardado);
    }

    public ReporteResponseDTO actualizarReporte(Long id, ReporteRequestDTO dto) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con ID: " + id));

        reporteMapper.updateEntity(reporte, dto);
        Reporte reporteActualizado = reporteRepository.save(reporte);

        return reporteMapper.toDTO(reporteActualizado);
    }

    public void eliminarReporte(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con ID: " + id));

        reporteRepository.delete(reporte);
    }

    public ReporteResponseDTO buscarPorCodigoReporte(String codigoReporte) {
        Reporte reporte = reporteRepository.findByCodigoReporte(codigoReporte)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con código: " + codigoReporte));
        
        return reporteMapper.toDTO(reporte); // <- Aquí también faltaba el mapeo
    }

    public List<ReporteResponseDTO> listarPorEstado(String estado) {
        return reporteRepository.findByEstado(estado)
                .stream()
                .map(reporteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReporteResponseDTO> listarPorTipoReporte(String tipoReporte) {
        return reporteRepository.findByTipoReporte(tipoReporte)
                .stream()
                .map(reporteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReporteResponseDTO> listarPorIncidencia(Long incidenciaId) {
        return reporteRepository.findByIncidenciaId(incidenciaId)
                .stream()
                .map(reporteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReporteResponseDTO> buscarPorTitulo(String titulo) {
        return reporteRepository.buscarPorTitulo(titulo)
                .stream()
                .map(reporteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public String consultarMicroservicioIncidencias() {
        // Utilizamos el WebClient inyectado con la ruta relativa
        return webClientIncidencias
                .get()
                .uri("/api/incidencias")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}