package tech_ops.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech_ops.project.entity.Equipment;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    List<Equipment> findByParentId(Long parentId);

    List<Equipment> findByParentIdIsNullAndInventoryNumberIsNotNull();

    Optional<Equipment> findByInventoryNumberAndParentIsNull(String inventoryNumber);

    List<Equipment> findByEmployee_Id(Long userId);

    boolean existsByInventoryNumberIn(Collection<String> inventoryNumbers);

    boolean existsBySerialNumberIn(Collection<String> serialNumbers);

    @Query(value = "SELECT inventory_number FROM equipment WHERE inventory_number LIKE 'ИТ%' ORDER BY inventory_number DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLastInventoryNumber();
}
