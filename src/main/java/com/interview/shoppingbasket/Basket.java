package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Basket {
    private List<BasketItem> items = new ArrayList<>();

    public void add(String productCode, String productName, int quantity) {
        BasketItem basketItem = new BasketItem();
        basketItem.setProductCode(productCode);
        basketItem.setProductName(productName);
        basketItem.setQuantity(quantity);

        items.add(basketItem);
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void consolidateItems() {
        HashMap<String, BasketItem> uniqueItems = new HashMap<>();

        items.stream().forEach(it -> {
            if(uniqueItems.containsKey(it.getProductCode())) {
                it.setQuantity(it.getQuantity() + uniqueItems.get(it.getProductCode()).getQuantity());
            }

            uniqueItems.put(it.getProductCode(), it);
        });

        items = new ArrayList<>(uniqueItems.values());
    }
}
