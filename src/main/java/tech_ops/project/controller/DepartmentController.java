package tech_ops.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech_ops.project.dto.DepartmentDto;
import tech_ops.project.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<DepartmentDto> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    @PostMapping
    public DepartmentDto save(@RequestBody DepartmentDto dto) {
        System.out.println(dto);
        return departmentService.save(dto);
    }

    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        departmentService.deleteById(id);
    }
}
