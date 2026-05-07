package tech_ops.project.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech_ops.project.dto.EquipmentDto;
import tech_ops.project.dto.UserDto;
import tech_ops.project.entity.Department;
import tech_ops.project.entity.Equipment;
import tech_ops.project.entity.EquipmentType;
import tech_ops.project.entity.User;
import tech_ops.project.repository.DepartmentRepository;
import tech_ops.project.repository.EquipmentRepository;
import tech_ops.project.repository.UserRepository;

@Component
public class EquipmentMapper {

    private final EquipmentRepository equipmentRepository;

    private final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;

    private final UserMapper userMapper;

    private final DepartmentMapper departmentMapper;

    @Autowired
    public EquipmentMapper(EquipmentRepository equipmentRepository,
                           UserRepository userRepository,
                           DepartmentRepository departmentRepository,
                           UserMapper userMapper,
                           DepartmentMapper departmentMapper) {
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.userMapper = userMapper;
        this.departmentMapper = departmentMapper;
    }

    public Equipment toEntity(EquipmentDto dto) {
        Equipment equipment = new Equipment();
        equipment.setId(dto.getId());

        /** Запрос к базе данных необходим из-за несоответствия полей
         * мы не можем присвоить объекту Equipment с полем Equipment parent
         * поле parent у DTO, т.к. оно имеет тип EquipmentDto
         **/

        if (dto.getParent() != null && dto.getParent().getId() != null) {
            Equipment parent = equipmentRepository.findById(dto.getParent().getId())
                    .orElseThrow(() -> new RuntimeException("Родительское оборудование не найдено"));
            equipment.setParent(parent);
        }

        if (dto.getType() != null && dto.getType().getId() != null) {
            EquipmentType type = dto.getType();
            equipment.setType(type);
        }

        equipment.setName(dto.getName());
        equipment.setInventoryNumber(dto.getInventoryNumber());
        equipment.setSerialNumber(dto.getSerialNumber());

        /**
         *Аналогичная проблема не соответствия полей
         * У Entity Equipment поле user имеет тип User
         * У DTO поле user имеет тип UserDto
         */
        if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
            User user = userRepository.findById(dto.getEmployee().getId())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            equipment.setEmployee(user);
        }
        equipment.setLocation(dto.getLocation());
        equipment.setCategory(dto.getCategory());

        if (dto.getDepartment() != null && dto.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(dto.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException("Подразделение не найдено"));
            equipment.setDepartment(department);
        }

        equipment.setCurrentOperatingHours(dto.getCurrentOperatingHours());
        equipment.setMaxOperatingHours(dto.getMaxOperatingHours());
        return equipment;
    }

    public EquipmentDto toDto(Equipment equipment) {
        if (equipment == null) return null;
        EquipmentDto dto = new EquipmentDto();
        dto.setId(equipment.getId());
        if (equipment.getParent() != null) {
            dto.setParent(this.toDto(equipment.getParent()));
        }
        dto.setType(equipment.getType());
        dto.setName(equipment.getName());
        dto.setInventoryNumber(equipment.getInventoryNumber());
        dto.setSerialNumber(equipment.getSerialNumber());
        dto.setEmployee(userMapper.toDto(equipment.getEmployee()));
        dto.setLocation(equipment.getLocation());
        dto.setCategory(equipment.getCategory());
        dto.setDepartment(departmentMapper.toDto(equipment.getDepartment()));
        dto.setCurrentOperatingHours(equipment.getCurrentOperatingHours());
        dto.setMaxOperatingHours(equipment.getMaxOperatingHours());
        return dto;
    }
}
