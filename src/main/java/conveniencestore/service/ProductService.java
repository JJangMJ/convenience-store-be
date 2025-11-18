package conveniencestore.service;

import conveniencestore.domain.Product;
import conveniencestore.domain.Promotion;
import conveniencestore.dto.product.ProductSearchResponse;
import conveniencestore.dto.promotion.PromotionSearchResponse;
import conveniencestore.repository.ProductRepository;
import conveniencestore.repository.PromotionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public List<ProductSearchResponse> findProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> {
                    boolean isSoldOut = product.getQuantity() <= 0;
                    Promotion promotion = promotionRepository.findByName(product.getPromotionName()).orElse(null);
                    PromotionSearchResponse promotionSearchResponse = null;
                    if (promotion != null) {
                        promotionSearchResponse = new PromotionSearchResponse(promotion.getName(),
                                promotion.getBuyQuantity(),
                                promotion.getGetQuantity(), promotion.getStartDate(), promotion.getEndDate());
                    }

                    return new ProductSearchResponse(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getQuantity(),
                            isSoldOut,
                            promotionSearchResponse
                    );
                })
                .toList();
    }
}
