package com.example.demo.models.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "produccion")
public class Produccion {
    private String id;

    private Long idSecuencial;
    private Integer cantidadProducida;
    private String codigoProduccion;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String productoNombre;
    private String operarioId;
    private String ordenPedidoId;

    private List<DetallePedidoEmbed> detallePedidos;
    private List<InsumoProduccionEmbed> insumosProduccion;
}
