package tech_ops.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech_ops.project.dto.UserDto;
import tech_ops.project.entity.UserRole;
import tech_ops.project.entity.UserStatus;
import tech_ops.project.service.UserService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<UserDto> getAllUsers() {
        return service.getAllUsers();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(params = "username")
    public UserDto getUserByUsername(@RequestParam("username") String username) {
        return service.getUserByUsername(username);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping("/status")
    public void updateUserStatus(@RequestParam("user_id") Long userId,
                                 @RequestParam("status") UserStatus newStatus) {
        service.updateUserStatus(userId, newStatus);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping("/role")
    public void updateUserRole(@RequestParam("user_id") Long userId,
                                 @RequestParam("role") UserRole newRole) {
        service.updateUserRole(userId, newRole);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long userId) {
        service.deleteUserById(userId);
    }
}