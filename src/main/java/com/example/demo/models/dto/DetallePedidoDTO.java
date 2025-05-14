package com.example.demo.models.dto;

import com.example.demo.models.entity.OrdenPedido;
import com.example.demo.models.entity.Produccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedidoDTO {
    private Long id;
    private int cantidad;
    private OrdenPedidoDTO ordenpedido;  // Campo para almacenar la ID de OrdenPedido
    private ProductoDTO producto; // Incluye ProductoDTO para asociar el producto
    private ProduccionDTO produccion;

}
