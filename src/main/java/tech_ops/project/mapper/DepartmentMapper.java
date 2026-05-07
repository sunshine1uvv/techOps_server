package tech_ops.project.mapper;

import org.springframework.stereotype.Component;
import tech_ops.project.dto.DepartmentDto;
import tech_ops.project.entity.Department;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentDto dto) {
        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        return department;
    }

    public DepartmentDto toDto(Department department) {
        if(department == null) return null;
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        return dto;
    }
}
