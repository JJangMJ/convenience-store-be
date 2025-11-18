package conveniencestore.controller;

import conveniencestore.dto.common.ApiResult;
import conveniencestore.dto.product.ProductSearchResponse;
import conveniencestore.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/api/products")
    public ResponseEntity<ApiResult<List<ProductSearchResponse>>> getProducts() {
        List<ProductSearchResponse> products = productService.findProducts();
        return ResponseEntity.ok(ApiResult.of("SUCCESS_GET_PRODUCTS", "상품 목록을 가져오는 데 성공했습니다.", products));
    }
}
