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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public OrdenPedidoDTO getOrdenpedido() {
        return ordenpedido;
    }

    public void setOrdenpedido(OrdenPedidoDTO ordenpedido) {
        this.ordenpedido = ordenpedido;
    }

    public ProduccionDTO getProduccion() {
        return produccion;
    }

    public void setProduccion(ProduccionDTO produccion) {
        this.produccion = produccion;
    }
}
