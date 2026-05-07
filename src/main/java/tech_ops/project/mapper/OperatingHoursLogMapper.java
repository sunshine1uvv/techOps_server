package tech_ops.project.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import tech_ops.project.dto.OperatingHoursLogDto;
import tech_ops.project.entity.Equipment;
import tech_ops.project.entity.OperatingHoursLog;
import tech_ops.project.entity.User;
import tech_ops.project.repository.EquipmentRepository;
import tech_ops.project.repository.UserRepository;

@Component
public class OperatingHoursLogMapper {

    private final EquipmentRepository equipmentRepository;

    private final UserRepository userRepository;

    private final EquipmentMapper equipmentMapper;

    private final UserMapper userMapper;

    @Autowired
    public OperatingHoursLogMapper(EquipmentRepository equipmentRepository,
                                   UserRepository userRepository,
                                   EquipmentMapper equipmentMapper,
                                   UserMapper userMapper) {
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
        this.equipmentMapper = equipmentMapper;
        this.userMapper = userMapper;
    }

    public OperatingHoursLog toEntity(OperatingHoursLogDto dto) {
        OperatingHoursLog log = new OperatingHoursLog();
        log.setId(dto.getId());
        if (dto.getEquipment() != null && dto.getEquipment().getId() != null) {
            Equipment equipment = equipmentRepository.findById(dto.getEquipment().getId())
                    .orElseThrow(() -> new RuntimeException("Оборудование не найдено"));
            log.setEquipment(equipment);
        }
        if (dto.getUser() != null && dto.getUser().getId() != null) {
            User user = userRepository.findById(dto.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            log.setUser(user);
        }
        log.setHoursAdded(dto.getHoursAdded());
        if (dto.getLogDate() != null) {
            log.setLogDate(dto.getLogDate());
        }
        return log;
    }

    public OperatingHoursLogDto toDto(OperatingHoursLog log) {
        OperatingHoursLogDto dto = new OperatingHoursLogDto();
        dto.setId(log.getId());
        dto.setEquipment(equipmentMapper.toDto(log.getEquipment()));
        dto.setUser(userMapper.toDto(log.getUser()));
        dto.setHoursAdded(log.getHoursAdded());
        dto.setLogDate(log.getLogDate());
        return dto;
    }
}
