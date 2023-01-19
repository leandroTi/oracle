package com.interview.shoppingbasket;

import java.util.List;
import java.util.Optional;

public class RetailPriceCheckoutStep implements CheckoutStep {
    private PricingService pricingService;
    private double retailTotal;

    public RetailPriceCheckoutStep(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        List<Promotion> promotions = checkoutContext.getPromotions();
        retailTotal = 0.0;

        for (BasketItem basketItem: basket.getItems()) {
            int quantity = basketItem.getQuantity();
            Optional<Promotion> promotion = promotions.stream().filter(it -> it.getProductCode().equals(basketItem.getProductCode())).findAny();
            double price = (promotion.isPresent())
                ? applyPromotion(promotion.get(), pricingService.getPrice(basketItem.getProductCode()))
                : pricingService.getPrice(basketItem.getProductCode());
            basketItem.setProductRetailPrice(price);
            retailTotal += quantity*price;
        }

        checkoutContext.setRetailPriceTotal(retailTotal);
    }

    public double applyPromotion(Promotion promotion, double price) {
        double promotionPrice;

        switch (promotion.getPromotionType()){
            case TWO_BY_ONE:
            case FIFTY_PER_CENT_OFF:
                promotionPrice = price / 2;
                break;
            case TEN_PER_CENT_OFF:
                promotionPrice = price - (price * 0.1);
                break;
            default:
                throw new RuntimeException("Unknown promotion type");
        }

        return promotionPrice;
    }
}
