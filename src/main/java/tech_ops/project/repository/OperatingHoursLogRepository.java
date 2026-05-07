package tech_ops.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech_ops.project.entity.OperatingHoursLog;

import java.util.List;

@Repository
public interface OperatingHoursLogRepository extends JpaRepository<OperatingHoursLog, Long> {
    List<OperatingHoursLog> findByEquipmentIdOrderByLogDateDesc(Long equipmentId);
}
