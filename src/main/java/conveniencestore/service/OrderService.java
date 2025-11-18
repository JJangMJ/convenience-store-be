package conveniencestore.service;

import conveniencestore.domain.Order;
import conveniencestore.domain.Product;
import conveniencestore.dto.order.OrderCreateRequest;
import conveniencestore.dto.order.OrderItemRequest;
import conveniencestore.repository.OrderRepository;
import conveniencestore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(OrderCreateRequest request) {
        int originalTotalAmount = 0;
        int promotionDiscountAmount = 0;
        int membershipDiscountAmount = 0;

        for (OrderItemRequest orderItem : request.orderItems()) {
            Product product = productRepository.findById(orderItem.productId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));
            product.decreaseQuantity(orderItem.quantity());
            originalTotalAmount += product.getPrice() * orderItem.quantity();
        }

        if (request.applyMembership()) {
            membershipDiscountAmount = Math.min((int) Math.floor(originalTotalAmount * 0.10), 8_000);
        }

        Order order = Order.createOrder(originalTotalAmount, promotionDiscountAmount, membershipDiscountAmount);
        orderRepository.save(order);
    }
}
