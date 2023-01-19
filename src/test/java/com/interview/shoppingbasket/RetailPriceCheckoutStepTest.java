package com.interview.shoppingbasket;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class RetailPriceCheckoutStepTest {

    PricingService pricingService;
    CheckoutContext checkoutContext;
    Basket basket;

    @BeforeEach
    void setup() {
        pricingService = Mockito.mock(PricingService.class);
        checkoutContext = Mockito.mock(CheckoutContext.class);
        basket = new Basket();

        when(checkoutContext.getBasket()).thenReturn(basket);
    }

    @Test
    void setPriceZeroForEmptyBasket() {

        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);

        retailPriceCheckoutStep.execute(checkoutContext);

        Mockito.verify(checkoutContext).setRetailPriceTotal(0.0);
    }

    @Test
    void setPriceOfProductToBasketItem() {

        basket.add("product1", "myproduct1", 10);
        basket.add("product2", "myproduct2", 10);

        when(pricingService.getPrice("product1")).thenReturn(3.99);
        when(pricingService.getPrice("product2")).thenReturn(2.0);
        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);

        retailPriceCheckoutStep.execute(checkoutContext);
        Mockito.verify(checkoutContext).setRetailPriceTotal(3.99*10+2*10);

    }

    @Test
    void setPriceOfProductToBasketItemWithPromotion() {
        basket.add("product1", "myproduct1", 10);
        basket.add("product2", "myproduct2", 10);
        basket.add("product3", "myproduct3", 10);

        when(pricingService.getPrice("product1")).thenReturn(1.0);
        when(pricingService.getPrice("product2")).thenReturn(2.0);
        when(pricingService.getPrice("product3")).thenReturn(3.0);


        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion("product1", PromotionType.TWO_BY_ONE));
        promotions.add(new Promotion("product2", PromotionType.FIFTY_PER_CENT_OFF));
        promotions.add(new Promotion("product3", PromotionType.TEN_PER_CENT_OFF));
        when(checkoutContext.getPromotions()).thenReturn(promotions);

        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);

        retailPriceCheckoutStep.execute(checkoutContext);
        Mockito.verify(checkoutContext).setRetailPriceTotal(42);
    }

}
