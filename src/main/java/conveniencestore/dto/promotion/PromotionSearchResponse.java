package conveniencestore.dto.promotion;

import java.time.LocalDate;

public record PromotionSearchResponse(
        String name,
        int buyQuantity,
        int getQuantity,
        LocalDate startDate,
        LocalDate endDate
) {
}
