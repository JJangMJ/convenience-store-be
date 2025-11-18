package conveniencestore.dto.common;

import java.util.List;

public record ApiResult<T>(
        String code,
        String message,
        T result
) {
    public static <T> ApiResult<T> of(String code, String message, T result) {
        return new ApiResult<>(code, message, result);
    }
}
