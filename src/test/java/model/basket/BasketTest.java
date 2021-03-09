package model.basket;

import model.item.Item;
import model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ProductService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {
    Basket basket;
    ProductService productService;
    Item itemProductA1;
    Item itemProductA2;
    Item itemProductA3;
    Item itemProductB1;
    Item itemProductB2;
    Map<String, SpecialPrice> discountList;

    @BeforeEach
    void setUp() {
        basket = new Basket();
        productService = new ProductService();
        Product product = productService.createProduct(BigDecimal.valueOf(50), 'A');
        Product product2 = productService.createProduct(BigDecimal.valueOf(30), 'B');
        itemProductA1 = product.getItem();
        itemProductA2 = product.getItem();
        itemProductA3 = product.getItem();
        itemProductB1 = product2.getItem();
        itemProductB2 = product2.getItem();
        discountList = new HashMap<>(Map.of("A", new SpecialPrice(3, BigDecimal.valueOf(130)),
                "B", new SpecialPrice(2, BigDecimal.valueOf(45))));
    }

    @Test
    void addItemToBasket() {
        basket.addItemToBasket(itemProductA1);

        List<Item> currentItemsInBasket = basket.getCurrentItemsInBasket();
        assertEquals(currentItemsInBasket.size(), 1);
        assertTrue(currentItemsInBasket.contains(itemProductA1));
    }

    @Test
    void addItemsToBasket() {
        basket.addItemsToBasket(itemProductA1, itemProductA2, itemProductB1);

        List<Item> currentItemsInBasket = basket.getCurrentItemsInBasket();
        assertEquals(currentItemsInBasket.size(), 3);
        assertTrue(currentItemsInBasket.containsAll(List.of(itemProductA1, itemProductA2, itemProductB1)));
    }

    @Test
    void removeItemFromBasket() {
        basket.addItemsToBasket(itemProductA1, itemProductA2, itemProductB1);
        boolean isRemoved = basket.removeItemFromBasket(itemProductA1);
        assertTrue(isRemoved);
        List<Item> currentItemsInBasket = basket.getCurrentItemsInBasket();
        assertEquals(currentItemsInBasket.size(), 2);
        assertTrue(currentItemsInBasket.containsAll(List.of(itemProductA2, itemProductB1)));
    }

    @Test
    void removeItemFromBasketItemDoesntExist() {
        basket.addItemsToBasket(itemProductB1);
        boolean isRemoved = basket.removeItemFromBasket(itemProductA1);
        assertFalse(isRemoved);
        List<Item> currentItemsInBasket = basket.getCurrentItemsInBasket();
        assertEquals(currentItemsInBasket.size(), 1);
        assertTrue(currentItemsInBasket.contains(itemProductB1));
    }

    @Test
    void checkout() {
        basket.addItemsToBasket(itemProductA1, itemProductA2, itemProductA3, itemProductB1, itemProductB2);
        BigDecimal totalPrice = basket.checkout(discountList);
        assertEquals(BigDecimal.valueOf(175).doubleValue(), totalPrice.doubleValue());
    }

    @Test
    void testToString() {
        basket.addItemsToBasket(itemProductA1, itemProductA2, itemProductB1);

        assertEquals("Basket{itemList=[ItemByUnit{unitPrice=50, sku=A}," +
                " ItemByUnit{unitPrice=50, sku=A}, ItemByUnit{unitPrice=30, sku=B}]," +
                " skuToSpecialPriceMap=null}", basket.toString());
    }

    @Test
    void testDiscountItemSku() {

        basket.addItemsToBasket(itemProductA1, itemProductA2, itemProductA3);

        basket.checkout(discountList);

        List<Item> currentItemsInBasket = basket.getCurrentItemsInBasket();
        Optional<Item> discountSpecialPrice = currentItemsInBasket.stream()
                .filter(item -> item.getSku().contains("DiscountSpecialPrice"))
                .findFirst();

        discountSpecialPrice.ifPresent(discountItem -> assertEquals("DiscountSpecialPrice{quantity=3, price=130}", discountItem.getSku()));

    }

    @Test
    void testDiscountItemToString() {

        basket.addItemsToBasket(itemProductA1, itemProductA2, itemProductA3);

        basket.checkout(discountList);

        List<Item> currentItemsInBasket = basket.getCurrentItemsInBasket();
        Optional<Item> discountSpecialPrice = currentItemsInBasket.stream()
                .filter(item -> item.getSku().contains("DiscountSpecialPrice"))
                .findFirst();

        discountSpecialPrice.ifPresent(discountItem -> assertEquals("DiscountItem{discountPrice=130, specialPrice=SpecialPrice{quantity=3, price=130}}", discountItem.toString()));

    }
}