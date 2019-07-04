package pl.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.example.shop.domain.User;

public interface UserRepository extends JpaRepository <User, Long>{

}
