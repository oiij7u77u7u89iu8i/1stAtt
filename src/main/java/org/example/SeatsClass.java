package org.example;

public enum SeatsClass {
    FIRST(2, 0.10),
    BUSINESS(1.5, 0.20),
    ECONOMY(1, 0.7);

    private final double priceMult;
    private final double seatsMult;

    SeatsClass(double priceMult, double seatsMult) {
        this.priceMult = priceMult;
        this.seatsMult = seatsMult;
    }

    public double getPriceMult() {
        return priceMult;
    }

    public double getSeatsMult() {
        return seatsMult;
    }
}
