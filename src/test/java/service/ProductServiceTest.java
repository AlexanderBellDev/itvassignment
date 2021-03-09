package service;

import model.item.Item;
import model.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductServiceTest {

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Test
    void createProductExistingSkuDifferentPrice() {
        productService.createProduct(BigDecimal.ONE, 'A');
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.createProduct(BigDecimal.TEN, 'A'));
    }

    @Test
    void createProductExistingSkuSamePrice() {
        Product productOne = productService.createProduct(BigDecimal.ONE, 'A');
        Product productTwo = productService.createProduct(BigDecimal.ONE, 'A');

        assertEquals(productTwo.getItem(), productOne.getItem());
    }

    @Test
    void createProduct() {
        Product product = productService.createProduct(BigDecimal.ONE, 'A');

        assertEquals(BigDecimal.ONE,product.getItem().priceOfItem());
        assertEquals(String.valueOf('A'),product.getItem().getSku());
    }
}