package tech_ops.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech_ops.project.entity.User;
import tech_ops.project.entity.UserRole;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByRole(UserRole role);
    boolean existsByPhoneNumber(String phoneNumber);
    void deleteById(Long userId);
}
