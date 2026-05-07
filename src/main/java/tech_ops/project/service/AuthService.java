package tech_ops.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech_ops.project.dto.JwtResponse;
import tech_ops.project.dto.LoginRequest;
import tech_ops.project.dto.RegistrationRequestDto;
import tech_ops.project.entity.RegistrationRequest;
import tech_ops.project.enums.RequestStatus;
import tech_ops.project.enums.UserRole;
import tech_ops.project.security.JwtUtils;
import tech_ops.project.security.UserDetailsImpl;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationRequestService registrationRequestService;
    private final UserService userService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                       PasswordEncoder passwordEncoder, RegistrationRequestService registrationRequestService,
                       UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.registrationRequestService = registrationRequestService;
        this.userService = userService;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        return new JwtResponse(jwt, userDetails.getUsername(), role);
    }

    public void registerRequest(RegistrationRequestDto requestDto) {
        if (userService.existsByUsername(requestDto.getUsername())) {
            throw new RuntimeException("Пользователь с указанным именем уже существует");
        }
        if (userService.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new RuntimeException("Пользователь с указанным номером телефона уже существует");
        }
        if (registrationRequestService.existsByUsernameAndStatusNot(requestDto.getUsername(), RequestStatus.REJECTED)) {
            throw new RuntimeException("Заявка с таким именем пользователя уже в обработке или одобрена");
        }
        if (registrationRequestService.existsByPhoneNumberAndStatusNot(requestDto.getPhoneNumber(), RequestStatus.REJECTED)) {
            throw new RuntimeException("Заявка с таким номером телефона уже в обработке или одобрена");
        }

        RegistrationRequest request = new RegistrationRequest();
        request.setUsername(requestDto.getUsername());
        request.setPasswordHash(passwordEncoder.encode(requestDto.getPassword()));
        request.setName(requestDto.getName());
        request.setSurname(requestDto.getSurname());
        request.setPatronymic(requestDto.getPatronymic());
        request.setMilitaryRank(requestDto.getMilitaryRank());
        request.setPhoneNumber(requestDto.getPhoneNumber());
        request.setRequestedRole(UserRole.USER);
        request.setStatus(RequestStatus.PENDING);

        registrationRequestService.save(request);
    }
}