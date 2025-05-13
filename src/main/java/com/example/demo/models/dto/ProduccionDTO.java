        package com.example.demo.models.dto;

        import com.example.demo.models.entity.OperarioIngreso;
        import lombok.Data;

        import java.time.LocalDateTime;
        import java.util.Date;
        import java.util.List;

        @Data
        public class ProduccionDTO {
            private Long id;
            private String codigoProduccion; // Nuevo campo
            private LocalDateTime fechaInicio;
            private LocalDateTime fechaFin;
            private int cantidad_producida;
            private String producto_nombre;
            private String estado;
            private OperarioIngreso operario;

            private List<Long> insumosProduccion;
            private List<DetallePedidoDTO> detallePedidos;
        }
        