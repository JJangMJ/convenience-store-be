package conveniencestore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "buy_quantity")
    private int buyQuantity;

    @Column(name = "get_quantity")
    private int getQuantity;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public static Promotion createPromotion(String name, int buyQuantity, int getQuantity, LocalDate startDate,
                                            LocalDate endDate) {
        Promotion promotion = new Promotion();
        promotion.name = name;
        promotion.buyQuantity = buyQuantity;
        promotion.getQuantity = getQuantity;
        promotion.startDate = startDate;
        promotion.endDate = endDate;
        return promotion;
    }
}
