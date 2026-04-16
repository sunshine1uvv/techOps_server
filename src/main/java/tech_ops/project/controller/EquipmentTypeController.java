package tech_ops.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech_ops.project.entity.EquipmentType;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public List<EquipmentType> findAll() {
        return service.findAll();
    }

    @GetMapping("/level/{level}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public List<EquipmentType> findAllByLevel(@PathVariable Integer level) {
        return service.findAllByLevel(level);
    }
}
