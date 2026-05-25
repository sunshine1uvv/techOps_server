package tech_ops.project.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tech_ops.project.dto.EquipmentTypeDto;
import tech_ops.project.entity.EquipmentType;
import tech_ops.project.repository.EquipmentTypeRepository;

@Controller
public class EquipmentTypeMapper {

    private final EquipmentTypeRepository equipmentTypeRepository;

    @Autowired
    public EquipmentTypeMapper(EquipmentTypeRepository equipmentTypeRepository) {
        this.equipmentTypeRepository = equipmentTypeRepository;
    }

    public EquipmentType toEntity(EquipmentTypeDto dto) {
        if (dto == null) return null;

        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setId(dto.getId());

        if (dto.getParent().getId() != null) {
            EquipmentType parent = equipmentTypeRepository.findById(dto.getParent().getId())
                    .orElseThrow(() -> new RuntimeException("Родительский тип оборудования не найден"));
            equipmentType.setParent(parent);
        }

        equipmentType.setName(dto.getName());
        equipmentType.setLevel(dto.getLevel());
        equipmentType.setCode(dto.getCode());
        equipmentType.setFullCode(dto.getFullCode());

        return equipmentType;
    }

    public EquipmentTypeDto toDto(EquipmentType entity) {
        if (entity == null) return null;

        EquipmentTypeDto dto = new EquipmentTypeDto();
        dto.setId(entity.getId());

        if (entity.getParent() != null) {
            dto.setParent(this.toDto(entity.getParent()));
        }

        dto.setName(entity.getName());
        dto.setLevel(entity.getLevel());
        dto.setCode(entity.getCode());
        dto.setFullCode(entity.getFullCode());

        return dto;
    }
}
