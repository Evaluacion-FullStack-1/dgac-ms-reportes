package cl.dgac.reportes.controller;

import cl.dgac.reportes.dto.ReporteRequestDTO;
import cl.dgac.reportes.dto.ReporteResponseDTO;
import cl.dgac.reportes.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Operaciones para la generación, consulta y gestión de informes técnicos, auditorías y estadísticas del ecosistema de drones DGAC")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @Operation(summary = "Listar todos los reportes", description = "Obtiene un registro completo de todos los informes técnicos y estadísticos generados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> listarReportes() {
        return ResponseEntity.ok(reporteService.listarReportes());
    }

    @Operation(summary = "Buscar reporte por ID", description = "Obtiene los detalles de un informe específico mediante su identificador único interno.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.buscarPorId(id));
    }

    @Operation(
            summary = "Crear nuevo reporte", 
            description = "Genera un nuevo documento o informe analítico consolidando datos de la operación.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Estructura de datos para crear un nuevo reporte",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de Creación de Reporte",
                                    summary = "JSON de prueba para reporte de incidente",
                                    value = "{\n  \"tipoReporte\": \"INCIDENTE\",\n  \"titulo\": \"Análisis Técnico de Infracción en Espacio Aéreo Restringido\",\n  \"descripcion\": \"El drone sobrepasó el límite de altura permitido y perdió señal de control temporalmente.\",\n  \"incidenciaId\": 8,\n  \"planVueloId\": 502,\n  \"usuarioId\": 42\n}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. código duplicado)")
    })
    @PostMapping
    public ResponseEntity<ReporteResponseDTO> crearReporte(
            @Valid @RequestBody ReporteRequestDTO dto) {

        ReporteResponseDTO reporteCreado = reporteService.crearReporte(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteCreado);
    }

    @Operation(
            summary = "Actualizar reporte", 
            description = "Modifica el contenido, estado o metadatos de un informe técnico existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Estructura de datos para actualizar el reporte",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de Actualización de Reporte",
                                    summary = "JSON de prueba para modificar un reporte",
                                    value = "{\n  \"tipoReporte\": \"AUDITORIA\",\n  \"titulo\": \"Revisión General Actualizada\",\n  \"descripcion\": \"Se adjuntan nuevos antecedentes al caso original.\",\n  \"incidenciaId\": 8,\n  \"planVueloId\": 502,\n  \"usuarioId\": 42\n}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> actualizarReporte(
            @PathVariable Long id,
            @Valid @RequestBody ReporteRequestDTO dto) {

        return ResponseEntity.ok(reporteService.actualizarReporte(id, dto));
    }

    @Operation(summary = "Eliminar reporte", description = "Elimina un registro de reporte del sistema mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado exitosamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar por código de reporte", description = "Busca el registro exacto utilizando el código oficial del documento generado (ej. REP-2026-042).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado"),
            @ApiResponse(responseCode = "404", description = "Código no registrado")
    })
    @GetMapping("/buscar-codigo")
    public ResponseEntity<ReporteResponseDTO> buscarPorCodigoReporte(
            @RequestParam String codigoReporte) {

        return ResponseEntity.ok(reporteService.buscarPorCodigoReporte(codigoReporte));
    }

    @Operation(summary = "Filtrar por estado del reporte", description = "Obtiene una lista de informes según su estado actual (ej. BORRADOR, PUBLICADO, ARCHIVADO).")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    @GetMapping("/estado")
    public ResponseEntity<List<ReporteResponseDTO>> listarPorEstado(
            @RequestParam String estado) {

        return ResponseEntity.ok(reporteService.listarPorEstado(estado));
    }

    @Operation(summary = "Filtrar por tipo de reporte", description = "Obtiene una lista de informes según su categorización (ej. AUDITORIA, ESTADISTICO, INCIDENTE).")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    @GetMapping("/tipo")
    public ResponseEntity<List<ReporteResponseDTO>> listarPorTipoReporte(
            @RequestParam String tipoReporte) {

        return ResponseEntity.ok(reporteService.listarPorTipoReporte(tipoReporte));
    }

    @Operation(summary = "Listar reportes por Incidencia", description = "Obtiene todos los informes técnicos y resoluciones asociadas a un ID de incidencia específico.")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    @GetMapping("/incidencia/{incidenciaId}")
    public ResponseEntity<List<ReporteResponseDTO>> listarPorIncidencia(
            @PathVariable Long incidenciaId) {

        return ResponseEntity.ok(reporteService.listarPorIncidencia(incidenciaId));
    }

    @Operation(summary = "Buscar por título de reporte", description = "Busca coincidencias parciales en el título o asunto del informe técnico.")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    @GetMapping("/titulo")
    public ResponseEntity<List<ReporteResponseDTO>> buscarPorTitulo(
            @RequestParam String titulo) {

        return ResponseEntity.ok(reporteService.buscarPorTitulo(titulo));
    }

    @Operation(summary = "Consultar estado del servicio de Incidencias (WebClient)", description = "Endpoint de integración para verificar la disponibilidad del microservicio de Incidencias.")
    @ApiResponse(responseCode = "200", description = "Comunicación exitosa con el microservicio de Incidencias")
    @GetMapping("/incidencias")
    public ResponseEntity<String> consultarIncidencias() {
        return ResponseEntity.ok(reporteService.consultarMicroservicioIncidencias());
    }
}