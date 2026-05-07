package tech_ops.project.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech_ops.project.dto.UserDto;
import tech_ops.project.entity.RegistrationRequest;
import tech_ops.project.entity.User;
import tech_ops.project.enums.UserRole;
import tech_ops.project.enums.UserStatus;
import tech_ops.project.repository.UserRepository;
import tech_ops.project.synchronization.WebSyncService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final WebSyncService syncService;


    @Autowired
    public UserService(UserRepository repository, WebSyncService syncService) {
        this.repository = repository;
        this.syncService = syncService;
    }

    @Transactional
    public List<UserDto> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto getUserByUsername(String username) {
        User user = repository.findByUsername(username).orElseThrow();
        return UserDto.fromUser(user);
    }

    @Transactional
    public User getUserById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID " + userId + " не найден"));
    }

    @Transactional
    public void updateUserStatus(Long userId, UserStatus newStatus) {
        User user = repository.findById(userId).orElseThrow();
        user.setStatus(newStatus);
        repository.save(user);
        syncService.sendUserSync("UPDATE", List.of(UserDto.fromUser(user)));
    }

    @Transactional
    public void updateUserRole(Long userId, UserRole newRole) {
        User user = repository.findById(userId).orElseThrow();
        user.setRole(newRole);
        repository.save(user);
        syncService.sendUserSync("UPDATE", List.of(UserDto.fromUser(user)));
    }

    @Transactional
    public void deleteUserById(Long userId) {
        User user = repository.findById(userId).orElseThrow();
        repository.delete(user);
        syncService.sendUserSync("DELETE", List.of(UserDto.fromUser(user)));
    }

    @Transactional
    public void save(User user) {
        boolean isNew = user.getId() == null;
        User saved = repository.save(user);
        syncService.sendUserSync(isNew ? "CREATE" : "UPDATE", List.of(UserDto.fromUser(saved)));
    }

    @Transactional
    public void createUserFromRequest(RegistrationRequest request, UserRole assignedRole) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPasswordHash(request.getPasswordHash());
        newUser.setName(request.getName());
        newUser.setSurname(request.getSurname());
        newUser.setPatronymic(request.getPatronymic());
        newUser.setMilitaryRank(request.getMilitaryRank());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setRole(assignedRole != null ? assignedRole : request.getRequestedRole());
        newUser.setStatus(UserStatus.ACTIVE);
        save(newUser);
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return repository.existsByPhoneNumber(phoneNumber);
    }
}