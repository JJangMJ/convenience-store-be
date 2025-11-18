package conveniencestore.controller;

import conveniencestore.service.DataInitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataInitController {
    private final DataInitService dataInitService;

    @PostMapping("/api/initData")
    public ResponseEntity<Void> initData() {
        dataInitService.initPromotions();
        dataInitService.initProducts();;
        return ResponseEntity.ok().build();
    }
}
