package com.example.demo.controller;

import com.example.demo.models.dto.DetallePedidoDTO;
import com.example.demo.services.DetallePedidoService;
import com.example.demo.services.OrdenPedidoService;
import com.example.demo.services.ProductoService;
import com.example.demo.services.ProduccionService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private OrdenPedidoService ordenPedidoService;

    @Autowired
    private ProduccionService produccionService;

    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> getAllDetalles() {
        return ResponseEntity.ok(detallePedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> getDetalleById(@PathVariable Long id) {
        DetallePedidoDTO detalle = detallePedidoService.findById(id);
        if (detalle == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(detalle);
    }

    @PostMapping
    public ResponseEntity<String> saveDetalle(@RequestBody DetallePedidoDTO detallePedidoDTO) {
        detallePedidoService.save(detallePedidoDTO);
        return ResponseEntity.ok("Detalle guardado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDetalle(@PathVariable Long id, @RequestBody DetallePedidoDTO detallePedidoDTO) {
        detallePedidoDTO.setId(id);
        detallePedidoService.save(detallePedidoDTO);
        return ResponseEntity.ok("Detalle actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDetalle(@PathVariable Long id) {
        detallePedidoService.deleteById(id);
        return ResponseEntity.ok("Detalle eliminado exitosamente");
    }

    // Exportar todos los detalles en PDF
    @GetMapping("/pdf")
    public void exportAllToPDF(HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=detalles-pedido.pdf");

            OutputStream out = response.getOutputStream();
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            doc.add(new Paragraph("Detalles de Pedido").setBold().setFontSize(14));

            Table table = new Table(new float[]{2, 3, 3, 3});
            table.setWidth(UnitValue.createPercentValue(100));
            table.addCell(new Cell().add(new Paragraph("Cantidad").setBold()));
            table.addCell(new Cell().add(new Paragraph("Producto").setBold()));
            table.addCell(new Cell().add(new Paragraph("Orden de Pedido").setBold()));
            table.addCell(new Cell().add(new Paragraph("Producción").setBold()));

            for (DetallePedidoDTO d : detallePedidoService.findAll()) {
                table.addCell(String.valueOf(d.getCantidad()));
                table.addCell(d.getProducto() != null ?
                        "Código: " + d.getProducto().getCodigo() + " - " + d.getProducto().getNombre() :
                        "Producto no disponible");
                table.addCell(d.getOrdenpedido() != null ?
                        "Número: " + d.getOrdenpedido().getNumeroPedido() :
                        "Orden no disponible");
                table.addCell(d.getProduccion() != null ?
                        "Código: " + d.getProduccion().getCodigoProduccion() :
                        "N/A");
            }

            doc.add(table);
            doc.close();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Exportar detalle específico en PDF
    @GetMapping("/pdf/{id}")
    public void exportDetalleToPDF(@PathVariable Long id, HttpServletResponse response) {
        try {
            DetallePedidoDTO d = detallePedidoService.findById(id);
            if (d == null) throw new RuntimeException("Detalle no encontrado");

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=detalle-" + id + ".pdf");

            OutputStream out = response.getOutputStream();
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            doc.setMargins(20, 20, 20, 20);
            doc.add(new Paragraph("Factura de Pedido").setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("Empresa Extincor").setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("Dirección: Calle Principal #123, Ciudad").setFontSize(10).setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("Teléfono: +1 234 567 890, Email: info@empresa.com").setFontSize(10).setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("\n"));

            doc.add(new Paragraph("Información del Cliente").setBold());
            doc.add(new Paragraph("Cliente: " + (
                    d.getOrdenpedido() != null && d.getOrdenpedido().getCliente() != null
                            ? d.getOrdenpedido().getCliente().getNombre() : "No disponible")));
            doc.add(new Paragraph("Dirección: " + (
                    d.getOrdenpedido() != null && d.getOrdenpedido().getCliente() != null
                            ? d.getOrdenpedido().getCliente().getDireccion() : "No disponible")));
            doc.add(new Paragraph("Teléfono: " + (
                    d.getOrdenpedido() != null && d.getOrdenpedido().getCliente() != null
                            ? d.getOrdenpedido().getCliente().getTelefono() : "No disponible")));
            doc.add(new Paragraph("Fecha del Pedido: " + (
                    d.getOrdenpedido() != null ? d.getOrdenpedido().getFechaPedido() : "No disponible")));
            doc.add(new Paragraph("\n"));

            Table table = new Table(new float[]{2, 5, 3, 3});
            table.setWidth(UnitValue.createPercentValue(100));
            table.addHeaderCell("Cantidad");
            table.addHeaderCell("Producto");
            table.addHeaderCell("Orden de Pedido");
            table.addHeaderCell("Producción");

            table.addCell(String.valueOf(d.getCantidad()));
            table.addCell(d.getProducto() != null ?
                    "Código: " + d.getProducto().getCodigo() + ", Nombre: " + d.getProducto().getNombre() :
                    "Producto no disponible");
            table.addCell(d.getOrdenpedido() != null ?
                    "Número: " + d.getOrdenpedido().getNumeroPedido() :
                    "Orden no disponible");
            table.addCell(d.getProduccion() != null ?
                    "Código: " + d.getProduccion().getCodigoProduccion() :
                    "Producción no disponible");

            doc.add(table);
            doc.add(new Paragraph("\nGracias por su compra").setTextAlignment(TextAlignment.CENTER).setFontSize(12));
            doc.close();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
