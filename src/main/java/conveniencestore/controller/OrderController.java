package conveniencestore.controller;

import conveniencestore.dto.common.ApiResult;
import conveniencestore.dto.order.OrderCreateRequest;
import conveniencestore.dto.order.OrderCreateResponse;
import conveniencestore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/orders")
    public ResponseEntity<ApiResult<OrderCreateResponse>> createOrder(
            @RequestBody OrderCreateRequest request
    ) {
        OrderCreateResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(ApiResult.of("SUCCESS_CREATE_ORDER", "결제가 완료되었습니다.", response));
    }
}
