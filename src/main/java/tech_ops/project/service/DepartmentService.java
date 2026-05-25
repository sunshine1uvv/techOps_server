package tech_ops.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech_ops.project.dto.DepartmentDto;
import tech_ops.project.entity.Department;
import tech_ops.project.mapper.DepartmentMapper;
import tech_ops.project.repository.DepartmentRepository;
import tech_ops.project.synchronization.WebSyncService;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final WebSyncService syncService;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository,
                             DepartmentMapper departmentMapper,
                             WebSyncService syncService) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper=departmentMapper;
        this.syncService = syncService;
    }

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream().map(departmentMapper::toDto).toList();
    }

    @Transactional
    public DepartmentDto save(DepartmentDto dto) {
        Department entity = departmentMapper.toEntity(dto);
        Department saved = departmentRepository.save(entity);
        syncService.sendDepartmentSync("CREATE", List.of(departmentMapper.toDto(saved)));
        return departmentMapper.toDto(saved);
    }

    @Transactional
    public void deleteById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow();
        departmentRepository.deleteById(id);
        syncService.sendDepartmentSync("DELETE", List.of(departmentMapper.toDto(department)));
    }
}
