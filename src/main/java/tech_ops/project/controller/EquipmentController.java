package tech_ops.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech_ops.project.dto.EquipmentDto;
import tech_ops.project.service.EquipmentService;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService service;

    @Autowired
    public EquipmentController(EquipmentService service) {
        this.service = service;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<EquipmentDto> findAll() {
        return service.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public EquipmentDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}")
    public List<EquipmentDto> getEquipmentByUserId(@PathVariable Long userId) {
        return service.findByUserId(userId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/bundle/{id}")
    public List<EquipmentDto> getBundle(@PathVariable Long id) {
        return service.findBundle(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/parent/{id}")
    public List<EquipmentDto> getEquipmentByParentId(@PathVariable Long id) {
        return service.findEquipmentByParentId(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping
    public EquipmentDto save(@Valid @RequestBody EquipmentDto equipmentDto) {
        return service.save(equipmentDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/roots")
    public List<EquipmentDto> getRootEquipment() {
        return service.findEquipmentByParentIdIsNullAndInventoryNumberIsNotNull();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/attach")
    public void attachToParent(@RequestParam("parent_id") Long parentId,
                               @RequestParam("child_id") Long childId) {
        service.attachToParent(parentId, childId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/detach")
    public void detachFromParent(@RequestParam("child_id") Long childId) {
        service.detachFromParent(childId);
    }
}