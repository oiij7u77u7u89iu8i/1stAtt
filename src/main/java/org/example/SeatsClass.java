package org.example;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

public enum SeatsClass {
    FIRST(2.0, 0.10),
    BUSINESS(1.5, 0.20),
    ECONOMY(1.0, 0.70);

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
    public static List<SeatsClass> getClasses(){
        return List.of(FIRST, BUSINESS, ECONOMY);
    }
}