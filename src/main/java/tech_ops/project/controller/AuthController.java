package tech_ops.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech_ops.project.dto.JwtResponse;
import tech_ops.project.dto.LoginRequest;
import tech_ops.project.dto.RegistrationRequestDto;
import tech_ops.project.service.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequestDto requestDto) {
        authService.registerRequest(requestDto);
        return ResponseEntity.ok("Запрос на регистрацию успешно отправлен. Ожидайте одобрения заявки.");
    }
}