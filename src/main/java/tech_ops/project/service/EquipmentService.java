package tech_ops.project.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech_ops.project.dto.EquipmentDto;
import tech_ops.project.entity.Equipment;
import tech_ops.project.entity.EquipmentType;
import tech_ops.project.entity.User;
import tech_ops.project.repository.EquipmentRepository;
import tech_ops.project.repository.EquipmentTypeRepository;
import tech_ops.project.repository.UserRepository;
import tech_ops.project.synchronization.WebSyncService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final UserRepository userRepository;
    private final WebSyncService syncService;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository,
                            EquipmentTypeRepository equipmentTypeRepository,
                            UserRepository userRepository,
                            WebSyncService syncService) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.userRepository = userRepository;
        this.syncService = syncService;
    }

    // ------------------- Конвертация -------------------

    private EquipmentDto toDto(Equipment equipment) {
        return EquipmentDto.fromEquipment(equipment);
    }

    private List<EquipmentDto> toDtoList(List<Equipment> equipments) {
        return equipments.stream().map(this::toDto).collect(Collectors.toList());
    }

    private Equipment toEntity(EquipmentDto dto) {
        Equipment equipment = new Equipment();
        equipment.setId(dto.getId());
        if (dto.getParent() != null && dto.getParent().getId() != null) {
            Equipment parent = equipmentRepository.findById(dto.getParent().getId())
                    .orElseThrow(() -> new RuntimeException("Родительское оборудование не найдено"));
            equipment.setParent(parent);
        }

        if (dto.getType() != null && dto.getType().getId() != null) {
            EquipmentType type = equipmentTypeRepository.findById(dto.getType().getId())
                    .orElseThrow(() -> new RuntimeException("Тип оборудования не найден"));
            equipment.setType(type);
        }
        equipment.setName(dto.getName());
        equipment.setInventoryNumber(dto.getInventoryNumber());
        equipment.setSerialNumber(dto.getSerialNumber());
        if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
            User user = userRepository.findById(dto.getEmployee().getId())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            equipment.setEmployee(user);
        }
        equipment.setLocation(dto.getLocation());
        equipment.setCategory(dto.getCategory());
        return equipment;
    }

    @Transactional
    public List<EquipmentDto> findByUserId(Long userId) {
       return toDtoList(equipmentRepository.findByEmployee_Id(userId));
    }

    @Transactional
    public List<EquipmentDto> findAll() {
        return toDtoList(equipmentRepository.findAll());
    }

    @Transactional
    public EquipmentDto findById(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Оборудование не найдено"));
        return toDto(equipment);
    }

    @Transactional
    public EquipmentDto save(EquipmentDto equipmentDto) {
        boolean isNew = (equipmentDto.getId() == null);
        Equipment equipment = toEntity(equipmentDto);
        if (equipment.getParent() != null) {
            throw new RuntimeException("Нельзя менять инвентарный номер у дочерних элементов");
        }
        List<EquipmentDto> affectedItems = new ArrayList<>();

        if (!isNew) {
            Equipment oldState = equipmentRepository.findById(equipment.getId()).orElseThrow();
            boolean inventoryChanged = (oldState.getParent() == null) &&
                    !Objects.equals(oldState.getInventoryNumber(), equipment.getInventoryNumber());
            if (inventoryChanged) {
                List<Equipment> children = equipmentRepository.findByParentId(equipment.getId());
                for (Equipment child : children) {
                    child.setInventoryNumber(equipment.getInventoryNumber());
                }
                equipmentRepository.saveAll(children);
                affectedItems.addAll(toDtoList(children));
            }
        }

        Equipment saved = equipmentRepository.save(equipment);
        affectedItems.add(toDto(saved));

        String action = isNew ? "CREATE" : "UPDATE";
        syncService.sendEquipmentSync(action, affectedItems);

        return toDto(saved);
    }

    @Transactional
    public void deleteById(Long id) {
        Equipment parent = equipmentRepository.findById(id).orElseThrow();
        List<Equipment> children = equipmentRepository.findByParentId(id);
        for (Equipment child : children) {
            child.setParent(null);
            child.setInventoryNumber(null);
        }
        equipmentRepository.saveAll(children);
        equipmentRepository.delete(parent);
        syncService.sendEquipmentSync("DELETE", List.of(toDto(parent)));
        if (!children.isEmpty()) {
            syncService.sendEquipmentSync("UPDATE", toDtoList(children));
        }
    }

    public List<EquipmentDto> findBundle(Long id) {
        List<Equipment> bundle = new ArrayList<>();
        Equipment e = equipmentRepository.findById(id).orElse(null);
        if (e != null) {
            bundle.add(e);
            bundle.addAll(equipmentRepository.findByParentId(id));
        }
        return toDtoList(bundle);
    }

    @Transactional
    public List<EquipmentDto> findEquipmentByParentId(Long id) {
        return toDtoList(equipmentRepository.findByParentId(id));
    }

    @Transactional
    public List<EquipmentDto> findEquipmentByParentIdIsNullAndInventoryNumberIsNotNull() {
        return toDtoList(equipmentRepository.findByParentIdIsNullAndInventoryNumberIsNotNull());
    }

    @Transactional
    public void attachToParent(Long parentId, Long childId) {
        Equipment child = equipmentRepository.findById(childId).orElseThrow();
        if (!equipmentRepository.findByParentId(childId).isEmpty()) {
            throw new RuntimeException("Невозможно добавить комплект в состав другого комплекта");
        }
        Equipment parent = equipmentRepository.findById(parentId).orElseThrow();
        if (parent.getInventoryNumber() == null) {
            throw new RuntimeException("Невозможно добавить в комплект: у родительского устройства нет инвентарного номера");
        }
        child.setParent(parent);
        child.setInventoryNumber(parent.getInventoryNumber());
        equipmentRepository.save(child);
        syncService.sendEquipmentSync("UPDATE", List.of(toDto(child)));
    }


    @Transactional
    public void detachFromParent(Long childId) {
        Equipment child = equipmentRepository.findById(childId).orElseThrow();
        child.setParent(null);
        child.setInventoryNumber(null);
        equipmentRepository.save(child);
        syncService.sendEquipmentSync("UPDATE", List.of(toDto(child)));
    }
}