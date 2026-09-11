package org.example;

public class StudentDiscount implements DiscountStrategy{
    private final double discount = 0.15;
    @Override
    public double getPrice(Route route) {
        double total = 0;
        for(Segment f:route.getList()){
            total += f.seatsClass().getPriceMult() *  f.flight().getTimeInAir().toMinutes() / 60 * 10 * (1 - discount);
        }
        return total;
    }
}
