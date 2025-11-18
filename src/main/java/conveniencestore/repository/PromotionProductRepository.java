package conveniencestore.repository;

import conveniencestore.domain.PromotionProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionProductRepository extends JpaRepository<PromotionProduct, Long> {
}
