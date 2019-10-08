package pl.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.example.shop.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long>{

    Optional<User> findByEmail(String email);

}
