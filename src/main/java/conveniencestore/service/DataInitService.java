package conveniencestore.service;

import conveniencestore.domain.Product;
import conveniencestore.domain.Promotion;
import conveniencestore.domain.PromotionProduct;
import conveniencestore.repository.ProductRepository;
import conveniencestore.repository.PromotionProductRepository;
import conveniencestore.repository.PromotionRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DataInitService {
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final PromotionProductRepository promotionProductRepository;

    @Transactional
    public void initPromotions() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("promotions.md").getInputStream(),
                        StandardCharsets.UTF_8))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String name = fields[0];
                int buyQuantity = Integer.parseInt(fields[1]);
                int getQuantity = Integer.parseInt(fields[2]);
                LocalDate startDate = LocalDate.parse(fields[3]);
                LocalDate endDate = LocalDate.parse(fields[4]);

                promotionRepository.save(Promotion.createPromotion(name, buyQuantity, getQuantity, startDate, endDate));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void initProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/products.md"))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String name = fields[0];
                int price = Integer.parseInt(fields[1]);
                int quantity = Integer.parseInt(fields[2]);
                String promotionName = fields[3];

                if (!promotionName.equals("null")) {
                    Product product = productRepository.findByName(name)
                            .orElseGet(() -> productRepository.save(Product.createProduct(name, price, quantity, promotionName)));
                    promotionRepository.findByName(promotionName)
                            .ifPresent(promotion -> promotionProductRepository.save(
                                    PromotionProduct.createPromotionProduct(product, promotion)));
                } else {
                    productRepository.save(Product.createProduct(name, price, quantity, null));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
