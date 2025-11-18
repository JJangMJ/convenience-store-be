package conveniencestore.repository;

import conveniencestore.domain.Promotion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> findByName(String name);
}
