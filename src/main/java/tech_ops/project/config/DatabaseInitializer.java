package tech_ops.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech_ops.project.repository.UserRepository;

    @Component
    public class DatabaseInitializer implements CommandLineRunner {
        @Autowired
        UserRepository userRepository;
        @Autowired
        PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) {
//            if (!userRepository.existsByRole(UserRole.USER)) {
//                User superAdmin = new User();
//                superAdmin.setUsername("Nikitenko_RA");
//                superAdmin.setPasswordHash(passwordEncoder.encode("12345"));
//                superAdmin.setName("Никитенко");
//                superAdmin.setSurname("Роман");
//                superAdmin.setPatronymic("Александрович");
//                superAdmin.setMilitaryRank("Рядовой");
//                superAdmin.setPhoneNumber("+375292222222");
//                superAdmin.setRole(UserRole.USER);
//                superAdmin.setStatus(UserStatus.ACTIVE);
//                userRepository.save(superAdmin);
             //   System.out.println("SUPERADMIN created: username=Chernyavskiy_VK, password=12345");
            }
        }
   // }
