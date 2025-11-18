package conveniencestore.dto.order;

import java.util.List;

public record OrderCreateRequest(
        List<OrderItemRequest> orderItems,
        boolean applyMembership,
        boolean takePromotionFreeGift
) {
}
