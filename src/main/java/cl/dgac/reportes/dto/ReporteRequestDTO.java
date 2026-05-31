package cl.dgac.reportes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReporteRequestDTO {

    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipoReporte;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El ID de la incidencia es obligatorio")
    private Long incidenciaId;

    @NotNull(message = "El ID del plan de vuelo es obligatorio")
    private Long planVueloId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
}