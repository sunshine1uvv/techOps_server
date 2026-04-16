package tech_ops.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech_ops.project.entity.Equipment;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    List<Equipment> findByParentId(Long parentId);

    List<Equipment> findByParentIdIsNullAndInventoryNumberIsNotNull();

    Optional<Equipment> findByInventoryNumberAndParentIsNull(String inventoryNumber);

    List<Equipment> findByEmployee_Id(Long userId);
}
