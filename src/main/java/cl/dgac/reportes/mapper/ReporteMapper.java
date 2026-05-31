package cl.dgac.reportes.mapper;

import cl.dgac.reportes.dto.ReporteRequestDTO;
import cl.dgac.reportes.dto.ReporteResponseDTO;
import cl.dgac.reportes.model.Reporte;
import org.springframework.stereotype.Component;

@Component
public class ReporteMapper {

    public Reporte toEntity(ReporteRequestDTO dto) {
        Reporte reporte = new Reporte();

        reporte.setTipoReporte(dto.getTipoReporte());
        reporte.setTitulo(dto.getTitulo());
        reporte.setDescripcion(dto.getDescripcion());
        reporte.setIncidenciaId(dto.getIncidenciaId());
        reporte.setPlanVueloId(dto.getPlanVueloId());
        reporte.setUsuarioId(dto.getUsuarioId());

        return reporte;
    }

    public ReporteResponseDTO toDTO(Reporte reporte) {
        ReporteResponseDTO dto = new ReporteResponseDTO();

        dto.setId(reporte.getId());
        dto.setCodigoReporte(reporte.getCodigoReporte());
        dto.setTipoReporte(reporte.getTipoReporte());
        dto.setTitulo(reporte.getTitulo());
        dto.setDescripcion(reporte.getDescripcion());
        dto.setFechaGeneracion(reporte.getFechaGeneracion());
        dto.setEstado(reporte.getEstado());
        dto.setIncidenciaId(reporte.getIncidenciaId());
        dto.setPlanVueloId(reporte.getPlanVueloId());
        dto.setUsuarioId(reporte.getUsuarioId());

        return dto;
    }

    public void updateEntity(Reporte reporte, ReporteRequestDTO dto) {
        reporte.setTipoReporte(dto.getTipoReporte());
        reporte.setTitulo(dto.getTitulo());
        reporte.setDescripcion(dto.getDescripcion());
        reporte.setIncidenciaId(dto.getIncidenciaId());
        reporte.setPlanVueloId(dto.getPlanVueloId());
        reporte.setUsuarioId(dto.getUsuarioId());
    }
}