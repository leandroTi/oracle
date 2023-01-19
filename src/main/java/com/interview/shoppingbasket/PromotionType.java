package com.interview.shoppingbasket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PromotionType {

    TWO_BY_ONE("2 items for the price of 1"),
    FIFTY_PER_CENT_OFF("50% off retail price"),
    TEN_PER_CENT_OFF("10% off retail price");

    @Getter
    String description;

}
