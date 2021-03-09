import model.basket.Basket;
import model.basket.SpecialPrice;
import model.product.Product;
import service.ProductService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        Product a = productService.createProduct(BigDecimal.valueOf(50),'A');
        Product b = productService.createProduct(BigDecimal.valueOf(30),'B');

        Basket basket = new Basket();
        basket.addItemsToBasket(a.getItem(),a.getItem(),a.getItem(),a.getItem(),b.getItem(),b.getItem());

        Map<String, SpecialPrice> discountList = new HashMap<>(Map.of("A",new SpecialPrice(3,BigDecimal.valueOf(130)),
                "B",new SpecialPrice(2,BigDecimal.valueOf(45))));
        BigDecimal totalPrice = basket.checkout(discountList);

        System.out.println("Total price was: " + totalPrice.doubleValue());
    }
}
