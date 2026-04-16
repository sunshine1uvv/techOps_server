package tech_ops.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech_ops.project.dto.RequestResponseDto;
import tech_ops.project.dto.ReviewRequestDto;
import tech_ops.project.entity.RegistrationRequest;
import tech_ops.project.entity.RequestStatus;
import tech_ops.project.entity.User;
import tech_ops.project.repository.RegistrationRequestRepository;
import tech_ops.project.security.UserDetailsImpl;
import tech_ops.project.synchronization.WebSyncService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationRequestService {

    private final RegistrationRequestRepository repository;
    private final UserService userService;
    private final WebSyncService syncService;

    @Autowired
    public RegistrationRequestService(RegistrationRequestRepository repository, UserService userService, WebSyncService syncService) {
        this.repository = repository;
        this.userService = userService;
        this.syncService = syncService;
    }

    public List<RequestResponseDto> getAllRequests() {
        return repository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<RequestResponseDto> getRequestsByStatus(RequestStatus status) {
        return repository.findByStatus(status).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public List<RequestResponseDto> getReviewedRequests() {
        List<RequestResponseDto> reviewedRequests = new ArrayList<>();
        reviewedRequests.addAll(repository.findByStatus(RequestStatus.APPROVED).stream().map(this::convertToDto).toList());
        reviewedRequests.addAll(repository.findByStatus(RequestStatus.REJECTED).stream().map(this::convertToDto).toList());
        return reviewedRequests;
    }

    @Transactional
    public void reviewRequest(Long requestId, ReviewRequestDto reviewDto) {
        RegistrationRequest request = repository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Запрос на регистрацию не найден"));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Запрос на регистрацию уже рассмотрен");
        }

        UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User reviewer = userService.getUserById(currentUser.getId());

        if (reviewDto.getStatus() == RequestStatus.APPROVED) {
            userService.createUserFromRequest(request, reviewDto.getRole());
            request.setStatus(RequestStatus.APPROVED);
        } else if (reviewDto.getStatus() == RequestStatus.REJECTED) {
            request.setStatus(RequestStatus.REJECTED);
        } else {
            throw new RuntimeException("Invalid review status");
        }

        request.setReviewedBy(reviewer);
        request.setReviewedAt(LocalDateTime.now());
        RegistrationRequest saved = repository.save(request);
        syncService.sendRequestSync("UPDATE", List.of(convertToDto(saved)));
    }

    @Transactional
    public void restoreRequest(Long requestId) {
        RegistrationRequest request = repository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Запрос на регистрацию не найден"));

        if (request.getStatus() != RequestStatus.REJECTED) {
            throw new RuntimeException("Восстановить можно только отклоненные запросы");
        }

        request.setStatus(RequestStatus.PENDING);
        request.setReviewedBy(null);
        request.setReviewedAt(null);
        RegistrationRequest saved = repository.save(request);
        syncService.sendRequestSync("UPDATE", List.of(convertToDto(saved)));
    }

    @Transactional
    public void save(RegistrationRequest request) {
        boolean isNew = request.getId() == null;
        RegistrationRequest saved = repository.save(request);
        syncService.sendRequestSync(isNew ? "CREATE" : "UPDATE", List.of(convertToDto(saved)));
    }

    public boolean existsByPhoneNumberAndStatusNot(String phoneNumber, RequestStatus status) {
        return repository.existsByPhoneNumberAndStatusNot(phoneNumber, status);
    }

    public boolean existsByUsernameAndStatusNot(String username, RequestStatus status) {
        return repository.existsByUsernameAndStatusNot(username, status);
    }

    public void deleteByUsername(String username) {
        RegistrationRequest request = repository.findByUsername(username).orElseThrow();
        repository.delete(request);
        syncService.sendRequestSync("DELETE", List.of(convertToDto(request)));
    }

    public RegistrationRequest getByUsername(String username) {
        return repository.findByUsername(username).orElseThrow();
    }

    private RequestResponseDto convertToDto(RegistrationRequest request) {
        RequestResponseDto dto = new RequestResponseDto();
        dto.setId(request.getId());
        dto.setUsername(request.getUsername());
        dto.setName(request.getName());
        dto.setSurname(request.getSurname());
        dto.setPatronymic(request.getPatronymic());
        dto.setMilitaryRank(request.getMilitaryRank());
        dto.setPhoneNumber(request.getPhoneNumber());
        dto.setRequestedRole(request.getRequestedRole());
        dto.setStatus(request.getStatus());
        dto.setCreatedAt(request.getCreatedAt());
        if (request.getReviewedBy() != null) {
            dto.setReviewedByUsername(request.getReviewedBy().getUsername());
        }
        dto.setReviewedAt(request.getReviewedAt());
        return dto;
    }
}