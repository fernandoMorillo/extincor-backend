    package com.example.demo.services.impl;

    import com.example.demo.models.dto.InsumoDTO;
    import com.example.demo.models.entity.Insumo;
    import com.example.demo.repository.InsumoRepository;
    import com.example.demo.services.InsumoService;
    import jakarta.transaction.Transactional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class InsumoServiceImpl implements InsumoService {

        @Autowired
        private InsumoRepository insumoRepository;

        @Override
        @Transactional
        public List<InsumoDTO> findAll() {
            return insumoRepository.findAll()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional
        public InsumoDTO findById(Long id) {
            Insumo insumo = insumoRepository.findById(id).orElse(null);
            return insumo != null ? convertToDTO(insumo) : null;
        }

        @Override
        @Transactional
        public InsumoDTO save(InsumoDTO insumoDTO) {
            Insumo insumo = convertToEntity(insumoDTO);
            Insumo savedInsumo = insumoRepository.save(insumo);
            return convertToDTO(savedInsumo);
        }

        @Override
        @Transactional
        public void deleteById(Long id) {
            insumoRepository.deleteById(id);
        }

        private InsumoDTO convertToDTO(Insumo insumo) {
            InsumoDTO dto = new InsumoDTO();
            dto.setId(insumo.getId());
            dto.setNombre(insumo.getNombre());
            dto.setStock(insumo.getStock());
            dto.setCantidad(insumo.getCantidad());
            dto.setUnidades(insumo.getUnidades());
            return dto;
        }

        private Insumo convertToEntity(InsumoDTO insumoDTO) {
            Insumo insumo = new Insumo();
            insumo.setId(insumoDTO.getId());
            insumo.setNombre(insumoDTO.getNombre());
            insumo.setStock(insumoDTO.getStock());
            insumo.setCantidad(insumoDTO.getCantidad());
            insumo.setUnidades(insumoDTO.getUnidades());
            return insumo;
        }
    }
