package pl.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.example.shop.domain.Role;

public interface RoleRepository extends JpaRepository <Role, Long> {

    Role findByName(String name);
}
