package tech_ops.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech_ops.project.dto.EquipmentTypeDto;
import tech_ops.project.entity.EquipmentType;
import tech_ops.project.mapper.EquipmentTypeMapper;
import tech_ops.project.service.EquipmentTypeService;

import java.util.List;

@RestController
@RequestMapping("/equipment/types")
public class EquipmentTypeController {

    private final EquipmentTypeService service;

    @Autowired
    public EquipmentTypeController(EquipmentTypeService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<EquipmentType> findAll() {
        return service.findAll();
    }

    @GetMapping("/level/{level}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public List<EquipmentType> findAllByLevel(@PathVariable Integer level) {
        return service.findAllByLevel(level);
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public EquipmentTypeDto save(@Valid @RequestBody EquipmentTypeDto dto) {
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
