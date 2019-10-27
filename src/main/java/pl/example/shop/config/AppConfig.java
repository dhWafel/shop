package pl.example.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.example.shop.domain.Role;
import pl.example.shop.repository.RoleRepository;

@Configuration
public class AppConfig {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (roleRepository.findByName("ROLE_USER") == null) {
                roleRepository.save(Role
                        .builder()
                        .name("ROLE_USER")
                        .build());
            }

            if (roleRepository.findByName("ROLE_ADMIN") == null) {
                roleRepository.save(Role
                        .builder()
                        .name("ROLE_ADMIN")
                        .build());

            }
        };
    }
}
