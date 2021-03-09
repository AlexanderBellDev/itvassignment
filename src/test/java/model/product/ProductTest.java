package model.product;

import model.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ProductService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    ProductService productService;
    Item itemOne;
    Item itemTwo;
    Item itemThree;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
        Product product = productService.createProduct(BigDecimal.ONE, 'A');
        Product product2 = productService.createProduct(BigDecimal.TEN,'B');
        itemOne = product.getItem();
        itemTwo = product.getItem();
        itemThree = product2.getItem();
    }

    @Test
    void setItemPriceToZero() {
        itemOne.setPriceToZero();

        assertEquals(BigDecimal.ZERO,itemOne.priceOfItem());
    }

    @Test
    void itemToString() {
        assertEquals("ItemByUnit{unitPrice=1, sku=A}",itemOne.toString());
    }

    @Test
    void itemEqualityEquals(){
        assertEquals(itemOne,itemTwo);
        assertEquals(itemOne.hashCode(), itemTwo.hashCode());
    }

    @Test
    void itemEqualityNotEquals(){
        assertNotEquals(itemOne,itemThree);
        assertNotEquals(itemOne.hashCode(), itemThree.hashCode());
    }
}