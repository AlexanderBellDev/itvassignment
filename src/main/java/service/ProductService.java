package service;

import model.product.Product;

import java.math.BigDecimal;
import java.util.*;

public class ProductService {
    //map of o(1) lookup - dont want to waste time searching through list if doesnt exist
    private final Map<Character,BigDecimal> productMap;
    private final List<Product> existingProductList;
    public ProductService() {
        productMap = new HashMap<>();
        existingProductList = new ArrayList<>();
    }

    public Product createProduct(BigDecimal unitPrice, Character sku) {
        if (productMap.get(sku) != null) {
            Optional<Product> foundProductBySkuAndUnitPrice = existingProductList.stream()
                    .filter(product -> product.getItem().getSku().equals(String.valueOf(sku)))
                    .filter(product -> product.getItem().priceOfItem().equals(unitPrice))
                    .findFirst();

            if(foundProductBySkuAndUnitPrice.isPresent()){
                return foundProductBySkuAndUnitPrice.get();
            }
            throw new IllegalArgumentException("Can't create duplicate product");
        }
        productMap.put(sku,unitPrice);
        Product createdProduct = new Product(unitPrice, sku);
        existingProductList.add(createdProduct);
        return createdProduct;
    }


}
