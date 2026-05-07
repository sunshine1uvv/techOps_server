package tech_ops.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech_ops.project.entity.RegistrationRequest;
import tech_ops.project.enums.RequestStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {
    List<RegistrationRequest> findByStatus(RequestStatus status);
    Optional<RegistrationRequest> findByUsername(String username);
    boolean existsByUsernameAndStatusNot(String username, RequestStatus status);
    boolean existsByPhoneNumberAndStatusNot(String phoneNumber, RequestStatus status);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
    void deleteById(Long id);
}
