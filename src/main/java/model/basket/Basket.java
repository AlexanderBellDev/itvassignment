package model.basket;

import model.item.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Basket {
    private final List<Item> itemList;
    private Map<String, SpecialPrice> skuToSpecialPriceMap;

    public Basket() {
        this.itemList = new ArrayList<>();
    }

    public void addItemToBasket(Item item) {
        itemList.add(item);
    }

    public List<Item> getCurrentItemsInBasket(){
       return this.itemList;
    }

    public void addItemsToBasket(Item... items) {
        itemList.addAll(Arrays.asList(items));
    }

    public boolean removeItemFromBasket(Item item) {
        if (itemList.contains(item)) {
            itemList.remove(item);
            return true;
        }
        return false;
    }


    public BigDecimal checkout(Map<String, SpecialPrice> skuToSpecialPriceMap) {
        applyDiscount(skuToSpecialPriceMap);
        return this.itemList.stream()
                .map(Item::priceOfItem)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void applyDiscount(Map<String, SpecialPrice> skuToSpecialPriceMap) {
        this.skuToSpecialPriceMap = skuToSpecialPriceMap;
        Map<String, List<Item>> collect = itemList.stream()
                .collect(Collectors.groupingBy(Item::getSku));

        for (Map.Entry<String, SpecialPrice> specialPriceEntry : skuToSpecialPriceMap.entrySet()) {
            List<Item> items = collect.get(specialPriceEntry.getKey());

            if (items != null && items.size() >= specialPriceEntry.getValue().getQuantity()) {
                for (int i = 0; i < specialPriceEntry.getValue().getQuantity(); i++) {
                    Item currentItem = items.get(i);

                    this.itemList.stream()
                            .filter(item -> item.equals(currentItem))
                            .findFirst()
                            .ifPresent(Item::setPriceToZero);
                }
                this.itemList.add(new DiscountItem(specialPriceEntry.getValue().getPrice(), specialPriceEntry.getValue()));
            }
        }
    }

    @Override
    public String toString() {
        return "Basket{" +
                "itemList=" + itemList +
                ", skuToSpecialPriceMap=" + skuToSpecialPriceMap +
                '}';
    }


    public static class DiscountItem implements Item {
        private final BigDecimal discountPrice;
        private final SpecialPrice specialPrice;

        private DiscountItem(BigDecimal discountPrice, SpecialPrice specialPrice) {
            this.discountPrice = discountPrice;
            this.specialPrice = specialPrice;
        }

        @Override
        public BigDecimal priceOfItem() {
            return this.discountPrice;
        }

        @Override
        public String getSku() {
            return "Discount" + specialPrice;
        }

        @Override
        public void setPriceToZero() {
        }

        @Override
        public String toString() {
            return "DiscountItem{" +
                    "discountPrice=" + discountPrice +
                    ", specialPrice=" + specialPrice +
                    '}';
        }
    }
}
