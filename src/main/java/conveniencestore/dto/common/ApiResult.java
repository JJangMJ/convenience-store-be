package conveniencestore.dto.common;

import java.util.List;

public record ApiResult<T>(
        String code,
        String message,
        List<T> result
) {
    public static <T> ApiResult<T> of(String code, String message, List<T> result) {
        return new ApiResult<>(code, message, result);
    }
}
