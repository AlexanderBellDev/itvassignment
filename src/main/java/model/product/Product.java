package model.product;

import model.item.Item;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final BigDecimal unitPrice;
    private final Character sku;

    public Product(BigDecimal unitPrice, Character sku) {
        this.unitPrice = unitPrice;
        this.sku = sku;
    }

    public Item getItem() {
        return new ItemByUnit(String.valueOf(sku), unitPrice);
    }


    public static class ItemByUnit implements Item {
        private BigDecimal unitPrice;
        private final String sku;

        private ItemByUnit(String sku, BigDecimal unitPrice) {
            this.sku = sku;
            this.unitPrice = unitPrice;
        }

        @Override
        public BigDecimal priceOfItem() {
            return unitPrice;
        }

        public String getSku() {
            return sku;
        }

        @Override
        public void setPriceToZero() {
            this.unitPrice = BigDecimal.ZERO;
        }

        @Override
        public String toString() {
            return "ItemByUnit{" +
                    "unitPrice=" + unitPrice +
                    ", sku=" + sku +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemByUnit that = (ItemByUnit) o;
            return Objects.equals(unitPrice, that.unitPrice) && Objects.equals(sku, that.sku);
        }

        @Override
        public int hashCode() {
            return Objects.hash(unitPrice, sku);
        }
    }
}

