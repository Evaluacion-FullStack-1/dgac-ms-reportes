package cl.dgac.reportes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Modelo de respuesta con la información detallada de un reporte técnico consolidado")
public class ReporteResponseDTO {

    @Schema(description = "Identificador interno del reporte en el sistema", example = "1")
    private Long id;

    @Schema(description = "Código único oficial generado automáticamente para el documento", example = "REP-2026-042")
    private String codigoReporte;

    @Schema(description = "Categoría o clasificación del documento", example = "INCIDENTE")
    private String tipoReporte;

    @Schema(description = "Título o asunto principal del informe", example = "Análisis Técnico de Infracción en Espacio Aéreo Restringido")
    private String titulo;

    @Schema(description = "Cuerpo principal del documento con las conclusiones y detalles", example = "El presente reporte consolida los datos de vuelo del drone, evidenciando una superación del límite de altura...")
    private String descripcion;

    @Schema(description = "Fecha en la que se generó o emitió el reporte oficial", example = "2026-06-26")
    private LocalDate fechaGeneracion;

    @Schema(description = "Estado actual del documento (ej. BORRADOR, PUBLICADO, ARCHIVADO)", example = "PUBLICADO")
    private String estado;

    @Schema(description = "Identificador único de la incidencia asociada que motivó este reporte", example = "8")
    private Long incidenciaId;

    @Schema(description = "Identificador único del plan de vuelo analizado en el documento", example = "502")
    private Long planVueloId;

    @Schema(description = "Identificador único del funcionario o auditor de la DGAC que elaboró el reporte", example = "42")
    private Long usuarioId;
}