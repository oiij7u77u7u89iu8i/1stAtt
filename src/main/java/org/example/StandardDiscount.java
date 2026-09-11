package org.example;

public class StandardDiscount implements DiscountStrategy {
    private final double discount = 0;
    @Override
    public double getPrice(Route route) {
        double total = 0;
        for(Segment f:route.getList()){
            total += f.seatsClass().getPriceMult() *  f.flight().getTimeInAir().toMinutes() / 60 * 10 * (1 - discount);
        }
        return total;
    }
}