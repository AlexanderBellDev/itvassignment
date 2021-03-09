package model.item;

import java.math.BigDecimal;

public interface Item {
    BigDecimal priceOfItem();

    String getSku();

    void setPriceToZero();
}
