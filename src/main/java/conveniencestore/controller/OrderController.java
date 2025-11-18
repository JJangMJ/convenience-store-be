package conveniencestore.controller;

import conveniencestore.dto.order.OrderCreateRequest;
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
    public ResponseEntity<Void> createOrder(
            @RequestBody OrderCreateRequest request
    ) {
        orderService.createOrder(request);
        return ResponseEntity.ok().build();
    }
}
