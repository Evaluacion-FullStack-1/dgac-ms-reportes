package cl.dgac.reportes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Modelo de petición para la creación o actualización de un reporte técnico consolidado")
public class ReporteRequestDTO {

    @Schema(description = "Categoría o clasificación del documento (ej. AUDITORIA, INCIDENTE, ESTADISTICO)", example = "INCIDENTE")
    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipoReporte;

    @Schema(description = "Título o asunto principal del informe", example = "Análisis Técnico de Infracción en Espacio Aéreo Restringido")
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @Schema(description = "Cuerpo principal del documento con las conclusiones, resoluciones o detalles analizados", example = "El presente reporte consolida los datos de vuelo del drone, evidenciando una superación del límite de altura...")
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Schema(description = "Identificador único de la incidencia asociada que motivó este reporte", example = "8")
    @NotNull(message = "El ID de la incidencia es obligatorio")
    private Long incidenciaId;

    @Schema(description = "Identificador único del plan de vuelo analizado en el documento", example = "502")
    @NotNull(message = "El ID del plan de vuelo es obligatorio")
    private Long planVueloId;

    @Schema(description = "Identificador único del funcionario o auditor de la DGAC que elabora el reporte", example = "42")
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
}