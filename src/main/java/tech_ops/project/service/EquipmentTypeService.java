package tech_ops.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech_ops.project.dto.EquipmentTypeDto;
import tech_ops.project.entity.EquipmentType;
import tech_ops.project.mapper.EquipmentTypeMapper;
import tech_ops.project.repository.EquipmentTypeRepository;
import tech_ops.project.synchronization.WebSyncService;

import java.util.List;

@Service
public class EquipmentTypeService {

    private final EquipmentTypeRepository repository;

    private final EquipmentTypeMapper equipmentTypeMapper;

    private final WebSyncService syncService;

    @Autowired
    public EquipmentTypeService(EquipmentTypeRepository repository,
                                EquipmentTypeMapper equipmentTypeMapper,
                                WebSyncService syncService) {
        this.repository = repository;
        this.equipmentTypeMapper = equipmentTypeMapper;
        this.syncService = syncService;
    }

    public List<EquipmentType> findAll() {
        return repository.findAll();
    }

    public List<EquipmentType> findAllByLevel(Integer level) {
        return repository.findAllByLevel(level);
    }

    @Transactional
    public EquipmentTypeDto save(EquipmentTypeDto dto) {
        EquipmentType entity = equipmentTypeMapper.toEntity(dto);
        if (dto.getParent() != null && dto.getParent().getId() != null) {
            EquipmentType parentFromDb = repository.findById(dto.getParent().getId())
                    .orElseThrow(() -> new RuntimeException("Родитель не найден в БД"));
            entity.setParent(parentFromDb);
        }
        EquipmentType saved = repository.save(entity);
        syncService.sendEquipmentTypeSync("CREATE", List.of(equipmentTypeMapper.toDto(saved)));
        return equipmentTypeMapper.toDto(saved);
    }

    @Transactional
    public void deleteById(Long id) {
            EquipmentType equipmentType = repository.findById(id).orElseThrow();
            repository.deleteById(id);
            syncService.sendEquipmentTypeSync("DELETE", List.of(equipmentTypeMapper.toDto(equipmentType)));
    }
}
