package tech_ops.project.dto;


import tech_ops.project.entity.User;
import tech_ops.project.enums.UserRole;
import tech_ops.project.enums.UserStatus;

import java.time.LocalDateTime;

public class UserDto {

    private Long id;

    private String username;

    private String name;

    private String surname;

    private String patronymic;

    private String militaryRank;

    private String phoneNumber;

    private UserRole role = UserRole.USER;

    private UserStatus status = UserStatus.ACTIVE;

    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserRole getRole() {
        return role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMilitaryRank() {
        return militaryRank;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setMilitaryRank(String militaryRank) {
        this.militaryRank = militaryRank;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static UserDto fromUser(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setPatronymic(user.getPatronymic());
        dto.setMilitaryRank(user.getMilitaryRank());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
