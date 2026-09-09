package org.example;


import java.time.*;
import java.util.HashMap;

public class Airport {
    private static int airportCount = 0;
    private int id;
    private ZoneId continentCity; //Asia/Tokio
    private HashMap<String, Flight> flightOut = new HashMap<>();

    public Airport(ZoneId continentCity){
        this.continentCity = continentCity;
        this.id = airportCount;
        airportCount++;
    }

    public ZoneId getZoneId() { return continentCity; }
    public int getId() {return id;}
    public String getCiti(){return continentCity.getId().split("/")[1];}

    public void createFlight(Airport placeIn, Instant timeOut, Instant timeIn, int seats) {
        Flight current = new Flight(this, placeIn, timeOut, timeIn, seats);
        flightOut.put(current.getId(), current);
    }
    public static void createFlight(Airport placeOut, Airport placeIn, Instant timeOut, Instant timeIn, int seats) {
        Flight current = new Flight(placeOut, placeIn, timeOut, timeIn, seats);
        placeOut.flightOut.put(current.getId(), current);
    }
}
