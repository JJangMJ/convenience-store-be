package conveniencestore.service;

import conveniencestore.domain.Order;
import conveniencestore.domain.Product;
import conveniencestore.domain.Promotion;
import conveniencestore.dto.order.OrderCreateRequest;
import conveniencestore.dto.order.OrderCreateResponse;
import conveniencestore.dto.order.OrderItemRequest;
import conveniencestore.repository.OrderRepository;
import conveniencestore.repository.ProductRepository;
import conveniencestore.repository.PromotionRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PromotionRepository promotionRepository;

    @Transactional
    public OrderCreateResponse createOrder(OrderCreateRequest request) {
        int originalTotalAmount = 0;
        int promotionDiscountAmount = 0;
        int membershipDiscountAmount = 0;
        int membershipTargetAmount = 0;

        for (OrderItemRequest orderItem : request.orderItems()) {
            Product product = productRepository.findById(orderItem.productId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

            product.decreaseQuantity(orderItem.quantity());

            int lineOriginalAmount = product.getPrice() * orderItem.quantity();
            originalTotalAmount += lineOriginalAmount;

            promotionDiscountAmount += calculatePromotionDiscount(product, orderItem.quantity());

            boolean hasPromotion = product.getPromotionName() != null && !product.getPromotionName().isBlank();
            if (!hasPromotion) {
                membershipTargetAmount += lineOriginalAmount;
            }
        }

        int amountAfterPromotion = originalTotalAmount - promotionDiscountAmount;
        amountAfterPromotion = Math.max(0, amountAfterPromotion);

        if (request.applyMembership()) {
            int membershipDiscountCandidate = (int) Math.floor(membershipTargetAmount * 0.30);
            membershipDiscountCandidate = Math.min(membershipDiscountCandidate, 8_000);
            membershipDiscountAmount = Math.min(membershipDiscountCandidate, amountAfterPromotion);
        }

        Order order = Order.createOrder(originalTotalAmount, promotionDiscountAmount, membershipDiscountAmount);
        orderRepository.save(order);

        return new OrderCreateResponse(
                order.getId(),
                order.getOriginalTotalAmount(),
                order.getPromotionDiscountAmount(),
                order.getMembershipDiscountAmount(),
                order.getFinalTotalAmount()
        );
    }

    private int calculatePromotionDiscount(Product product, int quantity) {
        String promotionName = product.getPromotionName();
        if (promotionName == null || promotionName.isBlank()) {
            return 0;
        }

        Promotion promotion = promotionRepository.findByName(promotionName)
                .filter(p -> isPromotionActive(p, LocalDate.now()))
                .orElse(null);

        if (promotion == null) {
            return 0;
        }

        int buyQuantity = promotion.getBuyQuantity();
        int getQuantity = promotion.getGetQuantity();
        if (buyQuantity <= 0 || getQuantity <= 0) {
            return 0;
        }

        int groupSize = buyQuantity + getQuantity;
        int groupCount = quantity / groupSize;
        int freeItems = groupCount * getQuantity;

        return freeItems * product.getPrice();
    }

    private boolean isPromotionActive(Promotion promotion, LocalDate today) {
        return (promotion.getStartDate() == null || !today.isBefore(promotion.getStartDate()))
                && (promotion.getEndDate() == null || !today.isAfter(promotion.getEndDate()));
    }
}
