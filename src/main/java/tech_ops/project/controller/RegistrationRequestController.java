package tech_ops.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech_ops.project.dto.RequestResponseDto;
import tech_ops.project.dto.ReviewRequestDto;
import tech_ops.project.entity.RequestStatus;
import tech_ops.project.service.RegistrationRequestService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/requests")
@PreAuthorize("hasRole('SUPERADMIN')")
public class RegistrationRequestController {

    private final RegistrationRequestService requestService;

    @Autowired
    public RegistrationRequestController(RegistrationRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public ResponseEntity<List<RequestResponseDto>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RequestResponseDto>> getRequestsByStatus(@PathVariable RequestStatus status) {
        return ResponseEntity.ok(requestService.getRequestsByStatus(status));
    }

    @GetMapping("/reviewed")
    public ResponseEntity<List<RequestResponseDto>> getReviewedRequests() {
        return ResponseEntity.ok(requestService.getReviewedRequests());
    }

    @PostMapping("/{requestId}/review")
    public ResponseEntity<?> reviewRequest(@PathVariable Long requestId, @Valid @RequestBody ReviewRequestDto reviewDto) {
        requestService.reviewRequest(requestId, reviewDto);
        return ResponseEntity.ok("Request reviewed successfully");
    }

    @PostMapping("/{requestId}/restore")
    public ResponseEntity<?> restoreRequest(@PathVariable Long requestId) {
        requestService.restoreRequest(requestId);
        return ResponseEntity.ok("Request restored to pending");
    }
}