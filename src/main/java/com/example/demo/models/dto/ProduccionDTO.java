        package com.example.demo.models.dto;

        import com.example.demo.models.entity.OperarioIngreso;
        import lombok.Data;
        import java.util.Date;

        @Data
        public class ProduccionDTO {
            private Long id;
            private String codigoProduccion; // Nuevo campo
            private Date fechaInicio;
            private Date fechaFin;
            private int cantidad_producida;
            private String producto_nombre;
            private String estado;
            private OperarioIngreso operario;
        }
        