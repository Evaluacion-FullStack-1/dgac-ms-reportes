package cl.dgac.reportes.service;

import cl.dgac.reportes.dto.ReporteRequestDTO;
import cl.dgac.reportes.dto.ReporteResponseDTO;
import cl.dgac.reportes.exception.ResourceNotFoundException;
import cl.dgac.reportes.mapper.ReporteMapper;
import cl.dgac.reportes.model.Reporte;
import cl.dgac.reportes.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final ReporteMapper reporteMapper;
    
    // Inyectamos RestTemplate en lugar de WebClient
    private final RestTemplate restTemplate;

    // Leemos la URL base desde el application.yml
    @Value("${incidencias.base-url}")
    private String incidenciasBaseUrl;

    public ReporteService(ReporteRepository reporteRepository,
                          ReporteMapper reporteMapper,
                          RestTemplate restTemplate) {
        this.reporteRepository = reporteRepository;
        this.reporteMapper = reporteMapper;
        this.restTemplate = restTemplate;
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
        
        return reporteMapper.toDTO(reporte);
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
        
        return reporteMapper.toDTO(reporte);
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

    // --- MÉTODO CORREGIDO ---
    public String consultarMicroservicioIncidencias() {
        // Construimos la URL completa para llamar al otro servicio vía Eureka
        String urlFinal = incidenciasBaseUrl + "/api/incidencias";
        
        // Hacemos la petición GET de forma síncrona
        return restTemplate.getForObject(urlFinal, String.class);
    }
}