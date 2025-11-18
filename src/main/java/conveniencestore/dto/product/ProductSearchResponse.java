package conveniencestore.dto.product;

import conveniencestore.dto.promotion.PromotionSearchResponse;

public record ProductSearchResponse(
        Long productId,
        String name,
        int price,
        int stock,
        boolean isSoldOut,
        PromotionSearchResponse promotionSearchResponse
) {
}
