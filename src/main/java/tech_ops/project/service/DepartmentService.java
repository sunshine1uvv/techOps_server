package tech_ops.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech_ops.project.dto.DepartmentDto;
import tech_ops.project.mapper.DepartmentMapper;
import tech_ops.project.repository.DepartmentRepository;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper=departmentMapper;
    }

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream().map(departmentMapper::toDto).toList();
    }
}
