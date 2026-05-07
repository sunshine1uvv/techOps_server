package tech_ops.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech_ops.project.entity.Department;
import tech_ops.project.entity.Equipment;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
