package pl.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.example.shop.domain.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
