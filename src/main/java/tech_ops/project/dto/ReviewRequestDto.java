package tech_ops.project.dto;

import jakarta.validation.constraints.NotNull;
import tech_ops.project.enums.RequestStatus;
import tech_ops.project.enums.UserRole;

public class ReviewRequestDto {
    @NotNull
    private RequestStatus status;
    private UserRole role;

    public RequestStatus getStatus() {
        return status;
    }

    public UserRole getRole() {
        return role;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
