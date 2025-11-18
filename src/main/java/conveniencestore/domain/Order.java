package conveniencestore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_total_amount")
    private int originalTotalAmount;

    @Column(name = "promotion_discount_amount")
    private int promotionDiscountAmount;

    @Column(name = "membership_discount_amount")
    private int membershipDiscountAmount;

    @Column(name = "final_total_amount")
    private int finalTotalAmount;

    public static Order createOrder(int originalTotalAmount, int promotionDiscountAmount, int membershipDiscountAmount) {
        Order order = new Order();
        order.originalTotalAmount = originalTotalAmount;
        order.promotionDiscountAmount = Math.max(0, promotionDiscountAmount);
        order.membershipDiscountAmount = Math.max(0, membershipDiscountAmount);
        int finalAmount = originalTotalAmount - order.promotionDiscountAmount - order.membershipDiscountAmount;
        order.finalTotalAmount = Math.max(0, finalAmount);
        return order;
    }
}
