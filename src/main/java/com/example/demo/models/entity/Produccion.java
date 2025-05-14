package com.example.demo.models.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "produccion")
public class Produccion {
    @Id
    private ObjectId id;

    private Long idSecuencial;
    private Integer cantidadProducida;
    private String codigoProduccion;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String productoNombre;
    private ObjectId operarioId;
    private ObjectId ordenPedidoId;

    private List<OrdenPedidoEmbed> ordenesPedido;
    private List<DetallePedidoEmbed> detallePedidos;
    private List<InsumoProduccionEmbed> insumosProduccion;
}
