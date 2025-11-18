package conveniencestore.dto.order;

public record OrderCreateResponse(
        Long orderId,
        int originalTotalAmount,
        int promotionDiscountAmount,
        int membershipDiscountAmount,
        int finalTotalAmount
) {
}
