package tech_ops.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech_ops.project.entity.User;

@Service
public class UserRegistrationCoordinatorService {

    private final UserService userService;
    private final RegistrationRequestService registrationRequestService;

    @Autowired
    public UserRegistrationCoordinatorService(UserService userService,
                                              RegistrationRequestService registrationRequestService) {
        this.userService = userService;
        this.registrationRequestService = registrationRequestService;
    }

    @Transactional
    public void deleteUserWithRequest(Long userId) {
        User user = userService.getUserById(userId);
        userService.deleteUserById(userId);
        registrationRequestService.deleteByUsername(user.getUsername());
    }
}
