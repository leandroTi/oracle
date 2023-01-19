package com.interview.shoppingbasket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class PromotionCheckoutStepTest {

    CheckoutContext checkoutContext;
    PromotionsService promotionsService;
    Basket basket;
    List<Promotion> promotions;

    @BeforeEach
    void setup() {
        promotionsService = Mockito.mock(PromotionsService.class);
        checkoutContext = Mockito.mock(CheckoutContext.class);

        basket = new Basket();
        promotions = new ArrayList<>();

        when(checkoutContext.getBasket()).thenReturn(basket);
        when(promotionsService.getPromotions(Mockito.any())).thenReturn(promotions);
    }

    @Test
    void setPromotionsToContext() {
        PromotionCheckoutStep promotionCheckoutStep = new PromotionCheckoutStep(promotionsService);
        promotionCheckoutStep.execute(checkoutContext);

        Mockito.verify(checkoutContext).setPromotions(promotions);
    }
}
