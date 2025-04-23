package com.example.demo.services.impl;

import com.example.demo.models.dto.CompraDTO;
import com.example.demo.models.dto.DetalleCompraDTO;
import com.example.demo.models.dto.InsumoDTO;
import com.example.demo.models.entity.Compra;
import com.example.demo.models.entity.DetalleCompra;
import com.example.demo.repository.CompraRepository;
import com.example.demo.repository.DetalleCompraRepository;
import com.example.demo.repository.InsumoRepository;
import com.example.demo.services.DetalleCompraService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleCompraServiceImpl implements DetalleCompraService {

    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Override
    @Transactional
    public List<DetalleCompraDTO> findAll() {
        return detalleCompraRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DetalleCompraDTO findById(Long id) {
        DetalleCompra detalleCompra = detalleCompraRepository.findById(id).orElse(null);
        return detalleCompra != null ? convertToDTO(detalleCompra) : null;
    }

    @Override
    @Transactional
    public DetalleCompraDTO save(DetalleCompraDTO detalleCompraDTO) {
        DetalleCompra detalleCompra = convertToEntity(detalleCompraDTO);
        DetalleCompra savedDetalleCompra = detalleCompraRepository.save(detalleCompra);
        return convertToDTO(savedDetalleCompra);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        detalleCompraRepository.deleteById(id);
    }

    private DetalleCompraDTO convertToDTO(DetalleCompra detalleCompra) {
        DetalleCompraDTO dto = new DetalleCompraDTO();
        dto.setId(detalleCompra.getId());
        dto.setCantidad(detalleCompra.getCantidad());

        // Solo establece el ID en CompraDTO
        if (detalleCompra.getCompra() != null) {
            CompraDTO compraDTO = new CompraDTO();
            compraDTO.setId(detalleCompra.getCompra().getId());
            dto.setCompraId(compraDTO);
        }

        // Asignar el insumo al DTO
        if (detalleCompra.getInsumo() != null) {
            InsumoDTO insumoDTO = new InsumoDTO();
            insumoDTO.setId(detalleCompra.getInsumo().getId());
            insumoDTO.setNombre(detalleCompra.getInsumo().getNombre());
            insumoDTO.setStock(detalleCompra.getInsumo().getStock());
            dto.setInsumo(insumoDTO);
        }

        return dto;
    }

    private DetalleCompra convertToEntity(DetalleCompraDTO detalleCompraDTO) {
        DetalleCompra detalleCompra = new DetalleCompra();
        detalleCompra.setId(detalleCompraDTO.getId());
        detalleCompra.setCantidad(detalleCompraDTO.getCantidad());

        // Verifica si CompraDTO no es nulo y establece solo el ID
        if (detalleCompraDTO.getCompraId() != null && detalleCompraDTO.getCompraId().getId() != null) {
            compraRepository.findById(detalleCompraDTO.getCompraId().getId()).ifPresent(detalleCompra::setCompra);
        }

        // Buscar y asignar el insumo en la entidad
        if (detalleCompraDTO.getInsumo() != null && detalleCompraDTO.getInsumo().getId() != null) {
            insumoRepository.findById(detalleCompraDTO.getInsumo().getId()).ifPresent(detalleCompra::setInsumo);
        }

        return detalleCompra;
    }
}
