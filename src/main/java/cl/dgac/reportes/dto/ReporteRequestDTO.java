package cl.dgac.reportes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReporteRequestDTO {

    @NotBlank(message = "El código del reporte es obligatorio")
    private String codigoReporte;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipoReporte;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La fecha de generación es obligatoria")
    private LocalDate fechaGeneracion;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "El ID de la incidencia es obligatorio")
    private Long incidenciaId;

    @NotNull(message = "El ID del plan de vuelo es obligatorio")
    private Long planVueloId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
}