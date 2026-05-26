package cl.dgac.reportes.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReporteResponseDTO {

    private Long id;
    private String codigoReporte;
    private String tipoReporte;
    private String titulo;
    private String descripcion;
    private LocalDate fechaGeneracion;
    private String estado;
    private Long incidenciaId;
    private Long planVueloId;
    private Long usuarioId;
}