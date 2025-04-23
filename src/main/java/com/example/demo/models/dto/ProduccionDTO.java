        package com.example.demo.models.dto;

        import lombok.Data;
        import java.util.Date;
        import java.util.List;

        @Data
        public class ProduccionDTO {
            private Long id;
            private String codigoProduccion; // Nuevo campo
            private Date fechaInicio;
            private Date fechaFin;
            private int cantidad_producida;
            private String producto_nombre;
            private String estado;
            private OperarioIngresoDto operario;
        }
        