package tech_ops.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
