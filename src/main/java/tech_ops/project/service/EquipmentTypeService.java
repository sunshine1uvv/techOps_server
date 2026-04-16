package tech_ops.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import tech_ops.project.entity.EquipmentType;
import tech_ops.project.repository.EquipmentTypeRepository;

import java.util.List;

@Service
public class EquipmentTypeService {

    private final EquipmentTypeRepository repository;

    @Autowired
    public EquipmentTypeService(EquipmentTypeRepository repository) {
        this.repository = repository;
    }

    public List<EquipmentType> findAll() {
        return repository.findAll();
    }

    public List<EquipmentType> findAllByLevel(Integer level) {
        return repository.findAllByLevel(level);
    }
}
