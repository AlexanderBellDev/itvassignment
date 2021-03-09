package model.basket;

import java.math.BigDecimal;

public class SpecialPrice {
    private final Integer quantity;
    private final BigDecimal price;

    public SpecialPrice(Integer quantity, BigDecimal price) {
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "SpecialPrice{" +
                "quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
